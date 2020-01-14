--liquibase formatted sql

--changeset dinghuang-2019-06-07-user-account-table-create:1
CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(200) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `name` varchar(100) DEFAULT NULL COMMENT '用户姓名',
  `mobile_phone` varchar(200) DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(200) DEFAULT NULL COMMENT '号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `org_code` varchar(50) DEFAULT NULL COMMENT '组织编码',
  `object_version_number` bigint(20) DEFAULT '1',
  `created_by` varchar(50) DEFAULT '-1',
  `created_by_name` varchar(50) DEFAULT '-1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_by` varchar(50) DEFAULT '-1',
  `last_updated_by_name` varchar(50) DEFAULT '-1',
  `last_updated_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1126324192788180995 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
--rollback DROP TABLE IF EXISTS `user_account`;

--changeset dinghuang-2019-06-07-user-account-table-init-data:2
INSERT INTO `user_account` VALUES (1102453645963550723, 'dinghuang', 'admin', '丁煌', '18060804886', '1123','dinghuang123@gmail.com','1','1','dinghuang', '丁煌','2019-03-04 06:20:06', 'dinghuang', '丁煌', '2019-03-04 06:20:06');
INSERT INTO `user_account` VALUES (1102453645963550724, 'admin', 'admin', '管理员', '18060804887', '1123','dinghuang123@gmail.com','1','1','dinghuang', '丁煌','2019-03-04 06:20:06', 'dinghuang', '丁煌', '2019-03-04 06:20:06');
--rollback DELETE from user_account where id in(1102453645963550723,1102453645963550724);