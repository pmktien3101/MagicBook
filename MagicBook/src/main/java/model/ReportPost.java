/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author MSI PC
 */
public class ReportPost {
    private int id;
    private int post_id;
    private String reason;
    private Account reporter;
    private Date time;

    public ReportPost() {
    }

    public ReportPost(int id, int post_id, String reason, Account reporter, Date time) {
        this.id = id;
        this.post_id = post_id;
        this.reason = reason;
        this.reporter = reporter;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Account getReporter() {
        return reporter;
    }

    public void setReporter(Account reporter) {
        this.reporter = reporter;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ReportPost{" + "id=" + id + ", post_id=" + post_id + ", reason=" + reason + ", reporter=" + reporter + ", time=" + time + '}';
    }
    
}