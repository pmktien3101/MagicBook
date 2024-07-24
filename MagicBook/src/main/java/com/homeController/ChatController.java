/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeController;

import dao.AccountProfileDAO;
import dao.ConversationDao;
import dao.FriendDao;
import dao.MessageDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.AccountProfile;
import model.Conversation;
import model.Conversation_user;
import model.Message;

/**
 *
 * @author MSI PC
 */
public class ChatController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("loadMess")) {
            loadMessageByFriendEmail(request, response);
        } else if (action != null && action.equals("load")) {
            loadConversations(request, response);
        } else if (action != null && action.equals("loadMessCon")) {
            loadMessageByConversation(request, response);
        } else {
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            FriendDao ad = new FriendDao();
            List<Map<String, String>> friendlist = ad.GetAllFriend(email);
            List<String> lastMessages = new ArrayList<>();
            MessageDao messDao = new MessageDao();
            for (Map<String, String> friend : friendlist) {
                String friendEmail = friend.get("friend_email");
                Message lastMessage = messDao.getMessLast(email, friendEmail);
                if (lastMessage != null) {
                    lastMessages.add(lastMessage.getContent());
                } else {
                    lastMessages.add("");
                }
            }
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            request.setAttribute("avatar", a);
            request.setAttribute("friendlist", friendlist);
            request.setAttribute("lastMessages", lastMessages);
            request.getRequestDispatcher("chat.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("loadMess")) {
            loadMessageByFriendEmail(request, response);
        } else if (action.equals("chatOne")) {
            chatOneFriend(request, response);
        } else if (action.equals("deleteM")) {
            deleteMessage(request, response);
        } else if (action.equals("loadManyConversation")) {
            loadConversations(request, response);
        } else if (action.equals("createConversation")) {
            createConversations(request, response);
        } else if (action.equals("loadConMess")) {
            loadMessageByConversation(request, response);
        } else if (action.equals("chatGroup")) {
            chatGroup(request, response);
        } else if (action.equals("InsertFriend")) {
            inviteFriendToGroup(request, response);
        } else if (action.equals("deleteGroup")) {
            deleteGroup(request, response);
        } else if (action.equals("leaveGroup")) {
            leaveOutGroup(request, response);
        } else if (action.equals("changeAvatar")) {
            updateAvatarConversation(request, response);
        } else if (action.equals("changeName")) {
            updateNameConversation(request, response);
        } else if (action.equals("viewFriend")) {
            loadFriendConversation(request, response);
        } else if (action.equals("loadM")) {
            loadMessage(request, response);
        }
    }

    protected void loadMessageByFriendEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String otherEmail = request.getParameter("friend_email");
        HttpSession session = request.getSession();
        session.setAttribute("friend_email", otherEmail);
        MessageDao messDao = new MessageDao();
        FriendDao ad = new FriendDao();
        List<Map<String, String>> friendlist = ad.GetAllFriend(email);
        List<Map<String, String>> infor = ad.getInforByEmail(otherEmail);
        List<String> lastMessages = new ArrayList<>();
        for (Map<String, String> friend : friendlist) {
            String friendEmail = friend.get("friend_email");
            Message lastMessage = messDao.getMessLast(email, friendEmail);
            if (lastMessage != null) {
                lastMessages.add(lastMessage.getContent());
            } else {
                lastMessages.add("");
            }
        }
        AccountProfileDAO ap = new AccountProfileDAO();
        AccountProfile a = ap.getAccountProfile(email);
        request.setAttribute("avatar", a);
        request.setAttribute("infor", infor);
        request.setAttribute("friendlist", friendlist);
        request.setAttribute("lastMessages", lastMessages);
        List<Message> messlist = messDao.getMessageByEmail(email, otherEmail);
        request.setAttribute("messlist", messlist);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    protected void loadConversations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        ConversationDao conDao = new ConversationDao();
        List<Conversation> conversationList = conDao.getConversationByEmail(email);
        request.setAttribute("conList", conversationList);
        FriendDao dao = new FriendDao();
        List<Map<String, String>> friendlist = dao.GetAllFriend(email);
        request.setAttribute("friendlistt", friendlist);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    protected void createConversations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String emailCreator = account.getEmail();
        String name = request.getParameter("name");
        String avatar = request.getParameter("avatar");
        String friendEmail = request.getParameter("friend");
        ConversationDao conDao = new ConversationDao();
        conDao.createConversation(name, avatar, emailCreator, friendEmail);
        response.sendRedirect(request.getContextPath() + "/Chat?action=load");
    }

    protected void loadMessageByConversation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String id = request.getParameter("cid");
        HttpSession session = request.getSession();
        session.setAttribute("cid", id);
        MessageDao messDao = new MessageDao();

        List<Message> messlist = messDao.getMessageByConversation(id);
        request.setAttribute("messConList", messlist);
        ConversationDao conDao = new ConversationDao();
        List<String> lastMessages = new ArrayList<>();
        List<Conversation> conversationList = conDao.getConversationByEmail(email);
//        for (Conversation conversation : conversationList) {
//            int conid = conversation.getId();
////            Message last = messDao.getMessLast(id);
//            if (last != null) {
//                lastMessages.add(last.getContent());
//            } else {
//                lastMessages.add("");
//            }
//        }
//        request.setAttribute("lastMessages", lastMessages);
        Conversation conversation = conDao.getConversationById(id);
        request.setAttribute("conversation_infor", conversation);
        FriendDao dao = new FriendDao();
        List<Map<String, String>> friendlist = dao.GetAllFriend(email);
        request.setAttribute("friendlistt", friendlist);
        int numberFriend = conDao.getNumberFriend(id);
        Conversation_user cu = conDao.getConversationUserById(email, id);
        if (cu != null && cu.isIsAdmin()) {
            request.setAttribute("isAdmin", cu);
        }
        request.setAttribute("number", numberFriend);
        request.setAttribute("conList", conversationList);
        request.setAttribute("email", email);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    protected void loadFriendConversation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String id = request.getParameter("cid");
        MessageDao messDao = new MessageDao();
        List<Message> messlist = messDao.getMessageByConversation(id);
        request.setAttribute("messConList", messlist);

        ConversationDao conDao = new ConversationDao();
        Conversation conversation = conDao.getConversationById(id);
        request.setAttribute("conversation_infor", conversation);

        int numberFriend = conDao.getNumberFriend(id);
        Conversation_user cu = conDao.getConversationUserById(email, id);
        if (cu != null && cu.isIsAdmin()) {
            request.setAttribute("isAdmin", cu);
        }
        FriendDao dao = new FriendDao();
        List<Map<String, String>> friendlist = dao.viewFriendConversation(id);
        request.setAttribute("friendGroup", friendlist);
        request.setAttribute("number", numberFriend);
        request.setAttribute("email", email);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    protected void updateAvatarConversation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String avatar = request.getParameter("newAvatar");
        ConversationDao conDao = new ConversationDao();
        conDao.updateAvatarConversation(avatar, cid);
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMessCon&cid=" + cid);

    }

    protected void updateNameConversation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String name = request.getParameter("newName");
        ConversationDao conDao = new ConversationDao();
        conDao.updateNameConversation(name, cid);
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMessCon&cid=" + cid);

    }

    protected void inviteFriendToGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String sender = account.getEmail();
        String cid = request.getParameter("cid");
        String friend = request.getParameter("friend");
        ConversationDao con = new ConversationDao();
        con.insertFriendToConversation(cid, friend);
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMessCon&cid=" + cid);
    }

    protected void chatOneFriend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String otherUser = request.getParameter("friend_email");
        String text = request.getParameter("textN");
        MessageDao messDao = new MessageDao();
        messDao.insert(email, otherUser, text);
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMess&friend_email=" + otherUser);
    }

    protected void chatGroup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String sender = account.getEmail();
        String cid = request.getParameter("cid");
        String text = request.getParameter("textN");
        MessageDao messDao = new MessageDao();
        messDao.insertMessConversation(sender, text, cid);
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMessCon&cid=" + cid);
    }

    protected void loadMessage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otherUser = (String) request.getSession().getAttribute("friend_email");
        String mid = request.getParameter("mid");
        String text = request.getParameter("textM");
        request.setAttribute("content", text);
        request.setAttribute("mid", mid);

        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMess&friend_email=" + otherUser);
    }

    protected void editMessage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otherUser = (String) request.getSession().getAttribute("friend_email");
        String id = request.getParameter("mid");
        String text = request.getParameter("textN");
        MessageDao messDao = new MessageDao();
        response.sendRedirect(request.getContextPath() + "/Chat?action=loadMess&friend_email=" + otherUser);
    }

    protected void deleteMessage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String messid = request.getParameter("messid");
        if (messid != null) {
            String conid = (String) request.getSession().getAttribute("cid");
            MessageDao messDao = new MessageDao();
            messDao.deleteMessage(messid);
            response.sendRedirect(request.getContextPath() + "/Chat?action=loadMessCon&cid=" + conid);
        } else {
            String otherUser = (String) request.getSession().getAttribute("friend_email");
            String id = request.getParameter("mid");
            MessageDao messDao = new MessageDao();
            messDao.deleteMessage(id);
            response.sendRedirect(request.getContextPath() + "/Chat?action=loadMess&friend_email=" + otherUser);
        }
    }

    protected void deleteGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String conid = request.getParameter("cid");
        ConversationDao con = new ConversationDao();
        con.deleteConversation(conid);
        response.sendRedirect(request.getContextPath() + "/Chat?action=load");

    }

    protected void leaveOutGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String conid = request.getParameter("cid");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        ConversationDao con = new ConversationDao();
        con.leaveConversation(conid, email);
        response.sendRedirect(request.getContextPath() + "/Chat?action=load");

    }
}
