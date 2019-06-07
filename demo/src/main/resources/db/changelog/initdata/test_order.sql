--liquibase formatted sql

--changeset dinghuang-2018-03-04-test-order-table-create:1
CREATE TABLE `test_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `order_name` varchar(20) DEFAULT NULL COMMENT '订单名称',
  `object_version_number` bigint(20) DEFAULT '1',
  `created_by` varchar(50) DEFAULT '-1',
  `created_by_name` varchar(50) DEFAULT '-1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_by` varchar(50) DEFAULT '-1',
  `last_updated_by_name` varchar(50) DEFAULT '-1',
  `last_updated_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--rollback DROP TABLE IF EXISTS `test_order`;

--changeset dinghuang-2018-03-04-test-order-table-init-data:2
INSERT INTO `test_order` VALUES (1102453645963550721, '订单1', 'order1', 1, 'dinghuang', '丁煌','2019-03-04 06:20:06', 'dinghuang', '丁煌', '2019-03-04 06:20:06');
--rollback DELETE from test_order where id in(1102453645963550721);