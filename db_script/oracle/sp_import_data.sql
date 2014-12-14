create or replace procedure sp_import_date is
  type user_curosr is ref cursor;
  rc               user_curosr;
  login_id         varchar2(20);
  member_id        varchar2(32);
  mobile           varchar2(32);
  area_code        varchar2(6);
  default_password varchar2(32);
  default_mobile   varchar2(32);
  bank_type        varchar2(3);
  i_count          number;
  type row_type is record(
    name             user_info.name%type,
    tel_num          user_info.tel_num%type,
    mobile_phone     user_info.mobile_phone%type,
    bank_type        user_info.bank_type%type,
    BIRTH_PLACE      user_info.birth_place%type,
    note             shop_info.note%type,
    shop_name        shop_info.name%type,
    market           shop_info.market%type,
    id               shop_info.id%type,
    PRODUCT_TYPE_CHN shop_info.product_type_chn%type);

  import_row row_type;

begin
  --过程中发现需要字段扩容，实在是数据太乱了
  execute immediate 'alter table member modify name varchar2(30)';
  execute immediate 'alter table shop modify shop_owner varchar2(30)';
  
  default_password := 'e22fb54c6fcd2647f09da91d2420f793';
  default_mobile   := '13000000000';
  i_count := 0;
  dbms_output.put_line('打开游标');
  open rc for
    select trim(u.NAME), --用户姓名
           u.TEL_NUM, --电话
           nvl(u.MOBILE_PHONE, ''), --手机
           u.BANK_TYPE, --银行
           u.BIRTH_PLACE, --省份
           s.NOTE, --地址
           s.name as shop_name, --店铺名称
           decode(s.market,0,1,s.market) as market, --市场类型
           s.id,
           s.PRODUCT_TYPE_CHN --主营
      from user_info u, shop_info s
     where u.address_num = s.id;
    dbms_output.put_line('循环插入数据');
  loop
    fetch rc
      into import_row;
    if rc%found then
    
      --判断手机
      if import_row.mobile_phone <> '' and
         length(trim(import_row.mobile_phone)) = 11 then
        mobile := trim(import_row.mobile_phone);
      else
        mobile := default_mobile;
      end if;
      --判断省份
      if import_row.BIRTH_PLACE = '浙江' then
        area_code := '330000';
      elsif import_row.BIRTH_PLACE = '浙江 温州' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '镇江' then
        area_code := '321100';
      elsif import_row.BIRTH_PLACE = '浙江东阳' then
        area_code := '330783';
      elsif import_row.BIRTH_PLACE = '浙江平阳' then
        area_code := '330326';
      elsif import_row.BIRTH_PLACE = '浙江绍兴' then
        area_code := '330600';
      elsif import_row.BIRTH_PLACE = '江苏镇江' then
        area_code := '321100';
      elsif import_row.BIRTH_PLACE = '江苏镇江' then
        area_code := '370000';
      elsif import_row.BIRTH_PLACE = '杭州' then
        area_code := '330100';
      elsif import_row.BIRTH_PLACE = '常数' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '高邮' then
        area_code := '321084';
      elsif import_row.BIRTH_PLACE = '徐州' then
        area_code := '320300';
      elsif import_row.BIRTH_PLACE = '内蒙古' then
        area_code := '150000';
      elsif import_row.BIRTH_PLACE = '温州' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '浙江湖州' then
        area_code := '330500';
      elsif import_row.BIRTH_PLACE = '黑龙江' then
        area_code := '230000';
      elsif import_row.BIRTH_PLACE = '绍兴' then
        area_code := '330600';
      elsif import_row.BIRTH_PLACE = '浙江义乌' then
        area_code := '330782';
      elsif import_row.BIRTH_PLACE = '广西' then
        area_code := '450000';
      elsif import_row.BIRTH_PLACE = '皖' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '浦江' then
        area_code := '330726';
      elsif import_row.BIRTH_PLACE = '张家港' then
        area_code := '320582';
      elsif import_row.BIRTH_PLACE = '平阳 ' then
        area_code := '330326';
      elsif import_row.BIRTH_PLACE = '天台' then
        area_code := '331023';
      elsif import_row.BIRTH_PLACE = '慈溪' then
        area_code := '330282';
      elsif import_row.BIRTH_PLACE = '丹阳' then
        area_code := '321181';
      elsif import_row.BIRTH_PLACE = '漳州' then
        area_code := '350600';
      elsif import_row.BIRTH_PLACE = '浙江安庆' then
        area_code := '340800';
      elsif import_row.BIRTH_PLACE = '常熟31' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '贵州' then
        area_code := '520000';
      elsif import_row.BIRTH_PLACE = '大连' then
        area_code := '210200';
      elsif import_row.BIRTH_PLACE = '常熟' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '安庆' then
        area_code := '340800';
      elsif import_row.BIRTH_PLACE = '无锡' then
        area_code := '320200';
      elsif import_row.BIRTH_PLACE = '吉林' then
        area_code := '220000';
      elsif import_row.BIRTH_PLACE = '浙江仙居' then
        area_code := '331024';
      elsif import_row.BIRTH_PLACE = '丽水' then
        area_code := '331100';
      elsif import_row.BIRTH_PLACE = '温岭' then
        area_code := '331081';
      elsif import_row.BIRTH_PLACE = '江苏盐城' then
        area_code := '320900';
      elsif import_row.BIRTH_PLACE = '东阳' then
        area_code := '330783';
      elsif import_row.BIRTH_PLACE = '江苏徐州' then
        area_code := '320300';
      elsif import_row.BIRTH_PLACE = '泉州' then
        area_code := '350500';
      elsif import_row.BIRTH_PLACE = '福建' then
        area_code := '350000';
      elsif import_row.BIRTH_PLACE = '安徽' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '浙江温州' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '河南' then
        area_code := '410000';
      elsif import_row.BIRTH_PLACE = '江苏 ' then
        area_code := '320000';
      elsif import_row.BIRTH_PLACE = '盐城' then
        area_code := '320900';
      elsif import_row.BIRTH_PLACE = '安微' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '吴江' then
        area_code := '320584';
      elsif import_row.BIRTH_PLACE = '南通' then
        area_code := '320600';
      elsif import_row.BIRTH_PLACE = '泰州' then
        area_code := '321200';
      elsif import_row.BIRTH_PLACE = '上海' then
        area_code := '310000';
      elsif import_row.BIRTH_PLACE = '浙江瑞安' then
        area_code := '330381';
      elsif import_row.BIRTH_PLACE = '义乌' then
        area_code := '330782';
      elsif import_row.BIRTH_PLACE = '常州' then
        area_code := '320400';
      elsif import_row.BIRTH_PLACE = '江阴' then
        area_code := '320281';
      elsif import_row.BIRTH_PLACE = '湖北' then
        area_code := '420000';
      elsif import_row.BIRTH_PLACE = '江西' then
        area_code := '360000';
      elsif import_row.BIRTH_PLACE = '河北' then
        area_code := '120105';
      elsif import_row.BIRTH_PLACE = '广东' then
        area_code := '440000';
      elsif import_row.BIRTH_PLACE = '泰兴' then
        area_code := '321283';
      elsif import_row.BIRTH_PLACE = '常熟 ' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '湖南' then
        area_code := '430000';
      elsif import_row.BIRTH_PLACE = '瑞安' then
        area_code := '330381';
      else
        area_code := '320000';
      end if;
      
      login_id  := null;
      member_id := null;
      --登陆id
      select lower(dbms_random.string('a', 6)) ||
             substr(dbms_random.normal, 4, 2)
        into login_id
        from dual;
      --用户编号
      select lower(dbms_random.string('a', 20)) into member_id from dual;
      
      --插入会员信息表
      
      insert into member
        (member_id,
         login_password,
         mobile,
         name,
         area_code,
         post_code,
         address,
         status,
         phone_validate,
         login_count,
         MEMBER_TYPE,
         LOGIN_ID,
         PHONE,
         register_ip,
         creator,
         modifier,
         gmt_create,
         gmt_modify,
         gmt_register)
      values
        (member_id,
         default_password,
         mobile,
         substr(trim(nvl(import_row.name, '不祥')),0,20),
         area_code,
         '215500',
         trim(nvl(import_row.note, '不祥')),
         'N',
         'Y',
         0,
         'S',
         login_id,
         import_row.TEL_NUM,
         '127.0.0.1',
         'system',
         'system',
         sysdate,
         sysdate,
         sysdate);
       
      bank_type := '';
      if import_row.BANK_TYPE = '建行' then
        bank_type := '004';
      elsif import_row.BANK_TYPE = '中行' then
        bank_type := '003';
      elsif import_row.BANK_TYPE = '农行' then
        bank_type := '002';
      elsif import_row.BANK_TYPE = '工行' then
        bank_type := '001';
      elsif import_row.BANK_TYPE = '交行' then
        bank_type := '005';
      end if;
      --插入店铺表
      
      insert into shop
        (shop_id,
         member_id,
         login_id,
         shop_name,
         shop_owner,
         belong_market_id,
         address,
         phone,
         bank,
         goods_count,
         COMMODITY,
         creator,
         modifier,
         gmt_create,
         gmt_modify,
         gis_address)
      values
        (member_id,
         member_id,
         login_id,
         nvl(import_row.shop_name, '商朝网卖家商铺'),
         substr(trim(nvl(import_row.name, '不祥')),0,20),
         nvl(import_row.market, '1'),
         nvl(import_row.note, '不祥'),
         to_char(import_row.TEL_NUM),
         bank_type,
         0,
         nvl(import_row.PRODUCT_TYPE_CHN, '不祥'),
         'system',
         'system',
         sysdate,
         sysdate,
         to_char(import_row.id));
      --插入备注
      insert into shop_commend(shop_id,commend) values(member_id,'');
      
         i_count := i_count + 1;
         if i_count = 500 then
            i_count := 0;
            --commit;
            dbms_output.put_line('500条记录提交');
         end if;
       
    else
      dbms_output.put_line('安全退出');
      exit;
    end if;
  end loop;

  close rc;
  dbms_output.put_line('全部执行成功、关闭游标');
  commit;
exception
  when others then
    close rc;
    dbms_output.put_line(SQLERRM || '------' || import_row.id);
    dbms_output.put_line('member_id=' || member_id);
    dbms_output.put_line('default_password=' || default_password);
    dbms_output.put_line('mobile=' || mobile);
    dbms_output.put_line('import_row.name=' || nvl(import_row.name, '不祥'));
    dbms_output.put_line('area_code=' || area_code);
    dbms_output.put_line('import_row.note=' || nvl(import_row.note, '不祥'));
    dbms_output.put_line('login_id=' || login_id);
    dbms_output.put_line('import_row.TEL_NUM=' || import_row.TEL_NUM);
    dbms_output.put_line('======================================================');
    dbms_output.put_line('import_row.shop_name=' || nvl(import_row.shop_name, '商朝网卖家商铺'));
    dbms_output.put_line('import_row.market=' || nvl(import_row.market, '1'));
    dbms_output.put_line('bank_type=' || bank_type);
    dbms_output.put_line('import_row.PRODUCT_TYPE_CHN=' || nvl(import_row.PRODUCT_TYPE_CHN, '不祥'));
    dbms_output.put_line('import_row.id=' || to_char(import_row.id));
    
    rollback;
end sp_import_date;