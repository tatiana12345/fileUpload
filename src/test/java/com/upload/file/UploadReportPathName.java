package com.upload.file;

import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class UploadReportPathName {
	 public static String getDate(){
		 Date d = new Date();
		 String date = d.toString().replace(":",".");
		 
		return date;
		 
	 }

	 public static ExtentReports getInstance(){
		 ExtentReports extent;
		 String FileName = getDate() + ".html";
		 String FilePath = "/Users/tatianakesler/Desktop/reports/" + FileName;
		 extent = new ExtentReports (FilePath, false);
		 extent.addSystemInfo("Selenium version", "3.4.0").addSystemInfo("Platform", "Mac");
		
		 return extent;
		 
	 }
}
/*
*import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class reportPathName {
	
	public static String getDate(){
		Date d = new Date();
		String date = d.toString().replace(":", ".");
		return date;
	}
	
	public static ExtentReports getInstance(){
		ExtentReports extent;
		String FileName = getDate() + ".html";
		String FilePath = "/Users/tatianakesler/Desktop/reports/" + FileName;
		extent = new ExtentReports (FilePath, false);
		extent.addSystemInfo("Selenium version", "3.4.0").addSystemInfo("Paltform", "MAC");
		
		return extent;
	}

*/