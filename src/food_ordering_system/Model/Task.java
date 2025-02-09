package food_ordering_system.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String taskID;
    private String orderID;
    private String runnerID;
    private String status;
    private String taskTime;
    private String location;

    public Task(String orderID, String runnerID, String status, String location) {
        this.taskID = generateTaskID();
        this.orderID = orderID;
        this.runnerID = runnerID;
        this.status = status;
        this.taskTime = getCurrentTime();
        this.location = location;
    }

    /*
        if runner rejects, it will fetch the next row in runners.txt.
        if no more runner, then it will assign the runner ID as "No-available runners"
    */

    private String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    private String generateTaskID() {
        return "";
    }

    public String getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }
}
