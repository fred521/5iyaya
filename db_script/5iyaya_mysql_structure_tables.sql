/*
SQLyog - Free MySQL GUI v5.18
Host - 5.0.18-nt : Database - shang
*********************************************************************
Server version : 5.0.18-nt
*/



SET NAMES GB2312;


SET SQL_MODE='';

create database if not exists `shang`;

USE `shang`;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

/*Table structure for table `admin_log` */

DROP TABLE IF EXISTS `admin_log`;

CREATE TABLE `admin_log` (
  `ID` int(10) unsigned NOT NULL auto_increment,
  `ADMIN_USER_ID` int(10) default NULL,
  `ACTION` varchar(255) default NULL,
  `GMT_CREATE` datetime default NULL,
  `ACTION_IP` varchar(128) default NULL,
  `PARAMETER0` varchar(4000) default NULL,
  `PARAMETER1` varchar(4000) default NULL,
  `PARAMETER2` varchar(4000) default NULL,
  `PARAMETER3` varchar(4000) default NULL,
  `PARAMETER4` varchar(4000) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `admin_user` */

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `user_id` int(10) NOT NULL auto_increment COMMENT '管理员编号',
  `login_name` varchar(20) default NULL COMMENT '管理员登陆名称',
  `login_password` varchar(32) default NULL COMMENT '管理员登陆密码',
  `user_status` char(1) default NULL COMMENT '管理员状态:N正常、P禁用',
  `phone` varchar(32) default NULL COMMENT '联系电话',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  `creator` varchar(32) default NULL COMMENT '创建人',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `modifier` varchar(32) default NULL COMMENT '最后修改人',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='管理员信息表';

/*Table structure for table `commend_content` */

DROP TABLE IF EXISTS `commend_content`;

CREATE TABLE `commend_content` (
  `content_id` int(10) NOT NULL auto_increment COMMENT '推荐内容编号',
  `commend_position_id` int(10) NOT NULL COMMENT '所属推荐位置编号',
  `commend_type` int(1) NOT NULL COMMENT '推荐类型：1、商品、2、店铺、3、咨询信息',
  `commend_text` varchar(128) NOT NULL COMMENT '推荐文字',
  `pic_path` varchar(128) default NULL COMMENT '推荐图片路径、如果图片url为空，则需要用这个地址替换图片地址',
  `pic_url` varchar(128) default NULL COMMENT '推荐图片url',
  `batch_num` int(10) default NULL COMMENT '起批数量',
  `batch_price` int(11) default NULL COMMENT '起批价格，控制到分',
  `commend_status` char(1) default NULL COMMENT '推荐内容状态:N正常、D删除、取消C',
  `commend_url` varchar(128) default NULL COMMENT '推荐信息url',
  `commend_desc` varchar(128) default NULL COMMENT '推荐说明',
  `gmt_start` datetime NOT NULL COMMENT '推荐开始日期',
  `gmt_end` datetime NOT NULL COMMENT '推荐截至日期',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  `creator` varchar(32) default NULL COMMENT '创建人',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `modifier` varchar(32) default NULL COMMENT '最后修改人',
  PRIMARY KEY  (`content_id`),
  KEY `IDX_COMMEND_START_ENDDATE` (`gmt_end`,`gmt_start`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='推荐信息表';

/*Table structure for table `commend_position` */

DROP TABLE IF EXISTS `commend_position`;

CREATE TABLE `commend_position` (
  `commend_id` int(10) NOT NULL auto_increment COMMENT '推荐位置编号',
  `commend_code` varchar(32) NOT NULL COMMENT '推荐位置识别码：只能用数字或名称，一旦建立，则不能修改',
  `position_order` int(10) NOT NULL COMMENT '位置顺序',
  `commend_type` char(1) NOT NULL COMMENT '1:商品推荐,2:店铺推荐,3:分类信息推荐',
  `commend_page` int(2) NOT NULL COMMENT '所属推荐页面：0：首页1：咨询页面、2：商品页面、3：三维首页',
  `commend_name` varchar(128) NOT NULL COMMENT '推荐位置名称',
  `commend_content_type` char(1) NOT NULL COMMENT '推荐内容类型：1、图片与文字、2、光图片、3、光文字',
  `pic_width` int(10) default NULL COMMENT '图片宽度',
  `pic_height` int(10) default NULL COMMENT '图片高度',
  `text_length` int(10) default NULL COMMENT '文字长度',
  `pic_path` varchar(128) default NULL COMMENT '替换图片路径',
  `replace_text` varchar(128) default NULL COMMENT '替换文字',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  `creator` varchar(32) default NULL COMMENT '创建人',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `modifier` varchar(32) default NULL COMMENT '最后修改人',
  PRIMARY KEY  (`commend_id`),
  UNIQUE KEY `AK_COMMEND_POSITION` (`commend_code`,`position_order`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='推荐位置表';

/*Table structure for table `goods_base_info` */

DROP TABLE IF EXISTS `goods_base_info`;

CREATE TABLE `goods_base_info` (
  `goods_id` varchar(32) NOT NULL COMMENT '商品编号',
  `shop_id` varchar(32) NOT NULL COMMENT '店铺编号',
  `member_id` varchar(32) default NULL COMMENT '会员编号',
  `goods_name` varchar(128) default NULL COMMENT '商品名称',
  `goods_cat` varchar(3) default NULL COMMENT '商品目前为2级分类，本字段存储到二级分类：如一级分类为001，则二级分类为001001 or 001002 ，当需要根据一级分类查询时，需要获取所有的二级分类进行查询',
  `market_price` int(11) default NULL COMMENT '商品市场价格,....',
  `batch_price` int(11) default NULL COMMENT '商品批发价格，价格到分',
  `batch_num` int(10) default NULL COMMENT '商品起批数量',
  `price_des` varchar(128) default NULL COMMENT '价格描述',
  `goods_num` int(10) default NULL COMMENT '商品库存量',
  `goods_code` varchar(32) default NULL COMMENT '商品code',
  `seller_goods_code` varchar(32) default NULL COMMENT '商家真实商品code',
  `goods_pic` varchar(128) default NULL COMMENT '商品图片路径',
  `abandon_days` int(10) default NULL COMMENT '商品过期天数',
  `gmt_abandon` datetime default NULL COMMENT '商品过期月数：根据商品过期月数和商品创建时间计算得到；商品手动下架则这个时间为下架时间',
  `goods_type` char(1) default NULL COMMENT 'N 正常商品, T 团购商品',
  `goods_status` char(1) default NULL COMMENT 'N正常，D删除，E：过期；商品一旦创建则为正常；商品上架则为正常，需要重新计算过期时间',
  `view_count` int(10) default NULL COMMENT '商品浏览次数：每次浏览加一',
  `creator` varchar(32) default NULL COMMENT '创建人',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `modifier` varchar(32) default NULL COMMENT '最后修改人',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  `cat_path` varchar(32) default NULL COMMENT '分类路径',
  PRIMARY KEY  (`goods_id`),
  KEY `IDX_GOODS_MEMBER_ID` (`member_id`),
  KEY `IDX_GOODS_SHOP_ID` (`shop_id`),
  KEY `IDX_GOODS_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商品基础表';

/*Table structure for table `goods_cat` */

DROP TABLE IF EXISTS `goods_cat`;

CREATE TABLE `goods_cat` (
  `type_id` varchar(3) default NULL COMMENT '分类编号',
  `type_name` varchar(128) default NULL COMMENT '分类名称',
  `levels` int(1) default NULL COMMENT '如果为一级分类，则为1，二级分类为2',
  `parent_id` varchar(3) default NULL COMMENT '父级类目编号',
  `show_order` int(10) default NULL COMMENT '显示顺序'
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商品分类';

/*Table structure for table `goods_content` */

DROP TABLE IF EXISTS `goods_content`;

CREATE TABLE `goods_content` (
  `goods_id` varchar(32) NOT NULL COMMENT '商品编号',
  `content` text COMMENT '商品描述内容',
  PRIMARY KEY  (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商品信息描述';

/*Table structure for table `market_type` */

DROP TABLE IF EXISTS `market_type`;

CREATE TABLE `market_type` (
  `market_type` varchar(2) NOT NULL COMMENT '市场分类  01、02',
  `market_name` varchar(128) default NULL COMMENT '市场名称',
  `market_gis` varchar(128) default NULL COMMENT '市场三维地址',
  `market_address` varchar(128) default NULL COMMENT '市场地址',
  `show_order` int(10) default NULL COMMENT '显示顺序',
  PRIMARY KEY  (`market_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='市场分类信息';

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `member_id` varchar(32) NOT NULL COMMENT '会员编号',
  `nick` varchar(20) default NULL COMMENT '昵称：显示用',
  `login_password` varchar(32) NOT NULL COMMENT '密码需要md5加密',
  `mobile` varchar(32) NOT NULL COMMENT '手机号码不能为空，需要通过手机号码验证',
  `email` varchar(32) default NULL COMMENT '电子邮件地址',
  `name` varchar(20) NOT NULL COMMENT '真实姓名',
  `member_type` char(1) NOT NULL COMMENT '会员类型：买家B，卖家S；买卖家有不同的会员权限',
  `sex` varchar(2) default NULL COMMENT '性别：M 男、F女',
  `area_code` varchar(6) default NULL COMMENT '所在地区：根据省、地区、市三级组合成一个地区编码',
  `phone` varchar(32) default NULL COMMENT '固定电话',
  `post_code` varchar(6) NOT NULL COMMENT '邮政编码',
  `address` varchar(128) NOT NULL COMMENT '联系地址',
  `status` char(1) NOT NULL COMMENT '会员状态：禁用P，正常N,D删除；如果会员被禁用怎不能登陆',
  `phone_validate` char(1) NOT NULL COMMENT '手机校验：通过Y，否N',
  `gmt_register` datetime default NULL COMMENT '注册时间',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_last_login` datetime default NULL COMMENT '最后登陆时间',
  `login_count` int(10) default NULL COMMENT '登陆次数',
  `login_id` varchar(20) NOT NULL COMMENT '会员登陆id、不能有中文、唯一不可修改',
  `register_ip` varchar(15) default NULL COMMENT '注册时的ip',
  `last_login_ip` varchar(15) default NULL COMMENT '最后登陆的ip',
  PRIMARY KEY  (`member_id`),
  KEY `IDX_MEMBER_LOGIN_ID` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='会员信息表';

/*Table structure for table `mobile_validate` */

DROP TABLE IF EXISTS `mobile_validate`;

CREATE TABLE `mobile_validate` (
  `member_id` varchar(32) NOT NULL COMMENT '会员编号',
  `phone` varchar(15) default NULL COMMENT '校验的手机号码',
  `validate_code` varchar(6) default NULL COMMENT '本次手机校验码:6位手机校验码',
  PRIMARY KEY  (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='   ';

/*Table structure for table `news_base_info` */

DROP TABLE IF EXISTS `news_base_info`;

CREATE TABLE `news_base_info` (
  `news_id` varchar(32) NOT NULL COMMENT '商业资讯编号',
  `news_title` varchar(128) NOT NULL COMMENT '商业资讯标题',
  `news_type` varchar(2) NOT NULL COMMENT '商业资讯所属分类',
  `phone` varchar(32) default NULL COMMENT '联系电话',
  `member_id` varchar(32) NOT NULL COMMENT '信息所属会员',
  `nick` varchar(20) NOT NULL COMMENT '昵称：登陆使用，全局唯一',
  `news_status` char(1) NOT NULL COMMENT '商业资讯状态:N 正常、P过期',
  `view_count` int(10) default NULL COMMENT '浏览次数',
  `abandon_days` int(10) default NULL COMMENT '信息过期月数',
  `gmt_abandon` datetime default NULL COMMENT '根据商品过期月数和商品创建时间计算得到',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  PRIMARY KEY  (`news_id`),
  KEY `IDX_NEWS_MEMBER_ID` (`member_id`),
  KEY `IDX_NEWS_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商业资讯表';

/*Table structure for table `news_content` */

DROP TABLE IF EXISTS `news_content`;

CREATE TABLE `news_content` (
  `news_id` varchar(32) NOT NULL COMMENT '商业资讯编号',
  `content` text COMMENT '商业咨询内容',
  PRIMARY KEY  (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商业资讯内容';

/*Table structure for table `news_type` */

DROP TABLE IF EXISTS `news_type`;

CREATE TABLE `news_type` (
  `news_type` char(2) NOT NULL COMMENT '信息编号：01、02',
  `type_name` varchar(128) default NULL COMMENT '信息名称',
  `show_order` int(10) default NULL COMMENT '显示排序',
  PRIMARY KEY  (`news_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商业资讯分类表';

/*Table structure for table `quartz_log` */

DROP TABLE IF EXISTS `quartz_log`;

CREATE TABLE `quartz_log` (
  `quartz_type` varchar(20) NOT NULL COMMENT '  ',
  `gmt_execute` datetime default NULL COMMENT '最后执行定时时间',
  `quartz_memo` varchar(255) default NULL COMMENT '定时说明',
  PRIMARY KEY  (`quartz_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT=' ';

/*Table structure for table `search_key_word` */

DROP TABLE IF EXISTS `search_key_word`;

CREATE TABLE `search_key_word` (
  `key_name` varchar(20) default NULL COMMENT '搜索关键字名称/页面搜索中需要控制文字数量',
  `key_type` varchar(16) default NULL COMMENT '搜索关键字类型:1、商品、2、店铺、3、店主、4、资讯信息',
  `search_count` int(10) default NULL COMMENT '该关键字搜索次数',
  `key_id` int(10) NOT NULL auto_increment COMMENT '关键字编号',
  PRIMARY KEY  (`key_id`),
  UNIQUE KEY `Idx_key_type` (`key_name`,`key_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='搜索关键字表';

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL default '1',
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `shop_id` varchar(32) NOT NULL COMMENT '店铺编号',
  `member_id` varchar(32) NOT NULL COMMENT '会员编号',
  `login_id` varchar(20) NOT NULL COMMENT '会员登陆id',
  `shop_name` varchar(128) NOT NULL COMMENT '店铺名称',
  `shop_owner` varchar(20) NOT NULL COMMENT '店主姓名',
  `commodity` varchar(128) NOT NULL COMMENT '主营商品',
  `belong_market_id` varchar(2) NOT NULL COMMENT '所在市场编号，所有的市场以两位编码',
  `address` varchar(128) NOT NULL COMMENT '店铺地址',
  `phone` varchar(30) default NULL COMMENT '店内固话',
  `bank` varchar(10) default NULL COMMENT '开户银行',
  `bank_account` varchar(20) default NULL COMMENT '银行帐号',
  `account_name` varchar(20) default NULL COMMENT '开户人姓名',
  `goods_count` int(10) default NULL,
  `camera` char(1) default 'N' COMMENT '是否有网络摄像头，Y有N无',
  `web_phone` char(1) default 'N' COMMENT '是否集成web800：Y是，N否',
  `logo` varchar(128) default NULL COMMENT 'logo图片路径',
  `shop_img` varchar(128) default NULL COMMENT '店铺照片',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gis_address` varchar(128) default NULL COMMENT '三维地址/新增时录入/会员自己不能修改三维地址',
  PRIMARY KEY  (`shop_id`),
  KEY `IDX_SHOP_MEMBER_ID` (`member_id`),
  KEY `IDX_SHOP_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='店铺基础表，一个卖家只能有一个店铺';

/*Table structure for table `shop_commend` */

DROP TABLE IF EXISTS `shop_commend`;

CREATE TABLE `shop_commend` (
  `shop_id` varchar(32) NOT NULL COMMENT '店铺编号',
  `commend` text COMMENT '店铺公告内容',
  PRIMARY KEY  (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='店铺公告表';

/*Table structure for table `shop_info` */

DROP TABLE IF EXISTS `shop_info`;

CREATE TABLE `shop_info` (
  `id` decimal(11,0) NOT NULL,
  `area` decimal(11,0) default NULL,
  `shop_type` varchar(200) default NULL,
  `note` varchar(200) default NULL,
  `product_type` varchar(200) default NULL,
  `market` decimal(10,0) default NULL,
  `district` decimal(10,0) default NULL,
  `building` varchar(45) default NULL,
  `door_number` varchar(45) default NULL,
  `created_by` varchar(45) NOT NULL,
  `name` varchar(200) default NULL,
  `product_type_chn` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `sign_shop` */

DROP TABLE IF EXISTS `sign_shop`;

CREATE TABLE `sign_shop` (
  `id` decimal(11,0) NOT NULL,
  `shop_id` varchar(32) default NULL,
  `member_id` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  KEY `IDX_SIGN_SHOP_SHOPMEMBER_ID` (`shop_id`,`member_id`),
  KEY `IDX_SIGN_SHOP_MEMBERID` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `sms` */

DROP TABLE IF EXISTS `sms`;

CREATE TABLE `sms` (
  `sms_id` int(10) NOT NULL auto_increment COMMENT '短信编号',
  `phone` varchar(11) default NULL COMMENT '本次发送手机号',
  `status` char(1) default NULL COMMENT '发送状态:W，等待发送；S，发送成功',
  `context` varchar(255) default NULL COMMENT '发送内容',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `SEND_COUNT` int(10) default NULL COMMENT '发送次数',
  PRIMARY KEY  (`sms_id`),
  KEY `IDX_SMS_MODIFYDATE` (`gmt_modify`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='短信发送表';

/*Table structure for table `trade_car` */

DROP TABLE IF EXISTS `trade_car`;

CREATE TABLE `trade_car` (
  `id` int(10) NOT NULL auto_increment COMMENT '会员编号',
  `goods_id` varchar(32) default NULL COMMENT '商品id',
  `shop_id` varchar(32) default NULL COMMENT '店铺id',
  `owner` varchar(32) default NULL COMMENT '创建人id',
  `gmt_create` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `IDX_TRADE_CAR_OWNER` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_car_shop` */

DROP TABLE IF EXISTS `trade_car_shop`;

CREATE TABLE `trade_car_shop` (
  `shop_id` varchar(32) default NULL COMMENT '店铺id',
  `owner` varchar(32) default NULL COMMENT '创建人id',
  KEY `IDX_TRADE_CAR_SHOP_OWNER` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order` */

DROP TABLE IF EXISTS `trade_order`;

CREATE TABLE `trade_order` (
  `order_no` varchar(32) NOT NULL COMMENT '订单号20070910(日期)+8为序列号',
  `shop_id` varchar(32) default NULL,
  `address` varchar(128) default NULL COMMENT '地址表',
  `shop_name` varchar(128) default NULL,
  `pay_fee` int(11) default NULL,
  `tools_type` int(2) default NULL COMMENT '支付工具类型',
  `status` varchar(32) default NULL COMMENT '订单状态：\n	订单初始状态   ORDER_INIT\n	等待卖家确认    WAIT_SELLER_CONFIRM\n	等待买家确认    WAIT_BUYER_CONFIRM\n	订单作废           ORDER_CLOSE\n	双方已确认       WAIT_PAY\n	订单已经支付   ORDER_SUCCESS',
  `order_date` varchar(8) default NULL,
  `buyer_id` varchar(32) default NULL,
  `seller_id` varchar(32) default NULL,
  `order_type` char(1) default NULL COMMENT '订单类型:F（快速订单）T（交易订单）快速订单无商品信息',
  `memo` varchar(256) default NULL,
  `buyer_login_id` varchar(32) default NULL,
  `seller_login_id` varchar(32) default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  `logistics_fee` int(10) default NULL COMMENT '运费,物流费用',
  PRIMARY KEY  (`order_no`),
  KEY `IDX_TRADE_ORDER_BUYER_STATUS` (`buyer_id`,`status`),
  KEY `IDX_TRADE_ORDER_SELLER_STATUS` (`seller_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order_item` */

DROP TABLE IF EXISTS `trade_order_item`;

CREATE TABLE `trade_order_item` (
  `id` int(10) NOT NULL auto_increment COMMENT '会员编号',
  `order_no` varchar(32) default NULL,
  `goods_id` varchar(32) default NULL,
  `shop_id` varchar(32) default NULL,
  `goods_name` varchar(128) default NULL,
  `batch_price` int(11) default NULL COMMENT '商品批量或单个单价，单位分',
  `total_num` int(10) default NULL,
  `total_free` int(11) default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_trade_order_item_orderno` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order_log` */

DROP TABLE IF EXISTS `trade_order_log`;

CREATE TABLE `trade_order_log` (
  `id` int(10) NOT NULL auto_increment,
  `order_no` varchar(32) NOT NULL,
  `status` varchar(32) default NULL,
  `member_type` char(1) default NULL,
  `pay_fee` int(11) default NULL,
  `memo` varchar(2000) default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `IDX_TRADE_ORDER_LOG_ORDERNO` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order_note` */

DROP TABLE IF EXISTS `trade_order_note`;

CREATE TABLE `trade_order_note` (
  `id` int(10) NOT NULL auto_increment,
  `order_no` varchar(32) NOT NULL,
  `member_type` char(1) default NULL,
  `memo` varchar(1024) default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `IDX_TRADE_ORDER_NOTE_ORDERNO` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_pay` */

DROP TABLE IF EXISTS `trade_pay`;

CREATE TABLE `trade_pay` (
  `id` int(10) NOT NULL auto_increment COMMENT '采购单编号',
  `order_no` varchar(32) NOT NULL,
  `buyer_name` varchar(32) default NULL,
  `seller_name` varchar(32) default NULL,
  `buyer_account` varchar(32) default NULL COMMENT '买家银行帐号，目前系统中是没有买家银行帐号的，所以这个字段可以为空',
  `buyer_bank` varchar(64) default NULL,
  `seller_account` varchar(32) default NULL COMMENT '卖家银行帐号：如果卖家没有银行帐号则不允许支付',
  `seller_bank` varchar(128) default NULL,
  `buyer_id` varchar(32) default NULL,
  `seller_id` varchar(32) default NULL,
  `pay_fee` int(11) default NULL,
  `pay_status` char(1) default NULL COMMENT '支付状态，支付不成功对订单也是等待支付状态、支付成功订单成功S(成功)F( 失败)I(初始状态)',
  `trans_status` char(1) default NULL COMMENT '转帐状态：S(成功)I( 未转帐)',
  `pay_date` datetime default NULL COMMENT '去银行支付的时间',
  `bank_no` varchar(64) default NULL,
  `trans_date` datetime default NULL,
  `pay_success_date` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  `pay_bank` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `IDX_TRADE_PAY_TRADEORDERNO` (`order_no`),
  KEY `IDX_TRADE_PAY_BUYERID` (`buyer_id`),
  KEY `IDX_TRADE_PAY_SELLERID` (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

DROP TABLE IF EXISTS `picture_info`;

CREATE TABLE `picture_info` (
  `id` varchar(32) NOT NULL COMMENT '图片编号',
  `goods_id` varchar(32) NOT NULL COMMENT '商品编号',
  `path` varchar(128) default NULL COMMENT '商品图片路径',
  `status` char(1) default NULL COMMENT 'N正常，D删除',
  `gmt_modify` datetime default NULL COMMENT '最后修改时间',
  `gmt_create` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='商品图片表';

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `address_id` varchar(32) NOT NULL,
  `member_id` varchar(32) NOT NULL,
  `consignee` varchar(32) default NULL,
  `street_address` varchar(32) default NULL,
  `area_code` varchar(6) default NULL,
  `phone` varchar(32) default NULL,
  `mobile` varchar(32) default NULL,
  `status` char(1) NOT NULL COMMENT '''T'' 代表临时地址 ''N'' 代表正常的地址地址',
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `modifier` varchar(32) default NULL,
  `post_code` varchar(6) NOT NULL,
  PRIMARY KEY  (`address_id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;
select * from picture_info

/*Table structure for table `urls` */

DROP TABLE IF EXISTS `urls`;

CREATE TABLE `urls` (
  `ID` varchar(32) NOT NULL,
  `PATH` varchar(128) default NULL,
  `URL` varchar(128) default NOT NULL,
  `TITLE` varchar(64) default NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

CREATE TABLE `helper` (
  `helper_id` varchar(32) NOT NULL,
  `helper_title` varchar(128) NOT NULL,
  `helper_type` int(2) NOT NULL,
  `helper_status` char(1) NOT NULL,
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `content` text,
  PRIMARY KEY  (`helper_id`)
) ENGINE=MyISAM DEFAULT CHARSET=gb2312;

/*Table structure for table `helper_type` */

CREATE TABLE `helper_type` (
  `helper_type` int(2) NOT NULL auto_increment,
  `type_name` varchar(128) default NULL,
  `show_order` int(2) default NULL,
  PRIMARY KEY  (`helper_type`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=gb2312;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;





CREATE TABLE USER_CENTER ( 
	FK_MEMBER VARCHAR(32)  NOT NULL ,
	MEMBER_TYPE CHARACTER (1)  NOT NULL ,
	VERIFY_CODE VARCHAR (32)  NOT NULL , 
	REQUEST_IP VARCHAR (16)  NOT NULL , 
	GMT_CREATE TIMESTAMP  NOT NULL  , 
	CONSTRAINT USER_CENTER_PK PRIMARY KEY ( VERIFY_CODE)  )ENGINE=InnoDB DEFAULT CHARSET=gb2312 ;