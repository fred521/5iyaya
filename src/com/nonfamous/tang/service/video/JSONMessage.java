package com.nonfamous.tang.service.video;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

public class JSONMessage{
	public static Object fromJSON( String jsonTextMessage ){
		return new JSONReader().read( jsonTextMessage ) ;
	}
	public String toJSON( ) {
		return new JSONWriter().write(this);
	}
}
