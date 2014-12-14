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
  `user_id` int(10) NOT NULL auto_increment COMMENT '����Ա���',
  `login_name` varchar(20) default NULL COMMENT '����Ա��½����',
  `login_password` varchar(32) default NULL COMMENT '����Ա��½����',
  `user_status` char(1) default NULL COMMENT '����Ա״̬:N������P����',
  `phone` varchar(32) default NULL COMMENT '��ϵ�绰',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  `creator` varchar(32) default NULL COMMENT '������',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `modifier` varchar(32) default NULL COMMENT '����޸���',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='����Ա��Ϣ��';

/*Table structure for table `commend_content` */

DROP TABLE IF EXISTS `commend_content`;

CREATE TABLE `commend_content` (
  `content_id` int(10) NOT NULL auto_increment COMMENT '�Ƽ����ݱ��',
  `commend_position_id` int(10) NOT NULL COMMENT '�����Ƽ�λ�ñ��',
  `commend_type` int(1) NOT NULL COMMENT '�Ƽ����ͣ�1����Ʒ��2�����̡�3����ѯ��Ϣ',
  `commend_text` varchar(128) NOT NULL COMMENT '�Ƽ�����',
  `pic_path` varchar(128) default NULL COMMENT '�Ƽ�ͼƬ·�������ͼƬurlΪ�գ�����Ҫ�������ַ�滻ͼƬ��ַ',
  `pic_url` varchar(128) default NULL COMMENT '�Ƽ�ͼƬurl',
  `batch_num` int(10) default NULL COMMENT '��������',
  `batch_price` int(11) default NULL COMMENT '�����۸񣬿��Ƶ���',
  `commend_status` char(1) default NULL COMMENT '�Ƽ�����״̬:N������Dɾ����ȡ��C',
  `commend_url` varchar(128) default NULL COMMENT '�Ƽ���Ϣurl',
  `commend_desc` varchar(128) default NULL COMMENT '�Ƽ�˵��',
  `gmt_start` datetime NOT NULL COMMENT '�Ƽ���ʼ����',
  `gmt_end` datetime NOT NULL COMMENT '�Ƽ���������',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  `creator` varchar(32) default NULL COMMENT '������',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `modifier` varchar(32) default NULL COMMENT '����޸���',
  PRIMARY KEY  (`content_id`),
  KEY `IDX_COMMEND_START_ENDDATE` (`gmt_end`,`gmt_start`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='�Ƽ���Ϣ��';

/*Table structure for table `commend_position` */

DROP TABLE IF EXISTS `commend_position`;

CREATE TABLE `commend_position` (
  `commend_id` int(10) NOT NULL auto_increment COMMENT '�Ƽ�λ�ñ��',
  `commend_code` varchar(32) NOT NULL COMMENT '�Ƽ�λ��ʶ���룺ֻ�������ֻ����ƣ�һ�������������޸�',
  `position_order` int(10) NOT NULL COMMENT 'λ��˳��',
  `commend_type` char(1) NOT NULL COMMENT '1:��Ʒ�Ƽ�,2:�����Ƽ�,3:������Ϣ�Ƽ�',
  `commend_page` int(2) NOT NULL COMMENT '�����Ƽ�ҳ�棺0����ҳ1����ѯҳ�桢2����Ʒҳ�桢3����ά��ҳ',
  `commend_name` varchar(128) NOT NULL COMMENT '�Ƽ�λ������',
  `commend_content_type` char(1) NOT NULL COMMENT '�Ƽ��������ͣ�1��ͼƬ�����֡�2����ͼƬ��3��������',
  `pic_width` int(10) default NULL COMMENT 'ͼƬ���',
  `pic_height` int(10) default NULL COMMENT 'ͼƬ�߶�',
  `text_length` int(10) default NULL COMMENT '���ֳ���',
  `pic_path` varchar(128) default NULL COMMENT '�滻ͼƬ·��',
  `replace_text` varchar(128) default NULL COMMENT '�滻����',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  `creator` varchar(32) default NULL COMMENT '������',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `modifier` varchar(32) default NULL COMMENT '����޸���',
  PRIMARY KEY  (`commend_id`),
  UNIQUE KEY `AK_COMMEND_POSITION` (`commend_code`,`position_order`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='�Ƽ�λ�ñ�';

/*Table structure for table `goods_base_info` */

DROP TABLE IF EXISTS `goods_base_info`;

CREATE TABLE `goods_base_info` (
  `goods_id` varchar(32) NOT NULL COMMENT '��Ʒ���',
  `shop_id` varchar(32) NOT NULL COMMENT '���̱��',
  `member_id` varchar(32) default NULL COMMENT '��Ա���',
  `goods_name` varchar(128) default NULL COMMENT '��Ʒ����',
  `goods_cat` varchar(3) default NULL COMMENT '��ƷĿǰΪ2�����࣬���ֶδ洢���������ࣺ��һ������Ϊ001�����������Ϊ001001 or 001002 ������Ҫ����һ�������ѯʱ����Ҫ��ȡ���еĶ���������в�ѯ',
  `market_price` int(11) default NULL COMMENT '��Ʒ�г��۸�,....',
  `batch_price` int(11) default NULL COMMENT '��Ʒ�����۸񣬼۸񵽷�',
  `batch_num` int(10) default NULL COMMENT '��Ʒ��������',
  `price_des` varchar(128) default NULL COMMENT '�۸�����',
  `goods_num` int(10) default NULL COMMENT '��Ʒ�����',
  `goods_code` varchar(32) default NULL COMMENT '��Ʒcode',
  `seller_goods_code` varchar(32) default NULL COMMENT '�̼���ʵ��Ʒcode',
  `goods_pic` varchar(128) default NULL COMMENT '��ƷͼƬ·��',
  `abandon_days` int(10) default NULL COMMENT '��Ʒ��������',
  `gmt_abandon` datetime default NULL COMMENT '��Ʒ����������������Ʒ������������Ʒ����ʱ�����õ�����Ʒ�ֶ��¼������ʱ��Ϊ�¼�ʱ��',
  `goods_type` char(1) default NULL COMMENT 'N ������Ʒ, T �Ź���Ʒ',
  `goods_status` char(1) default NULL COMMENT 'N������Dɾ����E�����ڣ���Ʒһ��������Ϊ��������Ʒ�ϼ���Ϊ��������Ҫ���¼������ʱ��',
  `view_count` int(10) default NULL COMMENT '��Ʒ���������ÿ�������һ',
  `creator` varchar(32) default NULL COMMENT '������',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `modifier` varchar(32) default NULL COMMENT '����޸���',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  `cat_path` varchar(32) default NULL COMMENT '����·��',
  PRIMARY KEY  (`goods_id`),
  KEY `IDX_GOODS_MEMBER_ID` (`member_id`),
  KEY `IDX_GOODS_SHOP_ID` (`shop_id`),
  KEY `IDX_GOODS_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��Ʒ������';

/*Table structure for table `goods_cat` */

DROP TABLE IF EXISTS `goods_cat`;

CREATE TABLE `goods_cat` (
  `type_id` varchar(3) default NULL COMMENT '������',
  `type_name` varchar(128) default NULL COMMENT '��������',
  `levels` int(1) default NULL COMMENT '���Ϊһ�����࣬��Ϊ1����������Ϊ2',
  `parent_id` varchar(3) default NULL COMMENT '������Ŀ���',
  `show_order` int(10) default NULL COMMENT '��ʾ˳��'
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��Ʒ����';

/*Table structure for table `goods_content` */

DROP TABLE IF EXISTS `goods_content`;

CREATE TABLE `goods_content` (
  `goods_id` varchar(32) NOT NULL COMMENT '��Ʒ���',
  `content` text COMMENT '��Ʒ��������',
  PRIMARY KEY  (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��Ʒ��Ϣ����';

/*Table structure for table `market_type` */

DROP TABLE IF EXISTS `market_type`;

CREATE TABLE `market_type` (
  `market_type` varchar(2) NOT NULL COMMENT '�г�����  01��02',
  `market_name` varchar(128) default NULL COMMENT '�г�����',
  `market_gis` varchar(128) default NULL COMMENT '�г���ά��ַ',
  `market_address` varchar(128) default NULL COMMENT '�г���ַ',
  `show_order` int(10) default NULL COMMENT '��ʾ˳��',
  PRIMARY KEY  (`market_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='�г�������Ϣ';

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `member_id` varchar(32) NOT NULL COMMENT '��Ա���',
  `nick` varchar(20) default NULL COMMENT '�ǳƣ���ʾ��',
  `login_password` varchar(32) NOT NULL COMMENT '������Ҫmd5����',
  `mobile` varchar(32) NOT NULL COMMENT '�ֻ����벻��Ϊ�գ���Ҫͨ���ֻ�������֤',
  `email` varchar(32) default NULL COMMENT '�����ʼ���ַ',
  `name` varchar(20) NOT NULL COMMENT '��ʵ����',
  `member_type` char(1) NOT NULL COMMENT '��Ա���ͣ����B������S���������в�ͬ�Ļ�ԱȨ��',
  `sex` varchar(2) default NULL COMMENT '�Ա�M �С�FŮ',
  `area_code` varchar(6) default NULL COMMENT '���ڵ���������ʡ����������������ϳ�һ����������',
  `phone` varchar(32) default NULL COMMENT '�̶��绰',
  `post_code` varchar(6) NOT NULL COMMENT '��������',
  `address` varchar(128) NOT NULL COMMENT '��ϵ��ַ',
  `status` char(1) NOT NULL COMMENT '��Ա״̬������P������N,Dɾ���������Ա�����������ܵ�½',
  `phone_validate` char(1) NOT NULL COMMENT '�ֻ�У�飺ͨ��Y����N',
  `gmt_register` datetime default NULL COMMENT 'ע��ʱ��',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_last_login` datetime default NULL COMMENT '����½ʱ��',
  `login_count` int(10) default NULL COMMENT '��½����',
  `login_id` varchar(20) NOT NULL COMMENT '��Ա��½id�����������ġ�Ψһ�����޸�',
  `register_ip` varchar(15) default NULL COMMENT 'ע��ʱ��ip',
  `last_login_ip` varchar(15) default NULL COMMENT '����½��ip',
  PRIMARY KEY  (`member_id`),
  KEY `IDX_MEMBER_LOGIN_ID` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��Ա��Ϣ��';

/*Table structure for table `mobile_validate` */

DROP TABLE IF EXISTS `mobile_validate`;

CREATE TABLE `mobile_validate` (
  `member_id` varchar(32) NOT NULL COMMENT '��Ա���',
  `phone` varchar(15) default NULL COMMENT 'У����ֻ�����',
  `validate_code` varchar(6) default NULL COMMENT '�����ֻ�У����:6λ�ֻ�У����',
  PRIMARY KEY  (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='   ';

/*Table structure for table `news_base_info` */

DROP TABLE IF EXISTS `news_base_info`;

CREATE TABLE `news_base_info` (
  `news_id` varchar(32) NOT NULL COMMENT '��ҵ��Ѷ���',
  `news_title` varchar(128) NOT NULL COMMENT '��ҵ��Ѷ����',
  `news_type` varchar(2) NOT NULL COMMENT '��ҵ��Ѷ��������',
  `phone` varchar(32) default NULL COMMENT '��ϵ�绰',
  `member_id` varchar(32) NOT NULL COMMENT '��Ϣ������Ա',
  `nick` varchar(20) NOT NULL COMMENT '�ǳƣ���½ʹ�ã�ȫ��Ψһ',
  `news_status` char(1) NOT NULL COMMENT '��ҵ��Ѷ״̬:N ������P����',
  `view_count` int(10) default NULL COMMENT '�������',
  `abandon_days` int(10) default NULL COMMENT '��Ϣ��������',
  `gmt_abandon` datetime default NULL COMMENT '������Ʒ������������Ʒ����ʱ�����õ�',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  PRIMARY KEY  (`news_id`),
  KEY `IDX_NEWS_MEMBER_ID` (`member_id`),
  KEY `IDX_NEWS_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��ҵ��Ѷ��';

/*Table structure for table `news_content` */

DROP TABLE IF EXISTS `news_content`;

CREATE TABLE `news_content` (
  `news_id` varchar(32) NOT NULL COMMENT '��ҵ��Ѷ���',
  `content` text COMMENT '��ҵ��ѯ����',
  PRIMARY KEY  (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��ҵ��Ѷ����';

/*Table structure for table `news_type` */

DROP TABLE IF EXISTS `news_type`;

CREATE TABLE `news_type` (
  `news_type` char(2) NOT NULL COMMENT '��Ϣ��ţ�01��02',
  `type_name` varchar(128) default NULL COMMENT '��Ϣ����',
  `show_order` int(10) default NULL COMMENT '��ʾ����',
  PRIMARY KEY  (`news_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��ҵ��Ѷ�����';

/*Table structure for table `quartz_log` */

DROP TABLE IF EXISTS `quartz_log`;

CREATE TABLE `quartz_log` (
  `quartz_type` varchar(20) NOT NULL COMMENT '  ',
  `gmt_execute` datetime default NULL COMMENT '���ִ�ж�ʱʱ��',
  `quartz_memo` varchar(255) default NULL COMMENT '��ʱ˵��',
  PRIMARY KEY  (`quartz_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT=' ';

/*Table structure for table `search_key_word` */

DROP TABLE IF EXISTS `search_key_word`;

CREATE TABLE `search_key_word` (
  `key_name` varchar(20) default NULL COMMENT '�����ؼ�������/ҳ����������Ҫ������������',
  `key_type` varchar(16) default NULL COMMENT '�����ؼ�������:1����Ʒ��2�����̡�3��������4����Ѷ��Ϣ',
  `search_count` int(10) default NULL COMMENT '�ùؼ�����������',
  `key_id` int(10) NOT NULL auto_increment COMMENT '�ؼ��ֱ��',
  PRIMARY KEY  (`key_id`),
  UNIQUE KEY `Idx_key_type` (`key_name`,`key_type`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='�����ؼ��ֱ�';

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
  `shop_id` varchar(32) NOT NULL COMMENT '���̱��',
  `member_id` varchar(32) NOT NULL COMMENT '��Ա���',
  `login_id` varchar(20) NOT NULL COMMENT '��Ա��½id',
  `shop_name` varchar(128) NOT NULL COMMENT '��������',
  `shop_owner` varchar(20) NOT NULL COMMENT '��������',
  `commodity` varchar(128) NOT NULL COMMENT '��Ӫ��Ʒ',
  `belong_market_id` varchar(2) NOT NULL COMMENT '�����г���ţ����е��г�����λ����',
  `address` varchar(128) NOT NULL COMMENT '���̵�ַ',
  `phone` varchar(30) default NULL COMMENT '���ڹ̻�',
  `bank` varchar(10) default NULL COMMENT '��������',
  `bank_account` varchar(20) default NULL COMMENT '�����ʺ�',
  `account_name` varchar(20) default NULL COMMENT '����������',
  `goods_count` int(10) default NULL,
  `camera` char(1) default 'N' COMMENT '�Ƿ�����������ͷ��Y��N��',
  `web_phone` char(1) default 'N' COMMENT '�Ƿ񼯳�web800��Y�ǣ�N��',
  `logo` varchar(128) default NULL COMMENT 'logoͼƬ·��',
  `shop_img` varchar(128) default NULL COMMENT '������Ƭ',
  `gmt_create` datetime default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gis_address` varchar(128) default NULL COMMENT '��ά��ַ/����ʱ¼��/��Ա�Լ������޸���ά��ַ',
  PRIMARY KEY  (`shop_id`),
  KEY `IDX_SHOP_MEMBER_ID` (`member_id`),
  KEY `IDX_SHOP_MODIFYDATE` (`gmt_modify`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='���̻�����һ������ֻ����һ������';

/*Table structure for table `shop_commend` */

DROP TABLE IF EXISTS `shop_commend`;

CREATE TABLE `shop_commend` (
  `shop_id` varchar(32) NOT NULL COMMENT '���̱��',
  `commend` text COMMENT '���̹�������',
  PRIMARY KEY  (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='���̹����';

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
  `sms_id` int(10) NOT NULL auto_increment COMMENT '���ű��',
  `phone` varchar(11) default NULL COMMENT '���η����ֻ���',
  `status` char(1) default NULL COMMENT '����״̬:W���ȴ����ͣ�S�����ͳɹ�',
  `context` varchar(255) default NULL COMMENT '��������',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `SEND_COUNT` int(10) default NULL COMMENT '���ʹ���',
  PRIMARY KEY  (`sms_id`),
  KEY `IDX_SMS_MODIFYDATE` (`gmt_modify`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='���ŷ��ͱ�';

/*Table structure for table `trade_car` */

DROP TABLE IF EXISTS `trade_car`;

CREATE TABLE `trade_car` (
  `id` int(10) NOT NULL auto_increment COMMENT '��Ա���',
  `goods_id` varchar(32) default NULL COMMENT '��Ʒid',
  `shop_id` varchar(32) default NULL COMMENT '����id',
  `owner` varchar(32) default NULL COMMENT '������id',
  `gmt_create` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `IDX_TRADE_CAR_OWNER` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_car_shop` */

DROP TABLE IF EXISTS `trade_car_shop`;

CREATE TABLE `trade_car_shop` (
  `shop_id` varchar(32) default NULL COMMENT '����id',
  `owner` varchar(32) default NULL COMMENT '������id',
  KEY `IDX_TRADE_CAR_SHOP_OWNER` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order` */

DROP TABLE IF EXISTS `trade_order`;

CREATE TABLE `trade_order` (
  `order_no` varchar(32) NOT NULL COMMENT '������20070910(����)+8Ϊ���к�',
  `shop_id` varchar(32) default NULL,
  `address` varchar(128) default NULL COMMENT '��ַ��',
  `shop_name` varchar(128) default NULL,
  `pay_fee` int(11) default NULL,
  `tools_type` int(2) default NULL COMMENT '֧����������',
  `status` varchar(32) default NULL COMMENT '����״̬��\n	������ʼ״̬   ORDER_INIT\n	�ȴ�����ȷ��    WAIT_SELLER_CONFIRM\n	�ȴ����ȷ��    WAIT_BUYER_CONFIRM\n	��������           ORDER_CLOSE\n	˫����ȷ��       WAIT_PAY\n	�����Ѿ�֧��   ORDER_SUCCESS',
  `order_date` varchar(8) default NULL,
  `buyer_id` varchar(32) default NULL,
  `seller_id` varchar(32) default NULL,
  `order_type` char(1) default NULL COMMENT '��������:F�����ٶ�����T�����׶��������ٶ�������Ʒ��Ϣ',
  `memo` varchar(256) default NULL,
  `buyer_login_id` varchar(32) default NULL,
  `seller_login_id` varchar(32) default NULL,
  `creator` varchar(32) default NULL,
  `gmt_modify` datetime default NULL,
  `modifier` varchar(32) default NULL,
  `gmt_create` datetime default NULL,
  `logistics_fee` int(10) default NULL COMMENT '�˷�,��������',
  PRIMARY KEY  (`order_no`),
  KEY `IDX_TRADE_ORDER_BUYER_STATUS` (`buyer_id`,`status`),
  KEY `IDX_TRADE_ORDER_SELLER_STATUS` (`seller_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Table structure for table `trade_order_item` */

DROP TABLE IF EXISTS `trade_order_item`;

CREATE TABLE `trade_order_item` (
  `id` int(10) NOT NULL auto_increment COMMENT '��Ա���',
  `order_no` varchar(32) default NULL,
  `goods_id` varchar(32) default NULL,
  `shop_id` varchar(32) default NULL,
  `goods_name` varchar(128) default NULL,
  `batch_price` int(11) default NULL COMMENT '��Ʒ�����򵥸����ۣ���λ��',
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
  `id` int(10) NOT NULL auto_increment COMMENT '�ɹ������',
  `order_no` varchar(32) NOT NULL,
  `buyer_name` varchar(32) default NULL,
  `seller_name` varchar(32) default NULL,
  `buyer_account` varchar(32) default NULL COMMENT '��������ʺţ�Ŀǰϵͳ����û����������ʺŵģ���������ֶο���Ϊ��',
  `buyer_bank` varchar(64) default NULL,
  `seller_account` varchar(32) default NULL COMMENT '���������ʺţ��������û�������ʺ�������֧��',
  `seller_bank` varchar(128) default NULL,
  `buyer_id` varchar(32) default NULL,
  `seller_id` varchar(32) default NULL,
  `pay_fee` int(11) default NULL,
  `pay_status` char(1) default NULL COMMENT '֧��״̬��֧�����ɹ��Զ���Ҳ�ǵȴ�֧��״̬��֧���ɹ������ɹ�S(�ɹ�)F( ʧ��)I(��ʼ״̬)',
  `trans_status` char(1) default NULL COMMENT 'ת��״̬��S(�ɹ�)I( δת��)',
  `pay_date` datetime default NULL COMMENT 'ȥ����֧����ʱ��',
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
  `id` varchar(32) NOT NULL COMMENT 'ͼƬ���',
  `goods_id` varchar(32) NOT NULL COMMENT '��Ʒ���',
  `path` varchar(128) default NULL COMMENT '��ƷͼƬ·��',
  `status` char(1) default NULL COMMENT 'N������Dɾ��',
  `gmt_modify` datetime default NULL COMMENT '����޸�ʱ��',
  `gmt_create` datetime default NULL COMMENT '����ʱ��',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='��ƷͼƬ��';

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
  `status` char(1) NOT NULL COMMENT '''T'' ������ʱ��ַ ''N'' ���������ĵ�ַ��ַ',
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