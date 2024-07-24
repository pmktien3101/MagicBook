
INSERT INTO Account (email, firstname, lastname,password,gender, dob, phone, status, role) VALUES 
('tienpmkse170552@fpt.edu.vn', 'tien','pham', 'tien123', 'Nu', '2003-01-31','0812654340','active', 0);
INSERT INTO Account (email, firstname, lastname,password,gender, dob, phone, status, role) VALUES 
('phammackimtien@gmail.com', 'tien','pham', 'tien123', 'Nu', '2003-01-31','0812654344','active', 0);
INSERT INTO Account (email, firstname, lastname,password,gender, dob, phone, status, role) VALUES 
('dathtvse170567@fpt.edu.vn', 'dat','huynh', 'dat123', 'Nam', '2003-01-31','0823456789','active', 1);
INSERT INTO Account (email, firstname, lastname,password,gender, dob, phone, status, role) VALUES 
('locbpse170514@fpt.edu.vn', 'loc','bui', 'loc123', 'Nam', '2003-12-28','0813405986','active', 0);
INSERT INTO Account (email, firstname, lastname,password,gender, dob, phone, status, role) VALUES 
('longlqse170568@fpt.edu.vn', 'long','le', 'long123', 'Nam', '2003-12-25','0824950139','active', 1);



INSERT INTO POST (caption, image, privacy, email)
VALUES ('Amazing beach view', 'https://hips.hearstapps.com/hmg-prod/images/champagne-beach-espiritu-santo-island-vanuatu-royalty-free-image-1655672510.jpg?crop=1.00xw:0.755xh;0,0.173xh&resize=1200:*',
'Public', 'tienpmkse170552@fpt.edu.vn');
INSERT INTO POST (caption, image, privacy, email)
VALUES ('Amazing beach view', 'https://hips.hearstapps.com/hmg-prod/images/champagne-beach-espiritu-santo-island-vanuatu-royalty-free-image-1655672510.jpg?crop=1.00xw:0.755xh;0,0.173xh&resize=1200:*',
'Public', 'tienpmkse170552@fpt.edu.vn');
INSERT INTO POST (caption, image, privacy, email)
VALUES ('Amazing beach view', 'https://hips.hearstapps.com/hmg-prod/images/champagne-beach-espiritu-santo-island-vanuatu-royalty-free-image-1655672510.jpg?crop=1.00xw:0.755xh;0,0.173xh&resize=1200:*',
'Public', 'tienpmkse170552@fpt.edu.vn');


INSERT INTO FRIEND(user_email, friend_email)
VALUES('tientien310103@gmail.com', 'tienpmkse170552@fpt.edu.vn');
INSERT INTO FRIEND(user_email, friend_email)
VALUES('tientien310103@gmail.com', 'phammackimtien@gmail.com');
INSERT INTO FRIEND(user_email, friend_email)
VALUES('tientien310103@gmail.com', 'locbpse170514@fpt.edu.vn');

INSERT INTO FRIEND(user_email, friend_email)
VALUES('phammackimtien@gmail.com', 'tientien310103@gmail.com');

INSERT INTO Messages(sender, receiver, content) 
	VALUES('tientien310103@gmail.com','phammackimtien@gmail.com','ban an com chua');

