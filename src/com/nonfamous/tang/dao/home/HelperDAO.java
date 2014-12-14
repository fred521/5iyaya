package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.web.dto.HelperTypeAndInfoDTO;

/**
 * 
 * @author fred
 * 
 */
public interface HelperDAO {

    /**
     * 新增Helper
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public void insertHelper(Helper helper) throws DataAccessException;

    /**
     * 更新Helper信息
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public int updateHelper(Helper helper) throws DataAccessException;

    /**
     * 删除Helper，这里的删除统指物理删除
     * 
     * @param HelperId
     * @return
     * @throws DataAccessException
     */
    public int deleteHelperById(String helperId) throws DataAccessException;

    /**
     * 得到Helper的base信息，这里包括content
     * 
     * @param HelperId
     * @return
     * @throws DataAccessException
     */
    public Helper getHelperById(String helperId) throws DataAccessException;


    /**
     * 用于前台得到所有Helper信息
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getAllHelperList(HelperQuery query) throws DataAccessException;

    /**
     * 用于前台得到所有Helper信息
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getAllHelperList( ) throws DataAccessException;
    
    /**
     * 后台查询新闻信息
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<Helper> queryHelperList(HelperQuery query) throws DataAccessException;

    /**
     * 得到更新时间晚于某时间的所有Helper信息
     * 
     * @param date
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getHelperListByDate(Date date) throws DataAccessException;


    /**
     * 获取某个信息编号下的所有信息用于咨询信息首页显示（最多10条）
     * @param newType
     */
    public HelperTypeAndInfoDTO getHelperByHelperType(String helperType) throws DataAccessException;;

    /**
     * 获取所有的咨询信息用于咨询信息首页显示（每个helpertype下最多10条）
     * @param newType
     */
    public Map<String, HelperTypeAndInfoDTO> getAllHelperInfo() throws DataAccessException;
    
    
   //qjy add 20071225 得到数据库系统时间
    public Date getSysDate();
}
