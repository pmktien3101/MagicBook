<%-- 
    Document   : changeDetail
    Created on : Jan 29, 2024, 1:46:45 PM
    Author     : MSI PC
--%>

<%@page import="model.AccountError"%>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>Change Detail</title>
        <%
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        %>
        <link href="<%=url%>/css/changedetail.css" rel="stylesheet">
    </head>

    <body>
        <%
            AccountError accError = (AccountError) request.getAttribute("ACC_ERROR");
            if (accError == null) {
                accError = new AccountError("", "", "", "", "", "", "");
            }
            String firstname = request.getAttribute("firstname") + "";
            if (accError.getFirstNameError() == null) {
                accError.setFirstNameError("");
            }
            String lastname = request.getAttribute("lastname") + "";
            if (accError.getLastNameError() == null) {
                accError.setLastNameError("");
            }
            String dob = request.getAttribute("dob") + "";
            if (accError.getDobError() == null) {
                accError.setDobError("");
            }
            String phone = request.getAttribute("phone") + "";
            String gender = request.getAttribute("gender") + "";
            String background = request.getAttribute("background") + "";
            background = (background.equals("null") || background.isEmpty() ? "./assets/bgr2.jpg" : background);
            String avatar = request.getAttribute("avatar") + "";
            avatar = (avatar.equals("null") || avatar.isEmpty() ? "./assets/defaultAvatar.jpg" : avatar);
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
        <div class="changedetail">
            <h2>Change Detail</h2>

            <!-- Avatar Section -->
            <div class="section">
                <label>Your avatar</label>
                <button type="button" class="edit-btn" data-bs-toggle="modal" data-bs-target="#avatarModal">Edit</button>
                <div>
                    <img class="avatar" src="<%= avatar%>" alt="Your avatar">
                </div>
            </div>

            <!-- Cover Section -->
            <div class="section">
                <label>Your cover</label>
                <button type="button" class="edit-btn" data-bs-toggle="modal" data-bs-target="#coverModal">Edit</button>
                <div>
                    <img class="background" src="<%= background%>" alt="Your background">
                </div>
            </div>

            <!-- Information Section -->
            <div class="section">
                <label>Your information</label>
                <form action="ChangeDetail" method="post">
                    <button type="submit" class="edit-btn">Edit</button>
                    <div class="information">
                        <div class="name-label">
                            <label for="firstname">First Name</label>
                            <label for="surname">Last Name</label>
                        </div>
                        <div class = "name-error">
                            <p class="msg text-danger" ><%= accError.getFirstNameError()%></p> 
                            <p class="msg text-danger" ><%= accError.getLastNameError()%></p> 
                        </div>
                        <div class="name">
                            <input type="text" value="<%= firstname%>" name="firstname" required>
                            <input type="text" value="<%=lastname%>" name="lastname" required>
                        </div>
                        <label for="gender">Gender</label>
                        <div class="gender-choice">
                            <%if (gender.equals("Male")) {%>
                            <input type="radio" name="gender" value="Male" checked >
                            <label for="male">Male</label>
                            <input type="radio" name="gender" value="Female"> 
                            <label for="female">Female</label>
                            <%} else {%>
                            <input type="radio" name="gender" value="Male">
                            <label for="male">Male</label>
                            <input type="radio" name="gender" value="Female" checked >
                            <label for="female">Female</label>
                            <%}%>
                        </div>
                        <div> 
                            <label for="dob">Date of birth</label>
                            <p class="msg text-danger" ><%= accError.getDobError()%></p>        
                            <input type="date" value="<%=dob%>" name="dob" required>
                            <label for="phone">Phone Number</label>
                            <input type="tel" value="<%=phone%>" name="phone" readonly required>
                            <input type="hidden" name="action" value="changeInformation">
                        </div>
                </form>
            </div>
        </div>

    </div>

    <!-- Modal Edit Background-->
    <form action="ChangeDetail?action=changeBackground" method="post" enctype="multipart/form-data">
        <div class="edit-background modal fade" id="coverModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content modal-edit-background">
                    <div class="modal-header background-edit-header">
                        <h4 class="modal-title">Change Background</h4>
                    </div>
                    <div class="modal-body background-edit-body">
                        <img src="<%= background%>" alt=""> <br>
                        Add new background <input id="image" name="image" type="file" class="form-control" placeholder="Enter image">
                    </div>
                    <div class="modal-footer background-edit-footer">
                        <!-- <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
                        <button type="submit" class="btn btn-default">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <!-- Modal Edit Avatar-->
    <form action="ChangeDetail?action=changeAvatar" method="post" enctype="multipart/form-data">
        <div class="edit-avatar  modal fade" id="avatarModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content modal-edit-avatar">
                    <div class="modal-header avatar-edit-header">
                        <h4 class="modal-title">Change avatar</h4>
                    </div>
                    <div class="modal-body avatar-edit-body">
                        <img src="<%= avatar%>" alt=""> <br>
                        Add new avatar <input id="image" name="image" type="file" class="form-control" placeholder="Enter image">
                    </div>
                    <div class="modal-footer avatar-edit-footer">
                        <!-- <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
                        <button type="submit" class="btn btn-default">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <%}%>
</body>

</html>