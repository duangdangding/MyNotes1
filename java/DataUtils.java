package com.ssm.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.rmi.server.UID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import sun.misc.BASE64Encoder;

/**
 * @author lsh
 *
 */
public class DataUtils {
	/**
	 * @disposition 不同浏览器下载文件的时候，对于中文文件名的转换
	 * @param filename 文件名
	 * @param agent 浏览器类型
	 * @return 返回文件名
	 * @throws UnsupportedEncodingException
	 */
	public static String downFileNameEncode(String filename,String agent) throws UnsupportedEncodingException {
		if (agent.contains("Firefox")){
			filename="=?utf-8?B?"+new BASE64Encoder().encode(filename.getBytes("utf-8"))+"?=";
			filename=filename.replaceAll("\r\n","");
		}else {
			filename= URLEncoder.encode(filename,"utf-8");
			filename=filename.replace("+"," ");
		}
		return filename;
	}

	/**
	 * 设置下载文件的两个头
	 * @param response
	 * @param filename
	 * @throws UnsupportedEncodingException
	 */
	public static void setHeaer(HttpServletResponse response, String filename) throws UnsupportedEncodingException {
		String header = ServletActionContext.getRequest().getHeader("User=Agent");
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		response.setContentType(mimeType);
		filename=downFileNameEncode(filename,header);
		response.setHeader("content-disposition","attachment;filename="+filename);
	}



	/**
	 * 
	//把String转换成date2012-02-24
	 * @param str
	 * @return
	 */
//	public static Date stringTosqlDate(String str){
		/*Date date=null;
		try {
			date=new SimpleDateFormat("yyyy-MM-dd").parse(str);// Fri Feb 24 00:00:00 CST 2012
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;*/
//		return java.sql.Date.valueOf(str);  // 2012-02-24
//	}
	/**
	 * 把Date类型转换成String类型
	 * @param date
	 * type  SHORT(07-1-18) MEDIUM(2007-1-18) FULL( 2007年1月18日 星期四)
	 * @return
	 */
	/*public static String dateToString(Date date,String type){
		String dateString=null;
		if (type.equals("SHORT")) dateString=DateFormat.getDateInstance(DateFormat.SHORT).format(date);
		if (type.equals("MEDIUM")) dateString=DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
		if (type.equals("FULL")) dateString=DateFormat.getDateInstance(DateFormat.FULL).format(date);
		return dateString;
	}*/
	/**
	 * 把Date类型格式化成String类型
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 把string类型格式化成sql timestamp类型
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date strToDate(String strDate,String pattern){
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date, new ParsePosition(8));
		Date date = null;
		try {
			date=new SimpleDateFormat(pattern).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 使用正则判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		return !Pattern.compile("[0-9]*").matcher(str).matches() ? false : true;
	}
	/**
	 * UID  java.rmi.server.UID.UID()
	 * @return 23位
	 */
	public static String getUID(){
		return new UID().toString().replace(":","").replace("-","");
	}
	public static void main(String[] args) {
		System.out.println(getUID());
	}
	/**
	 * UUID随机数 32位
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 二进制分离创建文件夹
	 * @param fileName
	 * @return
	 */
	public static String createFilePath(String fileName){
		int code = fileName.hashCode();
		int dir1=code & 0xf;
		code=code>>>4;
		int dir2=code & 0xf;
		return new StringBuilder(File.separator).append(dir1).append(File.separator).append(dir2).toString();
	}
	/**
	 * 对密码进行MD5加密
	 * @param pwd
	 * @return 32位的字符串
	 */
//	public static String md5Encrypt(String pwd) {
		//导入外部的包，直接调用方法,一句话搞定org.apache.commons.codec.digest.DigestUtils
//		return DigestUtils.md5Hex(pwd);
		/*StringBuffer buf=new StringBuffer("");
		try {
			//初始化MessageDigest
			MessageDigest	md = MessageDigest.getInstance("MD5");
			//调用update处理数据
			md.update("asd".getBytes());
			//调用下面的方法完成计算
			byte[] digest = md.digest();
			int i;
			//做相应的转化变成16进制的数据
			for (int j = 0; j < digest.length; j++) {
				i=digest[j];
				if (i<0) i+=256;
				if (i<16) buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();*/
//	}
	/**
	 * 获得IP地址
	 * @param request
	 * @return
	 */
	public static String getAddrIp(HttpServletRequest request) {
		String ip=request.getHeader("x-forwarded-for");
		if (ip==null||ip.length()==0||"Unknown".equalsIgnoreCase(ip)) ip=request.getHeader("Proxy-Client-IP");
		if (ip==null||ip.length()==0||"Unknown".equalsIgnoreCase(ip)) ip=request.getHeader("WL-Proxy-Client-IP");
		if (ip==null||ip.length()==0||"Unknown".equalsIgnoreCase(ip)) ip=request.getRemoteAddr();
		return ip;
	}
}	
