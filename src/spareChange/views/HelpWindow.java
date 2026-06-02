package spareChange.views;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;

public class HelpWindow extends JFrame{

	public HelpWindow() {
		setTitle("About Spare Change");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel label = new JLabel("<html><center><h3>Spare Change</h3>Version 1.1<br>Created by Ashley</center></html?", SwingConstants.CENTER);
		
		label.setOpaque(true);
		label.setBackground(new Color(255, 255, 200));
		
		add(label, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
