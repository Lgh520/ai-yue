CREATE TABLE aiyue.`user_info`
(
    `USER_ID`     varchar(16)  NOT NULL COMMENT '用户ID（手机号）',
    `USER_NAME`   varchar(64) DEFAULT NULL COMMENT '用户名',
    `PASSWORD`    varchar(255) NOT NULL COMMENT '密码',
    `USER_TYPE`   varchar(2)  DEFAULT NULL COMMENT '用户类型：0-普通用户，1-管理员',
    `IS_LOCK`     varchar(2)  DEFAULT NULL COMMENT '是否上锁：0-未锁，1-上锁',
    `CREATE_TIME` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户信息表';

CREATE TABLE aiyue.`consige_address`
(
    `CONSIGN_ID`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `USER_ID`         varchar(16)  DEFAULT NULL COMMENT '用户ID（手机号）',
    `ADDRESS`         varchar(255) DEFAULT NULL COMMENT '收货地址',
    `CONSIGNEE`       varchar(64)  DEFAULT NULL COMMENT '收货人',
    `CONSIGNEE_PHONE` varchar(32)  DEFAULT NULL COMMENT '收货人号码',
    `REMARK`          varchar(255) DEFAULT NULL COMMENT '备注',
    `CREATE_TIME`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`CONSIGN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户收货地址配置表';

CREATE TABLE aiyue.`sys_param`
(
    `PARAM_CODE`  varchar(32) DEFAULT NULL COMMENT 'code',
    `PARAM_KEY`   varchar(32) DEFAULT NULL COMMENT 'key',
    `PARAM_VALUE` varchar(32) DEFAULT NULL COMMENT 'value',
    `PARAM_NAME`  varchar(32) DEFAULT NULL COMMENT '名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '字典表';

CREATE TABLE aiyue.`order_info`
(
    `ORDER_ID`     bigint       NOT NULL COMMENT '订单号',
    `ORDER_MONEY`  bigint       NOT NULL COMMENT '订单金额',
    `USER_ID`      varchar(16) DEFAULT NULL COMMENT '用户ID',
    `ORDER_STATUS` varchar(4)   NOT NULL COMMENT '订单状态：A00-创建，A01-支付成功，A01-支付失败，B00-创建，B01-退款成功，B01-退款失败',
    `ORDER_TYPE`   varchar(255) NOT NULL COMMENT '订单类型：A-支付，B-退款',
    `CREATE_TIME`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '订单表';

CREATE TABLE aiyue.`trans_info`
(
    `TRANS_ID`     bigint       NOT NULL COMMENT '流水号',
    `ORDER_ID`     bigint       NOT NULL COMMENT '订单号',
    `TRANS_MONEY`  varchar(16)  NOT NULL COMMENT '流水金额',
    `TRANS_STATUS` varchar(4)   NOT NULL COMMENT '流水状态：A00-创建，A01-支付成功，A01-支付失败，B00-创建，B01-退款成功，B01-退款失败',
    `TRANS_TYPE`   varchar(255) NOT NULL COMMENT '流水类型：A-支付，B-退款',
    `CREATE_TIME`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`TRANS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '流水表';

CREATE TABLE aiyue.`book_info`
(
    `BOOK_ID`      bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `LEVEL_NUM`    varchar(12)  DEFAULT NULL COMMENT '推荐指数',
    `SUB_TITLE`    varchar(255) DEFAULT NULL COMMENT '副标题',
    `AUTHOR`       varchar(128) DEFAULT NULL COMMENT '作者',
    `PUB_DATE`     varchar(32)  DEFAULT NULL COMMENT '发版日期',
    `ORIGIN_TITLE` varchar(255) DEFAULT NULL COMMENT '源标题（国外源标题）',
    `BINDING`      varchar(32)  DEFAULT NULL COMMENT '装订方式',
    `PAGES`        varchar(32)  DEFAULT NULL COMMENT '总页数',
    `IMAGE_MEDIUM` varchar(255) DEFAULT NULL COMMENT '缩略图',
    `IMAGE_LARGE`  varchar(255) DEFAULT NULL COMMENT '大图',
    `PUBLISHER`    varchar(255) DEFAULT NULL COMMENT '出版社名称',
    `ISBN_10`      varchar(10)  DEFAULT NULL COMMENT '10位ISBN码',
    `ISBN_13`      varchar(13)  DEFAULT NULL COMMENT '13位ISBN码',
    `TITLE`        varchar(255) DEFAULT NULL COMMENT '书籍名称',
    `SUMMARY`      varchar(255) DEFAULT NULL COMMENT '内容简介',
    `PRICE`        varchar(32)  DEFAULT NULL COMMENT '销售价格',
    `BOOK_COUNTS`  int          DEFAULT NULL COMMENT '库存',
    `RENT_COUNTS`  bigint       DEFAULT NULL COMMENT '借阅次数',
    `GOODS_ID`     bigint       DEFAULT NULL COMMENT '货架ID(待定)',
    `CREATE_TIME`  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `AGE_TYPE`     varchar(32)  DEFAULT NULL COMMENT '年龄类型',
    `THEME_TYPE`   varchar(32)  DEFAULT NULL COMMENT '主题类型',
    PRIMARY KEY (`BOOK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '书籍表';

CREATE TABLE `book_rent`
(
    `RENT_ID`     bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `USER_ID`     varchar(16) DEFAULT NULL COMMENT '用户ID（手机号）',
    `BOOK_ID`     bigint      DEFAULT NULL COMMENT '书籍ID',
    `IS_BACK`     varchar(4)  DEFAULT NULL COMMENT '是否归还：0-未归还，1-已归还',
    `CREATE_TIME` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `BOOK_COUNT`  int         DEFAULT NULL COMMENT '借阅数量',
    PRIMARY KEY (`RENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='书籍借阅表';

CREATE TABLE aiyue.`delivery_record`
(
    `DELIVERY_ID`     bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
    `RENT_ID`         bigint      DEFAULT NULL COMMENT '书籍借阅ID',
    `DELIVERY_USER`   varchar(16) DEFAULT NULL COMMENT '配送用户ID',
    `DELIVERY_STATUS` varchar(4)  DEFAULT NULL COMMENT '配送状态：1-已接单，2-配送中，3-配送完成',
    `DELIVERY_TIME`   varchar(32) DEFAULT NULL COMMENT '配送时间（分）',
    `CREATE_TIME`     datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`     datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`DELIVERY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '配送记录表';



drop table user_info;
drop table consige_address;
drop table sys_param;
drop table order_info;
drop table trans_info;
drop table book_info;
drop table book_rent;
drop table delivery_record;
