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

                <div class="header-2">
                    <div class="home"><a href="AccountPage"><i class="far fa-home"></i></a> </div>
                    <div class="store"><i class="far fa-store"></i></div>
                    <div class="game"><i class="far fa-gamepad"></i></div>
                </div>

                <div class="header-3">
                    <button>
                        <i class="fas fa-user"></i>
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Hi, <%=account.getFirstName()%></a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="changePass.jsp">Change password</a></li>
                            <li><a class="dropdown-item" href="Logout">Logout</a></li>
                            <li><a class="dropdown-item" href="AccountPage?action=deleteAccount">Delete account</a></li>
                        </ul>
                    </button>
                </div>
            </div>
        </div>
        <!-- --------------------------------------------------------------------------------------------- -->
        <div class="friend">


            <ul class="friend-list">
                <c:if test="${not empty friendlist}">
                    <c:forEach items="${friendlist}" var="friend">
                        <li><img src="${friend.avatar}" alt="">
                            <form action="Friend" method="Post">
                                <input type="hidden" name="fid" value="${friend.email}">
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

        <%}%>
    </body>

</html>