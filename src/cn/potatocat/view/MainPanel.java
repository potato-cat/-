/**
 * 
 */
package cn.potatocat.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;

import cn.potatocat.util.NumberLabel;
import cn.potatocat.util.SerialDsp;
import cn.potatocat.util.SerialDsp.AvailablePort;

/**
 * @author Tom
 *
 */
public class MainPanel extends JPanel {

	private static MainPanel mainPanel;
	private JLabel label;
	public JLabel numberLabel;
	public JLabel help;
	public JButton record;
	private JButton connect;
	private JButton[] numberButton = new JButton[9];
	private JComboBox<AvailablePort> selector;
	SerialDsp serialDsp;
	private Timer timer = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	});
	private class NumberButtonListener implements ActionListener{
		private int i;
		public NumberButtonListener(int i) {
			// TODO Auto-generated constructor stub
			this.i = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(String.valueOf(i+1));
			serialDsp.sendMsg(String.valueOf(i+1));
		}
		
	}
	private MainPanel() {
		setBounds(0, 0, 300, 400);
		setLayout(null);
		timer.setRepeats(false);
		label = new JLabel("识别结果");
		label.setFont(new Font("华文彩云", Font.BOLD, 30));
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.TOP);
		label.setBounds(0, 0, 150, 30);
		add(label);
		serialDsp = SerialDsp.getSerialDsp();
		selector = new JComboBox<>(serialDsp.getAvailablePorts());
		selector.setBounds(160, 0, 70, 30);
		add(selector);
		connect = new JButton();
		connect.setFont(new Font("微软雅黑", Font.BOLD, 12));
		connect.setText("连接");
		connect.setContentAreaFilled(true);
		connect.setBorderPainted(false);
		connect.setBounds(240,0,60,30);
		add(connect);
		numberLabel = new NumberLabel(100, 60, 100, 100);
		add(numberLabel);
		record = new JButton();
		record.setFont(new Font("华文彩云", Font.BOLD, 25));
		record.setBackground(Color.YELLOW);
		record.setForeground(Color.RED);
		record.setIcon(new ImageIcon(this.getClass().getResource("../image/btn_recognize.png")));
		record.setBorder(null);
		record.setContentAreaFilled(false);
		record.setFocusPainted(false);
		record.setBounds(20, 180, 100, 100);
		add(record);
		for(int i = 0;i < 9;i++) {
			numberButton[i] = new JButton(String.valueOf(i+1));
			numberButton[i].setFont(new Font("宋体", Font.BOLD, 10));
			numberButton[i].setBackground(Color.YELLOW);
			numberButton[i].setForeground(Color.RED);
			numberButton[i].setBorder(null);
			numberButton[i].setContentAreaFilled(true);
			numberButton[i].setFocusPainted(false);
			numberButton[i].setBounds(140+(i%3)*50, 180+40*(i/3), 40, 30);
			add(numberButton[i]);
			numberButton[i].addActionListener(new NumberButtonListener(i));
			numberButton[i].addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					help.setText("按下F1显示帮助信息");
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					help.setText("单击开始训练！");
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		help = new JLabel("按下F1显示帮助信息");
		help.setBounds(0,300,300,35);
		help.setBorder(BorderFactory.createEtchedBorder());
		add(help);
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AvailablePort port = (AvailablePort) selector.getSelectedItem();
				serialDsp.connect(port, MainPanel.this);
			}
		});
		record.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				serialDsp.sendMsg("\n");
				timer.start();
				record.setEnabled(false);
				numberLabel.setText("");
			}
		});
	}
	
	
	public static JPanel getMainPanel() {
		if(mainPanel == null)
			mainPanel = new MainPanel();
		return mainPanel;
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}
	
}
