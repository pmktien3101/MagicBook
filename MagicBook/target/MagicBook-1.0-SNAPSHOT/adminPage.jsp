<%-- 
    Document   : adminPage
    Created on : Jan 28, 2024, 4:34:54 PM
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
        <title>AdminPage</title>


        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/adminpage.css" rel="stylesheet">
        <style>
            .view-button{
                background: green;
                color: white;
            }
            .delete-button{
                background: red;
                color: white;
            }
            .formm{
                display: inline-block;
                margin-right: 5px;
            }
            .moveon{

                margin-top: 100px;
                display: flex;
                justify-content: center;
                margin-bottom: 0px;
            }
            .moveon i{
                font-size: 30px;
            }
            .moveon button{
                border: none;
            }

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

        </style>
    </head>


    <body>
        <%
            Object obj = session.getAttribute("accountadmin");
            Account account = null;
            if (obj != null) {
                account = (Account) obj;
            }
            if (account == null) {
        %>
        <h1 style="color:red">You are not logged into the system. Please return to the <a href="index.jsp">Login page</a>! </h1>
        <%
        } else {%>
        <div class="header">
            <div class="headerh">
                <div class="header-1">
                    <div class="logo"><img src="assets/logoMagicBook.png" alt=""></div>
                    <div class="search"><i class="far fa-search"></i></div>
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
        <div class="content">
            <div class="moveon">
                <div><a href="ReportPage"> <i class="fa fa-envelope"></i></a></div>
                <div>
                    <form action="ReportPage" method="Post">
                        <input type="hidden" name="action" value="loadReportComment">
                        <button type="submit"> <i class="fa fa-comments"></i></button>
                    </form>
                </div>
            </div>
            <c:if test="${not empty reportPostList}">
                <h1>All Spam Post</h1>
                <table>
                    <thead>
                        <tr>
                            <td>Reporter</td>
                            <td>PostID</td>
                            <td>Reason</td>
                            <td>Time</td>
                            <td>Actions</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reportPostList}" var="r">
                            <tr>
                                <td>${r.reporter.email}</td>
                                <td>${r.post_id}</td>
                                <td>${r.reason}</td>
                                <td>${r.time}</td>
                                <td>
                                    <form action="ReportPage" method="Post" class="formm">
                                        <input type="hidden" name="pid" value="${r.post_id}">
                                        <input type="hidden" name="action" value="viewReportPost">
                                        <button type="submit" class="view-button">View</button>
                                    </form>
                                    <form action="ReportPage" method="Post" class="formm">
                                        <input type="hidden" name="rid" value="${r.id}">
                                        <input type="hidden" name="action" value="deleteReportPost">
                                        <button type="submit" class="delete-button">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach> 
                    </tbody>
                </table>
            </c:if>
            <c:if test="${not empty reportCmtList}">
                <h1>All Spam Post Comment</h1>
                <table>
                    <thead>
                        <tr>
                            <td>Reporter</td>
                            <td>CommentID</td>
                            <td>PostID</td>
                            <td>Reason</td>
                            <td>Time</td>
                            <td>Actions</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reportCmtList}" var="r">
                            <tr>
                                <td>${r.reporter.email}</td>
                                <td>${r.post_comment}</td>
                                <td>${r.post_id}</td>
                                <td>${r.reason}</td>
                                <td>${r.time}</td>
                                <td>
                                    <form action="ReportPage" method="Post" class="formm">
                                        <input type="hidden" name="cmtid" value="${r.post_comment}">
                                        <input type="hidden" name="pid" value="${r.post_id}">

                                        <input type="hidden" name="action" value="viewReportComment">
                                        <button type="submit" class="view-button">View</button>
                                    </form>
                                    <form action="ReportPage" method="Post" class="formm">
                                        <input type="hidden" name="rid" value="${r.id}">
                                        <input type="hidden" name="action" value="deleteReportComment">
                                        <button type="submit" class="delete-button">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach> 

                    </tbody>
                </table>
            </c:if>
        </div>
        <%}%>
    </body>

</html>