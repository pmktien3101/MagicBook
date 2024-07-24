<%-- 
    Document   : accountPage
    Created on : Jan 28, 2024, 3:49:20 PM
    Author     : MSI PC
--%>

<%@page import="model.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
            integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>${post.email.firstName} ${post.email.lastName}'s Post</title>


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
            .image {
                display: flex;
                flex-wrap: wrap;
            }

            .img-top {
                width: 50%; 
                box-sizing: border-box; 
                padding: 5px; 
            }
            .formm{
                display: inline-block;
            }
            .btt{
                background: red;
                color: white;
            }
            .icon-hover{
                font-weight: bold;
                color: #999;
                position: relative;
                top:0;
                left: 0;
            }
            #care{
                width: 38px;
                height: 38px;    
            }
            #icon-main{
                height: 40px;
                width: 40px;
                cursor: pointer;
            }

            .icon-img{
                width: 47px;
                height: 47px;
                cursor: pointer;
            }
            .number-reaction{
                font-size: 14px;
            }
            .post-footer{
                height: 70px;
            }
            .icon-hover .box-list-icons{
                display: none;
            } 

            .icon-hover:hover .box-list-icons{
                /*transition: transform 0.3s cubic-bezier(0.68, -0.55, 0.27, 1.55);*/
                top: -11px;
                left: -50%;
                display: flex;
                flex-direction: row;
                gap:0px;
                text-align: center;
                align-items: center;

            }

            .box-list-icons{
                position: absolute;
                background-color: white;

                border-radius: 10px;
                height: 35px;

            }
            .detail-icon{
                display: flex;
                flex-direction: column;
                top: 0px;
            }

            .detail-icon label{
                display: none;
            }

            .detail-icon {
                position: relative;

            }

            .detail-icon label {
                position: absolute;
                top: 0;
                left: 50%;
                transform: translateX(-50%);
                transition: top 0.5s ease-in-out; 
            }

            .detail-icon:hover label {
                top: -25px; /* Điều chỉnh khoảng cách từ label đến đỉnh của detail-icon */
                display: block;
            }

            .detail-icon label {
                font-size: 13px;
                background-color: rgba(0, 0, 0, 0.5);
                border-radius: 5px;
                padding: 0.5px;
                color: whitesmoke;
            }

            .detail-icon .icon-img:hover{

                transition: transform 0.3s cubic-bezier(0.68, -0.55, 0.27, 1.55);   
                transform: scale(1.4);

            }

            .lable-main{
                cursor: pointer;
            }

            .number-cmt{
                font-size: 14px;
                font-weight: bold;
                color: #999;
            }

            .number-shares {
                font-size: 14px;
                font-weight: bold;
                color: #999;
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
        <div class="post-element col-12 mt-5 mb-5 text-center ">
            <div class="post-header">
                <c:choose>
                    <c:when test="${post.shared == true}">
                        <div class="avatar-post">
                            <c:forEach items="${listAccProfile}" var="listAccProfile">
                                <c:if test="${ post.email.email eq listAccProfile.email }">
                                    <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                </c:if>
                            </c:forEach>
                            <h5>${post.email.firstName} ${post.email.lastName}</h5>
                            <h8>${post.time}</h8>
                                ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                        </div>
                        <div class="edit-icon">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-ellipsis-h"></i>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <form action="AccountPage" method="Post">
                                        <input type="hidden" name="action" value="loadSharePostById">
                                        <input type="hidden" name="postShareID" value="${post.id}">
                                        <button type="submit" class="btn btn-default" data-bs-toggle="modal"
                                                data-bs-target="#editShareModal">
                                            Edit Share
                                            <i class="fas fa-solid fa-pen"></i>
                                        </button>
                                    </form>
                                    <form action="AccountPage" method="Post">
                                        <input type="hidden" name="postShareID" value="${post.id}">
                                        <input type="hidden" name="action" value="deleteShare">
                                        <button type="submit" class="dropdown-item">
                                            Delete Share
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="avatar-post">
                            <c:forEach items="${listAccProfile}" var="listAccProfile">
                                <c:if test="${ post.email.email eq listAccProfile.email }">
                                    <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                </c:if>
                            </c:forEach>
                            <h5>${post.email.firstName} ${post.email.lastName}</h5>
                            <h8>${post.time}</h8>
                                ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                        </div>
                        <div class="edit-icon">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-ellipsis-h"></i>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <form action="AccountPage" method="Post">
                                        <input type="hidden" name="action" value="loadPostById">
                                        <input type="hidden" name="pid" value="${post.id}">
                                        <button type="submit" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#editModal">
                                            Edit
                                            <i class="fas fa-solid fa-pen"></i>
                                        </button>
                                    </form>
                                    <form action="AccountPage" method="Post">
                                        <input type="hidden" name="pid" value="${post.id}">
                                        <input type="hidden" name="action" value="deletePost">
                                        <button type="submit" class="dropdown-item">
                                            Delete
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>  
            </div>
            <div class="post-body">
                <div class="post-text">
                    <h6>${post.caption}</h6>
                </div>
                <c:choose>
                    <c:when test="${post.shared == true}">
                        <div class="post-img">
                            <img src="${post.originalImage}" alt="">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="post-img">
                            <img src="${post.image}" alt="">
                        </div>
                    </c:otherwise>
                </c:choose>

                <c:if test="${post.shared}">
                    <div class="avatar-post">
                        <c:forEach items="${listAccProfile}" var="listAccProfile">
                            <c:if test="${ post.originalEmail.email eq listAccProfile.email }">
                                <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                            </c:if>
                        </c:forEach>
                        <h5>${post.originalEmail.firstName} ${post.originalEmail.lastName}</h5>
                        <h8>${post.originalTime}</h8>
                            ${post.originalPrivacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                    </div>
                    <div class="post-text">
                        <h6>${post.originalCaption}</h6>
                    </div>
                </c:if>
            </div>
            <div class="post-footer">
                <div class="icon-hover ">

                    <div class="number-reaction">
                        <span class="reaction-label">Reaction</span>
                        <span class="reaction-num">${post.num_Reaction}</span>
                    </div>

                    <p class="post-id" style="display:none">${post.id}</p>
                    <p class="account-email" style="display:none"><%= account.getEmail()%></p> 


                    <div class="icon-bar">
                        <p class="post-id" style="display:none">${post.id}</p>
                        <p class="account-email" style="display:none"><%= account.getEmail()%></p>

                        <c:set var="isMatched" value="false" />
                        <c:forEach items="${listEmotion}" var="e">
                            <c:choose>
                                <c:when test="${e.getId() eq post.id}">
                                    <img class="icon-img icon-main " id="icon-main" src="${e.getImg()}" alt="">
                                    <label class="lable-main main">${e.getLable()}</label>
                                    <c:set var="isMatched" value="true" />
                                </c:when>

                            </c:choose>


                        </c:forEach>

                        <c:if test="${!isMatched}">
                            <img class="icon-img icon-main " id="icon-main"
                                 src="assets/no-like.png"
                                 alt="">
                            <label class="lable-main main">Like</label>

                        </c:if>


                    </div>


                    <div class="box-list-icons">
                        <div class="detail-icon Like">
                            <!--<p class="post-id" style="display:none">${post.id}</p>-->

                            <label>Like</label>
                            <img class="icon-img"
                                 src="assets/like.gif" alt="">
                        </div>


                        <div class="detail-icon Love">
                            <!--<p class="post-id" style="display:none">${post.id}</p>-->

                            <label>Love</label>
                            <img class="icon-img"
                                 src="assets/love.gif"
                                 alt="">            
                        </div>


                        <div class="detail-icon Care">
                            <!--<p class="post-id" style="display:none">${post.id}</p>-->

                            <label>Care</label>
                            <img class="icon-img" id="care"
                                 src="assets/care.gif"
                                 alt="">
                        </div>


                        <div class="detail-icon Haha">
                            <label>Haha</label>
                            <img class="icon-img"
                                 src="assets/haha.gif"
                                 alt="">                        
                        </div>


                        <div class="detail-icon Wow">
                            <label>Wow</label>
                            <img class="icon-img"
                                 src="assets/wow.gif" alt="">                            
                        </div>


                        <div class="detail-icon Sad">
                            <label>Sad</label>
                            <img class="icon-img" src="assets/sad.gif" alt="">
                        </div>


                        <div class="detail-icon Angry">
                            <label>Angry</label>
                            <img class="icon-img" src="assets/angry.gif" alt="">

                        </div>

                    </div>


                </div>

                <div>
                    <div class ="number-cmt">
                        <span class="reaction-label">Comments </span>
                        <span class="reaction-num">${post.listCmt.size()}</span>
                    </div>
                    <button type="button" class="btn btn-default">
                        <i class="fas fa-regular fa-comment">Comment</i>
                    </button>
                </div> 
                <c:choose>
                    <c:when test="${post.shared == true}">
                        <div>
                            <div class="number-shares">
                                <span class="reaction-label">Shares </span>
                                <span class="reaction-num">${post.num_shared}</span>
                            </div>
<!--                                        <button class="fas fa-solid fa-share" onclick="openModalShare(${post.originalId})" style="border: 0">Share</button>-->
                            <button type="button" class="btn btn-default" onclick="openModalShare(${post.originalId})">
                                <i class="fas fa-solid fa-share">Share</i>
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <div class="number-shares">
                                <span class="reaction-label">Shares </span>
                                <span class="reaction-num">${post.num_shared}</span>
                            </div>
                            <!--<button class="fas fa-solid fa-share" onclick="openModalShare(${post.id})" style="border: 0">Share</button>-->
                            <button type="button" class="btn btn-default" onclick="openModalShare(${post.id})">
                                <i class="fas fa-solid fa-share">Share</i>
                            </button> 
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
            <!-- Comments -->
            <div class="list-comments">
                <c:forEach items="${post.listCmt}" var="listCmt">
                    <div class="comment">
                        <c:forEach items="${listAccProfile}" var="listAccProfile">
                            <c:if test="${ listCmt.user_cmt eq listAccProfile.email }">
                                <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                            </c:if>
                        </c:forEach>
                        <div class="comment-content">
                            <c:forEach items="${listAcc}" var="listAcc">
                                <c:if test="${ listCmt.user_cmt eq listAcc.email }">
                                    <div class="user-name">${listAcc.getFirstName()} ${listAcc.getLastName()}</div>
                                    <div class="comment-text" id = "commentBox${post.id}${listCmt.getCmt_id()}">
                                        <p id="commentText${post.id}${listCmt.getCmt_id()}">
                                            ${listCmt.text_cmt}  
                                        </p>
                                        <input type="hidden" id ="pid" value="${listCmt.getPost_id()}">
                                        <input type="hidden" id="cmtid" value="${listCmt.getCmt_id()}">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                        <c:if test="${ listCmt.user_cmt eq name.getEmail() }">
                            <div class="dropdown comment-menu">
                                <button class="btn" type="button" id="dropdownMenuCmtButton" data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    <i class="fas fa-ellipsis-h"></i>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuCmtButton">

                                    <li>
                                        <button class="btn" id ="editButton${post.id}${listCmt.getCmt_id()}">
                                            Edit <i class="fas fa-solid fa-pen"></i>
                                        </button>
                                    </li>

                                    <li>
                                        <form action="Announcement" method="Post">
                                            <input type="hidden" name="pid" value="${listCmt.getPost_id()}">
                                            <input type="hidden" name="cmtid" value="${listCmt.getCmt_id()}">
                                            <input type="hidden" name="action" value="deleteCmt">
                                            <button class="btn" type="submit">
                                                Delete <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </form>
                                    </li>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
            <form action="Announcement" method="Post" id="postCommentForm">
                <div class="comment-box">
                    <img class="profile-pic" src="${avatar.avatar}" id="avatarImg">
                    <input class="comment-input" type="text" name="textCmt" placeholder="Write a comment..." autocomplete="off" id="inputBox${post.id}" onkeyup="showSuggestions${post.id}(this.value)" autofocus>

                    <button type="submit" class="post-btn">
                        <!-- <i class="fas fa-thin fa-paper-plane-top"></i> -->
                        <i class="fas fa-solid fa-paper-plane"></i>
                    </button>
                    <input type="hidden" name="userCmt" value="<%=account.getEmail()%>">
                    <input type="hidden" name="postID" value="${post.id}">
                    <input type="hidden" name="action" value="postCmt">
                </div>
                <div id="suggestions${post.id}" style = "text-align: left; background: #ffffff; margin: -1% 8%; width: 40%">
                    <!-- Danh sách gợi ý sẽ được hiển thị ở đây -->
                </div>
            </form>
        </div>
        <%}%>
        <!-- Modal Post Share-->
        <div class="create-post modal fade" id="myModalShare" role="dialog">
            <div class="modal-dialog">
                <form action="AccountPage" method="Post">
                    <input type="hidden" name="postId" value="${post.id}">
                    <div class="modal-content">
                        <div class="modal-header post-create-header">
                            <h4 class="modal-title">Share Post</h4>
                        </div>

                        <div class="select-container">
                            <select name="privacy" id="privacy">
                                <option value="Public">Public</option>
                                <option value="Private">Private</option>
                            </select>
                            <div id="privacy-icon">
                                <!-- Nơi để hiển thị icon -->
                            </div>
                        </div>


                        <div class="modal-body post-create-body">
                            <textarea name="caption-sharePost" id="" cols="30" rows="9" placeholder="What's on your mind?"></textarea>
                        </div>
                        <div class="modal-footer post-create-footer">
                            <button type="submit" class="btn btn-default">Share on profile</button>

                        </div>
                    </div>

                    <input type="hidden" name="action" value="sharePost">
                </form>
            </div>
        </div>
        <script>
            //Edit Comment
            <c:forEach items="${post.listCmt}" var="listCmt">
            var buttonId = "editButton" + ${post.id} + ${listCmt.getCmt_id()};
            var editButton = document.getElementById(buttonId);

            if (editButton) {
                editButton.addEventListener('click', function () {
                    // Lấy nội dung hiện tại của ô bình luận
                    var commentTextid = "commentText" + ${post.id} + ${listCmt.getCmt_id()};
                    var commentText = document.getElementById(commentTextid).innerText;

                    // Thay thế ô bình luận bằng ô nhập để chỉnh sửa
                    var commentBoxid = "commentBox" + ${post.id} + ${listCmt.getCmt_id()};
                    var formHtml = '<form action="Announcement" method="POST">\
                            <input type="text" id="editField" value="' + commentText + '" name ="textComment">\
                            <input type="hidden" name="pid" value="${listCmt.getPost_id()}">\
                            <input type="hidden" name="cmtid" value="${listCmt.getCmt_id()}">\
                            <input type="hidden" name="action" value="editCmt">\
                            <button type="submit">Save</button>\
                            </form>';
                    document.getElementById(commentBoxid).innerHTML = formHtml;
                });
            }
            </c:forEach>

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
            function showSuggestions${post.id}(inputValue) {
                const suggestionBox${post.id} = document.getElementById('suggestions${post.id}');

                if (inputValue.includes('@')) {
                    const nameToMatch = inputValue.slice(inputValue.lastIndexOf('@') + 1).toLowerCase();
                    const matchedNames = suggestions.filter(namefriend =>
                        namefriend.toLowerCase().includes(nameToMatch)
                    );
                    console.log(matchedNames);
                    if (matchedNames.length > 0) {
                        suggestionBox${post.id}.innerHTML = matchedNames.map(namefriend => {
                            return'<div onclick="selectName${post.id}(\'' + namefriend + '\')">' + namefriend.slice(2) + '</div>';
                        }
                        ).join('');
                        suggestionBox${post.id}.style.display = 'block';
                    } else {
                        suggestionBox${post.id}.style.display = 'none';
                    }

                } else {
                    suggestionBox${post.id}.style.display = 'none';
                }

            }

            function selectName${post.id}(name) {
                const inputBox${post.id} = document.getElementById('inputBox${post.id}');
                const currentInput${post.id} = inputBox${post.id}.value;
                const lastAtSignIndex${post.id} = currentInput${post.id}.lastIndexOf('@');
                friendID = name.charAt(0);
                if (!listFriendID.includes(friendID)) {
                    listFriendID.push(friendID);
                }
                // Nối tên đã chọn vào cuối chuỗi hiện tại của inputBox, bắt đầu từ vị trí của ký tự '@' cuối cùng
                const newValue = currentInput${post.id}.slice(0, lastAtSignIndex${post.id}) + '@' + name.slice(2);
                inputBox${post.id}.value = newValue;
                // Ngăn chặn người dùng xóa nội dung đã thêm
                inputBox${post.id}.addEventListener('keydown', function (event) {
                    if ((event.key === 'Backspace' || event.key === 'Delete') && inputBox${post.id}.value === newValue) {
                        document.getElementById('suggestions${post.id}').style.display = 'none';
                        event.preventDefault();
                    }
                });

                console.log('listFriendID:' + listFriendID);
                document.getElementById('suggestions${post.id}').style.display = 'none';
            }
            let inputElement = document.getElementById('inputBox${post.id}');

            inputElement.addEventListener('input', function () {
                if (inputElement.value === '') {
                    listFriendID = [];
                    console.log('listFriendID:' + listFriendID);
                }
            });

            document.getElementById('postCommentForm').addEventListener('submit', function (event) {
                try {
                    // Tạo một phần tử input mới với type là hidden
                    var newInput = document.createElement('input');
                    newInput.type = 'hidden';
                    newInput.name = 'listFriendID';
                    newInput.value = JSON.stringify(listFriendID); // Chuyển đổi mảng thành chuỗi

                    // Thêm phần tử input mới vào form
                    this.appendChild(newInput);
                } catch (error) {
                    // Nếu có lỗi xảy ra khi tạo thẻ input, ngăn chặn việc submit form và in lỗi ra console
                    console.error('Lỗi khi tạo thẻ input:', error);
                    event.preventDefault();
                    return;
                }

                // Tiếp tục với việc submit form
                this.submit();
            });

            //Reaction
            icons =
                    {
                        'Like': 'assets/like.gif',
                        'Love': 'assets/love.gif',
                        'Care': 'assets/care.gif',
                        'Haha': 'assets/haha.gif',
                        'Wow': 'assets/wow.gif',
                        'Sad': 'assets/sad.gif',
                        'Angry': 'assets/angry.gif'

                    }


            const listIconsHover = document.querySelectorAll('.icon-hover')
            listIconsHover.forEach(iconHover => {

                var iconMain = iconHover.querySelector('.icon-main')
                var lableMain = iconHover.querySelector('.lable-main')
                var numReaction = iconHover.querySelector('.reaction-num');
                var beforeIcon = iconMain.src


                var postId = iconHover.querySelector('.post-id').innerText;
                var accountEmail = iconHover.querySelector('.account-email').innerText

                if (iconMain === null || iconMain === undefined) {
                    iconMain = `<img class="icon-img icon-main a" 
                                src="assets/no-like.png"
                                alt="">`
                    lableMain = `<label class="label-main main">Like</label>`
                }
                const listIcons = iconHover.querySelectorAll('.detail-icon');
                // icon.addEventListener("click", function() {
                listIcons.forEach(icon => {
                    icon.addEventListener("click", () => {
                        if (beforeIcon === 'http://localhost:8080/MagicBook/assets/no-like.png') {
                            numReaction.innerText = parseInt(numReaction.innerText) + 1
                        }

                        var iconName = icon.classList[1]
                        console.log('iconMain', iconMain)
                        console.log('lableMain', lableMain)
                        console.log('iconName', iconName)

                        iconMain.src = icons[iconName]
                        lableMain.innerHTML = iconName
                        console.log('Clicked on post with ID (box-listicon):', postId);

                        beforeIcon = iconMain.src



                        var xhr = new XMLHttpRequest();//tao doi tuong XMLHttpRequest
                        var servletUrl = "EmotionController"; // Xác định URL của servlet
                        // Xác định phương thức và URL
                        xhr.open("POST", servletUrl, true);
                        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                        // Định dạng dữ liệu để gửi lên server
                        var data = "postId=" + postId + "&iconMain=" + iconMain.src + "&lableMain=" + iconName + "&email=" + accountEmail;
                        // Gửi request đi
                        console.log(data);
                        xhr.send(data);
                        // The rest of your logic for handling the click event

                    })
                })


                var changeIconMain = iconHover.querySelectorAll('.icon-bar')
                changeIconMain.forEach(iconBar => {
                    iconBar.addEventListener('click', () => {
                        console.log('nutLike', iconMain)
                        console.log('chu like', lableMain)
                        lableMain.innerHTML = 'Like';
                        if (iconMain.src === "http://localhost:8080/MagicBook/assets/no-like.png") {
                            iconMain.src = "assets/like.gif"
                        } else
                        if (iconMain.src !== "http://localhost:8080/MagicBook/assets/no-like.png") {
                            iconMain.src = "assets/no-like.png";

                        }
                        if (iconMain.src === 'http://localhost:8080/MagicBook/assets/no-like.png') {
                            numReaction.innerText = parseInt(numReaction.innerText) - 1
                        }
                        if (iconMain.src !== 'http://localhost:8080/MagicBook/assets/no-like.png') {
                            numReaction.innerText = parseInt(numReaction.innerText) + 1
                        }

                        beforeIcon = iconMain.src
                        var xhr = new XMLHttpRequest();//tao doi tuong XMLHttpRequest
                        var servletUrl = "EmotionController"; // Xác định URL của servlet
                        // Xác định phương thức và URL
                        xhr.open("POST", servletUrl, true);
                        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                        // Định dạng dữ liệu để gửi lên server
                        var data = "postId=" + postId + "&iconMain=" + iconMain.src + "&lableMain=Like" + "&email=" + accountEmail;
                        // Gửi request đi
                        console.log(data);
                        xhr.send(data);


                    });
                });

            })


            document.addEventListener('DOMContentLoaded', function () {
                var privacySelect = document.getElementById('privacy');
                var iconDiv = document.getElementById('privacy-icon');

                // Thiết lập mặc định là public khi trang được tải
                iconDiv.innerHTML = '<i class="fas fa-globe"></i>';

                // đổi icon khi select khác
                privacySelect.addEventListener('change', function () {
                    var privacy = this.value;
                    if (privacy === 'Public') {
                        iconDiv.innerHTML = '<i class="fas fa-globe"></i>';
                    } else if (privacy === 'Private') {
                        iconDiv.innerHTML = '<i class="fas fa-lock"></i>';
                    }
                });
            });

            window.addEventListener('pageshow', function (event) {
                // Kiểm tra xem trang có được tải lại từ bộ nhớ cache hay không
                if (event.persisted) {
                    // Thực hiện các hành động khi trang được tải lại từ bộ nhớ cache
                    console.log('Trang A được làm mới sau khi quay về từ trang B');
                    location.reload()
                }
            });

            //Modal Share
            function openModalShare(postId) {
                // Đặt giá trị của input "postId" trong modal bằng ID của bài đăng
                document.querySelector('#myModalShare input[name="postId"]').value = postId;
                // Mở modal
                var myModal = new bootstrap.Modal(document.getElementById('myModalShare'));
                myModal.show();
            }
        </script>
    </body>


</html>