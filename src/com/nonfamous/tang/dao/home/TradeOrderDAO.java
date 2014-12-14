package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.dao.query.OrderItemQuery;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.domain.trade.TradeOrderLog;
import com.nonfamous.tang.domain.trade.TradeOrderNote;

public interface TradeOrderDAO {

	/**
	 * 新增order记录
	 * 
	 * @param order
	 *            不能为null
	 * @return
	 */
	public boolean insertOrder(TradeOrder order);

	/**
	 * 分页查询用户的订单
	 * 
	 * @param query
	 *            不能为null
	 * @return
	 */
	public OrderQuery findOrders(OrderQuery query);

	/**
	 * 得到订单
	 * 
	 * @param orderId
	 *            不能为null
	 * @return
	 */
	public TradeOrder getTradeOrder(String orderId);

	/**
	 * 更新订单状态,modifer ,gmt_lastModif
	 * 
	 * @param order
	 *            不能为null
	 */
	public boolean updateOrderStatus(TradeOrder order);
	
	/**
	 * 确认订单
	 * 
	 * @param order
	 *            不能为null
	 */
	public boolean confirmOrder(TradeOrder order);
	
	public boolean updateOrderStatusAndPayFree(TradeOrder order);
	
	public int findUnpayOrderCount(String memberId);
	
	public int findUnsettledOrderCount(String memberId);

	/**
	 * -------------------------- 订单商品相关 --------------------------
	 */

	/**
	 * 增加一个订单商品
	 * 
	 * @param item
	 *            不能为null
	 */
	public boolean insertOrderItem(TradeOrderItem item);

	/**
	 * 根据订单号查询订单商品
	 * 
	 * @param orderId
	 *            不能为null
	 * @return
	 */
	public List<TradeOrderItem> findOrderItems(String orderId);

	/**
	 * 根据id获得订单商品
	 * 
	 * @param itemId
	 */
	public TradeOrderItem getOrderItem(Long itemId);

	/**
	 * 物理删除一个订单商品
	 * 
	 * @param itemId
	 */
	public boolean removeOrderItem(Long itemId);

	/**
	 * 批量更新订单商品的价格，数量，总价
	 * 
	 * @param items
	 */
	public void updateOrderItems(List<TradeOrderItem> items);

	/**
	 * -------------------------- 订单修改记录相关 --------------------------
	 */
	/**
	 * 创建一个订单修改记录
	 * 
	 * @param tradeLog
	 * 
	 */
	public boolean insertOrderLog(TradeOrderLog tradeLog);

	/**
	 * 根据订单号获得订单修改日志
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo);

	/**
	 * -------------------------- 留言相关 --------------------------
	 */

	/**
	 * 新增留言
	 * 
	 * @param note
	 *            不能为null
	 * @return
	 */
	public boolean insertNote(TradeOrderNote note);

	/**
	 * 根据订单号获得所有的留言记录
	 * 
	 * @param orderId
	 *            不能为null
	 * @return
	 */
	public List<TradeOrderNote> findNotesByOrderId(String orderId);
	/**
	 * 分页查询团购订单
	 * @param query
	 * @return
	 */
	public OrderItemQuery findOrderItems(OrderItemQuery query);

	

}
