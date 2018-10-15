/*
Navicat MySQL Data Transfer

Source Server         : kong
Source Server Version : 50722
Source Host           : 120.77.203.152:3306
Source Database       : property

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-12 13:06:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL COMMENT '编号ID',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `userName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('11232', '111111', 'admin');
INSERT INTO `admin` VALUES ('11234', '222222', 'system');

-- ----------------------------
-- Table structure for `maintenance`
-- ----------------------------
DROP TABLE IF EXISTS `maintenance`;
CREATE TABLE `maintenance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  `repairId` int(11) NOT NULL COMMENT '该维修记录对应的报修单编号',
  `technicianId` int(11) NOT NULL COMMENT '维修人员的编号',
  `startTime` datetime NOT NULL COMMENT '维修开始的时间',
  PRIMARY KEY (`id`),
  KEY `idx_repairId` (`repairId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='维修记录表';

-- ----------------------------
-- Records of maintenance
-- ----------------------------
INSERT INTO `maintenance` VALUES ('12', '19', '2', '2018-10-11 23:27:00');

-- ----------------------------
-- Table structure for `repair`
-- ----------------------------
DROP TABLE IF EXISTS `repair`;
CREATE TABLE `repair` (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '报修单状态：0为被学生删除，1为未安排检修，2为已安排检修，3为待同意取消，\r\n  4为已同意取消，5为待验收，6为已验收，默认为被学生删除',
  `detail` varchar(10240) NOT NULL COMMENT '问题详情',
  `place` varchar(256) NOT NULL COMMENT '发生故障的物业的地点',
  `picMD5` varchar(256) DEFAULT NULL COMMENT '现场照片',
  `submitTime` datetime NOT NULL COMMENT '提交报修单的时间',
  `studentId` char(30) NOT NULL COMMENT '提交该报修单的学生的编号',
  PRIMARY KEY (`id`),
  KEY `idx_studentId` (`studentId`),
  CONSTRAINT `FK_STUDENTID` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='报修单表';

-- ----------------------------
-- Records of repair
-- ----------------------------
INSERT INTO `repair` VALUES ('19', '2', '天花板漏水', '25栋705', '84df5cf96a80016e074dda01e05bd2f5', '2018-09-26 15:12:54', '15251104224');
INSERT INTO `repair` VALUES ('20', '7', ' 天花板漏水', '25栋705', 'd602f541df4030cc0cff0551530edfb7', '2018-09-26 15:14:36', '15251104224');
INSERT INTO `repair` VALUES ('21', '0', ' 天花板漏水', '25栋705', 'b714dc5976a769000c35507e0c327e98', '2018-09-28 15:31:13', '15251104224');
INSERT INTO `repair` VALUES ('22', '0', ' 天花板漏水', '25栋705', '4cf156f36b360471cd8d46fe3e2af082', '2018-09-29 22:45:24', '15251104224');
INSERT INTO `repair` VALUES ('23', '0', '水龙头坏了', '28栋', 'fa233c6b363ed4622393bbe94488c465', '2018-09-29 22:45:58', '15251104224');
INSERT INTO `repair` VALUES ('24', '0', ' 桌子坏了', '25栋', '41ba50aac9d596163cd09bf134154685', '2018-09-29 22:46:13', '15251104224');
INSERT INTO `repair` VALUES ('25', '0', ' 凳子坏了', '25栋', 'a593fc4929e3c963f66179b7bccd5c1', '2018-09-29 22:46:23', '15251104224');
INSERT INTO `repair` VALUES ('26', '0', ' 柜子坏了', '28栋', '7f4b1a4ed525f2af391968dd7a857f25', '2018-10-03 17:07:11', '15251104224');
INSERT INTO `repair` VALUES ('27', '3', ' 天花板漏水', '25栋', '9bb4a8e61b1de71c64e95a66a276e400', '2018-10-03 17:25:42', '15251104218');
INSERT INTO `repair` VALUES ('29', '0', ' 电脑坏了', '25栋', '2b97b261-121a-4d17-b031-646b4a288a7e.jpg', '2018-10-10 20:35:20', '15251104224');
INSERT INTO `repair` VALUES ('30', '0', ' 电脑又坏了', '25栋', '77a52f67-9a15-4a26-9955-a426ed5fa27e.jpg', '2018-10-10 20:41:42', '15251104224');
INSERT INTO `repair` VALUES ('31', '3', ' 电脑坏坏的', '25栋', '059f6275-7a64-4fe2-8f01-f4fcb3cb8d28.jpg', '2018-10-10 20:43:58', '15251104224');
INSERT INTO `repair` VALUES ('32', '1', ' 电脑坏坏的', '25栋', '', '2018-10-10 20:49:38', '15251104224');
INSERT INTO `repair` VALUES ('33', '0', ' 被子坏了', '25栋', '3eb570be-19e2-45c9-90f6-a583fdd6d48c.jpg', '2018-10-10 20:51:03', '15251104224');
INSERT INTO `repair` VALUES ('34', '0', ' 被子又坏了', '25栋', 'd58fc6e7-4247-448d-baf0-87146e90b6c4.jpg', '2018-10-10 20:55:32', '15251104224');
INSERT INTO `repair` VALUES ('35', '0', ' 管道漏水了', '25栋', '6a1721b5-5d31-4bb7-a2cd-c4d6dd90f52a.jpg', '2018-10-10 20:56:49', '15251104224');
INSERT INTO `repair` VALUES ('36', '1', ' 地板爆裂', '25栋705', '7884e23b1d7465214e507aa908b6d432', '2018-10-10 23:18:05', '15251104224');
INSERT INTO `repair` VALUES ('37', '0', ' 天花板爆炸', '25栋619', 'd2bfb14f2d089a9e652990e129e813b', '2018-10-11 10:49:29', '15251104224');
INSERT INTO `repair` VALUES ('38', '3', ' 柜子坏了', '25栋', 'd056d37b-fe81-493d-a3d9-8833934b89cc.jpg', '2018-10-11 11:03:38', '15251104218');
INSERT INTO `repair` VALUES ('41', '1', ' 灯管坏了', '25栋', '65f439ae2cae3985445933cd629f64cf', '2018-10-11 21:01:46', '15251104224');
INSERT INTO `repair` VALUES ('42', '6', '花洒漏水很严重，都裂开了，需要换一个花洒', '', 'a8865f54d8ea6ee7a19b164f14d0eab8', '2018-10-11 21:03:50', '15251104224');
INSERT INTO `repair` VALUES ('43', '1', ' 天花板漏水，有很多灰尘掉下来', '25栋705', '592a9cf91fbf4effe3ed4cd6699f6893', '2018-10-11 22:47:46', '15251104224');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` char(30) NOT NULL COMMENT '学生ID',
  `name` varchar(50) NOT NULL COMMENT '学生姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `sexual` int(11) DEFAULT '0' COMMENT '性别：0为男1为女，默认为男',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  KEY `name` (`name`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1297034860', 'liangyibinx', '121212', '0', '1297034860', '123123123');
INSERT INTO `student` VALUES ('12970348601', '梁毅滨', '4233', '0', '12970348601', '123123');
INSERT INTO `student` VALUES ('15251104218', '谢土飞(15251104218)', '121212', '0', '', '');
INSERT INTO `student` VALUES ('15251104224', '温小辉', '232323', '0', '15251104224', '112112112');

-- ----------------------------
-- Table structure for `technician`
-- ----------------------------
DROP TABLE IF EXISTS `technician`;
CREATE TABLE `technician` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `number` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='维修人员表';

-- ----------------------------
-- Records of technician
-- ----------------------------
INSERT INTO `technician` VALUES ('1', 'xiaofei', '001', '13423343647');
INSERT INTO `technician` VALUES ('2', 'xiaohui', '002', '13447834475');
INSERT INTO `technician` VALUES ('3', 'xiaobin', '003', '13480398447');
INSERT INTO `technician` VALUES ('4', 'Ben', '004', '13420329897');
INSERT INTO `technician` VALUES ('5', 'Mary', '005', '18424543647');
INSERT INTO `technician` VALUES ('6', 'liang', '006', '15425844447');

-- ----------------------------
-- Table structure for `urgentrepair`
-- ----------------------------
DROP TABLE IF EXISTS `urgentrepair`;
CREATE TABLE `urgentrepair` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0为待查看，1为已查看，2为被学生取消，默认为待查看',
  `repairId` int(11) NOT NULL COMMENT '该催单对应的报修单编号',
  `studentId` char(11) NOT NULL COMMENT '发起该催单的学生的编号',
  `createTime` datetime NOT NULL COMMENT '催单的创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_studentId` (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='催单表';

-- ----------------------------
-- Records of urgentrepair
-- ----------------------------
INSERT INTO `urgentrepair` VALUES ('7', '0', '7', '15251104224', '2018-09-26 14:19:08');
INSERT INTO `urgentrepair` VALUES ('8', '0', '8', '15251104224', '2018-09-26 14:16:20');
INSERT INTO `urgentrepair` VALUES ('9', '0', '13', '15251104224', '2018-09-26 14:19:23');
INSERT INTO `urgentrepair` VALUES ('10', '0', '19', '15251104224', '2018-09-28 15:30:41');
INSERT INTO `urgentrepair` VALUES ('13', '0', '38', '15251104218', '2018-10-11 11:06:25');
INSERT INTO `urgentrepair` VALUES ('16', '0', '40', '15251104224', '2018-10-11 16:43:55');
INSERT INTO `urgentrepair` VALUES ('19', '0', '42', '15251104224', '2018-10-11 21:34:08');
INSERT INTO `urgentrepair` VALUES ('20', '0', '36', '15251104224', '2018-10-11 22:46:56');
INSERT INTO `urgentrepair` VALUES ('21', '0', '27', '15251104218', '2018-10-11 23:41:06');
