--liquibase formatted sql

--changeset dinghuang-2019-06-08-role-table-create:1
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `object_version_number` bigint(20) DEFAULT '1',
  `created_by` varchar(50) DEFAULT '-1',
  `created_by_name` varchar(50) DEFAULT '-1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_by` varchar(50) DEFAULT '-1',
  `last_updated_by_name` varchar(50) DEFAULT '-1',
  `last_updated_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1126324192788180995 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
--rollback DROP TABLE IF EXISTS `role`;

--changeset dinghuang-2019-06-08-role-table-init-data:2
INSERT INTO `role` VALUES (1102453645963550725, 1102453645963550723, 'employee' ,'1','dinghuang', '丁煌','2019-03-04 06:20:06', 'dinghuang', '丁煌', '2019-03-04 06:20:06');
INSERT INTO `role` VALUES (1102453645963550726, 1102453645963550724, 'admin', '1','dinghuang', '丁煌','2019-03-04 06:20:06', 'dinghuang', '丁煌', '2019-03-04 06:20:06');
--rollback DELETE from role where id in(1102453645963550725,1102453645963550726);