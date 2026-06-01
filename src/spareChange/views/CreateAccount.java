package spareChange.views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class CreateAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsername;
	private JTextField textPassword;
	private JLabel lblSpareChangeLabel;
	private JButton btnCreateAccount;

	public CreateAccount() {
		initComponents();
	}
	
	private void initComponents()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateAccount.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png")));
		setTitle("Spare Change - Create Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 128));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), new Color(128, 0, 0)));
		setContentPane(contentPane);
		
        // -------------------------
        // TEXT FIELDS
        // -------------------------
		textUsername = new JTextField();
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		
        // -------------------------
        // LABELS FOR TEXT FIELDS
        // -------------------------
		JLabel lblPassword = new JLabel("Password:");
		
		JLabel lblUsername = new JLabel("Username:");
		
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
        // BUTTON
        // -------------------------
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textUsername.getText().trim();
				String pass = textPassword.getText().trim();
				
				//Validate username and password:
				if(!isValidUsername(user))
				{
			        JOptionPane.showMessageDialog(CreateAccount.this, "Invalid username. Must be 6–8 characters and contain one capital letter.");
			            return;
				}
				
				if(!isValidPassword(pass))
				{
			        JOptionPane.showMessageDialog(CreateAccount.this, "Invalid password. Must be 8–12 characters, 1 capital letter, and 1 special character.");
			            return;
				}
				
				if(usernameExists(user))
				{
					JOptionPane.showMessageDialog(CreateAccount.this, "Username already exists. Choose another one.");
					return;
				}
				
				try(FileWriter writer = new FileWriter("Accounts.txt", true))
				{
					writer.write(textUsername.getText() + "," + textPassword.getText() + "\n");
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
				new WelcomeScreen().setVisible(true);
				dispose();
			}
		});
		btnCreateAccount.setBackground(new Color(255, 0, 0));
		
        // -------------------------
        // INSTRUCTIONS (PANEL AND TEXT)
        // -------------------------
		JPanel panelInstructions = new JPanel();
		panelInstructions.setBackground(new Color(255, 255, 128));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(183)
							.addComponent(lblSpareChangeLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(96)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(134)
									.addComponent(btnCreateAccount))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblUsername)
										.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textPassword)
										.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panelInstructions, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)))))
					.addGap(466))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(38)
					.addComponent(lblSpareChangeLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblUsername)
							.addGap(18)
							.addComponent(lblPassword))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(btnCreateAccount)
					.addGap(18)
					.addComponent(panelInstructions, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(88, Short.MAX_VALUE))
		);
		panelInstructions.setLayout(new BoxLayout(panelInstructions, BoxLayout.X_AXIS));
		
		JTextPane txtpnInstructions = new JTextPane();
		panelInstructions.add(txtpnInstructions);
		txtpnInstructions.setBackground(new Color(255, 255, 0));
		txtpnInstructions.setText("Username must include 6-8 characters, and 1 capitol letter. Password must contain 8-12 characters, 1 capitol letter, and 1 special character.");
		contentPane.setLayout(gl_contentPane);
	}
	
	private boolean isValidUsername(String user) {
	    return user.matches("^[A-Za-z0-9!@#$%^&*]{6,8}$");//use regex to validate
	}
	
	private boolean isValidPassword(String pass) {
	    return pass.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,12}$");//use regex to validate
	}
	
	private boolean usernameExists(String user) {
		try(BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt")))
		{
			String line;
			while((line = reader.readLine()) != null)
			{
				String savedUser = line.split(",")[0];
				if(savedUser.equalsIgnoreCase(user))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return false;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount frame = new CreateAccount();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
