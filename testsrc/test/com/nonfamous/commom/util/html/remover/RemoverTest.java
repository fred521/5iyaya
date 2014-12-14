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
		String s = "<a href='abc.htm'>haha</a> f�����ֵذ���<br>�ֵķ��� ����ѧ�Υ۩`���<br/>�`���Ǥ��������� <script>alert('kao');</script>";
		s = remover.filterHtml(s);
		System.out.println(s);
	}
}
