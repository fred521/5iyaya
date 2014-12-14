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
 * 银行支付订单接口实现类
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
						// 1、获取交易订单信息
						TradeOrder order = tradeOrderService
								.getTradeOrder(tradeOrderNo);
						// 订单相关校验
						if (!checkOrder(order, memberId, result)) {
							return result;
						}
						// 3、判断该订单是否已经支付过(支付并且成功)
						TradePay tradePay = tradePayDAO
								.findTradePayByOrderNo(tradeOrderNo);
						if (tradePay != null) {
							if (TradePay.PAY_STATUS_SUCCESS.equals(tradePay
									.getPayStatus())) {
								result
										.setErrorCode(PayResult.ERROR_PAY_HAS_CREATED);
								return result;
							}
							// 已经支付，但是未支付成功，需要修改支付时间
							tradePayDAO.changePayDate(tradePay.getId(),
									memberId);
							result.setSuccess(true);
							result.setTradePay(tradePay);
							return result;
						}
						// 4、根据订单信息获得店铺信息
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("无法获取卖家店铺数据，不能进行支付");
							return result;
						}
						/*if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}*/
						//5. 更新订单状态
						tradeOrderService.updateOrderSetShipping(tradeOrderNo, memberId);
						
						// 6、增加新的支付订单（支付状态为I:初始,转账状态为I：未转账）
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
						// 1、获取交易订单信息
						TradeOrder order = tradeOrderService
								.getTradeOrder(tradeOrderNo);
						// 订单相关校验
						if (!checkOrder(order, memberId, result)) {
							return result;
						}
						// 3、判断该订单是否已经支付过(支付并且成功)
						TradePay tradePay = tradePayDAO
								.findTradePayByOrderNo(tradeOrderNo);
						if (tradePay != null) {
							if (TradePay.PAY_STATUS_SUCCESS.equals(tradePay
									.getPayStatus())) {
								result
										.setErrorCode(PayResult.ERROR_PAY_HAS_CREATED);
								return result;
							}
							// 已经支付，但是未支付成功，需要修改支付时间
							tradePayDAO.changePayDate(tradePay.getId(),
									memberId);
							result.setSuccess(true);
							result.setTradePay(tradePay);
							return result;
						}
						// 4、根据订单信息获得店铺信息，判断店铺是否有银行账号
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("无法获取卖家店铺数据，不能进行支付");
							return result;
						}
						if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}
						
						//5. 更新订单状态
						tradeOrderService.updateOrderSetShipping(tradeOrderNo, memberId);
						
						// 6、增加新的支付订单（支付状态为I:初始,转账状态为I：未转账）
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
						// 1、获取订单中店铺信息，判断店铺是否有银行账号（是否不需要店铺信息，只要要银行账号即可进行支付？）
						Shop shop = shopDAO.shopSelectByShopId(order
								.getShopId());
						if (shop == null) {
							result
									.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
							result.setErrorMessage("无法获取卖家店铺数据，不能进行支付");
							return result;
						}
						if (StringUtils.isEmpty(shop.getBankAccount())) {
							result
									.setErrorCode(PayResult.ERROR_NO_SELLER_ACCOUNT);
							return result;
						}
						// 2、 判断买卖家是否同一人
						if (order.getBuyerId().equals(shop.getMemberId())) {
							result
									.setErrorCode(PayResult.ERROR_SAME_BUYER_SELLER);
							return result;
						}
						// 3、增加新的交易订单，订单类型为F：快速订单，订单状态为"双方已确认，等待支付",增加订单留言信息
						createQuickOrder(order, note, shop);
						// 4、增加新的支付订单（支付状态为I:初始,转账状态为I：未转账）
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
		// 1、转化买家,卖家loginId为memberId,如果已经有买家memberId，则不用转换
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
		// 2、调用DAO进行搜索
		query = tradePayDAO.findTradePayList(query);
		// 3、拼装数据
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
							result.setErrorMessage("无法获取支付流水，不能修改支付状态");
							return result;
						}
						// 1、如果为修改支付状态成功。
						if (TradePay.PAY_STATUS_SUCCESS.equals(payStatus)) {
							// 1.1、获取支付订单信息，判断状态是否为“初始状态”或者“支付失败状态”
							if (!TradePay.PAY_STATUS_FAIL.equals(tradePay
									.getPayStatus())
									&& !TradePay.PAY_STATUS_INIT
											.equals(tradePay.getPayStatus())) {
								result.setErrorCode(PayResult.ERROR_PAY_STATUS);
								return result;
							}
							// 1.2、获取交易订单信息，判断状态是否为“双方已确认，等待支付”状态
							TradeOrder order = tradeOrderService
									.getTradeOrder(tradePay.getOrderNo());
							if (order == null) {
								result
										.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
								result.setErrorMessage("脏数据：无法获取交易订单，不能修改支付状态");
								return result;
							}
							if (!order.canSetSuccess()) {
								result
										.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
								result.setErrorMessage("交易订单状态异常，不能修改支付状态");
								return result;
							}

						}
						// 2、做相应的支付订单状态修改
						tradePayDAO.updatePayStatus(payId, payStatus, modifier,payBank,serialNo);
						// 3、修改相应的交易订单状态
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
			// 1、查看支付状态，支付状态必须为“支付成功”
			TradePay tradePay = tradePayDAO.findTradePayById(payId);
			if (tradePay == null) {
				result.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
				result.setErrorMessage("无法获取支付流水，不能修改转账状态");
				return result;
			}
			if (!TradePay.PAY_STATUS_SUCCESS.equals(tradePay.getPayStatus())) {
				result.setErrorCode(PayResult.ERROR_PAY_STATUS);
				return result;
			}
		}
		// 2、目前为后台应用，逻辑比较简单，允许做任何修改，直接调用DAO修改转账状态即可
		tradePayDAO.updateTransStatus(payId, transStatus, modifier);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 获取店铺信息
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
			// 获取店铺信息
			Map<String, Shop> shopMap = shopDAO
					.getgetShopMapByMemberIds(memberIds);
			// 增加店铺名称
			for (TradePay tradePay : tradePayList) {
				tradePay.setShopName(shopMap.get(tradePay.getSellerId())
						.getShopName());
			}
			query.setTradePayList(tradePayList);
		}
	}

	/**
	 * 订单数据校验
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
			result.setErrorMessage("无法获取交易订单数据，不能进行支付");
			return false;
		}
		// 2.1 判断是否处理自己的订单
		if (!memberId.equals(order.getBuyerId())) {
			result.setErrorCode(PayResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("您无权限处理他人订单");
			return false;
		}
		// 2.2、判断订单状态是否为“双方已确认，等待支付”状态
		if (!order.isWaitMyPay(memberId)) {
			result.setErrorCode(PayResult.ERROR_ORDER_STATUS);
			return false;
		}
		// 2.3 判断买卖家是否同一人
		if (order.getBuyerId().equals(order.getSellerId())) {
			result.setErrorCode(PayResult.ERROR_SAME_BUYER_SELLER);
			return false;
		}
		return true;
	}

	/**
	 * 增加新的支付订单（支付状态为I:初始,转账状态为I：未转账）
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
	 * 增加快速订单信息
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
