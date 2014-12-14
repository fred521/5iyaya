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
	 * ����order��¼
	 * 
	 * @param order
	 *            ����Ϊnull
	 * @return
	 */
	public boolean insertOrder(TradeOrder order);

	/**
	 * ��ҳ��ѯ�û��Ķ���
	 * 
	 * @param query
	 *            ����Ϊnull
	 * @return
	 */
	public OrderQuery findOrders(OrderQuery query);

	/**
	 * �õ�����
	 * 
	 * @param orderId
	 *            ����Ϊnull
	 * @return
	 */
	public TradeOrder getTradeOrder(String orderId);

	/**
	 * ���¶���״̬,modifer ,gmt_lastModif
	 * 
	 * @param order
	 *            ����Ϊnull
	 */
	public boolean updateOrderStatus(TradeOrder order);
	
	/**
	 * ȷ�϶���
	 * 
	 * @param order
	 *            ����Ϊnull
	 */
	public boolean confirmOrder(TradeOrder order);
	
	public boolean updateOrderStatusAndPayFree(TradeOrder order);
	
	public int findUnpayOrderCount(String memberId);
	
	public int findUnsettledOrderCount(String memberId);

	/**
	 * -------------------------- ������Ʒ��� --------------------------
	 */

	/**
	 * ����һ��������Ʒ
	 * 
	 * @param item
	 *            ����Ϊnull
	 */
	public boolean insertOrderItem(TradeOrderItem item);

	/**
	 * ���ݶ����Ų�ѯ������Ʒ
	 * 
	 * @param orderId
	 *            ����Ϊnull
	 * @return
	 */
	public List<TradeOrderItem> findOrderItems(String orderId);

	/**
	 * ����id��ö�����Ʒ
	 * 
	 * @param itemId
	 */
	public TradeOrderItem getOrderItem(Long itemId);

	/**
	 * ����ɾ��һ��������Ʒ
	 * 
	 * @param itemId
	 */
	public boolean removeOrderItem(Long itemId);

	/**
	 * �������¶�����Ʒ�ļ۸��������ܼ�
	 * 
	 * @param items
	 */
	public void updateOrderItems(List<TradeOrderItem> items);

	/**
	 * -------------------------- �����޸ļ�¼��� --------------------------
	 */
	/**
	 * ����һ�������޸ļ�¼
	 * 
	 * @param tradeLog
	 * 
	 */
	public boolean insertOrderLog(TradeOrderLog tradeLog);

	/**
	 * ���ݶ����Ż�ö����޸���־
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo);

	/**
	 * -------------------------- ������� --------------------------
	 */

	/**
	 * ��������
	 * 
	 * @param note
	 *            ����Ϊnull
	 * @return
	 */
	public boolean insertNote(TradeOrderNote note);

	/**
	 * ���ݶ����Ż�����е����Լ�¼
	 * 
	 * @param orderId
	 *            ����Ϊnull
	 * @return
	 */
	public List<TradeOrderNote> findNotesByOrderId(String orderId);
	/**
	 * ��ҳ��ѯ�Ź�����
	 * @param query
	 * @return
	 */
	public OrderItemQuery findOrderItems(OrderItemQuery query);

	

}
