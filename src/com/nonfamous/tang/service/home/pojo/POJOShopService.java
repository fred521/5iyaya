package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.home.SignShopDAO;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;
import com.nonfamous.tang.domain.result.ShopResult;
import com.nonfamous.tang.domain.trade.SignShop;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.ShopService;

/**
 * @author swordfish
 * 
 */
public class POJOShopService extends POJOServiceBase implements ShopService {
    private static final Log logger = LogFactory.getLog(POJOServiceBase.class);

    private ShopDAO          shopDAO;

    private MarketTypeDAO    marketTypeDAO;

    private SignShopDAO      signShopDAO;

    public void setShopDAO(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
        this.marketTypeDAO = marketTypeDAO;
    }

    public void setSignShopDAO(SignShopDAO signShopDAO) {
        this.signShopDAO = signShopDAO;
    }

    public Shop shopSelectByMemberId(String memberId) {
        // 1���ж�����Ƿ�Ϊ��
        if (memberId == null) {
            throw new NullPointerException("memberId can't be null");
        }
        // 2����ѯ������ϸ��Ϣ
        return shopDAO.shopSelectByMemberId(memberId);
    }

    public Shop shopSelectByShopId(String shopId) {
        // 1���ж�����Ƿ�Ϊ��
        if (shopId == null) {
            throw new NullPointerException("shopId can't be null");
        }
        // 2����ѯ������ϸ��Ϣ
        return shopDAO.shopSelectByShopId(shopId);
    }

    public ShopResult insertShop(Shop shop, boolean needSelectByMemeberId) {
        ShopResult result = new ShopResult();
        result.setSuccess(false);
        // �ж����
        if (shop == null) {
            throw new NullPointerException("shop info can't be null");
        }
        // �ж��Ƿ��л�Աid����Ϣ
        if (StringUtils.isEmpty(shop.getMemberId())) {
            result.setErrorCode(ShopResult.ERROR_INVALID_PARAMETER);
            result.setErrorMessage("��ԱidΪ��");
            return result;
        }
        // �жϸû�Ա�Ƿ��Ѿ��е�����Ϣ
        if (needSelectByMemeberId && shopDAO.shopSelectByMemberId(shop.getMemberId()) != null) {
            result.setErrorCode(ShopResult.ERROR_HAS_SHOP);
            result.setErrorMessage("�û�Ա�Ѿ������˵���");
            return result;
        }
        // �����ά��ַ�Ѿ����ڣ������ظ�����
        if (StringUtils.isNotBlank(shop.getGisAddress())) {
            Shop tmp = shopDAO.shopSelectByGisAddress(shop.getGisAddress());
            if (tmp != null) {
                result.setErrorCode(ShopResult.ERROR_GIS_ADDRESS_DUPLICATE);
                result.setErrorMessage("����ά��ַ�Ѿ�����ĵ���ʹ��");
                return result;
            }
        }

        String shopId = UUIDGenerator.generate();
        shop.setShopId(shopId);
        ShopCommend commend = shop.getShopCommend();
        commend.setShopId(shopId);
        try {
            shopDAO.insertShop(shop);
            shopDAO.insertShopCommend(commend);
            result.setSuccess(true);
        } catch (DataAccessException e) {
            logger.error("insert shop error :" + e.getMessage());
            result.setErrorMessage("���̴���ʧ��");
        }
        return result;
    }

    public ShopCommend updateShopCommend(ShopCommend commend, Shop shop) {
        if (commend == null) {
            throw new NullPointerException("ShopCommend can't be null");
        }
        shopDAO.updateShopCommend(commend, shop);
        return commend;
    }

    public Shop updateShop(Shop shop) {
        if (shop == null) {
            throw new NullPointerException("ShopCommend can't be null");
        }
        // �����ά��ַ�Ѿ����ڣ������ظ�����
        if (StringUtils.isNotBlank(shop.getGisAddress())) {
            Shop tmp = shopDAO.shopSelectByGisAddress(shop.getGisAddress());
            if (tmp != null && !tmp.getShopId().equalsIgnoreCase(shop.getShopId())) {
                throw new ServiceException("����ά��ַ�Ѿ�����ĵ���ʹ��");
            }
        }
        shopDAO.updateShop(shop);
        return shop;
    }

    public ShopCommend getShopCommendByMemberId(String memberId) {
        // 1���ж�����Ƿ�Ϊ��
        if (memberId == null) {
            throw new NullPointerException("memberId can't be null");
        }
        // 2����ѯ������ϸ��Ϣ
        return shopDAO.getShopCommendByMemberId(memberId);
    }

    public Shop shopfullSelectByMemberId(String memberId) {

        // 1���ж�����Ƿ�Ϊ��
        if (memberId == null) {
            throw new NullPointerException("memberId can't be null");
        }
        // 2����ѯ������ϸ��Ϣ
        Shop shop = (Shop) shopDAO.shopfullSelectByMemberId(memberId);
        if (shop != null) {
            String marketId = shop.getBelongMarketId();
            MarketType marketType = (MarketType) marketTypeDAO.getMarketTypeById(marketId);
            if (marketType != null) {
                shop.setBelongMarketName(marketType.getMarketName());
            }
        }
        return shop;

    }

    public Shop shopfullSelectByShopId(String shopId, String shopAddress) {
        // 1���ж�����Ƿ�Ϊ��
        if (StringUtils.isBlank(shopAddress) && StringUtils.isBlank(shopId)) {
            throw new NullPointerException("shopId or shopAddress can't be null");
        }
        // 2����ѯ������ϸ��Ϣ
        Shop shop = (Shop) shopDAO.shopfullSelectByShopId(shopId, shopAddress);
        if (shop != null) {
            String marketId = shop.getBelongMarketId();
            MarketType marketType = (MarketType) marketTypeDAO.getMarketTypeById(marketId);
            if (marketType != null) {
                shop.setBelongMarketName(marketType.getMarketName());
            }
        }
        return shop;
    }

    public ShopResult addSignShop(String shopId, String memberId) {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(memberId)) {
            throw new NullPointerException("shopId or memberId can't be null");
        }
        ShopResult result = new ShopResult();
        SignShop ss = this.signShopDAO.getByShopAndMember(shopId, memberId);
        //ǩԼ���̲����ڣ�������֮
        if (ss == null) {
            Shop shop = this.shopDAO.shopSelectByShopId(shopId);
            //���̴���
            if (shop != null) {
                ss = new SignShop();
                ss.setMemberId(memberId);
                ss.setShopId(shopId);
                //����
                this.signShopDAO.insert(ss);
                result.setSuccess(true);
            } else {
                result.setErrorCode(ShopResult.ERROR_SHOP_NOT_EXIST);
            }
        } else {
            result.setErrorCode(ShopResult.ERROR_HAS_SIGN_SHOP);
        }
        return result;
    }

	public List<Shop> getMySignShopList(String memberId) {
		if(memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return signShopDAO.getMySignShopList(memberId);
	}
	
	public List<Shop> getLatestShopList(){
		return shopDAO.getLatestShopList();
	}
	
	public Integer getEffectGoodsCount()
	 {
		 return (Integer)shopDAO.getEffectGoodsCount();
	 }
	
	public Integer getShopCommendCount()
	 {
		 return (Integer)shopDAO.getShopCommendCount();
	 }
	
	public Integer getShopNameCount()
	 {
		 return (Integer)shopDAO.getShopNameCount();
	 }
	
	 public Integer getAllNewsCount()
	 {
		 return (Integer)shopDAO.getAllNewsCount();
	 }

}
