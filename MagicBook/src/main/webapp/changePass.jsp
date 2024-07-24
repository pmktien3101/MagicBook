<%-- 
    Document   : changePass
    Created on : Jan 29, 2024, 1:48:35 PM
    Author     : MSI PC
--%>

<%@page import="model.ChangePassError"%>
<%@page import="error.ValidationError"%>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/login.css" rel="stylesheet">
        <link rel="stylesheet" href="css/animate.css">

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
        <h1>You are not logged into the system. Please return to the <a href="index.jsp">Login page</a>!</h1>
        <%
        } else {
            ChangePassError cpe = (ChangePassError) request.getAttribute("ChangePass_ERROR");
            if(cpe == null){
                cpe = new ChangePassError("","","");
            }
            
        %>
        <div class="video-background">
            <video autoplay muted loop id="myVideo">
                <source src="assets/background.mp4" type="video/mp4">
            </video>     
        </div>
        <div class="bg-img">
            <div class="box-change">
                <div class="form-input">
                    <h2>Change Password</h2>
                    <form action="ChangePass" method="post">
                        <div class="field">
                            
                            <input type="password" id="pass" name="oldPass" required placeholder="OldPass">
                            <p class="msg text-danger" ><%= cpe.getOldPasswordError()%></p> 
                        </div>
                        <div class="field">
                            
                            <input type="password" id="newPass" name="newPass" required placeholder="NewPass">
                            <p class="msg text-danger" ><%= cpe.getNewPasswordError()%></p>
                        </div>
                        <div class="field space">
                            
                            <input type="password" class="pass-key" id="confirm" name="confirm" required
                                   placeholder="Confirm" value=>
                            <p class="msg text-danger" ><%= cpe.getConfirmError()%></p>
                        </div>
                        <div>

                        </div>
                        <div class="fieldlogin">
                            <input type="submit" value="Save">
                        </div>

                    </form>

                </div>

            </div>
        </div>
        <% }%>
    </body>

</html>