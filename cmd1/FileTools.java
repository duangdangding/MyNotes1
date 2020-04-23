package com.lshs.gadget.file.core;

import org.junit.Test;

import java.io.*;

/**
 * @Description:
 * @author: LuShao
 * @create: 2018-09-28 11:04
 **/
public class FileTools {
	
	/**
	 * 复制或者移动指定文件到指定目录
	 * @param sourceDir
	 * @param targetDir
	 * @param prefix 后缀
	 * @param isDelFile 是否删除文件
	 * @param hasTree 是否构建原始目录结构
	 * @throws IOException
	 */
	public void copyAssignFile(String sourceDir,String targetDir,String prefix,boolean isDelFile,boolean hasTree) throws IOException {
		if (prefix!=null) sourceDir=sourceDir+"*."+prefix;
		String tree="";
		if (hasTree) tree="/e";
		if (prefix!=null) {
			// chcp 65001 设置UTF-8
			String cmd="chcp 65001 \n @echo off \n"
					+"for /f \"tokens=*\" %%i in ('dir /s /b /a -d \""+sourceDir+"\"') do copy \"%%i\" \""+targetDir+"\" "+tree+" /y";
			createAndRun(cmd);
		}else {
			Runtime.getRuntime().exec("cmd /K xcopy \""+sourceDir+"\" \""+targetDir+"\" "+tree+" /-Y");
		}
		if (isDelFile){
			delFile(sourceDir,prefix);
		}
	}

	/**
	 * 复制或者文件夹下的所有文件到另一个文件
	 * @param sourceDir 源文件夹
	 * @param targetDir 目标文件夹
	 * @param hasTree 是否包含目录结构 true包含 false不包含
	 * @param isDel 是否删除源文件夹true删除 false不删除
	 * @throws IOException
	 */
	public void copyDir(String sourceDir,String targetDir,boolean hasTree,boolean isDel) throws IOException {
		// XCOPY命令说明
		// /Y           取消提示以确认要覆盖现有目标文件。
		// /E           复制目录和子目录，包括空目录。
		// /J           复制时不使用缓冲的 I/O。推荐复制大文件时使用
		// /Z           在可重新启动模式下复制网络文件。
		if (hasTree){
			Runtime.getRuntime().exec("cmd /K xcopy \""+sourceDir+"\" \""+targetDir+"\" /e /Y");
		}else {
			String cmd="chcp 65001 \n @echo off \n"
					+"for /r \""+sourceDir+"\" %%i in (*.*) do if exist %%i copy \"%%i\" \""+targetDir+"\" /y";
			createAndRun(cmd);
		}
		if (isDel){
			delDirTree(sourceDir);
		}
	}

	/**
	 * 删除包含本身的目录树
	 * @param sourceDir
	 * @throws IOException
	 */
	public void delDirTree(String sourceDir) throws IOException {
		// RD 命令说明
		//  /S      除删除目录本身外，还将删除指定目录下的所有子目录和文件。用于删除目录树。
		//  /Q      安静模式，带 /S 删除目录树时不要求确认
		Runtime.getRuntime().exec("cmd /K rd /s /q \""+sourceDir+"\"");
	}

	/**
	 * 删除指定文件或者是指定类型文件
	 * @param sourceDir
	 * @param sufix
	 * @throws IOException
	 */
	public void delFile(String sourceDir,String sufix) throws IOException {
		if (sufix!=null) sourceDir=sourceDir+"*."+sufix;
		Runtime.getRuntime().exec("cmd /K del /s /q \""+sourceDir+"\"");
	}
	
	public void renameFiles(String sourceDir,String prefix,String sufix){
		
	}

	/**
	 * 创建bat文件并运行
	 * @param cmd
	 * @throws IOException
	 */
	public void createAndRun(String cmd) throws IOException {
		String file="./run.bat";
		FileWriter fw=new FileWriter(file);
		fw.write(cmd);
		fw.close();
		try{
			//运行bat文件
			Process process = Runtime.getRuntime().exec(file);
			InputStream in = process.getInputStream();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			process.waitFor();
			System.out.println("执行成功");
		}catch(Exception e){
			System.out.println("执行失败");
		}
	}
	
	@Test
	public void test1() throws IOException {
	    //Runtime.getRuntime().exec("cmd /c xcopy /f https://avatar.csdn.net/8/C/4/3_choigenguk.jpg e:\\ /Z /e /-Y");
	    //Runtime.getRuntime().exec("cmd /c copy F:\\test\\*.txt e:\\");
	    //Runtime.getRuntime().exec("cmd /K Xcopy F:\\test\\test1\\test2\\asd.txt e:\\ /e /-Y");
	    Runtime.getRuntime().exec("cmd /c xcopy F:\\test\\*.* e:\\ /-Y");
	    //Runtime.getRuntime().exec("cmd /c rmdir /s /q E:\\test1");
	    //Runtime.getRuntime().exec("cmd /c rd /s /q E:\\test1 ");
	    //Runtime.getRuntime().exec("cmd /c del /s /q E:\\test1\\*.txt ");
	    
	}
	
	@Test
	public void test2() throws IOException {
	    String cmd="chcp 65001 \n @echo off \n"
			    +"for /f \"tokens=*\" %%i in ('dir /s /b /a -d f:\\test\\*.txt') do copy \"%%i\" e:\\ /y";
	    String file="./run.bat";
		FileWriter fw=new FileWriter(file);
		fw.write(cmd);
		fw.close();
		try{
			//运行bat文件
			Process process = Runtime.getRuntime().exec(file);
			InputStream in = process.getInputStream();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			process.waitFor();
			System.out.println("执行成功");
		}catch(Exception e){
			System.out.println("执行失败");
		}

	}
}
