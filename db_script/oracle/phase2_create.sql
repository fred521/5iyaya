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
'ǩԼ���̱����ڴ洢������֧�����ĵ��̺��Լ�ѡ��ĵ���';

comment on column sign_shop.shop_id is
'��Ա���';

comment on column sign_shop.member_id is
'��Աid';

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
'�ɹ��������ﳵ��';

comment on column trade_car.id is
'��Ա���';

comment on column trade_car.goods_id is
'��Ʒid';

comment on column trade_car.shop_id is
'����id';

comment on column trade_car.owner is
'������id';

comment on column trade_car.gmt_create is
'����ʱ��';

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
'���ﳵ�����еĵ��̱���ֹ�������ﳵ����distinct';

comment on column trade_car_shop.shop_id is
'����id';

comment on column trade_car_shop.owner is
'������id';

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
'����';

comment on column trade_order.order_no is
'������20070910(����)+8Ϊ���к�';

comment on column trade_order.shop_id is
'����id';

comment on column trade_order.shop_name is
'��������';

comment on column trade_order.pay_fee is
'��������';

comment on column trade_order.status is
'����״̬��
������ʼ״̬   ORDER_INIT
�ȴ�����ȷ��    WAIT_SELLER_CONFIRM
�ȴ����ȷ��    WAIT_BUYER_CONFIRM
��������           ORDER_CLOSE
˫����ȷ��       WAIT_PAY
�����Ѿ�֧��   ORDER_SUCCESS
';

comment on column trade_order.order_date is
'�������ڣ�20070909';

comment on column trade_order.buyer_id is
'��ұ��';

comment on column trade_order.seller_id is
'���ұ��';

comment on column trade_order.order_type is
'��������:F�����ٶ�����T�����׶��������ٶ�������Ʒ��Ϣ';

comment on column trade_order.buyer_login_id is
'���login_id';

comment on column trade_order.seller_login_id is
'����login_id';

comment on column trade_order.creator is
'������';

comment on column trade_order.gmt_modify is
'����޸�ʱ��';

comment on column trade_order.modifier is
'����޸���';

comment on column trade_order.gmt_create is
'����ʱ��';

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
'������Ʒ��ϸ��һ�����������ж����Ʒ���ɹ�����Ŀ��';

comment on column trade_order_item.id is
'��Ա���';

comment on column trade_order_item.goods_id is
'��Ʒid';

comment on column trade_order_item.shop_id is
'����id';

comment on column trade_order_item.goods_name is
'��Ʒ����';

comment on column trade_order_item.batch_price is
'��Ʒ�����򵥸����ۣ���λ��';

comment on column trade_order_item.total_num is
'������Ʒ����';

comment on column trade_order_item.total_free is
'����Ʒ������=(��Ʒ����/ ��Ʒ��������)*��Ʒ�����򵥸��۸�';

comment on column trade_order_item.creator is
'������';

comment on column trade_order_item.gmt_modify is
'����޸�ʱ��';

comment on column trade_order_item.modifier is
'����޸���';

comment on column trade_order_item.gmt_create is
'����ʱ��';

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
'�����޸ļ�¼';

comment on column trade_order_log.id is
'��Ա���';

comment on column trade_order_log.order_no is
'��Ʒid';

comment on column trade_order_log.status is
'��ǰ����״̬���������ڼ�¼������ʷ״̬';

comment on column trade_order_log.member_type is
'�����������B������S����ʶ�����һ�����޸�';

comment on column trade_order_log.pay_fee is
'�޸�ǰ�Ľ��';

comment on column trade_order_log.memo is
'�޸���������';

comment on column trade_order_log.creator is
'������';

comment on column trade_order_log.gmt_modify is
'����޸�ʱ��';

comment on column trade_order_log.modifier is
'����޸���';

comment on column trade_order_log.gmt_create is
'����ʱ��';

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
'�������Ա�';

comment on column trade_order_note.id is
'��Ա���';

comment on column trade_order_note.order_no is
'��Ʒid';

comment on column trade_order_note.member_type is
'�����������B������S����ʶ�����һ��������';

comment on column trade_order_note.memo is
'��������';

comment on column trade_order_note.creator is
'������';

comment on column trade_order_note.gmt_modify is
'����޸�ʱ��';

comment on column trade_order_note.modifier is
'����޸���';

comment on column trade_order_note.gmt_create is
'����ʱ��';

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
'����֧����Ϣ��
����֧��ֻҪ������ͱ�����';

comment on column trade_pay.id is
'�ɹ������';

comment on column trade_pay.order_no is
'������';

comment on column trade_pay.buyer_account is
'��������ʺţ�Ŀǰϵͳ����û����������ʺŵģ���������ֶο���Ϊ��';

comment on column trade_pay.buyer_bank is
'֧������';

comment on column trade_pay.seller_account is
'���������ʺţ��������û�������ʺ�������֧��';

comment on column trade_pay.buyer_id is
'��ұ��';

comment on column trade_pay.seller_id is
'���ұ��';

comment on column trade_pay.pay_status is
'֧��״̬��֧�����ɹ��Զ���Ҳ�ǵȴ�֧��״̬��֧���ɹ������ɹ�S(�ɹ�)F( ʧ��)I(��ʼ״̬)';

comment on column trade_pay.trans_status is
'ת��״̬��S(�ɹ�)I( δת��)';

comment on column trade_pay.pay_date is
'ȥ����֧����ʱ��';

comment on column trade_pay.trans_date is
'ת��ʱ��';

comment on column trade_pay.pay_success_date is
'֧���ɹ�ʱ��';

comment on column trade_pay.creator is
'������';

comment on column trade_pay.gmt_modify is
'����޸�ʱ��';

comment on column trade_pay.modifier is
'����޸���';

comment on column trade_pay.gmt_create is
'����ʱ��';

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

