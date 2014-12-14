package com.nonfamous.tang.service.video;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class JSONMessageConverter implements MessageConverter {

	public Object fromMessage(Message msg ) throws JMSException,MessageConversionException {
		if( msg instanceof TextMessage ) {
			return JSONMessage.fromJSON( ((TextMessage)msg).getText() ) ;
		}
		throw new JMSException("Msg:[" + msg + "] is not text message" );
	}

	public Message toMessage(Object obj , Session session ) throws JMSException,MessageConversionException {
		if( obj instanceof JSONMessage ) {
			return session.createTextMessage( ((JSONMessage)obj).toJSON() ) ;
		}
		throw new JMSException("Object:[" + obj + "] is not a json string message");
	}
}
