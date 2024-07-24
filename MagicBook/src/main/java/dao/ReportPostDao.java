/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import model.ReportPost;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Post;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class ReportPostDao {

    private static String INSERT_ReportPost = "INSERT INTO ReportPost (post_id, reason, reporter) VALUES (?, ?, ?)";

    public int insert(String post_id, String reason, String email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_ReportPost);
            st.setString(1, post_id);
            st.setString(2, reason);
            st.setString(3, email);
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

    private static String GET_ALL_ReportPost = "SELECT * FROM ReportPost Order by time DESC";

    public List<ReportPost> getReportPost() {
        List<ReportPost> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_ALL_ReportPost)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int pid = rs.getInt("post_id");
                String reason = rs.getString("reason");
                String emaill = rs.getString("reporter");
                Date date = rs.getDate("time");
                Account account = new AccountDao().getByEmail(new Account(emaill));
                ReportPost p = new ReportPost(id, pid, reason, account, date);
                orders.add(p);
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

    private static String DELETE_ReportPost = "DELETE FROM ReportPost WHERE id=?";

    public void deleteReportPost(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_ReportPost);
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
    private static String DELETE_Report = "DELETE FROM ReportPost WHERE post_id=?";
    
    public void deleteReport(String postid) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_Report);
            st.setString(1, postid);
            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    private static String DELETE_Report_BEFORE_DELETE_POST = "DELETE FROM ReportPost WHERE post_id = ? ";

    public void deleteAllReportOfPost(String id) {

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_Report_BEFORE_DELETE_POST);
            st.setString(1, id);
            st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
        ReportPostDao reportPostDao = new ReportPostDao();
        reportPostDao.deleteReport("8");
//        ReportPost d = reportPostDao.getReportPost()
//
//        // Test the getReportPost method
//        List<ReportPost> reportPosts = reportPostDao.getReportPost();
//        for (ReportPost reportPost : reportPosts) {
//            System.out.println(reportPost.toString());
//        }
//        String id = "5";
//        reportPostDao.deleteReportPost(id);

//        postDao.updatePost(caption, "", "", id);
//        System.out.println("onee");
//        Post post = postDao.getPostById(id);
//        String p = post.getPrivacy();
//        System.out.println(p);
        // Print retrieved posts
//        List<Post> postsss = postDao.getPostByEmail(email);
//        System.out.println(postsss);
    }

}