/*==============================================================*/
/* DBMS name:      ORACLE Version 9i2                           */
/* Created on:     2007-9-28 13:20:58                           */
/*==============================================================*/


drop index IDX_SIGN_SHOP_SHOPMEMBER_ID;

drop index idx_sign_shop_memberid;

drop index IDX_TRADE_CAR_OWNER;

drop index IDX_TRADE_CAR_SHOP_OWNER;

drop index IDX_TRADE_ORDER_BUYER_STATUS;

drop index IDX_TRADE_ORDER_SELLER_STATUS;

drop index idx_trade_order_item_orderno;

drop index IDX_TRADE_ORDER_LOG_ORDERNO;

drop index IDX_TRADE_ORDER_NOTE_ORDERNO;

drop index IDX_TRADE_PAY_BUYERID;

drop index IDX_TRADE_PAY_SELLERID;

drop index IDX_TRADE_PAY_TRADEORDERNO;

drop table sign_shop cascade constraints;

drop table trade_car cascade constraints;

drop table trade_car_shop cascade constraints;

drop table trade_order cascade constraints;

drop table trade_order_item cascade constraints;

drop table trade_order_log cascade constraints;

drop table trade_order_note cascade constraints;

drop table trade_pay cascade constraints;

drop sequence seq_sign_shop;

drop sequence seq_trade_car;

drop sequence seq_trade_order;

drop sequence seq_trade_order_item;

drop sequence seq_trade_order_log;

drop sequence seq_trade_order_note;

drop sequence seq_trade_pay;

create sequence seq_sign_shop;

create sequence seq_trade_car;

create sequence seq_trade_order
start with 10000000;

create sequence seq_trade_order_item;

create sequence seq_trade_order_log;

create sequence seq_trade_order_note;

create sequence seq_trade_pay;

/*==============================================================*/
/* Table: sign_shop                                           */
/*==============================================================*/
create table sign_shop  (
   id                 number                          not null,
   shop_id            varchar2(32)                    not null,
   member_id          varchar2(32)                    not null,
   constraint PK_SIGN_SHOP primary key (id)
);

comment on table sign_shop is
'签约店铺表：用于存储我曾经支付过的店铺和自己选择的店铺';

comment on column sign_shop.shop_id is
'会员编号';

comment on column sign_shop.member_id is
'会员id';

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
/* Table: trade_car                                           */
/*==============================================================*/
create table trade_car  (
   id                 number                          not null,
   goods_id           varchar2(32),
   shop_id            varchar2(32),
   owner              varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_CAR primary key (id)
);

comment on table trade_car is
'采购单（购物车）';

comment on column trade_car.id is
'会员编号';

comment on column trade_car.goods_id is
'商品id';

comment on column trade_car.shop_id is
'店铺id';

comment on column trade_car.owner is
'创建人id';

comment on column trade_car.gmt_create is
'创建时间';

/*==============================================================*/
/* Index: IDX_TRADE_CAR_OWNER                                   */
/*==============================================================*/
create index IDX_TRADE_CAR_OWNER on trade_car (
   owner ASC
);

/*==============================================================*/
/* Table: trade_car_shop                                      */
/*==============================================================*/
create table trade_car_shop  (
   shop_id            varchar2(32),
   owner              varchar2(32)
);

comment on table trade_car_shop is
'购物车中所有的店铺表，防止到到购物车表中distinct';

comment on column trade_car_shop.shop_id is
'店铺id';

comment on column trade_car_shop.owner is
'创建人id';

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
   order_no           varchar2(32)                    not null,
   shop_id            varchar2(32),
   shop_name          varchar2(128),
   pay_fee            number,
   status             varchar2(32),
   order_date         varchar2(8),
   buyer_id           varchar2(32),
   seller_id          varchar2(32),
   order_type         char(1),
   memo               varchar2(256),
   buyer_login_id     varchar2(32),
   seller_login_id    varchar2(32),
   creator            varchar2(32),
   gmt_modify         date,
   modifier           varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_ORDER primary key (order_no)
);

comment on table trade_order is
'订单';

comment on column trade_order.order_no is
'订单号20070910(日期)+8为序列号';

comment on column trade_order.shop_id is
'店铺id';

comment on column trade_order.shop_name is
'店铺名称';

comment on column trade_order.pay_fee is
'订单费用';

comment on column trade_order.status is
'订单状态：
订单初始状态   ORDER_INIT
等待卖家确认    WAIT_SELLER_CONFIRM
等待买家确认    WAIT_BUYER_CONFIRM
订单作废           ORDER_CLOSE
双方已确认       WAIT_PAY
订单已经支付   ORDER_SUCCESS
';

comment on column trade_order.order_date is
'订单日期：20070909';

comment on column trade_order.buyer_id is
'买家编号';

comment on column trade_order.seller_id is
'卖家编号';

comment on column trade_order.order_type is
'订单类型:F（快速订单）T（交易订单）快速订单无商品信息';

comment on column trade_order.buyer_login_id is
'买家login_id';

comment on column trade_order.seller_login_id is
'卖家login_id';

comment on column trade_order.creator is
'创建人';

comment on column trade_order.gmt_modify is
'最后修改时间';

comment on column trade_order.modifier is
'最后修改人';

comment on column trade_order.gmt_create is
'创建时间';

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
/* Table: trade_order_item                                    */
/*==============================================================*/
create table trade_order_item  (
   id                 number                          not null,
   order_no           varchar2(32),
   goods_id           varchar2(32),
   shop_id            varchar2(32),
   goods_name         varchar2(128),
   batch_price        number,
   total_num          number,
   total_free         number,
   creator            varchar2(32),
   gmt_modify         date,
   modifier           varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_ORDER_ITEM primary key (id)
);

comment on table trade_order_item is
'订单商品明细：一个订单可以有多个商品（采购单条目）';

comment on column trade_order_item.id is
'会员编号';

comment on column trade_order_item.goods_id is
'商品id';

comment on column trade_order_item.shop_id is
'店铺id';

comment on column trade_order_item.goods_name is
'商品名称';

comment on column trade_order_item.batch_price is
'商品批量或单个单价，单位分';

comment on column trade_order_item.total_num is
'购买商品数量';

comment on column trade_order_item.total_free is
'该商品购买金额=(商品数量/ 商品起批数量)*商品批量或单个价格';

comment on column trade_order_item.creator is
'创建人';

comment on column trade_order_item.gmt_modify is
'最后修改时间';

comment on column trade_order_item.modifier is
'最后修改人';

comment on column trade_order_item.gmt_create is
'创建时间';

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
   id                 number                          not null,
   order_no           varchar2(32)                    not null,
   status             varchar2(32),
   member_type        char(1),
   pay_fee            number,
   memo               varchar2(2000),
   creator            varchar2(32),
   gmt_modify         date,
   modifier           varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_ORDER_LOG primary key (id)
);

comment on table trade_order_log is
'订单修改记录';

comment on column trade_order_log.id is
'会员编号';

comment on column trade_order_log.order_no is
'商品id';

comment on column trade_order_log.status is
'当前订单状态，可以用于记录订单历史状态';

comment on column trade_order_log.member_type is
'买卖方：买家B，卖家S；标识是卖家或买家修改';

comment on column trade_order_log.pay_fee is
'修改前的金额';

comment on column trade_order_log.memo is
'修改内容内容';

comment on column trade_order_log.creator is
'创建人';

comment on column trade_order_log.gmt_modify is
'最后修改时间';

comment on column trade_order_log.modifier is
'最后修改人';

comment on column trade_order_log.gmt_create is
'创建时间';

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
   id                 number                          not null,
   order_no           varchar2(32)                    not null,
   member_type        char(1),
   memo               varchar2(1024),
   creator            varchar2(32),
   gmt_modify         date,
   modifier           varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_ORDER_NOTE primary key (id)
);

comment on table trade_order_note is
'订单留言表';

comment on column trade_order_note.id is
'会员编号';

comment on column trade_order_note.order_no is
'商品id';

comment on column trade_order_note.member_type is
'买卖方：买家B，卖家S；标识是卖家或买家留言';

comment on column trade_order_note.memo is
'留言内容';

comment on column trade_order_note.creator is
'创建人';

comment on column trade_order_note.gmt_modify is
'最后修改时间';

comment on column trade_order_note.modifier is
'最后修改人';

comment on column trade_order_note.gmt_create is
'创建时间';

/*==============================================================*/
/* Index: IDX_TRADE_ORDER_NOTE_ORDERNO                          */
/*==============================================================*/
create index IDX_TRADE_ORDER_NOTE_ORDERNO on trade_order_note (
   order_no ASC
);

/*==============================================================*/
/* Table: trade_pay                                           */
/*==============================================================*/
create table trade_pay  (
   id                 number                          not null,
   order_no           varchar2(32)                    not null,
   buyer_name         varchar2(32),
   seller_name        varchar2(32),
   buyer_account      varchar2(32),
   buyer_bank         varchar2(64),
   seller_account     varchar2(32),
   seller_bank        varchar2(128),
   buyer_id           varchar2(32),
   seller_id          varchar2(32),
   pay_fee            number,
   pay_status         char(1),
   trans_status       char(1),
   pay_date           date,
   bank_no            varchar2(64),
   trans_date         date,
   pay_success_date   date,
   creator            varchar2(32),
   gmt_modify         date,
   modifier           varchar2(32),
   gmt_create         date,
   constraint PK_TRADE_PAY primary key (id)
);

comment on table trade_pay is
'网上支付信息表
快速支付只要订单表和本表即可';

comment on column trade_pay.id is
'采购单编号';

comment on column trade_pay.order_no is
'订单号';

comment on column trade_pay.buyer_account is
'买家银行帐号，目前系统中是没有买家银行帐号的，所以这个字段可以为空';

comment on column trade_pay.buyer_bank is
'支付银行';

comment on column trade_pay.seller_account is
'卖家银行帐号：如果卖家没有银行帐号则不允许支付';

comment on column trade_pay.buyer_id is
'买家编号';

comment on column trade_pay.seller_id is
'卖家编号';

comment on column trade_pay.pay_status is
'支付状态，支付不成功对订单也是等待支付状态、支付成功订单成功S(成功)F( 失败)I(初始状态)';

comment on column trade_pay.trans_status is
'转帐状态：S(成功)I( 未转帐)';

comment on column trade_pay.pay_date is
'去银行支付的时间';

comment on column trade_pay.trans_date is
'转帐时间';

comment on column trade_pay.pay_success_date is
'支付成功时间';

comment on column trade_pay.creator is
'创建人';

comment on column trade_pay.gmt_modify is
'最后修改时间';

comment on column trade_pay.modifier is
'最后修改人';

comment on column trade_pay.gmt_create is
'创建时间';

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

