/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountPageController;

import dao.AccountProfileDAO;
import dao.NotificationDao;
import dao.PostDao;
import dao.RequestFriendDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.AccountProfile;
import model.Post;

/**
 *
 * @author MSI PC
 */
public class RequestFriendController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("isRead")) {
            updateIsRead(request, response);
        } else {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);

            // Get the current page index
            String index = request.getParameter("indexPage");
            int indexx = index != null ? Integer.parseInt(index) : 1;
            RequestFriendDao dao = new RequestFriendDao();

            // get total page
            int totalPage = dao.getNumberPage(email);
            // get the list of request of the current page

            List<Map<String, String>> friendlist = dao.getRequestEmailAndIndex(email, indexx);
            request.setAttribute("requestlist", friendlist);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("index", indexx);
            request.getRequestDispatcher("requestFriend.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("loadAccountPage")) {
            loadAccountPage(request, response);
        } else if (action.equals("deleteRequest")) {
            deleteRequestFriend(request, response);
        } else if (action.equals("acceptRequest")) {
            acceptRequestFriend(request, response);
        }
    }

    protected void loadAccountPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("rid");
        PostDao dao = new PostDao();
        List<Post> listPost = dao.getPostByEmail(email);
        request.setAttribute("posts", listPost);
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(email);
        request.setAttribute("avatar", a);
        request.getRequestDispatcher("friendPage.jsp").forward(request, response);
    }

    protected void deleteRequestFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("rid");
        RequestFriendDao dao = new RequestFriendDao();
        dao.deleteRequestFriend(id);
        response.sendRedirect("RequestFriend");
    }

    protected void acceptRequestFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("rid");
        RequestFriendDao dao = new RequestFriendDao();
        dao.acceptRequestFriend(id);
        response.sendRedirect("RequestFriend");
    }

    protected void updateIsRead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("nid");
        NotificationDao nDao = new NotificationDao();
        nDao.updateIsRead(id);
        response.sendRedirect("RequestFriend");
    }
}