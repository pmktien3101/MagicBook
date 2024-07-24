<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Code</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Verify Code</title>
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
            String error = request.getAttribute("error") + "";
            if (error.equals("null")) {
                error = "";
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
                    <h1>Verify Code</h1>
                    <div style="color: red; text-align: center"> <%=error%></div>
                    <form action="VerifyCodeController" method="POST">
                        <div class="field">
                            <input type="text" name="verifyCode" value=""/>
                        </div>
                        <div class="fieldlogin">
                            <input type="submit" value="Send"/>
                        </div>     
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>