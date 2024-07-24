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
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import error.ForgetPassError;
import utils.Email;
import utils.Encode;

/**
 *
 * @author MSI PC
 */
public class ForgetPassController extends HttpServlet {

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
            out.println("<title>Servlet ForgetPassController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgetPassController at " + request.getContextPath() + "</h1>");
            
            //lay infor tu form
            String email = request.getParameter("email");
            String newPass = request.getParameter("newPass");
            String confirm = request.getParameter("confirm");
            
            AccountDao accDAO = new AccountDao();
            HttpSession session = request.getSession(); 
            ForgetPassError fpe = new ForgetPassError("","","");
            boolean checkError = false;
            Account account = new Account();
            account.setEmail(email);
            Account ac = accDAO.getByEmail(account);              
            
            if (ac == null) {
                fpe.setEmailError("Email does not exist!");
                checkError = true;
            }
            if (newPass.length()<8 || !containsUppercase(newPass) || !containsDigit(newPass)){
                fpe.setNewPasswordError("Password must be at least 8 characters long, one uppercase letter and one digit!");
                checkError = true;
            }

            if (!confirm.equals(newPass)){
                fpe.setConfirmError("Password does not match the confirmation.");
                checkError = true;
            }
            if (!checkError) {
                //Update the password
                String newPass_Sha1 = Encode.toSHA1(newPass);
                ac.setPassword(newPass_Sha1);
                    
                Random r = new Random();
                String verifyCode = "" + r.nextInt(9999);
                Email.sendEmail(ac.getEmail(), System.currentTimeMillis() + "", "The verify code is: " + verifyCode);
                session.setAttribute("VERIFY_CODE", verifyCode);
                session.setAttribute("CHECK_CONTROLLER", "ForgetPass");
                session.setAttribute("ACC_ForgetPass", ac);
                request.getRequestDispatcher("verifyCode.jsp").forward(request, response);
            } else {
                request.setAttribute("ForgetPass_ERROR", fpe);
                request.getRequestDispatcher("forgetPass.jsp").forward(request, response);
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
