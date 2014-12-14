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
'推荐位置表';

comment on column commend_position.commend_id is
'推荐位置编号';

comment on column commend_position.commend_code is
'推荐位置识别码：只能用数字或名称，一旦建立，则不能修改';

comment on column commend_position.position_order is
'位置顺序';

comment on column commend_position.commend_type is
'1:商品推荐,2:店铺推荐,3:分类信息推荐';

comment on column commend_position.commend_page is
'所属推荐页面：0：首页1：咨询页面、2：商品页面、3：三维首页';

comment on column commend_position.commend_name is
'推荐位置名称';

comment on column commend_position.commend_content_type is
'推荐内容类型：1、图片与文字、2、光图片、3、光文字';

comment on column commend_position.pic_width is
'图片宽度';

comment on column commend_position.pic_height is
'图片高度';

comment on column commend_position.text_length is
'文字长度';

comment on column commend_position.pic_path is
'替换图片路径';

comment on column commend_position.replace_text is
'替换文字';

comment on column commend_position.gmt_create is
'创建时间';

comment on column commend_position.creator is
'创建人';

comment on column commend_position.gmt_modify is
'最后修改时间';

comment on column commend_position.modifier is
'最后修改人';

/*==============================================================*/
/* Index: IDX_MEMBER_LOGIN_ID                                   */
/*==============================================================*/
create index IDX_MEMBER_LOGIN_ID on member (
   login_id ASC
);





--20070910
alter table member modify mobile null;
