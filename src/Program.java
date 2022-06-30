import java.util.ArrayList;
import java.util.Scanner;


public class Program {

    static DBWorker dbWorker = new DBWorker("192.168.0.193:3306/tinyTasks", "3306", "root", "bdujlygw");
    static TaskItem taskItem = new TaskItem();
    static ArrayList<ArrayList<String>> tasksList;
    static Boolean workProgram = true;
    static String tasksHelp = """
                Tiny Java Task manager.
                LIST - List tasks;
                ADD - Add new task;
                MOD - Modify task;
                DEL - Delete task;
                ___________
                EXIT - close program;
                HELP - output this message again;""";



    public static void main(String[] args) {


        dbWorker.connectToDB();

        tasksList = dbWorker.getDataFromDB();


        //dbWorker.addTaskItem(taskItem);
        //dbWorker.removeTaskItem(7);
        //dbWorker.modifyTaskItem(9, "T", "Go to market");
        //dbWorker.modifyTaskItem(9, "D", "Buy: meat, bread, salt");

        System.out.println(tasksHelp);
        System.out.println("\nList of tasks:");
        listTasks();

        programStart();

    }

    static void programStart() {

        Scanner in = new Scanner(System.in);
        System.out.print("\nWhat you need to do? >> ");
        String inputKey = in.nextLine();



        switch (inputKey) {
            case ("ADD"):

                System.out.println("Enter title for new task:");
                taskItem.taskTitle = inputController();
                System.out.println("Enter description for new task:");
                taskItem.taskDescription = inputController();
                dbWorker.addTaskItem(taskItem);
                System.out.printf("\nTask < %s > added to list.", taskItem.taskTitle);
                break;
            case ("MOD"):

                System.out.println("Enter ID for mod. task:");
                int taskId = Integer.parseInt(inputController());
                if (dbWorker.checkDBEntry(taskId)) {
                    System.out.println("Type T for edit task title; Type D for edit task description;");
                    String fieldType = inputController();

                    System.out.println("Type field update:");
                    String fieldUpdate = inputController();

                    dbWorker.modifyTaskItem(taskId, fieldType, fieldUpdate);
                }
                else {
                    System.out.printf("Task ID %s not found.\n", taskId);
                }


                break;

            case ("LIST"):

                listTasks();
                break;

            case ("DEL"):

                System.out.println("Enter ID for rem. task:");
                taskId = Integer.parseInt(inputController());

                if (dbWorker.checkDBEntry(taskId)) {
                    dbWorker.removeTaskItem(taskId);
                }
                else {
                    System.out.printf("Task ID %s not found.", taskId);
                }

                break;

            case ("HELP"):

                System.out.println(tasksHelp);
                break;

            case ("EXIT"):

                System.out.println("Program shutdown. Bye");
                workProgram = false;
                break;

            default:

                System.out.println("Please, input correct key");
                break;

        }
        //in.close();
        if (workProgram) {
            programStart();
        }





    }

    static String inputController() {
        Scanner in = new Scanner(System.in);
        System.out.print(">>>> ");
        //in.close();

        return in.nextLine();
    }

    static void listTasks() {
        tasksList = dbWorker.getDataFromDB();
        for (ArrayList obj:tasksList) {

            System.out.printf("ID %s | %s | %s | %s\n", ((ArrayList<String>) obj).get(0), ((ArrayList<String>) obj).get(1), ((ArrayList<String>) obj).get(2), ((ArrayList<String>) obj).get(3));
        }
    }

}

