<%-- 
    Document   : friend
    Created on : Jan 29, 2024, 1:51:59 PM
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>Friend</title>

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/friend.css" rel="stylesheet">


        <style>
            .pagination-v2 {
                display: flex;
                justify-content: center;
            }

            .pagination-v2 .page-item .page-link {
                color: #000;
            }

            .pagination-v2 .page-item .page-link:hover {
                background-color: #3FD2C7;
            }

            .pagination-v2 .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
            }

            .pagination-v2 .page-link {
                position: relative;
                display: block;
                padding: .5rem .75rem;
                margin-left: -1px;
                line-height: 1.25;
                color: #007bff;
                background-color: #fff;
                border: 1px solid #dee2e6;
            }
            
            /*-----------*/

            .header-3 {
                margin-right: 20px;
                display: flex;
                align-items: center;
            }

            .header-3 button {
                background: none;
                border: none;
                cursor: pointer;
                display: flex;
                align-items: center; /* Canh chỉnh icon và chữ theo chiều dọc */
            }

            .header-3 button i {
                margin-right: 5px; /* Để tạo khoảng cách giữa icon và chữ */
            }


            .friend-list button {
                background-color: #007bff; /* Màu nền của nút */
                color: white; /* Màu chữ của nút */
                border: none;
                padding: 5px 10px;
                border-radius: 5px; /* Làm tròn góc nút */
                cursor: pointer;
                transition: background-color 0.3s; /* Hiệu ứng chuyển đổi màu nền khi hover */
            }

            .friend-list button:hover {
                background-color: #0056b3; /* Màu nền khi hover */
            }
            
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
        <!-- --------------------------------------------------------------------------------------------- -->
        <div class="friend">
            <div class="friend-header">
                <h1>All Friends</h1>
                <button type="button"> <a href="RequestFriend">Friend request</a></button>
            </div>
            <div class="search-bar">
                <form action="Friend" method="post">
                    <input type="hidden" name="action" value="searchA">
                    <input type="text" name="keyword" placeholder="Search your friends">
                    <button type="submit"><i class="fas fa-search search-icon"></i></button>
                </form>

            </div>

            <ul class="friend-list">
                <c:if test="${not empty friendlist}">
                    <c:forEach items="${friendlist}" var="friend">
                        <li><img src="${friend.avatar}" alt="">
                            <form action="Friend" method="Post">
                                <input type="hidden" name="fid" value="${friend.friend_email}">
                                <input type="hidden" name="action" value="loadFriend">
                                <button type="submit">${friend.firstname} ${friend.lastname}</button>
                            </form>

                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${empty friendlist}">
                    <li style="font-size: 50px; color: red; text-align: center">No results found!</li>
                    </c:if>
            </ul>

        </div>
        <div class="text-center">
            <ul class="pagination pagination-v2">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <li class="page-item ${index==i?"active":""}">
                        <a class="page-link" href="Friend?indexPage=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <%}%>
    </body>

</html>