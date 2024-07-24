/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author MSI PC
 */
public class Post {
    private int id;
    private Date time;
    private String caption;
    private String image;
    private String privacy;
    private Account email;
    private int num_Reaction;
    
    private boolean shared;
    private int originalId;
    private Account originalEmail;
    private Date originalTime;
    private String originalCaption;
    private String originalPrivacy;
    private String originalImage;
    private List<PostComment> listCmt;
    private int num_shared;
    
    public Post(int id, Date time, String caption, String image, String privacy, int num_Reaction) {
        this.id = id;
        this.time = time;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
        this.num_Reaction = num_Reaction;
    }
    public Post(int id, String caption, String image, String privacy, Account email) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
        this.email = email;
    }

    public Post(int id, String caption, String privacy, Account email) {
        this.id = id;
        this.caption = caption;
        this.privacy = privacy;
        this.email = email;
    }
    
    public Post(int id, Date time, String caption, String image, String privacy, Account email) {
        this.id = id;
        this.time = time;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
        this.email = email;
    }

     public Post(int id, Date time, String caption, String image, String privacy, Account email, int num_Reaction) {
        this.id = id;
        this.time = time;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
        this.email = email;
        this.num_Reaction = num_Reaction;
    }
     
    public Post(int id, Date time, String caption, String image, String privacy) {
        this.id = id;
        this.time = time;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
    }

    public Post(int id, Date time, String caption, String image, String privacy, Account email, boolean shared, int originalId, Account originalEmail, Date originalTime, String originalCaption, String originalPrivacy, String originalImage, int num_Reaction) {
        this.id = id;
        this.time = time;
        this.caption = caption;
        this.image = image;
        this.privacy = privacy;
        this.email = email;
        this.shared = shared;
        this.originalId = originalId;
        this.originalEmail = originalEmail;
        this.originalTime = originalTime;
        this.originalCaption = originalCaption;
        this.originalPrivacy = originalPrivacy;
        this.originalImage = originalImage;
        this.num_Reaction = num_Reaction;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Account getEmail() {
        return email;
    }

    public void setEmail(Account email) {
        this.email = email;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean original) {
        this.shared = shared;
    }

    public int getNum_Reaction() {
        return num_Reaction;
    }

    public void setNum_Reaction(int num_Reaction) {
        this.num_Reaction = num_Reaction;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public List<PostComment> getListCmt() {
        return listCmt;
    }

    public void setListCmt(List<PostComment> listCmt) {
        this.listCmt = listCmt;
    }

    public Account getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(Account originalEmail) {
        this.originalEmail = originalEmail;
    }

    public Date getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(Date originalTime) {
        this.originalTime = originalTime;
    }

    public String getOriginalCaption() {
        return originalCaption;
    }

    public void setOriginalCaption(String originalCaption) {
        this.originalCaption = originalCaption;
    }

    public String getOriginalPrivacy() {
        return originalPrivacy;
    }

    public void setOriginalPrivacy(String originalPrivacy) {
        this.originalPrivacy = originalPrivacy;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public int getNum_shared() {
        return num_shared;
    }

    public void setNum_shared(int num_shared) {
        this.num_shared = num_shared;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", time=" + time + ", caption=" + caption + ", image=" + image + ", privacy=" + privacy + ", email=" + email + ", num_Reaction=" + num_Reaction + ", originalId=" + originalId + ", shared=" + shared + ", originalEmail=" + originalEmail + ", originalTime=" + originalTime + ", originalCaption=" + originalCaption + ", originalPrivacy=" + originalPrivacy + ", originalImage=" + originalImage + ", listCmt=" + listCmt + '}';
    }

    

}
