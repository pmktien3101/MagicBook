/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author MSI PC
 */
public class Conversation_user {
    private int conversationID;
    private Account user_email;
    private boolean isAdmin;
    private String nickname;
    
    public Conversation_user() {
    }

    public Conversation_user(int conversationID, Account user_email, boolean isAdmin, String nickname) {
        this.conversationID = conversationID;
        this.user_email = user_email;
        this.isAdmin = isAdmin;
        this.nickname = nickname;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public Account getUser_email() {
        return user_email;
    }

    public void setUser_email(Account user_email) {
        this.user_email = user_email;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Conversation_user{" + "conversationID=" + conversationID + ", user_email=" + user_email + ", isAdmin=" + isAdmin + ", nickname=" + nickname + '}';
    }
    
}