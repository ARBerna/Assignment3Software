package spareChange.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomeScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public WelcomeScreen() {
        initComponents();
    }

    private void initComponents()
    {

        setTitle("Spare Change");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeScreen.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window on screen

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 128));
        contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), new Color(128, 0, 0)));
        setContentPane(contentPane);

        // -------------------------
        // LOGO
        // -------------------------
        JLabel lblSpareChangeLabel = new JLabel();
        lblSpareChangeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalIcon = new ImageIcon(
                WelcomeScreen.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png"));

        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        lblSpareChangeLabel.setIcon(new ImageIcon(scaledImage));

        // -------------------------
        // BUTTON PANEL
        // -------------------------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // match background

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Login().setVisible(true);
        		dispose();
        	}
        });
        btnLogin.setBackground(Color.RED);
        btnLogin.setForeground(Color.BLACK);

        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new CreateAccount().setVisible(true);//open up new window CreateAccount
        		dispose();//close WelcomeScreen window
        	}
        });
        btnCreateAccount.setBackground(Color.RED);
        btnCreateAccount.setForeground(Color.BLACK);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCreateAccount);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblSpareChangeLabel, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        		.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(lblSpareChangeLabel, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        			.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }

    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen frame = new WelcomeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
