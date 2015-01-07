import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

// Java Communications API 3.0 : http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-misc-419423.html

public class SerialTool extends JFrame implements Runnable,ActionListener,SerialPortEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static CommPortIdentifier portID;
	static Enumeration<?> portList;
	SerialPort serialport;
	Thread read_thread;
	InputStream instream;
	OutputStream outstream;
	
	String port,str="";
	int baud,data,stop,parity;
	JPanel status = new StatPanel();
	JPanel receive = new RecPanel();
	JPanel send = new SenPanel();
	JPanel welcome = new JPanel();
	JTextArea wel = new JTextArea();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("java.library.path"));
//		System.loadLibrary("LinuxSerialParallel");
		new SerialTool();
	}

	class StatPanel extends StatusPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			
			if (s.equals("Link")){
				refresh();
				System.out.println(port);
				System.out.println(baud);
				System.out.println(data);
				System.out.println(stop);
				System.out.println(parity);
				portList = CommPortIdentifier.getPortIdentifiers();
				
				while (portList.hasMoreElements()){
					portID = (CommPortIdentifier)portList.nextElement();
					if (portID.getPortType() == CommPortIdentifier.PORT_SERIAL){
						if (portID.getName().equals(port)){
							try{
								serialport = (SerialPort)portID.open("ReadComm", 2000);
							}
							catch (PortInUseException ee) {}
							
							try{
								outstream = serialport.getOutputStream();
							}
							catch (IOException ee) {}
							
							try{
								serialport.addEventListener(SerialTool.this);
							}
							catch (TooManyListenersException ee){}
							
							serialport.notifyOnDataAvailable(true);
						}
					}
				}
				String message = "The port " + port + " has been openned successfully.";
				wel.setText(message);
			}
			else if (s.equals("Break")){
				serialport.close();
				String message = "The port " + port + " has been closed successfully.";
				wel.setText(message);
			}
		}
	}
	
	class RecPanel extends ReceivePanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) { // Clear
			// TODO Auto-generated method stub
			receive_text.setText("");
		}
		
	}

	class SenPanel extends SendPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) { // Send
			// TODO Auto-generated method stub
			try{
				System.out.println(baud);
				System.out.println(data);
				System.out.println(stop);
				System.out.println(parity);
				serialport.setSerialPortParams(baud, data, stop, parity);
			}
			catch (UnsupportedCommOperationException ee){}
			
			byte senddata[] = new byte[30];
			String str = jtsend.getText();
			char ch;
			int t=0;
			boolean pair = true;
			for (int i=0;i<str.length();i++){
				ch = str.charAt(i);
				if ((ch>='0') && (ch<='9')){
					if (!pair) senddata[t]*=16;
					senddata[t] += (byte)(ch-'0');
					pair = !pair;
				} else if ((ch>='A') && (ch<='F')){
					if (!pair) senddata[t]*=16;
					senddata[t] += (byte)(ch-'A'+10);
					pair = !pair;
				} else continue;
				
				if (pair) t++;
			}
			senddata[t] = '\0';
			
			try{
				outstream.write(senddata, 0, t);
			}
			catch (IOException ee){}
			String message = "The data has been sent successfully.";
			wel.setText(message);
		}
		
	}
	
	public SerialTool() {
		// TODO Auto-generated constructor stub
		super("SerialTool");
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		wel.setText("Welcome to use SerialTool!");
		wel.setEditable(false);
		welcome.add(wel);
		
		// How to use BorderFactory: http://wenku.baidu.com/link?url=hSJl_oC6MsPxvbIBFg5IMu4q6eut81wgsoEXqNKqqOG5bS2o2xVuxNConF5bsFp7tmc41BqLAMq9TqtWBy9_c48HbVyD-5DXJ7LKf_xFSUi
		Border border = BorderFactory.createEtchedBorder(Color.black,Color.gray);
		Border b1 = BorderFactory.createTitledBorder(border,"Com Status",TitledBorder.LEFT,TitledBorder.TOP);
		Border b2 = BorderFactory.createTitledBorder(border,"Receive",TitledBorder.LEFT,TitledBorder.TOP);
		Border b3 = BorderFactory.createTitledBorder(border,"Send",TitledBorder.LEFT,TitledBorder.TOP);
		
		status.setBorder(b1);
		receive.setBorder(b2);
		send.setBorder(b3);
		
		status.setPreferredSize(new Dimension(500,100));
		receive.setPreferredSize(new Dimension(500,300));
		send.setPreferredSize(new Dimension(500,90));
		welcome.setPreferredSize(new Dimension(500,30));
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
//		setLayout(new GridLayout(4,1));
		
		add(status);
		add(receive);
		add(send);
		add(welcome);
		
		port="COM1";
		baud = 4800;
		data=SerialPort.DATABITS_8;
		stop=SerialPort.STOPBITS_1;
		parity=SerialPort.PARITY_NONE;
		
		read_thread = new Thread(this);
		read_thread.start();
		
		setVisible(true);
	}
	
	public void refresh(){
		port = StatusPanel.getport();
		baud = StatusPanel.getbaud();
		data = StatusPanel.getdata();
		stop = StatusPanel.getstop();
		parity = StatusPanel.getparity();
	}
	
	@Override
	public void serialEvent(SerialPortEvent paramSerialPortEvent) {
		// TODO Auto-generated method stub
		try{
			serialport.setSerialPortParams(baud, data, stop, parity);
		}
		catch (UnsupportedCommOperationException e){}
		
		byte [] readBuf = new byte[1000];
		try{
			instream = serialport.getInputStream();
		}
		catch (IOException e){}
		
		int len = 0;
		String str;
		try{
			while (instream.available()>0){
				len = instream.read(readBuf);
			}
			
			str = new String(readBuf);
			String hex = "";
			for (int i=0;i<len;i++){
				hex = Integer.toHexString(readBuf[i] & 0xff);
				if (hex.length()==1) hex = '0' + hex;
				if ((i+1)%23!=0) str+=hex.toUpperCase()+" ";
				else str+=hex.toUpperCase()+"\n";
			}
			System.out.println(str);
			ReceivePanel.receive_text.append(str+"\n");
		}
		catch (IOException e){}
		
		String message = "Data received successfully.";
		wel.setText(message);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(20000);
		}
		catch(InterruptedException e){}
	}

}
