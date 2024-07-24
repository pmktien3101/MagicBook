<%-- 
    Document   : chat
    Created on : Feb 11, 2024, 11:56:25 AM
    Author     : MSI PC
--%>

<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
            integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>Chat</title>

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/chat.css" rel="stylesheet">
        <style>
            .avatar-header-3{
                width: 40px;
                height: 40px;
                border-radius: 100%;
                border: 1px;
                border-style: solid;
                margin-right: 30px;
            }
        </style>
    </head>

    <body>
        <%
            String avatar = request.getAttribute("avatar") + "";
            avatar = (avatar.equals("null") || avatar.isEmpty() ? "./assets/defaultAvatar.jpg" : avatar);
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
        %>
        <div class="header">
            <div class="headerh">
                <div class="header-1">
                    <div class="logo"><img src="assets/logoMagicBook.png" alt=""></div>
                    <div class="search"><i class="far fa-search"></i></div>
                </div>



                <div class="header-3">

                    <a href="AccountPage" class="avatar-home-css">
                        <img class="avatar-header-3" src="${avatar.avatar}" alt="avatar">                   
                    </a>
                </div>
            </div>
        </div>
        <div class="container">

            <div class="conversation-container">
                <div class="left-side">
                    <h1>Chats</h1>
<!--                    <div class="tab-control">

                        <a href="Chat"> <i class="fa fa-comment active"></i></a>
                        <form action="Chat" method="Post">
                            <input type="hidden" name="action" value="loadManyConversation">
                            <button type="submit"> <i class="fa fa-comments"></i></button>
                        </form>
                    </div>-->
                    <c:if test="${not empty conList}">
                        <!--                        <form action="Chat" method="Post">-->
                        <!--<input type="hidden" name="action" value="loadFriend">-->
                        <button type="submit" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createConversationModal">
                            Create New Conversation <i class="fas fa-plus"></i>
                        </button>
                        <!--</form>-->
                    </c:if>  

                    
                    <div class="list-user">
                        <ul>
                            <c:choose>
                                <c:when test="${not empty friendlist}">
                                    <c:forEach items="${friendlist}" var="friend" varStatus="loop">
                                        <form action="Chat" method="Post">
                                            <input type="hidden" name="action" value="loadMess">
                                            <input type="hidden" name="friend_email" value="${friend.friend_email}">
                                            <button type="submit">
                                                <li>
                                                    <div class="user-contain">
                                                        <div class="user-img">
                                                            <img src="${friend.avatar}" alt="Image of user">
                                                        </div>
                                                        <div class="user-info">
                                                            <h4>
                                                                <a href="Friend?action=loadFriend&fid=${friend.friend_email}">
                                                                    ${friend.firstname} ${friend.lastname}
                                                                </a>
                                                            </h4>
                                                            <span class="user-last-message">${lastMessages[loop.index]}</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </button>
                                        </form>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${not empty friendGroup}">
                                    <c:forEach items="${friendGroup}" var="friend" varStatus="loop">
                                        <form action="Chat" method="Post">
                                            <input type="hidden" name="action" value="loadMess">
                                            <input type="hidden" name="friend_email" value="${friend.friend_email}">
                                            <button type="submit">
                                                <li>
                                                    <div class="user-contain">
                                                        <div class="user-img">
                                                            <img src="${friend.avatar}" alt="Image of user">
                                                        </div>
                                                        <div class="user-info">
                                                            <h4>
                                                                <a href="Friend?action=loadFriend&fid=${friend.friend_email}">
                                                                    ${friend.firstname} ${friend.lastname}
                                                                </a>
                                                            </h4>
                                                        </div>
                                                    </div>
                                                </li>
                                            </button>
                                        </form>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${not empty conList}">
                                    <c:forEach items="${conList}" var="c">
                                        <form action="Chat" method="Post">
                                            <input type="hidden" name="action" value="loadConMess">
                                            <input type="hidden" name="cid" value="${c.id}">
                                            <button type="submit">
                                                <li>
                                                    <div class="user-contain">
                                                        <div class="user-img">
                                                            <img src="${c.avatar}" alt="Image of user">
                                                        </div>
                                                        <div class="user-info">
                                                            <h4>${c.name}</h4>
                                                            <span class="user-last-message">tessst</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </button>
                                        </form>
                                    </c:forEach>
                                </c:when>

                            </c:choose>
                        </ul>
                    </div>


                </div>
                <div class="right-side active">
                    <div class="user-contact">
                        <div class="back">
                            <i class="fa fa-arrow-left"></i>
                        </div>
                        <div class="user-contain">
                            <c:forEach items="${infor}" var="i">
                                <div class="user-img">
                                    <img src="${i.avatar}" alt="Image of user">
                                    <div class="user-img-dot"></div>
                                </div>
                                <div class="user-info">
                                    <span class="user-name">${i.firstname} ${i.lastname}</span>
                                </div>
                            </c:forEach>
                            <c:if test="${not empty conList}">
                                <div class="user-img">
                                    <img src="${conversation_infor.avatar}" alt="Image of user">
                                    <div class="user-img-dot"></div>
                                </div>
                                <div class="user-info">
                                    <span class="user-name">${conversation_infor.name}</span>
                                </div>
                            </c:if>
                        </div>
                        <c:if test="${not empty conList || not empty friendGroup}">
                            <div class="invite-user">
                                <span class="total-invite-user">${number} participants</span>
                            </div>
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="inviteDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    Invite
                                </button>

                                <div class="dropdown-menu" aria-labelledby="inviteDropdown">
                                    <c:if test="${not empty friendlistt}">
                                        <form action="Chat" method="POST">
                                            <input type="hidden" name="action" value="InsertFriend">
                                            <select name="friend" id="friendSelect" class="form-select">
                                                <c:forEach items="${friendlistt}" var="f">
                                                    <option value="${f.friend_email}">${f.firstname} ${f.lastname}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="hidden" name="cid" value="${cid}">
                                            <button type="submit" class="btn btn-primary">Add to Conversation</button>
                                        </form>
                                    </c:if>
                                </div>

                            </div>

                            <div class="setting">
                                <i class="fa fa-cog" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false"></i>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <form action="Chat" method="Post">
                                        <button type="button" class="dropdown-item" class="edit-btn" data-bs-toggle="modal" data-bs-target="#avatarModal">Update Avatar</button>
                                    </form>
                                    <form action="Chat" method="Post">
                                        <button type="button" class="dropdown-item" class="edit-btn" data-bs-toggle="modal" data-bs-target="#nameModal">Update Name</button>
                                    </form>
                                    <c:if test="${empty isAdmin}">
                                        <form action="Chat" method="Post">
                                            <input type="hidden" name="cid" value="${cid}">
                                            <input type="hidden" name="action" value="leaveGroup">
                                            <button type="submit" class="dropdown-item">Leave Group</button>
                                        </form>
                                    </c:if>
                                    <form action="Chat" method="Post">
                                        <input type="hidden" name="cid" value="${cid}">
                                        <input type="hidden" name="action" value="viewFriend">
                                        <button type="submit" class="dropdown-item">View participants</button>
                                    </form>
                                    <c:if test="${not empty isAdmin}">
                                        <form action="Chat" method="Post">
                                            <input type="hidden" name="cid" value="${cid}">
                                            <input type="hidden" name="action" value="deleteGroup">
                                            <button type="submit" class="dropdown-item">Delete Group</button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>

                        </c:if>

                    </div>
                    <div class="list-messages-contain">
                        <ul class="list-messages">

                            <c:forEach items="${messConList}" var="c">
                                <c:choose>
                                    <c:when test="${c.sender.email != email}">
                                        <li>
                                            <div class="message">
                                                <div class="edit-icon">
                                                    <div class="dropdown">
                                                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            <i class="fas fa-ellipsis-h"></i>
                                                        </button>

                                                    </div>
                                                </div>
                                                <div class="message-img">
                                                    <img src="user-male.jpg" alt="">
                                                </div>

                                                <div class="message-text">${c.content}</div>

                                            </div>
                                            <span>${c.created_at}</span>
                                        </li>
                                    </c:when>
                                    <c:when test="${c.sender.email == email}">
                                        <li>
                                            <div class="message right">
                                                <div class="dropdown">
                                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <i class="fas fa-ellipsis-h"></i>
                                                    </button>
                                                    <c:if test="${not empty messConList}">
                                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                            <form action="Chat" method="Post">
                                                                <button type="button" class="dropdown-item" class="edit-btn" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>
                                                            </form>
                                                            <form action="Chat" method="Post">
                                                                <input type="hidden" name="cid" value="${cid}">
                                                                <input type="hidden" name="messid" value="${c.id}">
                                                                <input type="hidden" name="action" value="deleteM">
                                                                <button type="submit">
                                                                    Delete
                                                                    <i class="fas fa-trash-alt"></i>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <div class="message-img">
                                                    <img src="user-male.jpg" alt="">
                                                </div>
                                                <div class="message-text">${c.content}</div>
                                            </div>
                                            <div class="timestamp" style="margin-left: 500px">${c.created_at}</div> 
                                        </li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>


                            <c:forEach items="${messlist}" var="m">
                                <c:choose>
                                    <c:when test="${m.sender.email==friend_email}">
                                        <li>
                                            <div class="message">
                                                <div class="edit-icon">
                                                    <div class="dropdown">
                                                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            <i class="fas fa-ellipsis-h"></i>
                                                        </button>

                                                    </div>
                                                </div>
                                                <div class="message-img">
                                                    <img src="user-male.jpg" alt="">
                                                </div>

                                                <div class="message-text">${m.content}</div>

                                            </div>
                                            <span>${m. created_at}</span>
                                        </li>
                                    </c:when>
                                    <c:when test="${m.sender.email!=friend_email}">
                                        <li>
                                            <div class="message right">
                                                <div class="dropdown">
                                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <i class="fas fa-ellipsis-h"></i>
                                                    </button>
                                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">                                                       
                                                        <form action="Chat" method="Post">
                                                            <input type="hidden" name="mid" value="${m.id}">
                                                            <input type="hidden" name="action" value="deleteM">
                                                            <button type="submit">
                                                                Delete
                                                                <i class="fas fa-trash-alt"></i>
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                                <div class="message-img">
                                                    <img src="user-male.jpg" alt="">
                                                </div>
                                                <div class="message-text">${m.content}</div>
                                            </div>
                                            <div class="timestamp" style="margin-left: 500px">${m.created_at}</div> 
                                        </li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>

                    </div>
                    <%--<c:if test="${not empty messlist}">--%>
                        <form action="Chat" method="Post" class="form-send-message">
                            <input type="hidden" name="action" value="chatOne">
                            <input type="hidden" name="friend_email" value="${friend_email}">
                            <input type="text" name="textN" class="txt-input" placeholder="Type message...">
                            <label class="btn btn-image" for="attach"><i class="fa fa-file"></i></label>
                            <input type="file" multiple id="attach">
                            <label class="btn btn-image" for="image"><i class="fa fa-file-image-o"></i></label>
                            <input type="file" accept="image/*" multiple id="image">
                            <button type="submit" class="btn btn-send"><i class="fa fa-paper-plane"></i></button>
                        </form>
                    <%--</c:if>--%>
<!--                    <form action="Chat" method="Post" class="form-send-message">
                        <input type="hidden" name="action" value="chatGroup">
                        <input type="hidden" name="cid" value="${cid}">
                        <input type="text" name="textN" class="txt-input" placeholder="Type message...">
                        <label class="btn btn-image" for="attach"><i class="fa fa-file"></i></label>
                        <input type="file" multiple id="attach">
                        <label class="btn btn-image" for="image"><i class="fa fa-file-image-o"></i></label>
                        <input type="file" accept="image/*" multiple id="image">
                        <button type="submit" class="btn btn-send"><i class="fa fa-paper-plane"></i></button>
                    </form>-->
                </div>
            </div>
        </div>

        <!-- Modal Edit Avatar-->
        <form action="Chat" method="post">
            <div class="edit-avatar  modal fade" id="editModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content modal-edit-avatar">
                        <div class="modal-header avatar-edit-header">
                            <h4 class="modal-title">Edit Message</h4>
                        </div>
                        <div class="modal-body avatar-edit-body">
                            <!-- <label for="file-upload" class="user-file-upload">
                            Add new Picture <i class="fas fa-image"></i>
                        </label>
                        <img src="./assets/bgr2.jpg" alt="postimg">
                        <input id="file-upload" type="file" /> -->
                            Add new message <input type="text" name="newM" value="${content}">
                        </div>
                        <div class="modal-footer avatar-edit-footer">
                            <!-- <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
                            <button type="submit" class="btn btn-default">Save</button>
                            <input type="hidden" name="action" value="EditM">
                            <input type="hidden" name="mid" value="${mid}">
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!-- Modal Edit Avatar-->
        <form action="Chat" method="post">
            <div class="edit-avatar  modal fade" id="avatarModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content modal-edit-avatar">
                        <div class="modal-header avatar-edit-header">
                            <h4 class="modal-title">Change avatar</h4>
                        </div>
                        <div class="modal-body avatar-edit-body">
                            <!-- <label for="file-upload" class="user-file-upload">
                            Add new Picture <i class="fas fa-image"></i>
                        </label>
                        <img src="./assets/bgr2.jpg" alt="postimg">
                        <input id="file-upload" type="file" /> -->
                            <img src="${conversation_infor.avatar}" alt=""> <br>
                            Add new avatar <input type="text" name="newAvatar" placeholder="Enter link of the avatar">
                        </div>
                        <div class="modal-footer avatar-edit-footer">
                            <!-- <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
                            <button type="submit" class="btn btn-default">Save</button>
                            <input type="hidden" name="action" value="changeAvatar">
                            <input type="hidden" name="cid" value="${cid}">

                        </div>
                    </div>
                </div>
            </div>
        </form>
        <!-- Modal Edit Avatar-->
        <form action="Chat" method="post">
            <div class="edit-avatar  modal fade" id="nameModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content modal-edit-avatar">
                        <div class="modal-header avatar-edit-header">
                            <h4 class="modal-title">Change Name</h4>
                        </div>
                        <div class="modal-body avatar-edit-body">
                            <!-- <label for="file-upload" class="user-file-upload">
                            Add new Picture <i class="fas fa-image"></i>
                        </label>
                        <img src="./assets/bgr2.jpg" alt="postimg">
                        <input id="file-upload" type="file" /> -->
                            <h4>${conversation_infor.name}</h4>
                            Enter new name of conversation <input type="text" name="newName" placeholder="Enter new Name">
                        </div>
                        <div class="modal-footer avatar-edit-footer">
                            <!-- <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
                            <button type="submit" class="btn btn-default">Save</button>
                            <input type="hidden" name="action" value="changeName">
                            <input type="hidden" name="cid" value="${cid}">

                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!-- Modal -->
        <div class="modal fade" id="createConversationModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Create New Conversation</h5>
                    </div>
                    <div class="modal-body">
                        <form action="Chat" method="Post">
                            <input type="hidden" name="action" value="createConversation">
                            <div class="mb-3">
                                <label for="conversationName" class="col-form-label">Conversation Name:</label>
                                <input type="text" class="form-control" id="conversationName" name="name">
                            </div>
                            <div class="mb-3">
                                <label for="conversationAvatar" class="col-form-label">Conversation Avatar:</label>
                                <input type="text" class="form-control" id="conversationAvatar" name="avatar">
                            </div>
                            <%
                                System.out.println("Friend list: " + request.getAttribute("friendlist"));
                            %>

                            <div>
                                <label for="conversationFriend" class="col-form-label">Conversation Friends:</label>
                                <c:if test="${not empty friendlistt}">
                                    <select name="friend" id="friendSelect" class="form-select">
                                        <c:forEach items="${friendlistt}" var="f">
                                            <option value="${f.friend_email}">${f.firstname} ${f.lastname}</option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </div>

                            <button type="submit" class="btn btn-primary">Create</button> 
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>

            var back = document.querySelector(".back");
            var rightSide = document.querySelector(".right-side");
            var leftSide = document.querySelector(".left-side");
            var conversation = document.querySelectorAll(".user-contain");

            var attachFile = document.getElementById("attach");
            var imageFile = document.getElementById("image");
            var file = document.querySelector(".list-file");
            var listFile = [];
            var typeFile = "image";
            var deleteAttach = document.querySelectorAll(".delete-attach");

            if (back) {
                back.addEventListener("click", function () {
                    rightSide.classList.remove("active");
                    leftSide.classList.add("active");
                    listFile = [];
                    renderFile();
                });
            }

            function setDeleteAttach() {
                deleteAttach = document.querySelectorAll(".delete-attach");

            }

            function renderFile(typeFile) {
                let listFileHTML = "";
                let idx = 0;

                if (typeFile == "image") {
                    for (const file of listFile) {
                        listFileHTML += '<li><img src="' + URL.createObjectURL(file) + '" alt="Image file"><span data-idx="' + (idx) + '" onclick="deleteFile(' + idx + ')" class="delete-attach">X</span></li>';
                        idx++;
                    }
                } else {
                    for (const file of listFile) {
                        listFileHTML += '<li><div class="file-input">' + file.name + '</div><span data-idx="' + (idx) + '" onclick="deleteFile(' + idx + ')" class="delete-attach">X</span></li>';
                        idx++;
                    }
                }


                if (listFile.length == 0) {
                    file.innerHTML = "";
                    file.classList.remove("active");
                } else {
                    file.innerHTML = listFileHTML;
                    file.classList.add("active");
                }

                deleteAttach = document.querySelectorAll(".delete-attach");
            }

            conversation.forEach(function (element, index) {
                element.addEventListener("click", function () {
                    rightSide.classList.add("active");
                    leftSide.classList.remove("active");
                });
            });

            attachFile.addEventListener("change", function (e) {
                let filesInput = e.target.files;

                for (const file of filesInput) {
                    listFile.push(file);
                    console.log(file);
                }

                typeFile = "file";
                renderFile("attach");

                this.value = null;
            });

            imageFile.addEventListener("change", function (e) {
                let filesImage = e.target.files;

                for (const file of filesImage) {
                    listFile.push(file);
                    console.log(file);
                }

                typeFile = "image";

                renderFile("image");

                this.value = null;
            });


            function deleteFile(idx) {
                if (!isNaN(idx))
                    listFile.splice(idx, 1);

                renderFile(typeFile);
            }
        </script>     
        <script type="text/javascript">
            function reloadPage
                    () {
                location.rel
                oad(); // Tải lại trang
            }
            // Tự động gọi hàm reloadPage sau 5 giây
            setTimeout(reloadPage, 5000);
        </script>

        <%}%>
    </body>

</html>