/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.AccountProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import utils.DatabaseUtils;

/**
 *
 * @author admin
 */
public class AccountProfileDAO {

    public AccountProfile getAccountProfile(String email) {
        AccountProfile dto = new AccountProfile();

        String sql = "SELECT avatar, background, about_You FROM Account_Profile where email = ? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String avatar = rs.getString("avatar");
                    String background = rs.getString("background");
                    String about_You = rs.getString("about_You");

                    dto = new AccountProfile(email, avatar, background, about_You);
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dto;
    }

    public void updateInformation(String email, String firstname, String lastname, String dob, String phone, String gender) {
        String sql = "UPDATE Account SET firstname = ?, lastname = ?, dob = ?, phone = ?, gender = ? WHERE email = ? ";

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
//            java.sql.Date sqlDate = java.sql.Date.valueOf(dob);
//            stmt.setDate(3, sqlDate);
            stmt.setString(3, dob);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, email);

            stmt.executeUpdate();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBackground(String email, String background) {
        String sql = "UPDATE Account_Profile SET background = ? WHERE email = ? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, background);
            stmt.setString(2, email);
            stmt.executeUpdate();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateAvatar(String email, String avatar) {
        String sql = "UPDATE Account_Profile SET avatar = ? WHERE email = ? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, avatar);
            stmt.setString(2, email);

            stmt.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateAboutMe(String email, String aboutMe) {
        String sql = "UPDATE Account_Profile SET about_You = ? WHERE email = ? ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, aboutMe);
            stmt.setString(2, email);

            stmt.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void registerUser(String email) {
        String sql = "INSERT INTO Account_Profile (email, avatar, background) VALUES (?, './assets/defaultAvatar.jpg', './assets/defaultBackground.jpg') ";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, email);

            stmt.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccountProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<AccountProfile> getListAccountProfile() {
        String sql = "SELECT email, avatar, background, about_You FROM Account_Profile";
        List<AccountProfile> list = new ArrayList<>();

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String avatar = rs.getString("avatar");
                String background = rs.getString("background");
                String about_Your = rs.getString("about_You");

                AccountProfile ap = new AccountProfile(email, avatar, background, about_Your);
                list.add(ap);
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

    public static void main(String[] args) {

        PostDao postDao = new PostDao();
        String email = "tientien310103@gmail.com";
        String id = "4";
        String caption = "heheeeee";
        AccountProfileDAO a = new AccountProfileDAO();
        AccountProfile s = a.getAccountProfile(email);
        System.out.println(s);
    }

}