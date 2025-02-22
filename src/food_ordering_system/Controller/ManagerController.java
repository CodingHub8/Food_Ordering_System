package food_ordering_system.Controller;

import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class ManagerController {
    private final String ordersFilePath = "src/food_ordering_system/Data/orders.txt";
    private final String ratingFilePath = "src/food_ordering_system/Data/reviews.txt";
    private final String complaintFilePath= "src/food_ordering_system/Data/complaints.txt";
    private final String itemFilePath = "src/food_ordering_system/Data/menu_items.txt";

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat weekFormat = new SimpleDateFormat("w/yyyy");
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
    private final String splitByDoubleQuote = "\"([^\"]*)\"";
    private final String splitByComma = ",";
    private List<String[]> complaintData = new ArrayList<>();

    public List<Double> getVendorRevenues(String timestamp, String vendorID) {
        // Data structure to store revenues by days, months, or quarters
        Map<String, Double> revenueMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            br.readLine();// skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String orderTimestamp = parts[7].trim(); // Extract timestamp
                double orderAmount = Double.parseDouble(parts[4]); // Extract total amount

                // Parse date from timestamp
                String key = getString(timestamp, orderTimestamp);

                // Add revenue to the respective key
                if(parts[2].trim().equals(vendorID)){
                    revenueMap.put(key, revenueMap.getOrDefault(key, 0.0) + orderAmount);
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

    public List<Double> getRunnerRatings(String runnerID) {
        List<Double> ratings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ratingFilePath))) {
            String line;
            br.readLine();  // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String targetId = parts[3].trim();  // Target ID (Runner ID) from reviews.txt file
                double rating = Double.parseDouble(parts[4].trim());  // Rating from reviews.txt file

                // Only add ratings for the specific runner ID provided
                if (targetId.equals(runnerID)) {
                    ratings.add(rating);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    public List<String> getComplaints() {
        List<String> complaints = new ArrayList<>();
        Pattern pattern = Pattern.compile(splitByDoubleQuote);
        complaintData.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(complaintFilePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                List<String> parts = new ArrayList<>();
                Matcher matcher = pattern.matcher(line);
                int lastMatchEnd = 0;

                while (matcher.find()) {
                    if (matcher.start() > lastMatchEnd) {
                        String beforeQuote = line.substring(lastMatchEnd, matcher.start());
                        String[] beforeParts = beforeQuote.split(splitByComma);
                        for (String part : beforeParts) {
                            if (!part.trim().isEmpty()) {
                                parts.add(part.trim());
                            }
                        }
                    }
                    parts.add(matcher.group(1).trim());
                    lastMatchEnd = matcher.end();
                }

                if (lastMatchEnd < line.length()) {
                    String afterQuote = line.substring(lastMatchEnd);
                    String[] afterParts = afterQuote.split(splitByComma);
                    for (String part : afterParts) {
                        if (!part.trim().isEmpty()) {
                            parts.add(part.trim());
                        }
                    }
                }

                if (parts.size() >= 5) {
                    String complaintID = parts.get(0);
                    String customerID = parts.get(1);
                    String title = parts.get(2);
                    String message = parts.get(3);
                    String managerReply = parts.get(4);

                    complaintData.add(new String[]{complaintID, customerID, title, message, managerReply});

                    String formattedComplaint = customerID + ": " + title + "\n" + message +
                            "\n -> " + (managerReply.isEmpty() ? "" : managerReply) +
                            "\n\n";
                    complaints.add(formattedComplaint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return complaints;
    }

    public void updateComplaints(List<String> updatedComplaints) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(complaintFilePath))) {
            bw.write("Complaint ID, Customer ID, Title, Message, Manager Reply\n");

            for (String[] data : complaintData) {
                String complaintID = data[0];
                String customerID = data[1];
                String title = data[2];
                String message = data[3];
                String managerReply = data[4]; // Default to the existing reply

                // Check if this complaint was updated
                for (String complaint : updatedComplaints) {
                    String newReply = "";
                    complaint = complaint.trim();
                    String[] line = complaint.split("\n");

                    if (line[2].trim().startsWith("->")) {
                        newReply = line[2].substring(3).trim(); // Extract the new manager reply
                    }

                    if (line[0].contains(title) && line[1].contains(message)) {
                        managerReply = newReply; // Update reply if found in edited complaints
                        break;
                    }
                }

                // Write back the updated complaint
                bw.write(complaintID + ", " + customerID + ", \"" + title + "\", \"" + message + "\", \"" + managerReply + "\"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeVendorItem(String itemID, String vendorID){
        List<String> lines = new ArrayList<>();
        boolean itemRemoved = false;

        try (BufferedReader br = new BufferedReader(new FileReader(itemFilePath))) {
            String line;
            br.readLine();//skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].trim().equals(itemID) && parts[1].trim().equals(vendorID)) {
                    itemRemoved = true; // Mark that the item was found and removed
                    continue; // Skip this line to remove the item
                }
                lines.add(line); // Add all other lines to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (itemRemoved) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemFilePath))) {
                bw.write("Item ID, Vendor ID, Name, Price (RM), Description\n");
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Item removed successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.");
        }
    }
}
