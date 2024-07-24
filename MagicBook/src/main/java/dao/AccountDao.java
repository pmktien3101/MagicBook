/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class AccountDao {

    private static String LoginGG = "Select * from Account Where email = ?";

    private static final String DUPLICATE = "SELECT email\n"
            + "FROM Account\n"
            + "WHERE email = ?";
    private static final String DUPLICATEPHONE = "SELECT phone\n"
            + "FROM Account\n"
            + "WHERE phone = ?";
    private static final String INSERT = "INSERT INTO Account (email, firstname, lastname,password,gender, dob,phone, status, role) VALUES \n"
            + "(?, ?, ?, ?, ?, ?,?,'active', 0);";

    public boolean changePass(Account acc) {
        int result = 0;
        try {
            Connection conn = DatabaseUtils.getConnection();

            String sql = "UPDATE Account SET password=? WHERE email=?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, acc.getPassword());
            st.setString(2, acc.getEmail());
            result = st.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result > 0;
    }

    public boolean registerUser(Account acc) throws SQLException, ParseException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(INSERT);
                stm.setString(1, acc.getEmail());
                stm.setString(2, acc.getFirstName());
                stm.setString(3, acc.getLastName());
                stm.setString(4, acc.getPassword());
                stm.setString(5, acc.getGender());
                java.sql.Date sqlDate = java.sql.Date.valueOf(acc.getDob());
                stm.setDate(6, sqlDate);
                stm.setString(7, acc.getPhone());
                check = stm.executeUpdate() > 0;
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
        return check;

    }

    public boolean checkDuplicatePhone(String phone) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(DUPLICATEPHONE);
                stm.setString(1, phone);
                rs = stm.executeQuery();
                if (rs.next()) {
                    check = true;
                }

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
        return check;
    }

    public boolean checkDuplicateEmail(String email) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(DUPLICATE);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    check = true;
                }

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
        return check;
    }

    public Account getByEmail(Account acc) {

        Account result = null;
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement st = conn.prepareStatement(LoginGG);
            st.setString(1, acc.getEmail());
            ResultSet rs = st.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String dob = rs.getString("dob");
                    String status = rs.getString("status");
                    String gender = rs.getString("gender");
                    int role = rs.getInt("role");
                    result = new Account(firstName, lastName, email, phone, password, dob, gender, status, role);
                }
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
    private static String Login = "Select * from Account Where email = ? and password = ?";

    public Account getByEmailAndPass(Account acc) {

        Account result = null;
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement st = conn.prepareStatement(Login);
            st.setString(1, acc.getEmail());
            st.setString(2, acc.getPassword());
            ResultSet rs = st.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String dob = rs.getString("dob");
                    String status = rs.getString("status");
                    String gender = rs.getString("gender");
                    int role = rs.getInt("role");
                    result = new Account(firstName, lastName, email, phone, password, dob, gender, status, role);
                }
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

    private static final String DELETE_ACCOUNT = "BEGIN TRANSACTION;\n"
            + "DECLARE @EmailToDelete VARCHAR(60);\n"
            + "SET @EmailToDelete = ?;\n"
            + "IF EXISTS (SELECT 1 FROM Account_Profile WHERE email = @EmailToDelete)\n"
            + "BEGIN\n"
            + "    DELETE FROM Account_Profile WHERE email = @EmailToDelete;\n"
            + "END\n"
            + "DELETE FROM Account WHERE email = @EmailToDelete;\n"
            + "COMMIT TRANSACTION;";

    public void deleteAccount(String email) {
        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(DELETE_ACCOUNT)) {
            st.setString(1, email);
            int result = st.executeUpdate();
            if (result > 0) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Account not found or already deleted.");
            }
         } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Account> getListAccount() {
        List<Account> listAcc = new ArrayList<>();
        String sql = "SELECT email, firstname, lastname, gender, dob, phone, role FROM Account ";

        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String gender = rs.getString("gender");
                String dob = rs.getString("dob");
                String phone = rs.getString("phone");
                int role = rs.getInt("role");

                Account acc = new Account();
                acc.setEmail(email);
                acc.setFirstName(firstname);
                acc.setLastName(lastname);
                acc.setGender(gender);
                acc.setDob(dob);
                acc.setPhone(phone);
                acc.setRole(role);
                listAcc.add(acc);
            }

            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error Detail: " + ex.getMessage());
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Error Detail:" + ex.getMessage());
            ex.printStackTrace();
        }

        return listAcc;
    }
    
    private static String UPDATE_INACTIVE = "UPDATE Account SET status='INACTIVE' WHERE email = ? ";

    public void banAcc(String email) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_INACTIVE);
            st.setString(1, email);
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
        AccountDao ad = new AccountDao();
        Account acc = new Account();
        String email = "tienpmkse170552@fpt.edu.vn";
        ad.deleteAccount(email);
    }
}