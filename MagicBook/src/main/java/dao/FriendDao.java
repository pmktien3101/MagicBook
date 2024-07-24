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
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class FriendDao {

    public int getNumberCommonFriend(String user, String otherUser) {
        String query = "SELECT DISTINCT COUNT(*) AS count\n"
                + "FROM (\n"
                + "    SELECT f1.friend_email\n"
                + "    FROM FRIEND f1\n"
                + "    INNER JOIN FRIEND f2 ON f1.friend_email = f2.user_email\n"
                + "    WHERE f2.friend_email = ?\n"
                + "    INTERSECT\n"
                + "    SELECT f3.friend_email\n"
                + "    FROM FRIEND f3\n"
                + "    INNER JOIN FRIEND f4 ON f3.friend_email = f4.user_email\n"
                + "    WHERE f4.friend_email = ?\n"
                + ") AS common_friends";
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user);
            st.setString(2, otherUser);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt(1);
                    int count = total;
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getNumberFriend(String user) {
        String query = "select DISTINCT COUNT(friend_email)\n"
                + "from FRIEND \n"
                + "where user_email = ?";
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt(1);
                    int count = total;
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    private static final String GET_INFO = "SELECT DISTINCT A.firstname, A.lastname, AP.avatar, A.email "
            + "FROM Account A "
            + "JOIN Friend F ON F.friend_email = A.email "
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email "
            + "WHERE A.email = ?";

    public List<Map<String, String>> getInforByEmail(String email) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_INFO)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("email");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("email", friendEmail);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static final String VIEWFRIEND = "SELECT a.lastname, a.firstname, ap.avatar, cu.user_email\n"
            + "FROM Conversations_users cu\n"
            + "JOIN Account a ON cu.user_email = a.email\n"
            + "LEFT JOIN Account_Profile ap ON a.email = ap.email\n"
            + "WHERE cu.conversations_id = ?";

    public List<Map<String, String>> viewFriendConversation(String id) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(VIEWFRIEND)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("user_email");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    private static final String GET_FRIEND_INFO = "SELECT DISTINCT A.firstname, A.lastname, AP.avatar, F.friend_email "
            + "FROM FRIEND F "
            + "JOIN Account A ON F.friend_email = A.email "
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email "
            + "WHERE F.user_email = ?";

    // dùng map cho tiện đỡ cấn Account vs AccountProfile
    public List<Map<String, String>> getFriendByEmail(String email) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_FRIEND_INFO)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("friend_email");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    // f1 là friend của bạn của mình
    private static final String GET_FRIEND_By_Propose = "SELECT DISTINCT f3.friend_email, a.firstname, a.lastname, ap.avatar\n"
            + "FROM FRIEND f1\n"
            + "JOIN FRIEND f2 ON f1.friend_email = f2.user_email\n"
            + "JOIN FRIEND f3 ON f2.friend_email = f3.user_email\n"
            + "JOIN Account a ON f3.friend_email = a.email\n"
            + "LEFT JOIN Account_Profile ap ON a.email = ap.email\n"
            + "WHERE f1.user_email IN (\n"
            + "    SELECT user_email\n"
            + "    FROM FRIEND\n"
            + "    WHERE friend_email = ? \n"
            + ") \n"
            + "AND f3.friend_email <> ? ";

    public List<Map<String, String>> getFriendByPropose(String email) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_FRIEND_By_Propose)) {
            st.setString(1, email);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("friend_email");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    private static final String SEARCH_FRIEND_INFO = "SELECT A.firstname, A.lastname, AP.avatar, F.friend_email "
            + "FROM FRIEND F "
            + "JOIN Account A ON F.friend_email = A.email "
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email "
            + "WHERE F.user_email = ? AND (A.firstname LIKE ? OR A.lastname LIKE ?)";

    public List<Map<String, String>> searchFriendByKeyword(String email, String keyword) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(SEARCH_FRIEND_INFO)) {
            st.setString(1, email);
            String likePattern = "%" + keyword + "%";
            st.setString(2, likePattern);
            st.setString(3, likePattern);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("friend_email");
                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);
                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static final String SEARCH_INFO = "SELECT A.email, A.firstname, A.lastname, AP.avatar\n"
            + "FROM Account A\n"
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email\n"
            + "WHERE A.role = 0 AND (A.firstname LIKE ? OR A.lastname LIKE ?) AND A.email <> ? ";

    public List<Map<String, String>> searchByKeyword(String keyword, String emailCurr) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(SEARCH_INFO)) {
            String likePattern = "%" + keyword + "%";
            st.setString(1, likePattern);
            st.setString(2, likePattern);
            st.setString(3, emailCurr);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("email", email);
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int getNumberPage(String userEmail) {
        String query = "SELECT COUNT(friend_email) FROM FRIEND WHERE user_email=?";
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

    private static final String GET_FRIEND_Page = "SELECT A.firstname, A.lastname, AP.avatar, F.friend_email "
            + "FROM FRIEND F "
            + "JOIN Account A ON F.friend_email = A.email "
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email "
            + "WHERE F.user_email = ? "
            + "ORDER BY F.id DESC "
            + "OFFSET ? ROWS "
            + "FETCH FIRST 5 ROWS ONLY";

    public List<Map<String, String>> getFriendByEmailAndIndex(String email, int index) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_FRIEND_Page)) {
            st.setString(1, email);
            // lấy ra index là số mà ngừ dùng chọn để hiện friend
            st.setInt(2, (index - 1) * 5);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("friend_email");

                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);

                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    private static final String FRIEND_INFO = "SELECT A.firstname, A.lastname, AP.avatar, F.friend_email "
            + "FROM FRIEND F "
            + "JOIN Account A ON F.friend_email = A.email "
            + "LEFT JOIN Account_Profile AP ON A.email = AP.email "
            + "WHERE F.user_email = ? ";

    public List<Map<String, String>> GetAllFriend(String email) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(FRIEND_INFO)) {
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String avatar = rs.getString("avatar");
                String friendEmail = rs.getString("friend_email");
                Map<String, String> friendInfo = new HashMap<>();
                friendInfo.put("firstname", firstname);
                friendInfo.put("lastname", lastname);
                friendInfo.put("avatar", avatar);
                friendInfo.put("friend_email", friendEmail);
                result.add(friendInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    private static String DELETE_P = "DELETE FROM FRIEND WHERE friend_email= ? and user_email=?";

    public void unFriend(String friend_email, String user_email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_P);
            st.setString(1, friend_email);
            st.setString(2, user_email);
            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }
    private static final String GET_FRIEND = "SELECT COUNT(*) AS count FROM Friend WHERE friend_email = ? AND user_email = ?";

    public boolean isFriend(String friendEmail, String userEmail) {
        boolean isFriend = false;

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_FRIEND)) {

            st.setString(1, friendEmail);
            st.setString(2, userEmail);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    isFriend = count > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return isFriend;
    }
    private static final String GET_ACCPET = "SELECT COUNT(*) AS count FROM FriendRequest WHERE sender_email = ? AND receiver_email = ? AND isAccept=0";

    public boolean isAccept(String friendEmail, String userEmail) {
        boolean isFriend = false;

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_ACCPET)) {

            st.setString(1, friendEmail);
            st.setString(2, userEmail);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    isFriend = count > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return isFriend;
    }

    public static void main(String[] args) {
        FriendDao friendDao = new FriendDao();

        // Gọi phương thức getFriendByEmail để lấy thông tin về bạn bè của một email nhất định
        String email = "zoen3145@gmail.com"; // Thay thế bằng email bạn muốn tìm kiếm
        List<Map<String, String>> friendList = friendDao.getFriendByPropose(email);

        System.out.println("ạkdhsajkhdkjas");
        // In ra thông tin về bạn bè
        for (Map<String, String> friendInfo : friendList) {
            System.out.println(friendInfo);
        }
//        String other = "locbpse170514@fpt.edu.vn";
//        int x = friendDao.getNumberFriend(email);
//        System.out.println(x);
    }

}
