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
public class RequestFriend {
    private int id;
    private Account sender_email;
    private Account receive_email;
    private Date send_date;
    private Date accept_date;
    private boolean isAccept;

    public RequestFriend() {
    }

    public RequestFriend(int id, Account sender_email, Account receive_email, Date send_date, Date accept_date, boolean isAccept) {
        this.id = id;
        this.sender_email = sender_email;
        this.receive_email = receive_email;
        this.send_date = send_date;
        this.accept_date = accept_date;
        this.isAccept = isAccept;
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

    public Date getSend_date() {
        return send_date;
    }

    public void setSend_date(Date send_date) {
        this.send_date = send_date;
    }

    public Date getAccept_date() {
        return accept_date;
    }

    public void setAccept_date(Date accept_date) {
        this.accept_date = accept_date;
    }

    public boolean isIsAccept() {
        return isAccept;
    }

    public void setIsAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }

    @Override
    public String toString() {
        return "RequestFriend{" + "id=" + id + ", sender_email=" + sender_email + ", receive_email=" + receive_email + ", send_date=" + send_date + ", accept_date=" + accept_date + ", isAccept=" + isAccept + '}';
    }
    
}