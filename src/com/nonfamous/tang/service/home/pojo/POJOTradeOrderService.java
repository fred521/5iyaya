package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.dao.query.OrderItemQuery;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.domain.trade.TradeOrderLog;
import com.nonfamous.tang.domain.trade.TradeOrderNote;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.TradeOrderService;

/**
 * 
 * @author fish
 * 
 */
public class POJOTradeOrderService extends POJOServiceBase implements
		TradeOrderService {
	private TradeOrderDAO tradeOrderDAO;

	public TradeOrderDAO getTradeOrderDAO() {
		return tradeOrderDAO;
	}

	public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
		this.tradeOrderDAO = tradeOrderDAO;
	}

	public OrderQuery findOrders(OrderQuery query) {
		if (query == null) {
			throw new NullPointerException("OrderQuery can't be null");
		}
		return tradeOrderDAO.findOrders(query);
	}

	public TradeOrder getTradeOrder(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return tradeOrderDAO.getTradeOrder(orderId);
	}

	public List<TradeOrderNote> findOrderNotesByOrderId(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return this.tradeOrderDAO.findNotesByOrderId(orderId);
	}

	public List<TradeOrderItem> findOrderItems(String orderId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		return this.tradeOrderDAO.findOrderItems(orderId);
	}

	public void updateOrderSetClosed(String orderId, String memberId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		if (!order.close(memberId)) {
			throw new ServiceException("用户不能关闭订单");
		}
		this.tradeOrderDAO.updateOrderStatus(order);
	}

	public void updateOrderSetConfirm(String orderId, String memberId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		
		if (!order.confirm(memberId)) {
			throw new ServiceException("用户不能确认订单");
		}
		this.tradeOrderDAO.updateOrderStatus(order);
	}

	public void confirmOrder(String orderId, String memberId, String address) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		if (address == null){
			throw new NullPointerException("address can't be null");
		}
		
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		order.setAddress(address);
		
		if (!order.confirm(memberId)) {
			throw new ServiceException("用户不能确认订单");
		}
		this.tradeOrderDAO.confirmOrder(order);
	}
	
	public void updateOrderSetShipping(String orderId, String memberId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		if (!order.setShipping(memberId)) {
			throw new ServiceException("不能修改订单成为发货中");
		}
		this.tradeOrderDAO.updateOrderStatus(order);
	}
	
	public void updateOrderSetSuccess(String orderId, String adminUserId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (adminUserId == null) {
			throw new NullPointerException("adminUserId can't be null");
		}
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		if (!order.setSuccess(adminUserId)) {
			throw new ServiceException("不能修改订单成为成功状态");
		}
		this.tradeOrderDAO.updateOrderStatus(order);
	}

	public void updateOrderRemoveItem(String orderId, Long itemId,
			String memberId) {
		if (orderId == null) {
			throw new NullPointerException("orderId can't be null");
		}
		if (itemId == null) {
			throw new NullPointerException("itemId can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		TradeOrder order = this.tradeOrderDAO.getTradeOrder(orderId);
		if (!order.canRemoveItem(memberId)) {
			throw new ServiceException("用户不能删除商品");
		}
		TradeOrderItem item = this.tradeOrderDAO.getOrderItem(itemId);
		if (!item.getOrderNo().equals(order.getOrderNo())) {
			return;
		}
		this.tradeOrderDAO.removeOrderItem(itemId);
	}

	public TradeOrder createQuickOrder(TradeOrder order, String note) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		order.setOrderType(TradeOrder.TypeQuick);
		this.tradeOrderDAO.insertOrder(order);
		if (note != null) {
			TradeOrderNote orderNote = new TradeOrderNote(order, order.getCreator(), note);
			this.tradeOrderDAO.insertNote(orderNote);
		}
		return order;
	}

	public void updateOrderSetItems(TradeOrder order, String memberId,
			List<TradeOrderItem> items, String log, String note) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		if (items == null) {
			throw new NullPointerException("TradeOrderItems can't be null");
		}
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		this.tradeOrderDAO.updateOrderStatusAndPayFree(order);
		this.tradeOrderDAO.updateOrderItems(items);
		if (StringUtils.isNotBlank(log)) {
			TradeOrderLog tradeLog = new TradeOrderLog(order, memberId, log);
			this.tradeOrderDAO.insertOrderLog(tradeLog);
		}
		if (StringUtils.isNotBlank(note)) {
			TradeOrderNote orderNote = new TradeOrderNote(order, memberId, note);
			this.tradeOrderDAO.insertNote(orderNote);
		}
	}

	public TradeOrder createOrder(TradeOrder order, List<TradeOrderItem> items) {
		if (order == null || items == null || items.size() == 0) {
			throw new NullPointerException(
					"TradeOrder or TradeOrderItem can't be null");
		}
		this.tradeOrderDAO.insertOrder(order);
		for (TradeOrderItem item : items) {
			item.setOrderNo(order.getOrderNo());
			tradeOrderDAO.insertOrderItem(item);
		}
		return order;
	}

	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo) {
		if (orderNo == null) {
			throw new NullPointerException("orderNo can't be null");
		}
		return this.tradeOrderDAO.findOrderLogByOrderNo(orderNo);
	}

	public int getUnpayOrderCount(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return this.tradeOrderDAO.findUnpayOrderCount(memberId);
	}

	public int findUnsettledOrderCount(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return this.tradeOrderDAO.findUnsettledOrderCount(memberId);
	}

	public void createOrderNote(TradeOrder order, String memberId, String note) {
		if (order == null) {
			throw new NullPointerException("TradeOrder can't be null");
		}
		if (note == null) {
			throw new NullPointerException("note can't be null");
		}
		TradeOrderNote orderNote = new TradeOrderNote(order, memberId, note);
		this.tradeOrderDAO.insertNote(orderNote);
	}

	public OrderItemQuery findOrderItems(OrderItemQuery query) {
		if (query == null) {
			throw new NullPointerException("OrderItemQuery can't be null");
		}
		return tradeOrderDAO.findOrderItems(query);
	}

}
