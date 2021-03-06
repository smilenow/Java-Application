package miniCAD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;


// MouseListener,MouseMotionListener reference:
// http://www.tuicool.com/articles/FrAF3m

public class miniCAD extends JFrame implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static int _width = 800;
	static int _height = 600;
	static shape cur = new shape();
	static int flag = 0;
	static ArrayList<shape> drawlist = new ArrayList<shape>();
	static BufferedImage bi = new BufferedImage(_width, _height, BufferedImage.TYPE_3BYTE_BGR);
	JMenuBar jmenubar = new JMenuBar();
	
	public miniCAD(){
		super("miniCAD -- SmilENow");
		 
		Graphics g = bi.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, _width, _height);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setJMenuBar(jmenubar);
		
		JMenu jm_file = new JMenu("File");
		JMenu jm_tools = new JMenu("Tools");
		JMenu jm_color = new JMenu("Color");
		JMenu jm_help = new JMenu("Help");
		jmenubar.add(jm_file);
		jmenubar.add(jm_tools);
		jmenubar.add(jm_color);
		jmenubar.add(jm_help);
		
		JMenuItem jm_file_open = new JMenuItem("Open");
		JMenuItem jm_file_save = new JMenuItem("Save");
		JMenuItem jm_file_close = new JMenuItem("Close");
		jm_file.add(jm_file_open);
		jm_file.add(jm_file_save);
		jm_file.addSeparator();
		jm_file.add(jm_file_close);
		
		ButtonGroup bg_tools = new ButtonGroup();
		JRadioButtonMenuItem jm_tools_line = new JRadioButtonMenuItem("Line");
		JRadioButtonMenuItem jm_tools_circle = new JRadioButtonMenuItem("Circle");
		JRadioButtonMenuItem jm_tools_rect = new JRadioButtonMenuItem("Rectangle");
		JRadioButtonMenuItem jm_tools_text = new JRadioButtonMenuItem("Text");
		JRadioButtonMenuItem jm_tools_move = new JRadioButtonMenuItem("Move");
		jm_tools.add(jm_tools_line);	bg_tools.add(jm_tools_line);
		jm_tools.add(jm_tools_circle);	bg_tools.add(jm_tools_circle);
		jm_tools.add(jm_tools_rect);	bg_tools.add(jm_tools_rect);
		jm_tools.add(jm_tools_text);	bg_tools.add(jm_tools_text);
		jm_tools.addSeparator();
		jm_tools.add(jm_tools_move);	bg_tools.add(jm_tools_move);
		
		ButtonGroup bg_color = new ButtonGroup();
		JRadioButtonMenuItem jm_color_black = new JRadioButtonMenuItem("Black");
		JRadioButtonMenuItem jm_color_blue = new JRadioButtonMenuItem("Blue");
		JRadioButtonMenuItem jm_color_green = new JRadioButtonMenuItem("Green");
		JRadioButtonMenuItem jm_color_red = new JRadioButtonMenuItem("Red");
		JRadioButtonMenuItem jm_color_yellow = new JRadioButtonMenuItem("Yellow");
		jm_color.add(jm_color_black);	bg_color.add(jm_color_black);
		jm_color.add(jm_color_blue);	bg_color.add(jm_color_blue);
		jm_color.add(jm_color_green);	bg_color.add(jm_color_green);
		jm_color.add(jm_color_red);		bg_color.add(jm_color_red);
		jm_color.add(jm_color_yellow);	bg_color.add(jm_color_yellow);
		
		JMenuItem jm_help_help = new JMenuItem("Help");
		jm_help.add(jm_help_help);
		
		this.setSize(_width, _height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// define action
		
		
		jm_file_open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				File curimagefile;
				Image curimage;
				
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int ret = jfc.showOpenDialog(jfc);
				if (ret != JFileChooser.APPROVE_OPTION) return;
				curimagefile = jfc.getSelectedFile();
				curimage = Toolkit.getDefaultToolkit().getImage(curimagefile.toString());
				int tmp_type = cur.type;
				cur.type = shape._image;
				cur.image = curimage;
				drawlist.add(new shape(cur));
				cur.type = tmp_type;
				
				drawit(curimage);
			}
		});
		jm_file_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				File curimagefile;
				JFileChooser jfc = new JFileChooser();
				int ret = jfc.showSaveDialog(jfc);
				if (ret != JFileChooser.APPROVE_OPTION) return;
				curimagefile = jfc.getSelectedFile();
				
				Graphics g = bi.getGraphics();
				Iterator<shape> it = drawlist.iterator();
				while (it.hasNext()) draw(g,it.next());
				
				try{
					ImageIO.write(bi, "jpg", curimagefile);
				} catch (IOException ex){
					ex.printStackTrace();
				}
			}
		});
		jm_file_close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		jm_tools_line.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flag = 0;
				cur.type = shape._line;
			}
		});
		jm_tools_circle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flag = 0;
				cur.type = shape._circle;
				
			}
		});
		jm_tools_rect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flag = 0;
				cur.type = shape._rectangle;
			}
		});
		jm_tools_text.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flag = 0;
				cur.type = shape._text;
				cur.text = JOptionPane.showInputDialog("Please input the text:");
			}
		});
		jm_tools_move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flag = 1;
			}
		});
		jm_color_black.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cur.color = shape._black;
			}
		});
		jm_color_blue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cur.color = shape._blue;
			}
		});
		jm_color_green.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cur.color = shape._green;
			}
		});
		jm_color_red.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cur.color = shape._red;
			}
		});
		jm_color_yellow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cur.color = shape._yellow;
			}
		});
		jm_help_help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Email to : troysmilenow@gmail.com");
			}
		});
	}
	
	public void drawit(Image ima){
		Graphics g = bi.getGraphics();
		g.drawImage(ima, 0, 0, _width, _height, this);
	}
	
	public void draw(Graphics g,shape s){
		switch (s.color){
		case shape._black : g.setColor(Color.black);	break;
		case shape._blue  : g.setColor(Color.blue);		break;
		case shape._green : g.setColor(Color.green);	break;
		case shape._red	  : g.setColor(Color.red);		break;
		case shape._yellow: g.setColor(Color.yellow);	break;
		}
		switch (s.type){
		case shape._line : g.drawLine(s.st_x, s.st_y, s.ed_x, s.ed_y); break;
		case shape._circle : g.drawOval(s.st_x, s.st_y, Math.abs(s.ed_x-s.st_x), Math.abs(s.ed_y-s.st_y)); break;
		case shape._rectangle : g.drawRect(s.st_x, s.st_y, s.ed_x-s.st_x, s.ed_y-s.st_y); break;
		case shape._text : 	g.setFont(new Font("Courier",Font.BOLD,30));
							g.drawString(s.text, s.st_x, s.st_y);
							break;
		case shape._image : g.drawImage(s.image, 0, 0, _width, _height, this);
		}
	}
	public static void main(String args[]){
		new miniCAD();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		cur.st_x = e.getX();
		cur.st_y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		cur.ed_x = e.getX();
		cur.ed_y = e.getY();
		if (flag==0) drawlist.add(new shape(cur));
		else if (flag==1){
			if (!drawlist.isEmpty()){
				shape needtomove = drawlist.remove(drawlist.size()-1);
				Graphics g = getGraphics();
				
				g.setColor(Color.white);
				switch (needtomove.type){
				case shape._line : g.drawLine(needtomove.st_x, needtomove.st_y, needtomove.ed_x, needtomove.ed_y); break;
				case shape._circle : g.drawOval(needtomove.st_x, needtomove.st_y, Math.abs(needtomove.ed_x-needtomove.st_x), Math.abs(needtomove.ed_y-needtomove.st_y)); break;
				case shape._rectangle : g.drawRect(needtomove.st_x, needtomove.st_y, needtomove.ed_x-needtomove.st_x, needtomove.ed_y-needtomove.st_y); break;
				case shape._text : 	g.setFont(new Font("Courier",Font.BOLD,30));
									g.drawString(needtomove.text, needtomove.st_x, needtomove.st_y);
									break;
				case shape._image : g.drawImage(needtomove.image, 0, 0, _width, _height, this);
				}
				
				needtomove.st_x += cur.ed_x - cur.st_x;
				needtomove.st_y += cur.ed_y - cur.st_y;
				needtomove.ed_x += cur.ed_x - cur.st_x;
				needtomove.ed_y += cur.ed_y - cur.st_y;
				drawlist.add(needtomove);
			}
		}
		repaint();
	}

	public void paint(Graphics g){
		Iterator<shape> it = drawlist.iterator();
		while (it.hasNext()) draw(g,it.next());
		this.setJMenuBar(jmenubar);
		this.setSize(_width, _height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
