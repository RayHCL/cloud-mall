

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_connect
-- ----------------------------
DROP TABLE IF EXISTS `app_connect`;
CREATE TABLE `app_connect`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `user_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '本系统userId',
  `app_id` tinyint NULL DEFAULT NULL COMMENT '第三方系统id 1：微信小程序',
  `nick_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '第三方系统昵称',
  `image_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '第三方系统头像',
  `biz_user_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '第三方系统userid',
  `biz_unionid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '第三方系统unionid',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_app_id`(`user_id` ASC, `app_id` ASC) USING BTREE COMMENT '用户id和appid联合索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `area_id` bigint NOT NULL AUTO_INCREMENT,
  `area_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  PRIMARY KEY (`area_id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE COMMENT '上级id'
) ENGINE = InnoDB AUTO_INCREMENT = 659006000003 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for attach_file
-- ----------------------------
DROP TABLE IF EXISTS `attach_file`;
CREATE TABLE `attach_file`  (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` int NULL DEFAULT NULL COMMENT '文件大小',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `file_join_id` bigint NULL DEFAULT NULL COMMENT '文件关联的表主键id',
  `file_join_type` tinyint NULL DEFAULT NULL COMMENT '文件关联表类型：1 商品表  FileJoinType',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for basket
-- ----------------------------
DROP TABLE IF EXISTS `basket`;
CREATE TABLE `basket`  (
  `basket_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `prod_id` bigint UNSIGNED NOT NULL COMMENT '产品ID',
  `sku_id` bigint UNSIGNED NOT NULL COMMENT 'SkuID',
  `open_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
  `prod_count` int NOT NULL DEFAULT 0 COMMENT '购物车产品个数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '购物时间',
  PRIMARY KEY (`basket_id`) USING BTREE,
  UNIQUE INDEX `uk_user_shop_sku`(`sku_id` ASC, `open_id` ASC, `shop_id` ASC) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  INDEX `user_id`(`open_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table`  (
  `branch_id` bigint NOT NULL COMMENT '分支事务ID',
  `xid` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '全局事务ID',
  `transaction_id` bigint NULL DEFAULT NULL COMMENT '全局事务ID，不带TC地址',
  `resource_group_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '资源分组ID',
  `resource_id` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '资源ID',
  `branch_type` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '事务模式，AT、XA等',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `client_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `application_data` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '应用数据',
  `gmt_create` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`branch_id`) USING BTREE,
  INDEX `idx_xid`(`xid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `brand_id` bigint UNSIGNED NOT NULL COMMENT '主键',
  `brand_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '品牌名称',
  `brand_pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `user_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `memo` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `seq` int NULL DEFAULT 1 COMMENT '顺序',
  `status` int NOT NULL DEFAULT 1 COMMENT '默认是1，表示正常状态,0为下线状态',
  `brief` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简要描述',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '内容',
  `rec_time` datetime NULL DEFAULT NULL COMMENT '记录时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `first_char` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '品牌首字母',
  PRIMARY KEY (`brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '类目ID',
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '父节点',
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '产品类目名称',
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类目图标',
  `pic` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类目的显示图片',
  `seq` int NOT NULL COMMENT '排序',
  `status` int NOT NULL DEFAULT 1 COMMENT '默认是1，表示正常状态,0为下线状态',
  `create_time` datetime NOT NULL COMMENT '记录时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `status_index`(`status` ASC) USING BTREE,
  INDEX `ab_index`(`category_name` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '产品类目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for category_brand
-- ----------------------------
DROP TABLE IF EXISTS `category_brand`;
CREATE TABLE `category_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类id',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  INDEX `brand_id`(`brand_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for category_prop
-- ----------------------------
DROP TABLE IF EXISTS `category_prop`;
CREATE TABLE `category_prop`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类id',
  `prop_id` bigint NULL DEFAULT NULL COMMENT '商品属性id即表prod_prop中的prop_id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  INDEX `prop_id`(`prop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for delivery
-- ----------------------------
DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery`  (
  `dvy_id` bigint UNSIGNED NOT NULL COMMENT 'ID',
  `dvy_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '物流公司名称',
  `company_home_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '公司主页',
  `rec_time` datetime NOT NULL COMMENT '建立时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `query_url` varchar(520) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '物流查询接口',
  PRIMARY KEY (`dvy_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '物流公司' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table`  (
  `xid` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '全局事务ID',
  `transaction_id` bigint NULL DEFAULT NULL COMMENT '事务ID',
  `status` tinyint NOT NULL COMMENT '状态',
  `application_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `transaction_service_group` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '事务分组名',
  `transaction_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '执行事务的方法',
  `timeout` int NULL DEFAULT NULL COMMENT '超时时间',
  `begin_time` bigint NULL DEFAULT NULL COMMENT '开始时间',
  `application_data` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '应用数据',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`xid`) USING BTREE,
  INDEX `idx_gmt_modified_status`(`gmt_modified` ASC, `status` ASC) USING BTREE,
  INDEX `idx_transaction_id`(`transaction_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for hot_search
-- ----------------------------
DROP TABLE IF EXISTS `hot_search`;
CREATE TABLE `hot_search`  (
  `hot_search_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺ID 0为全局热搜',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '录入时间',
  `seq` int NULL DEFAULT NULL COMMENT '顺序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0下线 1上线',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '热搜标题',
  PRIMARY KEY (`hot_search_id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '热搜' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for index_img
-- ----------------------------
DROP TABLE IF EXISTS `index_img`;
CREATE TABLE `index_img`  (
  `img_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺ID',
  `img_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片',
  `des` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '说明文字,描述',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `link` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '链接',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态',
  `seq` int NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `prod_id` bigint NULL DEFAULT NULL COMMENT '关联',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '关联商品类型，0已关联商品,-1未关联商品',
  PRIMARY KEY (`img_id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '主页轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table`  (
  `row_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '行键',
  `xid` varchar(96) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '全局事务ID',
  `transaction_id` bigint NULL DEFAULT NULL COMMENT '全局事务ID，不带TC 地址',
  `branch_id` bigint NOT NULL COMMENT '分支ID',
  `resource_id` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '资源ID',
  `table_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表名',
  `pk` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '主键对应的值',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`row_key`) USING BTREE,
  INDEX `idx_branch_id`(`branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '会员表的主键',
  `open_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT 'ID',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_mail` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `pay_password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '支付密码',
  `user_mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `user_regip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '注册IP',
  `user_lasttime` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `user_lastip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `sex` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'M' COMMENT 'M(男) or F(女)',
  `birth_date` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '例如：2009-11-27',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像图片路径',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 1 正常 0 无效',
  `score` int NULL DEFAULT NULL COMMENT '用户积分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openId_index`(`open_id` ASC) USING BTREE,
  UNIQUE INDEX `ud_user_mail`(`user_mail` ASC) USING BTREE,
  UNIQUE INDEX `ud_user_unique_mobile`(`user_mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for member_addr
-- ----------------------------
DROP TABLE IF EXISTS `member_addr`;
CREATE TABLE `member_addr`  (
  `addr_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `open_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户ID',
  `receiver` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `province_id` bigint NULL DEFAULT NULL COMMENT '省ID',
  `province` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '城市',
  `city_id` bigint NULL DEFAULT NULL COMMENT '城市ID',
  `area` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '区',
  `area_id` bigint NULL DEFAULT NULL COMMENT '区ID',
  `post_code` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `addr` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机',
  `status` int NOT NULL COMMENT '状态,1正常，0无效',
  `common_addr` int NOT NULL DEFAULT 0 COMMENT '是否默认地址 1是',
  `create_time` datetime NOT NULL COMMENT '建立时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`addr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户配送地址' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for member_collection
-- ----------------------------
DROP TABLE IF EXISTS `member_collection`;
CREATE TABLE `member_collection`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏表',
  `prod_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `open_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint UNSIGNED NOT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '留言创建时间',
  `user_name` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `email` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `contact` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '留言内容',
  `reply` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '留言回复',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态：0:未审核 1审核通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `title` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '公告内容',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1:公布 0:撤回)',
  `is_top` tinyint NULL DEFAULT NULL COMMENT '是否置顶',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '终端信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `open_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订购用户ID',
  `order_number` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订购流水号',
  `total_money` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '总值',
  `actual_total` decimal(15, 2) NULL DEFAULT NULL COMMENT '实际总值',
  `remarks` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `status` int NOT NULL DEFAULT 0 COMMENT '订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败',
  `dvy_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '配送类型',
  `dvy_id` bigint NULL DEFAULT NULL COMMENT '配送方式ID',
  `dvy_flow_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '物流单号',
  `freight_amount` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '订单运费',
  `addr_order_id` bigint NULL DEFAULT NULL COMMENT '用户订单地址Id',
  `product_nums` int NULL DEFAULT NULL COMMENT '订单商品总数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订购时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '订单更新时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  `dvy_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `finally_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `cancel_time` timestamp NULL DEFAULT NULL COMMENT '取消时间',
  `is_payed` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经支付，1：已经支付过，0：，没有支付过',
  `delete_status` int NULL DEFAULT 0 COMMENT '用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除',
  `refund_sts` int NULL DEFAULT 0 COMMENT '0:默认,1:在处理,2:处理完成',
  `reduce_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '优惠总额',
  `close_type` tinyint NULL DEFAULT NULL COMMENT '订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE INDEX `order_number_unique_ind`(`order_number` ASC) USING BTREE,
  UNIQUE INDEX `order_number_userid_unique_ind`(`open_id` ASC, `order_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `order_item_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `order_number` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单order_number',
  `prod_id` bigint UNSIGNED NOT NULL COMMENT '产品ID',
  `sku_id` bigint UNSIGNED NOT NULL COMMENT '产品SkuID',
  `prod_count` int NOT NULL DEFAULT 0 COMMENT '购物车产品个数',
  `prod_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `sku_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '产品主图片路径',
  `price` decimal(15, 2) NOT NULL COMMENT '产品价格',
  `product_total_amount` decimal(15, 2) NOT NULL COMMENT '商品总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '购物时间',
  `comm_sts` int NOT NULL DEFAULT 0 COMMENT '评论状态： 0 未评价  1 已评价',
  PRIMARY KEY (`order_item_id`) USING BTREE,
  INDEX `order_number`(`order_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order_refund
-- ----------------------------
DROP TABLE IF EXISTS `order_refund`;
CREATE TABLE `order_refund`  (
  `refund_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单流水号',
  `order_amount` double(12, 2) NOT NULL COMMENT '订单总金额',
  `order_item_id` bigint NOT NULL DEFAULT 0 COMMENT '订单项ID 全部退款是0',
  `refund_sn` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '退款编号',
  `flow_trade_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单支付流水号',
  `out_refund_no` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '第三方退款单号(微信退款单号)',
  `pay_type` int NULL DEFAULT NULL COMMENT '订单支付方式 1 微信支付 2 支付宝',
  `pay_type_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单支付名称',
  `user_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '买家ID',
  `goods_num` int NULL DEFAULT NULL COMMENT '退货数量',
  `refund_amount` decimal(10, 2) NOT NULL COMMENT '退款金额',
  `apply_type` int NOT NULL DEFAULT 0 COMMENT '申请类型:1,仅退款,2退款退货',
  `refund_sts` int NOT NULL DEFAULT 0 COMMENT '处理状态:1为待审核,2为同意,3为不同意',
  `return_money_sts` int NOT NULL DEFAULT 0 COMMENT '处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `handel_time` datetime NULL DEFAULT NULL COMMENT '卖家处理时间',
  `refund_time` datetime NULL DEFAULT NULL COMMENT '退款时间',
  `photo_files` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件凭证json',
  `buyer_msg` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '申请原因',
  `seller_msg` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '卖家备注',
  `express_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '物流公司名称',
  `express_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `receive_message` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货备注',
  PRIMARY KEY (`refund_id`) USING BTREE,
  UNIQUE INDEX `refund_sn_unique`(`refund_sn` ASC) USING BTREE,
  INDEX `order_number`(`order_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order_settlement
-- ----------------------------
DROP TABLE IF EXISTS `order_settlement`;
CREATE TABLE `order_settlement`  (
  `settlement_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '支付结算单据ID',
  `biz_pay_no` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '外部订单流水号',
  `order_number` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'order表中的订单号',
  `pay_type` int NULL DEFAULT NULL COMMENT '支付方式 1 支付宝 2 微信',
  `pay_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `is_clearing` int NULL DEFAULT NULL COMMENT '是否清算 0:否 1:是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `clearing_time` datetime NULL DEFAULT NULL COMMENT '清算时间',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `pay_status` int NULL DEFAULT NULL COMMENT '支付状态',
  PRIMARY KEY (`settlement_id`) USING BTREE,
  UNIQUE INDEX `primary_order_no`(`order_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pick_addr
-- ----------------------------
DROP TABLE IF EXISTS `pick_addr`;
CREATE TABLE `pick_addr`  (
  `addr_id` bigint UNSIGNED NOT NULL COMMENT 'ID',
  `addr_name` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '自提点名称',
  `addr` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机',
  `province_id` bigint NULL DEFAULT NULL COMMENT '省份ID',
  `province` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city_id` bigint NULL DEFAULT NULL COMMENT '城市ID',
  `city` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '城市',
  `area_id` bigint NULL DEFAULT NULL COMMENT '区/县ID',
  `area` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '区/县',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`addr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户配送地址' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for points_change
-- ----------------------------
DROP TABLE IF EXISTS `points_change`;
CREATE TABLE `points_change`  (
  `points_change_id` bigint NOT NULL AUTO_INCREMENT COMMENT '积分流动记录表',
  `points_wallet_id` bigint NULL DEFAULT NULL COMMENT '积分钱包id',
  `add_or_reduce` tinyint NULL DEFAULT NULL COMMENT '增加或减少(增加 0 减少 1)',
  `reason` tinyint NULL DEFAULT NULL COMMENT '原因(订单，邀请，签到，兑换)',
  `state` tinyint NULL DEFAULT NULL COMMENT '积分状态（0:用户未收货待结算，1:已结算 2:用户退货退单）',
  `points_number` double NULL DEFAULT NULL COMMENT '积分数额',
  `order_id` bigint NULL DEFAULT NULL COMMENT '关联订单id',
  `merchant_order_id` bigint NULL DEFAULT NULL COMMENT '商户订单id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`points_change_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for points_prod
-- ----------------------------
DROP TABLE IF EXISTS `points_prod`;
CREATE TABLE `points_prod`  (
  `points_prod_id` bigint NOT NULL AUTO_INCREMENT COMMENT '积分商品id',
  `points_id` bigint NULL DEFAULT NULL COMMENT '所需积分id',
  `points_number` double NULL DEFAULT NULL COMMENT '所需积分量',
  `amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '所需金额',
  `prod_id` bigint NULL DEFAULT NULL COMMENT '关联商品id',
  `stocks` int NULL DEFAULT NULL COMMENT '库存',
  `state` tinyint NULL DEFAULT NULL COMMENT '状态(0下架 1上架)',
  `upper_shelf_time` datetime NULL DEFAULT NULL COMMENT '上架时间',
  `lower_shelf` datetime NULL DEFAULT NULL COMMENT '下架时间',
  PRIMARY KEY (`points_prod_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for points_wallet
-- ----------------------------
DROP TABLE IF EXISTS `points_wallet`;
CREATE TABLE `points_wallet`  (
  `points_wallet_id` bigint NOT NULL AUTO_INCREMENT COMMENT '积分钱包id',
  `points_id` bigint NULL DEFAULT NULL COMMENT '积分Id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `unsettled` double NULL DEFAULT NULL COMMENT '待结算积分',
  `settled` double NULL DEFAULT NULL COMMENT '已结算积分',
  `addup` double NULL DEFAULT NULL COMMENT '积累收益积分',
  `version` int NULL DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`points_wallet_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod
-- ----------------------------
DROP TABLE IF EXISTS `prod`;
CREATE TABLE `prod`  (
  `prod_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `prod_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `ori_price` decimal(15, 2) NULL DEFAULT 0.00 COMMENT '原价',
  `price` decimal(15, 2) NULL DEFAULT NULL COMMENT '现价',
  `brief` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '简要描述,卖点等',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '详细描述',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品主图',
  `imgs` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片，以,分割',
  `status` int NULL DEFAULT 0 COMMENT '默认是1，表示正常状态, -1表示删除, 0下架',
  `category_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '商品分类',
  `sold_num` int NULL DEFAULT NULL COMMENT '销量',
  `total_stocks` int NULL DEFAULT 0 COMMENT '总库存',
  `delivery_mode` json NULL COMMENT '配送方式json见TransportModeVO',
  `delivery_template_id` bigint NULL DEFAULT NULL COMMENT '运费模板id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `putaway_time` timestamp NULL DEFAULT NULL COMMENT '上架时间',
  `version` int NULL DEFAULT NULL COMMENT '版本 乐观锁',
  PRIMARY KEY (`prod_id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品' ROW_FORMAT = DYNAMIC PARTITION BY HASH (`prod_id`)
PARTITIONS 3
(PARTITION `p0` ENGINE = InnoDB MAX_ROWS = 100000 MIN_ROWS = 0 ,
PARTITION `p1` ENGINE = InnoDB MAX_ROWS = 100000 MIN_ROWS = 0 ,
PARTITION `p2` ENGINE = InnoDB MAX_ROWS = 10000 MIN_ROWS = 0 )
;

-- ----------------------------
-- Table structure for prod_comm
-- ----------------------------
DROP TABLE IF EXISTS `prod_comm`;
CREATE TABLE `prod_comm`  (
  `prod_comm_id` bigint UNSIGNED NOT NULL COMMENT 'ID',
  `prod_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `prod_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品的名字',
  `order_item_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '订单项ID',
  `open_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '评论用户ID',
  `content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '评论内容',
  `reply_content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '掌柜回复',
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录时间',
  `reply_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  `reply_sts` int NULL DEFAULT 0 COMMENT '是否回复 0:未回复  1:已回复',
  `postip` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'IP来源',
  `score` tinyint NULL DEFAULT 0 COMMENT '得分，0-5分',
  `useful_counts` int NULL DEFAULT 0 COMMENT '有用的计数',
  `pics` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '晒图的json字符串',
  `is_anonymous` int NULL DEFAULT 0 COMMENT '是否匿名(1:是  0:否)',
  `status` int NULL DEFAULT NULL COMMENT '是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1',
  `evaluate` tinyint NULL DEFAULT NULL COMMENT '评价(0好评 1中评 2差评)',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺',
  PRIMARY KEY (`prod_comm_id`) USING BTREE,
  INDEX `prod_id`(`prod_id` ASC) USING BTREE,
  INDEX `grade_level_index`(`evaluate` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod_favorite
-- ----------------------------
DROP TABLE IF EXISTS `prod_favorite`;
CREATE TABLE `prod_favorite`  (
  `favorite_id` bigint UNSIGNED NOT NULL COMMENT '主键',
  `prod_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `rec_time` datetime NOT NULL COMMENT '收藏时间',
  `user_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  PRIMARY KEY (`favorite_id`) USING BTREE,
  INDEX `prod_id`(`prod_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod_prop
-- ----------------------------
DROP TABLE IF EXISTS `prod_prop`;
CREATE TABLE `prod_prop`  (
  `prop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `prop_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名称',
  `rule` tinyint NULL DEFAULT NULL COMMENT 'ProdPropRule 1:销售属性(规格); 2:参数属性;',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`prop_id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod_prop_value
-- ----------------------------
DROP TABLE IF EXISTS `prod_prop_value`;
CREATE TABLE `prod_prop_value`  (
  `value_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性值ID',
  `prop_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性值名称',
  `prop_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
  PRIMARY KEY (`value_id`) USING BTREE,
  INDEX `prop_id`(`prop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 416 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod_tag
-- ----------------------------
DROP TABLE IF EXISTS `prod_tag`;
CREATE TABLE `prod_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组标签id',
  `title` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分组标题',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1为正常,0为删除)',
  `style` int NULL DEFAULT NULL COMMENT '列表样式(0:一列一个,1:一列两个,2:一列三个)',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品分组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for prod_tag_reference
-- ----------------------------
DROP TABLE IF EXISTS `prod_tag_reference`;
CREATE TABLE `prod_tag_reference`  (
  `reference_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组引用id',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `tag_id` bigint NULL DEFAULT NULL COMMENT '标签id',
  `prod_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1:正常,0:删除)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`reference_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 386 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '定时任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '定时任务日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for score_log
-- ----------------------------
DROP TABLE IF EXISTS `score_log`;
CREATE TABLE `score_log`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '积分记录id',
  `user_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `type` tinyint NULL DEFAULT NULL COMMENT '0支出 1收入',
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录创建时间',
  `sn` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '流水号',
  `score_type` tinyint NULL DEFAULT NULL COMMENT '积分类型：1回收加积分 2购买减积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for shop_detail
-- ----------------------------
DROP TABLE IF EXISTS `shop_detail`;
CREATE TABLE `shop_detail`  (
  `shop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `shop_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一',
  `user_id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店长用户id',
  `shop_type` tinyint NULL DEFAULT NULL COMMENT '店铺类型',
  `intro` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺简介(可修改)',
  `shop_notice` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺公告(可修改)',
  `shop_industry` tinyint NULL DEFAULT NULL COMMENT '店铺行业(餐饮、生鲜果蔬、鲜花等)',
  `shop_owner` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店长',
  `mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺绑定的手机(登录账号：唯一)',
  `tel` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺联系电话',
  `shop_lat` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺所在纬度(可修改)',
  `shop_lng` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺所在经度(可修改)',
  `shop_address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺详细地址',
  `province` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺所在省份（描述）',
  `city` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺所在城市（描述）',
  `area` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺所在区域（描述）',
  `pca_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺省市区代码，用于回显',
  `shop_logo` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺logo(可修改)',
  `shop_photos` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '店铺相册',
  `open_time` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '每天营业时间段(可修改)',
  `shop_status` tinyint NULL DEFAULT NULL COMMENT '店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改',
  `transport_type` tinyint NULL DEFAULT NULL COMMENT '0:商家承担运费; 1:买家承担运费',
  `fixed_freight` decimal(15, 2) NULL DEFAULT NULL COMMENT '固定运费',
  `full_free_shipping` decimal(15, 2) NULL DEFAULT NULL COMMENT '满X包邮',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_distribution` tinyint NULL DEFAULT NULL COMMENT '分销开关(0:开启 1:关闭)',
  PRIMARY KEY (`shop_id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile` ASC) USING BTREE,
  UNIQUE INDEX `shop_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sku
-- ----------------------------
DROP TABLE IF EXISTS `sku`;
CREATE TABLE `sku`  (
  `sku_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '单品ID',
  `prod_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `properties` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '销售属性组合字符串 格式是p1:v1;p2:v2',
  `ori_price` decimal(15, 2) NULL DEFAULT NULL COMMENT '原价',
  `price` decimal(15, 2) NULL DEFAULT NULL COMMENT '价格',
  `stocks` int NOT NULL COMMENT '商品在付款减库存的状态下，该sku上未付款的订单数量',
  `actual_stocks` int NULL DEFAULT NULL COMMENT '实际库存',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL COMMENT '记录时间',
  `model_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品条形码',
  `pic` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'sku图片',
  `sku_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `prod_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  `weight` double NULL DEFAULT NULL COMMENT '商品重量',
  `volume` double NULL DEFAULT NULL COMMENT '商品体积',
  `status` tinyint NULL DEFAULT 1 COMMENT '0 禁用 1 启用',
  PRIMARY KEY (`sku_id`) USING BTREE,
  INDEX `prod_id`(`prod_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 440 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '单品SKU表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `open_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `template_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '短信模板',
  `mobile_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '手机验证码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `response_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发送短信返回码',
  `status` int NULL DEFAULT 0 COMMENT '状态  1:有效  0：失效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '短信记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'value',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`param_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 963 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 324 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '用户所在的商城Id',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_tx_exception
-- ----------------------------
DROP TABLE IF EXISTS `t_tx_exception`;
CREATE TABLE `t_tx_exception`  (
  `id` bigint NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `ex_state` smallint NOT NULL,
  `group_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mod_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `registrar` smallint NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `transaction_state` int NULL DEFAULT NULL,
  `unit_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transcity
-- ----------------------------
DROP TABLE IF EXISTS `transcity`;
CREATE TABLE `transcity`  (
  `transcity_id` bigint NOT NULL AUTO_INCREMENT,
  `transfee_id` bigint NULL DEFAULT NULL COMMENT '运费项id',
  `city_id` bigint NULL DEFAULT NULL COMMENT '城市id',
  PRIMARY KEY (`transcity_id`) USING BTREE,
  INDEX `transfee_id`(`transfee_id` ASC) USING BTREE,
  INDEX `city_id`(`city_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2078 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transcity_free
-- ----------------------------
DROP TABLE IF EXISTS `transcity_free`;
CREATE TABLE `transcity_free`  (
  `transcity_free_id` bigint NOT NULL AUTO_INCREMENT COMMENT '指定条件包邮城市项id',
  `transfee_free_id` bigint NULL DEFAULT NULL COMMENT '指定条件包邮项id',
  `free_city_id` bigint NULL DEFAULT NULL COMMENT '城市id',
  PRIMARY KEY (`transcity_free_id`) USING BTREE,
  INDEX `transfee_free_id`(`transfee_free_id` ASC) USING BTREE,
  INDEX `city_id`(`free_city_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2681 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transfee
-- ----------------------------
DROP TABLE IF EXISTS `transfee`;
CREATE TABLE `transfee`  (
  `transfee_id` bigint NOT NULL AUTO_INCREMENT COMMENT '运费项id',
  `transport_id` bigint NULL DEFAULT NULL COMMENT '运费模板id',
  `continuous_piece` decimal(15, 2) NULL DEFAULT NULL COMMENT '续件数量',
  `first_piece` decimal(15, 2) NULL DEFAULT NULL COMMENT '首件数量',
  `continuous_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '续件费用',
  `first_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '首件费用',
  PRIMARY KEY (`transfee_id`) USING BTREE,
  INDEX `transport_id`(`transport_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transfee_free
-- ----------------------------
DROP TABLE IF EXISTS `transfee_free`;
CREATE TABLE `transfee_free`  (
  `transfee_free_id` bigint NOT NULL AUTO_INCREMENT COMMENT '指定条件包邮项id',
  `transport_id` bigint NULL DEFAULT NULL COMMENT '运费模板id',
  `free_type` tinyint NULL DEFAULT NULL COMMENT '包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）',
  `amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '需满金额',
  `piece` decimal(15, 2) NULL DEFAULT NULL COMMENT '包邮x件/重量/体积',
  PRIMARY KEY (`transfee_free_id`) USING BTREE,
  INDEX `transport_id`(`transport_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transport
-- ----------------------------
DROP TABLE IF EXISTS `transport`;
CREATE TABLE `transport`  (
  `transport_id` bigint NOT NULL AUTO_INCREMENT COMMENT '运费模板id',
  `trans_name` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '运费模板名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `charge_type` tinyint NULL DEFAULT NULL COMMENT '收费方式（0 按件数,1 按重量 2 按体积）',
  `is_free_fee` tinyint NULL DEFAULT NULL COMMENT '是否包邮 0:不包邮 1:包邮',
  `has_free_condition` tinyint NULL DEFAULT NULL COMMENT '是否含有包邮条件 0 否 1是',
  PRIMARY KEY (`transport_id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
