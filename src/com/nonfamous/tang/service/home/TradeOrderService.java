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
	 * �������׶��� ���Զ����ɶ�����(orderNo)
	 * 
	 * @param order
	 *            ����Ϊnull
	 * @param items
	 *            ����Ϊnull
	 * @return
	 */
	public TradeOrder createOrder(TradeOrder order, List<TradeOrderItem> items);

	/**
	 * �������ٽ��׶����� ���Զ����ɶ�����(orderNo)
	 * 
	 * @param order
	 *            ����Ϊnull
	 * @param note
	 *            ��������,����Ϊnull
	 * @return
	 */
	public TradeOrder createQuickOrder(TradeOrder order, String note);

	/**
	 * ��ҳ��ѯ�û��Ķ���
	 * 
	 * @param query
	 *            ����Ϊnull
	 * @return
	 */
	public OrderQuery findOrders(OrderQuery query);
	
	/**
	 * ��ҳ��ѯ�Ź��Ķ���
	 * @param query
	 * @return
	 */
	public OrderItemQuery findOrderItems(OrderItemQuery query);

	/**
	 * �õ�����
	 * 
	 * @param orderId
	 *            ����Ϊnull
	 * @return
	 */
	public TradeOrder getTradeOrder(String orderId);

	/**
	 * ���ݶ����Ų�ѯ������Ʒ
	 * 
	 * @param orderId
	 *            ����Ϊnull
	 * @return
	 */
	public List<TradeOrderItem> findOrderItems(String orderId);

	public void confirmOrder(String orderId, String memberId, String addressId);
	
	/**
	 * ���ݶ����Ż�����е����Լ�¼
	 * 
	 * @param roderId
	 *            ����Ϊnull
	 * @return
	 */
	public List<TradeOrderNote> findOrderNotesByOrderId(String orderId);

	/**
	 * ȷ�϶���,�������ȷ��,��throw serviceException
	 * 
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetConfirm(String orderId, String memberId);

	/**
	 * ����,������ܷ���,��throw serviceException
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetShipping(String orderId, String memberId);
	
	/**
	 * �رն���,������ܹر�,��throw serviceException
	 * 
	 * @param orderId
	 * @param memberId
	 */
	public void updateOrderSetClosed(String orderId, String memberId);

	/**
	 * �޸Ķ���״̬��Ϊ�ɹ�״̬,��������޸�,��throw serviceException
	 * 
	 * @param orderId
	 * @param adminUserId
	 */
	public void updateOrderSetSuccess(String orderId, String adminUserId);

	/**
	 * ɾ�������е���Ʒ,�������ɾ��,��throw serviceException
	 * 
	 * @param orderId
	 * @param itemId
	 * @param memberId
	 */
	public void updateOrderRemoveItem(String orderId, Long itemId,
			String memberId);

	/**
	 * ���¶����е���Ʒ�������۸��ܼ�
	 * 
	 * @param order
	 * @param items
	 * @param memberId
	 */
	public void updateOrderSetItems(TradeOrder order, String memberId,
			List<TradeOrderItem> items, String log, String note);

	/**
	 * ����һ����������
	 * 
	 * @param order
	 * @param note
	 */
	public void createOrderNote(TradeOrder order,String memberId, String note);

	/**
	 * ���ݶ����Ż�ö����޸���־
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderLog> findOrderLogByOrderNo(String orderNo);

	/**
	 * �õ�����δ���������
	 * 
	 * @param memberId
	 * @return
	 */
	public int getUnpayOrderCount(String memberId);

	/**
	 * �õ�δ����������
	 * 
	 * @param memberId
	 * @return
	 */
	public int findUnsettledOrderCount(String memberId);

}
