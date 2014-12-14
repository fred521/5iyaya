package test.com.nonfamous.tang.dao.home;

import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;

public class GoodsDaoTest extends DAOTestBase{
	private GoodsDAO goodsDAO;

	@SuppressWarnings("unused")
	private QuartzLogDAO quartzLogDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		goodsDAO = (GoodsDAO) this.daoBeanFactory
				.getBean("goodsDAO");
		quartzLogDAO = (QuartzLogDAO) this.daoBeanFactory
		.getBean("quartzLogDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		goodsDAO = null;
		super.tearDown();
	}
	
//	public void testAddGoods(){
//		GoodsBaseInfo gbi =new GoodsBaseInfo(); 
//		for(int i = 0;i<20;i++){
//		String goodsId = UUIDGenerator.generate();
//		gbi.setGoodsId(goodsId);
//		gbi.setGoodsStatus("N");
//		gbi.setGoodsName("newproduct" + i);
//		gbi.setGoodsCat("201");
//		gbi.getGoodsContent().setContent("一定要添内容啊，真烦");
//		gbi.setGoodsNum(new Long(5));
//		gbi.setGoodsPic("goods.jpg");
//		gbi.setAbandonDays(new Long(30));
//		gbi.setBatchNum(new Long(30));
//		gbi.setBatchPrice(new Long(30));
//		gbi.setCatPath("200201");
//		gbi.setCreator("f89e47866096449ea5b63939ac0f606c");
//		gbi.setMemberId("f89e47866096449ea5b63939ac0f606c");
//		gbi.setModifier("f89e47866096449ea5b63939ac0f606c");
//		gbi.setPriceDes("nothing");
//		gbi.setShopId("402881e5131b250401131b25054d0001");
//		String id = goodsDAO.addGoods(gbi);
//		assertNotNull(id);
//		System.out.println(id);
//		}
//	}
	
//	public void testUpdateGoods(){
//		GoodsBaseInfo gbi =new GoodsBaseInfo(); 
//		String goodsId = "43d991a39ef740b49ba2e0662b71c95f";
//		gbi.setGoodsId(goodsId);
//		gbi.setAbandonDays(new Long(60));
//		gbi.setPriceDes("改一下价格描述");
//		gbi.getGoodsContent().setContent("content");
//		int i = goodsDAO.updateGoods(gbi);
//		assertEquals(i,1);
//	}
	
//	public void testDeleteGoodsById(){
//		GoodsBaseInfo gbi =new GoodsBaseInfo(); 
//		String goodsId = "43d991a39ef740b49ba2e0662b71c95f";
//		gbi.setGoodsId(goodsId);
//		gbi.setGoodsStatus("D");
//		int i = goodsDAO.deleteGoodsById(goodsId);
//		assertEquals(i,1);
//	}
//	
//	public void testGetGoodsInfoById(){
//		String goodsId = "43d991a39ef740b49ba2e0662b71c95f";
//		GoodsBaseInfo gbi=goodsDAO.getGoodsInfoById(goodsId);
//		assertNotNull(gbi);
//		assertEquals(gbi.getCreator(),"alien");
//	}
	
//	public void testGetActiveGoodsList(){
//		GoodsQuery query = new GoodsQuery();
//		query.setMemberId("200");
//		query.setGoodsStatus("N");
//		
//		List<GoodsBaseInfo> list = goodsDAO.getActiveGoodsList(query);
//		assertNotNull(list);
//		assertEquals(list.size(),1);
//	}
//	public void testGetDownGoodsList(){
//		GoodsQuery query = new GoodsQuery();
//		query.setMemberId("alien");
//		query.setGoodsStatus("D");
//		
//		List<GoodsBaseInfo> list = goodsDAO.getActiveGoodsList(query);
//		assertNotNull(list);
//		assertEquals(list.size(),1);
//	}
	
	public void testGetAllGoodsList(){
		GoodsQuery query = new GoodsQuery();

		query.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		query.setGoodsStatus(GoodsBaseInfo.DOWN_STATUS);
		query = goodsDAO.getSearchList(query);
		assertEquals(query.getGoods().size(),3);
	}
	
//	public void testGetGoodsListByDate(){
//		Calendar cal = Calendar.getInstance();
//		cal.add(cal.DATE,-20);
//		List list = goodsDAO.getGoodsListByDate(cal.getTime());
//		assertNotNull(list);
//		assertEquals(list.size(),3);
//	}
//
	//这个方法慢点测
//	public void testGetShopIdByMemberId(){
//		String member = "alien";
//		String shop = goodsDAO.getShopIdByMemberId(member);
//		assertEquals(shop,3);
//	}
	
//	public void testGetMemberIdByGoodsId(){
//		String goodsId = "43d991a39ef740b49ba2e0662b71c95f";
//		String member = goodsDAO.getCreatorByGoodsId(goodsId);
//		assertNotNull(member);
//		assertEquals(member,"alien");
//	}
//	public void testGetCreatorByGoodsId(){
//		String goodsId = "43d991a39ef740b49ba2e0662b71c95f";
//		String creator = goodsDAO.getCreatorByGoodsId(goodsId);
//		assertNotNull(creator);
//		assertEquals(creator,"alien");
//	}
//	public void testGetGoodsListForIndex(){
//		IndexGoodsQuery query=new IndexGoodsQuery();
//		query.setPageSize(50);
//		query.setBegin(quartzLogDAO.getQuartzLogByType("goods").getGmtExecute());
//		query.setEnd(new Date());
//		List list=goodsDAO.getGoodsListForIndex(query);
//		assertNotNull(list);
//	}
	
//	public void testBatchUpdateViewCount(){
//		Map<String,Integer> map = new HashMap<String,Integer>();
//		map.put("4028928d1344261e0113442629cc0009", 5);
//		map.put("4028928d1344261e011344262ac6000b", 6);
//		map.put("4028928d1344261e011344262b91000d", 9);
//		map.put("4028928d1344261e011344262c3d000f", 23);
//		map.put("4028928d1344261e011344262da40013", 53);
//		map.put("4028928d1344261e011344262f0c0017", 76);
//		map.put("4028928d1344261e011344263073001b", 527);
//		map.put("4028928b1339ff3c011339ff41e90005", 23);
//		map.put("4028928b1339ff3c011339ff43020009", 95);
//		map.put("4028928b1339ff3c011339ff4360000b", 3);
//		map.put("4028928b1339ff3c011339ff43ce000d", 13);
//		map.put("4028928b1339ff3c011339ff44b80011", 43);
//		map.put("4028928b1339ff3c011339ff418b0003", 65);
//		map.put("4028928b1339ff3c011339ff463f0019", 83);
//		map.put("4028928b1339ffb0011339ffb61f0007", 32);
//		this.goodsDAO.updateGoodsViewCount(map);
//	}
	
	public void testFindGoodsBaseInfos(){
		String[] ids = new String[1];
		ids[0] = "297e932914b72ae00114b72ae07b0000";
		List<GoodsBaseInfo> list = goodsDAO.findGoodsBaseInfos(ids);
		
		
	}
}
