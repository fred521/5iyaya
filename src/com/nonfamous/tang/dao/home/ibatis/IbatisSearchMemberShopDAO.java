package com.nonfamous.tang.dao.home.ibatis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
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
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.OperateFile;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.dao.home.SearchMemberShopDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.domain.QuartzLog;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.exception.ServiceException;

/**
 * 
 * @author victor
 * 
 */
public class IbatisSearchMemberShopDAO extends SqlMapClientDaoSupport implements
		SearchMemberShopDAO {

	private Analyzer analyzer = new CJKAnalyzer();

	private String indexFilePath = "";

	private static final int BATCHNUM = 100;

	private static final String QuartzName = "shop";

	// isUpdating 索引状态标志，表示索引是否正在更新中
	private boolean isUpdating = false;

	private static Object writelock = new Object();

	private ShopDAO shopDAO;
	
	private MarketTypeDAO marketTypeDAO;

	private QuartzLogDAO quartzLogDAO;

	public void init() {
		// 解除索引文件锁定，删除上次应用异常中断而留下没有删除的write.lock文件
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
		updateMemberShopIndex();
	}

	public void createMemberShopIndex() {
		File indexFile = new File(getIndexFilePath());
		if (!indexFile.exists()) {
			indexFile.mkdirs();
		}
		if (!indexFile.exists() || !indexFile.canWrite()) {
			if (logger.isErrorEnabled())
				logger.error("店铺索引文件目录创建失败或不可写入！");
			return;
		}
		indexUpdate(indexFile, true, false);
	}

	public void updateMemberShopIndex() {
		File indexFile = new File(indexFilePath);
		if (!indexFile.exists()) {
			createMemberShopIndex();
			return;
		}
		if (!indexFile.canWrite()) {
			if (logger.isErrorEnabled())
				logger.error("店铺索引文件目录不可写入！");
			return;
		}
		indexUpdate(indexFile, false, false);
	}

	public void rebuildMemberShopIndex() {
		File indexFile = new File(getIndexFilePath());
		if (!indexFile.exists()) {
			createMemberShopIndex();
			return;
		}
		if (!indexFile.canWrite()) {
			if (logger.isErrorEnabled())
				logger.error("店铺索引文件目录不可写入！");
			return;
		}
		indexUpdate(indexFile, true, true);
	}

	@SuppressWarnings("unchecked")
	private void indexUpdate(File indexFile, boolean isCreate, boolean isRebuild) {
		if (isUpdating) {
			if (logger.isDebugEnabled())
				logger.debug("goodsIndex is in updating state,so this update request will be refused");
			return;
		}
		isUpdating = true;
		Date start = new Date();
		IndexWriter writer = null;
		if (logger.isDebugEnabled())
			logger.debug("Indexing to directory '" + indexFile + "'...");
		SearchMemberShopQuery query = new SearchMemberShopQuery();
		query.setPageSize(BATCHNUM);
		QuartzLog quartzLog = quartzLogDAO.getQuartzLogByType(QuartzName);
		query.setCurrentPage(1);
		Date updateDate = shopDAO.getSysDate();
		// alan modify 20070702
		if (isRebuild) {
			query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
					.getGmtExecute(), -1000));
		} else {
		
			// qjy modify 20071225 从timestamp文件中取索引上次更新时间			
			File file = new File(indexFilePath+"/timestamp");				
			if(!file.exists())
			{
				//如果索引目录不存在，表示是第一次部署				
				query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
					.getGmtExecute(), -1000));
			     }else{
					 query.setBegin(OperateFile.getTimestamp(file));
			     }		
		}
		if(query.getBegin()==null)
		{
		   logger.error("获取上次索引更新时间失败！");
		}

		query.setEnd(updateDate);
		List<Shop> list = shopDAO.getShopListForIndex(query);
		modifyIndex(indexFile, isCreate, writer, query, quartzLog, updateDate,
				list);
		if (logger.isDebugEnabled()) {
			Date end = new Date();
			logger.debug(end.getTime() - start.getTime()
					+ " total milliseconds");
		}

		isUpdating = false;
	}

	private void modifyIndex(File indexFile, boolean isCreate,
			IndexWriter writer, SearchMemberShopQuery query,
			QuartzLog quartzLog, Date updateDate, List<Shop> list) {
		// 保证同步修改索引
		synchronized (writelock) {
			try {
				IndexReader reader = null;
				try {
					writer = new IndexWriter(indexFile, analyzer, isCreate);
					writer.setMergeFactor(100);
					writer.setMaxBufferedDocs(155);
					writer.setMaxFieldLength(Integer.MAX_VALUE);
					
					reader = IndexReader.open(indexFile);
					reader.close();
					reader = null;
				} catch (IOException e) {
					if (logger.isErrorEnabled()) {
						this.logger.error("店铺索引文件出错，索引完全重建", e);
					}
					writer = new IndexWriter(indexFile, analyzer, true);
					writer.setMergeFactor(100);
					writer.setMaxBufferedDocs(155);
					writer.setMaxFieldLength(Integer.MAX_VALUE);
					
					query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
							.getGmtExecute(), -1000));
					list = shopDAO.getShopListForIndex(query);
				} finally {
					if (reader != null) {
						try {
							reader.close();
							reader = null;
						} catch (IOException e) {
							this.logger
									.error("error then close IndexReader", e);
						}
					}
				}
				indexShop(writer, list, isCreate);
				while (query.getCurrentPage() < query.getTotalPage()) {
					query.setCurrentPage(query.getCurrentPage() + 1);
					indexShop(writer, shopDAO.getShopListForIndex(query),
							isCreate);
				}
				quartzLog.setGmtExecute(updateDate);
				//qjy modify 20071127	更新时间写入文件				
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
					logger.error(" caught a " + e.getClass()
							+ "\n with message: " + e.getMessage());
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

	private void indexShop(IndexWriter writer, List<Shop> list,
			boolean isCreate) throws IOException {
		if (list == null || list.isEmpty()) {
			if (logger.isDebugEnabled())
				logger.debug("库中没有店铺！");
			return;
		}
		if (!isCreate) {
			deleteShop(writer, list);
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			indexShop(writer, (Shop) iterator.next());
		}
	}

	private void deleteShop(IndexWriter writer, List<Shop> list)
			throws IOException {
		if (list == null || list.isEmpty()) {
			if (logger.isDebugEnabled())
				logger.debug("库中没有店铺！");
			return;
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext())
			deleteShop(writer, (Shop) iterator.next());
	}

	private void deleteShop(IndexWriter writer, Shop info) throws IOException {
		writer.deleteDocuments(new Term("shopId", info.getShopId()));
		if (logger.isDebugEnabled())
			logger.debug("delete shop" + info.getShopId());
	}

	private void indexShop(IndexWriter writer, Shop shop) throws IOException {
		int starNum = calcStarNum(shop);
		shop.setStarNum(starNum);
		Document doc = getDoc(shop);
		writer.addDocument(doc);
		if (logger.isDebugEnabled())
			logger.debug("index shop " + shop.getShopId());
	}
	/*
	 * calculate the shop star num for default sort
	 */
	private int calcStarNum(Shop shop){
		int starNum = 0;
		if(shop.getGoodsCount()>0){
			starNum += 1;
		}
		if(StringUtils.isNotBlank(shop.getPhone())){
			starNum += 1;
		}
		if(StringUtils.isNotBlank(shop.getLogo())){
			starNum +=1;
		}
		if(StringUtils.isNotBlank(shop.getShopImg())){
			starNum +=1;
		}
		return starNum;
	}
	
	private Document getDoc(Shop shop) {
		Document doc = new Document();
		addField2Doc(doc, shop, "starNum", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "shopId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "memberId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "shopName", Store.YES, Index.TOKENIZED);
		addField2Doc(doc, shop, "address", Store.YES, Index.TOKENIZED);
		addField2Doc(doc, shop, "belongMarketId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "belongMarketName", Store.YES, Index.TOKENIZED);
		addField2Doc(doc, shop, "goodsCount", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "shopOwner", Store.YES, Index.TOKENIZED);
		if (shop.getGmtCreate() != null) {
			doc.add(new Field("gmtCreate", Long.toString(shop.getGmtCreate()
					.getTime()), Store.YES, Index.UN_TOKENIZED));
		}
		addField2Doc(doc, shop, "logo", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "camera", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, shop, "webPhone", Store.YES, Index.UN_TOKENIZED);
		return doc;
	}

	private void addField2Doc(Document doc, Object bean, String name, Store s,
			Index i) {
		String value;
		try {
			value = BeanUtils.getProperty(bean, name);
			if (value != null) {
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

	public SearchMemberShopQuery findAllMemberShop(SearchMemberShopQuery query) {
		File indexFile = new File(indexFilePath);
		if (!indexFile.exists()) {
			throw new ServiceException("店铺索引目录不存在");
		}
		try {
			IndexReader reader = IndexReader.open(indexFile);
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			Hits hits = search(indexSearcher, query);
			hitsToQuery(hits, query);
			indexSearcher.close();
			reader.close();
			return query;
		} catch (ParseException e) {
			throw new ServiceException("查询语句解析出错",e);
		} catch (IOException e) {
			throw new ServiceException("找不到店铺索引文件",e);
		}
	}

	private Hits search(IndexSearcher indexSearcher, SearchMemberShopQuery query)
			throws ParseException, IOException {
		
		BooleanQuery q = new BooleanQuery();
		Query q1 = null;
		if (!StringUtils.isEmpty(query.getKeyWords())) {
			QueryParser parser = new QueryParser(query.getSearchType(),
					analyzer);
			q1 = parser.parse(query.getKeyWordsForSearch());
			q.add(q1, BooleanClause.Occur.MUST);
		} else{
			BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
			q.add(new PrefixQuery(new Term(query.getSearchType(), "")),BooleanClause.Occur.MUST);
			//q.add(new MatchAllDocsQuery(),BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isEmpty(query.getMarketType())) {
			q1 = new TermQuery(
					new Term("belongMarketId", query.getMarketType()));
			q.add(q1, BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isEmpty(query.getSort())) {
			return indexSearcher.search(q, new Sort(query.getSort(), query
					.isReverse()));
		}
		Sort sort=new Sort("starNum",true);
		return indexSearcher.search(q, sort);
	}

	private void hitsToQuery(Hits hits, SearchMemberShopQuery query)
			throws IOException {
		query.setTotalItem(hits.length());
		List<Shop> shopList = new ArrayList<Shop>();
		// alan modify 20070702 i = query.getPageFristItem()- 1
		for (int i = query.getPageFristItem() - 1; i < query.getPageLastItem(); i++) {
			shopList.add(docToShop(hits.doc(i)));
		}
		query.setShopList(shopList);
	}

	@SuppressWarnings("deprecation")
	private Shop docToShop(Document doc) {
		Shop shop = new Shop();
		shop.setShopId(doc.get("shopId"));
		shop.setMemberId(doc.get("memberId"));
		shop.setShopName(doc.get("shopName"));
		shop.setAddress(doc.get("address"));
		shop.setBelongMarketId(doc.get("belongMarketId"));
		shop.setBelongMarketName(doc.get("belongMarketName"));
		if(StringUtils.isNumeric(doc.get("starNum"))){
			shop.setStarNum(Integer.parseInt(doc.get("starNum")));
		}
		if (StringUtils.isNumeric(doc.get("goodsCount"))) {
			shop.setGoodsCount(Long.parseLong(doc.get("goodsCount")));
		}
		shop.setShopOwner(doc.get("shopOwner"));
		if (StringUtils.isNotEmpty(doc.get("gmtCreate"))) {
			shop.setGmtCreate(new Date(Long.parseLong(doc.get("gmtCreate"))));
		}
		shop.setLogo(doc.get("logo"));
		shop.setCamera(doc.get("camera"));
		shop.setWebPhone(doc.get("webPhone"));
		return shop;
	}

	public void setIndexFilePath(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}

	public void setQuartzLogDAO(QuartzLogDAO quartzLogDAO) {
		this.quartzLogDAO = quartzLogDAO;
	}

	public String getIndexFilePath() {
		return indexFilePath;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public MarketTypeDAO getMarketTypeDAO() {
		return marketTypeDAO;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

}
