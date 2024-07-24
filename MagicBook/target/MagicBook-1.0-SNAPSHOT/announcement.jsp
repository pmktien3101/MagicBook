<%-- 
    Document   : ann
    Created on : Jan 28, 2024, 3:43:25 PM
    Author     : MSI PC
--%>

<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
              integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <title>Announcement</title>

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/thongbao.css" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;

            }
            body,html {
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
                /*overflow-x: hidden;*/ 
            }
            .header{
                width: 100%;
            }
            .friend-list li {
                /*border: 1px solid #000;*/
                border-radius: 10px;
                background-color: rgba(220, 220, 220, 0.7); 
                margin-bottom: 10px; 
                padding: 10px;
                list-style: none; 

            }



            #symbol-img{
                width: 80px;
                height: 80px;
                border-radius: 50%;

            }

            .icon-Notification{
                position:absolute;         
                width: 50px;
                height: 50px; 
                bottom: -10px;
                right: -2px;
            }
            .icon-Notification img{
                width: 100%;
            }       


            .symbol-Notification{
                position: relative;
                width: 80px;
                height: 80px;
                border-radius: 50%;

            }
            .content h1{
                margin-top: 50px;
                text-align: center;
                font-size: 50px;
            }
            .content{
                margin: 0;
                padding: 0;
                width: 100%;
                height: auto;
            }   
            .content-body{
                margin: 0;
                padding: 0;
                width: 100%;
                display: flex;
                /*align-items: center;*/
                justify-content: center;

            }
            .friend-list{
                width: 40%;


            }
            .notification{
                display: flex;
                align-items: center;
                /*                justify-content: flex-start;*/
                gap: 10px;
            }
            .page-item{

                display: flex;
                justify-content: space-between;
                align-items: center;

            }
            .main-notification{
                left:0;

            }
            .notification a{
                text-decoration: none;
                color: black;
                font-size: 20px;
            }

            .avatar-header-3{
                width: 40px;
                height: 40px;
                border-radius: 100%;
                border: 1px;
                border-style: solid;
                margin-right: 30px;
            }
            .home a{
                text-decoration: none;
            }

            .home i{
                color: black;
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
        %>
        <div class="header">
            <div class="headerh">
                <div class="header-1">
                    <div class="logo"><img src="assets/logoMagicBook.png" alt=""></div>
                </div>

                <div class="header-2">
                    <div class="home"><a href="Home"><i class="far fa-home"></i> </a></div>
                </div>

                <div class="header-3">

                    <a href="AccountPage" class="avatar-home-css">
                        <img class="avatar-header-3" src="${avatar.avatar}" alt="avatar">                   
                    </a>
                </div>

            </div>

        </div>
        <div class="content">
            <h1>Announcement</h1>

            <div class="content-body">

                <ul class="friend-list">
                    <c:if test="${not empty listNo}">

                        <c:forEach items="${listNo}" var="no">

                            <li class="page-item" style="${no.isRead eq 'false' ? 'background-color:gainsboro;font-weight:bold;' : ''}">

                                <span class="main-notification">

                                    <c:choose>
                                        <c:when test="${no.reportPost.id !=null}">

                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="assets/logoMagicBook.png" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="http://clipart-library.com/images/kcKopp8Xi.png"
                                                             alt=""> 
                                                    </div>
                                                </span>
                                                <span><a class="text" href="Announcement?action=loadPost&nid=${no.id}&pid=${no.reportPost.id}">
                                                        ${no.content}
                                                    </a>
                                                </span>
                                            </div>                                            
                                        </c:when>
                                        <c:when test="${no.reportComment !=null}">

                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="assets/logoMagicBook.png" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="http://clipart-library.com/images/kcKopp8Xi.png"
                                                             alt=""> 
                                                    </div>
                                                </span>
                                                <span><a class="text" href="Announcement?action=loadPost&nid=${no.id}&pid=${no.reportComment.post_id}">
                                                        ${no.content}
                                                    </a>
                                                </span>
                                            </div>                                            
                                        </c:when>
                                        <c:when test="${no.requestFriend.equals('Request Friend')}">

                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="${avatarImg}" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="https://cdn-icons-png.flaticon.com/512/6388/6388049.png"
                                                             alt=""> 
                                                    </div>
                                                </span>
                                                <span><a class="text" href="RequestFriend?action=isRead&nid=${no.id}">
                                                        <b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> ${no.content}</a>
                                                </span>
                                            </div>
                                        </c:when>
                                        <c:when test="${no.acceptRequest.equals('Accept Request Friend')}">

                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="${avatarImg}" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="https://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Free-Download.png"
                                                             alt=""> 
                                                    </div>
                                                </span>
                                                <span><a class="text" href="Friend?action=loadFriend&nid=${no.id}&fid=${no.sender_email.email}">
                                                        <b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> ${no.content}</a>
                                                </span>

                                            </div>
                                        </c:when>
                                        <c:when test="${no.react != null}">

                                            <c:set var="avatarImg" value="https://inkythuatso.com/uploads/thumbnails/800/2023/03/9-anh-dai-dien-trang-inkythuatso-03-15-27-03.jpg"/>
                                            <c:set var="iconReact" value=""/>
                                            <c:forEach items="${listAvatar}" var="avatar">
                                                <c:if test="${not empty no.sender_email and no.sender_email.email eq avatar.key}">
                                                    <c:set var="avatarImg" value="${avatar.value}" />
                                                </c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${no.react eq 'Haha'}">
                                                    <c:set var="iconReact" value="assets/haha.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Sad'}">
                                                    <c:set var="iconReact" value="assets/sad.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Like'}">
                                                    <c:set var="iconReact" value="assets/like.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Angry'}">
                                                    <c:set var="iconReact" value="assets/angry.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Care'}">
                                                    <c:set var="iconReact" value="assets/care.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Love'}">
                                                    <c:set var="iconReact" value="assets/love.gif"/>
                                                </c:when>
                                                <c:when test="${no.react eq 'Wow'}">
                                                    <c:set var="iconReact" value="assets/wow.gif"/>
                                                </c:when>
                                            </c:choose>
                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="${avatarImg}" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="${iconReact}"
                                                             alt=""> 
                                                    </div>
                                                </span>

                                                <span><a class="text" href="Announcement?action=loadPost&nid=${no.id}&pid=${no.post_id.id}"><b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> ${no.content} <b>${no.react}</b></a></span>


                                            </div>
                                        </c:when>
                                        <c:when test="${no.content eq 'commented on your post'}">

                                            <div class="notification">
                                                <span class="symbol-Notification">
                                                    <img id="symbol-img" src="${avatarImg}" alt="">
                                                    <div class="icon-Notification">
                                                        <img id="symbol-icon"
                                                             src="https://purepng.com/public/uploads/thumbnail/purepng.com-messages-iconsymbolsiconsapple-iosiosios-8-iconsios-8-721522596080z9jx2.png"
                                                             alt=""> 
                                                    </div>
                                                </span>

                                                <span><a class="text" href="Announcement?action=loadPost&nid=${no.id}&pid=${no.post_id.id}" class="text" href=""><b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> ${no.content} </a></span>


                                            </div>
                                        </c:when>
                                        <c:when test="${no.post_id.id != null && no.tag == true}">
                                            <div class="notification">
                                                <div class="notification">
                                                    <span class="symbol-Notification">
                                                        <img id="symbol-img" src="${avatarImg}" alt="">
                                                        <div class="icon-Notification">
                                                            <img id="symbol-icon"
                                                                 src="https://purepng.com/public/uploads/thumbnail/purepng.com-messages-iconsymbolsiconsapple-iosiosios-8-iconsios-8-721522596080z9jx2.png"
                                                                 alt=""> 
                                                        </div>
                                                    </span>

                                                    <span><a class="text" href="Announcement?action=loadPost&nid=${no.id}&pid=${no.post_id.id}" class="text" href=""><b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> ${no.content} </a></span>


                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${no.msg_id != null}">
                                            <div class="notification">
                                                <div class="notification">
                                                    <span class="symbol-Notification">
                                                        <img id="symbol-img" src="${avatarImg}" alt="">
                                                        <div class="icon-Notification">
                                                            <img id="symbol-icon"
                                                                 src="https://res.9appsinstall.com/group2/M00/8A/62/QQ0DAFnfR7iAAyYgAAAZzNQKLWk132.png?x-oss-process=style/mq200"
                                                                 alt=""> 
                                                        </div>
                                                    </span>

                                                    <span><a class="text" href="Chat?action=loadMess&friend_email=${no.sender_email.email}" class="text" href=""><b>${no.sender_email.firstName} ${no.sender_email.lastName}</b> sent a message " ${no.content} "</a></span>


                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </span>

                                <form action="Announcement" method="Post">
                                    <input type="hidden" name="aid" value="${no.id}">
                                    <input type="hidden" name="action" value="deleteNo">
                                    <button type="submit">Delete <i class="fas fa-trash-alt"></i></button>
                                </form>                         


                            </li>

                        </c:forEach>
                    </c:if>
                </ul>

            </div>
        </div>
        <%}%>

        <script>
            window.addEventListener('pageshow', function (event) {
                if (event.persisted) {
                    location.reload()
                }
            });
        </script>
    </body>

</html>
