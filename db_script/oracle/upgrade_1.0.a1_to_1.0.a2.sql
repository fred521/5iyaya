/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2007-7-15 1:08:00                            */
/*==============================================================*/


drop table commend_position cascade constraints;

/*==============================================================*/
/* Table: commend_position                                      */
/*==============================================================*/
create table commend_position  (
   commend_id           number                          not null,
   commend_code         varchar2(32)                    not null,
   position_order       NUMBER                          not null,
   commend_type         CHAR(1)                         not null,
   commend_page         number(2)                       not null,
   commend_name         varchar2(128)                   not null,
   commend_content_type char(1)                         not null,
   pic_width            number,
   pic_height           number,
   text_length          number,
   pic_path             varchar2(128),
   replace_text         varchar2(128),
   gmt_create           date,
   creator              varchar2(32),
   gmt_modify           date,
   modifier             varchar2(32),
   constraint PK_COMMEND_POSITION primary key (commend_id),
   constraint AK_COMMEND_POSITION unique (commend_code, position_order)
);

comment on table commend_position is
'�Ƽ�λ�ñ�';

comment on column commend_position.commend_id is
'�Ƽ�λ�ñ��';

comment on column commend_position.commend_code is
'�Ƽ�λ��ʶ���룺ֻ�������ֻ����ƣ�һ�������������޸�';

comment on column commend_position.position_order is
'λ��˳��';

comment on column commend_position.commend_type is
'1:��Ʒ�Ƽ�,2:�����Ƽ�,3:������Ϣ�Ƽ�';

comment on column commend_position.commend_page is
'�����Ƽ�ҳ�棺0����ҳ1����ѯҳ�桢2����Ʒҳ�桢3����ά��ҳ';

comment on column commend_position.commend_name is
'�Ƽ�λ������';

comment on column commend_position.commend_content_type is
'�Ƽ��������ͣ�1��ͼƬ�����֡�2����ͼƬ��3��������';

comment on column commend_position.pic_width is
'ͼƬ���';

comment on column commend_position.pic_height is
'ͼƬ�߶�';

comment on column commend_position.text_length is
'���ֳ���';

comment on column commend_position.pic_path is
'�滻ͼƬ·��';

comment on column commend_position.replace_text is
'�滻����';

comment on column commend_position.gmt_create is
'����ʱ��';

comment on column commend_position.creator is
'������';

comment on column commend_position.gmt_modify is
'����޸�ʱ��';

comment on column commend_position.modifier is
'����޸���';

/*==============================================================*/
/* Index: IDX_MEMBER_LOGIN_ID                                   */
/*==============================================================*/
create index IDX_MEMBER_LOGIN_ID on member (
   login_id ASC
);





--20070910
alter table member modify mobile null;
