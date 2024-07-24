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
import model.ReportComment;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class ReportCommentDao {

    private static String INSERT_ReportPost = "INSERT INTO ReportComment (comment_id, post_id, reason, reporter) VALUES (?, ?, ?, ?)";

    public int insert(String comment_id, String post_id, String reason, String email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_ReportPost);
            st.setString(1, comment_id);
            st.setString(2, post_id);
            st.setString(3, reason);
            st.setString(4, email);
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

    private static String GET_ALL_ReportPost = "SELECT * FROM ReportComment Order by time DESC";

    public List<ReportComment> getReportPostComment() {
        List<ReportComment> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_ALL_ReportPost)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int pid = rs.getInt("post_id");
                int cid = rs.getInt("comment_id");
                String reason = rs.getString("reason");
                String emaill = rs.getString("reporter");
                Date date = rs.getDate("time");
                Account account = new AccountDao().getByEmail(new Account(emaill));
                ReportComment p = new ReportComment(id, pid, cid, reason, account, date);
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

    private static String DELETE_ReportPost = "DELETE FROM ReportComment WHERE id=?";

    public void deleteReportComment(String id) {
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
    private static String DELETE_Report = "DELETE FROM ReportComment WHERE comment_id=? And post_id = ?";

    public void deleteReportCommentByPostID(String cmtid, String post_id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_Report);
            st.setString(1, cmtid);
            st.setString(2, post_id);

            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }
   private static String DELETE_Reportt = "DELETE FROM ReportComment WHERE comment_id=? And post_id = ?";

    public void deleteReportCommentByPostID(int cmtid, int post_id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_Reportt);
            st.setInt(1, cmtid);
            st.setInt(2, post_id);

            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }
    
    public void deleteReportCommentByPostID(String post_id) {
        String sql = "DELETE FROM ReportComment WHERE post_id = ? ";
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, post_id);
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
        ReportCommentDao reportPostDao = new ReportCommentDao();
//
//        // Test the getReportPost method
//        List<ReportComment> reportPosts = reportPostDao.getReportPostComment();
//        for (ReportComment reportPost : reportPosts) {
//            System.out.println(reportPost.toString());
//        }
////        String id = "5";
        String cid = "1";
        String pid = "11";
        String email = "phammackimtien@gmail.com";
        reportPostDao.deleteReportCommentByPostID(cid, pid);
//        reportPostDao.insert(cid, id, "dddd", email);
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