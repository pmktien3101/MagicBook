/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountInforController;

import dao.AccountDao;
import dao.AccountProfileDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "VerifyCodeController", urlPatterns = {"/VerifyCodeController"})
public class VerifyCodeController extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyCodeController</title>");
            out.println("</head>");
            out.println("<body>");
            
            HttpSession session = request.getSession();
            String verifyCode = session.getAttribute("VERIFY_CODE").toString();
            String verifyCodeUser =  request.getParameter("verifyCode");
            String checkController = session.getAttribute("CHECK_CONTROLLER").toString();
            
            Account acc = null;
            AccountDao dao = new AccountDao();

            if (verifyCode.equals(verifyCodeUser)) {
                if (checkController.equals("ChangePass")){
                    acc = (Account) session.getAttribute("ACC_ChangePass");                  
                    dao.changePass(acc);                  
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if (checkController.equals("ForgetPass")){
                    acc = (Account) session.getAttribute("ACC_ForgetPass");
                    dao.changePass(acc);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if (checkController.equals("Register")){
                    acc = (Account) session.getAttribute("USER");
                    dao.registerUser(acc);
                    AccountProfileDAO profileDAO = new AccountProfileDAO();
                    profileDAO.registerUser(acc.getEmail());
                    
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                
            } else {
                request.setAttribute("error", "The verify code does not match!");
                if (acc != null) {
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("verifyCode.jsp").forward(request, response);

                }

            }

            out.println("</body>");
            out.println("</html>");
        }
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
        } catch (SQLException ex) {
            Logger.getLogger(VerifyCodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VerifyCodeController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(VerifyCodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VerifyCodeController.class.getName()).log(Level.SEVERE, null, ex);
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