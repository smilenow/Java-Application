import java.util.Scanner;
import java.util.Vector;


public class Import {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sin = new Scanner(System.in);
		String what_to_input = null;
		if (args.length > 0) what_to_input = args[0];
		else what_to_input = sin.nextLine();
		
		Base base = new Base();
		Vector<Record> database = base.load(base._filename);
		
		Vector<Record> newdatabase = base.load(what_to_input);
		
		for (Record x:newdatabase){
			boolean flag = false;
			for (Record y:database){
				if ((x.name.equals(y.name)) && (x.course.equals(y.course))){
					flag = true;
					y.mark = x.mark;
					System.out.println(x.name+"\'s "+x.course+" has been changed to "+String.valueOf(x.mark));
					break;
				}
			}
			if (!flag){
				database.add(x);
				System.out.println(x.name+"\'s "+x.course+" has been added, and the mark is "+String.valueOf(x.mark));
			}
		}
		base.save(base._filename, database);
	}

}
