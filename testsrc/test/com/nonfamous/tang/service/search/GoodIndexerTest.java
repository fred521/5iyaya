package test.com.nonfamous.tang.service.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.domain.GoodsContent;
import com.nonfamous.tang.service.search.pojo.POJOGoodsIndexService;

import test.com.nonfamous.tang.service.ServiceTestBase;

/**
 * @author victor
 * 
 */
public class GoodIndexerTest extends ServiceTestBase{
	private POJOGoodsIndexService goodsIndexService;
	private GoodsCatDAO goodsCatDAO;
	private GoodsDAO goodsDAO;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		goodsIndexService = (POJOGoodsIndexService) this.serviceBeanFactory
				.getBean("goodsIndexService");
		goodsCatDAO = (GoodsCatDAO) this.serviceBeanFactory
		.getBean("goodsCatDAO");
		goodsDAO = (GoodsDAO) this.serviceBeanFactory
		.getBean("goodsDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		goodsIndexService = null;
		super.tearDown();
	}
	public void testAdd10000Goods() {
		GoodsBaseInfo good = null;
		List<GoodsCat> list=(List<GoodsCat>)goodsCatDAO.getAllGoodsCat();
		Random r=new Random();
		String words="";
		File wordsFile=new File(GoodIndexerTest.class.getResource("words.txt").getPath());
		FileInputStream fis;
		try {
			fis=new FileInputStream(wordsFile);
			byte[] bytes=new byte[fis.available()];
			fis.read(bytes);
			words=new String(bytes);
		} catch (FileNotFoundException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}
		for (int i = 0; i < 100; i++) {
			good = new GoodsBaseInfo();
			String goodsName = RandomStringUtils.random(r.nextInt(20), words);
			while(StringUtils.isEmpty(goodsName))
				goodsName=RandomStringUtils.random(r.nextInt(20), words);
			String content = RandomStringUtils.random(r.nextInt(1000), words);
			while(StringUtils.isEmpty(content))
				content=RandomStringUtils.random(r.nextInt(1000), words);
			GoodsCat cat = list.get(r.nextInt(list.size()));
			String catPath;
			if (cat.isRoot())
				catPath = cat.getTypeId();
			else
				catPath = cat.getParentId() + cat.getTypeId();
			good.setGoodsName(goodsName);
			good.setMemberId("654321");
			good.setBatchNum(new Long(r.nextInt(500)));
			good.setBatchPrice(new Long(r.nextInt(5000)));
			good.setAbandonDays(new Long(r.nextInt(100)));
			good.setGoodsCat(cat.getTypeId());
			good.setGoodsStatus("n");
			good.setCatPath(catPath);
			good.setShopId("123456");
			GoodsContent goodsContent=new GoodsContent();
			goodsContent.setGoodsId(content);
		    good.setGoodsContent(goodsContent);
			goodsDAO.addGoods(good);
		}
	} 
	public void testCreateGoodIndex(){
		goodsIndexService.createGoodsIndex();
	}
	public void testUpdateGoodIndex(){
		goodsIndexService.updateGoodsIndex();
	}
	public void testFindGoods(){
		SearchGoodsQuery query=new SearchGoodsQuery();
		query.setKeyWords(null);
		query=goodsIndexService.findGoods(query);
		assertTrue(query.getTotalItem()>0);
	}
}
