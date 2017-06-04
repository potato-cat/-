/**
 * 
 */
package cn.potatocat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.potatocat.view.MainPanel;
import gnu.io.*;

/**
 * @author Tom
 * @version v1.0
 * data : 2017.05.12
 */
public class SerialDsp implements SerialPortEventListener {

	private static SerialDsp serialDsp;
	private Enumeration<CommPortIdentifier> portList;
	private ArrayList<AvailablePort> serialPortList;
	private AvailablePort[] serialPorts = new AvailablePort[0];
	private JLabel label;
	private JButton button;
	private JLabel helpLabel;
	private SerialPort usingSerialPort;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	
	private SerialDsp() {
		portList = CommPortIdentifier.getPortIdentifiers();
		serialPortList = new ArrayList<>();
		while(portList.hasMoreElements()) {
			CommPortIdentifier temp = portList.nextElement();
			if(temp.getPortType() == CommPortIdentifier.PORT_SERIAL)
				serialPortList.add(new AvailablePort(temp));
		}
		serialPorts = serialPortList.toArray(serialPorts);
	}
	
	public AvailablePort[] getAvailablePorts() {
		return serialPorts;
	}
	
	public static SerialDsp getSerialDsp() {
		if(serialDsp == null) 
			serialDsp = new SerialDsp();
		return serialDsp;
	}
	
	public int connect(AvailablePort port, MainPanel panel) {
		try {
			usingSerialPort = (SerialPort) port.port.open("dsp", 0);
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		this.label = (JLabel)panel.numberLabel;
		this.button = panel.record;
		this.helpLabel = panel.help;
		try {
			usingSerialPort.addEventListener(this);
			usingSerialPort.notifyOnDataAvailable(true);
			usingSerialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			printWriter = new PrintWriter(usingSerialPort.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(usingSerialPort.getInputStream()));
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 4;
		}
		return 0;
	}
	
	public void sendMsg(String str){
		printWriter.print(str);
		printWriter.flush();
	}
	@Override
	public void serialEvent(SerialPortEvent e) {
		// TODO Auto-generated method stub
		StringBuffer temp = new StringBuffer();
		switch(e.getEventType()){
			case SerialPortEvent.BI:
				break;
			case SerialPortEvent.CD:
				break;
			case SerialPortEvent.CTS:
				break;
			case SerialPortEvent.DSR:
				break;
			case SerialPortEvent.FE:
				break;
			case SerialPortEvent.OE:
				break;
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.PE:
				break;
			case SerialPortEvent.RI:
				break;
			case SerialPortEvent.DATA_AVAILABLE:
				char[] c = new char[10];
			try {
					while(bufferedReader.read(c, 0, 10) != -1) {
						temp.append(new String(c).trim());
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String str = temp.toString();
				System.out.println(str);
				temp.delete(0, temp.length());
				if(str.equals("ERROR"))
					helpLabel.setText("无法识别！尝试重新训练");
				else if(str.equals("OK"))
					helpLabel.setText("训练完成！");
				else{
					helpLabel.setText("识别成功！");
					label.setText(str);
				}
				
				button.setEnabled(true);
				break;
		}
	}
	
	public class AvailablePort {

		CommPortIdentifier port;
		
		public AvailablePort(CommPortIdentifier port) {
			this.port = port;
		}
		
		@Override
		public String toString() {
			return port.getName();
		}
		
		
	}
	
}
