/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adminController;

import dao.AccountDao;
import dao.AccountProfileDAO;
import dao.EmotionDAO;
import dao.NotificationDao;
import dao.PostCommentDAO;
import dao.PostDao;
import dao.ReportCommentDao;
import dao.ReportPostDao;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.AccountProfile;
import model.Post;
import model.PostComment;
import model.ReportComment;
import model.ReportPost;

/**
 *
 * @author MSI PC
 */
public class AdminPageController extends HttpServlet {

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
        if (action != null && action.equals("loadReportComment")) {
            loadReportComment(request, response);
        } else {
            ReportPostDao reportDao = new ReportPostDao();
            List<ReportPost> reportList = reportDao.getReportPost();
            request.setAttribute("reportPostList", reportList);
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("loadReportForm")) {
            loadReportForm(request, response);
        } else if (action.equals("insertReport")) {
            insertReport(request, response);
        } else if (action.equals("viewReportPost")) {
            viewReportPost(request, response);
        } else if (action.equals("deleteReportPost")) {
            deleteReportPost(request, response);
        } else if (action.equals("remindAccount")) {
            remindAccount(request, response);
        } else if (action.equals("banAcc")) {
            banAcc(request, response);
        } else if (action.equals("loadReportComment")) {
            loadReportComment(request, response);
        } else if (action.equals("insertReportC")) {
            insertReportComment(request, response);
        } else if (action.equals("deleteReportComment")) {
            deleteReportComment(request, response);
        } else if (action.equals("viewReportComment")) {
            viewReportComment(request, response);
        } else if (action.equals("remindComment")) {
            remindComment(request, response);
        } else if (action.equals("deleteCmt")) {
            deleteComment(request, response);
        } else if (action.equals("deletePost")) {
            deletePost(request, response);
        }
    }

    protected void loadReportForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String mid = request.getParameter("cmtid");
        request.setAttribute("pid", pid);
        request.setAttribute("mid", mid);
        request.getRequestDispatcher("reportForm.jsp").forward(request, response);
    }

    protected void loadReportComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ReportCommentDao dao = new ReportCommentDao();
        List<ReportComment> reportCmtList = dao.getReportPostComment();
        request.setAttribute("reportCmtList", reportCmtList);
        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }

    protected void insertReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String reason = request.getParameter("reportReason");
        String pid = request.getParameter("pid");
        ReportPostDao reportDao = new ReportPostDao();
        reportDao.insert(pid, reason, email);
        response.sendRedirect("Home");
    }

    protected void insertReportComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String reason = request.getParameter("reportReason");
        String pid = request.getParameter("pid");
        String cmtid = request.getParameter("commentid");
        ReportCommentDao dao = new ReportCommentDao();
        dao.insert(cmtid, pid, reason, email);
        response.sendRedirect("Home");
    }

    protected void viewReportPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pid = request.getParameter("pid");
        PostDao pDao = new PostDao();
        Post post = pDao.getAllSharePostInforById(pid);
        request.setAttribute("post", post);
        AccountProfileDAO ap = new AccountProfileDAO();
        List<AccountProfile> listAccProfile = ap.getListAccountProfile();
        request.setAttribute("listAccProfile", listAccProfile);
        request.getRequestDispatcher("reportPost.jsp").forward(request, response);
    }

    protected void viewReportComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String cmtid = request.getParameter("cmtid");
        PostDao pDao = new PostDao();
        Post post = pDao.getAllSharePostInforById(pid);
        request.setAttribute("post", post);
        PostCommentDAO cDao = new PostCommentDAO();
        PostComment cmt = cDao.getCmtById(cmtid, pid);
        request.setAttribute("reportCmt", cmt);
        AccountProfileDAO ap = new AccountProfileDAO();
        List<AccountProfile> listAccProfile = ap.getListAccountProfile();
        request.setAttribute("listAccProfile", listAccProfile);
        AccountDao AccDAO = new AccountDao();
        List<Account> listAcc = AccDAO.getListAccount();
        request.setAttribute("listAcc", listAcc);
        request.getRequestDispatcher("reportPost.jsp").forward(request, response);
    }

    protected void banAcc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        AccountDao dao = new AccountDao();
        dao.banAcc(email);
        response.sendRedirect("ReportPage");
    }

    protected void deleteReportPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rid = request.getParameter("rid");
        ReportPostDao rDao = new ReportPostDao();
        rDao.deleteReportPost(rid);
        response.sendRedirect("ReportPage");
    }

    protected void deleteReportComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rid = request.getParameter("rid");
        ReportCommentDao rDao = new ReportCommentDao();
        rDao.deleteReportComment(rid);
        response.sendRedirect(request.getContextPath() + "/ReportPage?action=loadReportComment");
    }

    protected void deletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("pid");
        Post p = new Post();
        PostDao dao = new PostDao();
        PostCommentDAO pDao = new PostCommentDAO();
        EmotionDAO eDao = new EmotionDAO();
        ReportPostDao rDao = new ReportPostDao();
        TagDAO tagDAO = new TagDAO();
        tagDAO.deleteAllTagOfPost(id);
        ReportCommentDao rcDao = new ReportCommentDao();
        rcDao.deleteReportCommentByPostID(id);
        pDao.deleteAllCommentsOfPost(id);
        eDao.deleteAllEmotionsOfPost(id);
        rDao.deleteReport(id);
        

        //Muốn deleteAllShareOfPost phải delete cmt share emotion của post :<
        //Lấy all post có originalID=PostID
        List<Post> list = dao.getAllSharePostOfPost(id);
        for (Post post : list) {
            //
            rDao.deleteReport(post.getId() + "");
            //delete all tag of share post
            tagDAO.deleteAllTagOfPost(post.getId() + "");
            //
            rcDao.deleteReportCommentByPostID(post.getId() + "");
            //delete all cmt of share post
            pDao.deleteAllCommentsOfPost(post.getId() + "");
            //delete all emotion of share post
            eDao.deleteAllEmotionsOfPost(post.getId() + "");
            
        }
        dao.deleteAllSharePostOfPost(id);
        dao.deletePost(id);
        response.sendRedirect("ReportPage");
    }

    protected void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("pid"));
        int cmt = Integer.parseInt(request.getParameter("cmtid"));
        PostCommentDAO pDao = new PostCommentDAO();
        ReportCommentDao rDao = new ReportCommentDao();
        rDao.deleteReportCommentByPostID(cmt, id);
        pDao.deleteCmt(id, cmt);
        response.sendRedirect(request.getContextPath() + "/ReportPage?action=loadReportComment");
    }

    protected void remindAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("accountadmin");
        String sender_email = account.getEmail();
        String user_email = request.getParameter("user_email");
        String pid = request.getParameter("pid");
        HttpSession session = request.getSession();
        session.setAttribute("reportPostID", pid);
        NotificationDao nDao = new NotificationDao();
        nDao.insertNotification(user_email, sender_email, "", "", pid, "Your post has been reported for violating community rules");
        response.sendRedirect("ReportPage");
    }

    protected void remindComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("accountadmin");
        String sender_email = account.getEmail();
        String user_email = request.getParameter("user_email");
        String pid = request.getParameter("pid");
        String cmtid = request.getParameter("cmtid");
        HttpSession session = request.getSession();
        session.setAttribute("reportPostID", pid);
        NotificationDao nDao = new NotificationDao();
        nDao.insertNotificationComment(user_email, sender_email, pid, cmtid, "Your comment has been reported for violating community rules");
        response.sendRedirect(request.getContextPath() + "/ReportPage?action=loadReportComment");

    }
}
