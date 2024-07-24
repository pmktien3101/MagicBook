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

        <title>Report Post</title>
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
                    <c:if test="${not empty mid}">
                        <form action="ReportPage" method="Post">
                            <input type="hidden" name="action" value="insertReportC">
                            <div class="modal-content modal-edit-content">
                                <div class="modal-header post-edit-header">
                                    <h4 class="modal-title">Report Form</h4>
                                </div>
                                <div class="modal-body">
                                    <input type="hidden" name="commentid" value="${mid}">
                                    <input type="hidden" name="pid" value="${pid}">
                                    <div class="select-container">
                                        <label for="reportReason">Reason for Report:</label>
                                        <div class="report-option">
                                            <input type="radio" id="nudePhotos" name="reportReason" value="Nude Photos">
                                            <label for="nudePhotos">Nude Photos</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="violently" name="reportReason" value="Violently">
                                            <label for="violently">Violently</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="messing" name="reportReason" value="Messing">
                                            <label for="messing">Messing</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="spam" name="reportReason" value="Spam">
                                            <label for="spam">Spam</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="hatefulWords" name="reportReason" value="Hateful Words">
                                            <label for="hatefulWords">Hateful Words</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="misinformation" name="reportReason" value="Misinformation">
                                            <label for="misinformation">Misinformation</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="unauthorizedSales" name="reportReason" value="Unauthorized Sales">
                                            <label for="unauthorizedSales">Unauthorized Sales</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer post-edit-footer" style="margin-bottom: 40px">
                                    <button type="submit" class="btn btn-default">Save</button>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${empty mid}">
                        <form action="ReportPage" method="Post">
                            <input type="hidden" name="action" value="insertReport">
                            <div class="modal-content modal-edit-content">
                                <div class="modal-header post-edit-header">
                                    <h4 class="modal-title">Report Post</h4>
                                </div>
                                <div class="modal-body">
                                    <input type="hidden" name="pid" value="${pid}">
                                    <div class="select-container">
                                        <label for="reportReason">Reason for Report:</label>
                                        <div class="report-option">
                                            <input type="radio" id="nudePhotos" name="reportReason" value="Nude Photos">
                                            <label for="nudePhotos">Nude Photos</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="violently" name="reportReason" value="Violently">
                                            <label for="violently">Violently</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="messing" name="reportReason" value="Messing">
                                            <label for="messing">Messing</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="spam" name="reportReason" value="Spam">
                                            <label for="spam">Spam</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="hatefulWords" name="reportReason" value="Hateful Words">
                                            <label for="hatefulWords">Hateful Words</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="misinformation" name="reportReason" value="Misinformation">
                                            <label for="misinformation">Misinformation</label>
                                        </div>
                                        <div class="report-option">
                                            <input type="radio" id="unauthorizedSales" name="reportReason" value="Unauthorized Sales">
                                            <label for="unauthorizedSales">Unauthorized Sales</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer post-edit-footer" style="margin-bottom: 40px">
                                    <button type="submit" class="btn btn-default">Save</button>
                                </div>
                            </div>
                        </form>
                    </c:if>

            </div>
        </div>

        <%}%>

    </body>
</html>