package com.nonfamous.tang.dao.home.ibatis;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.dao.query.OrderItemQuery;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.domain.trade.TradeOrderLog;
import com.nonfamous.tang.domain.trade.TradeOrderNote;

public class IbatisTradeOrderDAO extends SqlMapClientDaoSupport implements
		TradeOrderDAO {

	@SuppressWarnings("unchecked")
	public OrderQuery findOrders(OrderQuery query) {
		if (query == null) {
			throw new NullPointerException("OrderQuery can't be null");
		}
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("TradeOrder.get_order_number_by_query", query);
		query.setTotalItem(total);
		if (total == 0) {
			query.setOrders(Collections.EMPTY_LIST);
			return query;
		}
		List<TradeOrder> find = this.getSqlMapClientTemplate().queryForList(
				"TradeOrder.get_order_by_query", query);
		query.setOrders(find);
		return query;
	}

	private static final String newOrderNoFormat = "yyyyMMdd";

	public boolean insertOrder(TradeOrder order) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		Integer seq = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"TradeOrder.get_next_order_no");
		String newOrderNo = new SimpleDateFormat(newOrderNoFormat)
				.format(new Date());// date format 不是线程安全的,所有只好这样
		order.setOrderDate(newOrderNo);
		order.setOrderNo(newOrderNo + seq);
		this.getSqlMapClientTemplate().insert("TradeOrder.insert_new_order",
				order);
		return true;// 主键是自己生成得,所有insert不会返回主键值,如果没有sql exception,就认为执行正确
	}

	public TradeOrder getTradeOrder(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return (TradeOrder) this.getSqlMapClientTemplate().queryForObject(
				"TradeOrder.get_tradeorder", orderId);
	}

	public boolean updateOrderStatus(TradeOrder order) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		return this.getSqlMapClientTemplate().update(
				"TradeOrder.tradeorder_update_status", order) == 1;
	}

	public boolean confirmOrder(TradeOrder order){
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		return this.getSqlMapClientTemplate().update(
				"TradeOrder.tradeorder_confirm", order) == 1;
	}
	
	@SuppressWarnings("unchecked")
	public List<TradeOrderNote> findNotesByOrderId(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return this.getSqlMapClientTemplate().queryForList(
				"TRADEORDERNOTE_SPACE.get_tradeordernote_list_by_orderid",
				orderId);
	}

	public boolean insertNote(TradeOrderNote note) {
		if (note == null) {
			throw new NullPointerException("TradeOrderNote can't be null");
		}
		return this.getSqlMapClientTemplate().insert(
				"TRADEORDERNOTE_SPACE.tradeordernote_insert", note) != null;
	}

	@SuppressWarnings("unchecked")
	public List<TradeOrderItem> findOrderItems(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return this.getSqlMapClientTemplate().queryForList(
				"TRADEORDERITEM_SPACE.get_tradeorderitem_list_by_orderid",
				orderId);
	}

	public boolean insertOrderItem(TradeOrderItem item) {
		if (item == null) {
			throw new NullPointerException("TradeOrderItem can't be null");
		}
		return this.getSqlMapClientTemplate().insert(
				"TRADEORDERITEM_SPACE.tradeorderitem_insert", item) != null;
	}

	public TradeOrderItem getOrderItem(Long itemId) {
		if (itemId == null) {
			throw new NullPointerException("itemId can't be null");
		}
		return (TradeOrderItem) this.getSqlMapClientTemplate().queryForObject(
				"TRADEORDERITEM_SPACE.get_tradeorderitem", itemId);
	}

	public boolean removeOrderItem(Long itemId) {
		if (itemId == null) {
			throw new NullPointerException("itemId can't be null");
		}
		return this.getSqlMapClientTemplate().update(
				"TRADEORDERITEM_SPACE.delete_tradeorderitem", itemId) == 1;
	}

	public void updateOrderItems(final List<TradeOrderItem> items) {
		if (items == null) {
			throw new NullPointerException("items can't be null");
		}
		if (items.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (TradeOrderItem item : items) {
					executor.update(
							"TRADEORDERITEM_SPACE.tradeorderitem_update", item);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	public boolean insertOrderLog(TradeOrderLog tradeLog) {
		if (tradeLog == null) {
			throw new NullPointerException("tradeLog can't be null");
		}
		return this.getSqlMapClientTemplate().insert(
				"TRADEORDERLOG_SPACE.tradeorderlog_insert", tradeLog) != null;
	}

	@SuppressWarnings("unchecked")
	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo) {
		if (orderNo == null) {
			throw new NullPointerException("trade order no can't be null");
		}
		return this.getSqlMapClientTemplate().queryForList(
				"TRADEORDERLOG_SPACE.get_tradeorderlog_by_orderid", orderNo);
	}

	public boolean updateOrderStatusAndPayFree(TradeOrder order) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		return this.getSqlMapClientTemplate().update(
				"TradeOrder.tradeorder_update_status_and_payfree", order) == 1;
	}

	public int findUnpayOrderCount(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		Integer i = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"TradeOrder.tradeorder_count_unpay_order", memberId);
		return i == null ? 0 : i.intValue();
	}

	public int findUnsettledOrderCount(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		Integer i = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"TradeOrder.tradeorder_count_unsettled_order", memberId);
		return i == null ? 0 : i.intValue();
	}

	public OrderItemQuery findOrderItems(OrderItemQuery query) {
		if (query == null) {
			throw new NullPointerException("OrderItemQuery can't be null");
		}
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("TRADEORDERITEM_SPACE.get_order_item_number_by_query", query);
		query.setTotalItem(total);
		if (total == 0) {
			query.setOrderItems(Collections.EMPTY_LIST);
			return query;
		}
		List<TradeOrderItem> find = this.getSqlMapClientTemplate().queryForList(
				"TRADEORDERITEM_SPACE.get_tradeorderitem_list_by_goodsid", query);
		query.setOrderItems(find);
		return query;
	}
}
