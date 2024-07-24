/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Post;
import model.PostComment;
import utils.DatabaseUtils;

/**
 *
 * @author MSI PC
 */
public class PostDao {

    private static String INSERT_POST = "INSERT INTO POST (caption, image, privacy, email) VALUES (?, ?, ?, ?)";

    public int insert(String caption, String image, String privacy, String email) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(INSERT_POST);
            st.setString(1, caption);
            st.setString(2, image);
            st.setString(3, privacy);
            st.setString(4, email);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static String GET_POST_BY_EMAIL = "SELECT * FROM POST WHERE email=? And privacy= 'Public' Order by time DESC";

    public List<Post> getPostByEmail(String email) {
        List<Post> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_POST_BY_EMAIL)) {

            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String emaill = rs.getString("email");
                Account account = new AccountDao().getByEmail(new Account(emaill));
                int numReaction = rs.getInt("num_reaction");
                Post p = new Post(id, date, caption, image, privacy, account, numReaction);
                orders.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }
    private static String GET_POST_BY_PRIVACY = "SELECT p.*\n"
            + "FROM POST p\n"
            + "WHERE p.privacy = 'Public' AND (p.email = ? OR p.email IN (SELECT friend_email FROM FRIEND WHERE user_email = ?))\n"
            + "ORDER BY p.time DESC;";

    public List<Post> getPostByPrivacy(String email) {
        List<Post> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_POST_BY_PRIVACY)) {
            EmotionDAO eDao = new EmotionDAO();

            st.setString(1, email);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String emaill = rs.getString("email");
                Account account = new AccountDao().getByEmail(new Account(emaill));
                int numReaction = rs.getInt("num_reaction");
                Post p = new Post(id, date, caption, image, privacy, numReaction);
                orders.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }
    private static String getPostByID = "SELECT * FROM POST WHERE id = ?";

    public Post getPostById(String id) {
        Post result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getPostByID)) {

            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt("id");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String email = rs.getString("email");
                Account account = new AccountDao().getByEmail(new Account(email));
                result = new Post(postId, caption, image, privacy, account);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private static String getPostID = "SELECT * FROM POST WHERE id = ?";

    public Post getPostById(int id) {
        Post result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getPostID)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt("id");
                Date time = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String email = rs.getString("email");
                Account account = new AccountDao().getByEmail(new Account(email));
                result = new Post(postId, time, caption, image, privacy, account);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    private static String UPDATE_P = "UPDATE POST SET caption=?, image=?,privacy=? WHERE id=?";

    public void updatePost(String caption, String image, String privacy, String id) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_P);
            st.setString(1, caption);
            st.setString(2, image);
            st.setString(3, privacy);
            st.setString(4, id);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String SELECT_IMAGE = "SELECT p.image, p.privacy \n"
            + "FROM POST p\n"
            + "JOIN Account a ON p.email = a.email\n"
            + "WHERE p.privacy = 'Public'\n"
            + "AND a.email = ?";

    public List<Post> getImageByEmail(String email) {
        List<Post> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(SELECT_IMAGE)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                String image = rs.getString("image");
                String privacy = rs.getString("privacy");

                Post p = new Post();
                p.setImage(image);
                p.setPrivacy(privacy);
                orders.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    private static String DELETE_P = "DELETE FROM POST WHERE id=?";

    public void deletePost(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_P);
            st.setString(1, id);
            result = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }

    private static String SAVE_SHARE = "INSERT INTO POST (caption, privacy, email, originalId) VALUES (?, ?, ?, ?)";

    public int saveShare(String id, String email, String privacy, String caption) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(SAVE_SHARE);
            st.setString(1, caption);
            st.setString(2, privacy);
            st.setString(3, email);
            st.setString(4, id);
            result = st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static String getPostShareByID = "SELECT * FROM POST WHERE id = ? ";

    public Post getPostShareById(String id) {
        Post result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getPostShareByID)) {

            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt("id");
                String caption = rs.getString("caption");
                String privacy = rs.getString("privacy");
                String email = rs.getString("email");
                Account account = new AccountDao().getByEmail(new Account(email));
                result = new Post(postId, caption, privacy, account);
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private static String getAllSharePostInforById = "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, "
            + "CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared, "
            + "p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime, p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage "
            + "FROM POST p "
            + "LEFT JOIN POST p2 ON p.originalId = p2.id "
            + "WHERE p.id = ? "
            + "ORDER BY time DESC ";

    public Post getAllSharePostInforById(String id) {
        Post result = null;

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getAllSharePostInforById)) {

            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int idPost = rs.getInt("id");
                Date time = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String postEmail = rs.getString("email");
                int numReaction = rs.getInt("num_reaction");
                boolean shared = rs.getString("shared").equals("true");
                int originalId = rs.getInt("originalId");
                String originalEmail = rs.getString("originalEmail");
                Date originalTime = rs.getDate("originalTime");
                String originalCaption = rs.getString("originalCaption");
                String originalPrivacy = rs.getString("originalPrivacy");
                String originalImage = rs.getString("originalImage");
                int num_shared = rs.getInt("num_shared");
                Account account1 = new AccountDao().getByEmail(new Account(postEmail));
                Account account2 = new AccountDao().getByEmail(new Account(originalEmail));
                PostCommentDAO pcDAO = new PostCommentDAO();
                List<PostComment> listCmt = pcDAO.getCmtByPostID(id + "");
                Post p = new Post(idPost, time, caption, image, privacy, account1, shared, originalId, account2, originalTime, originalCaption, originalPrivacy, originalImage, numReaction);
                p.setListCmt(listCmt);
                p.setNum_shared(num_shared);
                result = p;
            }

        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

//    
    //for AccountPage
    private static String getAllPostAndSharePost = "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, "
            + "CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared, "
            + "p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime, p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage "
            + "FROM POST p "
            + "LEFT JOIN POST p2 ON p.originalId = p2.id "
            + "WHERE p.email = ? "
            + "ORDER BY time DESC ";

    public List<Post> getAllPostAndSharePost(String email) {
        List<Post> posts = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getAllPostAndSharePost)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date time = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String postEmail = rs.getString("email");
                int numReaction = rs.getInt("num_reaction");
                boolean shared = rs.getString("shared").equals("true");
                int originalId = rs.getInt("originalId");
                String originalEmail = rs.getString("originalEmail");
                Date originalTime = rs.getDate("originalTime");
                String originalCaption = rs.getString("originalCaption");
                String originalPrivacy = rs.getString("originalPrivacy");
                String originalImage = rs.getString("originalImage");
                int num_shared = rs.getInt("num_shared");
                Account account1 = new AccountDao().getByEmail(new Account(postEmail));
                Account account2 = new AccountDao().getByEmail(new Account(originalEmail));
                PostCommentDAO pcDAO = new PostCommentDAO();
                List<PostComment> listCmt = pcDAO.getCmtByPostID(id + "");
                Post p = new Post(id, time, caption, image, privacy, account1, shared, originalId, account2, originalTime, originalCaption, originalPrivacy, originalImage, numReaction);
                p.setListCmt(listCmt);
                p.setNum_shared(num_shared);
                posts.add(p);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return posts;
    }

    private static String getTop3Posts = "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, \n"
            + "    CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared,\n"
            + "    p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime,\n"
            + "	   p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage \n"
            + "    FROM POST p \n"
            + "    LEFT JOIN POST p2 ON p.originalId = p2.id  \n"
            + "    WHERE p.id in(SELECT top 3 p.id     \n"
            + "    FROM [dbo].[POST] p\n"
            + "    LEFT JOIN [dbo].[POST] s ON p.id = s.originalId\n"
            + "    LEFT JOIN [dbo].[Emotions] e ON p.id = e.id\n"
            + "    LEFT JOIN [dbo].[Post_Comment] pc ON p.id = pc.post_id\n"
            + "    WHERE p.privacy = 'public'\n"
            + "    GROUP BY p.id order by  COUNT(DISTINCT e.id)  +   ISNULL(COUNT(s.originalId), 0) + ISNULL(COUNT(pc.cmt_id), 0) desc\n"
            + "    ) ORDER BY time DESC ";

    public List<Post> getTop3Posts() {
        List<Post> posts = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection(); PreparedStatement st = con.prepareStatement(getTop3Posts)) {

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date time = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String postEmail = rs.getString("email");
                int numReaction = rs.getInt("num_reaction");
                boolean shared = rs.getString("shared").equals("true");
                int originalId = rs.getInt("originalId");
                String originalEmail = rs.getString("originalEmail");
                Date originalTime = rs.getDate("originalTime");
                String originalCaption = rs.getString("originalCaption");
                String originalPrivacy = rs.getString("originalPrivacy");
                String originalImage = rs.getString("originalImage");
                int num_shared = rs.getInt("num_shared");
                Account account1 = new AccountDao().getByEmail(new Account(postEmail));
                Account account2 = new AccountDao().getByEmail(new Account(originalEmail));
                PostCommentDAO pcDAO = new PostCommentDAO();
                List<PostComment> listCmt = pcDAO.getCmtByPostID(id + "");
                Post p = new Post(id, time, caption, image, privacy, account1, shared, originalId, account2, originalTime, originalCaption, originalPrivacy, originalImage, numReaction);
                p.setListCmt(listCmt);
                p.setNum_shared(num_shared);
                posts.add(p);
            }

            // Add top 3 posts at the beginning of the posts list
            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return posts;
    }

    private static String DELETE_SHARE = "DELETE FROM POST WHERE id = ? ";

    public void deleteShare(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_SHARE);
            st.setString(1, id);
            result = st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }

    private static String DELETE_SHARE_BEFORE_DELETE_POST = "DELETE FROM POST WHERE originalId = ? ";

    public void deleteAllSharePostOfPost(String id) {
        int result = 0;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(DELETE_SHARE_BEFORE_DELETE_POST);
            st.setString(1, id);
            result = st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

    }

    private static String UPDATE_SHARE = "UPDATE POST SET caption=?, privacy=? WHERE id = ? ";

    public void updateSharePost(String caption, String privacy, String id) {
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(UPDATE_SHARE);
            st.setString(1, caption);
            st.setString(2, privacy);
            st.setString(3, id);
            st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }
    }

    //for HomePage
    private static String GET_POST_AND_SHARE_POST_BY_PRIVACY = "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, "
            + "CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared, "
            + "p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime, p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage "
            + "FROM POST p "
            + "LEFT JOIN POST p2 ON p.originalId = p2.id "
            + "WHERE (p.email = ? OR p.email IN (SELECT friend_email FROM FRIEND WHERE user_email = ?)) "
            + "ORDER BY time DESC ";

    public List<Post> getAllPostAndSharePostByPrivacy(String email) {
        List<Post> orders = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_POST_AND_SHARE_POST_BY_PRIVACY)) {

            st.setString(1, email);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date time = rs.getDate("time");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String postEmail = rs.getString("email");
                int numReaction = rs.getInt("num_reaction");
                boolean shared = rs.getString("shared").equals("true");
                int originalId = rs.getInt("originalId");
                String originalEmail = rs.getString("originalEmail");
                Date originalTime = rs.getDate("originalTime");
                String originalCaption = rs.getString("originalCaption");
                String originalPrivacy = rs.getString("originalPrivacy");
                String originalImage = rs.getString("originalImage");
                int num_shared = rs.getInt("num_shared");
                Account account1 = new AccountDao().getByEmail(new Account(postEmail));
                Account account2 = new AccountDao().getByEmail(new Account(originalEmail));
                PostCommentDAO pcDAO = new PostCommentDAO();
                List<PostComment> listCmt = pcDAO.getCmtByPostID(id + "");
                Post p = new Post(id, time, caption, image, privacy, account1, shared, originalId, account2, originalTime, originalCaption, originalPrivacy, originalImage, numReaction);
                p.setListCmt(listCmt);
                p.setNum_shared(num_shared);
                orders.add(p);
            }
            List<Post> listTop3 = getTop3Posts();
            for (int i = 0; i < orders.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    if (orders.get(i).getId() == listTop3.get(j).getId()) {
                        orders.remove(orders.get(i));
                    }
                }
            }
            orders.addAll(0, listTop3);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error Detail: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error Detail:" + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    private static String GET_POST_TAGGED_TO_FRIENDS_AND_PRIVACY_AND_COMMENT
            = "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, "
            + "CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared, "
            + "p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime, p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage "
            + "FROM POST p "
            + "LEFT JOIN POST p2 ON p.originalId = p2.id "
            + "JOIN TAG t ON p.id = t.post_id "
            + "JOIN Account a ON a.email = t.tagged_email "
            + "JOIN FRIEND f ON a.email = f.friend_email "
            + "WHERE f.user_email = ? AND p.email <> ? "
            + "UNION "
            + "SELECT p.id, p.time, p.num_reaction, p.caption, p.image, p.privacy, p.email, p.num_shared, "
            + "CASE WHEN p.originalId IS NULL THEN 'false' ELSE 'true' END AS shared, "
            + "p.originalId AS originalId, p2.email AS originalEmail, p2.time AS originalTime, p2.caption AS originalCaption, p2.privacy AS originalPrivacy, p2.image AS originalImage "
            + "FROM POST p "
            + "LEFT JOIN POST p2 ON p.originalId = p2.id "
            + "WHERE (p.email = ? OR p.email IN (SELECT friend_email FROM FRIEND WHERE user_email = ?)) "
            + "UNION "
            + "SELECT DISTINCT P.id, P.time, P.num_reaction, P.caption, P.image, P.privacy, P.email, P.num_shared, "
            + "'false' AS shared, NULL AS originalId, NULL AS originalEmail, NULL AS originalTime, NULL AS originalCaption, NULL AS originalPrivacy, NULL AS originalImage "
            + "FROM POST P "
            + "JOIN Post_Comment PC ON P.id = PC.post_id "
            + "JOIN Account A ON P.email = A.email "
            + "WHERE PC.user_comment IN ( "
            + "   SELECT DISTINCT friend_email "
            + "   FROM FRIEND "
            + "   WHERE user_email = ? "
            + ") "
            + "AND P.email NOT IN ( "
            + "   SELECT DISTINCT friend_email "
            + "   FROM FRIEND "
            + "   WHERE user_email = ? "
            + ") "
            + "AND P.email <> ? "
            + "AND P.privacy = 'public' "
            + "ORDER BY time DESC ";

    public List<Post> getPostTaggedToFriendsAndPrivacyAndComment(String userEmail) {
        List<Post> posts = new ArrayList<>();

        try (Connection con = DatabaseUtils.getConnection();
                PreparedStatement st = con.prepareStatement(GET_POST_TAGGED_TO_FRIENDS_AND_PRIVACY_AND_COMMENT)) {

            st.setString(1, userEmail);
            st.setString(2, userEmail);
            st.setString(3, userEmail);
            st.setString(4, userEmail);
            st.setString(5, userEmail);
            st.setString(6, userEmail);
            st.setString(7, userEmail);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date time = rs.getDate("time");
                int numReaction = rs.getInt("num_reaction");
                String caption = rs.getString("caption");
                String image = rs.getString("image");
                String privacy = rs.getString("privacy");
                String postEmail = rs.getString("email");
                boolean shared = rs.getString("shared").equals("true");
                int originalId = rs.getInt("originalId");
                String originalEmail = rs.getString("originalEmail");
                Date originalTime = rs.getDate("originalTime");
                String originalCaption = rs.getString("originalCaption");
                String originalPrivacy = rs.getString("originalPrivacy");
                String originalImage = rs.getString("originalImage");
                int num_shared = rs.getInt("num_shared");

                Account account1 = new AccountDao().getByEmail(new Account(postEmail));

                Account account2 = null;
                if (originalEmail != null) {
                    account2 = new AccountDao().getByEmail(new Account(originalEmail));
                }

                Post post = new Post(id, time, caption, image, privacy, account1, shared, originalId, account2, originalTime, originalCaption, originalPrivacy, originalImage, numReaction);

                PostCommentDAO pcDAO = new PostCommentDAO();
                List<PostComment> comments = pcDAO.getCmtByPostID(String.valueOf(id));
                post.setListCmt(comments);
                post.setNum_shared(num_shared);
                posts.add(post);
            }

            List<Post> listTop3 = getTop3Posts();
            for (int i = 0; i < posts.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    if (posts.get(i).getId() == listTop3.get(j).getId()) {
                        posts.remove(posts.get(i));
                    }
                }
            }
            posts.addAll(0, listTop3);
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public List<Post> getAllSharePostOfPost(String originalId) {
        String sql = "SELECT * FROM POST WHERE originalId = ? ";
        List<Post> list = null;
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, originalId);

            ResultSet rs = st.executeQuery();

            if (rs != null) {
                list = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Post p = new Post();
                    p.setId(id);
                    list.add(p);
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static void main(String[] args) {

        PostDao postDao = new PostDao();
//        String email = "tientien310103@gmail.com";
        String email = "quanglong3145@gmail.com";
//        String id = "3";
//        String caption = "heheeeee";
//        Post p = new Post();
//        postDao.deletePost(id);

        Post postsss = postDao.getAllSharePostInforById("1020");
        System.out.println(postsss);

        System.out.println("------");
        Post p = new Post();
        p = postDao.getPostShareById("1020");

//        postDao.updatePost(caption, "", "", id);
//        System.out.println("onee");
//        Post post = postDao.getPostById(id);
//        String p = post.getPrivacy();
//        System.out.println(p);
        // Print retrieved posts
//        List<Post> postsss = postDao.getPostByEmail(email);
//        System.out.println(postsss);
    }

}
