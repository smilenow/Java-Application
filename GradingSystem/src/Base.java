import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

class Record{
	public Record(){};
	String name,course;
	int mark;
};  

public class Base {
	public final String _filename = "./database.csv";
	
	public Record ToRecord(String str){
		Record tmp = new Record();
		int comma = str.indexOf(',');
		tmp.name = str.substring(0, comma);
		int comma2 = str.indexOf(',', comma+1);
		tmp.course = str.substring(comma+1, comma2);
		String ss = str.substring(comma2+1);
		tmp.mark = Integer.parseInt(ss);
		return tmp;
	}
	public void save(String filename,Vector<Record> database){
		FileWriter file = null;
		try{
			file = new FileWriter(filename);
			for (Record x:database) file.write(x.name+","+x.course+","+String.valueOf(x.mark)+"\n");
			file.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public Vector<Record> load(String filename){
		Vector<Record> database = new Vector<Record>();
		File file = null;
		BufferedReader reader = null;
		try{
			file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
			String nowline = null;
			Record nowrecord = null;
			while ((nowline = reader.readLine()) != null){
				nowrecord = ToRecord(nowline);
				database.add(nowrecord);
			}
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return database;
	}
}
