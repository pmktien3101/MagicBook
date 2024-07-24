<%-- 
    Document   : editPost
    Created on : Jan 31, 2024, 2:08:42 PM
    Author     : MSI PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">

        <title>Edit Post</title>
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


        <!-- Modal Edit Post-->
        <div class="edit-post modal fade" id="editModal" role="dialog">
            <div class="modal-dialog">
                <form action="AccountPage?action=editPost" method="Post" enctype="multipart/form-data" id="postCommentForm${detailPost.id}">
                    <div class="modal-content modal-edit-content">
                        <div class="modal-header post-edit-header">
                            <h4 class="modal-title">Edit Post</h4>
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

                            <input value="${detailPost.id}" name="pid" type="hidden">
                             <textarea name="new-post-content" cols="30" rows="9" onkeyup="showSuggestions${detailPost.id}(this.value)" id="inputBox${detailPost.id}">${detailPost.caption}</textarea>
                            <div id="suggestions${detailPost.id}" style = "text-align: left; background: #ffffff; margin: -1% 8%; width: 40%">
                                <!-- Danh sách gợi ý sẽ được hiển thị ở đây -->
                            </div>
                            <img src="${detailPost.image}" alt="posting">
                            <!--<input value="${detailPost.image}" name="image" type="text">-->
                            <input id="image" name="image" type="file" class="form-control" placeholder="Enter image">

                        </div>
                        <div class="modal-footer post-edit-footer" style="margin-top: 40px">
                            <button type="submit" class="btn btn-default">Save</button>
                        </div>
                    </div>
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

        <script>
            //Tag
             let listFriendID = [];
            var friendID = null;
            <c:set var="count" value="-1" scope="page" />
            const suggestions = [
            <c:forEach items="${friendList}" var="friend" varStatus="loop">
                <c:set var="count" value="${count + 1}" scope="page" />
            '<c:out value='${count} ${friend.firstname} ${friend.lastname}'/>' <c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
            console.log(suggestions);
            function showSuggestions${detailPost.id}(inputValue) {
                const suggestionBox${detailPost.id} = document.getElementById('suggestions${detailPost.id}');

                if (inputValue.includes('@')) {
                    const nameToMatch = inputValue.slice(inputValue.lastIndexOf('@') + 1).toLowerCase();
                    const matchedNames = suggestions.filter(namefriend =>
                        namefriend.toLowerCase().includes(nameToMatch)
                    );
                    console.log(matchedNames);
                    if (matchedNames.length > 0) {
                        suggestionBox${detailPost.id}.innerHTML = matchedNames.map(namefriend => {
                            return'<div onclick="selectName${detailPost.id}(\'' + namefriend + '\')">' + namefriend.slice(2) + '</div>';
                        }
                        ).join('');
                        suggestionBox${detailPost.id}.style.display = 'block';
                    } else {
                        suggestionBox${detailPost.id}.style.display = 'none';
                    }

                } else {
                    suggestionBox${detailPost.id}.style.display = 'none';
                }

            }
            console.log(document.getElementById('inputBox${detailPost.id}').value);

            function selectName${detailPost.id}(name) {
                const inputBox${detailPost.id} = document.getElementById('inputBox${detailPost.id}');
                const currentInput${detailPost.id} = inputBox${detailPost.id}.value;
                const lastAtSignIndex${detailPost.id} = currentInput${detailPost.id}.lastIndexOf('@');
                friendID = name.charAt(0);
                if (!listFriendID.includes(friendID)) {
                    listFriendID.push(friendID);
                }
                // Nối tên đã chọn vào cuối chuỗi hiện tại của inputBox, bắt đầu từ vị trí của ký tự '@' cuối cùng
                const newValue = currentInput${detailPost.id}.slice(0, lastAtSignIndex${detailPost.id}) + '@' + name.slice(2);
                inputBox${detailPost.id}.value = newValue;
                // Ngăn chặn người dùng xóa nội dung đã thêm
                inputBox${detailPost.id}.addEventListener('keydown', function (event) {
                    if ((event.key === 'Backspace' || event.key === 'Delete') && inputBox${detailPost.id}.value === newValue) {
                        document.getElementById('suggestions${detailPost.id}').style.display = 'none';
                        event.preventDefault();
                    }
                });

                console.log('listFriendID:' + listFriendID);
                document.getElementById('suggestions${detailPost.id}').style.display = 'none';
            }
            let inputElement${detailPost.id} = document.getElementById('inputBox${detailPost.id}');

            inputElement${detailPost.id}.addEventListener('input', function () {
                if (inputElement${detailPost.id}.value === '') {
                    listFriendID = [];
                    console.log('listFriendID:' + listFriendID);
                }
            });
            document.getElementById('postCommentForm${detailPost.id}').addEventListener('submit', function (event) {
                event.preventDefault();
                // Tạo một phần tử input mới với type là hidden
                var newInput = document.createElement('input');
                newInput.type = 'hidden';
                newInput.name = 'listFriendID';
                newInput.value = JSON.stringify(listFriendID); // Chuyển đổi mảng thành chuỗi

                // Thêm phần tử input mới vào form
                this.appendChild(newInput);
                console.log(JSON.stringify(listFriendID));

                // Tiếp tục với việc submit form
                this.submit();
            });
        </script>

    </body>
</html>