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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Conversation;
import model.Conversation_user;
import model.Post;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class ConversationDao {

    public int getNumberFriend(String id) {
        String query = "select DISTINCT COUNT(user_email)\n"
                + "from Conversations c\n"
                + "JOIN Conversations_users cu ON c.id = cu.conversations_id\n"
                + "where c.id = ?";
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, id);

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

    private static String Insert_Conversation = "INSERT INTO Conversations_users (conversations_id, user_email, isAdmin) VALUES (?, ?, 0)";

    public int insertFriendToConversation(String id, String user_email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(Insert_Conversation);
            st.setString(1, id);
            st.setString(2, user_email);
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

    private static String Insert_ConversationFirst = "INSERT INTO Conversations (name, avatar) VALUES (?, ?)";

    public int createConversation(String name, String avatar, String creatorEmail, String friendEmail) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(Insert_ConversationFirst, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, avatar);
            result = st.executeUpdate();

            // Lấy ID của cuộc trò chuyện vừa tạo
            ResultSet rs = st.getGeneratedKeys();
            int conversationId = 0;
            if (rs.next()) {
                conversationId = rs.getInt(1);
            }
            // Thêm người tạo ra cuộc trò chuyện làm quản trị viên
            addMemberToConversation(con, conversationId, creatorEmail, true);
            addMember(con, conversationId, friendEmail, false);
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private void addMemberToConversation(Connection con, int conversationId, String userEmail, boolean isAdmin) throws SQLException {
        String Insert_Conversation_User = "INSERT INTO Conversations_users (conversations_id, user_email, isAdmin) VALUES (?, ?, ?)";
        PreparedStatement st = con.prepareStatement(Insert_Conversation_User);
        st.setInt(1, conversationId);
        st.setString(2, userEmail);
        st.setBoolean(3, isAdmin);
        st.executeUpdate();
    }

    private void addMember(Connection con, int conversationId, String userEmail, boolean isAdmin) throws SQLException {
        String Insert_Conversation_User = "INSERT INTO Conversations_users (conversations_id, user_email, isAdmin) VALUES (?, ?, ?)";
        PreparedStatement st = con.prepareStatement(Insert_Conversation_User);
        st.setInt(1, conversationId);
        st.setString(2, userEmail);
        st.setBoolean(3, isAdmin);
        st.executeUpdate();
    }
    private static String GET_Conversation_BY_EMAIL = "SELECT conv.id AS conversation_id, conv.name AS conversation_name, conv.avatar AS conversation_avatar\n"
            + "FROM Conversations conv\n"
            + "JOIN Conversations_users conv_users ON conv.id = conv_users.conversations_id\n"
            + "WHERE conv_users.user_email = ?";

    public List<Conversation> getConversationByEmail(String email) {
        List<Conversation> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_Conversation_BY_EMAIL)) {

            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("conversation_id");
                String name = rs.getString("conversation_name");
                String avatar = rs.getString("conversation_avatar");
                Conversation p = new Conversation(id, name, avatar);
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

    private static String getPostByID = "SELECT * FROM Conversations WHERE id = ?";

    public Conversation getConversationById(String id) {
        Conversation result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getPostByID)) {

            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int idd = rs.getInt("id");
                String name = rs.getString("name");
                String avatar = rs.getString("avatar");
                result = new Conversation(idd, name, avatar);
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

    private static String UPDATE_P = "UPDATE Conversations SET avatar=? WHERE id=?";

    public void updateAvatarConversation(String avatar, String id) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_P);
            st.setString(1, avatar);
            st.setString(2, id);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String UPDATE_N = "UPDATE Conversations SET name=? WHERE id=?";

    public void updateNameConversation(String name, String id) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_N);
            st.setString(1, name);
            st.setString(2, id);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static final String DELETE_CONVERSATION_USERS = "DELETE FROM Conversations_users WHERE conversations_id = ?";
    private static final String DELETE_CONVERSATION = "DELETE FROM Conversations WHERE id = ?";
    private static final String DELETE_MESS = "DELETE FROM Messages WHERE conversationID = ?";

    public void deleteConversation(String id) {
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement deleteConUsersStmt = con.prepareStatement(DELETE_CONVERSATION_USERS);
                PreparedStatement deleteConMessStmt = con.prepareStatement(DELETE_MESS);
                PreparedStatement deleteConStmt = con.prepareStatement(DELETE_CONVERSATION)) {

            // First, delete related records from Conversations_users
            deleteConUsersStmt.setString(1, id);
            int deletedConUsersRows = deleteConUsersStmt.executeUpdate();

            deleteConMessStmt.setString(1, id);
            int deletedConMessRows = deleteConMessStmt.executeUpdate();

            // Then, delete the conversation itself
            deleteConStmt.setString(1, id);
            int deletedConRows = deleteConStmt.executeUpdate();
            System.out.println("Deleted the conversation.");

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static final String leaveOutGroup = "DELETE FROM Conversations_users WHERE conversations_id = ? AND user_email =?";

    public void leaveConversation(String id, String user_email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(leaveOutGroup);
            st.setString(1, id);
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

    private static String getIsAdmin = "SELECT * FROM Conversations_users WHERE user_email = ? AND conversations_id = ?";

    public Conversation_user getConversationUserById(String user, String id) {
        Conversation_user result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getIsAdmin)) {

            st.setString(1, user);
            st.setString(2, id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Conversation_user c = new Conversation_user();
                Boolean isAdmin = rs.getBoolean("isAdmin");
                c.setIsAdmin(isAdmin);
                result = c;
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

    public static void main(String[] args) {

        ConversationDao postDao = new ConversationDao();
        String email = "tienpmkse170552@fpt.edu.vn";
        String em = "phammackimtien@gmail.com";

        String id = "1";
        String caption = "testname";
//        postDao.createConversation(id, "", email, em);
postDao.updateAvatarConversation(caption, id);
//        System.out.println(postDao.getConversationUserById(email, id));
//        if(cu.isIsAdmin()){
//            System.out.println("hhhh");
//        }else{
//                        System.out.println("lllll");
//        
//
//        }
    }

}