package edu.brooklyncollege.cisc3130.project.organizedtodolistapplication;

// import  sets and hashSet
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;


public class OrganizedToDoListApplication {
    public static void main(String[] args) {
        TaskList<Task> taskList = new TaskList<>();
        Scanner in = new Scanner(System.in);
        
        // create hashet to help store certain types of key words
        // using set - more efficient to do with dealing with 
        Set<String> specificKeyWords = new HashSet<>();
        specificKeyWords.add("add");
        // others would look something like this - switch case
        specificKeyWords.add("remove");
        specificKeyWords.add("move");
        specificKeyWords.add("sort");
        specificKeyWords.add("display");
        specificKeyWords.add("exit");

        System.out.println("You have joined a text-based ToDoList application");

        
        while (true) {
            // Display the available keywords
            String options = specificKeyWords.stream().sorted().collect(Collectors.joining(", "));
            System.out.println("\nEnter one of the following key words: " + options);

            System.out.print("Enter a key word: ");
            String keyWord = in.nextLine().trim().toLowerCase();

            // when the specificKeyWords is not the same as keyword - redos the loop
            if (!specificKeyWords.contains(keyWord)) {
                System.out.println("User has entered ann invalid key word. Try again.");
                continue;
            }
            // try catch iaddn the switch case instead of each most of each specificKeyWords having there own try catch - cleaner to use it here 
            try {
                // each case would have their own method
                switch (keyWord) {
                    case "add" -> add(in, taskList);
                    case "remove" -> remove(in, taskList);
                    case "move" -> move(in, taskList);
                    case "sort" -> sort(in, taskList);
                    case "display" -> display(in, taskList);
                    case "exit" -> exit(in, taskList);
                    
                    default -> System.out.println("Try again. User has entered an invalid keyword.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format, please enter a number.");
            } catch (DateTimeParseException e) {
                System.out.println("You have entered an invalid date. You must enter the date in this format: YYYY-MM-DD ");
            } catch (IllegalArgumentException e) {
                System.out.println("You have entered a Illegal arguement: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error has an occured: " + e.getMessage());
            }
        }
    }
    // throws exception - do noy need to specify specific one because in the try catch would deal with that 
    private static void add(Scanner in, TaskList taskList) throws Exception {
        String taskName;
        while (true) {
            System.out.print("\nEnter a task name: ");
            // added .trim - prevent possible error
            taskName = in.nextLine().trim();
                            
            // need to check if the task is empty 
            if (taskName.isEmpty()) throw new IllegalArgumentException("Task name is empty. User is unable to add anything from this ToDoList application.");    
            // need to check if task name has a duplicate - calls containsTask from TaskList Class
            if (taskList.containsTask(taskName)) {
                System.out.print("User has entered a duplicate task name. Do you still want to add this task? (yes/no): ");
                // added .toLowerCase() does not matter if uppercase or lower
                String response = in.nextLine().trim().toLowerCase();
                // if not yes or no throw exception
                if (!response.equals("yes") && !response.equals("no")) throw new IllegalArgumentException("Invalid response. Please respond with 'yes' or 'no'.");
                                
                if (response.equals("no")) continue;
                                
                break;
                } else {
                    break;
                }
            }

            System.out.print("Enter due date (YYYY-MM-DD): ");
            LocalDate dueDate = LocalDate.parse(in.nextLine().trim());
            LocalDate today = LocalDate.now();
            if (dueDate.isBefore(today)) throw new IllegalArgumentException("Due date cannot be in the past.");

            System.out.print("Enter priority level from highest to lowest (1-5): ");
            // added .trim() - prevent possible error 
            int priority = Integer.parseInt(in.nextLine().trim());
            if (priority < 1 || priority > 5) throw new IllegalArgumentException("Priority must be between 1 and 5.");

            System.out.print("Enter an estimated time spent on this task (in minutes): ");
            // .trim - prevent possible exception
            int estimatedTime = Integer.parseInt(in.nextLine().trim());
            System.out.print("Enter completion status (true/false): ");
            boolean completionStatus = in.nextBoolean();
            in.nextLine();

            int timeSpent = 0;
            if (completionStatus) {
                System.out.print("Enter how long you have spent on this task (minutes): ");
                timeSpent = Integer.parseInt(in.nextLine().trim());
            }

            Task newTask = new Task(taskName, dueDate, priority, estimatedTime, completionStatus, timeSpent);
            // now deals with specific index - adding 
            // only do this when taskList is not empty 
            if (!taskList.isEmpty()) {
                // making sure user wants to add this at specific index
                System.out.print("Do you want to add this task at a specific index? (yes/no): ");
                // .trim() - prevent possible error 
                // .toLowerCase() - uppercase or lowercase does not matter
                String indexResponce = in.nextLine().trim().toLowerCase();
                // possible exception
                if (!indexResponce.equals("yes") && !indexResponce.equals("no")) throw new IllegalArgumentException("Invalid response. Please respond with 'yes' or 'no'.");
                // when user enters yes  
                if (indexResponce.equals("yes")) {
                    System.out.print("Enter an index (0 to " + taskList.size() + "): ");
                    int index = Integer.parseInt(in.nextLine());
                    // possible exception
                    if (index < 0 || index > taskList.size()) throw new IllegalArgumentException("Index must be from 0 to " + taskList.size());
                    taskList.add(index, newTask);
                    System.out.println("Task added at index " + index + ".");
                    // when it is not add it to the end of the list 
                } else {
                     taskList.add(newTask);
                     System.out.println("You have successfully added to the ToDoList application");
                }      
            } else {
                taskList.add(newTask);
                System.out.println("You have successfully added to the ToDoList application");
            }
    }
    private static void remove(Scanner in, TaskList taskList) {
        if (taskList.isEmpty()) throw new IllegalArgumentException("Task list is empty. User is unable to remove anything from this application.");

        System.out.print("\nEnter the task name to remove: ");
        String taskRemove = in.nextLine().trim();

        if (!taskList.containsTask(taskRemove)) {
            System.out.println("Task name has not been found.");
            return;
        }

        System.out.print("Are you sure you want to remove \"" + taskRemove + "\"? (yes/no): ");
        String confirm = in.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes") && !confirm.equals("no")) throw new IllegalArgumentException("Invalid response. Please respond with 'yes' or 'no'.");

        if (confirm.equals("yes")) {
            taskList.remove(taskRemove);
            System.out.println(taskRemove + " was successfully removed from the ToDoList application.");
        } else {
            System.out.println(taskRemove + " was not removed from the ToDoList application.");
        }
    }

    private static void move(Scanner in, TaskList taskList) {
        if (taskList.isEmpty()) throw new IllegalArgumentException("Task list is empty. User is unable to move anything from this application.");

        System.out.print("\nEnter the name of the task you want to move: ");
        String moveName = in.nextLine();
        System.out.print("Enter a new index (0 to " + taskList.size() + "): ");
        int newIndex = Integer.parseInt(in.nextLine());
        taskList.moveItem(moveName, newIndex);
        System.out.println("The task was found and has been successfully moved.");
    }

    private static void sort(Scanner in, TaskList taskList) {
        if (taskList.isEmpty()) throw new IllegalArgumentException("Task list is empty. User is unable to sort anything from this application.");

        System.out.print("Sort by (priority/dueDate): ");
        String sortBy = in.nextLine();
        if (sortBy.equalsIgnoreCase("priority")) {
            taskList.sortByPriority();
            System.out.println("Tasks sorted by priority:");
        } else if (sortBy.equalsIgnoreCase("duedate")) {
            taskList.sortByDueDate();
            System.out.println("Tasks sorted by due date:");
        } else {
            System.out.println("Invalid sort option.");
            return;
        }
        taskList.display();
    }

    private static void display(Scanner in, TaskList taskList) {
        if (taskList.isEmpty()) throw new IllegalArgumentException("Task list is empty. User is unable to display anything from this application.");

        System.out.print("\nDo you want to sort the tasks? (yes/no): ");
        String displaySortResponse = in.nextLine().trim().toLowerCase();
        if (displaySortResponse.equals("no")) {
            System.out.println("Displaying tasks in the order the user has requested: \n ");
            taskList.display();
        } else if (displaySortResponse.equals("yes")) {
            System.out.print("Sort by (priority/dueDate): ");
            String sorting = in.nextLine().trim().toLowerCase();
            if (sorting.equals("priority")) {
                taskList.sortByPriority();
            } else if (sorting.equals("duedate")) {
                taskList.sortByDueDate();
            } else {
                System.out.println("You have entered an invalid sort option.");
                return;
            }
            taskList.display();
        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
        }
    }
    // method for exit - need to call it in switch case
    private static void exit(Scanner in, TaskList taskList) {
        // need to check if list is empty
        if(taskList.isEmpty()) {
            System.out.println("The user has entered an empty task list. ");
        } else {
            System.out.println("You have decided to exit this text-based ToDoList application!");
        }
        in.close();
    }
}