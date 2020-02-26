/*
Navicat MySQL Data Transfer

Source Server         : 123.57.164.79
Source Server Version : 50727
Source Host           : 123.57.164.79:3306
Source Database       : web

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-02-26 14:02:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sms_admin
-- ----------------------------
DROP TABLE IF EXISTS `sms_admin`;
CREATE TABLE `sms_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(120) DEFAULT NULL COMMENT '账户',
  `nickname` varchar(120) DEFAULT NULL COMMENT '昵称',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT NULL COMMENT '状态（0：停用，1：正常）',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(1024) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of sms_admin
-- ----------------------------
INSERT INTO `sms_admin` VALUES ('1', 'admin', 'admin', '2019-11-13 11:21:49', '2019-11-12 21:57:16', '1', '$2a$10$dHIxV4PRJ7vAT2zJ9MvEL.ofcCxyELGmj3hu9I2B18N2W08j76Mei', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('22', 'test', 'asdfasdf', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('23', 'test1', 'test1', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '0', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('24', 'test2', 'test2', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('25', 'test3', 'test3', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('26', 'test4', 'test4', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('27', 'test5', 'test5', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('28', 'test6', 'test6', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('29', 'test7', 'test7', '2019-11-21 19:44:28', '2019-11-21 19:44:34', '1', '$2a$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('30', 'test8', 'test8', '2019-11-22 11:02:15', '2019-11-22 11:02:18', '1', '$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('31', 'test9', 'test9', '2019-11-22 11:02:15', '2019-11-22 11:02:18', '1', '$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('32', 'test10', 'test10', '2019-11-22 11:02:15', '2019-11-22 11:02:18', '1', '$10$ZJHoSQhZsiqnLcug6YxJUebzcr/IBBJAbUFgwcYfugs9gqrE2krhm', 'shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('33', 'test11', 'test11', null, '2019-11-22 13:52:45', '1', '$2a$10$8Eb4iDZY0MoXZV3YVC9S..vk11K8vtQRqQrPefn4acVS.H1MvvOGm', 'shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg');
INSERT INTO `sms_admin` VALUES ('34', 'rancho', 'rancho', null, '2019-11-27 22:53:15', '1', '$2a$10$hiIDTJIT2UNDXU3YSwIENuezARCd0br2K8ZT/o4wpYn.hycA7lx/i', null);

-- ----------------------------
-- Table structure for sms_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sms_admin_role`;
CREATE TABLE `sms_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL COMMENT '管理员id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='管理员角色表';

-- ----------------------------
-- Records of sms_admin_role
-- ----------------------------
INSERT INTO `sms_admin_role` VALUES ('26', '33', '37', '2019-11-22 17:20:15');
INSERT INTO `sms_admin_role` VALUES ('43', '34', '40', '2020-01-21 09:51:30');
INSERT INTO `sms_admin_role` VALUES ('44', '22', '1', '2020-02-24 11:36:42');
INSERT INTO `sms_admin_role` VALUES ('45', '22', '37', '2020-02-24 11:36:42');

-- ----------------------------
-- Table structure for sms_menu
-- ----------------------------
DROP TABLE IF EXISTS `sms_menu`;
CREATE TABLE `sms_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '权限值',
  `type` int(1) DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `uri` varchar(255) DEFAULT NULL COMMENT 'uri地址',
  `status` int(1) DEFAULT NULL COMMENT '状态（0禁用，1启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `icon` varchar(512) DEFAULT NULL COMMENT 'icon',
  `is_hidden` int(1) DEFAULT NULL COMMENT '是否隐藏->0：否，1：是',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sms_menu
-- ----------------------------
INSERT INTO `sms_menu` VALUES ('1', '0', '系统管理', 'system', '0', '/system', '1', '2019-11-15 10:25:18', 'Layout', 'system', '0', '1');
INSERT INTO `sms_menu` VALUES ('2', '1', '管理员', 'admin:list', '1', '/admin/list', '1', '2019-11-15 10:26:00', 'sms/admin/index', 'admin', '0', '1');
INSERT INTO `sms_menu` VALUES ('3', '1', '角色', 'role:list', '1', '/role/list', '1', '2019-11-16 12:53:54', 'sms/role/index', 'role', '0', '2');
INSERT INTO `sms_menu` VALUES ('5', '2', '添加管理员', 'admin:save', '2', '/admin/save', '1', '2019-11-21 16:31:52', '', '', '1', '1');
INSERT INTO `sms_menu` VALUES ('11', '2', '修改管理员', 'admin:update', '2', '/admin/update', '1', '2019-11-21 16:31:52', '', '', '1', '2');
INSERT INTO `sms_menu` VALUES ('12', '3', '添加角色', 'role:save', '2', '/role/save', '1', '2019-11-16 12:53:54', '', '', '1', '1');
INSERT INTO `sms_menu` VALUES ('13', '3', '修改角色', 'role:update', '2', '/role/update', '1', '2020-01-21 10:05:32', null, null, '1', '2');
INSERT INTO `sms_menu` VALUES ('14', '3', '删除角色', 'role:delete', '2', '/role/delete', '1', '2020-01-21 10:06:24', null, null, '1', '3');
INSERT INTO `sms_menu` VALUES ('15', '2', '禁用|启用管理员', 'admin:updateStatus', '2', '/admin/updateStatus', '1', '2020-01-21 10:08:11', null, null, '1', '3');

-- ----------------------------
-- Table structure for sms_role
-- ----------------------------
DROP TABLE IF EXISTS `sms_role`;
CREATE TABLE `sms_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` int(1) DEFAULT NULL COMMENT '状态（0禁用，1启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sms_role
-- ----------------------------
INSERT INTO `sms_role` VALUES ('1', 'admin', '超级管理员', '1', '2019-11-15 10:26:52');
INSERT INTO `sms_role` VALUES ('37', 'test', '测试角色', '1', '2019-11-19 10:04:18');
INSERT INTO `sms_role` VALUES ('40', 'test1', 'test', '1', '2020-01-20 17:47:01');
INSERT INTO `sms_role` VALUES ('43', '而我却二群', '', '1', '2020-02-25 10:57:18');
INSERT INTO `sms_role` VALUES ('44', '1', '1', '1', '2020-02-25 10:57:42');
INSERT INTO `sms_role` VALUES ('45', 'del', 'del', '1', '2020-02-25 13:21:36');

-- ----------------------------
-- Table structure for sms_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sms_role_menu`;
CREATE TABLE `sms_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of sms_role_menu
-- ----------------------------
INSERT INTO `sms_role_menu` VALUES ('36', '38', '1', '2019-11-22 16:22:25');
INSERT INTO `sms_role_menu` VALUES ('37', '38', '3', '2019-11-22 16:22:25');
INSERT INTO `sms_role_menu` VALUES ('49', '40', '1', '2020-01-21 09:36:24');
INSERT INTO `sms_role_menu` VALUES ('50', '40', '2', '2020-01-21 09:36:24');
INSERT INTO `sms_role_menu` VALUES ('51', '40', '11', '2020-01-21 09:36:24');
INSERT INTO `sms_role_menu` VALUES ('52', '40', '6', '2020-01-21 09:36:24');
INSERT INTO `sms_role_menu` VALUES ('53', '40', '9', '2020-01-21 09:36:24');
INSERT INTO `sms_role_menu` VALUES ('63', '44', '1', '2020-02-25 10:57:42');
INSERT INTO `sms_role_menu` VALUES ('64', '44', '2', '2020-02-25 10:57:42');
INSERT INTO `sms_role_menu` VALUES ('65', '44', '5', '2020-02-25 10:57:42');
INSERT INTO `sms_role_menu` VALUES ('66', '37', '1', '2020-02-25 12:51:20');
INSERT INTO `sms_role_menu` VALUES ('67', '37', '2', '2020-02-25 12:51:20');
INSERT INTO `sms_role_menu` VALUES ('68', '37', '5', '2020-02-25 12:51:20');
INSERT INTO `sms_role_menu` VALUES ('69', '37', '3', '2020-02-25 12:51:20');
INSERT INTO `sms_role_menu` VALUES ('79', '43', '1', '2020-02-26 10:22:10');
INSERT INTO `sms_role_menu` VALUES ('80', '43', '2', '2020-02-26 10:22:10');
INSERT INTO `sms_role_menu` VALUES ('81', '43', '11', '2020-02-26 10:22:10');
INSERT INTO `sms_role_menu` VALUES ('82', '43', '15', '2020-02-26 10:22:10');
INSERT INTO `sms_role_menu` VALUES ('83', '45', '1', '2020-02-26 11:57:21');
INSERT INTO `sms_role_menu` VALUES ('84', '45', '2', '2020-02-26 11:57:21');
INSERT INTO `sms_role_menu` VALUES ('85', '45', '5', '2020-02-26 11:57:21');
INSERT INTO `sms_role_menu` VALUES ('86', '45', '11', '2020-02-26 11:57:21');
INSERT INTO `sms_role_menu` VALUES ('87', '45', '15', '2020-02-26 11:57:21');
