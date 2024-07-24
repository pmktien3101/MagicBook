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
public class Tag {

    private int id;
    private Account tag_email;
    private Account tagged_email;
    private Post post_id;

    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getTag_email() {
        return tag_email;
    }

    public void setTag_email(Account tag_email) {
        this.tag_email = tag_email;
    }

    public Account getTagged_email() {
        return tagged_email;
    }

    public void setTagged_email(Account tagged_email) {
        this.tagged_email = tagged_email;
    }

    public Post getPost_id() {
        return post_id;
    }

    public void setPost_id(Post post_id) {
        this.post_id = post_id;
    }

}
