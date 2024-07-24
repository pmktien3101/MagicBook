<%-- 
    Document   : register.jsp
    Created on : Jan 28, 2024, 3:40:43 PM
    Author     : MSI PC
--%>

<%@page import="model.AccountError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign up Page</title>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/register.css" rel="stylesheet">
    </head>

    <body>

        <%
            AccountError accError = (AccountError) request.getAttribute("ACC_ERROR");
            if (accError == null) {
                accError = new AccountError("", "", "", "", "", "", "");
            }
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String dob = request.getParameter("dob");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
            String phone = request.getParameter("phone");
            if (firstname == null) {
                firstname = "";
            }
            if (lastname == null) {
                lastname = "";
            }
            if (email == null) {
                email = "";
            }
            if (password == null) {
                password = "";
            }
            if (phone == null) {
                phone = "";
            }
            if (confirmPassword == null) {
                confirmPassword = "";
            }

        %>


        <div class="video-background">
            <video autoplay muted loop id="myVideo">
                <source src="assets/background.mp4" type="video/mp4">
            </video>
        </div>


        <div class="wrapper">
            <form action="Register" class="form-sigup" method="POST">
                <div>
                    <h1 class="form-heading">Sign up</h1>

                </div>

                <div class="form-group_name">
                    <i class="fa fa-user"></i>
                    <input type="text" id="input-name" class="form-input" placeholder="First name"  name="firstname" value="<%= firstname%>" required autofocus>
                    <p class="msg text-danger" ><%= accError.getFirstNameError()%></p>      
                    <input type="text" id="input-name" class="form-input" placeholder="Surname" name="lastname" value="<%= lastname%>" required>
                    <p class="msg text-danger" ><%= accError.getLastNameError()%></p>       

                </div>
                <div class="form-group">
                    <i class="fa-solid fa-venus-mars"></i>
                    <div class="gender-choice">
                        <input type="radio" name="gender" value="Male" checked >Male
                        <input type="radio" name="gender" value="Female">Female                                     
                    </div>

                </div>
                <div class="form-group">
                    <i class="fa-regular fa-envelope"></i>
                    <input type="text" class="form-input" name="email" placeholder="Email" value="<%= email%>" required>
                    <p class="msg text-danger" ><%= accError.getEmailError()%></p>     

                </div>
                <div class="form-group">
                    <i class="fa-solid fa-phone"></i>
                    <input type="text" class="form-input" name="phone" placeholder="Phone number" value="<%= phone%>" required>
                    <p class="msg text-danger" ><%= accError.getPhoneError()%></p>     

                </div>
                <div class="form-group">
                    <i class="fa-regular fa-calendar-days"></i>
                    <input type="date" class="form-input" placeholder="Date of birth" name="dob" value="<%= dob%>" required >
                    <p class="msg text-danger" ><%= accError.getDobError()%></p>        

                </div>
                <div class="form-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" class="form-input"placeholder="Password" name="password"  value="<%= password%>" required> 
                    <p class="msg text-danger" ><%= accError.getPasswordError()%></p> 
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" class="form-input" name="confirmpassword" value="<%= confirmPassword%>" placeholder="Confirm password"  required>
                    <p class="msg text-danger" ><%= accError.getConfirmPasswordError()%></p>      

                </div>
                <div class="form-submit">
                    <input type="submit" value="Sign up" class="button-submit">
                </div>

            </form>
            <div class="login-account">
                <span class="question">Have an account?</span>
                <a href="index.jsp"><button class="login-button">Login</button></a>
            </div>
        </div>
    </body>

</html>