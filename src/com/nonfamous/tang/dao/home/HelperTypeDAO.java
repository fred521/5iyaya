package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.HelperType;

/**
 * @author: fred
 * 
 * <pre>
 * </pre>
 * 
 * @version $Id: HelperTypeDAO.java,v 1.1 2008/09/15 13:44:58 fred Exp $
 */
public interface HelperTypeDAO {

    /**
     * 
     * @return
     */
    public List<HelperType> getAllHelperType();
    

    /**
     * 
     * @param typeId
     * @return
     */
    public HelperType getHelperTypeById(String typeId);
    
    public HelperType addHelperType(HelperType helperType);


	public String getHelperTypeByTypeName(String typeName);

}
