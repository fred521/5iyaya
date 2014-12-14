package test.com.nonfamous.tang.service.mail;

import java.util.HashMap;
import java.util.Map;

import test.com.nonfamous.tang.service.ServiceTestBase;

import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.service.mail.IMailEngine;

public class MailEngineTest extends ServiceTestBase {
	
	private IMailEngine mailEngine;

    protected void setUp() throws Exception {
        super.setUp();
        
        mailEngine = (IMailEngine) this.serviceBeanFactory.getBean("mailEngine");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mailEngine = null;
    }

    public void test_sendVelocityMessage() throws Exception {

        MailInfo info = new MailInfo();

        info.setTo(new String[] { "wq0032230@hotmail.com" });
        info.setSubject("Test Subject");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("username", "Kitty");

        mailEngine.sendVelocityMessage(info, "sendVelocityMailTest.vm", model);
    }

    public void test_sendMessage() throws Exception {
        MailInfo info = new MailInfo();

        info.setTo(new String[] { "wq0032230@hotmail.com" });
        info.setSubject("Test Subject");
        info.setContent("Test Content");

        mailEngine.sendMessage(info);
    }
}
