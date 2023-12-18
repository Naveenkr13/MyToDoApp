package com.asndeveloper.mytodolist;

public class Model_for_todo {

    private int id,check_status;
    private String tasked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheck_status() {
        return check_status;
    }

    public void setCheck_status(int check_status) {
        this.check_status = check_status;
    }

    public String getTasked() {
        return tasked;
    }

    public void setTasked(String tasked) {
        this.tasked = tasked;
    }
}
