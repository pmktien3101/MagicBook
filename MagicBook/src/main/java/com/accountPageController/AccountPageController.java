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
import dao.PostCommentDAO;
import dao.PostDao;
import dao.ReportCommentDao;
import dao.ReportPostDao;
import dao.TagDAO;
import java.io.File;
import model.AccountProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Emotion;
import model.Post;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author MSI PC
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10, // 10MB
        fileSizeThreshold = 1024 * 1024, // 1MB
        location = "/tmp")

public class AccountPageController extends HttpServlet {

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
            //them vô để hiện image
            List<Post> posts = postdao.getPostByEmail(email);
            request.setAttribute("posts", posts);
            List<Post> postss = postdao.getImageByEmail(email);
            request.setAttribute("images", postss);
//        List<Post> posts = postdao.getPostByEmail(email);
//        request.setAttribute("posts", posts);
            AccountProfileDAO ap = new AccountProfileDAO();
            AccountProfile a = ap.getAccountProfile(email);
            FriendDao friendDao = new FriendDao();
            int number = friendDao.getNumberFriend(email);
            request.setAttribute("numberFriend", number);
            request.setAttribute("avatar", a);

            List<Post> allPosts = postdao.getAllPostAndSharePost(email);
            request.setAttribute("allPosts", allPosts);

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
            EmotionDAO eDao = new EmotionDAO();
            ArrayList<Emotion> listEmotion = new ArrayList<>();
            listEmotion = eDao.loadEmotion(email);
            request.setAttribute("listEmotion", listEmotion);
            NotificationDao nDao = new NotificationDao();
            int numberN = nDao.getNumberNo(email);
            request.setAttribute("number", numberN);
            request.getRequestDispatcher("accountPage.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AccountPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("createPost".equals(action)) {
            createPost(request, response);
        } else if ("loadPostById".equals(action)) {
            loadPostById(request, response);
        } else if ("editPost".equals(action)) {
            editPost(request, response);
        } else if (action.equals("changeDetail")) {
            changeDetail(request, response);
        } else if (action.equals("changeAbout")) {
            changeAboutMe(request, response);
        } else if (action.equals("deletePost")) {
            deletePost(request, response);
        } else if (action.equals("deleteAccount")) {
            deleteAccount(request, response);
        } else if (action.equals("sharePost")) {
            sharePost(request, response);
        } else if (action.equals("deleteShare")) {
            deleteShare(request, response);
        } else if (action.equals("editSharePost")) {
            editSharePost(request, response);
        } else if (action.equals("loadSharePostById")) {
            loadSharePostById(request, response);
        } else if (action.equals("postCmt")) {
            postCmt(request, response);
        } else if (action.equals("deleteCmt")) {
            deleteCmt(request, response);
        } else if (action.equals("editCmt")) {
            editCmt(request, response);
        } else if (action.equals("deletePostHome")) {
            deletePostHome(request, response);
        }
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Configure a repository (to ensure a secure temp location is used)
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir"); // Or "javax.servlet.context.tempdir" for javax
            factory.setRepository(repository);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            // Process the uploaded items
            String caption = null;
            String privacy = null;
            String fileName = null;
            HashMap<String, String> fields = new HashMap<>();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Trường dữ liệu thông thường
                    fields.put(item.getFieldName(), item.getString());
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                } else {
                    // Trường là file được tải lên
                    fileName = item.getName();
                    if (fileName == null || fileName.equals("")) {
                        break;
                    } else {
                        Path path = Paths.get(fileName);
                        String storePath = servletContext.getRealPath("/uploads");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        // Kiểm tra xem tệp tin đã tồn tại chưa
                        if (uploadFile.exists()) {
                            // Nếu tệp tin đã tồn tại, tạo một tên mới cho tệp tin hoặc ghi đè lên tệp tin hiện có
                            String newFileName = generateNewFileName(path.getFileName().toString());
                            uploadFile = new File(storePath + "/" + newFileName);
                        }
                        item.write(uploadFile);
                        System.out.println(storePath + "/" + path.getFileName());
                    }
                }
            }

            // Sau khi đã xử lý các trường dữ liệu và file, thực hiện lưu bài post
            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            caption = fields.get("new-post-content");
            privacy = fields.get("privacy");
            if (fileName == null || fileName.equals("")) {
                fileName = "";
            } else {
                fileName = "./uploads/" + fileName;
            }
            PostDao dao = new PostDao();
            int result = dao.insert(caption, fileName, privacy, email);
            if (result > 0) {
                response.sendRedirect("AccountPage");
            } else {
                request.setAttribute("error", "Failed to add the new post");
                request.getRequestDispatcher("accountPage.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("pid");
        PostDao dao = new PostDao();
        Post post = dao.getPostById(id);
        String privacy = post.getPrivacy();

        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        request.setAttribute("friendList", friendList);

        request.setAttribute("privacy", privacy);
        request.setAttribute("detailPost", post);
        request.getRequestDispatcher("editPost.jsp").forward(request, response);
    }

    private void editPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Configure a repository (to ensure a secure temp location is used)
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir"); // Or "javax.servlet.context.tempdir" for javax
            factory.setRepository(repository);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            // Process the uploaded items
            String id = null;
            String caption = null;
            String privacy = null;
            String fileName = null;
            HashMap<String, String> fields = new HashMap<>();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Trường dữ liệu thông thường
                    fields.put(item.getFieldName(), item.getString());
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                } else {
                    // Trường là file được tải lên
                    fileName = item.getName();
                    if (fileName == null || fileName.equals("")) {
                        break;
                    } else {
                        Path path = Paths.get(fileName);
                        String storePath = servletContext.getRealPath("/uploads");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        if (uploadFile.exists()) {
                            // Nếu tệp tin đã tồn tại, tạo một tên mới cho tệp tin hoặc ghi đè lên tệp tin hiện có
                            String newFileName = generateNewFileName(path.getFileName().toString());
                            uploadFile = new File(storePath + "/" + newFileName);
                        }
                        item.write(uploadFile);
                        System.out.println(storePath + "/" + path.getFileName());
                    }
                }
            }

            // Sau khi đã xử lý các trường dữ liệu và file, thực hiện lưu bài post
            id = fields.get("pid");
            caption = fields.get("new-post-content");
            privacy = fields.get("privacy");

            PostDao dao = new PostDao();
            Post p = dao.getPostById(id);
            if (fileName == null || fileName.equals("")) {
                fileName = p.getImage();
            } else {
                fileName = "./uploads/" + fileName;
            }

            Account account = (Account) request.getSession().getAttribute("account");
            String email = account.getEmail();
            FriendDao friendDAO = new FriendDao();
            List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
            String[] listFriendCmt = request.getParameterValues("listFriendID");
            System.out.println("Come on: " + Arrays.toString(listFriendCmt));
            if (listFriendCmt != null && listFriendCmt.length > 0) {
                try {
                    // Phân tích cú pháp chuỗi JSON
                    JSONArray jsonArray = new JSONArray(listFriendCmt[0]);

                    // Duyệt qua mảng JSON
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String tagged_id = jsonArray.getString(i);
                        String tagged_email = friendList.get(Integer.parseInt(tagged_id)).get("friend_email");
                        TagDAO tagDAO = new TagDAO();
                        tagDAO.insert(email, tagged_email, id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace(); // Xử lý hoặc ghi log cho ngoại lệ JSONException
                }
            }

            dao.updatePost(caption, fileName, privacy, id);
            response.sendRedirect("AccountPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        AccountDao accDAO = new AccountDao();
        Account accDTO = accDAO.getByEmail(account);
        AccountProfileDAO profileDAO = new AccountProfileDAO();
        AccountProfile profileDTO = profileDAO.getAccountProfile(email);

        request.setAttribute("firstname", accDTO.getFirstName());
        request.setAttribute("lastname", accDTO.getLastName());
        request.setAttribute("dob", accDTO.getDob());
        request.setAttribute("phone", accDTO.getPhone());
        request.setAttribute("gender", accDTO.getGender());
        request.setAttribute("avatar", profileDTO.getAvatar());
        request.setAttribute("background", profileDTO.getBackground());
        request.getRequestDispatcher("changeDetail.jsp").forward(request, response);
    }

    private void changeAboutMe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String about = request.getParameter("about");

        AccountDao accDAO = new AccountDao();
        Account accDTO = accDAO.getByEmail(account);
        AccountProfileDAO profileDAO = new AccountProfileDAO();
        AccountProfile profileDTO = profileDAO.getAccountProfile(email);
        profileDAO.updateAboutMe(email, about);
        response.sendRedirect("AccountPage");
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("pid");
        Post p = new Post();
        PostDao dao = new PostDao();
        PostCommentDAO pDao = new PostCommentDAO();
        EmotionDAO eDao = new EmotionDAO();
        TagDAO tagDAO = new TagDAO();
        ReportCommentDao rcDAO = new ReportCommentDao();
        rcDAO.deleteReportCommentByPostID(id);
        tagDAO.deleteAllTagOfPost(id);
        pDao.deleteAllCommentsOfPost(id);
        eDao.deleteAllEmotionsOfPost(id);
        ReportPostDao rDao = new ReportPostDao();
        rDao.deleteReport(id);
        //Muốn deleteAllShareOfPost phải delete cmt share emotion của post :<
        //Lấy all post có originalID=PostID
        List<Post> list = dao.getAllSharePostOfPost(id);
        for (Post post : list) {
            //
            rDao.deleteReport(post.getId() + "");
            //
            rcDAO.deleteReportCommentByPostID(post.getId() + "");
            //delete all tag of share post
            tagDAO.deleteAllTagOfPost(post.getId() + "");
            //delete all cmt of share post
            pDao.deleteAllCommentsOfPost(post.getId() + "");
            //delete all emotion of share post
            eDao.deleteAllEmotionsOfPost(post.getId() + "");
        }
        dao.deleteAllSharePostOfPost(id);
        dao.deletePost(id);
        response.sendRedirect("AccountPage");
    }
    
    private void deletePostHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("pid");
        Post p = new Post();
        PostDao dao = new PostDao();
        PostCommentDAO pDao = new PostCommentDAO();
        EmotionDAO eDao = new EmotionDAO();
        TagDAO tagDAO = new TagDAO();
        ReportCommentDao rcDAO = new ReportCommentDao();
        rcDAO.deleteReportCommentByPostID(id);
        tagDAO.deleteAllTagOfPost(id);
        pDao.deleteAllCommentsOfPost(id);
        eDao.deleteAllEmotionsOfPost(id);
        ReportPostDao rDao = new ReportPostDao();
        rDao.deleteReport(id);
        //Muốn deleteAllShareOfPost phải delete cmt share emotion của post :<
        //Lấy all post có originalID=PostID
        List<Post> list = dao.getAllSharePostOfPost(id);
        for (Post post : list) {
            rDao.deleteReport(post.getId() + "");
            //
            rcDAO.deleteReportCommentByPostID(post.getId() + "");
            //delete all tag of share post
            tagDAO.deleteAllTagOfPost(post.getId() + "");
            //delete all cmt of share post
            pDao.deleteAllCommentsOfPost(post.getId() + "");
            //delete all emotion of share post
            eDao.deleteAllEmotionsOfPost(post.getId() + "");
        }
        dao.deleteAllSharePostOfPost(id);
        dao.deletePost(id);
        response.sendRedirect("Home");
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        AccountDao dao = new AccountDao();
        dao.deleteAccount(email);
        response.sendRedirect("login.jsp");
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

    private void deleteShare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("postShareID");
        Post p = new Post();
        PostDao dao = new PostDao();
        PostCommentDAO pDao = new PostCommentDAO();
        EmotionDAO eDao = new EmotionDAO();
        TagDAO tagDAO = new TagDAO();
        pDao.deleteAllCommentsOfPost(id);
        eDao.deleteAllEmotionsOfPost(id);
        tagDAO.deleteAllTagOfPost(id);
        dao.deleteShare(id);
        response.sendRedirect("AccountPage");
    }

    private void editSharePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("postShareID");
        String caption = request.getParameter("new-post-share-content");
        String privacy = request.getParameter("privacy");

        PostDao dao = new PostDao();
        dao.updateSharePost(caption, privacy, id);
        response.sendRedirect("AccountPage");
    }

    private void loadSharePostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("postShareID");
//        String email = request.getParameter("postShareEmail");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);
        request.setAttribute("friendList", friendList);
        PostDao dao = new PostDao();
        Post post = dao.getPostShareById(id);
        String privacy = post.getPrivacy();
        request.setAttribute("privacy", privacy);
        request.setAttribute("detailPost", post);
        request.getRequestDispatcher("editSharePost.jsp").forward(request, response);
    }

    private void postCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String postID = request.getParameter("postID");
        String textCmt = request.getParameter("textCmt");
        String userCmt = request.getParameter("userCmt");
        FriendDao friendDAO = new FriendDao();
        List<Map<String, String>> friendList = friendDAO.getFriendByEmail(email);

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
        response.sendRedirect("AccountPage");

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
        response.sendRedirect("AccountPage");
    }

    private void editCmt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postID = request.getParameter("pid");
        int pid = Integer.parseInt(postID);
        String cmtID = request.getParameter("cmtid");
        int cmtid = Integer.parseInt(cmtID);
        String textCmt = request.getParameter("textComment");

        PostCommentDAO pcDAO = new PostCommentDAO();
        pcDAO.editCmt(pid, cmtid, textCmt);
        response.sendRedirect("AccountPage");

    }

    private String generateNewFileName(String fileName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String extension = FilenameUtils.getExtension(fileName);
        return "new_file_" + timeStamp + "_" + generateUniqueString() + "." + extension;
    }

    private String generateUniqueString() {
        return UUID.randomUUID().toString();
    }

}
