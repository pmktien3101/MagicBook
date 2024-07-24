<%-- 
    Document   : editSharePost
    Created on : Feb 29, 2024, 11:09:57 PM
    Author     : ADMIN
--%>

<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">

        <title>Edit Share Post</title>
        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/accountpage.css" rel="stylesheet">
        <style>
            .select-container {
                display: inline-block;
                vertical-align: middle;
            }

            #privacy {
                margin-right: 15px; 
            }

            #privacy-icon {
                display: inline-block;
                vertical-align: middle;
            }           
        </style>
    </head>
    <body>
        <%
            Object obj = session.getAttribute("account");
            Account account = null;
            if (obj != null) {
                account = (Account) obj;
            }
            if (account == null) {
        %>
        <h1 style="color:red">You are not logged into the system. Please return to the <a href="index.jsp">Login page</a>! </h1>
        <%
        } else {%>
                            
        <!-- Modal Post Share Update-->
            <div class="edit-post modal fade" id="editShareModal" role="dialog">
                <div class="modal-dialog">
                    <form action="AccountPage" method="Post">                        
                        <div class="modal-content modal-edit-content">
                            <div class="modal-header post-edit-header">
                                <h4 class="modal-title">Edit Share Post</h4>
                            </div>

                            <div class="select-container">
                                <select name="privacy" id="privacy">
                                    <option value="Public" ${privacy.equals("Public")?'selected':''}>Public</option>
                                    <option value="Private" ${privacy.equals("Private")?'selected':''}>Private</option>
                                </select>
                                <div id="privacy-icon">
                                    <!-- Nơi để hiển thị icon -->
                                </div>
                            </div>
                                
                            <div class="modal-body post-edit-body">
                                <input value="${detailPost.id}" name="postShareID" type="hidden">
                                <textarea name="new-post-share-content" id="" cols="30" rows="9">${detailPost.caption}</textarea>
                            </div>
                            <div class="modal-footer post-edit-footer">
                                <button type="submit" class="btn btn-default">Save</button>

                            </div>
                        </div>

                        <input type="hidden" name="action" value="editSharePost">
                    </form>
                </div>
            </div>
        <%}%>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var privacySelect = document.getElementById('privacy');
                var iconDiv = document.getElementById('privacy-icon');

                // Thiết lập biểu tượng ban đầu dựa trên giá trị privacy khi trang được tải
                updatePrivacyIcon();

                // Đổi biểu tượng khi privacy thay đổi
                privacySelect.addEventListener('change', updatePrivacyIcon);

                function updatePrivacyIcon() {
                    var privacy = privacySelect.value;
                    if (privacy === 'Public') {
                        iconDiv.innerHTML = '<i class="fas fa-globe"></i>';
                    } else if (privacy === 'Private') {
                        iconDiv.innerHTML = '<i class="fas fa-lock"></i>';
                    }
                }
            });
        </script>
    </body>
</html>
