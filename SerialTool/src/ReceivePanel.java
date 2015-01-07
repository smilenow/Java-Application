import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class ReceivePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JTextArea receive_text = new JTextArea();
	
	public ReceivePanel(){
		this.setLayout(new BorderLayout());
		
		JPanel tmp1 = new JPanel();
		tmp1.setLayout(new FlowLayout());
		JButton jb_clear = new JButton("Clear");
		jb_clear.addActionListener(this);
		tmp1.add(jb_clear);
		this.add(tmp1,"South");
		
		JPanel tmp2 = new JPanel();
		tmp2.setLayout(new BorderLayout());
		
		receive_text.setLineWrap(true); // auto newline
		receive_text.setEditable(false);
		JScrollPane js = new JScrollPane(receive_text);
		tmp2.add(js);
		this.add(tmp2,"Center");
	}
	
}
