package com.aa.tagthebus.utils;


public class DateUtils {
	
	// From server : 2014-09-23 15:21:14.000Z
	// From ui : 23/09/2014
	// return 2013-12-05
	public static String getFormattedDate(String date){
		String result = "";
		String[] rstTab = date.split(" ");
		if(rstTab.length == 2){
//			from server
			result = rstTab[1];
		}
		else if(rstTab.length == 1){
//			from ui
			String[] rstUITab = date.split("/");
			result = rstUITab[2]+"-"+rstUITab[1]+"-"+rstUITab[0];
		}
		return result;
	}
	
	private static String getMonth(String monthLetter){
		String month = "";
		switch (monthLetter) {
		case "jan":
			month = "01";
			break;
		case "feb":
			month = "02";
			break;
		case "mar":
			month = "03";
			break;
		case "apr":
			month = "04";
			break;
		case "may":
			month = "05";
			break;
		case "jun":
			month = "06";
			break;
		case "jul":
			month = "07";
			break;
		case "aug":
			month = "08";
			break;
		case "sep":
			month = "09";
			break;
		case "oct":
			month = "10";
			break;
		case "nov":
			month = "11";
			break;
		case "dec":
			month = "12";
			break;
		default:
			break;
		}
		return month;
	}
}
