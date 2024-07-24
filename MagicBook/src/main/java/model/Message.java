/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author MSI PC
 */
public class Message {
    private int id;
    private Account sender;
    private Account receiver;
    private String content;
    private Timestamp created_at;
    private int conversationID;
    public Message() {
    }

    public Message(int id, Account sender, Account receiver, String content, Timestamp created_at) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.created_at = created_at;
    }

    public Message(int id, Account sender, Account receiver, String content, Timestamp created_at, int conversationID) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.created_at = created_at;
        this.conversationID = conversationID;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content + ", created_at=" + created_at + ", conversationID=" + conversationID + '}';
    }
    
    

   
    
    
}