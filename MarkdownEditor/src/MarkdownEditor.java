import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.html.HTMLEditorKit;

import com.petebevin.markdown.MarkdownProcessor;


public class MarkdownEditor extends JFrame implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTextArea inputarea = new JTextArea();
	static JEditorPane htmlpane = new JEditorPane();
	
	public MarkdownEditor(){
		super("Markdown Editor");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridLayout(1, 2));
		inputarea.setLineWrap(true);
		inputarea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String tmp = inputarea.getText();
				String mark = new MarkdownProcessor().markdown(tmp);
				System.out.println(mark);
				htmlpane.setText(mark);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		HTMLEditorKit html_kit = new HTMLEditorKit();
		htmlpane.setEditorKit(html_kit);
		htmlpane.setContentType("text/html");
		htmlpane.setEditable(false);
		
		JScrollPane js1 = new JScrollPane(inputarea);
		JScrollPane js2 = new JScrollPane(htmlpane);
		add(js1);
		add(js2);
		setVisible(true);
	}
	
	public static void main(String args[]){
		new MarkdownEditor();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
}
