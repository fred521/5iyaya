/*==============================================================*/
/* DBMS name:      MySQL  5.0                                      */
/* Created on:     2007-7-16 19:09:21                            */
/*==============================================================*/
create database if not exists shang ;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS ADMIN_LOG;
DROP TABLE IF EXISTS admin_user;
DROP TABLE IF EXISTS commend_content;
DROP TABLE IF EXISTS commend_position;
DROP TABLE IF EXISTS goods_base_info;
DROP TABLE IF EXISTS goods_cat;
DROP TABLE IF EXISTS goods_content;
DROP TABLE IF EXISTS market_type;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS mobile_validate;
DROP TABLE IF EXISTS news_base_info;
DROP TABLE IF EXISTS news_content;
DROP TABLE IF EXISTS news_type ;
DROP TABLE IF EXISTS quartz_log;
DROP TABLE IF EXISTS search_key_word  ;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS shop_commend;
DROP TABLE IF EXISTS sms;

SET FOREIGN_KEY_CHECKS=1;

/*==============================================================*/
/* Table: ADMIN_LOG                                             */
/*==============================================================*/
create table ADMIN_LOG  (
   ID                   int(10)          unsigned                not null AUTO_INCREMENT ,
   ADMIN_USER_ID        int(10),
   ACTION               varchar(255),
   GMT_CREATE           datetime,
   ACTION_IP            varchar(128),
   PARAMETER0           varchar(4000),
   PARAMETER1           varchar(4000),
   PARAMETER2           varchar(4000),
   PARAMETER3           varchar(4000),
   PARAMETER4           varchar(4000),
   constraint PK_ADMIN_LOG primary key (ID)
);

/*==============================================================*/
/* Table: admin_user                                            */
/*==============================================================*/
create table admin_user  (
   user_id              int(10)                          not null COMMENT '管理员编号' AUTO_INCREMENT,
   login_name           varchar(20) COMMENT '管理员登陆名称' ,
   login_password       varchar(32) COMMENT '管理员登陆密码',
   user_status          char(1) COMMENT '管理员状态:N正常、P禁用',
   phone                varchar(32) COMMENT '联系电话',
   gmt_create           datetime COMMENT '创建时间',
   creator              varchar(32) COMMENT '创建人',
   gmt_modify           datetime COMMENT '最后修改时间',
   modifier             varchar(32) COMMENT '最后修改人',
   constraint PK_ADMIN_USER primary key (user_id)
) COMMENT '管理员信息表';

/*==============================================================*/
/* Table: commend_content                                       */
/*==============================================================*/
create table commend_content  (
   content_id           int(10)                          not null COMMENT '推荐内容编号' AUTO_INCREMENT,
   commend_position_id  int(10)                          not null COMMENT '所属推荐位置编号',
   commend_type         int(1)                           not null COMMENT '推荐类型：1、商品、2、店铺、3、咨询信息',
   commend_text         varchar(128)                     not null COMMENT '推荐文字',
   pic_path             varchar(128) COMMENT '推荐图片路径、如果图片url为空，则需要用这个地址替换图片地址',
   pic_url              varchar(128) COMMENT '推荐图片url',
   batch_num            int(10) COMMENT '起批数量',
   batch_price          int(11) COMMENT '起批价格，控制到分',
   commend_status       char(1) COMMENT '推荐内容状态:N正常、D删除、取消C',
   commend_url          varchar(128) COMMENT '推荐信息url',
   commend_desc         varchar(128) COMMENT '推荐说明',
   gmt_start            datetime                         not null COMMENT '推荐开始日期',
   gmt_end              datetime                         not null COMMENT '推荐截至日期',
   gmt_create           datetime COMMENT '创建时间',
   creator              varchar(32) COMMENT '创建人',
   gmt_modify           datetime COMMENT '最后修改时间',
   modifier             varchar(32) COMMENT '最后修改人',
   constraint PK_COMMEND_CONTENT primary key (content_id)
) comment '推荐信息表';

/*==============================================================*/
/* Index: IDX_COMMEND_START_ENDDATE                             */
/*==============================================================*/
create index IDX_COMMEND_START_ENDDATE on commend_content (
   gmt_end ASC,
   gmt_start ASC
);

/*==============================================================*/
/* Table: commend_position                                      */
/*==============================================================*/
create table commend_position  (
   commend_id           int(10)                          not null  AUTO_INCREMENT comment '推荐位置编号',
   commend_code         varchar(32)                    not null comment '推荐位置识别码：只能用数字或名称，一旦建立，则不能修改',
   position_order       int(10)                          not null comment '位置顺序',
   commend_type         CHAR(1)                         not null comment '1:商品推荐,2:店铺推荐,3:分类信息推荐',
   commend_page         int(2)                       not null comment '所属推荐页面：0：首页1：咨询页面、2：商品页面、3：三维首页',
   commend_name         varchar(128)                   not null comment '推荐位置名称',
   commend_content_type char(1)                         not null comment '推荐内容类型：1、图片与文字、2、光图片、3、光文字',
   pic_width            int(10) comment '图片宽度',
   pic_height           int(10) comment '图片高度',
   text_length          int(10) comment '文字长度',
   pic_path             varchar(128) comment '替换图片路径',
   replace_text         varchar(128) comment '替换文字',
   gmt_create           datetime comment '创建时间',
   creator              varchar(32) comment '创建人',
   gmt_modify           datetime comment '最后修改时间',
   modifier             varchar(32) comment '最后修改人',
   constraint PK_COMMEND_POSITION primary key (commend_id),
   constraint AK_COMMEND_POSITION unique (commend_code, position_order)
) comment '推荐位置表';

/*==============================================================*/
/* Table: goods_base_info                                       */
/*==============================================================*/
create table goods_base_info  (
   goods_id             varchar(32)                    not null comment '商品编号',
   shop_id              varchar(32)                    not null comment '店铺编号',
   member_id            varchar(32) comment '会员编号',
   goods_name           varchar(128) comment '商品名称',
   goods_cat            varchar(3) comment '商品目前为2级分类，本字段存储到二级分类：如一级分类为001，则二级分类为001001 or 001002 ，当需要根据一级分类查询时，需要获取所有的二级分类进行查询',
   batch_price          int(11) comment '商品批发价格，价格到分',
   batch_num            int(10) comment '商品起批数量',
   price_des            varchar(128) comment '价格描述',
   goods_num            int(10) comment '商品库存量',
   goods_pic            varchar(128) comment '商品图片路径',
   abandon_days         int(10) comment '商品过期天数',
   gmt_abandon          datetime comment '商品过期月数：根据商品过期月数和商品创建时间计算得到；商品手动下架则这个时间为下架时间',
   goods_status         char(1) comment 'N正常，D删除，E：过期；商品一旦创建则为正常；商品上架则为正常，需要重新计算过期时间',
   view_count           int(10) comment '商品浏览次数：每次浏览加一',
   creator              varchar(32) comment '创建人',
   gmt_modify           datetime comment '最后修改时间',
   modifier             varchar(32) comment '最后修改人',
   gmt_create           datetime comment '创建时间',
   cat_path             varchar(32) comment '分类路径',
   constraint PK_GOODS_BASE_INFO primary key (goods_id)
) comment '商品基础表';

/*==============================================================*/
/* Index: IDX_GOODS_MEMBER_ID                                   */
/*==============================================================*/
create index IDX_GOODS_MEMBER_ID on goods_base_info (
   member_id ASC
);

/*==============================================================*/
/* Index: IDX_GOODS_SHOP_ID                                     */
/*==============================================================*/
create index IDX_GOODS_SHOP_ID on goods_base_info (
   shop_id ASC
);

/*==============================================================*/
/* Index: IDX_GOODS_MODIFYDATE                                  */
/*==============================================================*/
create index IDX_GOODS_MODIFYDATE on goods_base_info (
   gmt_modify ASC
);

/*==============================================================*/
/* Table: goods_cat                                             */
/*==============================================================*/
create table goods_cat  (
   type_id              varchar(3) comment '分类编号',
   type_name            varchar(128) comment '分类名称',
   levels               int(1) comment '如果为一级分类，则为1，二级分类为2',
   parent_id            varchar(3) comment '父级类目编号',
   show_order           int(10)  comment '显示顺序'
) comment '商品分类';

/*==============================================================*/
/* Table: goods_content                                         */
/*==============================================================*/
create table goods_content  (
   goods_id             varchar(32)                    not null comment '商品编号',
   content              text comment '商品描述内容',
   constraint PK_GOODS_CONTENT primary key (goods_id)
) comment '商品信息描述';

/*==============================================================*/
/* Table: market_type                                           */
/*==============================================================*/
create table market_type  (
   market_type          varchar(2)                     not null comment '市场分类  01、02',
   market_name          varchar(128) comment '市场名称',
   market_gis           varchar(128) comment '市场三维地址',
   market_address       varchar(128) comment '市场地址',
   show_order           int(10) comment '显示顺序',
   constraint PK_MARKET_TYPE primary key (market_type)
) comment '市场分类信息';

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member  (
   member_id            varchar(32)                    not null comment '会员编号',
   nick                 varchar(20) comment '昵称：显示用',
   login_password       varchar(32)                    not null comment '密码需要md5加密',
   mobile               varchar(15)                    not null comment '手机号码不能为空，需要通过手机号码验证',
   email                varchar(32) comment '电子邮件地址',
   name                 varchar(20)                    not null comment '真实姓名',
   member_type          char(1)                        not null comment '会员类型：买家B，卖家S；买卖家有不同的会员权限',
   sex                  varchar(2) comment '性别：M 男、F女',
   area_code            varchar(6) comment '所在地区：根据省、地区、市三级组合成一个地区编码',
   phone                varchar(32) comment '固定电话',
   post_code            varchar(6)                     not null comment '邮政编码',
   address              varchar(128)                   not null comment '联系地址',
   status               char(1)                         not null comment '会员状态：禁用P，正常N,D删除；如果会员被禁用怎不能登陆',
   phone_validate       char(1)                         not null comment '手机校验：通过Y，否N',
   gmt_register         datetime comment '注册时间',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   gmt_last_login       datetime comment '最后登陆时间',
   login_count          int(10) comment '登陆次数',
   login_id             varchar(20)                    not null comment '会员登陆id、不能有中文、唯一不可修改',
   register_ip          varchar(15) comment '注册时的ip',
   last_login_ip        varchar(15) comment '最后登陆的ip',
   constraint PK_MEMBER primary key (member_id)
) comment '会员信息表';

/*==============================================================*/
/* Index: IDX_MEMBER_LOGIN_ID                                   */
/*==============================================================*/
create index IDX_MEMBER_LOGIN_ID on member (
   login_id ASC
);

/*==============================================================*/
/* Table: mobile_validate  
 * 手机验证表，如果会员手机没有校验通过，则再这个表中会存在一条手机校验记录，如果校验通过，则删除此表中的信息，重发校验码需要删除这里的记录并新增一条记录                                     */
/*==============================================================*/
create table mobile_validate  (
   member_id            varchar(32)                    not null comment '会员编号',
   phone                varchar(15) comment '校验的手机号码',
   validate_code        varchar(6) comment '本次手机校验码:6位手机校验码',
   constraint PK_MOBILE_VALIDATE primary key (member_id)
) comment '   ';

/*==============================================================*/
/* Table: news_base_info                                        */
/*==============================================================*/
create table news_base_info  (
   news_id              varchar(32)                    not null comment '商业资讯编号',
   news_title           varchar(128)                   not null comment '商业资讯标题',
   news_type            varchar(2)                     not null comment '商业资讯所属分类',
   phone                varchar(32) comment '联系电话',
   member_id            varchar(32)                    not null comment '信息所属会员',
   nick                 varchar(20)                    not null comment '昵称：登陆使用，全局唯一',
   news_status          char(1)                         not null comment '商业资讯状态:N 正常、P过期',
   view_count           int(10) comment '浏览次数',
   abandon_days         int(10) comment '信息过期月数',
   gmt_abandon          datetime comment '根据商品过期月数和商品创建时间计算得到',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   constraint PK_NEWS_BASE_INFO primary key (news_id)
) comment '商业资讯表';

/*==============================================================*/
/* Index: IDX_NEWS_MEMBER_ID                                    */
/*==============================================================*/
create index IDX_NEWS_MEMBER_ID on news_base_info (
   member_id ASC
);

/*==============================================================*/
/* Index: IDX_NEWS_MODIFYDATE                                   */
/*==============================================================*/
create index IDX_NEWS_MODIFYDATE on news_base_info (
   gmt_modify ASC
);

/*==============================================================*/
/* Table: news_content                                          */
/*==============================================================*/
create table news_content  (
   news_id              varchar(32)                    not null comment '商业资讯编号',
   content              text comment '商业咨询内容',
   constraint PK_NEWS_CONTENT primary key (news_id)
) comment '商业资讯内容';

/*==============================================================*/
/* Table: news_type                                             */
/*==============================================================*/
create table news_type  (
   news_type            char(2)                         not null comment '信息编号：01、02',
   type_name            varchar(128) comment '信息名称',
   show_order           int(10) comment '显示排序',
   constraint PK_NEWS_TYPE primary key (news_type)
) comment '商业资讯分类表';

/*==============================================================*/
/* Table: quartz_log
 * 定时类型如：QUARTZ_INDEX_GOODS（商品的索引定时）、QUARTZ_COMMEND（推荐的最后执行时间）   
 * 定时程序执行的时间记录，根据不同的类型来获取不同的最后执行时间                                         */
/*==============================================================*/
create table quartz_log  (
   quartz_type          varchar(20)                    not null comment '  ',
   gmt_execute          datetime comment '最后执行定时时间',
   quartz_memo          varchar(255) comment '定时说明',
   constraint PK_QUARTZ_LOG primary key (quartz_type)
) comment ' ';

/*==============================================================*/
/* Table: search_key_word                                       */
/*==============================================================*/
create table search_key_word  (
   key_name             varchar(20) comment '搜索关键字名称/页面搜索中需要控制文字数量',
   key_type             varchar(16) comment '搜索关键字类型:1、商品、2、店铺、3、店主、4、资讯信息',
   search_count         int(10) comment '该关键字搜索次数',
   key_id               int(10)                          not null AUTO_INCREMENT comment '关键字编号',
   constraint PK_SEARCH_KEY_WORD primary key (key_id)
) comment '搜索关键字表';

/*==============================================================*/
/* Index: Idx_key_type                                          */
/*==============================================================*/
create unique index Idx_key_type on search_key_word (
   key_name ASC,
   key_type ASC
);

/*==============================================================*/
/* Table: shop                                                  */
/*==============================================================*/
create table shop  (
   shop_id              varchar(32)                    not null comment '店铺编号',
   member_id            varchar(32)                    not null comment '会员编号',
   login_id             varchar(20)                    not null comment '会员登陆id',
   shop_name            varchar(128)                   not null comment '店铺名称',
   shop_owner           varchar(20)                    not null comment '店主姓名',
   commodity            varchar(128)                   not null comment '主营商品',
   belong_market_id     varchar(2)                     not null comment '所在市场编号，所有的市场以两位编码',
   address              varchar(128)                   not null comment '店铺地址',
   phone                varchar(30) comment '店内固话',
   bank                 varchar(10) comment '开户银行',
   bank_account         varchar(20) comment '银行帐号',
   account_name         varchar(20) comment '开户人姓名',
   goods_count          int(10) comment '',
   camera               char(1)                        default 'N' comment '是否有网络摄像头，Y有N无',
   web_phone            char(1)                        default 'N' comment '是否集成web800：Y是，N否',
   logo                 varchar(128) comment 'logo图片路径',
   shop_img             varchar(128) comment '店铺照片',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   gis_address          varchar(128) comment '三维地址/新增时录入/会员自己不能修改三维地址',
   constraint PK_SHOP primary key (shop_id)
) comment '店铺基础表，一个卖家只能有一个店铺';

/*==============================================================*/
/* Index: IDX_SHOP_MEMBER_ID                                    */
/*==============================================================*/
create index IDX_SHOP_MEMBER_ID on shop (
   member_id ASC
);

/*==============================================================*/
/* Index: IDX_SHOP_MODIFYDATE                                   */
/*==============================================================*/
create index IDX_SHOP_MODIFYDATE on shop (
   gmt_modify ASC
);

/*==============================================================*/
/* Table: shop_commend                                          */
/*==============================================================*/
create table shop_commend  (
   shop_id              varchar(32)                    not null comment '店铺编号',
   commend              text comment '店铺公告内容',
   constraint PK_SHOP_COMMEND primary key (shop_id)
) comment '店铺公告表';

/*==============================================================*/
/* Table: sms                                                   */
/*==============================================================*/
create table sms  (
   sms_id               int(10)                          not null  AUTO_INCREMENT comment '短信编号',
   phone                varchar(11) comment '本次发送手机号',
   status               char(1) comment '发送状态:W，等待发送；S，发送成功',
   context              varchar(255) comment '发送内容',
   gmt_create           datetime comment '创建时间',
   gmt_modify           datetime comment '最后修改时间',
   SEND_COUNT           int(10) comment '发送次数',
   constraint PK_SMS primary key (sms_id)
) comment '短信发送表';

/*==============================================================*/
/* Index: IDX_SMS_MODIFYDATE                                    */
/*==============================================================*/
create index IDX_SMS_MODIFYDATE on sms (
   gmt_modify ASC,
   status ASC
);


--phase two

/*==============================================================*/
/* DBMS name:      mysql Version 5.0                           */
/* Created on:     2007-10-15 13:20:58                           */
/*==============================================================*/
/*==============================================================*/
/* Table: sign_shop 签约店铺表：用于存储我曾经支付过的店铺和自己选择的店铺 */
/*==============================================================*/
create table sign_shop  (
   id                 int(10)                          not null AUTO_INCREMENT ,
   shop_id            varchar(32)                    not null comment '会员编号',
   member_id          varchar(32)                    not null comment '会员id',
   constraint PK_SIGN_SHOP primary key (id)
);
/*==============================================================*/
/* Index: idx_sign_shop_memberid                              */
/*==============================================================*/
create index idx_sign_shop_memberid on sign_shop (
   member_id ASC
);

/*==============================================================*/
/* Index: IDX_SIGN_SHOP_SHOPMEMBER_ID                           */
/*==============================================================*/
create index IDX_SIGN_SHOP_SHOPMEMBER_ID on sign_shop (
   member_id ASC,
   shop_id ASC
);

/*==============================================================*/
/* Table: trade_car 采购单（购物车                                     */
/*==============================================================*/
create table trade_car  (
   id                 int(10)                          not null AUTO_INCREMENT comment '会员编号',
   goods_id           varchar(32)                                              comment '商品id',
   shop_id            varchar(32)                                              comment '店铺id',
   owner              varchar(32)                                              comment '创建人id',
   gmt_create         datetime,
   constraint PK_TRADE_CAR primary key (id)
);

/*==============================================================*/
/* Index: IDX_TRADE_CAR_OWNER                                   */
/*==============================================================*/
create index IDX_TRADE_CAR_OWNER on trade_car (
   owner ASC
);

/*==============================================================*/
/* Table: trade_car_shop 购物车中所有的店铺表，防止到到购物车表中distinct*/
/*==============================================================*/
create table trade_car_shop  (
   shop_id            varchar(32) comment '店铺id',
   owner              varchar(32) comment '创建人id'
);

/*==============================================================*/
/* Index: IDX_TRADE_CAR_SHOP_OWNER                              */
/*==============================================================*/
create index IDX_TRADE_CAR_SHOP_OWNER on trade_car_shop (
   owner ASC
);

/*==============================================================*/
/* Table: trade_order                                         */
/*==============================================================*/
create table trade_order  (
   order_no           varchar(32)                    not null comment '订单号20070910(日期)+8为序列号',
   shop_id            varchar(32),
   shop_name          varchar(128),
   pay_fee            int(11),
   status             varchar(32)  comment '订单状态：
	订单初始状态   ORDER_INIT
	等待卖家确认    WAIT_SELLER_CONFIRM
	等待买家确认    WAIT_BUYER_CONFIRM
	订单作废           ORDER_CLOSE
	双方已确认       WAIT_PAY
	订单已经支付   ORDER_SUCCESS',
   order_date         varchar(8),
   buyer_id           varchar(32),
   seller_id          varchar(32),
   order_type         char(1) comment '订单类型:F（快速订单）T（交易订单）快速订单无商品信息',
   memo               varchar(256),
   buyer_login_id     varchar(32),
   seller_login_id    varchar(32),
   creator            varchar(32),
   gmt_modify         datetime,
   modifier           varchar(32),
   gmt_create         datetime,
   constraint PK_TRADE_ORDER primary key (order_no)
);

/*==============================================================*/
/* Index: IDX_TRADE_ORDER_BUYER_STATUS                          */
/*==============================================================*/
create index IDX_TRADE_ORDER_BUYER_STATUS on trade_order (
   buyer_id ASC,
   status ASC
);

/*==============================================================*/
/* Index: IDX_TRADE_ORDER_SELLER_STATUS                         */
/*==============================================================*/
create index IDX_TRADE_ORDER_SELLER_STATUS on trade_order (
   seller_id ASC,
   status ASC
);
/*==============================================================*/
/* Table: trade_order_item 订单商品明细：一个订单可以有多个商品（采购单条目） */
/*==============================================================*/
create table trade_order_item  (
   id                 int(10)                          not null AUTO_INCREMENT comment '会员编号',
   order_no           varchar(32),
   goods_id           varchar(32),
   shop_id            varchar(32),
   goods_name         varchar(128),
   batch_price        int(11)                                                 comment '商品批量或单个单价，单位分',
   total_num          int(10),
   total_free         int(11),
   creator            varchar(32),
   gmt_modify         datetime,
   modifier           varchar(32),
   gmt_create         datetime,
   constraint PK_TRADE_ORDER_ITEM primary key (id)
);

/*==============================================================*/
/* Index: idx_trade_order_item_orderno                        */
/*==============================================================*/
create index idx_trade_order_item_orderno on trade_order_item (
   order_no ASC
);


/*==============================================================*/
/* Table: trade_order_log                                     */
/*==============================================================*/
create table trade_order_log  (
   id                 int(10)                          not null AUTO_INCREMENT,
   order_no           varchar(32)                    not null,
   status             varchar(32),
   member_type        char(1),
   pay_fee            int(11),
   memo               varchar(2000),
   creator            varchar(32),
   gmt_modify         datetime,
   modifier           varchar(32),
   gmt_create         datetime,
   constraint PK_TRADE_ORDER_LOG primary key (id)
);

/*==============================================================*/
/* Index: IDX_TRADE_ORDER_LOG_ORDERNO                           */
/*==============================================================*/
create index IDX_TRADE_ORDER_LOG_ORDERNO on trade_order_log (
   order_no ASC
);

/*==============================================================*/
/* Table: trade_order_note                                    */
/*==============================================================*/
create table trade_order_note  (
   id                 int(10)                          not null AUTO_INCREMENT,
   order_no           varchar(32)                    not null,
   member_type        char(1),
   memo               varchar(1024),
   creator            varchar(32),
   gmt_modify         datetime,
   modifier           varchar(32),
   gmt_create         datetime,
   constraint PK_TRADE_ORDER_NOTE primary key (id)
);
/*==============================================================*/
/* Index: IDX_TRADE_ORDER_NOTE_ORDERNO                          */
/*==============================================================*/
create index IDX_TRADE_ORDER_NOTE_ORDERNO on trade_order_note (
   order_no ASC
);
/*==============================================================*/
/* Table: trade_pay 网上支付信息表
快速支付只要订单表和本表即可                                          */
/*==============================================================*/
create table trade_pay  (
   id                 int(10)                          not null AUTO_INCREMENT comment '采购单编号',
   order_no           varchar(32)                    not null,
   buyer_name         varchar(32),
   seller_name        varchar(32),
   buyer_account      varchar(32)                             comment '买家银行帐号，目前系统中是没有买家银行帐号的，所以这个字段可以为空',
   buyer_bank         varchar(64),
   seller_account     varchar(32)                             comment '卖家银行帐号：如果卖家没有银行帐号则不允许支付',
   seller_bank        varchar(128),
   buyer_id           varchar(32),
   seller_id          varchar(32),
   pay_fee            int(11),
   pay_status         char(1)                                 comment '支付状态，支付不成功对订单也是等待支付状态、支付成功订单成功S(成功)F( 失败)I(初始状态)',
   trans_status       char(1)                                 comment '转帐状态：S(成功)I( 未转帐)',
   pay_date           datetime                                comment '去银行支付的时间',
   bank_no            varchar(64),
   trans_date         datetime,
   pay_success_date   datetime,
   creator            varchar(32),
   gmt_modify         datetime,
   modifier           varchar(32),
   gmt_create         datetime,
   constraint PK_TRADE_PAY primary key (id)
);
/*==============================================================*/
/* Index: IDX_TRADE_PAY_TRADEORDERNO                            */
/*==============================================================*/
create unique index IDX_TRADE_PAY_TRADEORDERNO on trade_pay (
   order_no ASC
);

/*==============================================================*/
/* Index: IDX_TRADE_PAY_BUYERID                                 */
/*==============================================================*/
create index IDX_TRADE_PAY_BUYERID on trade_pay (
   buyer_id ASC
);

/*==============================================================*/
/* Index: IDX_TRADE_PAY_SELLERID                                */
/*==============================================================*/
create index IDX_TRADE_PAY_SELLERID on trade_pay (
   seller_id ASC
);




