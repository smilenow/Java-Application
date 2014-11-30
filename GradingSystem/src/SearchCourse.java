import java.util.Scanner;
import java.util.Vector;


public class SearchCourse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sin = new Scanner(System.in);
		String what_to_input = null;
		if (args.length > 0) what_to_input = args[0];
		else what_to_input = sin.nextLine();
		
		Base base = new Base();
		Vector<Record> database = base.load(base._filename);
		Vector<Record> answer = new Vector<Record>();
		
		for (Record x:database)
			if (x.course.equals(what_to_input)) answer.add(x);
		
		if (answer.size() > 0){
			int total = 0;
			double avg = 0.0;
			System.out.println("The gradebook of "+what_to_input+" : ");
			System.out.println("+---------------+---------------+");
			System.out.println("|    Student    +      Mark     |");
			System.out.println("+---------------+---------------+");
			for (Record x:answer){
				System.out.println("|\t"+x.name+"\t|\t"+x.mark+"\t|");
				total += x.mark;
			}
			System.out.println("+---------------+---------------+");
			avg = total*1.0/answer.size();
			System.out.println("Total student(s): "+answer.size());
			System.out.printf("Average marks: %.2f\n",avg);
		}
		else System.out.println("Cannot find course called :"+what_to_input);
	}

}
