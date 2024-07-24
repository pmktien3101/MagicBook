/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PostComment;
import utils.DatabaseUtils;

/**
 *
 * @author admin
 */
public class PostCommentDAO {

    public int insertCMT(String post_id, String user_cmt, String text_cmt) {
        int result = 0;
        String sql = "exec proc_addCMT ?,?,? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, post_id);
            st.setString(2, user_cmt);
            st.setString(3, text_cmt);
            result = st.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error Detail: " + ex.getMessage());
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Error Detail:" + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public List<PostComment> getCmtByPostID(String postID) {
        List<PostComment> list = new ArrayList<>();
        String sql = "SELECT cmt_id, post_id, user_comment, time_create, text_comment "
                + "FROM Post_Comment "
                + "WHERE post_id = ? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, postID);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int cmt_id = rs.getInt("cmt_id");
                int post_id = rs.getInt("post_id");
                String user_cmt = rs.getString("user_comment");
                Date time_create = rs.getDate("time_create");
                String text_cmt = rs.getString("text_comment");

                PostComment pc = new PostComment(cmt_id, post_id, user_cmt, time_create, text_cmt);
                list.add(pc);
            }

            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error Detail: " + ex.getMessage());
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Error Detail:" + ex.getMessage());
            ex.printStackTrace();
        }

        return list;
    }
    private static String getCmtByID = "SELECT * FROM Post_Comment WHERE cmt_id = ? AND post_id = ?";

    public PostComment getCmtById(String cmt_id, String post_id) {
        PostComment result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getCmtByID)) {

            st.setString(1, cmt_id);
            st.setString(2, post_id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int cmtId = rs.getInt("cmt_id");
                int postId = rs.getInt("post_id");
                String caption = rs.getString("user_comment");
                Date time = rs.getDate("time_create");
                String privacy = rs.getString("text_comment");
                result = new PostComment(cmtId, postId, caption, time, privacy);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    private static String getCmtByid = "SELECT * FROM Post_Comment WHERE cmt_id = ?";

    public PostComment getCmtByID(String cmt_id) {
        PostComment result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getCmtByid)) {

            st.setString(1, cmt_id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int cmtId = rs.getInt("cmt_id");
                int postId = rs.getInt("post_id");
                String caption = rs.getString("user_comment");
                Date time = rs.getDate("time_create");
                String privacy = rs.getString("text_comment");
                result = new PostComment(cmtId, postId, caption, time, privacy);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    public void deleteCmt(int post_id, int cmt_id) {
        String sql = "exec proc_deleteCMT ?, ? ";

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, post_id);
            st.setInt(2, cmt_id);

            st.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editCmt(int post_id, int cmt_id, String text_comment) {
        String sql = "UPDATE Post_Comment Set text_comment = ? WHERE post_id = ? AND cmt_id = ? ";

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, text_comment);
            st.setInt(2, post_id);
            st.setInt(3, cmt_id);

            st.executeUpdate();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostCommentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String DELETE_COMMENT_BEFORE_DELETE_POST = "DELETE FROM Post_Comment WHERE post_id = ? ";

    public void deleteAllCommentsOfPost(String id) {

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_COMMENT_BEFORE_DELETE_POST);
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

        PostDao postDao = new PostDao();
//        String email = "tientien310103@gmail.com";
        String email = "tienpmkse170552@fpt.edu.vn";
//        String id = "3";
//        String caption = "heheeeee";
//        Post p = new Post();
//        postDao.deletePost(id);
        PostCommentDAO dao = new PostCommentDAO();
        int cmt = 1;
        int post = 11;
//        System.out.println(dao.getCmtById(cmt, post));
        dao.deleteCmt(post, cmt);

    }
}