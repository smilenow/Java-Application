package TechSupport;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class database {

	List<data> _list = new ArrayList<data>();
	
	void get() throws IOException,EOFException,ClassNotFoundException{
		data tmp;
		FileInputStream fin = new FileInputStream("src/TechSupport/data.txt");
		ObjectInputStream oin = new ObjectInputStream(fin);
		while ((tmp=(data)oin.readObject())!=null)
			_list.add(tmp);
		oin.close();
		fin.close();
	}
	
	void update() throws IOException,EOFException,ClassNotFoundException{
		try{
			get();
		} catch (EOFException e){}
		
		Scanner cin = new Scanner(System.in);
		data tmp = new data();
		
		System.out.println("Please input the new keyword, the first line is the keyword(s), and the second line is the answer");
		
		String s = cin.nextLine();
		String[] keywords = s.split(" ");
		
		for (String i:keywords) tmp.keyword.add(i);
		tmp.answer = cin.nextLine();
		_list.add(tmp);
		
		FileOutputStream fout = new FileOutputStream("src/TechSupport/data.txt");
		ObjectOutputStream oout = new ObjectOutputStream(fout);
		for (data i:_list) oout.writeObject(i);
		
//		cin.close();
		oout.close();
		fout.close();
	}
	
	void query(){
		System.out.println("Hello, Sir/Madam, what can I help you? (Input # to quit)");
		Scanner cin = new Scanner(System.in);
		String q = cin.nextLine();
		
		while (q.charAt(0) != '#'){
			String[] q2 = q.split(" ");
			Set<String> qkeywords = new HashSet<String>();
			Set<String> res = new HashSet<String>();
			for (String i:q2) qkeywords.add(i);
			
			int index = -1, _min = 0;
			for (int i=0;i<_list.size();i++){
				res.clear();
				res.addAll(_list.get(i).keyword);
				res.retainAll(qkeywords);
				if (res.size()>_min){
					index = i;
					_min = res.size();
				}
			}
			
			if (index == -1) System.out.println("Sorry, I don't know what you said.");
			else System.out.println(_list.get(index).answer);
		
			q = cin.nextLine();
		}
//		cin.close();
	}

}
