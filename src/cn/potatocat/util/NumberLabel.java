/**
 * 
 */
package cn.potatocat.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

/**
 * @author Tom
 * @version v1.0
 * data : 2017.05.11
 */
public class NumberLabel extends JLabel {
	
	private String number = "";
	
	public NumberLabel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		System.out.println("test!");
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.setFont(new Font("Î¢ÈíÑÅºÚ Bold", Font.BOLD, 120));
		g.drawString(number, 12, 95);
	}
	
	@Override
	public void setText(String s) {
		// TODO Auto-generated method stub
		number = s;
		repaint();
	}


	
	
	
	
	
}
