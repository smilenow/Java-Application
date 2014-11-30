import java.util.Scanner;
import java.util.Vector;


public class PrintAll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Base base = new Base();
		Vector<Record> database = base.load(base._filename);
        
        if (database.size() > 0){
            System.out.println("The gradebook : ");
            System.out.println("+---------------+---------------+---------------+");
            System.out.println("|    Student    +     Course    +      Mark     |");
            System.out.println("+---------------+---------------+---------------+");
            for (Record x:database)
                System.out.println("|\t"+x.name+"\t|\t"+x.course+"\t|\t"+x.mark+"\t|");
            System.out.println("+---------------+---------------+---------------+");
        } else System.out.println("There is no data item in current database!");
		
	}

}
