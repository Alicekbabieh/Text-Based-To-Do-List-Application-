/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.brooklyncollege.cisc3130.project.organizedtodolistapplication;

import java.time.LocalDate;

// no longer generic class since no generic features in this class
public class Task {
 
    // changed taskName from T (generic) to String data type 
    private final String taskName;
    private final LocalDate dueDate;
    private final int priority;
    private final int estimatedTime;
    private int timeSpent;
    private boolean completed;

    public Task(String taskName, LocalDate dueDate, int priority, int estimatedTime, boolean completed, int timeSpent) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.completed = completed;
        this.timeSpent = timeSpent;
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompletionStatus(boolean completed) {
        this.completed = completed;
    }

    public void markAsCompleted() {
        completed = true;
    }

    public void addTimeSpent(int min) {
        timeSpent += min;
        if (timeSpent >= estimatedTime) {
            markAsCompleted();
        }
    }

    @Override
    public String toString() {
        return String.format("Task Name: %s, Due Date: %s, Priority: %d, Estimated Time: %d min, Time Spent: %d min, Completed Status: %s",
                taskName, dueDate, priority, estimatedTime, timeSpent, completed);
    }
}