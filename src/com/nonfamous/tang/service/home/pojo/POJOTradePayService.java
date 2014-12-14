package com.nonfamous.tang.service.home.pojo;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.home.TradePayDAO;
import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradePay;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.home.TradePayService;

/**
 * <p>
 * ����֧�������ӿ�ʵ����
 * </p>
 * 
 * @author:daodao
 * @version $Id: POJOTradePayService.java,v 1.1 2008/07/11 00:46:45 fred Exp $
 */
public class POJOTradePayService extends POJOServiceBase implements
		TradePayService {
	private TradeOrderService tradeOrderService;

	private ShopDAO shopDAO;

	private MemberDAO memberDAO;

	private TradePayDAO tradePayDAO;

	private TransactionTemplate transactionTemplate;
	public PayResult toolsPay(final String tradeOrderNo, final String memberId) {
		if (tradeOrderNo == null || memberId == null) {
			throw new NullPointerException(
					"tradeOrderNo or memberId can't be null");
		}
		PayResult result = (PayResult) transactionTemplate
				.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						PayResult result = new PayResult();
						result.setSuccess(false);
						// 1����ȡ���׶�����Ϣ
						TradeOrder order = tradeOrderService
								.getTradeOrder(tradeOrderNo);
						// �������У��
						if (!checkOrder(order, memberId, result)) {
							return result;
						}
						// 3���жϸö����Ƿ��Ѿ�֧����(֧�����ҳɹ�)
						TradePay tradePay = tradePayDAO
								.findTradePayByOrderNo(tradeOrderNo);
						if (tradePay != null) {
							if (TradePay.PAY_STATUS_SUCCESS.equals(tradePay
									.getPayStatus())) {
								result
										.setErrorCode(PayResult.ERROR_PAY_HAS_CREATED);
								return result;
							}
							// �Ѿ�֧��������δ֧���ɹ�����Ҫ�޸�֧��ʱ��
							tradePayDAO.changePayDate(tradePay.getId(),
									memberId);
							result.setSuccess(true);
							result.setTradePay(tradePay);
							return result;
						}
						// 4�����ݶ�����Ϣ��õ�����Ϣ
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("�޷���ȡ���ҵ������ݣ����ܽ���֧��");
							return result;
						}
						/*if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}*/
						//5. ���¶���״̬
						tradeOrderService.updateOrderSetShipping(tradeOrderNo, memberId);
						
						// 6�������µ�֧��������֧��״̬ΪI:��ʼ,ת��״̬ΪI��δת�ˣ�
						tradePay = payByOrder(order, shop);
						result.setSuccess(true);
						result.setTradePay(tradePay);
						return result;
					}
				});
		return result;
	}

	public PayResult orderPay(final String tradeOrderNo, final String memberId) {
		if (tradeOrderNo == null || memberId == null) {
			throw new NullPointerException(
					"tradeOrderNo or memberId can't be null");
		}
		PayResult result = (PayResult) transactionTemplate
				.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						PayResult result = new PayResult();
						result.setSuccess(false);
						// 1����ȡ���׶�����Ϣ
						TradeOrder order = tradeOrderService
								.getTradeOrder(tradeOrderNo);
						// �������У��
						if (!checkOrder(order, memberId, result)) {
							return result;
						}
						// 3���жϸö����Ƿ��Ѿ�֧����(֧�����ҳɹ�)
						TradePay tradePay = tradePayDAO
								.findTradePayByOrderNo(tradeOrderNo);
						if (tradePay != null) {
							if (TradePay.PAY_STATUS_SUCCESS.equals(tradePay
									.getPayStatus())) {
								result
										.setErrorCode(PayResult.ERROR_PAY_HAS_CREATED);
								return result;
							}
							// �Ѿ�֧��������δ֧���ɹ�����Ҫ�޸�֧��ʱ��
							tradePayDAO.changePayDate(tradePay.getId(),
									memberId);
							result.setSuccess(true);
							result.setTradePay(tradePay);
							return result;
						}
						// 4�����ݶ�����Ϣ��õ�����Ϣ���жϵ����Ƿ��������˺�
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("�޷���ȡ���ҵ������ݣ����ܽ���֧��");
							return result;
						}
						if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}
						
						//5. ���¶���״̬
						tradeOrderService.updateOrderSetShipping(tradeOrderNo, memberId);
						
						// 6�������µ�֧��������֧��״̬ΪI:��ʼ,ת��״̬ΪI��δת�ˣ�
						tradePay = payByOrder(order, shop);
						result.setSuccess(true);
						result.setTradePay(tradePay);
						return result;
					}
				});
		return result;
	}

	public PayResult quickPay(final TradeOrder order, final String note) {
		if (order == null || order.getShopId() == null) {
			throw new NullPointerException(
					"order info or shop info can't be null");
		}
		PayResult result = (PayResult) transactionTemplate
				.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						PayResult result = new PayResult();
						result.setSuccess(false);
						// 1����ȡ�����е�����Ϣ���жϵ����Ƿ��������˺ţ��Ƿ���Ҫ������Ϣ��ֻҪҪ�����˺ż��ɽ���֧������
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("�޷���ȡ���ҵ������ݣ����ܽ���֧��");
							return result;
						}
						if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}
						// 2�� �ж��������Ƿ�ͬһ��
						if (order.getBuyerId().equals(shop.getMemberId())) {
							result
									.setErrorCode(PayResult.ERROR_SAME_BUYER_SELLER);
							return result;
						}
						// 3�������µĽ��׶�������������ΪF�����ٶ���������״̬Ϊ"˫����ȷ�ϣ��ȴ�֧��",���Ӷ���������Ϣ
						createQuickOrder(order, note, shop);
						// 4�������µ�֧��������֧��״̬ΪI:��ʼ,ת��״̬ΪI��δת�ˣ�
						TradePay tradePay = payByOrder(order, shop);
						result.setSuccess(true);
						result.setTradePay(tradePay);
						return result;
					}
				});
		return result;
	}

	public TradePayQuery queryTradePayList(TradePayQuery query) {
		if (query == null) {
			throw new NullPointerException("query info can't be null");
		}
		// 1��ת�����,����loginIdΪmemberId,����Ѿ������memberId������ת��
		if (StringUtils.isNotEmpty(query.getBuyLoginId())) {
			Member buyer = memberDAO.getMemberByLoginId(query.getBuyLoginId());
			if (buyer == null) {
				return query;
			}
			query.setBuyId(buyer.getMemberId());
		}
		if (StringUtils.isNotEmpty(query.getSellerLoginId())) {
			Member seller = memberDAO.getMemberByLoginId(query
					.getSellerLoginId());
			if (seller == null) {
				return query;
			}
			query.setSellerId(seller.getMemberId());
		}
		// 2������DAO��������
		query = tradePayDAO.findTradePayList(query);
		// 3��ƴװ����
		addShopInfo(query);
		return query;
	}

	public PayResult changePayStatus(final Long payId, final String payStatus,
			final String modifier,final String payBank,final String serialNo) {
		if (payId == null || payStatus == null) {
			throw new NullPointerException("payId or payStatus can't be null");
		}
		PayResult result = (PayResult) transactionTemplate
				.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						PayResult result = new PayResult();
						result.setSuccess(false);
						TradePay tradePay = tradePayDAO.findTradePayById(payId);
						if (tradePay == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("�޷���ȡ֧����ˮ�������޸�֧��״̬");
							return result;
						}
						// 1�����Ϊ�޸�֧��״̬�ɹ���
						if (TradePay.PAY_STATUS_SUCCESS.equals(payStatus)) {
							// 1.1����ȡ֧��������Ϣ���ж�״̬�Ƿ�Ϊ����ʼ״̬�����ߡ�֧��ʧ��״̬��
							if (!TradePay.PAY_STATUS_FAIL.equals(tradePay
									.getPayStatus())
									&& !TradePay.PAY_STATUS_INIT
											.equals(tradePay.getPayStatus())) {
								result.setErrorCode(PayResult.ERROR_PAY_STATUS);
								return result;
							}
							// 1.2����ȡ���׶�����Ϣ���ж�״̬�Ƿ�Ϊ��˫����ȷ�ϣ��ȴ�֧����״̬
							TradeOrder order = tradeOrderService
									.getTradeOrder(tradePay.getOrderNo());
							if (order == null) {
								result
										.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
								result.setErrorMessage("�����ݣ��޷���ȡ���׶����������޸�֧��״̬");
								return result;
							}
							if (!order.canSetSuccess()) {
								result
										.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
								result.setErrorMessage("���׶���״̬�쳣�������޸�֧��״̬");
								return result;
							}

						}
						// 2������Ӧ��֧������״̬�޸�
						tradePayDAO.updatePayStatus(payId, payStatus, modifier,payBank,serialNo);
						// 3���޸���Ӧ�Ľ��׶���״̬
						tradeOrderService.updateOrderSetSuccess(tradePay
								.getOrderNo(), modifier);
						result.setSuccess(true);
						return result;
					}
				});
		return result;
	}

	public PayResult changeTransStatus(Long payId, String transStatus,
			String modifier) {
		if (payId == null || transStatus == null) {
			throw new NullPointerException("payId or payStatus can't be null");
		}
		PayResult result = new PayResult();
		result.setSuccess(false);
		if (TradePay.TRANS_STATUS_SUCCESS.equals(transStatus)) {
			// 1���鿴֧��״̬��֧��״̬����Ϊ��֧���ɹ���
			TradePay tradePay = tradePayDAO.findTradePayById(payId);
			if (tradePay == null) {
				result.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
				result.setErrorMessage("�޷���ȡ֧����ˮ�������޸�ת��״̬");
				return result;
			}
			if (!TradePay.PAY_STATUS_SUCCESS.equals(tradePay.getPayStatus())) {
				result.setErrorCode(PayResult.ERROR_PAY_STATUS);
				return result;
			}
		}
		// 2��ĿǰΪ��̨Ӧ�ã��߼��Ƚϼ򵥣��������κ��޸ģ�ֱ�ӵ���DAO�޸�ת��״̬����
		tradePayDAO.updateTransStatus(payId, transStatus, modifier);
		result.setSuccess(true);
		return result;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param query
	 */
	private void addShopInfo(TradePayQuery query) {
		List<TradePay> tradePayList = query.getTradePayList();
		if (tradePayList != null && tradePayList.size() > 0) {
			String[] memberIds = new String[tradePayList.size()];
			for (int i = 0; i < tradePayList.size(); i++) {
				memberIds[i] = tradePayList.get(i).getSellerId();
			}
			// ��ȡ������Ϣ
			Map<String, Shop> shopMap = shopDAO
					.getgetShopMapByMemberIds(memberIds);
			// ���ӵ�������
			for (TradePay tradePay : tradePayList) {
				tradePay.setShopName(shopMap.get(tradePay.getSellerId())
						.getShopName());
			}
			query.setTradePayList(tradePayList);
		}
	}

	/**
	 * ��������У��
	 * 
	 * @param order
	 * @param memberId
	 * @param result
	 * @return
	 */
	private boolean checkOrder(TradeOrder order, String memberId,
			PayResult result) {
		if (order == null) {
			result.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("�޷���ȡ���׶������ݣ����ܽ���֧��");
			return false;
		}
		// 2.1 �ж��Ƿ����Լ��Ķ���
		if (!memberId.equals(order.getBuyerId())) {
			result.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("����Ȩ�޴������˶���");
			return false;
		}
		// 2.2���ж϶���״̬�Ƿ�Ϊ��˫����ȷ�ϣ��ȴ�֧����״̬
		if (!order.isWaitMyPay(memberId)) {
			result.setErrorCode(PayResult.ERROR_ORDER_STATUS);
			return false;
		}
		// 2.3 �ж��������Ƿ�ͬһ��
		if (order.getBuyerId().equals(order.getSellerId())) {
			result.setErrorCode(PayResult.ERROR_SAME_BUYER_SELLER);
			return false;
		}
		return true;
	}

	/**
	 * �����µ�֧��������֧��״̬ΪI:��ʼ,ת��״̬ΪI��δת�ˣ�
	 * 
	 * @param order
	 * @param operator
	 * @return
	 */
	private TradePay payByOrder(TradeOrder order, Shop shop) {
		TradePay tradePay = new TradePay();
		Member buyer = memberDAO.getMemberById(order.getBuyerId());
		Member seller = memberDAO.getMemberById(order.getSellerId());
		tradePay.setBuyerId(order.getBuyerId());
		if (buyer != null) {
			tradePay.setBuyerName(buyer.getName());
		}
		tradePay.setCreator(order.getBuyerId());
		tradePay.setModifier(order.getBuyerId());
		tradePay.setOrderNo(order.getOrderNo());
		tradePay.setPayFee(order.getPayFee());
		tradePay.setPayStatus(TradePay.PAY_STATUS_INIT);
		tradePay.setSellerAccount(shop.getBankAccount());
		tradePay.setSellerBank(shop.getBank());
		tradePay.setSellerId(order.getSellerId());
		if (seller != null) {
			tradePay.setSellerName(seller.getName());
		}
		tradePay.setTransStatus(TradePay.TRANS_STATUS_INIT);
		Long id = tradePayDAO.insertTradePay(tradePay);
		tradePay.setId(id);
		return tradePay;
	}

	/**
	 * ���ӿ��ٶ�����Ϣ
	 * 
	 * @param order
	 * @param note
	 * @param shop
	 */
	private void createQuickOrder(TradeOrder order, String note, Shop shop) {
		order.setSellerId(shop.getMemberId());
		order.setCreator(order.getBuyerId());
		order.setStatus(TradeOrder.StatusWaitPay);
		order.setSellerLoginId(shop.getLoginId());
		order.setModifier(order.getBuyerId());
		tradeOrderService.createQuickOrder(order, note);
	}

	
	
	
	public TradePay getTradePayByOrderNo(String orderNo) {
		return tradePayDAO.findTradePayByOrderNo(orderNo);
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setTradePayDAO(TradePayDAO tradePayDAO) {
		this.tradePayDAO = tradePayDAO;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	

}
