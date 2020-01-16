--liquibase formatted sql

--changeset dinghuang-2018-03-04-test-table-create:1
CREATE TABLE test (
ID BIGINT PRIMARY KEY NOT NULL,
NAME CHARACTER VARYING ( 19 ) NOT NULL,
SEX CHARACTER VARYING ( 19 ) NOT NULL,
OBJECT_VERSION_NUMBER BIGINT NOT NULL DEFAULT 0,
CREATED_BY CHARACTER VARYING ( 50 ) NOT NULL DEFAULT '-1',
CREATED_BY_NAME CHARACTER VARYING ( 50 ) NOT NULL DEFAULT '-1',
CREATION_DATE DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
LAST_UPDATED_BY CHARACTER VARYING ( 50 ) NOT NULL DEFAULT '-1',
LAST_UPDATED_BY_NAME CHARACTER VARYING ( 50 ) NOT NULL DEFAULT '-1',
LAST_UPDATED_DATE DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE test IS '测试对象';
COMMENT ON COLUMN test.ID IS '主键';
COMMENT ON COLUMN test.NAME IS '姓名';
COMMENT ON COLUMN test.SEX IS '性别';
COMMENT ON COLUMN test.OBJECT_VERSION_NUMBER IS '乐观锁版本号';
COMMENT ON COLUMN test.CREATED_BY IS '创建人';
COMMENT ON COLUMN test.CREATED_BY_NAME IS '创建人姓名';
COMMENT ON COLUMN test.CREATION_DATE IS '创建时间';
COMMENT ON COLUMN test.LAST_UPDATED_BY IS '最后更新人';
COMMENT ON COLUMN test.LAST_UPDATED_BY_NAME IS '最后更新人姓名';
COMMENT ON COLUMN test.LAST_UPDATED_DATE IS '最后更新时间';
--rollback DROP TABLE TEST;

--changeset dinghuang-2018-03-04-test-table-init-data:2
INSERT INTO "public"."test" ( "id", "name", "sex", "object_version_number", "created_by", "created_by_name", "creation_date", "last_updated_by", "last_updated_by_name", "last_updated_date" )
VALUES
	( 1, '1                  ', '2                  ', 1, '-1                                                ', '-1                                                ', '2020-01-16', '-1                                                ', '-1                                                ', '2020-01-16' );
--rollback DELETE from TEST where id in(1);