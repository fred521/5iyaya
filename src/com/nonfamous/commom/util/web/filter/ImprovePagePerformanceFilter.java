package com.nonfamous.commom.util.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImprovePagePerformanceFilter implements Filter {

	private Map<String,String> mapHeaders = new HashMap<String,String>();
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest  hreq = (HttpServletRequest)req  ;
		HttpServletResponse hres = (HttpServletResponse)res ;
		
		// 1, whether we need GZIP it
		if( "true".equals( hreq.getParameter( "gzip" ) ) 
				&& mapHeaders.containsKey( "gzip" ) ){  
				    //&& ( "" + hreq.getHeader("Accept-Encoding") ).toLowerCase( ).indexOf( "gzip" ) >= 0 ) {
			hres.addHeader( "Content-Encoding", mapHeaders.get( "gzip" ) ) ;
		}
		
		// 2, whether we need set expire date for it
		if( mapHeaders.containsKey( "ExpiredInHours" ) ) {
			hres.setDateHeader( "Expires", System.currentTimeMillis() + 1000*3600*Integer.valueOf( mapHeaders.get( "ExpiredInHours" ) ) ) ;
		}		
		chain.doFilter( req , res ) ;
	}

	public void init(FilterConfig cfg ) throws ServletException {
		Enumeration enu = cfg.getInitParameterNames( ) ;		
		while( enu.hasMoreElements() ) {
			String hName = enu.nextElement().toString( ) ;
			mapHeaders.put( hName , cfg.getInitParameter( hName ) ) ;
		}
	}

}
