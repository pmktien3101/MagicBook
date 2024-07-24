/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class AccountProfile {

    private String email;
    private String avatar;
    private String background;
    private String about_You;

    public AccountProfile() {
    }

    public AccountProfile(String email, String avatar, String background, String about_You) {
        this.email = email;
        this.avatar = avatar;
        this.background = background;
        this.about_You = about_You;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAbout_You() {
        return about_You;
    }

    public void setAbout_You(String about_You) {
        this.about_You = about_You;
    }

    @Override
    public String toString() {
        return "AccountProfile{" + "email=" + email + ", avatar=" + avatar + ", background=" + background + ", about_You=" + about_You + '}';
    }

}