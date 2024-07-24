/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeController;

import com.accountPageController.AccountPageController;
import dao.AccountDao;
import dao.AccountProfileDAO;
import dao.EmotionDAO;
import dao.FriendDao;
import dao.NotificationDao;
import dao.PostCommentDAO;
import dao.PostDao;
import dao.ReportCommentDao;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.AccountProfile;
import model.Emotion;
import model.Notification;
import model.Post;
import org.json.JSONArray;

/**
 *
 * @author MSI PC
 */
public class AnnouncementController extends HttpServlet {

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

        String action = request.getParameter("action");
        if (action != null && action.equals("loadPost")) {
            viewPost(request, response);
        } else {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);
            NotificationDao nDao = new NotificationDao();
            List<Notification> listNo = nDao.getNByEmail(email);
            HashMap<String, String> listAvatar = new HashMap<>();
            try {
                listAvatar = nDao.getAvatarNotification();
            } catch (SQLException ex) {
                Logger.getLogger(AnnouncementController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("listAvatar", listAvatar);

            request.setAttribute("listNo", listNo);
            request.getRequestDispatcher("announcement.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        if (action.equals("deleteNo")) {
            deleteNotification(request, response);
        } else if (action.equals("postCmt")) {
            postCmt(request, response);
        } else if (action.equals("deleteCmt")) {
            deleteCmt(request, response);
        } else if (action.equals("editCmt")) {
            editCmt(request, response);
        }
    }

    protected void deleteNotification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String noid = request.getParameter("aid");
        NotificationDao nDao = new NotificationDao();
        nDao.deleteNo(noid);
        response.sendRedirect("Announcement");
    }

    protected void viewPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String postId = request.getParameter("pid");
        if (postId == null || postId.isEmpty()) {
            response.sendRedirect("404page.jsp");
        } else {
            String noid = request.getParameter("nid");
            PostDao pDao = new PostDao();
            List<Post> list = pDao.getAllPostAndSharePostByPrivacy(email);
            Post post = new Post();
            for (Post p : list) {
                if (p.getId() == Integer.parseInt(postId)) {
                    post = p;
                }
            }
            request.setAttribute("post", post);
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);
            //VD them de lam cac tinh nang cmt
            try {
                List<AccountProfile> listAccProfile = ap.getListAccountProfile();
                request.setAttribute("listAccProfile", listAccProfile);
                AccountDao AccDAO = new AccountDao();
                List<Account> listAcc = AccDAO.getListAccount();
                request.setAttribute("listAcc", listAcc);
                Account acc = AccDAO.getByEmail(account);
                request.setAttribute("name", acc);
                FriendDao friendDAO = new FriendDao();
                List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
                request.setAttribute("friendList", friendList);
                EmotionDAO eDao = new EmotionDAO();
                ArrayList<Emotion> listEmotion = new ArrayList<>();
                listEmotion = eDao.loadEmotion(email);
                request.setAttribute("listEmotion", listEmotion);
            } catch (SQLException ex) {
                Logger.getLogger(AccountPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //update isRead
            NotificationDao nDao = new NotificationDao();
            nDao.updateIsRead(noid);
            request.getRequestDispatcher("postAnnouncement.jsp").forward(request, response);

        }
    }

    private void postCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String postID = request.getParameter("postID");
        String textCmt = request.getParameter("textCmt");
        String userCmt = request.getParameter("userCmt");
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);

        PostDao pDao = new PostDao();
        List<Post> list = pDao.getAllPostAndSharePostByPrivacy(email);
        Post post = new Post();
        for (Post p : list) {
            if (p.getId() == Integer.parseInt(postID)) {
                post = p;
            }
        }
        request.setAttribute("post", post);
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(email);
        request.setAttribute("avatar", a);
        try {
            List<AccountProfile> listAccProfile = ap.getListAccountProfile();
            request.setAttribute("listAccProfile", listAccProfile);
            AccountDao AccDAO = new AccountDao();
            List<Account> listAcc = AccDAO.getListAccount();
            request.setAttribute("listAcc", listAcc);
            Account acc = AccDAO.getByEmail(account);
            request.setAttribute("name", acc);
            request.setAttribute("friendList", friendList);
            EmotionDAO eDao = new EmotionDAO();
            ArrayList<Emotion> listEmotion = new ArrayList<>();
            listEmotion = eDao.loadEmotion(email);
            request.setAttribute("listEmotion", listEmotion);
        } catch (SQLException ex) {
            Logger.getLogger(AccountPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] listFriendCmt = request.getParameterValues("listFriendID");
        if (listFriendCmt.length > 0) {
            // Phân tích cú pháp chuỗi JSON
            JSONArray jsonArray = new JSONArray(listFriendCmt[0]);

            // Duyệt qua mảng JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                String tagged_id = jsonArray.getString(i);
                String tagged_email = friendList.get(Integer.parseInt(tagged_id)).get("friend_email");
                TagDAO tagDAO = new TagDAO();
                tagDAO.insert(email, tagged_email, postID);
            }
        }

        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.insertCMT(postID, userCmt, textCmt);
        response.sendRedirect("Announcement?action=loadPost&pid=" + postID);
    }

    private void deleteCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postID = request.getParameter("pid");
        int pid = Integer.parseInt(postID);
        String cmtID = request.getParameter("cmtid");
        int cmtid = Integer.parseInt(cmtID);
        ReportCommentDao rDao = new ReportCommentDao();
        rDao.deleteReportCommentByPostID(cmtID, postID);
        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.deleteCmt(pid, cmtid);
        response.sendRedirect("Announcement?action=loadPost&pid=" + postID);
    }

    private void editCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postID = request.getParameter("pid");
        int pid = Integer.parseInt(postID);
        String cmtID = request.getParameter("cmtid");
        int cmtid = Integer.parseInt(cmtID);
        String textCmt = request.getParameter("textComment");

        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.editCmt(pid, cmtid, textCmt);
        response.sendRedirect("Announcement?action=loadPost&pid=" + postID);

    }

}
