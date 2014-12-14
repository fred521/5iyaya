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
   user_id              int(10)                          not null COMMENT '����Ա���' AUTO_INCREMENT,
   login_name           varchar(20) COMMENT '����Ա��½����' ,
   login_password       varchar(32) COMMENT '����Ա��½����',
   user_status          char(1) COMMENT '����Ա״̬:N������P����',
   phone                varchar(32) COMMENT '��ϵ�绰',
   gmt_create           datetime COMMENT '����ʱ��',
   creator              varchar(32) COMMENT '������',
   gmt_modify           datetime COMMENT '����޸�ʱ��',
   modifier             varchar(32) COMMENT '����޸���',
   constraint PK_ADMIN_USER primary key (user_id)
) COMMENT '����Ա��Ϣ��';

/*==============================================================*/
/* Table: commend_content                                       */
/*==============================================================*/
create table commend_content  (
   content_id           int(10)                          not null COMMENT '�Ƽ����ݱ��' AUTO_INCREMENT,
   commend_position_id  int(10)                          not null COMMENT '�����Ƽ�λ�ñ��',
   commend_type         int(1)                           not null COMMENT '�Ƽ����ͣ�1����Ʒ��2�����̡�3����ѯ��Ϣ',
   commend_text         varchar(128)                     not null COMMENT '�Ƽ�����',
   pic_path             varchar(128) COMMENT '�Ƽ�ͼƬ·�������ͼƬurlΪ�գ�����Ҫ�������ַ�滻ͼƬ��ַ',
   pic_url              varchar(128) COMMENT '�Ƽ�ͼƬurl',
   batch_num            int(10) COMMENT '��������',
   batch_price          int(11) COMMENT '�����۸񣬿��Ƶ���',
   commend_status       char(1) COMMENT '�Ƽ�����״̬:N������Dɾ����ȡ��C',
   commend_url          varchar(128) COMMENT '�Ƽ���Ϣurl',
   commend_desc         varchar(128) COMMENT '�Ƽ�˵��',
   gmt_start            datetime                         not null COMMENT '�Ƽ���ʼ����',
   gmt_end              datetime                         not null COMMENT '�Ƽ���������',
   gmt_create           datetime COMMENT '����ʱ��',
   creator              varchar(32) COMMENT '������',
   gmt_modify           datetime COMMENT '����޸�ʱ��',
   modifier             varchar(32) COMMENT '����޸���',
   constraint PK_COMMEND_CONTENT primary key (content_id)
) comment '�Ƽ���Ϣ��';

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
   commend_id           int(10)                          not null  AUTO_INCREMENT comment '�Ƽ�λ�ñ��',
   commend_code         varchar(32)                    not null comment '�Ƽ�λ��ʶ���룺ֻ�������ֻ����ƣ�һ�������������޸�',
   position_order       int(10)                          not null comment 'λ��˳��',
   commend_type         CHAR(1)                         not null comment '1:��Ʒ�Ƽ�,2:�����Ƽ�,3:������Ϣ�Ƽ�',
   commend_page         int(2)                       not null comment '�����Ƽ�ҳ�棺0����ҳ1����ѯҳ�桢2����Ʒҳ�桢3����ά��ҳ',
   commend_name         varchar(128)                   not null comment '�Ƽ�λ������',
   commend_content_type char(1)                         not null comment '�Ƽ��������ͣ�1��ͼƬ�����֡�2����ͼƬ��3��������',
   pic_width            int(10) comment 'ͼƬ���',
   pic_height           int(10) comment 'ͼƬ�߶�',
   text_length          int(10) comment '���ֳ���',
   pic_path             varchar(128) comment '�滻ͼƬ·��',
   replace_text         varchar(128) comment '�滻����',
   gmt_create           datetime comment '����ʱ��',
   creator              varchar(32) comment '������',
   gmt_modify           datetime comment '����޸�ʱ��',
   modifier             varchar(32) comment '����޸���',
   constraint PK_COMMEND_POSITION primary key (commend_id),
   constraint AK_COMMEND_POSITION unique (commend_code, position_order)
) comment '�Ƽ�λ�ñ�';

/*==============================================================*/
/* Table: goods_base_info                                       */
/*==============================================================*/
create table goods_base_info  (
   goods_id             varchar(32)                    not null comment '��Ʒ���',
   shop_id              varchar(32)                    not null comment '���̱��',
   member_id            varchar(32) comment '��Ա���',
   goods_name           varchar(128) comment '��Ʒ����',
   goods_cat            varchar(3) comment '��ƷĿǰΪ2�����࣬���ֶδ洢���������ࣺ��һ������Ϊ001�����������Ϊ001001 or 001002 ������Ҫ����һ�������ѯʱ����Ҫ��ȡ���еĶ���������в�ѯ',
   batch_price          int(11) comment '��Ʒ�����۸񣬼۸񵽷�',
   batch_num            int(10) comment '��Ʒ��������',
   price_des            varchar(128) comment '�۸�����',
   goods_num            int(10) comment '��Ʒ�����',
   goods_pic            varchar(128) comment '��ƷͼƬ·��',
   abandon_days         int(10) comment '��Ʒ��������',
   gmt_abandon          datetime comment '��Ʒ����������������Ʒ������������Ʒ����ʱ�����õ�����Ʒ�ֶ��¼������ʱ��Ϊ�¼�ʱ��',
   goods_status         char(1) comment 'N������Dɾ����E�����ڣ���Ʒһ��������Ϊ��������Ʒ�ϼ���Ϊ��������Ҫ���¼������ʱ��',
   view_count           int(10) comment '��Ʒ���������ÿ�������һ',
   creator              varchar(32) comment '������',
   gmt_modify           datetime comment '����޸�ʱ��',
   modifier             varchar(32) comment '����޸���',
   gmt_create           datetime comment '����ʱ��',
   cat_path             varchar(32) comment '����·��',
   constraint PK_GOODS_BASE_INFO primary key (goods_id)
) comment '��Ʒ������';

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
   type_id              varchar(3) comment '������',
   type_name            varchar(128) comment '��������',
   levels               int(1) comment '���Ϊһ�����࣬��Ϊ1����������Ϊ2',
   parent_id            varchar(3) comment '������Ŀ���',
   show_order           int(10)  comment '��ʾ˳��'
) comment '��Ʒ����';

/*==============================================================*/
/* Table: goods_content                                         */
/*==============================================================*/
create table goods_content  (
   goods_id             varchar(32)                    not null comment '��Ʒ���',
   content              text comment '��Ʒ��������',
   constraint PK_GOODS_CONTENT primary key (goods_id)
) comment '��Ʒ��Ϣ����';

/*==============================================================*/
/* Table: market_type                                           */
/*==============================================================*/
create table market_type  (
   market_type          varchar(2)                     not null comment '�г�����  01��02',
   market_name          varchar(128) comment '�г�����',
   market_gis           varchar(128) comment '�г���ά��ַ',
   market_address       varchar(128) comment '�г���ַ',
   show_order           int(10) comment '��ʾ˳��',
   constraint PK_MARKET_TYPE primary key (market_type)
) comment '�г�������Ϣ';

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member  (
   member_id            varchar(32)                    not null comment '��Ա���',
   nick                 varchar(20) comment '�ǳƣ���ʾ��',
   login_password       varchar(32)                    not null comment '������Ҫmd5����',
   mobile               varchar(15)                    not null comment '�ֻ����벻��Ϊ�գ���Ҫͨ���ֻ�������֤',
   email                varchar(32) comment '�����ʼ���ַ',
   name                 varchar(20)                    not null comment '��ʵ����',
   member_type          char(1)                        not null comment '��Ա���ͣ����B������S���������в�ͬ�Ļ�ԱȨ��',
   sex                  varchar(2) comment '�Ա�M �С�FŮ',
   area_code            varchar(6) comment '���ڵ���������ʡ����������������ϳ�һ����������',
   phone                varchar(32) comment '�̶��绰',
   post_code            varchar(6)                     not null comment '��������',
   address              varchar(128)                   not null comment '��ϵ��ַ',
   status               char(1)                         not null comment '��Ա״̬������P������N,Dɾ���������Ա�����������ܵ�½',
   phone_validate       char(1)                         not null comment '�ֻ�У�飺ͨ��Y����N',
   gmt_register         datetime comment 'ע��ʱ��',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   gmt_last_login       datetime comment '����½ʱ��',
   login_count          int(10) comment '��½����',
   login_id             varchar(20)                    not null comment '��Ա��½id�����������ġ�Ψһ�����޸�',
   register_ip          varchar(15) comment 'ע��ʱ��ip',
   last_login_ip        varchar(15) comment '����½��ip',
   constraint PK_MEMBER primary key (member_id)
) comment '��Ա��Ϣ��';

/*==============================================================*/
/* Index: IDX_MEMBER_LOGIN_ID                                   */
/*==============================================================*/
create index IDX_MEMBER_LOGIN_ID on member (
   login_id ASC
);

/*==============================================================*/
/* Table: mobile_validate  
 * �ֻ���֤�������Ա�ֻ�û��У��ͨ��������������л����һ���ֻ�У���¼�����У��ͨ������ɾ���˱��е���Ϣ���ط�У������Ҫɾ������ļ�¼������һ����¼                                     */
/*==============================================================*/
create table mobile_validate  (
   member_id            varchar(32)                    not null comment '��Ա���',
   phone                varchar(15) comment 'У����ֻ�����',
   validate_code        varchar(6) comment '�����ֻ�У����:6λ�ֻ�У����',
   constraint PK_MOBILE_VALIDATE primary key (member_id)
) comment '   ';

/*==============================================================*/
/* Table: news_base_info                                        */
/*==============================================================*/
create table news_base_info  (
   news_id              varchar(32)                    not null comment '��ҵ��Ѷ���',
   news_title           varchar(128)                   not null comment '��ҵ��Ѷ����',
   news_type            varchar(2)                     not null comment '��ҵ��Ѷ��������',
   phone                varchar(32) comment '��ϵ�绰',
   member_id            varchar(32)                    not null comment '��Ϣ������Ա',
   nick                 varchar(20)                    not null comment '�ǳƣ���½ʹ�ã�ȫ��Ψһ',
   news_status          char(1)                         not null comment '��ҵ��Ѷ״̬:N ������P����',
   view_count           int(10) comment '�������',
   abandon_days         int(10) comment '��Ϣ��������',
   gmt_abandon          datetime comment '������Ʒ������������Ʒ����ʱ�����õ�',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   constraint PK_NEWS_BASE_INFO primary key (news_id)
) comment '��ҵ��Ѷ��';

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
   news_id              varchar(32)                    not null comment '��ҵ��Ѷ���',
   content              text comment '��ҵ��ѯ����',
   constraint PK_NEWS_CONTENT primary key (news_id)
) comment '��ҵ��Ѷ����';

/*==============================================================*/
/* Table: news_type                                             */
/*==============================================================*/
create table news_type  (
   news_type            char(2)                         not null comment '��Ϣ��ţ�01��02',
   type_name            varchar(128) comment '��Ϣ����',
   show_order           int(10) comment '��ʾ����',
   constraint PK_NEWS_TYPE primary key (news_type)
) comment '��ҵ��Ѷ�����';

/*==============================================================*/
/* Table: quartz_log
 * ��ʱ�����磺QUARTZ_INDEX_GOODS����Ʒ��������ʱ����QUARTZ_COMMEND���Ƽ������ִ��ʱ�䣩   
 * ��ʱ����ִ�е�ʱ���¼�����ݲ�ͬ����������ȡ��ͬ�����ִ��ʱ��                                         */
/*==============================================================*/
create table quartz_log  (
   quartz_type          varchar(20)                    not null comment '  ',
   gmt_execute          datetime comment '���ִ�ж�ʱʱ��',
   quartz_memo          varchar(255) comment '��ʱ˵��',
   constraint PK_QUARTZ_LOG primary key (quartz_type)
) comment ' ';

/*==============================================================*/
/* Table: search_key_word                                       */
/*==============================================================*/
create table search_key_word  (
   key_name             varchar(20) comment '�����ؼ�������/ҳ����������Ҫ������������',
   key_type             varchar(16) comment '�����ؼ�������:1����Ʒ��2�����̡�3��������4����Ѷ��Ϣ',
   search_count         int(10) comment '�ùؼ�����������',
   key_id               int(10)                          not null AUTO_INCREMENT comment '�ؼ��ֱ��',
   constraint PK_SEARCH_KEY_WORD primary key (key_id)
) comment '�����ؼ��ֱ�';

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
   shop_id              varchar(32)                    not null comment '���̱��',
   member_id            varchar(32)                    not null comment '��Ա���',
   login_id             varchar(20)                    not null comment '��Ա��½id',
   shop_name            varchar(128)                   not null comment '��������',
   shop_owner           varchar(20)                    not null comment '��������',
   commodity            varchar(128)                   not null comment '��Ӫ��Ʒ',
   belong_market_id     varchar(2)                     not null comment '�����г���ţ����е��г�����λ����',
   address              varchar(128)                   not null comment '���̵�ַ',
   phone                varchar(30) comment '���ڹ̻�',
   bank                 varchar(10) comment '��������',
   bank_account         varchar(20) comment '�����ʺ�',
   account_name         varchar(20) comment '����������',
   goods_count          int(10) comment '',
   camera               char(1)                        default 'N' comment '�Ƿ�����������ͷ��Y��N��',
   web_phone            char(1)                        default 'N' comment '�Ƿ񼯳�web800��Y�ǣ�N��',
   logo                 varchar(128) comment 'logoͼƬ·��',
   shop_img             varchar(128) comment '������Ƭ',
   gmt_create           datetime,
   creator              varchar(32),
   gmt_modify           datetime,
   modifier             varchar(32),
   gis_address          varchar(128) comment '��ά��ַ/����ʱ¼��/��Ա�Լ������޸���ά��ַ',
   constraint PK_SHOP primary key (shop_id)
) comment '���̻�����һ������ֻ����һ������';

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
   shop_id              varchar(32)                    not null comment '���̱��',
   commend              text comment '���̹�������',
   constraint PK_SHOP_COMMEND primary key (shop_id)
) comment '���̹����';

/*==============================================================*/
/* Table: sms                                                   */
/*==============================================================*/
create table sms  (
   sms_id               int(10)                          not null  AUTO_INCREMENT comment '���ű��',
   phone                varchar(11) comment '���η����ֻ���',
   status               char(1) comment '����״̬:W���ȴ����ͣ�S�����ͳɹ�',
   context              varchar(255) comment '��������',
   gmt_create           datetime comment '����ʱ��',
   gmt_modify           datetime comment '����޸�ʱ��',
   SEND_COUNT           int(10) comment '���ʹ���',
   constraint PK_SMS primary key (sms_id)
) comment '���ŷ��ͱ�';

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
/* Table: sign_shop ǩԼ���̱����ڴ洢������֧�����ĵ��̺��Լ�ѡ��ĵ��� */
/*==============================================================*/
create table sign_shop  (
   id                 int(10)                          not null AUTO_INCREMENT ,
   shop_id            varchar(32)                    not null comment '��Ա���',
   member_id          varchar(32)                    not null comment '��Աid',
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
/* Table: trade_car �ɹ��������ﳵ                                     */
/*==============================================================*/
create table trade_car  (
   id                 int(10)                          not null AUTO_INCREMENT comment '��Ա���',
   goods_id           varchar(32)                                              comment '��Ʒid',
   shop_id            varchar(32)                                              comment '����id',
   owner              varchar(32)                                              comment '������id',
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
/* Table: trade_car_shop ���ﳵ�����еĵ��̱���ֹ�������ﳵ����distinct*/
/*==============================================================*/
create table trade_car_shop  (
   shop_id            varchar(32) comment '����id',
   owner              varchar(32) comment '������id'
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
   order_no           varchar(32)                    not null comment '������20070910(����)+8Ϊ���к�',
   shop_id            varchar(32),
   shop_name          varchar(128),
   pay_fee            int(11),
   status             varchar(32)  comment '����״̬��
	������ʼ״̬   ORDER_INIT
	�ȴ�����ȷ��    WAIT_SELLER_CONFIRM
	�ȴ����ȷ��    WAIT_BUYER_CONFIRM
	��������           ORDER_CLOSE
	˫����ȷ��       WAIT_PAY
	�����Ѿ�֧��   ORDER_SUCCESS',
   order_date         varchar(8),
   buyer_id           varchar(32),
   seller_id          varchar(32),
   order_type         char(1) comment '��������:F�����ٶ�����T�����׶��������ٶ�������Ʒ��Ϣ',
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
/* Table: trade_order_item ������Ʒ��ϸ��һ�����������ж����Ʒ���ɹ�����Ŀ�� */
/*==============================================================*/
create table trade_order_item  (
   id                 int(10)                          not null AUTO_INCREMENT comment '��Ա���',
   order_no           varchar(32),
   goods_id           varchar(32),
   shop_id            varchar(32),
   goods_name         varchar(128),
   batch_price        int(11)                                                 comment '��Ʒ�����򵥸����ۣ���λ��',
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
/* Table: trade_pay ����֧����Ϣ��
����֧��ֻҪ������ͱ�����                                          */
/*==============================================================*/
create table trade_pay  (
   id                 int(10)                          not null AUTO_INCREMENT comment '�ɹ������',
   order_no           varchar(32)                    not null,
   buyer_name         varchar(32),
   seller_name        varchar(32),
   buyer_account      varchar(32)                             comment '��������ʺţ�Ŀǰϵͳ����û����������ʺŵģ���������ֶο���Ϊ��',
   buyer_bank         varchar(64),
   seller_account     varchar(32)                             comment '���������ʺţ��������û�������ʺ�������֧��',
   seller_bank        varchar(128),
   buyer_id           varchar(32),
   seller_id          varchar(32),
   pay_fee            int(11),
   pay_status         char(1)                                 comment '֧��״̬��֧�����ɹ��Զ���Ҳ�ǵȴ�֧��״̬��֧���ɹ������ɹ�S(�ɹ�)F( ʧ��)I(��ʼ״̬)',
   trans_status       char(1)                                 comment 'ת��״̬��S(�ɹ�)I( δת��)',
   pay_date           datetime                                comment 'ȥ����֧����ʱ��',
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




