/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Account;
import model.Notification;
import model.Post;
import model.PostComment;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class NotificationDao {

    private static String INSERT_Notification = "INSERT INTO Notification (user_email, sender_email,requestFriend,accept,reportPost,content) VALUES (?, ?, ?, ?,?,?)";

    public int insertNotification(String user_email, String sender_email, String request, String accept, String reportPost, String content) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_Notification);
            st.setString(1, user_email);
            st.setString(2, sender_email);
            st.setString(3, request);
            st.setString(4, accept);
            st.setString(5, reportPost);
            st.setString(6, content);

            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    private static String INSERT_NotificationCmt = "INSERT INTO Notification (user_email, sender_email, post_id, reportComment, content) VALUES (?,?,?,?,?)";

    public int insertNotificationComment(String user_email, String sender_email, String post_id, String reportComment, String content) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_NotificationCmt);
            st.setString(1, user_email);
            st.setString(2, sender_email);
            st.setString(3, post_id);
            st.setString(4, reportComment);
            st.setString(5, content);

            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    private static String GET_N_BY_EMAIL = "SELECT * FROM Notification WHERE user_email=? Order by time DESC";

    public List<Notification> getNByEmail(String email) {
        List<Notification> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_N_BY_EMAIL)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String user_emaill = rs.getString("user_email");
                String send_emaill = rs.getString("sender_email");
                String requestFriend = rs.getString("requestFriend");
                String accept = rs.getString("accept");
                int reportPost = rs.getInt("reportPost");
                String reportComment = rs.getString("reportComment");
                Date date = rs.getDate("time");
                String caption = rs.getString("content");
                boolean isRead = rs.getBoolean("isRead");
                int msg_id = rs.getInt("msg_id");
                Post postid = new PostDao().getPostById(reportPost);
                PostComment cmtid = new PostCommentDAO().getCmtByID(reportComment);

                //cột comment trong db là lưu id của của bài được được vào
                int post_id = rs.getInt("post_id");
                Post p = new PostDao().getPostById(post_id);
                String tag = rs.getString("tag");
                String react = rs.getString("react");

                Account account = new AccountDao().getByEmail(new Account(user_emaill));
                Account accountt = new AccountDao().getByEmail(new Account(send_emaill));
                Notification notificate = new Notification(id, accountt, account, date, requestFriend, accept, postid, cmtid,caption, isRead);

                //Comment + tag
                notificate.setPost_id(p);
                notificate.setTag(tag);
                notificate.setReact(react);
                notificate.setMsg_id(msg_id);
                orders.add(notificate);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    private static String DELETE_P = "DELETE FROM Notification WHERE id=?";

    public void deleteNo(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_P);
            st.setString(1, id);
            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public int getNumberNo(String userEmail) {
        String query = "SELECT COUNT(id) FROM Notification WHERE user_email=? And isRead=0";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, userEmail);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int total = rs.getInt(1);
                int count = total;
                return count;
            }
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private static String UPDATE_R = "UPDATE Notification SET isRead= 1 WHERE id=?";

    public void updateIsRead(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_R);
            st.setString(1, id);
            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        NotificationDao postDao = new NotificationDao();
        String email = "tientien310103@gmail.com";
        String sender = "longlqse170568@fpt.edu.vn";
        String type = "test";
        String content = "kkkklkkk";
        String postid = "2";
//        postDao.insertNotification(email, sender, content, "", postid, type);
        List<Notification> list = postDao.getNByEmail(email);
        for (Notification notification : list) {
            System.out.println(notification);
        }
//        List<Notification> postss = postDao.getNByEmail(email);
//        for (Notification posts : postss) {
//            System.out.println(posts);
//        }
//        String i = "6";
//        postDao.updateIsRead(i);
//        int x = postDao.getNumberNo(email);
//        System.out.println(x);

//        postDao.updatePost(caption, "", "", id);
//        System.out.println("onee");
//        Post post = postDao.getPostById(id);
//        String p = post.getPrivacy();
//        System.out.println(p);
        // Print retrieved posts
//        List<Post> postsss = postDao.getPostByEmail(email);
//        System.out.println(postsss);
    }
    private final String GET_AVATAR = "select email, avatar from Account_Profile";

    public HashMap<String, String> getAvatarNotification() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        HashMap<String, String> listAvatar = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(GET_AVATAR);
                rs = stm.executeQuery();
                if (rs != null) {
                    listAvatar = new HashMap<>();
                }
                while (rs.next()) {
                    String email = rs.getString("email");
                    String avatar = rs.getString("avatar");
                    listAvatar.put(email, avatar);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error at Notification: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return listAvatar;
    }
}
