package com.nonfamous.tang.domain.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.nonfamous.commom.util.Money;

/**
 * @author fred
 * 
 */
public class DomainBase implements Serializable {

	private static final long serialVersionUID = 402154372800404254L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * »ñÈ¡½ð¶î
	 * 
	 * @param cent
	 * @return
	 */
	public Money getMoney(Long cent) {
		if (cent == null) {
			return null;
		}
		Money money = new Money();
		money.setCent(cent.longValue());
		return money;
	}

}
