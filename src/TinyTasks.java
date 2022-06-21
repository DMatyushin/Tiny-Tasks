import java.util.ArrayList;
import java.util.Scanner;

public class TinyTasks {

    static ArrayList<String> tasks = new ArrayList<>();
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


        tasks.add("Go to hospital");
        tasks.add("Call to Thomas");




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
                String newTask = inputController();
                tasks.add(newTask);
                System.out.printf("\nTask < %s > added to list.", newTask);
                break;
            case ("MOD"):
                System.out.println("Enter ID for mod. task:");
                int taskId = Integer.parseInt(inputController()) - 1;
                if (taskId >= tasks.size()) {
                    System.out.printf("Error: task < ID %s > not found.", taskId + 1);
                }
                else {
                    System.out.printf("Current title task ID %s is < %s >. Type new title:\n", taskId + 1, tasks.get(taskId));
                    tasks.set(taskId, inputController());
                    System.out.printf("\nTask < ID %s > changed. New title: %s.\n", taskId + 1, tasks.get(taskId));
                }

                break;
            case ("LIST"):
                listTasks();
                break;
            case ("DEL"):
                System.out.println("Enter ID for rem. task:");
                taskId = Integer.parseInt(inputController()) - 1;
                if (taskId >= tasks.size()) {
                    System.out.printf("Error: task < ID %s > don't exist.\n", taskId);

                }
                else {
                    tasks.remove(taskId);
                    System.out.printf("\nTask < ID %s > was delete.", taskId + 1);

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
        if (tasks.size() == 0) {
            System.out.println("Task list is empty. Try add in list some tasks.");
        }
        else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("id %s\t%s\n", i + 1, tasks.get(i));
            }
        }
    }

}