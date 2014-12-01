import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Parse{
	public HashMap<String,Integer> page = new HashMap<String,Integer>();
	public HashMap<String,Integer> ip = new HashMap<String,Integer>();
	public HashMap<String,Integer> day = new HashMap<String,Integer>();
	public HashMap<String,Integer> week = new HashMap<String,Integer>();
	public HashMap<String,Integer> month = new HashMap<String,Integer>();
	public HashMap<String,Integer> hour = new HashMap<String,Integer>();
	
	BigInteger totaldata = BigInteger.ZERO;
	boolean broken_flag = false;
	
	void addElement(HashMap<String,Integer> tmp, String now){
		if (tmp.containsKey(now)){
			int cnt = tmp.get(now);
			tmp.put(now, cnt+1);
		} else tmp.put(now, 1);
	}
	
	String cal_week(String _year,String _month,String _day){
		int y = 0 , m = 0 , d = 0;
		try{
			y=Integer.parseInt(_year);
			d=Integer.parseInt(_day);
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
		
		switch (_month){
		case "Jan" : m = 1; break;
		case "Feb" : m = 2; break;
		case "Mar" : m = 3; break;
		case "Apr" : m = 4; break;
		case "May" : m = 5; break;
		case "Jun" : m = 6; break;
		case "Jul" : m = 7; break;
		case "Aug" : m = 8; break;
		case "Sep" : m = 9; break;
		case "Oct" : m = 10; break;
		case "Nov" : m = 11; break;
		case "Dec" : m = 12; break;
		default: m = 0; break;
		}
		
		// using Larsson algorithm
		if (m==1) {m = 13; y--;}
		if (m==2) {m = 14; y--;}
		
		int tmp_w = (d+2*m+3*(m+1)/5+y+y/4-y/100+y/400+1) % 7;
		String ret = null;
		switch (tmp_w){
		case 0 : ret = "Sunday"; break;
		case 1 : ret = "Monday"; break;
		case 2 : ret = "Tuesday"; break;
		case 3 : ret = "Wednesday"; break;
		case 4 : ret = "Thursday"; break;
		case 5 : ret = "Friday"; break;
		case 6 : ret = "Saturday"; break;
		}
		return ret;
	}
	
	void analyse(String line){ 
		// format: 58.206.199.97 - - [20/Mar/2012:13:28:45 +0800] "GET /favicon.ico HTTP/1.1" 404 209
		String tmp_ip = null;
		int pos = line.indexOf(' ');
		tmp_ip = line.substring(0,pos);
		
		pos = line.indexOf('[');
		String tmp_day = line.substring(pos+1,pos+3);
		String tmp_month = line.substring(pos+4,pos+7);
		String tmp_year = line.substring(pos+8,pos+12);
		String tmp_hour = line.substring(pos+13,pos+15);
		String tmp_week = cal_week(tmp_year, tmp_month, tmp_day);
		
		addElement(day, tmp_day);
		addElement(hour,tmp_hour);
		addElement(week, tmp_week);
		addElement(month, tmp_month);
		
		pos = line.indexOf("\"");
		int pos_end = line.indexOf(" ",pos);
		String method = line.substring(pos+1,pos_end);
		
		pos = line.indexOf(" ", pos_end+1);
		String tmp_page = line.substring(pos_end+1,pos);
		addElement(page, tmp_page);
		
		pos_end = line.indexOf("\"",pos);
		pos = line.indexOf(" ",pos_end+2);
		String statue = line.substring(pos_end+2,pos);
		String data = line.substring(pos+1);
		
		if (method.equals("GET"))
			addElement(ip, tmp_ip);
		
		if (method.equals("POST") && (statue.charAt(0)=='2') && !data.equals("-")){
			BigInteger Bigdata = new BigInteger(data);
			totaldata = totaldata.add(Bigdata);
		}
		if (statue.charAt(0)=='5') broken_flag = true;
	}
	
	void print_result(){
		try{
			FileWriter file = new FileWriter("result.txt");
			file.write("The weblog has been analysed, and here is the result:\n");
			file.write("\n");
			
			file.write(findmax(page) + " is the most popular pages they provide;\n");
			
			file.write("IP:" + findmax(ip) + " took the most pages from the site;\n");
			
			if (broken_flag) file.write("Other sites appeared to have broken links to this site’s pages;\n");
			else file.write("Other sites didn't appear to have broken links to this site’s pages;\n");
			
			file.write(totaldata+" bytes have been delivered to clients;\n");
			
			file.write("\n");
			file.write("In one day, " + findmax(hour) + " o'clock is the busiest period;\n");
			file.write("In one week, " + findmax(week) + " is the busiest period;\n");
			file.write("In one month, " + findmax(day) + " is the busiest period;\n");
			file.write("In one year, " + findmax(month) + ". is the busiest period.\n");
			file.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	// http://www.cnblogs.com/meieiem/archive/2011/11/02/2233041.html
	String findmax(HashMap<String, Integer> map)
	{
		int max = -1;
		String maxstring = "";
		/*
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
	       String key =  (String)iter.next();
	       int value = map.get(key);
	       if(value>max){
	    	   maxstring = key;
	    	   max = value;
	       }
		}
		*/
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			int value = (int)entry.getValue();
			if (value>max){ max=value; maxstring = key; }
		}
		
		return maxstring;
	}
};


// log : http://www.ittribalwo.com/article/1994.html

public class LogAnalyzer {

	public static void main(String[] args) {
		try{
			FileReader reader = new FileReader("access.log");
			BufferedReader buffer = new BufferedReader(reader);
			Parse parse = new Parse();
			String line = null;
			while ((line = buffer.readLine()) != null)
				parse.analyse(line);
			parse.print_result();
			buffer.close();
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

}
