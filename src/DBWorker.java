import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
    String dbURL;
    String dbPort;
    private final String dbUsername;
    private final String dbPassword;

    DBWorker(String dbURL, String dbPort, String dbUsername, String dbPassword) {
        this.dbURL = "jdbc:mysql://" + dbURL;
        this.dbPort = dbPort;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    private void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection succesfull!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void dbConnect1() {

        try{
            /*Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection succesfull!");*/
            connectToDB();
            String sqlComm = "SELECT * FROM tasks";

            try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)){

                System.out.println("Connection to database successful");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlComm);

                while(resultSet.next()) {

                    int dbId = resultSet.getInt(1);
                    String dbTitle = resultSet.getString(2);
                    String dbDesc = resultSet.getString(3);
                    int dbDate = resultSet.getInt(4);

                    System.out.printf("ID %s; Title: %s; Description: %s; Date: %s;\n",
                            dbId, dbTitle, dbDesc, dbDate);
                }
            }
        }
        catch(Exception e){
            System.out.println("Connection failed...");

            System.out.println(e.getMessage());
        }

    }

    public ArrayList<ArrayList<String>> getDataFromDB() {
        ArrayList<ArrayList<String>> tasksList = new ArrayList<>();


        try {
            connectToDB();

            try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
                System.out.println("Connection to database successful");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

                while(resultSet.next()) {

                    ArrayList<String> taskBody = new ArrayList<>();

                    taskBody.add(resultSet.getString(1));
                    taskBody.add(resultSet.getString(2));
                    taskBody.add(resultSet.getString(3));
                    taskBody.add(resultSet.getString(4));

                    tasksList.add(taskBody);
                }

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return tasksList;
    }

    public void addTaskItem(TaskItem taskItem) {

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)){
            Statement statement = conn.createStatement();

            statement.executeUpdate("insert tasks(title, descr, date) " +
                    "values ('" + taskItem.taskTitle + "', '" + taskItem.taskDescription + "', " + taskItem.taskDate + ");");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeTaskItem(int taskId) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM tasks WHERE id =" + taskId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
