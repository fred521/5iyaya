/**
 * 
 */
package com.nonfamous.tang.domain.result;

/**
 * @author swordfish
 *
 */
public class ShopResult extends ResultBase {

    private static final long  serialVersionUID            = -3228316902264797039L;

    /** 无效参数 */
    public static final String ERROR_INVALID_PARAMETER     = "ERROR_INVALID_PARAMETER";

    /** 用户已经创建店铺 */
    public static final String ERROR_HAS_SHOP              = "ERROR_HAS_SHOP";

    /** 三维地址重复*/
    public static final String ERROR_GIS_ADDRESS_DUPLICATE = "ERROR_GIS_ADDRESS_DUPLICATE";

    /** 签约店铺已经存在*/
    public static final String ERROR_HAS_SIGN_SHOP         = "ERROR_HAS_SIGN_SHOP";

    /** 店铺不存在*/
    public static final String ERROR_SHOP_NOT_EXIST        = "ERROR_SHOP_NOT_EXIST";

}
