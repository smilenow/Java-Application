import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public abstract class SendPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JTextArea jtsend = new JTextArea();
	
	public SendPanel(){
		this.setLayout(new BorderLayout());
		JPanel tmp1 = new JPanel(new FlowLayout());
		JLabel jl = new JLabel("Send Data: ");
		tmp1.add(jl);
		this.add(tmp1,"North");
		
		JPanel tmp2 = new JPanel(new FlowLayout());
		
		tmp2.add(jtsend);
		this.add(jtsend,"Center");
		
		JPanel tmp3 = new JPanel();
		JButton jbsend = new JButton("Send");
		jbsend.addActionListener(this);
		tmp3.add(jbsend);
		this.add(tmp3,"East");
	}
}
