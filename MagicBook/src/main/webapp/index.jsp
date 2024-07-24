<%-- 
    Document   : login
    Created on : Jan 24, 2024, 3:47:18 PM
    Author     : MSI PC
--%>

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
        <link href="<%=url%>/css/animate.css" rel="stylesheet">

    </head>

    <body>
        <%
            String error = request.getAttribute("error") + "";
            error = (error.equals("null") ? "" : error);

            String email = "";
            String pass = "";
            String rem = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("cookEmail")) {
                        email = c.getValue();
                    } else if (c.getName().equals("cookPassword")) {
                        pass = c.getValue();
                    } else if (c.getName().equals("cookRemember")) {
                        rem = c.getValue();
                    }

                }
            }
        %>

        <div class="video-background">
            <video autoplay muted loop id="myVideo">
                <source src="assets/background.mp4" type="video/mp4">
            </video>     
        </div>


        <div class="bg-img">

            <div class="box-left">
                <div class="imagelogin">
                    <img src="assets/friend.jpg" alt="" width="200px" height="300px" >
                    <img src="assets/market.jpg" alt="" width="200px" height="400px">
                    <img src="assets/gamebox.jpg" alt="" width="200px" height="300px" >
                </div>
            </div>
            <div class="box-right">
                <div class="form-input">
                    <h2>Magic Book</h2>
                    <div style="color: red; text-align: center"> <%=error%></div>

                    <form action="LoginController" method="post">
                        <div class="field">
                            <span class="fa fa-user"></span>
                            <input type="text" id="email" name="email" required placeholder="Your Email" value="<%=email%>">
                        </div>
                        <div class="field space">
                            <span class="fa fa-lock"></span>
                            <input type="password" class="pass-key" id="password" name="password" required
                                   placeholder="Your Password" value="<%=pass%>">
                        </div>
                        <div style="text-align: center">
                            Remember me: <input type="checkbox" name="remember" value="<%=rem%>">
                        </div>

                        <div class="pass">
                            <a href="forgetPass.jsp"><i>Forgot Password?</i></a>
                        </div>
                        <div class="fieldlogin">
                            <input type="submit" value="Login">
                        </div>
                    </form>
                    <div class="or">OR</div>
                    <div class="google">
                        <div class="google">
                            <div class="flex items-center justify-center h-screen dark:bg-gray-100">
                                <button
                                    class="px-4 py-2 border flex gap-2 border-slate-200 dark:border-slate-700 rounded-lg text-slate-100 dark:text-slate-100 hover:border-slate-100 dark:hover:border-slate-100 hover:text-slate-200 dark:hover:text-slate-100 hover:shadow transition duration-50">
                                    <img class="w-4 h-4" src="https://www.svgrepo.com/show/475656/google-color.svg"
                                         loading="lazy" alt="google logo">
                                    <a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/MagicBook/LoginController&response_type=code&client_id=843581811066-q2bnmoel637qda4rjrpjt39guec7sarf.apps.googleusercontent.com&approval_prompt=force">
                                        <span class="text-base">Login with Google</span></a>
                                </button>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="signup">
                    <div class="signuptext">Don't have account?</div>
                    <a href="register.jsp"> <input type="submit" value="Sign up"></a>

                </div>
            </div>
        </div>

    </body>

</html>