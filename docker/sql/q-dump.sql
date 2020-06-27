create table chat_room
(
	chat_room_id bigint auto_increment,
	name varchar(20) null,
	created_at datetime default current_timestamp not null,
	updated_at datetime not null default CURRENT_TIMESTAMP on update current_timestamp,
	constraint chat_room_pk primary key (chat_room_id)
);

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,'TEST ROOM NAME','2020-06-27 01:33:09','2020-06-27 01:33:09'),(2,'TEST2 ROOM','2020-06-27 01:34:08','2020-06-27 01:34:08');
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

create table chat_room_user
(
	chat_room_user_id bigint auto_increment,
	chat_room_id bigint not null,
	user_id bigint not null,
	created_at datetime default current_timestamp not null,
	updated_at datetime not null default CURRENT_TIMESTAMP on update current_timestamp,
	constraint chat_room_user_pk
		primary key (chat_room_user_id)
);

LOCK TABLES `chat_room_user` WRITE;
/*!40000 ALTER TABLE `chat_room_user` DISABLE KEYS */;
INSERT INTO `chat_room_user` VALUES (1,1,1,'2020-06-27 01:34:19','2020-06-27 01:34:19'),(2,1,2,'2020-06-27 01:34:19','2020-06-27 01:34:19'),(3,1,3,'2020-06-27 01:34:24','2020-06-27 01:34:24'),(4,2,4,'2020-06-27 01:34:24','2020-06-27 01:34:24');
/*!40000 ALTER TABLE `chat_room_user` ENABLE KEYS */;
UNLOCK TABLES;

create table user
(
	user_id bigint auto_increment,
	email varchar(100) not null comment '이메일 정보',
	name varchar(50) not null comment '유저명',
	created_at datetime default current_timestamp not null,
	updated_at datetime not null default CURRENT_TIMESTAMP on update current_timestamp,
	constraint user_pk primary key (user_id)
);

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test@yopmail.com','test','2020-06-27 01:33:50','2020-06-27 01:33:50'),(2,'test2@yopmail.com','test2','2020-06-27 01:33:50','2020-06-27 01:33:50'),(3,'test3@yopmail.com','test3','2020-06-27 01:33:50','2020-06-27 01:33:50'),(4,'test4@yopmail.com','test4','2020-06-27 01:33:50','2020-06-27 01:33:50');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

create table split_money
(
	split_money_id bigint auto_increment,
	user_id bigint not null,
	chat_room_id bigint not null,
	token varchar(10) not null,
	number int not null,
	amount int not null,
	expired_at datetime null,
	created_at datetime default current_timestamp not null,
	updated_at datetime not null default CURRENT_TIMESTAMP on update current_timestamp,
	constraint split_money_pk
		primary key (split_money_id)
);

create unique index ux_chat_room_id_token on split_money (chat_room_id, token);

LOCK TABLES `split_money` WRITE;
/*!40000 ALTER TABLE `split_money` DISABLE KEYS */;
INSERT INTO `split_money` VALUES (24,1,1,'yv-',2,2,date_add(now(), interval 10 minute),'2020-06-20 01:33:50',now());
/*!40000 ALTER TABLE `split_money` ENABLE KEYS */;
UNLOCK TABLES;

create table split_money_user
(
	split_money_user_id bigint auto_increment,
	split_money_id bigint not null,
	user_id bigint null,
	amount int not null,
	created_at datetime default current_timestamp not null,
	updated_at datetime not null default CURRENT_TIMESTAMP on update current_timestamp,
	constraint split_money_user_pk
		primary key (split_money_user_id)
);

LOCK TABLES `split_money_user` WRITE;
/*!40000 ALTER TABLE `split_money_user` DISABLE KEYS */;
INSERT INTO `split_money_user` VALUES (1,24,null,1,now(),now());
INSERT INTO `split_money_user` VALUES (2,24,null,1,now(),now());
/*!40000 ALTER TABLE `split_money_user` ENABLE KEYS */;
UNLOCK TABLES;






