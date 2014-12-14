package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.PictureInfo;


public interface PictureDAO {

    /**
     * 添加图片记录
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public String addPicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * 添加一批图片记录
     * 
     * @param pis
     * @throws DataAccessException
     */
    public void addPictures(List<PictureInfo> pis) throws DataAccessException;
    
    /**
     * 更新图片记录
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public int updatePicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * 删除图片记录
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public int deletePicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * 删除商品的所有图片
     * 
     * @param goodsId
     * @throws DataAccessException
     */
    public void deletePictureByGoodsId(String goodsId) throws DataAccessException;
    
    /**
     * 获取商品的所有图片
     * 
     * @param goodsId
     * @return
     * @throws DataAccessException
     */
    public List<PictureInfo> getPictureByGoodsId(String goodsId) throws DataAccessException;
}
