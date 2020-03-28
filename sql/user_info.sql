/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springsecurity

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-03-28 21:59:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_info` 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'zhangsan123', 'zhangsan', '$2a$10$4PcrIi3J.JFlSIcLT9CNbu2Nq1JLSGhvklFAJ4cWZ1W4F4L7tlMb6');
INSERT INTO `user_info` VALUES ('2', 'lisi345', 'lisi', '$2a$10$4PcrIi3J.JFlSIcLT9CNbu2Nq1JLSGhvklFAJ4cWZ1W4F4L7tlMb6');
