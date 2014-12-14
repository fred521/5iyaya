delimiter //
USE `shang`//

/* Function  structure for function  `currval` */

drop function  if exists `currval`//

CREATE FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(8)
BEGIN
	DECLARE value INTEGER;
	SET value = 0;
	SELECT current_value INTO value
	FROM sequence
	WHERE name = seq_name;
	RETURN value;
END//

/* Function  structure for function  `nextval` */

drop function  if exists `nextval`//

CREATE FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(8)
BEGIN
	UPDATE sequence
	SET	current_value = current_value + increment
	WHERE name = seq_name;
	RETURN currval(seq_name);
END//

/* Function  structure for function  `setval` */

drop function  if exists `setval`//

CREATE FUNCTION `setval`(seq_name VARCHAR(50), value INTEGER) RETURNS int(8)
BEGIN   
   UPDATE sequence   
   SET          current_value = value   
   WHERE name = seq_name;   
   RETURN currval(seq_name);   
END//


DELIMITER $$

DROP FUNCTION IF EXISTS `5iyya`.`func_get_split_string`$$

CREATE DEFINER=`root`@`localhost` FUNCTION `func_get_split_string`(
f_string varchar(1000),f_delimiter varchar(5),f_order int) RETURNS varchar(255) CHARSET utf8
BEGIN
  declare result varchar(255) default '';
  set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1));
  return result;
END$$

DELIMITER ;

DELIMITER $$

DROP FUNCTION IF EXISTS `5iyya`.`func_get_split_string_total`$$

CREATE DEFINER=`root`@`localhost` FUNCTION `func_get_split_string_total`(
f_string varchar(1000),f_delimiter varchar(5)
) RETURNS int(11)
BEGIN
  return 1+(length(f_string) - length(replace(f_string,f_delimiter,'')));
END$$

DELIMITER ;