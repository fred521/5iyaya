package test.com.nonfamous.tang.dao.home;

import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.domain.GoodsCat;

/**
 * @author eyeieye
 * @version $Id: GoodsCatDAOTest.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */
public class GoodsCatDAOTest extends DAOTestBase {
	GoodsCatDAO goodsCatDAO = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		goodsCatDAO = (GoodsCatDAO) this.daoBeanFactory.getBean("goodsCatDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		goodsCatDAO = null;
		super.tearDown();
	}

	public void testGetAll() {
		List<GoodsCat> all = this.goodsCatDAO.getAllGoodsCat();
		assertNotNull(all);
		assertTrue(all.size() > 1);
	}

	public void testGetById() {
		GoodsCat cat = this.goodsCatDAO.getGoodsCatById("100");
		assertNotNull(cat);
		assertEquals("100", cat.getTypeId());
	}

	public void testGetTopCats() {
		List<GoodsCat> tops = this.goodsCatDAO.getRootGoodsCat();
		assertNotNull(tops);
		assertTrue(tops.size() > 1);
	}
	
	public void testGetAllChildren(){
		List<GoodsCat> tops = this.goodsCatDAO.getRootGoodsCat();
		for(GoodsCat cat : tops){
			assertFalse(cat.getAllChildren().isEmpty());
		}
	}
	
	public void testGetPath(){
		List<GoodsCat> cats = this.goodsCatDAO.getAllGoodsCat();
		for(GoodsCat cta : cats){
			System.out.println("cat:"+cta.getTypeName()+" path:"+cta.getPath());
		}
	}
}
