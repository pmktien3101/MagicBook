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
public class Notification {

    private int id;
    private Account sender_email;
    private Account receive_email;
    private Date time;
    private String requestFriend;
    private String acceptRequest;
    private Post reportPost;
    private PostComment reportComment;
    private String type;
    private String content;
    private boolean isRead;
    private Post post_id;
    private String tag;
    private String react;
    private int msg_id;

    public Notification() { 
    }


    public Notification(int id, Account sender_email, Account receive_email, Date time, String requestFriend, String acceptRequest, Post reportPost, PostComment reportComment, String content, boolean isRead) {
        this.id = id;
        this.sender_email = sender_email;
        this.receive_email = receive_email;
        this.time = time;
        this.requestFriend = requestFriend;
        this.acceptRequest = acceptRequest;
        this.reportPost = reportPost;
        this.reportComment = reportComment;
        this.content = content;
        this.isRead = isRead;
        this.post_id = post_id;
    }

    public Notification(int id, Account sender_email, Account receive_email, Date time, String requestFriend, String acceptRequest, Post reportPost, String type, String content, boolean isRead) {
        this.id = id;
        this.sender_email = sender_email;
        this.receive_email = receive_email;
        this.time = time;
        this.requestFriend = requestFriend;
        this.acceptRequest = acceptRequest;
        this.reportPost = reportPost;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
    }

    public Notification(int id, Account sender_email, Account receive_email, Date time, String requestFriend, String acceptRequest, Post reportPost, String type, String content, boolean isRead, String react, Post post_id) {
        this.id = id;
        this.sender_email = sender_email;
        this.receive_email = receive_email;
        this.time = time;
        this.requestFriend = requestFriend;
        this.acceptRequest = acceptRequest;
        this.reportPost = reportPost;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
        this.react = react;
        this.post_id = post_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSender_email() {
        return sender_email;
    }

    public void setSender_email(Account sender_email) {
        this.sender_email = sender_email;
    }

    public Account getReceive_email() {
        return receive_email;
    }

    public void setReceive_email(Account receive_email) {
        this.receive_email = receive_email;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getRequestFriend() {
        return requestFriend;
    }

    public void setRequestFriend(String requestFriend) {
        this.requestFriend = requestFriend;
    }

    public String getAcceptRequest() {
        return acceptRequest;
    }

    public void setAcceptRequest(String acceptRequest) {
        this.acceptRequest = acceptRequest;
    }

    public Post getReportPost() {
        return reportPost;
    }

    public void setReportPost(Post reportPost) {
        this.reportPost = reportPost;
    }

    public Post getPost_id() {
        return post_id;
    }

    public void setPost_id(Post post_id) {
        this.post_id = post_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReact() {
        return react;
    }

    public void setReact(String react) {
        this.react = react;
    }

    public PostComment getReportComment() {
        return reportComment;
    }

    public void setReportComment(PostComment reportComment) {
        this.reportComment = reportComment;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", sender_email=" + sender_email + ", receive_email=" + receive_email + ", time=" + time + ", requestFriend=" + requestFriend + ", acceptRequest=" + acceptRequest + ", reportPost=" + reportPost + ", reportComment=" + reportComment + ", type=" + type + ", content=" + content + ", isRead=" + isRead + ", post_id=" + post_id + ", tag=" + tag + ", react=" + react + '}';
    }

   
}