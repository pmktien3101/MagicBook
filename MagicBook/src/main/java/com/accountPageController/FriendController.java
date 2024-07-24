/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountPageController;

import dao.AccountDao;
import dao.AccountProfileDAO;
import dao.EmotionDAO;
import dao.FriendDao;
import dao.NotificationDao;
import dao.PostDao;
import dao.RequestFriendDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
import javax.servlet.http.HttpSession;
import model.Account;
import model.AccountProfile;
import model.Emotion;
import model.Post;

/**
 *
 * @author MSI PC
 */
public class FriendController extends HttpServlet {

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
        if (action != null && action.equals("loadFriend")) {
            loadFriend(request, response);
        } else {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();

            // Get the current page index
            String index = request.getParameter("indexPage");
            int indexx = index != null ? Integer.parseInt(index) : 1;
            FriendDao ad = new FriendDao();
            // get total page
            int totalPage = ad.getNumberPage(email);
            // get the list of friends of the current page
            List<Map<String, String>> friendlist = ad.getFriendByEmailAndIndex(email, indexx);
            PostDao postdao = new PostDao();

            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);

            request.setAttribute("friendlist", friendlist);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("index", indexx);
            request.getRequestDispatcher("friend.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("searchA")) {
            searchFriendName(request, response);
        } else if (action.equals("searchFriend")) {
            searchFriend(request, response);
        } else if (action.equals("loadFriend")) {
            loadFriendPage(request, response);
        } else if (action.equals("unfriend")) {
            unFriend(request, response);
        } else if (action.equals("addFriend")) {
            addFriend(request, response);
        } else if (action.equals("addFriendHome")) {
            addFriendHome(request, response);
        } else if (action.equals("loadFriendPage")) {
            loadFriendPage(request, response);
        }
    }

    protected void searchFriendName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        FriendDao ad = new FriendDao();
        List<Map<String, String>> friendlist = ad.searchFriendByKeyword(email, keyword);
        request.setAttribute("friendlist", friendlist);
        request.getRequestDispatcher("friend.jsp").forward(request, response);
    }

    protected void searchFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        FriendDao ad = new FriendDao();
        List<Map<String, String>> friendlist = ad.searchByKeyword(keyword, email);
        request.setAttribute("friendlist", friendlist);
        request.getRequestDispatcher("listFriend.jsp").forward(request, response);
    }

    protected void loadFriendPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            String friend_email = request.getParameter("fid");
            HttpSession session = request.getSession();
            session.setAttribute("friend_email", friend_email);
            PostDao dao = new PostDao();
            List<Post> listPost = dao.getPostByEmail(friend_email);
            request.setAttribute("posts", listPost);
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(friend_email);
            request.setAttribute("avatar", a);

            FriendDao friendDao = new FriendDao();
            int numberCommon = friendDao.getNumberCommonFriend(email, friend_email);
            request.setAttribute("number", numberCommon);
            boolean isFriend = friendDao.isFriend(friend_email, email);
            request.setAttribute("isFriend", isFriend);
            boolean isWaitAccept = friendDao.isAccept(friend_email, email);
            request.setAttribute("isWait", isWaitAccept);
            //QL them
            List<AccountProfile> listAccProfile = ap.getListAccountProfile();
            request.setAttribute("listAccProfile", listAccProfile);
            AccountDao AccDAO = new AccountDao();
            List<Account> listAcc = AccDAO.getListAccount();
            request.setAttribute("listAcc", listAcc);
            FriendDao friendDAO = new FriendDao();
            List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
            request.setAttribute("friendList", friendList);

            // nhận nid để update isRead
            String id = request.getParameter("nid");
            NotificationDao nDao = new NotificationDao();
            nDao.updateIsRead(id);
            List<Post> allPosts = dao.getAllPostAndSharePost(friend_email);
            request.setAttribute("allPosts", allPosts);
            int numberA = nDao.getNumberNo(email);
            request.setAttribute("numberA", numberA);

            EmotionDAO eDao = new EmotionDAO();
            ArrayList<Emotion> listEmotion = new ArrayList<>();
            listEmotion = eDao.loadEmotion(email);
            request.setAttribute("listEmotion", listEmotion);
            request.getRequestDispatcher("friendPage.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FriendController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void loadFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            String friend_email = request.getParameter("fid");
            HttpSession session = request.getSession();
            session.setAttribute("friend_email", friend_email);
            PostDao dao = new PostDao();
            List<Post> listPost = dao.getPostByEmail(friend_email);
            request.setAttribute("posts", listPost);
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(friend_email);
            request.setAttribute("avatar", a);
            FriendDao friendDao = new FriendDao();
            int numberCommon = friendDao.getNumberCommonFriend(email, friend_email);
            request.setAttribute("number", numberCommon);
            boolean isFriend = friendDao.isFriend(friend_email, email);
            request.setAttribute("isFriend", isFriend);
            boolean isWaitAccept = friendDao.isAccept(friend_email, email);
            request.setAttribute("isWait", isWaitAccept);
            //QL them
            NotificationDao nDao = new NotificationDao();
            int numberA = nDao.getNumberNo(email);
            request.setAttribute("numberA", numberA);
            List<AccountProfile> listAccProfile = ap.getListAccountProfile();
            request.setAttribute("listAccProfile", listAccProfile);
            AccountDao AccDAO = new AccountDao();
            List<Account> listAcc = AccDAO.getListAccount();
            request.setAttribute("listAcc", listAcc);
            FriendDao friendDAO = new FriendDao();
            List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
            request.setAttribute("friendList", friendList);

            // nhận nid để update isRead
            String id = request.getParameter("nid");
            nDao.updateIsRead(id);
            List<Post> allPosts = dao.getAllPostAndSharePost(friend_email);
            request.setAttribute("allPosts", allPosts);
            EmotionDAO eDao = new EmotionDAO();
            ArrayList<Emotion> listEmotion = new ArrayList<>();
            listEmotion = eDao.loadEmotion(email);
            request.setAttribute("listEmotion", listEmotion);
            request.getRequestDispatcher("friendPage.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FriendController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void unFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        // Lấy giá trị friend_email từ session
        String friend_email = (String) request.getSession().getAttribute("friend_email");
        FriendDao ad = new FriendDao();
        ad.unFriend(friend_email, email);
        FriendDao friendDao = new FriendDao();
        boolean isWaitAccept = friendDao.isAccept(friend_email, email);
        request.setAttribute("isWait", isWaitAccept);
        PostDao dao = new PostDao();
        List<Post> listPost = dao.getPostByEmail(friend_email);
        request.setAttribute("posts", listPost);
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(friend_email);
        request.setAttribute("avatar", a);

        //QL
        NotificationDao nDao = new NotificationDao();
        int numberA = nDao.getNumberNo(email);
        request.setAttribute("numberA", numberA);
        List<Post> allPosts = dao.getAllPostAndSharePost(friend_email);
        request.setAttribute("allPosts", allPosts);
        List<AccountProfile> listAccProfile = ap.getListAccountProfile();
        request.setAttribute("listAccProfile", listAccProfile);
        AccountDao AccDAO = new AccountDao();
        List<Account> listAcc = AccDAO.getListAccount();
        request.setAttribute("listAcc", listAcc);
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        request.setAttribute("friendList", friendList);

        request.getRequestDispatcher("friendPage.jsp").forward(request, response);
    }

    protected void addFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String friend_email = (String) request.getSession().getAttribute("friend_email");
        RequestFriendDao requestDao = new RequestFriendDao();
        requestDao.insert(email, friend_email);
        FriendDao friendDao = new FriendDao();
        boolean isWaitAccept = friendDao.isAccept(email, friend_email);
        request.setAttribute("isWait", isWaitAccept);
        PostDao dao = new PostDao();
        List<Post> listPost = dao.getPostByEmail(friend_email);
        request.setAttribute("posts", listPost);
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(friend_email);
        request.setAttribute("avatar", a);

        //QL
        NotificationDao nDao = new NotificationDao();
        int numberA = nDao.getNumberNo(email);
        request.setAttribute("numberA", numberA);
        List<Post> allPosts = dao.getAllPostAndSharePost(friend_email);
        request.setAttribute("allPosts", allPosts);
        List<AccountProfile> listAccProfile = ap.getListAccountProfile();
        request.setAttribute("listAccProfile", listAccProfile);
        AccountDao AccDAO = new AccountDao();
        List<Account> listAcc = AccDAO.getListAccount();
        request.setAttribute("listAcc", listAcc);
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        request.setAttribute("friendList", friendList);

        request.getRequestDispatcher("friendPage.jsp").forward(request, response);
    }

    protected void addFriendHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String friend_email = request.getParameter("friend_email");
        RequestFriendDao requestDao = new RequestFriendDao();
        requestDao.insert(email, friend_email);
        FriendDao friendDao = new FriendDao();
        boolean isWaitAccept = friendDao.isAccept(email, friend_email);
        request.setAttribute("isWait", isWaitAccept);
        PostDao dao = new PostDao();
        List<Post> listPost = dao.getPostByEmail(friend_email);
        request.setAttribute("posts", listPost);
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(friend_email);
        request.setAttribute("avatar", a);
//        NotificationDao nDao = new NotificationDao();
//        int number = nDao.getNumberNo(email);
//        request.setAttribute("number", number);

        //QL
        List<Post> allPosts = dao.getAllPostAndSharePost(friend_email);
        request.setAttribute("allPosts", allPosts);
        List<AccountProfile> listAccProfile = ap.getListAccountProfile();
        request.setAttribute("listAccProfile", listAccProfile);
        AccountDao AccDAO = new AccountDao();
        List<Account> listAcc = AccDAO.getListAccount();
        request.setAttribute("listAcc", listAcc);
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        request.setAttribute("friendList", friendList);

        request.getRequestDispatcher("friendPage.jsp").forward(request, response);
    }
}
