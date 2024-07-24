<%-- 
    Document   : accountPage
    Created on : Jan 28, 2024, 3:49:20 PM
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
            .image {
                display: flex;
                flex-wrap: wrap;
            }

            .img-top {
                width: 50%; 
                box-sizing: border-box; 
                padding: 5px; 
            }
            .formm{
                margin-top: 20px;
                margin-left: 10px;
                display: inline-block;
            }
            .btt{
                padding: 5px 5px;
                background: red;
                color: white;
                border-color: red;
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


        <c:if test="${not empty reportCmt}">   

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="cmtid" value="${reportCmt.cmt_id}">
                <input type="hidden" name="pid" value="${reportCmt.post_id}">

                <input type="hidden" name="user_email" value="${reportCmt.user_cmt}">
                <input type="hidden" name="action" value="remindComment">

                <button class="btt">Remind reported account</button>
            </form>

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="cmtid" value="${reportCmt.cmt_id}">
                <input type="hidden" name="pid" value="${reportCmt.post_id}">
                <input type="hidden" name="user_email" value="${reportCmt.user_cmt}">
                <input type="hidden" name="action" value="deleteCmt">

                <button class="btt">Delete reported comment</button>
            </form>

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="email" value="${reportCmt.user_cmt}">
                <input type="hidden" name="action" value="banAcc">

                <button class="btt">Ban reported account</button>
            </form>
            <h1 style="text-align: center; margin-top: 20px">Spam Post</h1>

            <div class="post-element col-12 mt-5 mb-5 text-center ">
                <div class="post-header">
                    <c:choose>
                        <c:when test="${post.shared == true}">
                            <div class="avatar-post">
                                <c:forEach items="${listAccProfile}" var="listAccProfile">
                                    <c:if test="${ post.email.email eq listAccProfile.email }">
                                        <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                    </c:if>
                                </c:forEach>
                                <h5>${post.email.firstName} ${post.email.lastName}</h5>
                                <h8>${post.time}</h8>
                                    ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                            </div>
                            
                        </c:when>
                        <c:otherwise>
                            <div class="avatar-post">
                                <c:forEach items="${listAccProfile}" var="listAccProfile">
                                    <c:if test="${ post.email.email eq listAccProfile.email }">
                                        <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                    </c:if>
                                </c:forEach>
                                <h5>${post.email.firstName} ${post.email.lastName}</h5>
                                <h8>${post.time}</h8>
                                    ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                            </div>
                        </c:otherwise>
                    </c:choose>  
                </div>
                <div class="post-body">
                    <div class="post-text">
                        <h6>${post.caption}</h6>
                    </div>
                    <c:choose>
                        <c:when test="${post.shared == true}">
                            <div class="post-img">
                                <img src="${post.originalImage}" alt="">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="post-img">
                                <img src="${post.image}" alt="">
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${post.shared}">
                        <div class="avatar-post">
                            <c:forEach items="${listAccProfile}" var="listAccProfile">
                                <c:if test="${ post.originalEmail.email eq listAccProfile.email }">
                                    <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                </c:if>
                            </c:forEach>
                            <h5>${post.originalEmail.firstName} ${post.originalEmail.lastName}</h5>
                            <h8>${post.originalTime}</h8>
                                ${post.originalPrivacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                        </div>
                        <div class="post-text">
                            <h6>${post.originalCaption}</h6>
                        </div>
                    </c:if>
                </div>
                <div class="post-footer">

                    <button type="button" class="btn btn-default" data-bs-toggle="modal" data-bs-target="#commentModal${post.id}">
                        <i class="fas fa-regular fa-comment">Comment</i>
                    </button>
                </div>
                <!-- Comments -->
                <div class="list-comments">
                    <div class="comment">
                        <c:forEach items="${listAccProfile}" var="listAccProfile">
                            <c:if test="${ reportCmt.user_cmt eq listAccProfile.email }">
                                <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                            </c:if>
                        </c:forEach>
                        <div class="comment-content">
                            <c:forEach items="${listAcc}" var="listAcc">
                                <c:if test="${ reportCmt.user_cmt eq listAcc.email }">
                                    <div class="user-name">${listAcc.getFirstName()} ${listAcc.getLastName()}</div>
                                    <div class="comment-text" id = "commentBox${post.id}${listCmt.getCmt_id()}">
                                        <p>
                                            ${reportCmt.text_cmt}  
                                        </p>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>
        </c:if>




        <c:if test="${empty reportCmt}">   

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="pid" value="${post.id}">
                <input type="hidden" name="user_email" value="${post.email.email}">
                <input type="hidden" name="action" value="remindAccount">

                <button class="btt">Remind reported account</button>
            </form>

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="pid" value="${post.id}">
                <input type="hidden" name="user_email" value="${post.email.email}">
                <input type="hidden" name="action" value="deletePost">

                <button class="btt">Delete reported post</button>
            </form>

            <form action="ReportPage" method="Post" class="formm">
                <input type="hidden" name="pid" value="${post.id}">
                <input type="hidden" name="email" value="${post.email.email}">
                <input type="hidden" name="action" value="banAcc">

                <button class="btt">Ban reported account</button>
            </form>
            <h1 style="text-align: center; margin-top: 20px">Spam Post</h1>

            <div class="post-element col-12 mt-5 mb-5 text-center ">
                <div class="post-header">
                    <c:choose>
                        <c:when test="${post.shared == true}">
                            <div class="avatar-post">
                                <c:forEach items="${listAccProfile}" var="listAccProfile">
                                    <c:if test="${ post.email.email eq listAccProfile.email }">
                                        <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                    </c:if>
                                </c:forEach>
                                <h5>${post.email.firstName} ${post.email.lastName}</h5>
                                <h8>${post.time}</h8>
                                    ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                            </div>                         
                        </c:when>
                        <c:otherwise>
                            <div class="avatar-post">
                                <c:forEach items="${listAccProfile}" var="listAccProfile">
                                    <c:if test="${ post.email.email eq listAccProfile.email }">
                                        <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                    </c:if>
                                </c:forEach>
                                <h5>${post.email.firstName} ${post.email.lastName}</h5>
                                <h8>${post.time}</h8>
                                    ${post.privacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                            </div>
                        </c:otherwise>
                    </c:choose>  
                </div>
                <div class="post-body">
                    <div class="post-text">
                        <h6>${post.caption}</h6>
                    </div>
                    <c:choose>
                        <c:when test="${post.shared == true}">
                            <div class="post-img">
                                <img src="${post.originalImage}" alt="">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="post-img">
                                <img src="${post.image}" alt="">
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${post.shared}">
                        <div class="avatar-post">
                            <c:forEach items="${listAccProfile}" var="listAccProfile">
                                <c:if test="${ post.originalEmail.email eq listAccProfile.email }">
                                    <img class="user-pic" src="${listAccProfile.getAvatar()}" id="avatarImg">
                                </c:if>
                            </c:forEach>
                            <h5>${post.originalEmail.firstName} ${post.originalEmail.lastName}</h5>
                            <h8>${post.originalTime}</h8>
                                ${post.originalPrivacy.equals("Public") ? '<i class="fas fa-globe"></i>' : '<i class="fas fa-lock"></i>'}
                        </div>
                        <div class="post-text">
                            <h6>${post.originalCaption}</h6>
                        </div>
                    </c:if>
                </div>
                <div class="post-footer">
                    <div class="icon-hover ">

                        <div class="number-reaction">
                            <span class="reaction-label">Reaction</span>
                            <span class="reaction-num">${post.num_Reaction}</span>
                        </div>

                        <p class="post-id" style="display:none">${post.id}</p>
                        <p class="account-email" style="display:none"><%= account.getEmail()%></p> 


                        <div class="icon-bar">
                            <p class="post-id" style="display:none">${post.id}</p>
                            <p class="account-email" style="display:none"><%= account.getEmail()%></p>

                            <c:set var="isMatched" value="false" />
                            <c:forEach items="${listEmotion}" var="e">
                                <c:choose>
                                    <c:when test="${e.getId() eq post.id}">
                                        <img class="icon-img icon-main " id="icon-main" src="${e.getImg()}" alt="">
                                        <label class="lable-main main">${e.getLable()}</label>
                                        <c:set var="isMatched" value="true" />
                                    </c:when>

                                </c:choose>


                            </c:forEach>

                            <c:if test="${!isMatched}">
                                <img class="icon-img icon-main " id="icon-main"
                                     src="assets/no-like.png"
                                     alt="">
                                <label class="lable-main main">Like</label>

                            </c:if>


                        </div>


                        <div class="box-list-icons">
                            <div class="detail-icon Like">
                                <!--<p class="post-id" style="display:none">${post.id}</p>-->

                                <label>Like</label>
                                <img class="icon-img"
                                     src="assets/like.gif" alt="">
                            </div>


                            <div class="detail-icon Love">
                                <!--<p class="post-id" style="display:none">${post.id}</p>-->

                                <label>Love</label>
                                <img class="icon-img"
                                     src="assets/love.gif"
                                     alt="">            
                            </div>


                            <div class="detail-icon Care">
                                <!--<p class="post-id" style="display:none">${post.id}</p>-->

                                <label>Care</label>
                                <img class="icon-img" id="care"
                                     src="assets/care.gif"
                                     alt="">
                            </div>


                            <div class="detail-icon Haha">
                                <label>Haha</label>
                                <img class="icon-img"
                                     src="assets/haha.gif"
                                     alt="">                        
                            </div>


                            <div class="detail-icon Wow">
                                <label>Wow</label>
                                <img class="icon-img"
                                     src="assets/wow.gif" alt="">                            
                            </div>


                            <div class="detail-icon Sad">
                                <label>Sad</label>
                                <img class="icon-img" src="assets/sad.gif" alt="">
                            </div>


                            <div class="detail-icon Angry">
                                <label>Angry</label>
                                <img class="icon-img" src="assets/angry.gif" alt="">

                            </div>

                        </div>


                    </div>

                    <div>
                        <div class ="number-cmt">
                            <span class="reaction-label">Comments </span>
                            <span class="reaction-num">${post.listCmt.size()}</span>
                        </div>
                        <button type="button" class="btn btn-default">
                            <i class="fas fa-regular fa-comment">Comment</i>
                        </button>
                    </div> 
                    <c:choose>
                        <c:when test="${post.shared == true}">
                            <div>
                                <div class="number-shares">
                                    <span class="reaction-label">Shares </span>
                                    <span class="reaction-num">${post.num_shared}</span>
                                </div>
    <!--                                        <button class="fas fa-solid fa-share" onclick="openModalShare(${post.originalId})" style="border: 0">Share</button>-->
                                <button type="button" class="btn btn-default" onclick="openModalShare(${post.originalId})">
                                    <i class="fas fa-solid fa-share">Share</i>
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <div class="number-shares">
                                    <span class="reaction-label">Shares </span>
                                    <span class="reaction-num">${post.num_shared}</span>
                                </div>
                                <!--<button class="fas fa-solid fa-share" onclick="openModalShare(${post.id})" style="border: 0">Share</button>-->
                                <button type="button" class="btn btn-default" onclick="openModalShare(${post.id})">
                                    <i class="fas fa-solid fa-share">Share</i>
                                </button> 
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </c:if>

    </div>


</div>
</div>



<%}%>
</body>

</html>