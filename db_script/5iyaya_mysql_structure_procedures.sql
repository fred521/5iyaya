delimiter //
USE `shang`//

/* Procedure structure for procedure `list_top6_belong_market_id` */

drop procedure if exists `list_top6_belong_market_id`//


CREATE PROCEDURE `list_top6_belong_market_id`()
BEGIN
set @num = 0;
set @belong_market_id:='';
select * from ( 
    select @num := if(@belong_market_id = belong_market_id, @num + 1, 0) as ROWNUM , @belong_market_id:=belong_market_id as dummy,t.* 
     from shop t order by t.belong_market_id,t.gmt_create desc
) as A  where ROWNUM< 6;
END//



/* Procedure structure for procedure `list_top10_news_by_type` */

drop procedure if exists `list_top10_news_by_type`//


CREATE PROCEDURE `list_top10_news_by_type`()
BEGIN
set @num=0;
set @news_type:='';
select * from ( 
    select @num := if(@news_type = news_type, @num + 1, 0) as ROWNUM , @news_type:=news_type as dummy,t.* 
     from news_base_info t order by t.news_type,t.gmt_create desc
) as A where ROWNUM< 10;
END//

/* Procedure structure for procedure `list_yy_news_by_type` */

drop procedure if exists `list_yy_news_by_type`//


CREATE PROCEDURE `list_yy_news_by_type`()
BEGIN
set @num=0;
set @news_type:='';
select * from ( 
    select @num := if(@news_type = news_type, @num + 1, 0) as ROWNUM , @news_type:=news_type as dummy,t.* 
     from news_base_info t order by t.news_type,t.gmt_create desc
) as A where ROWNUM< 10;
END//
delimiter ;







DELIMITER $$

DROP PROCEDURE IF EXISTS `5iyya`.`pro_insert_goods`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_insert_goods`(login_id varchar(32),goods_id varchar(32),
				  goods_name varchar(128),
                                  goods_cat varchar(3),market_price int(11),
                                  batch_price int(11),batch_num int(10),
                                  group_num int(10),price_des varchar(128),
                                  goods_num int(10),goods_pic varchar(128),
                                  abandon_days int(10),
                                  goods_type varchar(1),
                                  goods_status varchar(1),
                                  cat_path varchar(32),
                                  size varchar(64),colors varchar(64),
                                  content text,
                                  images varchar(512),
                                  imageIds Varchar(512),
                                  contentId varchar(32),
                                  operator varchar(32),
                                  goods_code varchar(32),
                                  seller_goods_code varchar(32),
                                  properties varchar(128))
begin 
	declare imageNum int default 0;
	declare imageIndex int default 1;
	DECLARE imageUrl varchar(128);
	DECLARE imageId varchar(32);
	set imageIndex = 1;
	set imageNum=func_get_split_string_total(images,'|');
	
	select @memberId:=member.member_id,@shopid:=shop.shop_id from member left join shop on member.member_id = shop.member_id where
		member.login_id=login_id;
	Insert into goods_base_info values(goods_id,@shopid,@memberId,goods_name,goods_cat,
                                                market_price,batch_price,batch_num,group_num,price_des,goods_num,
                                                goods_pic,abandon_days,INTERVAL abandon_days DAY + now(),goods_type,goods_status,
                                                0,operator,current_timestamp,operator,current_timestamp,
                                                cat_path,size,colors,goods_code,seller_goods_code,properties);
        INSert into goods_content values(goods_id,content);
	while imageIndex <= imageNum do
                set imageUrl = func_get_split_string(images,'|',imageIndex);
                SET imageId =func_get_split_string(imageIds,'|',imageIndex);
                insert into picture_info values(imageId,goods_id,imageUrl,'N',current_timestamp,current_timestamp);
                set imageIndex = imageIndex + 1;
        end while;
	
end$$

DELIMITER ;
