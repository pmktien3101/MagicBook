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
import model.Emotion;
import utils.DatabaseUtils;
import java.sql.Timestamp;

/**
 *
 * @author Asus
 */
public class EmotionDAO {

    private static String INSERT_EMOTION = "insert into Emotions(id,image,lable, email) values(?,?,?,?)";
    private static String UPDATE_EMOTION = "update Emotions set image = ?, lable =?, time =? where id=? and email=?";
    private static String CHECK_VALID = "select id, email from Emotions where id=? and email=?";
    private static String LOAD_EMOTION = "select * from emotions where email = ?";
    private static String COUNT_REACTION = "update post set num_reaction = \n"
            + "(select count(*) from emotions where id = ?\n"
            + "and image != 'http://localhost:8080/MagicBook/assets/no-like.png') where id = ?";

    public void countReaction(int id) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(COUNT_REACTION);
                stm.setInt(1, id);
                stm.setInt(2, id);
                stm.executeUpdate();
            }
        } catch (SQLException e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public ArrayList<Emotion> loadEmotion(String accountEmail) throws SQLException {
        ArrayList<Emotion> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(LOAD_EMOTION);
                stm.setString(1, accountEmail);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String lable = rs.getString("lable");
                    String image = rs.getString("image");

                    String email = rs.getString("email");
                    Emotion e = new Emotion(id, lable, image, email);
                    System.out.println(e.getId());
                    System.out.println(e.getLable());
                    System.out.println(e.getImg());

                    System.out.println(e.getEmail());
                    list.add(e);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
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
        return list;
    }

    public boolean checkValidPostById(int id, String email) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            stm = conn.prepareStatement(CHECK_VALID);
            stm.setInt(1, id);
            stm.setString(2, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (ClassNotFoundException | SQLException e) {
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
        return check;
    }

    public void updateEmotion(int id, String lable, String img, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;

        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(UPDATE_EMOTION);

                stm.setString(1, img);
                stm.setString(2, lable);
                stm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                stm.setInt(4, id);
                stm.setString(5, email);
                stm.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void insertEmotion(int id, String lable, String img, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(INSERT_EMOTION);
                stm.setInt(1, id);
                stm.setString(2, img);
                stm.setString(3, lable);
                stm.setString(4, email);
                stm.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static String DELETE_EMOTION_BEFORE_DELETE_POST = "DELETE FROM Emotions WHERE id = ? ";

    public void deleteAllEmotionsOfPost(String id) {

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_EMOTION_BEFORE_DELETE_POST);
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
