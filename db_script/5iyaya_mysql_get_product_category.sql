============5iyya first cat===============================
select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where levels=1


#############################################CHILDREN#########################################################################################

============5iyya second cat children===============================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where parent_id=1 and levels=2

============5iyya third cat children===========================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where parent_id in (select type_id from goods_cat where parent_id=1 and levels=2) and levels=3

============5iyya forth cat children=====================================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where levels =4 and parent_id in (select type_id from goods_cat where parent_id in (select type_id from goods_cat where parent_id=1 and levels=2) and levels=3)




#####################################PREGNANT WOMEN################################################################

============5iyya second cat pregnant women===============================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where parent_id=501 and levels=2

============5iyya third cat pregnant women===========================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where parent_id in (select type_id from goods_cat where parent_id=501 and levels=2) and levels=3

============5iyya forth cat children=====================================================

select type_id as "��Ŀid", type_name as "��Ŀ����", levels as "��Ŀ����", parent_id as "�ϼ���ĿId" from goods_cat where levels =4 and parent_id in (select type_id from goods_cat where parent_id in (select type_id from goods_cat where parent_id=501 and levels=2) and levels=3)