--liquibase formatted sql

--changeset dinghuang-2018-03-04-test-order-table-create:1
CREATE TABLE `test_order` (
  `uuid` bigint(50) NOT NULL COMMENT '主键',
  `customer_name` varchar(50) NOT NULL DEFAULT '',
  `description` varchar(500) DEFAULT NULL,
  `update_user` bigint(50) NOT NULL,
  `create_user` bigint(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `lock_user` bigint(50) NOT NULL DEFAULT '0',
  `lock_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `lock_key` varchar(50) DEFAULT NULL,
  `version` bigint(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE IF EXISTS `test_order`;

--changeset dinghuang-2018-03-04-test-order-table-init-data:2
INSERT INTO `test_order` VALUES (1102453645963550721, 'string', 'string', 16320, 16320, '2019-03-04 06:20:06', '2019-03-04 06:20:06', 0, 0, '2019-03-04 06:20:06', NULL, 0);
INSERT INTO `test_order` VALUES (1102453952537800706, 'string', 'string', 16320, 16320, '2019-03-04 06:21:20', '2019-03-04 06:21:20', 0, 0, '2019-03-04 06:21:20', NULL, 0);
INSERT INTO `test_order` VALUES (1102453969155633153, 'string', 'string', 16320, 16320, '2019-03-04 06:21:24', '2019-03-04 06:21:24', 0, 0, '2019-03-04 06:21:24', NULL, 0);
--rollback DELETE from test_order where uuid in(1102453645963550721,1102453952537800706,1102453969155633153);