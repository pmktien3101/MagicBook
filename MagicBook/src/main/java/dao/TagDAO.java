/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DatabaseUtils;

/**
 *
 * @author admin
 */
public class TagDAO {

    public int insert(String tag_email, String tagged_email, String post_id) {
        String sql = "INSERT INTO TAG (tag_email, tagged_email, post_id) VALUES (?,?,?) ";
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, tag_email);
            st.setString(2, tagged_email);
            st.setString(3, post_id);
            result = st.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TagDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TagDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    private static String DELETE_TAG_BEFORE_DELETE_POST = "DELETE FROM TAG WHERE post_id = ? ";

    public void deleteAllTagOfPost(String id) {

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_TAG_BEFORE_DELETE_POST);
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
}
