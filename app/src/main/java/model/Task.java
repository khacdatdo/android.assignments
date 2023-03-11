package model;

public class Task {
    private String name;
    private String description;
    private String gender;
    private String dueDate;

    public Task() {
    }

    public Task(String name, String description, String gender, String dueDate) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}