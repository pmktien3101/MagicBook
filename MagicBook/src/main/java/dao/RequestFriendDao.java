/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Friend;
import model.RequestFriend;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class RequestFriendDao {

    public int getNumberPage(String userEmail) {
        String query = "SELECT COUNT(sender_email) FROM FriendRequest WHERE receiver_email=? AND isAccept = 0";
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, userEmail);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt(1);
                    // tính sl trang
                    int countPage = (total/5) +1; // làm tròn nếu lẽ
                    return countPage;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private static final String GET_Page = "SELECT \n"
        + "    AR.firstname AS sender_firstname,\n"
        + "    AR.lastname AS sender_lastname,\n"
        + "    AP.avatar AS sender_avatar,\n"
        + "    FR.sender_email AS sender_email,\n"
        + "    FR.id AS id\n"
        + "FROM \n"
        + "    FriendRequest FR\n"
        + "JOIN \n"
        + "    Account AR ON FR.sender_email = AR.email\n"
        + "LEFT JOIN \n"
        + "    Account_Profile AP ON FR.sender_email = AP.email\n"
        + "WHERE \n"
        + "    FR.receiver_email = ? AND isAccept = 0\n"
        + "ORDER BY FR.id DESC\n" 
        + "OFFSET ? ROWS "
        + "FETCH FIRST 5 ROWS ONLY";


    public List<Map<String, String>> getRequestEmailAndIndex(String email, int index) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_Page)) {
            st.setString(1, email);
            // lấy ra index là số mà ngừ dùng chọn để hiện friend
            st.setInt(2, (index - 1) * 5);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("sender_firstname");
                String lastname = rs.getString("sender_lastname");
                String avatar = rs.getString("sender_avatar");
                String sender_email = rs.getString("sender_email");
                String id = rs.getString("id");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("email", sender_email);
                friendInfo.put("id", id);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static String DELETE_R = "DELETE FROM FriendRequest WHERE id=?";

    public void deleteRequestFriend(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_R);
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
     private static String UPDATE_R = "UPDATE FriendRequest SET isAccept= 1, accept_date = GETDATE() WHERE id=?";

    public void acceptRequestFriend(String id) {
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
    
    private static String INSERT_R = "INSERT INTO FriendRequest (sender_email, receiver_email, isAccept) VALUES (?, ?, 0)";

    public int insert(String sender_email, String receiver_email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_R);
            st.setString(1, sender_email);
            st.setString(2, receiver_email);
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
    
    public static void main(String[] args) {
        RequestFriendDao friendDao = new RequestFriendDao();
        String userEmail = "tientien310103@gmail.com";
        int index = 1;
        String i = "12";
        friendDao.acceptRequestFriend(i);
        String sen= "locbpse170514@fpt.edu.vn";
        friendDao.insert(sen, userEmail);
        
//        int d;
//        d = friendDao.getNumberPage(userEmail);
//        friendDao.deleteRequestFriend(i);
//        System.out.println(d);
//        List<Map<String, String>> friendList = friendDao.getRequestEmailAndIndex(userEmail, index);
//        // Display the results
//        if (friendList.isEmpty()) {
//            System.out.println("No matching friends found.");
//        } else {
//            System.out.println("Matching friends found:");
//            for (Map<String, String> friend : friendList) {
//                System.out.println(friend);
//            }
//        }
    }

}