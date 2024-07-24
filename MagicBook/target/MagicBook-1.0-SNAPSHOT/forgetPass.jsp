<%-- 
    Document   : forgetPass
    Created on : Jan 29, 2024, 1:50:46 PM
    Author     : MSI PC
--%>

<%@page import="model.ForgetPassError"%>
<%@page import="error.ValidationError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forget Password</title>
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
        ForgetPassError fpe = (ForgetPassError) request.getAttribute("ForgetPass_ERROR");
        if(fpe == null){
                fpe = new ForgetPassError("","","");
            }
            String email = request.getParameter("email");
            
            if (email == null){
                email = "";
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
                <h2>Forget Password</h2>
                <form action="ForgetPass" method="post">
                    <div class="field">
                        <input type="text" id="email" name="email" required placeholder="Enter Email" value="<%= email%>">
                        <p class="msg text-danger" ><%= fpe.getEmailError()%></p> 
                    </div>
                    <div class="field">
                        <input type="password" id="newPass" name="newPass" required placeholder="NewPass">
                        <p class="msg text-danger" ><%= fpe.getNewPasswordError()%></p> 
                    </div>
                    <div class="field space">
                        <input type="password" class="pass-key" id="confirm" name="confirm" required
                            placeholder="Confirm" >
                        <p class="msg text-danger" ><%= fpe.getConfirmError()%></p> 
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

</body>

</html>