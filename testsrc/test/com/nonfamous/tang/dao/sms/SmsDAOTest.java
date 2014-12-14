package test.com.nonfamous.tang.dao.sms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.sms.SmsDAO;
import com.nonfamous.tang.domain.Sms;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: SmsDAOTest.java,v 1.1 2008/07/11 00:47:18 fred Exp $
 */
public class SmsDAOTest extends DAOTestBase {

	private SmsDAO smsDAO;

	protected void setUp() throws Exception {
		super.setUp();
		smsDAO = (SmsDAO) this.daoBeanFactory.getBean("smsDAO");
	}

	public void testCreateSms() throws Exception {
		Sms sms = new Sms();
		sms.setContext("·¢ËÍµÄÄÚÈÝ");
		sms.setPhone("12345678901");
		sms.setStatus(Sms.SMS_WAIT_SEND);
		smsDAO.createSms(sms);
	}

	public void testSetSmsStatus() throws Exception {
		Sms sms = new Sms();
		sms.setSmsId(new Long(1));
		sms.setStatus(Sms.SMS_SEND_SUCCESS);
		smsDAO.updateSms(sms);
	}

	public void testGetSmsById() throws Exception {
		Sms sms = smsDAO.getSmsById(new Long(2));
		assert (sms != null);
	}

	public void testDeleteSmsById() throws Exception {
		smsDAO.deleteSmsById(new Long(3));
	}

	public void testGetWaitSendSms() throws Exception {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.HOUR, -1);
		List ls = (List) smsDAO.getWaitSendSms(c.getTime());
		for (int i = 0; i < ls.size(); i++) {
			Sms sms = (Sms) ls.get(i);
			System.out.println(sms.getSmsId() + "" + sms.getStatus());
		}
	}
	
	public void testUpdateStatus() throws Exception{
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.YEAR, -1);
		
		List<Sms> ls = smsDAO.getWaitSendSms(c.getTime());
		smsDAO.updateSmsSendStatus(ls,false);
	}
}
