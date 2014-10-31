package Calculator;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	private static boolean legal = true;
	private static String ErrorMessage = "";
	private static Stack<String> postfix = new Stack<String>();
	
	private static int GetPriority(char c){
		switch (c){
			case '*': 
			case '/':
			case '%': return 2;
			
			case '+':
			case '-': return 1;
			
			case '(':
			case ')': return 0;
			
			case '#': return -1; // mark the last stack element
			
			default:
				legal = false;
				ErrorMessage = "illegal character";
				return -2;
		}
	}
	
	private static boolean isOperator(char c){
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')';
	}
	
	private static String cal(String x,String y,char op){
		String ret = "";
		BigInteger bx = new BigInteger(x);
		BigInteger by = new BigInteger(y);
		switch (op){
			case '+':
				ret = String.valueOf(bx.add(by));
				break;
			case '-':
				ret = String.valueOf(bx.subtract(by));
				break;
			case '*':
				ret = String.valueOf(bx.multiply(by));
				break;
			case '/':
				if (by.compareTo(BigInteger.ZERO) == 0 ){
					legal = false;
					ErrorMessage = "Divided by zero!!!";
					ret = "0";
					break;
				}
				ret = String.valueOf(bx.divide(by));
				break;
			case '%':
				if (by.compareTo(BigInteger.ZERO) == 0 ){
					legal = false;
					ErrorMessage = "Divided by zero!!!";
					ret = "0";
					break;
				}
				ret = String.valueOf(bx.remainder(by));
				break;
		}
		return ret;
	}
	
	private static boolean ChangeExprIntoPostfix(String expr){
		Stack<Character> opStack = new Stack<Character>();
		int cnt = 0, cur_index = 0;
		
		opStack.push('#');
		
		for (int i=0;i<expr.length();i++){
			char nowch = expr.charAt(i);
			// check whether Operator
			if (isOperator(nowch)){
				// add the number if it exists
				if (cnt>0) postfix.push(expr.substring(cur_index, cur_index+cnt));
				
				char opStackpeek = opStack.peek();
				if (nowch == ')'){
					while (opStack.peek() != '(' )
						postfix.push(String.valueOf(opStack.pop()));
					opStack.pop();
				}
				else{
					while (nowch != '(' && opStackpeek != '#' && GetPriority(nowch)<=GetPriority(opStackpeek)){
						postfix.push(String.valueOf(opStack.pop()));
						opStackpeek = opStack.peek();
					}
					opStack.push(nowch);
				}
				cnt = 0;
				cur_index = i+1;
			}
			// check whether number
			else if (Character.isDigit(nowch))
				cnt++;
			else {
				legal = false;
				ErrorMessage = "Syntax Error!!";
				return false;
			}
		}
		
		// analyse the last item
		if (cnt > 1 || cnt == 1 && !isOperator(expr.charAt(expr.length()-1)))
			postfix.push(expr.substring(cur_index, cur_index+cnt));
		
		// put the remaining operator
		while (opStack.peek()!='#')
			postfix.push(String.valueOf(opStack.pop()));
		
		return true;
	}
	
	private static BigInteger GetResult(String expr){
		Stack<String> resStack = new Stack<String>();
		
		if (!ChangeExprIntoPostfix(expr)){
			legal = false;
			ErrorMessage = "Syntax Error!!";
			return BigInteger.ZERO;
		}
		
		Collections.reverse(postfix);
		String x,y,now;
		while (!postfix.isEmpty()){
			now = postfix.pop();
			if (!isOperator(now.charAt(0))) resStack.push(now);
			else {
				if (!resStack.isEmpty()) y = resStack.pop();
				else {legal = false; ErrorMessage = "Syntax Error!!"; return BigInteger.ZERO;}
				if (!resStack.isEmpty()) x = resStack.pop();
				else {legal = false; ErrorMessage = "Syntax Error!!"; return BigInteger.ZERO;}
				resStack.push(cal(x, y, now.charAt(0)));
			}
		}
		return new BigInteger(resStack.pop());
	}
	
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String s1 = cin.nextLine();
		String s = s1.replace(" ", ""); // delete " "
		while (s1 != null){
			legal = true;
			BigInteger result = BigInteger.ZERO;
			if (s.charAt(0) == '-' || s.charAt(0) == '+') s = '0' + s;
			result = GetResult(s);
			if (legal) System.out.println(result);
			else System.out.println(ErrorMessage);
			s1 = cin.nextLine();
			s = s1.replace(" ", "");
		}
		cin.close();
	}
}
