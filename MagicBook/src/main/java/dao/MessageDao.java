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
import java.util.List;
import model.Account;
import model.Message;
import model.Post;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class MessageDao {

    private static String GET_M_BY_EMAIL = "SELECT *\n"
            + "FROM Messages\n"
            + "WHERE (sender = ? AND receiver = ?)\n"
            + "   OR (receiver = ? AND sender = ?)";

    public List<Message> getMessageByEmail(String user, String otherUser) {
        List<Message> messages = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_M_BY_EMAIL)) {
            st.setString(1, user);
            st.setString(2, otherUser);
            st.setString(3, user);
            st.setString(4, otherUser);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("created_at");

                Account senderAccount = new AccountDao().getByEmail(new Account(sender));
                Account receiverAccount = new AccountDao().getByEmail(new Account(receiver));
                Message message = new Message(id, senderAccount, receiverAccount, content, date);
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return messages;
    }
    
    private static String GET_M_BY_Conversation = "Select * \n"
            + "from Messages m\n"
            + "where m.conversations_id = ?";

    public List<Message> getMessageByConversation(String id) {
        List<Message> messages = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_M_BY_Conversation)) {
            st.setString(1, id);


            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int idd = rs.getInt("id");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("created_at");
                int conid = rs.getInt("conversations_id");
                Account senderAccount = new AccountDao().getByEmail(new Account(sender));
                Account receiverAccount = new AccountDao().getByEmail(new Account(receiver));
                Message message = new Message(idd, senderAccount, receiverAccount, content, date, conid);
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return messages;
    }
    
    private static String getPostByID = "SELECT TOP 1 *\n"
            + "FROM Messages\n"
            + "WHERE (sender = ? AND receiver = ?) OR (receiver = ? AND sender = ?)\n"
            + "ORDER BY created_at DESC";

    public Message getMessLast(String user, String otherUser) {
        Message result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getPostByID)) {

            st.setString(1, user);
            st.setString(2, otherUser);
            st.setString(3, user);
            st.setString(4, otherUser);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("created_at");
                Account senderAccount = new AccountDao().getByEmail(new Account(sender));
                Account receiverAccount = new AccountDao().getByEmail(new Account(receiver));
                result = new Message(id, senderAccount, receiverAccount, content, date);
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
    private static String getMLastConversation = "SELECT TOP 1 *\n"
            + "FROM Messages m\n"
            + "WHERE m.conversations_id = ? \n"
            + "ORDER BY created_at DESC";

    public Message getMessLast(int conID) {
        Message result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getMLastConversation)) {

            st.setInt(1, conID);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("created_at");
                Account senderAccount = new AccountDao().getByEmail(new Account(sender));
                Account receiverAccount = new AccountDao().getByEmail(new Account(receiver));
                result = new Message(id, senderAccount, receiverAccount, content, date);
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
    private static String INSERT_M = "INSERT INTO Messages (sender, receiver, content) VALUES (?, ?, ?)";

    public int insert(String sender, String receiver, String content) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_M);
            st.setString(1, sender);
            st.setString(2, receiver);
            st.setString(3, content);
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
    
    private static String INSERT_M_Conversation = "INSERT INTO Messages (sender,content,conversations_id) VALUES (?, ?, ?)";

    public int insertMessConversation(String sender,String content, String conID) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_M_Conversation);
            st.setString(1, sender);
            st.setString(2, content);
            st.setString(3, conID);
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
    private static String DELETE_M = "DELETE FROM Messages WHERE id=?";

    public void deleteMessage(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_M);
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
    private static String UPDATE_M = "UPDATE Messages SET content=? WHERE id=?";

    public void updateMessage(String content, String id) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_M);
            st.setString(1, content);
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

    public static void main(String[] args) {

        MessageDao postDao = new MessageDao();
        String email = "tientien310103@gmail.com";
        String other = "phammackimtien@gmail.com";

//        List<Message> postss = postDao.getMessageByEmail(email, other);
//        for (Message posts : postss) {
//            System.out.println(posts);
//        }
////        String test = "test insert nhe";
////        postDao.insert(email, other, test);
        Message m = postDao.getMessLast(email, other);
        System.out.println(m);
    }

}