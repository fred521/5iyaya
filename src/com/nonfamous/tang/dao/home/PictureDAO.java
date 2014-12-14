package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.PictureInfo;


public interface PictureDAO {

    /**
     * ���ͼƬ��¼
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public String addPicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * ���һ��ͼƬ��¼
     * 
     * @param pis
     * @throws DataAccessException
     */
    public void addPictures(List<PictureInfo> pis) throws DataAccessException;
    
    /**
     * ����ͼƬ��¼
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public int updatePicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * ɾ��ͼƬ��¼
     * 
     * @param pi
     * @return
     * @throws DataAccessException
     */
    public int deletePicture(PictureInfo pi) throws DataAccessException;
    
    /**
     * ɾ����Ʒ������ͼƬ
     * 
     * @param goodsId
     * @throws DataAccessException
     */
    public void deletePictureByGoodsId(String goodsId) throws DataAccessException;
    
    /**
     * ��ȡ��Ʒ������ͼƬ
     * 
     * @param goodsId
     * @return
     * @throws DataAccessException
     */
    public List<PictureInfo> getPictureByGoodsId(String goodsId) throws DataAccessException;
}
