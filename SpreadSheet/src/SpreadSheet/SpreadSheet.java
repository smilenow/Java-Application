package SpreadSheet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SpreadSheet extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JTable table = null;
	
	public SpreadSheet(){
		super("SpreadSheet");
		setSize(800,600);
		setLocation(200, 100);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JButton jb1 = new JButton("Open");
		JButton jb2 = new JButton("Save");
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				int ret = jfc.showOpenDialog(jfc);
				if (ret!=JFileChooser.APPROVE_OPTION) return;
				File file = jfc.getSelectedFile();
				TableModel tmodel = getFileModel(file);
				table.setModel(tmodel);
				
				DefaultTableCellRenderer render = new DefaultTableCellRenderer();
				render.setHorizontalAlignment(SwingConstants.CENTER);
				
				int cnt = table.getColumnCount();
				String col;
				for (int i=0;i<cnt;i++){
					col = table.getColumnName(i);
					table.getColumn(col).setCellRenderer(render);
				}
				table.revalidate();
			}
		});
		
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				int ret = jfc.showSaveDialog(jfc);
				if (ret!=JFileChooser.APPROVE_OPTION) return;
				File file = jfc.getSelectedFile();
				saveFile(file);
				table.revalidate();
			}
		});
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		jpanel.add(jb1); jpanel.add(jb2);
		
		add(jpanel,BorderLayout.SOUTH);
		
		table = new JTable();
		JScrollPane jsp = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(jsp,BorderLayout.CENTER);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SpreadSheet();
	}
	
	private static TableModel getFileModel(File file){
		DefaultTableModel ret = new DefaultTableModel();
		Object[] obj = null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String data = reader.readLine();
			obj = data.split(",");
			ret.setColumnIdentifiers(obj);
			while ((data=reader.readLine())!=null){
				obj = data.split(",");
				ret.addRow(obj);
			}
			reader.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	private static void saveFile(File file){
		try{
			FileWriter fw = new FileWriter(file);
			
			int n = table.getColumnCount();
			int m = table.getRowCount();
			
			String col;
			for (int i=0;i<n;i++){
				col = table.getColumnName(i);
				fw.write(col);
				if (i!=n-1) fw.write(",");
				else fw.write("\n");
			}
			for (int i=0;i<m;i++)
				for (int j=0;j<n;j++){
					fw.write(table.getValueAt(i, j).toString());
					if (j!=n-1) fw.write(",");
					else fw.write("\n");
				}
			fw.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
