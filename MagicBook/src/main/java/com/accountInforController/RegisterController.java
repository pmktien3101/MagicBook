/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountInforController;

import dao.AccountDao;
import java.io.IOException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import error.AccountError;
import org.apache.http.ParseException;
import utils.Email;
import utils.Encode;

/**
 *
 * @author MSI PC
 */
public class RegisterController extends HttpServlet {

    private final String ERROR = "register.jsp";
    private final String SUCCESS = "index.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, java.text.ParseException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        AccountError accError = new AccountError("", "", "", "", "", "", "");
        AccountDao accDAO = new AccountDao();
        boolean checkError = false;
        try {
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String dob = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
            if (!isValidName(firstName)) {
                accError.setFirstNameError("Cannot contain special characters!");
                checkError = true;
            }
            if (!isValidName(lastName)) {
                accError.setLastNameError("Cannot contain special characters!");
                checkError = true;
            }
            if (!isValidEmail(email)) {
                accError.setEmailError("Email is not valid!");
                checkError = true;
            }
            if (accDAO.checkDuplicateEmail(email)) {
                accError.setEmailError("This email is used before!");
                checkError = true;
            }
            if (accDAO.checkDuplicatePhone(phone)) {
                accError.setPhoneError("This phone number is used before!");
                checkError = true;
            }
            if (!isValidPhone(phone)) {
                accError.setPhoneError("Not valid! The length must be 9 - 15 decimal");
                checkError = true;
            }
            if (password.length()<8 || !containsUppercase(password) || !containsDigit(password)){
                accError.setPasswordError("At least 8 characters, 1 uppercase letter, 1 digit!");
                checkError = true;
            }
            if (!confirmPassword.equals(password)) {
                accError.setConfirmPasswordError("Password and Confirm Password are not same!");
                checkError = true;
            }
            if (!isValidDate(dob)) {
                accError.setDobError("Must be older than 12 years");
                checkError = true;
            }

            if (!checkError) {
                Random r = new Random();
                String verifyCode =   r.nextInt(9999)+ "";
                Email.sendEmail(email, System.currentTimeMillis() + "", "The verify code is: " + verifyCode);
                
                password = Encode.toSHA1(password);
                
                Account acc = new Account(firstName, lastName, email, phone, password, dob, gender, "active", 0);
                
                HttpSession session = request.getSession();
                session.setAttribute("VERIFY_CODE", verifyCode);
                session.setAttribute("USER", acc);
                session.setAttribute("CHECK_CONTROLLER", "Register");
                request.getRequestDispatcher("verifyCode.jsp").forward(request, response);
//                accDAO.registerUser(acc);
//                url = SUCCESS;

            }
        } catch (SQLException e) {
            log("Error at Register Controller: " + e.getMessage());
        } finally {

            request.setAttribute("ACC_ERROR", accError);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private static boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{9,15}$");
    }

    private static boolean isValidName(String name) {
        return name.matches("^[\\p{L}0-9]+$");
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private static boolean isValidDate(String dateString) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(dateString, formatter);

            LocalDate currentDate = LocalDate.now();
            System.out.println("current: " + currentDate);
            // Tính số năm giữa năm hiện tại và năm được truyền vào
            Period period = Period.between(inputDate, currentDate);
            int age = period.getYears();

            // Kiểm tra nếu tuổi lớn hơn 12
            return age >= 12;
        } catch (DateTimeException e) {
            return false;
        }
    }
    
    private boolean containsUppercase(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsDigit(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
