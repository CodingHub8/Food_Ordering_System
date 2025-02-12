package food_ordering_system.Controller;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeliveryRunnerController {
    private final String reviewsFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String runnersFilePath = "src/food_ordering_system/Data/delivery_runners.txt";
    private final String ordersFilePath = "src/food_ordering_system/Data/orders.txt";
    private final String tasksFilePath = "src/food_ordering_system/Data/tasks.txt";

    public String[] getRunnerDetails(String vendorID){
        String[] vendor = new String[4];

        try (BufferedReader br = new BufferedReader(new FileReader(runnersFilePath))) {
            String line;
            br.readLine();//skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(vendorID)){
                    vendor[0] = parts[0];// ID
                    vendor[1] = parts[1];// name
                    vendor[2] = parts[2];// password
                    vendor[3] = parts[3];// rating
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading reviews file: " + e.getMessage());
        }

        return vendor;
    }

    public List<Double> getRevenues(String runnerID, String timestamp) {
        // Data structure to store revenues by days, months, or quarters
        Map<String, Double> revenueMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(tasksFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String taskTimestamp = parts[6].trim(); // Extract timestamp
                double taskPayment = Double.parseDouble(parts[4]); // Extract total amount

                // Parse date from timestamp
                String key = getString(timestamp, taskTimestamp);

                // Add revenue to the respective key
                if(parts[2].trim().equals(runnerID)){
                    revenueMap.put(key, revenueMap.getOrDefault(key, 0.0) + taskPayment);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing file: " + e.getMessage());
        }

        // Add revenues from map to list in the correct order
        return new ArrayList<>(revenueMap.values());
    }

    public String getReviews(String runnerID){
        String reviews = "";

        try (BufferedReader br = new BufferedReader(new FileReader(reviewsFilePath))) {
            String line;
            br.readLine();//skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts[3].trim().equals(runnerID)){
                    for(int i = 0; i < parts.length; i++){
                        parts[i] = parts[i].trim();
                    }

                    reviews += parts[2] + ": " + parts[5] + " (" + parts[4] + " rating)" + "\n";
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        return reviews;
    }

    static class CustomTableModel extends AbstractTableModel {
        private List<String[]> data;
        private String[] columnNames;

        public CustomTableModel(List<String[]> data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    public TableModel loadData(String runnerID, String... status) {
        List<String[]> data = readTaskData(runnerID, status);

        String header = "Task ID, Order ID, Runner ID, Status, Payment (RM), Location, Timestamp";
        String[] columnNames = header.split(",");

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return new DeliveryRunnerController.CustomTableModel(data, columnNames);
    }

    private List<String[]> readTaskData(String vendorID, String... status) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tasksFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");

                if(row[2].trim().equals(vendorID)) {
                    if(status.length > 1){
                        if(row[3].trim().equals(status[0]) || row[3].trim().equals(status[1])){
                            data.add(row);
                        }
                    } else {
                        if(row[3].trim().equals(status[0])){
                            data.add(row);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String getString(String timestamp, String orderTimestamp) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = sdf.parse(orderTimestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String key;
        switch (timestamp) {
            case "Days" -> {
                // Group revenues by specific day (dd/MM/yyyy)
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
                key = dayFormat.format(date);
            }
            case "Months" -> {
                // Group revenues by month (MM/yyyy)
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
                key = monthFormat.format(date);
            }
            case "Quarters" -> {
                // Group revenues by quarter (Q/yyyy)
                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
                int quarter = (month - 1) / 3 + 1; // Calculate quarter
                int year = calendar.get(Calendar.YEAR);
                key = "Q" + quarter + "/" + year;
            }
            default -> throw new IllegalArgumentException("Invalid timestamp type: " + timestamp);
        }
        return key;
    }

    public void updateTaskStatusInFile(String taskID, String newStatus) {
        List<String> fileContents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(tasksFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].trim().equals(taskID)) {
                    parts[3] = newStatus; // Update status
                    line = String.join(",", parts);
                }
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tasksFilePath))) {
            for (String contentLine : fileContents) {
                bw.write(contentLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
