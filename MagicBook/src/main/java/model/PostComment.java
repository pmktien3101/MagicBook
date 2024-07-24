/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class PostComment {
    private int cmt_id;
    private int post_id;
    private String user_cmt;
    private Date time_create;
    private String text_cmt;

    public PostComment() {
    }
    
    public PostComment(int cmt_id, int post_id, String user_cmt, Date time_create, String text_cmt) {
        this.cmt_id = cmt_id;
        this.post_id = post_id;
        this.user_cmt = user_cmt;
        this.time_create = time_create;
        this.text_cmt = text_cmt;
    }

    public int getCmt_id() {
        return cmt_id;
    }

    public void setCmt_id(int cmt_id) {
        this.cmt_id = cmt_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getUser_cmt() {
        return user_cmt;
    }

    public void setUser_cmt(String user_cmt) {
        this.user_cmt = user_cmt;
    }

    public Date getTime_create() {
        return time_create;
    }

    public void setTime_create(Date time_create) {
        this.time_create = time_create;
    }

    public String getText_cmt() {
        return text_cmt;
    }

    public void setText_cmt(String text_cmt) {
        this.text_cmt = text_cmt;
    }
    
    @Override
    public String toString() {
        return "PostComment{" + "cmt_id=" + cmt_id + ", post_id=" + post_id + ", user_cmt=" + user_cmt + ", time_create=" + time_create + ", text_cmt=" + text_cmt + '}';
    }
    
}