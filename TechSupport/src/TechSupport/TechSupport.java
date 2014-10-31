package TechSupport;

import java.io.EOFException;
import java.io.IOException;
import java.util.Scanner;

// About Serializable: http://xiebh.iteye.com/blog/121311
// http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
// java IO: http://see.xidian.edu.cn/cpp/html/1744.html
// java IO: http://www.cnblogs.com/hqr9313/archive/2012/04/23/2467294.html

public class TechSupport {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		
		System.out.println("Welcome to TechSupport Online System.");
		System.out.println("Please input what you want to do:");
		System.out.println("  0: Add keyword and corresponding answer");
		System.out.println("  1: Send a query");
		System.out.println("  2: Exit");
		
		String str = cin.next();
		char ch = str.charAt(0);
		database db = new database();

		while (ch!='2'){
			switch (ch){
				case '0':
					try{
						db.update();
					}catch(EOFException e){}
					 catch(IOException e){ e.printStackTrace(); }
					 catch(ClassNotFoundException e) { e.printStackTrace(); }
					break;
				case '1':
					try{
						db.get();
					}catch(EOFException e){}
					 catch(IOException e){ e.printStackTrace(); }
					 catch(ClassNotFoundException e) { e.printStackTrace(); }
					
					db.query();
					break;
				default:
					System.out.println("Sorry, your input is invalid. Please try again.");
					break;
			}
			
			System.out.println("Welcome to TechSupport Online System.");
			System.out.println("Please input what you want to do:");
			System.out.println("  0: Add keyword and corresponding answer");
			System.out.println("  1: Send a query");
			System.out.println("  2: Exit");
			
			str = cin.next();
			ch = str.charAt(0);
		}
		cin.close();
	}

}
