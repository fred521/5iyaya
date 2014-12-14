package test.com.nonfamous.commom.util.html.remover;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nonfamous.commom.util.html.remover.HtmlRemover;

public class RemoverTest extends TestCase {
	private HtmlRemover remover;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		BeanFactory beanFactory = new ClassPathXmlApplicationContext(
				"test/com/nonfamous/commom/util/html/remover/remover-cfg.xml");
		remover = (HtmlRemover) beanFactory.getBean("remover");
	}

	@Override
	protected void tearDown() throws Exception {
		remover = null;
		super.tearDown();
	}

	public void testFilter() {
		String s = "<a href='abc.htm'>haha</a> f大赛分地阿三<br>分的发送 都大学のホームペ<br/>ージです。京都大 <script>alert('kao');</script>";
		s = remover.filterHtml(s);
		System.out.println(s);
	}
}
