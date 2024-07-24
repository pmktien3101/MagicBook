
--insert bạn vào nếu cập nhật isAccept thành true
CREATE TRIGGER UpdateFriendAfterAccept
ON FriendRequest
AFTER UPDATE
AS
BEGIN
    IF UPDATE(isAccept)
    BEGIN
        DECLARE @sender_email VARCHAR(60);
        DECLARE @receiver_email VARCHAR(60);

        SELECT @sender_email = sender_email,
               @receiver_email = receiver_email
        FROM inserted
        WHERE isAccept = 1;

        INSERT INTO FRIEND (user_email, friend_email)
        VALUES (@receiver_email, @sender_email);
    END
END

INSERT INTO FriendRequest (sender_email, receiver_email, isAccept)
VALUES ('locbpse170514@fpt.edu.vn', 'tientien310103@gmail.com', 0);

UPDATE FriendRequest SET isAccept= 1 WHERE id= 15



-- đảo chiều insert vào bảng friend để đảm bảo liên kết
CREATE TRIGGER InsertFriend
ON FRIEND
AFTER INSERT 
AS 
BEGIN	
	INSERT INTO FRIEND (user_email, friend_email)
	SELECT inserted.friend_email,inserted.user_email
	FROM inserted
END

--insert vô notification khi có lời mời kết bạn
CREATE TRIGGER InsertRequest
ON FriendRequest
AFTER INSERT
AS
BEGIN
    INSERT INTO Notification (user_email, sender_email, isRead, requestFriend, accept, reportPost, content)
    SELECT inserted.receiver_email, inserted.sender_email, 0, 'Request Friend','','', 'sent you a friend request'
    FROM inserted;
END 

-- insert vô notification khi accept lời mời
CREATE TRIGGER UpdateRequest
ON FriendRequest
AFTER UPDATE
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted WHERE isAccept = 1)
    BEGIN
        INSERT INTO Notification (user_email, sender_email, isRead, requestFriend, accept, reportPost, content)
    SELECT inserted.sender_email, inserted.receiver_email, 0, '','Accept Request Friend','', 'accept your friend request'
        FROM inserted
        WHERE isAccept = 1
    END
END
-- insert vo notification khi cmt
CREATE TRIGGER CommentNotification
ON Post_Comment
AFTER INSERT
AS
BEGIN
	INSERT INTO Notification (user_email, sender_email, isRead, requestFriend, accept, reportPost, content, post_id)
	SELECT acc.email, pc.user_comment, 0, '','','', 'commented on your post', pc.post_id
		FROM inserted pc, POST p, Account acc
		WHERE pc.post_id = p.id AND acc.email = p.email AND p.email != pc.user_comment
END
-- insert vo notification khi tag
CREATE TRIGGER TagNotification
On Tag
AFTER INSERT
AS
BEGIN
	INSERT INTO Notification (user_email, sender_email, isRead, requestFriend, accept, reportPost, content, post_id, tag)
	SELECT ins.tagged_email, ins.tag_email, 0, '', '', '', 'metionted you in a comment',ins.post_id, 'true'
		FROM inserted ins
END

-- trigger xoa friend 2 chieu
CREATE TRIGGER DeleteFriend
ON FRIEND
AFTER DELETE 
AS 
BEGIN	
    DECLARE @user_email VARCHAR(255);
    DECLARE @friend_email VARCHAR(255);

    SELECT @user_email = user_email, @friend_email = friend_email
    FROM deleted;

    DELETE FROM FRIEND
    WHERE user_email = @friend_email AND friend_email = @user_email;
END

--minh tu tha minh thi khong thong bao
CREATE FUNCTION TestReactByMySelf()
RETURNS INT
AS
BEGIN
    DECLARE @idPost INT
    DECLARE @emailUserReact VARCHAR(60)

    SELECT TOP 1 @idPost = id, @emailUserReact = email
    FROM Emotions
    ORDER BY time DESC;

    DECLARE @emailCreatePost VARCHAR(60)
    SELECT @emailCreatePost = email FROM Post WHERE id = @idPost

    IF (@emailUserReact = @emailCreatePost)
        RETURN 1;
    
    RETURN 0; 
END;



--them, cap nhat notification
CREATE TRIGGER EmotionNotification
ON Emotions
AFTER INSERT, UPDATE
AS
BEGIN
    IF dbo.TestReactByMySelf() = 0
    BEGIN
		declare @reaction varchar(20)
		select @reaction = n.react from Notification n, inserted i where n.post_id = i.id and n.content='has reaction on your post'
		if (@reaction is null)
			begin
				INSERT INTO Notification (user_email, sender_email, isRead, content, react,post_id)
				SELECT p.email AS user_email, i.email AS sender_email, 0 AS isRead, 'has reaction on your post' AS content,
				i.lable as react, i.id as post_id
				FROM inserted i INNER JOIN Post p ON i.id = p.id  
			end
		else
			begin
				UPDATE n
				SET n.react = i.lable, n.time = i.time
				FROM Notification n
				INNER JOIN inserted i ON n.post_id = i.id 
			end
	END
END
GO

--xoa thong bao khi thu hoi like
CREATE TRIGGER deleteUnlike
ON Notification
AFTER INSERT, UPDATE
AS
BEGIN
	declare @unLike VARCHAR(MAX)
	select @unLike = e.image from Emotions e, inserted i where e.id = i.post_id
    IF (@unLike = 'http://localhost:8080/MagicBook/assets/no-like.png')
    BEGIN
		DELETE n
		FROM Notification n
		INNER JOIN inserted i ON n.post_id = i.post_id
	END
END
GO

create trigger Msg_Notification
on Messages
after insert, update
as
	begin
		insert into Notification(msg_id, user_email, sender_email, isRead, content)
		(select id, receiver, sender, 0, content from  inserted)

	end



ALTER TABLE Notification
ADD msg_id INT






