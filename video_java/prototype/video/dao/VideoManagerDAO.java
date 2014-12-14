package prototype.video.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import prototype.video.domain.VideoInformation;

public class VideoManagerDAO extends HibernateDaoSupport implements IVideoManagerDAO {
	
	private Logger logger = LoggerFactory.getLogger( VideoManagerDAO.class ) ;
	
	public Long saveVideoInformation( VideoInformation vi ) {
		getHibernateTemplate().saveOrUpdate( vi ) ;
		return vi.getId( ) ;
	}
	public VideoInformation loadVideoInformation( Long vid  ) {
		return  (VideoInformation)getHibernateTemplate().get( VideoInformation.class , vid  ) ;
	}
	
	@SuppressWarnings("unchecked")
	public VideoInformation getLiveVideoInformation( String userId ) {
		String aclObjByIdentity = " from " + VideoInformation.class.getName()
		+ " as o where o.userId=:userId and o.isLive=:isLive" ;	
		
		Session session = null;
		try {
			session = getSession();
			List list = session.createQuery(aclObjByIdentity).setParameter("userId", userId).setParameter( "isLive" , true ).list() ;
			if( list.size() > 0 ) {
				return (VideoInformation)list.get(0) ;
			}
		} catch (Exception e) {
			logger.error( e.getMessage() ) ;
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					;
				}
			}
		} 
		return null ;
	}
}