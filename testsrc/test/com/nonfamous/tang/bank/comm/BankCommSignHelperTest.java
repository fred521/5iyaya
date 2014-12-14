package test.com.nonfamous.tang.bank.comm;

import java.io.InputStream;

import junit.framework.TestCase;

import com.nonfamous.tang.bank.comm.BankCommData;
import com.nonfamous.tang.bank.comm.BankCommReturnData;
import com.nonfamous.tang.bank.comm.BankCommSignHelper;

/**
 * 跑这个单元测试请修改下同目录下的 B2CMerchant.xml 文件，指向正确的文件路径
 * 
 * @author HGS-BEIJING
 * 
 */
public class BankCommSignHelperTest extends TestCase {

	private BankCommSignHelper helper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new BankCommSignHelper();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				"test/com/nonfamous/tang/bank/comm/B2CMerchant.xml");
		helper.init(is);
		helper.setMerchantId("301310063009501");
	}

	@Override
	protected void tearDown() throws Exception {
		helper = null;
		super.tearDown();
	}

	public void testSign() {
		BankCommData data = new BankCommData();
		data.setOrderid("1234");
		data.setOrderDate("20060729");
		data.setOrderTime("010101");
		data.setNotifyType("1");
		data.setAmount("100");
		data.setOrderContent("测试订单");
		data.setOrderMono("测试内容");
		data.setNotifyType("0");
		data.setPayBatchNo("123124");
		String sign = helper.sign(data);
		System.out.println(data);
		System.out.println(sign);
		assertNotNull(sign);
	}

	public void testVerify() {
		String s = "301310053110018|99990001|100.00|CNY|14124|412444|20060729|120902|0000052|1|10.21|2|||QWR+QWRQWEQD+G\\RQWRFVBNJKFSFAF";
		BankCommReturnData d = new BankCommReturnData(s);
		assertTrue(helper.verify(d));
	}

}
