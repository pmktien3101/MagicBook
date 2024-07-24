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

            .friend-list li {
                border: 1px solid #ccc; 
                margin-bottom: 10px; 
                padding: 10px;
                list-style: none; 
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
                <h1>Friend request</h1>
            </div>

            <ul class="friend-list">
                <c:forEach items="${requestlist}" var="request">
                    <li>
                        <div class="friend-info">
                            <img src="${request.avatar}" alt="">
                            <form action="RequestFriend" method="Post">
                                <input type="hidden" name="rid" value="${request.email}">
                                <input type="hidden" name="action" value="loadAccountPage">
                                <button type="submit">${request.firstname} ${request.lastname}</button>
                            </form>
                        </div>
                        <div class="friend-actions">
                            <form action="RequestFriend" method="Post">
                                <input type="hidden" name="rid" value="${request.id}">
                                <input type="hidden" name="action" value="deleteRequest">
                                <button type="submit">Delete   <i class="fas fa-trash-alt"></i></button>
                            </form>
                            <form action="RequestFriend" method="Post">
                                <input type="hidden" name="rid" value="${request.id}">
                                <input type="hidden" name="action" value="acceptRequest">
                                <button type="submit">Accept  <i class="fas fa-user"></i></button>
                            </form>
                        </div>
                    </li> 
                </c:forEach>
            </ul>


        </div>
        <div class="text-center">
            <ul class="pagination pagination-v2">
                <c:forEach begin="1" end="${totalPage}" var="i">
                    <li class="page-item ${index==i?"active":""}">
                        <a class="page-link" href="RequestFriend?indexPage=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <%}%>
    </body>

</html>