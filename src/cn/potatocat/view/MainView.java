/**
 * 
 */
package cn.potatocat.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * @author Tom
 * @version v1.0
 * date : 2017.05.10
 */
public class MainView {

	/**
	 * @param args
	 */
	JFrame mainFrame;
	JPanel mainPanel;
	
	public MainView() {
		mainFrame = new JFrame("孤立词语音识别上位机");
		ImageIcon img = new ImageIcon(this.getClass().getResource("../image/mic2.png"));
		mainFrame.setIconImage(img.getImage());
		mainFrame.setBounds(100, 100, 300, 360);
		mainFrame.setResizable(false);
		mainPanel = MainPanel.getMainPanel();
		mainFrame.add(mainPanel);
		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		mainFrame.setVisible(true);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainView();
			}
		});
	}

}
