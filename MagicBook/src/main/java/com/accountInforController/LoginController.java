/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountInforController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import utils.Encode;

/**
 *
 * @author MSI PC
 */
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");

            // lấy infor từ form
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            HttpSession session = request.getSession();
            AccountDao dao = new AccountDao();

            //check user login gg or bth
            String code = request.getParameter("code");
            if (code != null && !code.isEmpty()) {
                // login with google
                String accessToken = getToken(code);
                Account getUser = getUserInfo(accessToken);
                Account acc = dao.getByEmail(getUser);
                if (acc != null) {
                    //user dưới db
                    if (acc.getRole() == 1) {
                        session.setAttribute("accountadmin", acc);
                        response.sendRedirect("ReportPage");
                    } else {
                        if (!acc.getStatus().equals("INACTIVE")) {
                            session.setAttribute("account", acc);
                            response.sendRedirect("AccountPage");
                        } else {
                            request.setAttribute("error", "Your account has been baned.");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                } else {
                    request.setAttribute("error", "Google account not related to any account");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                //login bth
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);

                Account ac = dao.getByEmail(account);
                if (ac != null) {

                    if (remember != null) {
                        Cookie emailCookie = new Cookie("cookEmail", email);
                        Cookie passCookie = new Cookie("cookPassword", password);
                        Cookie rememberCookie = new Cookie("cookRemember", remember);
                        emailCookie.setMaxAge(30 * 24 * 60 * 60);
                        passCookie.setMaxAge(30 * 24 * 60 * 60);
                        rememberCookie.setMaxAge(30 * 24 * 60 * 60);
                        response.addCookie(emailCookie);
                        response.addCookie(passCookie);
                        response.addCookie(rememberCookie);
                    }

                    if (ac.getRole() == 1) {
                        // nếu admin
                        if (password.equals(ac.getPassword())) {
                            session.setAttribute("accountadmin", ac);
                            response.sendRedirect("ReportPage");
                        } else {
                            // Xử lý khi mật khẩu admin không đúng
                            request.setAttribute("error", "Wrong email or password");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } else {
                        //nếu user
                        String encode = Encode.toSHA1(password);
                        if (encode.equals(ac.getPassword()) && ac.getStatus().equals("active")) {
                            session.setAttribute("account", ac);
                            response.sendRedirect("AccountPage");
                        } else {
                            if (ac.getStatus().equals("INACTIVE")) {
                                request.setAttribute("error", "Your account has been baned.");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                // Xử lý khi mật khẩu user không đúng
                                request.setAttribute("error", "Wrong email or password");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        }
                    }
                } else {
                    // Xử lý khi không tìm thấy account
                    request.setAttribute("error", "Wrong email or password");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }

            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class
        );
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static Account getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        Account google = new Gson().fromJson(response, Account.class
        );

        return google;
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
