package edu.brooklyncollege.cisc3130.project.organizedtodolistapplication;
// Double Linked List  

// imported HashSet
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;

public class TaskList <T extends Task> extends LinkedList<T> implements Iterable<T>  {
    
    // created Hashset of Strings
    private final HashSet<String> taskNames = new HashSet<>();
    
    // kept @Override since I moditfied add - dealing with HashSet class - contains methods 
    @Override
    public boolean add(T elt) {
        // need to check if there any duplicates with hashSet and getTaskName
        if(taskNames.contains(elt.getTaskName())) {
            return false;
        }
        // add it end of the list 
        taskNames.add(elt.getTaskName());
        return super.add(elt);
    }
    // added add - specific index 
    // kept @Override since I moditfied it  - dealing with HashSet class - contains methods 
    @Override
    public void add(int index, T elt) {
        // need to check if there any duplicates with hashSet and getTaskName
        if(taskNames.contains(elt.getTaskName())) {
            // if it is than add to specific index
            taskNames.add(elt.getTaskName());
            super.add(index, elt);
        }   
    }
    
    // removes the last element 
    @Override
    public T remove() {
        T removed = super.remove();
        if (removed != null) {
            taskNames.remove(removed.getTaskName());
        }
        return removed;
    }
    
    // remove at a specific index
    @Override
    public T remove(int index) {
        T removed = super.remove(index);
        if (removed != null) {
            taskNames.remove(removed.getTaskName());
        }
        return removed;
    }
    
    public T get(int index) {
        return super.get(index);
    }

    public int size() {
        return super.size();
    }
    
    // method that deals with duplicates - containsTask - instead using iterators
    // runtime is faster O(1) - constant
    public boolean containsTask(String taskName) {
        // call the contains method from HashSet class
        return taskNames.contains(taskName);
    }
    
    // display
    public void display() {
        if (isEmpty()) {
            System.out.println("The user has not entered any tasks.");
            return;
        }
        // use iterator better than for loop
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // if the user want to be able to move an item (based on task name) to a different index 
    public void moveItem(String taskName, int newIndex) {
        for (int i = 0; i < size(); i++) {
            T task = get(i);
            if (task.getTaskName().equals(taskName)) {
                remove(i);
                add(newIndex, task);
                return;
            }
        }
        System.out.println("Task was not found.");
    }
    
    public void sortByPriority() {
        if (size() < 2) return;
        mergeSortByPriority((LinkedList<Task>) this, 0, size());
    }

    // Sort tasks by due date
    public void sortByDueDate() {
        if (size() < 2) return;
        mergeSortByDueDate((LinkedList<Task>) this, 0, size());
    }

    // Sorting a linked list tasks based on priority
    public void mergeSortByPriority(LinkedList<Task> list, int begin, int end) {
        // Base case
        if (end - begin <= 1) return;
        // Getting the middle value
        int middle = begin + (end - begin) / 2;

        // Recursion to sort left and right side
        mergeSortByPriority(list, begin, middle);
        mergeSortByPriority(list, middle, end);

        // Creating scratch space
        LinkedList<Task> scratch = new LinkedList<>();

        // Need to loop to copy space into scratch space
        for (int i = begin; i < middle; i++) {
            scratch.add(list.get(i));
        }
        // 3 pointers
        int nextFirst = 0, nextSecond = middle, nextWrite = begin;

        // Loop until one of them ends
        while (nextFirst < scratch.size() && nextSecond < end) {
            Task leftTask = scratch.get(nextFirst);
            Task rightTask = list.get(nextSecond);

            // Condition for sorting by priority
            if (leftTask.getPriority() <= rightTask.getPriority()) {
                list.set(nextWrite++, scratch.get(nextFirst++));
            } else {
                list.set(nextWrite++, list.get(nextSecond++));
            }
        }

        while (nextFirst < scratch.size()) {
            list.set(nextWrite++, scratch.get(nextFirst++));
        }
    }
    // Sorting a linked list tasks based on due date
    public void mergeSortByDueDate(LinkedList<Task> list, int begin, int end) {
        // Base case
        if (end - begin <= 1) return;

        // Getting the middle value
        int middle = begin + (end - begin) / 2;

        // Recursion to sort left and right side
        mergeSortByDueDate(list, begin, middle);
        mergeSortByDueDate(list, middle, end);

        // Merging both of these lists to one list
        // Creating scratch space
        LinkedList<Task> scratch = new LinkedList<>();

        // Copying the left half into scratch space
        for (int i = begin; i < middle; i++) {
            scratch.add(list.get(i));
        }
        // 3 pointers
        int nextFirst = 0, nextSecond = middle, nextWrite = begin;

        // Loop until one of them ends
        while (nextFirst < scratch.size() && nextSecond < end) {
            Task leftTask = scratch.get(nextFirst);
            Task rightTask = list.get(nextSecond);
            // this is reason why need to sort due date
            if (leftTask.getDueDate().compareTo(rightTask.getDueDate()) <= 0) {
                list.set(nextWrite++, scratch.get(nextFirst++));
            } else {
                list.set(nextWrite++, list.get(nextSecond++));
            }
        }

        // Copy remaining elements from scratch
        while (nextFirst < scratch.size()) {
            list.set(nextWrite++, scratch.get(nextFirst++));
        }
    }
}