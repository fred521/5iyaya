package com.nonfamous.tang.service.admin.pojo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.nonfamous.tang.service.admin.HomePageGenerateService;

public class POJOHomePageGenerateService implements HomePageGenerateService {
	
	private final static Object lock = new Object();
	
	private String pageFilePath = "/var/sjq/index.html";
	
	private String homePageUrl = "http://www.5iyya.com/index.htm";
	
	private String fileCharSet = "gb2312";
	
	public String getPageFilePath() {
		return pageFilePath;
	}


	public void setPageFilePath(String pageFilePath) {
		this.pageFilePath = pageFilePath;
	}


	public String getFileCharSet() {
		return fileCharSet;
	}


	public void setFileCharSet(String fileCharSet) {
		this.fileCharSet = fileCharSet;
	}


	public String getHomePageUrl() {
		return homePageUrl;
	}


	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}


	public void generateHomePage() throws Exception{
		synchronized (lock) {
            HttpURLConnection huc = null;
            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                huc = (HttpURLConnection)new URL(this.homePageUrl).openConnection();
                huc.addRequestProperty("Accept-Language", "zh-cn");
                //huc.setRequestProperty("user-agent", "Mozilla(MSIE)");
                System.setProperty("sun.net.client.defaultConnectTimeout", "3000000");
                System.setProperty("sun.net.client.defaultReadTimeout", "3000000"); 
                huc.connect();
                InputStream stream = huc.getInputStream();
                bw = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(pageFilePath),fileCharSet));
                br = new BufferedReader(new InputStreamReader(stream, fileCharSet));
                String line;
                while((line = br.readLine())!= null){
                   if(line.trim().length() > 0){
                        bw.write(line);
                        bw.newLine();
                   }
                }
            }catch (Exception e) {
            	throw new Exception(e);
                //e.printStackTrace();
            }finally {
                try {
                    br.close();
                    bw.close();
                    huc.disconnect();
                }catch (Exception e) {
                	throw new Exception(e);
                    //e.printStackTrace();
                }
            } 
        }
	}
	
	public String getAttribute(String name){
		if(name==null){
			return null;
		}
		if(name.equals("pageFilePath")){
			return this.pageFilePath;
		}else if(name.equals("homePageUrl")){
			return this.homePageUrl;
		}else if(name.equals("fileCharSet")){
			return this.fileCharSet;
		}
		return null;
	}

}
