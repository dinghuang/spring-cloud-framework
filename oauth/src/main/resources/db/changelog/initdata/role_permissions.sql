--liquibase formatted sql

--changeset dinghuang-2019-06-08-role-permissions-table-create:1
CREATE TABLE `role_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `url` varchar(200) DEFAULT NULL COMMENT '路径',
  `role_name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `object_version_number` bigint(20) DEFAULT '1',
  `created_by` varchar(50) DEFAULT '-1',
  `created_by_name` varchar(50) DEFAULT '-1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_by` varchar(50) DEFAULT '-1',
  `last_updated_by_name` varchar(50) DEFAULT '-1',
  `last_updated_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_name` (`role_name`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1126324192788180995 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';
--rollback DROP TABLE IF EXISTS `role_permissions`;
