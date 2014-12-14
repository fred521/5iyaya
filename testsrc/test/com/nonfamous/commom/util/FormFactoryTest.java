//===================================================================
// Created on 2007-6-16
//===================================================================
package test.com.nonfamous.commom.util;

import java.util.Date;

import junit.framework.TestCase;

import com.nonfamous.commom.form.DefaultForm;
import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Field;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.tang.domain.base.DomainBase;

public class FormFactoryTest extends TestCase {

	public void testFormToDo() throws Exception {
		DefaultFormFactory formFactory = new DefaultFormFactory();
		Form form = new DefaultForm();
		form.setKey("test1");
		Field field = new Field();
		field.setName("test1");
		field.setValue("test1-value");
		form.setField(field);
		
		form.setKey("test2");
		field = new Field();
		field.setName("test2");
		field.setValue(DateUtils.simpleFormat(new Date()));
		form.setField(field);
		
		form.setKey("test3");
		field = new Field();
		field.setName("test3");
		field.setValue(String.valueOf(1));
		form.setField(field);
		
		TestDo testDo = new TestDo();
		formFactory.formCopy(form, testDo);
		System.out.println(testDo.getTest1());
		System.out.println(testDo.getTest2());
		System.out.println(testDo.getTest3());
	}
	
	public void testDoToForm() throws Exception {
		DefaultFormFactory formFactory = new DefaultFormFactory();
		Form form = new DefaultForm();
		form.setKey("test1");
		Field field = new Field();
		field.setName("test1");
		form.setField(field);
		
		form.setKey("test2");
		field = new Field();
		field.setName("test2");
		form.setField(field);
		
		form.setKey("test3");
		field = new Field();
		field.setName("test3");
		form.setField(field);
		
		TestDo testDo = new TestDo();
		testDo.setTest1("test111");
		testDo.setTest2(new Date());
		testDo.setTest3(new Long(1));
		formFactory.formCopy(testDo, form);
		
		
		System.out.println(form.getField("test1").getValue());
		System.out.println(form.getField("test2").getValue());
		System.out.println(form.getField("test3").getValue());
	}

	public class TestDo extends DomainBase {
		/**
		 * Default Serial UID
		 */
		private static final long serialVersionUID = 1L;

		private String test1;

		private Date test2;

		private Long test3;

		public String getTest1() {
			return test1;
		}

		public void setTest1(String test1) {
			this.test1 = test1;
		}

		public Date getTest2() {
			return test2;
		}

		public void setTest2(Date test2) {
			this.test2 = test2;
		}

		public Long getTest3() {
			return test3;
		}

		public void setTest3(Long test3) {
			this.test3 = test3;
		}

	}
}
