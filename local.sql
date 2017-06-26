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

/*Table structure for table `datadictionary` */

DROP TABLE IF EXISTS `datadictionary`;

CREATE TABLE `datadictionary` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ddName` varchar(50) DEFAULT NULL COMMENT '数据名字',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `checkedType` int(2) DEFAULT NULL COMMENT '是否已使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

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

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `rid` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rolename` varchar(50) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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

/*Table structure for table `xiaoimode` */

DROP TABLE IF EXISTS `xiaoimode`;

CREATE TABLE `xiaoimode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `triggerTime` bigint(20) DEFAULT NULL COMMENT '执行时间',
  `classId` int(1) DEFAULT NULL COMMENT '控制类型',
  `orders` varchar(10) DEFAULT NULL COMMENT '枚举值',
  `eaNumber` varchar(32) DEFAULT NULL COMMENT '电器标号',
  `argument` varchar(32) DEFAULT NULL COMMENT '执行参数',
  `mode` int(4) DEFAULT NULL COMMENT '情景模式',
  `groupNumber` int(20) DEFAULT NULL COMMENT '家庭组编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
  `orders` varchar(10) DEFAULT NULL COMMENT 'orders 枚举值',
  PRIMARY KEY (`id`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `xiaoitask_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `familygroup` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
