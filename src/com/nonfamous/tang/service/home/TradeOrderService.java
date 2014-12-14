package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.dao.query.OrderItemQuery;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.domain.trade.TradeOrderLog;
import com.nonfamous.tang.domain.trade.TradeOrderNote;

/**
 * 
 * @author fish
 * 
 */
public interface TradeOrderService {

	/**
	 * 创建交易订单 会自动生成订单号(orderNo)
	 * 
	 * @param order
	 *            不能为null
	 * @param items
	 *            不能为null
	 * @return
	 */
	public TradeOrder createOrder(TradeOrder order, List<TradeOrderItem> items);

	/**
	 * 创建快速交易订单。 会自动生成订单号(orderNo)
	 * 
	 * @param order
	 *            不能为null
	 * @param note
	 *            交易留言,可以为null
	 * @return
	 */
	public TradeOrder createQuickOrder(TradeOrder order, String note);

	/**
	 * 分页查询用户的订单
	 * 
	 * @param query
	 *            不能为null
	 * @return
	 */
	public OrderQuery findOrders(OrderQuery query);
	
	/**
	 * 分页查询团购的订单
	 * @param query
	 * @return
	 */
	public OrderItemQuery findOrderItems(OrderItemQuery query);

	/**
	 * 得到订单
	 * 
	 * @param orderId
	 *            不能为null
	 * @return
	 */
	public TradeOrder getTradeOrder(String orderId);

	/**
	 * 根据订单号查询订单商品
	 * 
	 * @param orderId
	 *            不能为null
	 * @return
	 */
	public List<TradeOrderItem> findOrderItems(String orderId);

	public void confirmOrder(String orderId, String memberId, String addressId);
	
	/**
	 * 根据订单号获得所有的留言记录
	 * 
	 * @param roderId
	 *            不能为null
	 * @return
	 */
	public List<TradeOrderNote> findOrderNotesByOrderId(String orderId);

	/**
	 * 确认订单,如果不能确认,则throw serviceException
	 * 
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetConfirm(String orderId, String memberId);

	/**
	 * 发货,如果不能发货,则throw serviceException
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetShipping(String orderId, String memberId);
	
	/**
	 * 关闭订单,如果不能关闭,则throw serviceException
	 * 
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetClosed(String orderId, String memberId);

	/**
	 * 修改订单状态成为成功状态,如果不能修改,则throw serviceException
	 * 
	 * @param orderId
	 * @param adminUserId
	 */
	public void updateOrderSetSuccess(String orderId, String adminUserId);

	/**
	 * 删除订单中的商品,如果不能删除,则throw serviceException
	 * 
	 * @param orderId
	 * @param itemId
	 * @param memberId
	 */
	public void updateOrderRemoveItem(String orderId, Long itemId,
			String memberId);

	/**
	 * 更新订单中的商品数量，价格，总价
	 * 
	 * @param order
	 * @param items
	 * @param memberId
	 */
	public void updateOrderSetItems(TradeOrder order, String memberId,
			List<TradeOrderItem> items, String log, String note);

	/**
	 * 增加一个订单留言
	 * 
	 * @param order
	 * @param note
	 */
	public void createOrderNote(TradeOrder order,String memberId, String note);

	/**
	 * 根据订单号获得订单修改日志
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo);

	/**
	 * 得到卖家未付款订单数量
	 * 
	 * @param memberId
	 * @return
	 */
	public int getUnpayOrderCount(String memberId);

	/**
	 * 得到未处理订单数量
	 * 
	 * @param memberId
	 * @return
	 */
	public int findUnsettledOrderCount(String memberId);

}
