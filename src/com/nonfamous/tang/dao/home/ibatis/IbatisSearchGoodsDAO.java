package com.nonfamous.tang.dao.home.ibatis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.OperateFile;
import com.nonfamous.commom.util.PriceUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.dao.home.SearchGoodsDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.query.IndexGoodsQuery;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.QuartzLog;
import com.nonfamous.tang.domain.SearchGoodsCat;
import com.nonfamous.tang.domain.SearchGoodsResult;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.exception.ServiceException;

/**
 * 
 * @author victor
 * 
 */
public class IbatisSearchGoodsDAO extends SqlMapClientDaoSupport implements
		SearchGoodsDAO {
	private final Log logger = LogFactory.getLog(IbatisSearchGoodsDAO.class);

	private Analyzer analyzer = new CJKAnalyzer();

	private String indexFilePath = "";

	private GoodsCatDAO goodsCatDAO;

	private static final int BATCHNUM = 100;

	private static final String QuartzName = "goods";

	// isUpdating 索引状态标志，表示索引是否正在更新中
	private boolean isUpdating = false;

	private static Object writelock = new Object();

	private GoodsDAO goodsDAO;

	private ShopDAO shopDAO;

	private MarketTypeDAO marketTypeDAO;

	private QuartzLogDAO quartzLogDAO;

	public void init() {
        //解除索引文件锁定，删除上次应用异常中断而留下没有删除的write.lock文件
		try {
			Directory directory = FSDirectory.getDirectory(indexFilePath);
			if (IndexReader.isLocked(directory)) {
				IndexReader.unlock(directory);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("解除索引文件锁定失败！" + e.getCause());
			}
		}
		updateGoodsIndex();
	}

	public void createGoodsIndex() {
		File indexFile = new File(getIndexFilePath());
		if (!indexFile.exists()) {
			indexFile.mkdirs();
		}
		if (!indexFile.exists() || !indexFile.canWrite()) {
			if (logger.isDebugEnabled())
				logger.error("索引文件目录创建失败或不可写入！");
			return;
		}
		indexUpdate(indexFile, true, false);
	}

	public void updateGoodsIndex() {
		File indexFile = new File(getIndexFilePath());
		if (!indexFile.exists()) {
			long s = System.currentTimeMillis();
			createGoodsIndex();
			long e = System.currentTimeMillis();
			System.out.println("USE TIME: " + (e - s));
			return;
		}
		if (!indexFile.canWrite()) {
			if (logger.isErrorEnabled())
				logger.error("索引文件目录不可写入！");
			return;
		}
		indexUpdate(indexFile, false, false);
	}

	public void rebuildGoodsIndex() {
		File indexFile = new File(getIndexFilePath());
		if (!indexFile.exists()) {
			indexFile.mkdirs();
		}
		if (!indexFile.exists() || !indexFile.canWrite()) {
			if (logger.isDebugEnabled())
				logger.error("索引文件目录创建失败或不可写入！");
			return;
		}
		indexUpdate(indexFile, true, true);
	}

	private void indexUpdate(File indexFile, boolean isCreate, boolean isRebuild) {
		if (isUpdating) {
			if (logger.isDebugEnabled())
				logger.debug("goodsIndex is in updating state,so this update request will be refused");
			return;
		}
		isUpdating = true;
		Date start = new Date();
		IndexWriter writer = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Indexing to directory '" + indexFile + "'...");
		}
		IndexGoodsQuery query = new IndexGoodsQuery();
		query.setPageSize(BATCHNUM);
		QuartzLog quartzLog = quartzLogDAO.getQuartzLogByType(QuartzName);
		// alan modify 20070702
		if (isRebuild) {
			query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
					.getGmtExecute(), -1000));
		} else {
			// qjy modify 20071127 从timestamp文件中取索引上次更新时间			
			File file = new File(indexFilePath+"/timestamp");				
			if(!file.exists())
			{
			    //如果索引目录不存在，表示是第一次部署				
				query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
					.getGmtExecute(), -1000));
				}else{
					  query.setBegin(OperateFile.getTimestamp(file));
					 }					
			 	//*******
			 logger.debug("上次更新时间为:"+query.getBegin());

			}
		if(query.getBegin()==null)
		{
			logger.error("获取上次索引更新时间失败！");
		}

		Date updateDate=goodsDAO.getSysDate();
		query.setEnd(updateDate);
		query.setCurrentPage(1);
		List<GoodsBaseInfo> list = goodsDAO.getGoodsListForIndex(query);
		modifyIndex(indexFile, isCreate, writer, query, quartzLog, updateDate, list);
		if (logger.isDebugEnabled()) {
			Date end = new Date();
			logger.debug(end.getTime() - start.getTime()
					+ " total milliseconds");
		}
		isUpdating = false;
	}

	private void modifyIndex(File indexFile, boolean isCreate, IndexWriter writer, IndexGoodsQuery query, QuartzLog quartzLog, Date updateDate, List<GoodsBaseInfo> list) {
		//保证同步修改索引
		synchronized (writelock) {
			try {
				IndexReader reader=null;
				try {
					writer = new IndexWriter(indexFile, analyzer, isCreate);
					writer.setMergeFactor(100);
					writer.setMaxBufferedDocs(155);
					writer.setMaxFieldLength(Integer.MAX_VALUE);
					reader=IndexReader.open(indexFile);
				    reader.close();
				    reader=null;
				} catch (IOException e) {
					if (logger.isErrorEnabled()) {
						this.logger.error("商品索引文件出错，索引完全重建", e);
					}
					writer = new IndexWriter(indexFile, analyzer, true);
					writer.setMergeFactor(100);
					writer.setMaxBufferedDocs(155);
					writer.setMaxFieldLength(Integer.MAX_VALUE);
					query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
							.getGmtExecute(), -1000));
					list = goodsDAO.getGoodsListForIndex(query);
				}finally {
					if (reader != null) {
						try {
							reader.close();
							reader=null;
						} catch (IOException e) {
							this.logger.error("error then close IndexReader", e);
						}
					}
				}
				indexGoods(writer, list, isCreate);
				while (query.getCurrentPage() < query.getTotalPage()) {
					query.setCurrentPage(query.getCurrentPage() + 1);
					indexGoods(writer, goodsDAO.getGoodsListForIndex(query),
							isCreate);
				}
				quartzLog.setGmtExecute(updateDate);
				//qjy modify 20071225	更新时间写入文件				
				File file = new File(indexFilePath+"/timestamp");				
				OperateFile.setTimestamp(file, updateDate);
				quartzLogDAO.updateQuartzLog(quartzLog);

				
				if (logger.isDebugEnabled())
					logger.debug("Optimizing...");
				writer.optimize();
				writer.close();
				writer = null;
			} catch (Exception e) {
				isUpdating = false;
				if (logger.isErrorEnabled())
					this.logger.error(" caught a error then index ", e);
			} finally {
				if (writer != null) {
					try {
						writer.close();
						writer=null;
					} catch (IOException e) {
						this.logger.error("error then close IndexModifier", e);
					}
				}
			}
		}
	}

	/**
	 * TODO:此函数可以做优化，批量获取店铺信息
	 * 
	 * @param writer
	 * @param list
	 * @param isCreate
	 * @throws IOException
	 */
	private void indexGoods(IndexWriter writer, List list, boolean isCreate)
			throws IOException {
		if (list == null || list.isEmpty()) {
			logger.debug("库中没有商品！");
			return;
		}
		if (!isCreate) {
			deleteGoods(writer, list);
		}
		Iterator iterator = list.iterator();
		String[] ids = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = ((GoodsBaseInfo) list.get(i)).getShopId();
		}
		List<Shop> shopList = shopDAO.getShopListByIds(ids);
		if (shopList != null) {
			Map<String, Shop> shopMap = new HashMap<String, Shop>();
			for (Shop temp : shopList) {
				shopMap.put(temp.getShopId(), temp);
			}
			while (iterator.hasNext()) {
				GoodsBaseInfo goodsTmp = (GoodsBaseInfo) iterator.next();
				indexGood(writer, goodsTmp, shopMap.get(goodsTmp.getShopId()));
			}
		}
	}

	private void deleteGoods(IndexWriter writer, List list)
			throws IOException {
		if (list == null || list.isEmpty()) {
			if (logger.isDebugEnabled())
				logger.debug("库中没有商品！");
			return;
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext())
			deleteGood(writer, (GoodsBaseInfo) iterator.next());
	}

	private void deleteGood(IndexWriter writer, GoodsBaseInfo info)
			throws IOException {
		writer.deleteDocuments(new Term("goodsId", info.getGoodsId()));
		if (logger.isDebugEnabled())
			logger.debug("deleteGood" + info.getGoodsId());
	}

	private void indexGood(IndexWriter writer, GoodsBaseInfo good, Shop shop)
			throws IOException {
		// 只索引商品状态为N的
		if (good.getGoodsStatus() == null
				|| !good.getGoodsStatus().equalsIgnoreCase("N"))
			return;
		// Shop shop = shopDAO.shopSelectByShopId(good.getShopId());
		//商品推荐星级
		int starNum=0;
		if(good.getPriceDes()!=null){
			starNum=starNum+1;
		}
		if(good.getGoodsPic()!=null){
			starNum=starNum+1;
		}
		if(good.getGoodsContent()!=null){
			starNum=starNum+1;
		}
		if(good.getBatchPrice()!=null&&good.getBatchPrice()>0){
			starNum=starNum+1;
		}
		if(good.getMarketPrice()!=null&&good.getMarketPrice()>0){
			starNum=starNum+1;
		}
		Integer betweenDays=DateUtils.getBetweenDate(good.getGmtCreate(), new Date());
		if(betweenDays<30){
			good.setIsNew(true);
			starNum=starNum+1;
		}else{
			good.setIsNew(false);
		}
		good.setStarNum(starNum);
		if (shop == null) {
			if (logger.isErrorEnabled())
				logger.error("不能得到商品" + good.getGoodsName() + "店铺信息");
			return;
		}
		MarketType marketType = marketTypeDAO.getMarketTypeById(shop
				.getBelongMarketId());
		if (marketType == null) {
			if (logger.isErrorEnabled())
				logger.error("不能得到商品" + good.getGoodsName() + "所属市场信息");
			return;
		}
		good.getPropertiesMap();
		Document doc = getDoc(good, shop, marketType);
		writer.addDocument(doc);
		if (logger.isDebugEnabled())
			logger.debug("indexGood" + good.getGoodsId());
	}
	
	private String getContentWithOutHtml(String source){
		String result = null;
		if(source==null||StringUtils.isBlank(source)){
			return result;
		}
		HtmlCleaner cleaner = new HtmlCleaner();
		try{
			TagNode tag = cleaner.clean(source);
			/*
			PrettyXmlSerializer xmlserializer = new PrettyXmlSerializer(cleaner.getProperties());
			result = xmlserializer.getXmlAsString(tag);
			*/
			result = tag.getText().toString();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
	private Document getDoc(GoodsBaseInfo good, Shop shop, MarketType marketType) {
		Document doc = new Document();
		addField2Doc(doc, shop, "shopName", Store.YES, Index.NO);
		addField2Doc(doc, shop, "address", Store.YES, Index.NO);
		addField2Doc(doc, shop, "gisAddress", Store.YES, Index.NO);
		addField2Doc(doc, marketType, "marketName", Store.YES,
				Index.UN_TOKENIZED);
		addField2Doc(doc, marketType, "marketType", Store.YES,
				Index.UN_TOKENIZED);
		addField2Doc(doc, good, "goodsId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "shopId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "memberId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "goodsCat", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "batchPrice", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "marketPrice", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "batchNum", Store.YES, Index.NO);
		addField2Doc(doc, good, "groupNum", Store.YES, Index.NO);
		addField2Doc(doc, good, "priceDes", Store.YES, Index.NO);
		addField2Doc(doc, good, "goodsNum", Store.YES, Index.NO);
		addField2Doc(doc, good, "goodsPic", Store.YES, Index.NO);
		addField2Doc(doc, good, "starNum", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "isNew", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "goodsStatus", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "goodsType", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "viewCount", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "catPath", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "gmtModify", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "gmtCreate", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, good, "goodsName", Store.YES, Index.TOKENIZED);
		String content = getContentWithOutHtml(good.getGoodsContent().getContent());
		String ageRange = good.getPropertiesMap().get("ageRange");
		String brand = good.getPropertiesMap().get("brand");
		if(ageRange!=null&&StringUtils.isNotBlank(ageRange)){
			doc.add(new Field("ageRange",ageRange,Store.NO,Index.TOKENIZED));
		}
		if(brand!=null&&StringUtils.isNotBlank(brand)){
			doc.add(new Field("brand",brand,Store.NO,Index.TOKENIZED));
		}
		if (content != null) {
			doc.add(new Field("content", content, Store.NO, Index.TOKENIZED));
		}
		return doc;
	}

	private void addField2Doc(Document doc, Object bean, String name, Store s,
			Index i) {
		String value;
		try {
			value = BeanUtils.getProperty(bean, name);
			if (value != null) {
				if (name.equals("goodsStatus"))
					value = value.toUpperCase();
				doc.add(new Field(name, value, s, i));
			}
		} catch (IllegalAccessException e) {
			logger.error("get bean property error", e);
		} catch (InvocationTargetException e) {
			logger.error("get bean property error", e);
		} catch (NoSuchMethodException e) {
			logger.error("get bean property error", e);
		}

	}

	public SearchGoodsQuery findAllGoods(SearchGoodsQuery query) {
		// 组合查询条件
		preQuery(query);
		File indexFile = new File(indexFilePath);
		if (!indexFile.exists())
			throw new ServiceException("商品索引目录不存在");
		try {
			Hits hits;
			// 设置是否排序，搜索，返回结果集
			IndexReader reader = IndexReader.open(indexFilePath);
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			if (!StringUtils.isEmpty(query.getSort())) {
				Sort sort = new Sort(query.getSort(), query.isReverse());
				hits = search(indexSearcher, query.getQueryList(), query
						.getKeyWordsForSearch(), sort);
			} else{
				//default search follow the star number
				Sort sort=new Sort("starNum",true);
				hits = search(indexSearcher, query.getQueryList(), query
						.getKeyWordsForSearch(), sort);
			}
			// 结果集转换，document(lucence 概念) to GoodsDO
			hitsToQuery(hits, query);
			// 统计各类目中命中个数
			setSearchCatNum(indexSearcher, query);
			indexSearcher.close();
			reader.close();
			return query;
		} catch (ParseException e) {
			throw new ServiceException("查询语句解析出错", e);
		} catch (IOException e) {
			throw new ServiceException("找不到商品索引文件", e);
		}
	}
	
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query) {
		// 组合查询条件
		effectUpPreQuery(query);
		File indexFile = new File(indexFilePath);
		if (!indexFile.exists())
			throw new ServiceException("商品索引目录不存在");
		try {
			Hits hits ;
			// 设置是否排序，搜索，返回结果集
			IndexReader reader = IndexReader.open(indexFilePath);
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			if (!StringUtils.isEmpty(query.getSort())) {
				Sort sort = new Sort(query.getSort(), query.isReverse());
				hits = search(indexSearcher, query.getQueryList(), query
						.getKeyWordsForSearch(), sort);
			} else
				hits = search(indexSearcher, query.getQueryList(), query
						.getKeyWordsForSearch(), null);
			

			// 结果集转换，document(lucence 概念) to GoodsDO
			hitsToQuery(hits, query);
			// 统计各类目中命中个数
			setSearchCatNum(indexSearcher, query);
			indexSearcher.close();
			reader.close();
			return query;
		} catch (ParseException e) {
			throw new ServiceException("查询语句解析出错", e);
		} catch (IOException e) {
			throw new ServiceException("找不到商品索引文件", e);
		}
	}

	private Hits search(IndexSearcher indexSearcher, List<Query> qlist,
			String keyWords, Sort sort) throws ParseException, IOException {

		BooleanQuery q = new BooleanQuery();
		if (!StringUtils.isEmpty(keyWords)) {
			QueryParser parser = new MultiFieldQueryParser(new String[] {
					"goodsName", "content","ageRange","brand" }, analyzer);
			Query q1 = parser.parse(keyWords);
			q.add(q1, BooleanClause.Occur.MUST);
		}
		for (int i = 0; i < qlist.size(); i++)
			q.add(qlist.get(i), BooleanClause.Occur.MUST);
		if (sort != null)
			return indexSearcher.search(q, sort);
		return indexSearcher.search(q);
	}

	private void setSearchCatNum(IndexSearcher indexSearcher,
			SearchGoodsQuery query) throws ParseException, IOException {
		List<SearchGoodsCat> list = query.getSearchGoodsCatList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String goodsCatPath;
				goodsCatPath = list.get(i).getPath();
				List<Query> qlist = new ArrayList<Query>();
				for (int j = 0; j < query.getQueryList().size(); j++) {
					qlist.add(query.getQueryList().get(j));
				}
				Query q = new PrefixQuery(new Term("catPath", goodsCatPath));
				qlist.add(q);
				list.get(i).setNum(
						search(indexSearcher, qlist, query.getKeyWords(), null)
								.length());
				if (list.get(i).isEmpty()) {
					list.remove(i);
					i--;
				}
			}
		}
		query.setSearchGoodsCatList(list);
	}

	private void hitsToQuery(Hits hits, SearchGoodsQuery query)
			throws IOException {
		query.setTotalItem(hits.length());
		List<SearchGoodsResult> goodsList = new ArrayList<SearchGoodsResult>();
		for (int i = (query.getPageFristItem() - 1); i < query
				.getPageLastItem(); i++) {
			goodsList.add(docToGoodsBaseInfo(hits.doc(i)));
		}
		query.setGoodsList(goodsList);
	}

	private SearchGoodsResult docToGoodsBaseInfo(Document doc) {
		SearchGoodsResult info = new SearchGoodsResult();
		info.setShopName(doc.get("shopName"));
		info.setAddress(doc.get("address"));
		info.setGisAddress(doc.get("gisAddress"));
		info.setMarketName(doc.get("marketName"));
		info.setMarketType(doc.get("marketType"));
		info.setGoodsName(doc.get("goodsName"));
		info.setGoodsId(doc.get("goodsId"));
		info.setShopId(doc.get("shopId"));
		info.setMemberId(doc.get("memberId"));
		info.setGoodsCat(doc.get("goodsCat"));
		if(StringUtils.isNotBlank(doc.get("brand"))){
			info.getPropertiesMap().put("brand", doc.get("brand"));
		}
		if(StringUtils.isNotBlank(doc.get("ageRange"))){
			info.getPropertiesMap().put("ageRange", doc.get("ageRange"));
		}
		if (StringUtils.isNumeric(doc.get("batchPrice"))) {
			info.setBatchPrice(Long.valueOf(doc.get("batchPrice")));
			info.setBatchPriceYuan(PriceUtils.fenToYuanString(Long.valueOf(doc
					.get("batchPrice"))));
		}
		if (StringUtils.isNumeric(doc.get("marketPrice"))) {
			info.setMarketPrice(Long.valueOf(doc.get("marketPrice")));
			info.setMarketPriceYuan(PriceUtils.fenToYuanString(Long.valueOf(doc
					.get("marketPrice"))));
		}
		if (StringUtils.isNumeric(doc.get("batchNum")))
			info.setBatchNum(Long.valueOf(doc.get("batchNum")));
		if (StringUtils.isNumeric(doc.get("groupNum")))
			info.setGroupNum(Long.valueOf(doc.get("groupNum")));
		info.setPriceDes(doc.get("priceDes"));
		if (StringUtils.isNumeric(doc.get("goodsNum")))
			info.setGoodsNum(Long.valueOf(doc.get("goodsNum")));
		info.setGoodsPic(doc.get("goodsPic"));
		info.setGoodsStatus(doc.get("goodsStatus"));
		info.setStarNum(Integer.valueOf(doc.get("starNum")));
		info.setIsNew(Boolean.valueOf(doc.get("isNew")));
		info.setGmtCreate(DateUtils.string2Date(doc.get("gmtCreate")));
		info.setGmtModify(DateUtils.string2Date(doc.get("gmtModify")));
		info.setGoodsType(doc.get("goodsType"));
		if (StringUtils.isNumeric(doc.get("viewCount")))
			info.setViewCount(Long.valueOf(doc.get("viewCount")));
		return info;
	}

	/**
	 * 组合查询条件，得到查询目录下所有子目录
	 */
	@SuppressWarnings("unchecked")
	private void preQuery(SearchGoodsQuery query) {
		// 1 过滤掉非正常状态的商品
		Query q = new TermQuery(new Term("goodsStatus", "N"));
		query.getQueryList().add(q);
		q=new TermQuery(new Term("goodsType", query.getGoodsType()));
		query.getQueryList().add(q);
		// 2 得到需要统计搜索结果数量的所有类目列表
		// 3 组合查询类目路径 goodsCatPath
		List<SearchGoodsCat> searchCatList = new ArrayList<SearchGoodsCat>();
		// 用户没有指定类目时，返回所有类目;否则返回指定类目下的所有子类目，包括儿子类目,孙子类目等所有的子类目
		if (query.getGoodsCat() == null) {
			List<GoodsCat> catList = goodsCatDAO.getAllGoodsCat();
			for (int i = 0; i < catList.size(); i++) {
				searchCatList.add(new SearchGoodsCat(catList.get(i)));
			}
			query.setSearchGoodsCatList(searchCatList);
		} else {
			String goodsCat = query.getGoodsCat();
			GoodsCat cat = goodsCatDAO.getGoodsCatById(goodsCat);
			String goodsCatPath = cat.getPath();
			List<GoodsCat> catList = cat.getAllChildren();
			for (int i = 0; i < catList.size(); i++) {
				searchCatList.add(new SearchGoodsCat(catList.get(i)));
			}
			q = new PrefixQuery(new Term("catPath", goodsCatPath));
			query.getQueryList().add(q);
			query.setSearchGoodsCatList(searchCatList);
		}
		// 4 如果用户指定所属市场，设置所属市场过滤条件
		if (!StringUtils.isEmpty(query.getMarketType())) {
			q = new TermQuery(new Term("marketType", query.getMarketType()));
			query.getQueryList().add(q);
		}
	}
	
	/**
	 * 组合查询条件，得到查询目录下所有子目录
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	private void effectUpPreQuery(SearchGoodsQuery query)  {
		// 1 过滤掉非正常状态的商品
		Query q = new TermQuery(new Term("goodsStatus", "N"));
		query.getQueryList().add(q);
		q=new TermQuery(new Term("goodsType", query.getGoodsType()));
		query.getQueryList().add(q);
		 
		// 2 得到需要统计搜索结果数量的所有类目列表
		// 3 组合查询类目路径 goodsCatPath
		List<SearchGoodsCat> searchCatList = new ArrayList<SearchGoodsCat>();
		// 用户没有指定类目时，返回所有类目;否则返回指定类目下的所有子类目，包括儿子类目,孙子类目等所有的子类目
		if (query.getGoodsCat() == null) {
			
			List<GoodsCat> catList = goodsCatDAO.getAllGoodsCat();
			for (int i = 0; i < catList.size(); i++) {
				searchCatList.add(new SearchGoodsCat(catList.get(i)));
				
			}
			query.setSearchGoodsCatList(searchCatList);
		} else {
			
			String goodsCat = query.getGoodsCat();
			GoodsCat cat = goodsCatDAO.getGoodsCatById(goodsCat);
			String goodsCatPath = cat.getPath();
			List<GoodsCat> catList = cat.getAllChildren();
			for (int i = 0; i < catList.size(); i++) {
				searchCatList.add(new SearchGoodsCat(catList.get(i)));
			}
			q= new PrefixQuery(new Term("catPath", goodsCatPath));
			 
			query.getQueryList().add(q);
			query.setSearchGoodsCatList(searchCatList);
		}
		// 4 如果用户指定所属市场，设置所属市场过滤条件
		if (!StringUtils.isEmpty(query.getMarketType())) {
			q = new TermQuery(new Term("marketType", query.getMarketType()));
			 query.getQueryList().add(q);
		 
			 
		}
		
	}
	

	public void setGoodsDAO(GoodsDAO goodsDAO) {
		this.goodsDAO = goodsDAO;
	}

	public void setQuartzLogDAO(QuartzLogDAO quartzLogDAO) {
		this.quartzLogDAO = quartzLogDAO;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public String getIndexFilePath() {
		return indexFilePath;
	}

	public void setIndexFilePath(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}

	public void setGoodsCatDAO(GoodsCatDAO goodsCatDAO) {
		this.goodsCatDAO = goodsCatDAO;
	}

}
