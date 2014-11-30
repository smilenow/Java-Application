import java.util.Scanner;
import java.util.Vector;


public class Input {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String what_to_input;
		
		Scanner sin = new Scanner(System.in);
		if (args.length > 0){
			what_to_input = args[0];
			int flag=0;
			for (String str:args){
				if (flag == 0) {flag=1; continue;}
				what_to_input += " " + str;
			}
		} else what_to_input = sin.nextLine();
		
		Vector<Record> database = new Vector<Record>();
		
		Base base = new Base();
		database = base.load(base._filename);
		
		Record nowrecord = base.ToRecord(what_to_input);
		
		boolean flag = false;
		for (Record x:database){
			if ((x.name.compareTo(nowrecord.name)==0) && (x.course.compareTo(nowrecord.course)==0)){
				flag = true;
				x.mark = nowrecord.mark;
				System.out.println(x.name+"\'s "+x.course+" has been changed to "+String.valueOf(x.mark));
				break;
			}
		}
		if (!flag){
			database.add(nowrecord);
			System.out.println(nowrecord.name+"\'s "+nowrecord.course+" has been added, and the mark is "+String.valueOf(nowrecord.mark));
		}
		
		base.save(base._filename,database);
	}

}
