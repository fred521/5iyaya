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
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.OperateFile;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.dao.home.SearchNewsDAO;
import com.nonfamous.tang.dao.home.SearchYYNewsDAO;
import com.nonfamous.tang.dao.home.YYNewsDAO;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.QuartzLog;
import com.nonfamous.tang.exception.ServiceException;

/**
 * 
 * @author victor
 * 
 */
public class IbatisSearchYYNewsDAO extends SqlMapClientDaoSupport implements
		SearchYYNewsDAO {
	private Analyzer analyzer = new CJKAnalyzer();

	private String indexFilePath = "";

	private static final int BATCHNUM = 100;

	private static final int NEWSINFOLENGTH = 200;// 资讯搜索结果页面显示内容的最大字数

	private static final String QuartzName = "yynews";

	// isUpdating 索引状态标志，表示索引是否正在更新中
	private boolean isUpdating = false;

	private static Object writelock = new Object();

	private YYNewsDAO yyNewsDAO;

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
		updateNewsIndex();
	}

	public void createNewsIndex() {
		// TODO Auto-generated method stub

	}

	public SearchNewsQuery findAllNews(SearchNewsQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void rebuildNewsIndex() {
		// TODO Auto-generated method stub

	}

	public void updateNewsIndex() {
		File indexFile = new File(indexFilePath);
		if (!indexFile.exists()) {
			createNewsIndex();
			return;
		}
		if (!indexFile.canWrite()) {
			if (logger.isErrorEnabled())
				logger.error("资讯索引文件目录不可写入！");
			return;
		}
		indexUpdate(indexFile, false, false);
	}

	private void indexUpdate(File indexFile, boolean isCreate, boolean isRebuild) {
		if (isUpdating) {
			if (logger.isDebugEnabled())
				logger
						.debug("goodsIndex is in updating state,so this update request will be refused");
			return;
		}
		isUpdating = true;
		Date start = new Date();
		IndexWriter writer = null;
		if (logger.isDebugEnabled())
			logger.debug("Indexing to directory '" + indexFile + "'...");
		SearchNewsQuery query = new SearchNewsQuery();
		query.setPageSize(BATCHNUM);
		QuartzLog quartzLog = quartzLogDAO.getQuartzLogByType(QuartzName);
		// alan modify 20070702
		if (isRebuild) {
			query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
					.getGmtExecute(), -1000));
		} else {
			// qjy modify 20071225 从timestamp文件中取索引上次更新时间
			File file = new File(indexFilePath + "/timestamp");
			if (!file.exists()) {
				// 如果索引目录不存在，表示是第一次部署
				query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
						.getGmtExecute(), -1000));
			} else {
				query.setBegin(OperateFile.getTimestamp(file));
			}
		}
		if (query.getBegin() == null) {
			logger.error("获取上次索引更新时间失败！");
		}

		Date updateDate = yyNewsDAO.getSysDate();
		query.setEnd(updateDate);
		query.setCurrentPage(1);
		List<NewsBaseInfo> list = yyNewsDAO.getYYNewsListForIndex(query);
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
			IndexWriter writer, SearchNewsQuery query, QuartzLog quartzLog,
			Date updateDate, List<NewsBaseInfo> list) {
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
						this.logger.error("新闻索引文件出错，索引完全重建", e);
					}
					writer = new IndexWriter(indexFile, analyzer, true);
					writer.setMergeFactor(100);
					writer.setMaxBufferedDocs(155);
					writer.setMaxFieldLength(Integer.MAX_VALUE);
					query.setBegin(DateUtils.getDiffDateFromEnterDate(quartzLog
							.getGmtExecute(), -1000));
					list = yyNewsDAO.getYYNewsListForIndex(query);
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
				indexNews(writer, list, isCreate);
				while (query.getCurrentPage() < query.getTotalPage()) {
					query.setCurrentPage(query.getCurrentPage() + 1);
					indexNews(writer, yyNewsDAO.getYYNewsListForIndex(query),
							isCreate);
				}
				quartzLog.setGmtExecute(updateDate);
				// qjy modify 20071225 更新时间写入文件
				File file = new File(indexFilePath + "/timestamp");
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
						writer = null;
					} catch (IOException e) {
						this.logger.error("error then close IndexModifier", e);
					}
				}
			}
		}
	}

	private void indexNews(IndexWriter writer, List<NewsBaseInfo> list,
			boolean isCreate) throws IOException {
		if (list == null || list.isEmpty()) {
			if (logger.isDebugEnabled())
				logger.debug("库中没有资讯！");
			return;
		}
		if (!isCreate) {
			deleteNews(writer, list);
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			indexNews(writer, (NewsBaseInfo) iterator.next());
		}

	}

	private void deleteNews(IndexWriter writer, List<NewsBaseInfo> list)
			throws IOException {
		if (list == null || list.isEmpty()) {
			if (logger.isDebugEnabled())
				logger.debug("库中没有资讯！");
			return;
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext())
			deleteNews(writer, (NewsBaseInfo) iterator.next());
	}

	private void deleteNews(IndexWriter writer, NewsBaseInfo info)
			throws IOException {
		writer.deleteDocuments(new Term("newsId", info.getNewsId()));
		if (logger.isDebugEnabled())
			logger.debug("delete news" + info.getNewsId());
	}

	private void indexNews(IndexWriter writer, NewsBaseInfo news)
			throws IOException {
		// 咨讯状态不为N的不索引
		if (!news.getNewsStatus().equals("N"))
			return;
		Document doc = getDoc(news);
		if (doc == null)
			return;
		writer.addDocument(doc);
		if (logger.isDebugEnabled())
			logger.debug("index news " + news.getNewsId());
	}
	private Document getDoc(NewsBaseInfo news) {
		Document doc = new Document();
		// use sql map get content with NewsBaseInfo
		// NewsContent content =
		// newsContentDAO.getNewsContentById(news.getNewsId());
		/*
		 * if (content == null) { if (logger.isDebugEnabled())
		 * logger.debug("不能得到资讯： " + news.getNewsId() + " 的内容"); return null; }
		 */
		addField2Doc(doc, news, "newsId", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, news, "newsTitle", Store.YES, Index.TOKENIZED);
		// addField2Doc(doc, news, "gmtModify", Store.YES, Index.UN_TOKENIZED);
		if (news.getGmtModify() != null) {
			doc.add(new Field("gmtModify", Long.toString(news.getGmtModify()
					.getTime()), Store.YES, Index.UN_TOKENIZED));
		}
		addField2Doc(doc, news, "viewCount", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, news, "newsType", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, news, "newsStatus", Store.YES, Index.UN_TOKENIZED);
		addField2Doc(doc, news, "memberId", Store.YES, Index.NO);
		String strContent = news.getContent().getContentWithoutHtml();
		if (strContent != null) {
			doc
					.add(new Field("content", strContent, Store.NO,
							Index.TOKENIZED));
			String lessContent;// 用于显示在列表页面的部分内容
			if (strContent != null && strContent.length() > NEWSINFOLENGTH)
				lessContent = strContent.substring(0, NEWSINFOLENGTH);
			else
				lessContent = strContent;
			doc.add(new Field("lessContent", lessContent, Store.YES,
					Index.UN_TOKENIZED));
		}
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

	private Hits search(IndexSearcher indexSearcher, SearchNewsQuery query)
			throws ParseException, IOException {

		BooleanQuery q = new BooleanQuery();
		Query q1 = null;
		q1 = new TermQuery(new Term("newsStatus", "N"));
		q.add(q1, BooleanClause.Occur.MUST);
		if (!StringUtils.isEmpty(query.getKeyWords())) {
			QueryParser parser = new MultiFieldQueryParser(new String[] {
					"newsTitle", "content" }, analyzer);
			q1 = parser.parse(query.getKeyWordsForSearch());
			q.add(q1, BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isEmpty(query.getNewsType())) {
			q1 = new TermQuery(new Term("newsType", query.getNewsType()));
			q.add(q1, BooleanClause.Occur.MUST);
			// q.add(new MatchAllDocsQuery(),BooleanClause.Occur.MUST);
		}
		if (!StringUtils.isEmpty(query.getSort()))
			return indexSearcher.search(q, new Sort(query.getSort(), query
					.isReverse()));
		return indexSearcher.search(q);
	}

	private void hitsToQuery(Hits hits, SearchNewsQuery query)
			throws IOException {
		query.setTotalItem(hits.length());
		List<NewsBaseInfo> newsList = new ArrayList<NewsBaseInfo>();
		for (int i = (query.getPageFristItem() - 1); i < query
				.getPageLastItem(); i++) {
			newsList.add(docToGoodsBaseInfo(hits.doc(i)));
		}
		query.setSearchNewsList(newsList);
	}

	private NewsBaseInfo docToGoodsBaseInfo(Document doc) {
		NewsBaseInfo info = new NewsBaseInfo();
		info.setNewsId(doc.get("newsId"));
		info.setNewsTitle(doc.get("newsTitle"));
		info.setNewsType(doc.get("newsType"));
		info.setNewsStatus(doc.get("newsStatus"));
		if (StringUtils.isNumeric(doc.get("viewCount")))
			info.setViewCount(Long.parseLong(doc.get("viewCount")));
		if (StringUtils.isNotEmpty(doc.get("gmtModify")))
			info.setGmtModify(new Date(Long.parseLong((doc.get("gmtModify")))));
		// alan modify 20070703 NewsContent instance has exist in NewsBaseInfo
		// NewsContent content = new NewsContent();
		info.getContent().setContent(doc.get("lessContent"));
		// info.setContent(content);
		return info;
	}
	public void setIndexFilePath(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}

	public void setQuartzLogDAO(QuartzLogDAO quartzLogDAO) {
		this.quartzLogDAO = quartzLogDAO;
	}
	public YYNewsDAO getYyNewsDAO() {
		return yyNewsDAO;
	}

	public void setYyNewsDAO(YYNewsDAO yyNewsDAO) {
		this.yyNewsDAO = yyNewsDAO;
	}


}