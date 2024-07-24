/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeController;

import dao.EmotionDAO;
import dao.AccountDao;
import dao.AccountProfileDAO;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Emotion;
import model.AccountProfile;
import model.Post;
import org.json.JSONArray;

/**
 *
 * @author MSI PC
 */
public class HomeController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            PostDao postdao = new PostDao();
            //display post with public privacy 
//            List<Post> posts = postdao.getAllPostAndSharePostByPrivacy(email);
            List<Post> posts = postdao.getPostTaggedToFriendsAndPrivacyAndComment(email);
            request.setAttribute("posts", posts);
            EmotionDAO eDao = new EmotionDAO();
            ArrayList<Emotion> listEmotion = new ArrayList<>();
            listEmotion = eDao.loadEmotion(email);
            request.setAttribute("listEmotion", listEmotion);
            FriendDao ad = new FriendDao();
            //display friend
            List<Map<String, String>> friendlist = ad.getFriendByEmail(email);
            List<Map<String, String>> proposeList = ad.getFriendByPropose(email);
            request.setAttribute("proposelist", proposeList);
            request.setAttribute("friendlist", friendlist);
            NotificationDao nDao = new NotificationDao();
            int number = nDao.getNumberNo(email);
            request.setAttribute("number", number);

            //VD
            List<Post> postss = postdao.getImageByEmail(email);
            request.setAttribute("images", postss);
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);
            //VD them de lam cac tinh nang cmt
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
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("sharePost")) {
            sharePost(request, response);
        } else if (action.equals("postCmt")) {
            postCmt(request, response);
        } else if (action.equals("deleteCmt")) {
            deleteCmt(request, response);
        } else if (action.equals("editCmt")) {
            editCmt(request, response);
        }
    }

    private void sharePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("postId");
        String caption = request.getParameter("caption-sharePost");
        String privacy = request.getParameter("privacy");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();

        PostDao dao = new PostDao();
        int result = dao.saveShare(postId, email, privacy, caption);

        if (result > 0) {
            response.sendRedirect("AccountPage");
        } else {
            request.setAttribute("error", "Failed to share post");
            request.getRequestDispatcher("accountPage.jsp").forward(request, response);
        }
    }

    private void postCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        String postID = request.getParameter("postID");
        String textCmt = request.getParameter("textCmt");
        String userCmt = request.getParameter("userCmt");

        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.insertCMT(postID, userCmt, textCmt);
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
        response.sendRedirect("Home");

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
        response.sendRedirect("Home");
    }

    private void editCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postID = request.getParameter("pid");
        int pid = Integer.parseInt(postID);
        String cmtID = request.getParameter("cmtid");
        int cmtid = Integer.parseInt(cmtID);
        String textCmt = request.getParameter("textComment");

        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.editCmt(pid, cmtid, textCmt);
        response.sendRedirect("Home");

    }
}
