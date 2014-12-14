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
  --�����з�����Ҫ�ֶ����ݣ�ʵ��������̫����
  execute immediate 'alter table member modify name varchar2(30)';
  execute immediate 'alter table shop modify shop_owner varchar2(30)';
  
  default_password := 'e22fb54c6fcd2647f09da91d2420f793';
  default_mobile   := '13000000000';
  i_count := 0;
  dbms_output.put_line('���α�');
  open rc for
    select trim(u.NAME), --�û�����
           u.TEL_NUM, --�绰
           nvl(u.MOBILE_PHONE, ''), --�ֻ�
           u.BANK_TYPE, --����
           u.BIRTH_PLACE, --ʡ��
           s.NOTE, --��ַ
           s.name as shop_name, --��������
           decode(s.market,0,1,s.market) as market, --�г�����
           s.id,
           s.PRODUCT_TYPE_CHN --��Ӫ
      from user_info u, shop_info s
     where u.address_num = s.id;
    dbms_output.put_line('ѭ����������');
  loop
    fetch rc
      into import_row;
    if rc%found then
    
      --�ж��ֻ�
      if import_row.mobile_phone <> '' and
         length(trim(import_row.mobile_phone)) = 11 then
        mobile := trim(import_row.mobile_phone);
      else
        mobile := default_mobile;
      end if;
      --�ж�ʡ��
      if import_row.BIRTH_PLACE = '�㽭' then
        area_code := '330000';
      elsif import_row.BIRTH_PLACE = '�㽭 ����' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '��' then
        area_code := '321100';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '330783';
      elsif import_row.BIRTH_PLACE = '�㽭ƽ��' then
        area_code := '330326';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '330600';
      elsif import_row.BIRTH_PLACE = '������' then
        area_code := '321100';
      elsif import_row.BIRTH_PLACE = '������' then
        area_code := '370000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '330100';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '321084';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320300';
      elsif import_row.BIRTH_PLACE = '���ɹ�' then
        area_code := '150000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '330500';
      elsif import_row.BIRTH_PLACE = '������' then
        area_code := '230000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '330600';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '330782';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '450000';
      elsif import_row.BIRTH_PLACE = '��' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '�ֽ�' then
        area_code := '330726';
      elsif import_row.BIRTH_PLACE = '�żҸ�' then
        area_code := '320582';
      elsif import_row.BIRTH_PLACE = 'ƽ�� ' then
        area_code := '330326';
      elsif import_row.BIRTH_PLACE = '��̨' then
        area_code := '331023';
      elsif import_row.BIRTH_PLACE = '��Ϫ' then
        area_code := '330282';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '321181';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '350600';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '340800';
      elsif import_row.BIRTH_PLACE = '����31' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '520000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '210200';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '340800';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320200';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '220000';
      elsif import_row.BIRTH_PLACE = '�㽭�ɾ�' then
        area_code := '331024';
      elsif import_row.BIRTH_PLACE = '��ˮ' then
        area_code := '331100';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '331081';
      elsif import_row.BIRTH_PLACE = '�����γ�' then
        area_code := '320900';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '330783';
      elsif import_row.BIRTH_PLACE = '��������' then
        area_code := '320300';
      elsif import_row.BIRTH_PLACE = 'Ȫ��' then
        area_code := '350500';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '350000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '�㽭����' then
        area_code := '330300';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '410000';
      elsif import_row.BIRTH_PLACE = '���� ' then
        area_code := '320000';
      elsif import_row.BIRTH_PLACE = '�γ�' then
        area_code := '320900';
      elsif import_row.BIRTH_PLACE = '��΢' then
        area_code := '340000';
      elsif import_row.BIRTH_PLACE = '�⽭' then
        area_code := '320584';
      elsif import_row.BIRTH_PLACE = '��ͨ' then
        area_code := '320600';
      elsif import_row.BIRTH_PLACE = '̩��' then
        area_code := '321200';
      elsif import_row.BIRTH_PLACE = '�Ϻ�' then
        area_code := '310000';
      elsif import_row.BIRTH_PLACE = '�㽭��' then
        area_code := '330381';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '330782';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320400';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '320281';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '420000';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '360000';
      elsif import_row.BIRTH_PLACE = '�ӱ�' then
        area_code := '120105';
      elsif import_row.BIRTH_PLACE = '�㶫' then
        area_code := '440000';
      elsif import_row.BIRTH_PLACE = '̩��' then
        area_code := '321283';
      elsif import_row.BIRTH_PLACE = '���� ' then
        area_code := '320581';
      elsif import_row.BIRTH_PLACE = '����' then
        area_code := '430000';
      elsif import_row.BIRTH_PLACE = '��' then
        area_code := '330381';
      else
        area_code := '320000';
      end if;
      
      login_id  := null;
      member_id := null;
      --��½id
      select lower(dbms_random.string('a', 6)) ||
             substr(dbms_random.normal, 4, 2)
        into login_id
        from dual;
      --�û����
      select lower(dbms_random.string('a', 20)) into member_id from dual;
      
      --�����Ա��Ϣ��
      
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
         substr(trim(nvl(import_row.name, '����')),0,20),
         area_code,
         '215500',
         trim(nvl(import_row.note, '����')),
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
      if import_row.BANK_TYPE = '����' then
        bank_type := '004';
      elsif import_row.BANK_TYPE = '����' then
        bank_type := '003';
      elsif import_row.BANK_TYPE = 'ũ��' then
        bank_type := '002';
      elsif import_row.BANK_TYPE = '����' then
        bank_type := '001';
      elsif import_row.BANK_TYPE = '����' then
        bank_type := '005';
      end if;
      --������̱�
      
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
         nvl(import_row.shop_name, '�̳�����������'),
         substr(trim(nvl(import_row.name, '����')),0,20),
         nvl(import_row.market, '1'),
         nvl(import_row.note, '����'),
         to_char(import_row.TEL_NUM),
         bank_type,
         0,
         nvl(import_row.PRODUCT_TYPE_CHN, '����'),
         'system',
         'system',
         sysdate,
         sysdate,
         to_char(import_row.id));
      --���뱸ע
      insert into shop_commend(shop_id,commend) values(member_id,'');
      
         i_count := i_count + 1;
         if i_count = 500 then
            i_count := 0;
            --commit;
            dbms_output.put_line('500����¼�ύ');
         end if;
       
    else
      dbms_output.put_line('��ȫ�˳�');
      exit;
    end if;
  end loop;

  close rc;
  dbms_output.put_line('ȫ��ִ�гɹ����ر��α�');
  commit;
exception
  when others then
    close rc;
    dbms_output.put_line(SQLERRM || '------' || import_row.id);
    dbms_output.put_line('member_id=' || member_id);
    dbms_output.put_line('default_password=' || default_password);
    dbms_output.put_line('mobile=' || mobile);
    dbms_output.put_line('import_row.name=' || nvl(import_row.name, '����'));
    dbms_output.put_line('area_code=' || area_code);
    dbms_output.put_line('import_row.note=' || nvl(import_row.note, '����'));
    dbms_output.put_line('login_id=' || login_id);
    dbms_output.put_line('import_row.TEL_NUM=' || import_row.TEL_NUM);
    dbms_output.put_line('======================================================');
    dbms_output.put_line('import_row.shop_name=' || nvl(import_row.shop_name, '�̳�����������'));
    dbms_output.put_line('import_row.market=' || nvl(import_row.market, '1'));
    dbms_output.put_line('bank_type=' || bank_type);
    dbms_output.put_line('import_row.PRODUCT_TYPE_CHN=' || nvl(import_row.PRODUCT_TYPE_CHN, '����'));
    dbms_output.put_line('import_row.id=' || to_char(import_row.id));
    
    rollback;
end sp_import_date;