package spareChange.views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblSpareChangeLabel;
	private JTextField textUsername;
	private JTextField textPassword;
	private JButton btnLogin;
	
	public Login() {
		setTitle("Spare Change - Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png")));
		initComponents();
	}

	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 128));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), new Color(128, 0, 0)));
		setContentPane(contentPane);
		
        // -------------------------
        // LOGO
        // -------------------------
        lblSpareChangeLabel = new JLabel();
        lblSpareChangeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalIcon = new ImageIcon(
                WelcomeScreen.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png"));

        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        lblSpareChangeLabel.setIcon(new ImageIcon(scaledImage));
		
        // -------------------------
        // TEXT FEILDS
        // -------------------------
		textUsername = new JTextField();
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		
        // -------------------------
        // LABELS
        // -------------------------
		JLabel lblUsername = new JLabel("Username:");
		
		JLabel lblPassword = new JLabel("Password");
		
        // -------------------------
        // LOGIN BUTTON
        // -------------------------
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textUsername.getText().trim();
				String pass = textPassword.getText().trim();
				
				if(checkCredentials(user, pass))
				{
					JOptionPane.showMessageDialog(Login.this, "Login successful!");
					new MainPage(user).setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(Login.this, "Invalid username or password.");
				}
			}
		});
		btnLogin.setBackground(new Color(255, 0, 0));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(178)
							.addComponent(lblSpareChangeLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(118)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(140)
									.addComponent(btnLogin))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblUsername))
									.addGap(4)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
										.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(68, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(40)
					.addComponent(lblSpareChangeLabel)
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblPassword))
						.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addComponent(btnLogin)
					.addGap(89))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private boolean checkCredentials(String user, String pass) {
		try(BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt")))
		{
			String line;
			
			while((line = reader.readLine()) != null)
			{
				String[] parts = line.split(",");
				
				if(parts.length == 2)
				{
					String savedUser = parts[0];
					String savedPass = parts[1];
					
					if(savedUser.equals(user) && savedPass.equals(pass))
					{
						return true;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
