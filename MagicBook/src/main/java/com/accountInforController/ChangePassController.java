/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountInforController;

import dao.AccountDao;
import error.ValidationError;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import error.ChangePassError;
import utils.Email;
import utils.Encode;

/**
 *
 * @author MSI PC
 */
public class ChangePassController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePassController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassController at " + request.getContextPath() + "</h1>");
            
            ChangePassError cpe = new ChangePassError("","","");
            boolean checkError = false;
            
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            //lay infor tu form
            String oldPass = request.getParameter("oldPass");
            String newPass = request.getParameter("newPass");
            String confirm = request.getParameter("confirm");
            //ma hoa pass
            String oldPass_Sha1 = Encode.toSHA1(oldPass);
            if (!oldPass_Sha1.equals(account.getPassword())) {
                cpe.setOldPasswordError("Old password does not correct!");
                checkError = true;
            }
            if (newPass.length()<8 || !containsUppercase(newPass) || !containsDigit(newPass)){
                cpe.setNewPasswordError("Password must be at least 8 characters long, one uppercase letter and one digit!");
                checkError = true;
            }
            if (newPass.equals(oldPass)){
                cpe.setNewPasswordError("New password must be different with the old password.");
                checkError = true;
            }
            if (!confirm.equals(newPass)){
                cpe.setConfirmError("Password does not match the confirmation.");
                checkError = true;
            }
            if (!checkError) {
                String newPass_Sha1 = Encode.toSHA1(newPass);
                account.setPassword(newPass_Sha1);
                Random r = new Random();
                String verifyCode = r.nextInt(9999)+ "";
                Email.sendEmail(account.getEmail(), System.currentTimeMillis() + "", "The verify code is: " + verifyCode);
                session.setAttribute("VERIFY_CODE", verifyCode);
                session.setAttribute("CHECK_CONTROLLER", "ChangePass");
                session.setAttribute("ACC_ChangePass", account);
                request.getRequestDispatcher("verifyCode.jsp").forward(request, response);
            } else {
                request.setAttribute("ChangePass_ERROR", cpe);
                request.getRequestDispatcher("changePass.jsp").forward(request, response);
            }
            

            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);

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
        processRequest(request, response);
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