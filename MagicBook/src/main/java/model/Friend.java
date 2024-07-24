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
public class Friend {
   private int id;
   private Account user_email;
   private Account friend_email;

    public Friend() {
    }

    public Friend(Account user_email, Account friend_email) {
        this.user_email = user_email;
        this.friend_email = friend_email;
    }

    public Friend(int id, Account user_email, Account friend_email) {
        this.id = id;
        this.user_email = user_email;
        this.friend_email = friend_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getUser_email() {
        return user_email;
    }

    public void setUser_email(Account user_email) {
        this.user_email = user_email;
    }

    public Account getFriend_email() {
        return friend_email;
    }

    public void setFriend_email(Account friend_email) {
        this.friend_email = friend_email;
    }

    @Override
    public String toString() {
        return "Friend{" + "id=" + id + ", user_email=" + user_email + ", friend_email=" + friend_email + '}';
    }
   
   
   
}
