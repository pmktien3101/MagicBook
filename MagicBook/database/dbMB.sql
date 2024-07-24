CREATE DATABASE MBProject 
use MBProject

CREATE TABLE Account (
    email VARCHAR(60) NOT NULL primary key,
    firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
	gender VARCHAR (20) NOT NULL,
	dob DATE NOT NULL,
    phone VARCHAR(12) NOT NULL UNIQUE,
	status VARCHAR(20) NOT NULL,
    role SMALLINT NOT NULL DEFAULT 0 CHECK (role >= 0 AND role <= 1)
);

CREATE TABLE POST(
	id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	time DATETIME DEFAULT GETDATE(),
	caption VARCHAR(300),
	image VARCHAR(MAX),
	privacy VARCHAR(50),
	email varchar(60),
    CONSTRAINT FK_Account FOREIGN KEY (email) REFERENCES Account(email),
	num_reaction int default 0
);

ALTER TABLE POST ADD originalId int;
ALTER TABLE POST ADD
CONSTRAINT FK_OriginalID FOREIGN KEY (originalId) REFERENCES POST(id);

CREATE TABLE Account_Profile(
	email VARCHAR(60) NOT NULL primary key,
	avatar VARCHAR(MAX),
	background VARCHAR(MAX),
	about_You NVARCHAR(300),
	CONSTRAINT FK_PROFILE_ACCOUNT FOREIGN KEY (email) REFERENCES ACCOUNT (email),
);

CREATE TABLE FRIEND (
    id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
    user_email VARCHAR(60) NOT NULL,
    friend_email VARCHAR(60) NOT NULL,
    CONSTRAINT FK_Account_User FOREIGN KEY (user_email) REFERENCES Account(email),
    CONSTRAINT FK_Account_Friend FOREIGN KEY (friend_email) REFERENCES Account(email)
);

CREATE TABLE FriendRequest (
    id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
    sender_email VARCHAR(60) NOT NULL,
    receiver_email VARCHAR(60) NOT NULL,
	sent_date DATETIME DEFAULT GETDATE(),
	accept_date DATETIME DEFAULT NULL,
	isAccept BIT NOT NULL,
    CONSTRAINT FK_Account_sender FOREIGN KEY (sender_email) REFERENCES Account(email),
    CONSTRAINT FK_Account_receiver FOREIGN KEY (receiver_email) REFERENCES Account(email)
);

CREATE TABLE Notification(
	id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	user_email VARCHAR(60) NOT NULL,
    sender_email VARCHAR(60) NOT NULL, 
	isRead BIT DEFAULT 0,
	requestFriend VARCHAR(60),
	accept VARCHAR(60),
	reportPost INT,
	content VARCHAR(60) NOT NULL,
	time DATETIME DEFAULT GETDATE(),
 	CONSTRAINT FK_N_User FOREIGN KEY (user_email) REFERENCES Account(email),
    CONSTRAINT FK_N_Friend FOREIGN KEY (sender_email) REFERENCES Account(email)
);

CREATE TABLE ReportPost(
	id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	post_id INT,
	reason VARCHAR(100),
	reporter VARCHAR(60),
	time DATETIME DEFAULT GETDATE(),
	CONSTRAINT FK_R_Post FOREIGN KEY (post_id) REFERENCES Post(id),
	CONSTRAINT FK_R_Account FOREIGN KEY (reporter) REFERENCES Account(email)
);

CREATE TABLE ReportComment (
    id INT PRIMARY KEY NOT NULL IDENTITY(1,1),
    comment_id INT,
    post_id INT,
    reason VARCHAR(100),
    reporter VARCHAR(60),
	time DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_RM_Post FOREIGN KEY (comment_id,post_id) REFERENCES Post_Comment(cmt_id, post_id),
    CONSTRAINT FK_RM_Account FOREIGN KEY (reporter) REFERENCES Account(email)
);

create table Conversations(
	id int identity(1,1) primary key,
	name nvarchar(50) NOT NULL,
	avatar varchar(MAX)
) ;

create table Conversations_users(
	conversations_id int,
	user_email varchar(60),
	isAdmin bit NOT NULL,
	foreign key (conversations_id) references Conversations(id),
	foreign key (user_email) references Account(email),
	primary key(conversations_id, user_email)
) ;


CREATE TABLE Messages (
	id INT identity(1,1) primary key,
	sender VARCHAR(60) NOT NULL,
	receiver VARCHAR(60),
	content NVARCHAR(max) NOT NULL,
	created_at DATETIME default current_timestamp,
	foreign key (sender) references Account(email),
	foreign key (receiver) references Account(email),
) ;

ALTER TABLE Messages
ADD conversations_id INT
ALTER TABLE Messages
ADD isRead  BIT DEFAULT 0

CREATE TABLE Emotions (
    id INT,
    label VARCHAR(20),
    image VARCHAR(MAX),
    time DATETIME DEFAULT GETDATE(),
    email VARCHAR(60),
    PRIMARY KEY (id, email),
    FOREIGN KEY (id) REFERENCES POST(id),
    FOREIGN KEY (email) REFERENCES Account(email)
);

drop table Post_Shares

CREATE TABLE Post_Comment (
	cmt_id INT,
	post_id INT,
	user_comment VARCHAR(60) NOT NULL,
	time_create DATETIME DEFAULT GETDATE(),
	text_comment VARCHAR(300),
	CONSTRAINT FK_Comment FOREIGN KEY (post_id) REFERENCES Post(id),
	CONSTRAINT PK_Comment PRIMARY KEY (cmt_id, post_id),
)

create proc proc_addCMT (@post_id int, @user_cmt VARCHAR(60), @text_cmt varchar(300))
as
begin
	DECLARE @cmt_id INT;
	SELECT @cmt_id = ISNULL(MAX(cmt_id), 0) + 1
    FROM Post_Comment
    WHERE post_id = @post_id;

	INSERT INTO Post_Comment (cmt_id, post_id, user_comment, text_comment)
	VALUES (@cmt_id, @post_id, @user_cmt, @text_cmt);
end
go

create proc proc_deleteCMT (@post_id int, @cmt_id int)
as
begin
	DELETE FROM Post_Comment
	WHERE cmt_id = @cmt_id AND post_id = @post_id
	
	UPDATE Post_Comment
	SET cmt_id = cmt_id - 1
	WHERE post_id = @post_id AND cmt_id>@cmt_id

end
go

-- them cot vao notification
ALTER TABLE Notification ADD post_id int
ALTER TABLE Notification ADD tag VARCHAR(60)
ALTER TABLE Notification ADD react varchar(20)

SELECT * FROM TAG
CREATE TABLE TAG(
	id INT identity(1,1) primary key,
	tag_email varchar(60),
	tagged_email varchar(60),
	post_id int,
	CONSTRAINT FK_Tag_User FOREIGN KEY (tag_email) REFERENCES ACCOUNT(email),
	CONSTRAINT FK_Tagged_User FOREIGN KEY (tagged_email) REFERENCES ACCOUNT(email),
	CONSTRAINT FK_TagPlace FOREIGN KEY (post_id) REFERENCES POST(id)
)


ALTER TABLE Notification 
ADD reportComment INT


alter table post_comment
add constraint FK_User_Comment
foreign key (user_comment)
references account(email)

--
drop table Conversations_users
drop table Conversations
alter table Messages
drop column conversations_id
alter table Messages
drop column isRead
