/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.16 : Database - xiaoi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xiaoi` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `xiaoi`;

/*Table structure for table `administrate` */

DROP TABLE IF EXISTS `administrate`;

CREATE TABLE `administrate` (
  `aid` int(10) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `aname` varchar(50) NOT NULL COMMENT '管理员名字',
  `password` varchar(50) NOT NULL COMMENT '管理员登入密码',
  `createdate` datetime NOT NULL COMMENT '创建时间',
  `loginLastTime` timestamp NULL DEFAULT NULL COMMENT '最后的登入时间',
  `loginNumber` int(20) DEFAULT '0' COMMENT '登入次数',
  `rid` int(10) DEFAULT '2' COMMENT '角色id(1,高级管理员;2,普通管理员)',
  `realName` varchar(20) NOT NULL COMMENT '真实姓名',
  `phoneNumber` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `sex` varchar(10) DEFAULT NULL COMMENT '管理员性别',
  PRIMARY KEY (`aid`),
  KEY `aid_r2` (`rid`),
  CONSTRAINT `aid_r2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `administrate` */

insert  into `administrate`(`aid`,`aname`,`password`,`createdate`,`loginLastTime`,`loginNumber`,`rid`,`realName`,`phoneNumber`,`sex`) values (2,'2','2','2017-04-13 15:46:50','2017-04-13 15:46:52',0,2,'2','2',NULL),(9,'c','1','2016-12-28 17:06:55','2017-04-13 16:20:54',16,1,'汉库克','125478794548',NULL),(10,'admin','123','2017-05-17 15:57:21','2017-06-14 15:18:43',54,1,'jack','12345678911','男');

/*Table structure for table `channel` */

DROP TABLE IF EXISTS `channel`;

CREATE TABLE `channel` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `chanName` varchar(50) DEFAULT NULL COMMENT '频道名',
  `chanNumber` varchar(50) DEFAULT NULL COMMENT '频道号',
  `groupId` int(11) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  KEY `ci_gid` (`groupId`),
  CONSTRAINT `ci_gid` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `channel` */

/*Table structure for table `datadictionary` */

DROP TABLE IF EXISTS `datadictionary`;

CREATE TABLE `datadictionary` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ddName` varchar(50) DEFAULT NULL COMMENT '数据名字',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `checkedType` int(2) DEFAULT NULL COMMENT '是否已使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `datadictionary` */

insert  into `datadictionary`(`id`,`ddName`,`createTime`,`checkedType`) values (1,'空调使用情况','2017-02-07 17:58:38',1),(2,'电视','2017-02-08 11:32:25',NULL),(3,'电风扇','2017-02-08 11:32:43',NULL),(4,'洗衣机',NULL,NULL),(6,'公文',NULL,NULL),(8,'第三个',NULL,NULL),(9,'智能插座',NULL,NULL),(10,'带',NULL,NULL),(12,'gd',NULL,NULL),(13,'af',NULL,NULL),(14,'faa',NULL,NULL),(15,'热水器','2017-02-09 11:16:45',NULL),(16,'1','2017-03-03 02:58:55',NULL),(17,'1','2017-03-03 04:27:11',NULL),(18,'1','2017-04-12 04:40:16',NULL);

/*Table structure for table `family_user` */

DROP TABLE IF EXISTS `family_user`;

CREATE TABLE `family_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `groupId` int(10) DEFAULT NULL COMMENT '家庭组id',
  `userId` int(10) DEFAULT NULL COMMENT '用户id',
  `dna` varchar(20) DEFAULT NULL COMMENT '创建标识',
  PRIMARY KEY (`id`),
  KEY `g_id` (`groupId`),
  KEY `u_id` (`userId`),
  CONSTRAINT `g_id` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`),
  CONSTRAINT `u_id` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `family_user` */

insert  into `family_user`(`id`,`groupId`,`userId`,`dna`) values (7,7,2,'2'),(14,13,2,'2'),(21,21,1,'1'),(22,22,6,'6');

/*Table structure for table `familygroup` */

DROP TABLE IF EXISTS `familygroup`;

CREATE TABLE `familygroup` (
  `groupId` int(10) NOT NULL AUTO_INCREMENT COMMENT '家庭组id(自增)',
  `groupName` varchar(20) NOT NULL COMMENT '家庭组名字',
  `creationTime` datetime DEFAULT NULL COMMENT '创建时间',
  `groupNumber` int(24) DEFAULT NULL COMMENT '家庭组编号',
  `state` varchar(20) DEFAULT NULL COMMENT '国家',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `district` varchar(50) DEFAULT NULL COMMENT '街道',
  `groupPassword` varchar(20) DEFAULT NULL COMMENT '绑定家庭组时的验证密码',
  `managerId` int(10) DEFAULT NULL COMMENT '创建家庭组的用户ID',
  `versionNumber` int(30) DEFAULT '0' COMMENT '家庭组版本号',
  `groupIp` varchar(20) DEFAULT NULL COMMENT '家庭组IP',
  PRIMARY KEY (`groupId`,`groupName`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `familygroup` */

insert  into `familygroup`(`groupId`,`groupName`,`creationTime`,`groupNumber`,`state`,`city`,`district`,`groupPassword`,`managerId`,`versionNumber`,`groupIp`) values (7,'哈酷哈酷','2017-06-09 14:40:03',10007,NULL,NULL,NULL,'0362461236',2,9,NULL),(10,'中科院','2017-06-22 18:39:46',10010,NULL,NULL,NULL,'0018595608',1,2,NULL),(13,'水浒传','2017-06-26 10:47:02',10013,NULL,NULL,NULL,'8966253120',2,3,NULL),(19,'中科院','2017-06-27 15:37:03',10019,NULL,NULL,NULL,'8618639198',1,3,NULL),(21,'中科院','2017-06-28 16:43:46',10021,NULL,NULL,NULL,'7553223115',1,37,NULL),(22,'14','2017-06-29 10:21:01',10022,NULL,NULL,NULL,'7525006981',6,3,NULL);

/*Table structure for table `household` */

DROP TABLE IF EXISTS `household`;

CREATE TABLE `household` (
  `hid` int(10) NOT NULL AUTO_INCREMENT COMMENT '家电id',
  `classId` int(10) DEFAULT NULL COMMENT '家电类别(1,普通;2,智能)',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `eaName` varchar(20) DEFAULT NULL COMMENT '家电昵称',
  `groupId` int(10) DEFAULT NULL COMMENT '家庭组id',
  `roomId` int(10) DEFAULT NULL COMMENT '房间id',
  `eaNumber` varchar(100) DEFAULT NULL COMMENT '家电编号',
  `prop` bigint(50) DEFAULT NULL COMMENT '通讯参数，见链路协议',
  `stub` int(10) DEFAULT NULL COMMENT '智能索引(Key)',
  `type` varchar(20) DEFAULT NULL COMMENT '家电类型',
  `port` int(10) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`hid`),
  KEY `ah_bgb` (`classId`),
  KEY `nk_rid` (`roomId`),
  KEY `da_ffg` (`groupId`),
  CONSTRAINT `da_ffg` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`),
  CONSTRAINT `nk_rid` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `household` */

insert  into `household`(`hid`,`classId`,`brand`,`model`,`createTime`,`eaName`,`groupId`,`roomId`,`eaNumber`,`prop`,`stub`,`type`,`port`,`status`) values (10,2,NULL,'*','2017-06-29 10:21:50','电冰箱',22,5,'0-0-LIVING_ROOM#288793330400101121',288793330400101121,NULL,NULL,32,-1),(13,1,'LG','*','2017-06-29 17:59:45',NULL,21,10,'0-0-LIVING_ROOM#acLG539',0,539,'ac',NULL,NULL),(14,1,'SANYO','*','2017-06-29 18:00:47',NULL,21,12,'0-0-BALCONY#tvSANYO8058',0,8058,'tv',NULL,NULL);

/*Table structure for table `privilege` */

DROP TABLE IF EXISTS `privilege`;

CREATE TABLE `privilege` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `rid` int(10) DEFAULT NULL COMMENT '角色id(角色表外键)',
  `priName` varchar(50) DEFAULT NULL COMMENT '权限名字',
  `description` varchar(50) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  KEY `gh_rid` (`rid`),
  CONSTRAINT `gh_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `privilege` */

insert  into `privilege`(`id`,`rid`,`priName`,`description`) values (1,2,'家庭组管理','对家庭组的增删改查'),(2,2,'用户管理','对用户的增删改查'),(3,2,'家电管理','对家电的增删改查'),(4,2,'机器人小艾管理','对小艾的增删改查'),(5,2,'日志管理','对日志的增删改查');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `rid` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rolename` varchar(50) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`rid`,`rolename`) values (1,'高级管理员'),(2,'普通管理员');

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '房间id',
  `roomName` varchar(20) DEFAULT NULL COMMENT '房间名字',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `groupId` int(10) DEFAULT NULL COMMENT '家庭组id',
  `roomNickName` varchar(50) DEFAULT NULL COMMENT '房间昵称',
  `floor` int(10) DEFAULT '0' COMMENT '房间楼层(默认 0)',
  `parentId` varchar(20) DEFAULT NULL COMMENT '父节点标识',
  `roomNumber` varchar(20) DEFAULT NULL COMMENT '房间编号',
  `robot` varchar(20) DEFAULT NULL COMMENT '终端绑定',
  `creator` varchar(20) DEFAULT NULL COMMENT '终端创建',
  PRIMARY KEY (`id`),
  KEY `bn_roi` (`groupId`),
  CONSTRAINT `bn_roi` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `room` */

insert  into `room`(`id`,`roomName`,`createTime`,`groupId`,`roomNickName`,`floor`,`parentId`,`roomNumber`,`robot`,`creator`) values (5,'LIVING_ROOM','2017-06-29 10:21:26',22,NULL,0,NULL,'0-0-LIVING_ROOM','0030000200009902','0030000200009902'),(10,'LIVING_ROOM','2017-06-29 17:59:08',21,NULL,0,NULL,'0-0-LIVING_ROOM','0030000200000102','0030000200000102'),(11,'BED_ROOM','2017-06-29 17:59:26',21,NULL,0,NULL,'0-0-BED_ROOM',NULL,'0030000200000102'),(12,'BALCONY','2017-06-29 18:00:33',21,NULL,0,NULL,'0-0-BALCONY','0030000100009702','0030000100009702'),(13,'STAIRCASE_ROOM','2017-06-29 18:13:11',21,NULL,0,NULL,'0-0-STAIRCASE_ROOM','0030000200000102','0030000200000102'),(14,'DINING_ROOM','2017-06-29 18:13:39',21,NULL,0,NULL,'0-0-DINING_ROOM',NULL,'0030000200000102');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userId` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id(自增)',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `userPassword` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `accessper` int(10) DEFAULT '1' COMMENT '用户登入状态',
  `userPhoneNumber` varchar(50) DEFAULT NULL COMMENT '手机号',
  `userSex` varchar(10) DEFAULT NULL COMMENT '用户性别',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `loginLastTimes` datetime DEFAULT NULL COMMENT '最后登录时间',
  `loginLastIp` varchar(20) DEFAULT NULL COMMENT '用户最后登录的IP',
  PRIMARY KEY (`userId`),
  KEY `jk_id` (`loginLastIp`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`userId`,`userName`,`userPassword`,`accessper`,`userPhoneNumber`,`userSex`,`createTime`,`loginLastTimes`,`loginLastIp`) values (1,'jock','e10adc3949ba59abbe56e057f20f883e',1,'15570895417','男士','2017-06-05 16:27:42','2017-06-29 17:57:55','2'),(2,'丘丘','6c2d1c105a9f5d871a6ea5f9496158c6',1,'18923881858','女士','2017-06-06 16:12:30','2017-06-15 14:40:53','2'),(4,'goog','93be52ba8f828a41647f919a5b646221',1,'18086107575','男士','2017-06-07 10:20:11','2017-06-07 10:34:37','2'),(5,'测试','123',1,'18086107515','男','2017-06-09 11:17:17','2017-06-09 15:21:32','2'),(6,'suchun1237','e10adc3949ba59abbe56e057f20f883e',1,'18688707348','男士','2017-06-22 16:29:02','2017-06-29 18:27:42',NULL),(7,'杰','02bd783ae7c2389d200ead9416b9669e',1,'15130933031','男士','2017-06-27 11:56:40','2017-06-28 16:21:32',NULL),(8,'你好啊','e10adc3949ba59abbe56e057f20f883e',1,'12345678910','男','2017-06-29 16:34:29',NULL,NULL);

/*Table structure for table `versions` */

DROP TABLE IF EXISTS `versions`;

CREATE TABLE `versions` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '版本id',
  `versionName` varchar(50) DEFAULT NULL COMMENT '版本名',
  `versionNumber` int(10) DEFAULT NULL COMMENT '版本号',
  `versionPackage` varchar(50) DEFAULT NULL COMMENT '版本包名',
  `upgradeClass` varchar(50) DEFAULT NULL COMMENT '更新类容',
  `upgradeTime` datetime DEFAULT NULL COMMENT '更新时间',
  `versionType` int(11) DEFAULT '0' COMMENT '平台类型(1,android;2,ios; 3,小艾;4,其它)',
  `versionUrl` varchar(100) DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `versions` */

insert  into `versions`(`id`,`versionName`,`versionNumber`,`versionPackage`,`upgradeClass`,`upgradeTime`,`versionType`,`versionUrl`) values (1,'0.0.7',7,'com.ihakul.i.listen','test','2017-05-27 15:08:32',3,'/upload/unisound-listen-0.0.7-SNAPSHOT.apk'),(2,'0.0.7',7,'com.ihakul.i.control','test','2017-05-27 15:09:06',3,'/upload/protocol-wrapper-0.0.7-SNAPSHOT.apk'),(3,'0.0.7',7,'com.ihakul.i.server','update','2017-05-27 15:09:38',3,'/upload/i-http-server-0.0.7-SNAPSHOT.apk'),(4,'8',8,'com.ihakul.i.version.update.activity','test','2017-05-27 15:11:49',3,'/upload/Ihakul_VersionUpdate-release-1.8.apk'),(5,'0.0.14',14,'com.ihakul.i.translater','tes','2017-05-27 15:12:29',3,'/upload/Translater-0.1.4-SNAPSHOT.apk');

/*Table structure for table `xiaoaisession` */

DROP TABLE IF EXISTS `xiaoaisession`;

CREATE TABLE `xiaoaisession` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gid` varchar(10) DEFAULT NULL COMMENT 'session全局id',
  `nid` varchar(10) DEFAULT NULL COMMENT 'session在本台服务器上的ID',
  `deviceId` varchar(10) DEFAULT NULL COMMENT '客户端ID',
  `host` varchar(50) DEFAULT NULL COMMENT 'session绑定的服务器IP',
  `account` varchar(50) DEFAULT NULL COMMENT 'session绑定的账号',
  `channel` varchar(20) DEFAULT NULL COMMENT '终端设备类型',
  `deviceModel` varchar(20) DEFAULT NULL COMMENT '终端设备型号',
  `clientVersion` varchar(20) DEFAULT NULL COMMENT '终端应用版本',
  `systemVersion` varchar(20) DEFAULT NULL COMMENT '终端系统版本',
  `bindTime` longtext COMMENT '登录时间',
  `heartbeat` longtext COMMENT '心跳时间',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '维度',
  `location` varchar(50) DEFAULT NULL COMMENT '位置',
  `apnsAble` int(11) DEFAULT NULL COMMENT 'apns推送状态',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `xiaoaisession` */

/*Table structure for table `xiaoi` */

DROP TABLE IF EXISTS `xiaoi`;

CREATE TABLE `xiaoi` (
  `xid` int(10) NOT NULL AUTO_INCREMENT COMMENT '终端id',
  `xname` varchar(20) DEFAULT NULL COMMENT '终端名称',
  `onlineState` int(11) DEFAULT NULL COMMENT '在线状态(0,不在线;1,在线)',
  `xiaoNumber` varchar(20) DEFAULT NULL COMMENT '终端编号',
  `groupId` int(10) DEFAULT NULL COMMENT '家庭组的id',
  `xiaoType` int(10) DEFAULT NULL COMMENT '终端类型(1,普通;2时尚))',
  `activationTime` datetime DEFAULT NULL COMMENT '激活时间',
  `xiaoIp` varchar(50) DEFAULT NULL COMMENT '小艾ip',
  `mode` int(10) DEFAULT NULL COMMENT '终端情景模式(10-标准模式；20-睡眠模式；30-离家模式)',
  `volume` int(10) DEFAULT NULL COMMENT '扬声器输出音量(取值范围 1-100，当设置为-1 时，终端静音)',
  PRIMARY KEY (`xid`),
  KEY `xv_hj` (`groupId`),
  CONSTRAINT `xv_hj` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoi` */

insert  into `xiaoi`(`xid`,`xname`,`onlineState`,`xiaoNumber`,`groupId`,`xiaoType`,`activationTime`,`xiaoIp`,`mode`,`volume`) values (2,'哈酷小ⅰ',0,'0030000200000202',13,2,'2017-06-28 18:02:03','192.168.2.127',NULL,50),(7,'哈酷小ⅰ',1,'0030000200009902',22,2,'2017-06-29 18:06:35','192.168.2.137',NULL,50),(11,'哈酷小ⅰ',0,'0030000200000102',21,2,'2017-06-29 18:13:39','192.168.2.113',NULL,50),(12,'哈酷小ⅰ',1,'0030000100009702',21,2,'2017-06-29 18:06:35','192.168.2.118',NULL,50);

/*Table structure for table `xiaoilog` */

DROP TABLE IF EXISTS `xiaoilog`;

CREATE TABLE `xiaoilog` (
  `lid` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `usingDetails` varchar(50) DEFAULT NULL COMMENT '使用详情',
  `userTime` datetime DEFAULT NULL COMMENT '使用时间',
  `xid` int(10) DEFAULT NULL COMMENT '终端id',
  `hid` int(10) DEFAULT NULL COMMENT '家电id',
  PRIMARY KEY (`lid`),
  KEY `ca_xid` (`xid`),
  KEY `ca_hid` (`hid`),
  CONSTRAINT `ca_hid` FOREIGN KEY (`hid`) REFERENCES `household` (`hid`),
  CONSTRAINT `ca_xid` FOREIGN KEY (`xid`) REFERENCES `xiaoi` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `xiaoilog` */

/*Table structure for table `xiaoimode` */

DROP TABLE IF EXISTS `xiaoimode`;

CREATE TABLE `xiaoimode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `triggerTime` bigint(20) DEFAULT NULL COMMENT '执行时间',
  `classId` int(1) DEFAULT NULL COMMENT '控制类型',
  `orders` varchar(10) DEFAULT NULL COMMENT '枚举值',
  `eaNumber` varchar(100) DEFAULT NULL COMMENT '电器标号',
  `argument` varchar(32) DEFAULT NULL COMMENT '执行参数',
  `mode` int(4) DEFAULT NULL COMMENT '情景模式',
  `groupNumber` int(20) DEFAULT NULL COMMENT '家庭组编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoimode` */

insert  into `xiaoimode`(`id`,`triggerTime`,`classId`,`orders`,`eaNumber`,`argument`,`mode`,`groupNumber`) values (3,1498645500000,1,'DEVICE_ON','0-0-LIVING_ROOM#acLG539','POWER,POWER_ON',10,10021);

/*Table structure for table `xiaoisms` */

DROP TABLE IF EXISTS `xiaoisms`;

CREATE TABLE `xiaoisms` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `OperID` varchar(30) NOT NULL COMMENT '已开通的帐号名称',
  `OperPass` varchar(30) NOT NULL COMMENT '与帐号名称对应的密码',
  `AppendID` int(20) DEFAULT NULL COMMENT '附加号码',
  `url` varchar(50) DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoisms` */

insert  into `xiaoisms`(`id`,`OperID`,`OperPass`,`AppendID`,`url`) values (1,'ceshfj1','n9JUBHgw',123456,'http://hsms.guodulink.net:9007/QxtSms/QxtFirewall');

/*Table structure for table `xiaoismsmessage` */

DROP TABLE IF EXISTS `xiaoismsmessage`;

CREATE TABLE `xiaoismsmessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `HeadMessage` varchar(200) DEFAULT NULL COMMENT '发送的头消息',
  `YZMessage` varchar(200) DEFAULT NULL COMMENT '验证消息',
  `Messageone` varchar(200) DEFAULT NULL COMMENT '消息',
  `Messagetwo` varchar(200) DEFAULT NULL COMMENT '消息',
  `minuteTime` int(11) DEFAULT NULL COMMENT '短信有效时间(分钟)',
  `MessageNumber` varchar(30) DEFAULT NULL COMMENT '消息编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoismsmessage` */

insert  into `xiaoismsmessage`(`id`,`HeadMessage`,`YZMessage`,`Messageone`,`Messagetwo`,`minuteTime`,`MessageNumber`) values (1,'【哈酷智能】','您注册的验证码为:',',此验证码','分钟之内有效。',10,NULL),(2,'【哈酷智能】','','祝您快乐生活每一天!',NULL,NULL,NULL);

/*Table structure for table `xiaoitask` */

DROP TABLE IF EXISTS `xiaoitask`;

CREATE TABLE `xiaoitask` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `creationTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `triggerTime` bigint(20) DEFAULT NULL COMMENT '执行时间',
  `things` varchar(100) DEFAULT NULL COMMENT '任务详情',
  `rules` varchar(50) DEFAULT NULL COMMENT '重复规则',
  `object` varchar(50) DEFAULT NULL COMMENT '任务类型',
  `groupId` int(11) DEFAULT NULL COMMENT '家庭组Id',
  `orders` varchar(20) DEFAULT NULL COMMENT 'orders 枚举值',
  PRIMARY KEY (`id`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `xiaoitask_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoitask` */

/*Table structure for table `xiaoiurl` */

DROP TABLE IF EXISTS `xiaoiurl`;

CREATE TABLE `xiaoiurl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `url` varchar(100) NOT NULL COMMENT 'url地址',
  `urlRemark` varchar(200) DEFAULT NULL COMMENT 'url地址说明',
  `urlNumber` int(20) DEFAULT NULL COMMENT 'url编号',
  `other` varchar(50) DEFAULT NULL COMMENT '其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `xiaoiurl` */

insert  into `xiaoiurl`(`id`,`url`,`urlRemark`,`urlNumber`,`other`) values (1,'192.168.2.112','本地ip',10001,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
