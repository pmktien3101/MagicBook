<%-- 
    Document   : productPage
    Created on : Jan 28, 2024, 3:21:53 PM
    Author     : MSI PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
              integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/productPage.css" rel="stylesheet">
        <title>Product Page</title>
    </head>


    <body>
        <div class="background">
            <div class="header">
                <div class="headerh">
                    <div class="header-1">
                        <div class="logo"><img src="assets/logoMagicBook.png" alt=""></div>
                        <div class="search"><i class="far fa-search"></i></div> 
                    </div>

                    <div class="header-2">
                        <div class="home"><i class="far fa-home"></i> </div>
                        <div class="store"><i class="far fa-store"></i></div>
                        <div class="game"><i class="far fa-gamepad"></i></div>
                    </div>

                    <div class="header-3">
                        <div class="chat"><i class="far fa-comment"></i></div>
                        <div class="bell"><i class="far fa-bell"></i></div>
                        <div class="account">
                            <button><i class="fas fa-user"></i>
                                <a class="btn btn-primary">Account</a></button>
                        </div>
                    </div>

                </div>

            </div>
            <div class="content">
                <div class="box-left">
                    <h2>Market place</h2>
                    <div class="search"><i class="far fa-search"></i>
                        <input type="text" placeholder="Search">
                    </div>
                    <div class="sale">
                        <i class="far fa-cart-arrow-down"></i> Sales
                    </div>
                    <div class="create">
                        <button type="button"><i class="far fa-plus"></i>Create product to sale</button>
                    </div>
                </div>
                <div class="box-right">
                    <div class="form-input">
                        <h4>Product</h4>
                        <img src="assets/logo1.png" alt="" srcset="">
                        <img src="assets/logo1.png" alt="" srcset="">
                        <img src="assets/logo1.png" alt="" srcset="">
                    </div>

                </div>
            </div>
        </div>
    </body>

</html>