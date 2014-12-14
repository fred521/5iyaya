package com.nonfamous.tang.dao.home.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.dao.home.PictureDAO;
import com.nonfamous.tang.domain.PictureInfo;

public class IbatisPictureDAO extends SqlMapClientDaoSupport implements
	PictureDAO {
    private String PICTURE_SPACE = "GOODSPICTURE_SPACE.";

    public String addPicture(PictureInfo pi) throws DataAccessException {
	if (pi == null) {
	    throw new NullPointerException("图片信息不能为空");
	}

	String id = UUIDGenerator.generate();
	if (StringUtils.isBlank(id)) {
	    throw new NullPointerException("pictureId is null");
	}
	pi.setId(id);
	this.getSqlMapClientTemplate().insert(
		PICTURE_SPACE + "pictureinfo_insert", pi);
	return id;
    }

    // XXX: need optimize
    public void addPictures(List<PictureInfo> pis) throws DataAccessException {
	for (PictureInfo pi : pis) {
	    addPicture(pi);
	}

    }

    public int deletePicture(PictureInfo pi) throws DataAccessException {
	if (StringUtils.isBlank(pi.getId())) {
	    throw new NullPointerException("pictureId is null");
	}
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", pi.getId());
	map.put("status", PictureInfo.DELETE_STATUS);

	return this.getSqlMapClientTemplate().update(
		PICTURE_SPACE + "pictureinfo_delete", map);

    }

    public void deletePictureByGoodsId(String goodsId)
	    throws DataAccessException {
	if (StringUtils.isBlank(goodsId)) {
	    throw new NullPointerException("goodsId is null");
	}
	Map<String, String> map = new HashMap<String, String>();
	map.put("goodsId", goodsId);
	map.put("status", PictureInfo.DELETE_STATUS);

	this.getSqlMapClientTemplate().update(
		PICTURE_SPACE + "pictureinfo_delete_by_goodsid", map);

    }

    @SuppressWarnings("unchecked")
    public List<PictureInfo> getPictureByGoodsId(String goodsId)
	    throws DataAccessException {
	if (goodsId == null) {
	    throw new NullPointerException("goodsId is null");
	}

	return this.getSqlMapClientTemplate().queryForList(
		PICTURE_SPACE + "get_pictureinfo_list_by_goodsid", goodsId);
    }

    public int updatePicture(PictureInfo pi) throws DataAccessException {
	if (pi == null) {
	    throw new NullPointerException("图片信息不能为空");
	}

	return this.getSqlMapClientTemplate().update(
		PICTURE_SPACE + "pictureinfo_update", pi);
    }

}
