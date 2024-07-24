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
public class Conversation {
    private int id;
    private String name;
    private String avatar;

    public Conversation() {
    }

    public Conversation(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return "Conversation{" + "id=" + id + ", name=" + name + ", avatar=" + avatar + ", isGroup=" +  '}';
    }
    
    
}