import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.comm.SerialPort;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public abstract class StatusPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Choice c1,c2,c3,c4,c5;
	
	public static String getport(){
		return c1.getSelectedItem();
	}
	
	public static int getbaud(){
		return Integer.parseInt(c2.getSelectedItem());
	}
	
	public static int getdata(){
		return Integer.parseInt(c3.getSelectedItem());
	}
	
	public static int getstop(){
		String t = c4.getSelectedItem();
		if (t.equals("1")) return SerialPort.STOPBITS_1;
		if (t.equals("1.5")) return SerialPort.STOPBITS_1_5;
		return SerialPort.STOPBITS_2;
	}
	
	public static int getparity(){
		String t = c5.getSelectedItem();
		if (t.equals("NONE")) return SerialPort.PARITY_NONE;
		if (t.equals("MARK")) return SerialPort.PARITY_MARK;
		if (t.equals("EVEN")) return SerialPort.PARITY_EVEN;
		if (t.equals("ODD")) return SerialPort.PARITY_ODD;
		return SerialPort.PARITY_SPACE;
	}
	
	public StatusPanel() {
		// TODO Auto-generated constructor stub
		JLabel status_lb1 = new JLabel("Port:");
		JLabel status_lb2 = new JLabel("Baud:");
		JLabel status_lb3 = new JLabel("Data:");
		JLabel status_lb4 = new JLabel("Stop");
		JLabel status_lb5 = new JLabel("Parity:");
		
		this.setLayout(new GridLayout(2,3));
		
		// construct Port:
		JPanel tmp1 = new JPanel();
		tmp1.add(status_lb1);
		c1 = new Choice();
		c1.addItem("COM1");
		c1.addItem("COM2");
		c1.addItem("COM3");
		c1.addItem("COM4");
		c1.addItem("COM5");
		tmp1.add(c1);
		this.add(tmp1);
		
		// construct Baud:
		JPanel tmp2 = new JPanel();
		tmp2.add(status_lb2);
		c2 = new Choice();
		c2.addItem("4800");
		c2.addItem("9600");
		c2.addItem("14400");
		c2.addItem("19200");
		tmp2.add(c2);
		this.add(tmp2);
		
		// construct Data:
		JPanel tmp3 = new JPanel();
		tmp3.add(status_lb3);
		c3 = new Choice();
		c3.addItem("8");
		c3.addItem("7");
		c3.addItem("6");
		c3.addItem("5");
		tmp3.add(c3);
		this.add(tmp3);
		
		// construct Stop:
		JPanel tmp4 = new JPanel();
		tmp4.add(status_lb4);
		c4 = new Choice();
		c4.addItem("1");
		c4.addItem("1.5");
		c4.addItem("2");
		tmp4.add(c4);
		this.add(tmp4);
		
		// construct Parity:
		JPanel tmp5 = new JPanel();
		tmp5.add(status_lb5);
		c5 = new Choice();
		c5.addItem("NONE");
		c5.addItem("EVEN");
		c5.addItem("MARK");
		c5.addItem("ODD");
		c5.addItem("SPACE");
		tmp5.add(c5);
		this.add(tmp5);
		
		//
		JPanel tmp6 = new JPanel(new FlowLayout());
		JButton jb_link = new JButton("Link");
		JButton jb_break = new JButton("Break");
		jb_link.addActionListener(this);
		jb_break.addActionListener(this);
		tmp6.add(jb_link); tmp6.add(jb_break);
		this.add(tmp6);
	}
}
