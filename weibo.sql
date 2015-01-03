/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50614
 Source Host           : localhost
 Source Database       : weibo

 Target Server Type    : MySQL
 Target Server Version : 50614
 File Encoding         : utf-8

 Date: 12/31/2014 16:30:16 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `wb_users`
-- ----------------------------
DROP TABLE IF EXISTS `wb_users`;
CREATE TABLE `wb_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `wb_users`
-- ----------------------------
BEGIN;
INSERT INTO `wb_users` VALUES ('1', 'yong', '111111', '3b08b61f5a5a617c10fd6b4c15d76a8f8692543d', null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `wb_weibo`
-- ----------------------------
DROP TABLE IF EXISTS `wb_weibo`;
CREATE TABLE `wb_weibo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `content` text,
  `image` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
