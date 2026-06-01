package spareChange.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MainPage extends JFrame {
	
	private String username;
	
	private JButton btnMainMenu;
	private JButton btnCalculateFunds;
	private JButton btnSavingsGoal;
	private JButton btnLogout;
	
	//Pie chart variables
	private int lastBills = 0;
	private int lastSpending = 0;
	private int lastSavings = 0;
	
	private int lastPlanBills = 0;
	private int lastPlanSpending = 0;
	private int lastPlanSavings = 0;
	
	private PieChart userChart;
	private PieChart planChart;
	
	private JPanel userChartPanel;
	private JPanel planChartPanel;
	
	private JPanel legendPanelUser;
	private JPanel legendPanelPlan;
	
	private JTextField txtBills, txtSpending, txtSavings;
	private JTextField txtIncome, txtBillsPlan;
	
	private JButton btnUpdateCurrent, btnUpdatePlan;
	
	private String[] userLabels = {"Bills", "Spending", "Savings"};
	private String[] planLabels = {"Bills", "Spending", "Savings"};
	
	private Color[] userColors = {Color.BLUE, Color.GREEN, Color.MAGENTA};
	private Color[] planColors = {Color.BLUE, Color.GREEN, Color.MAGENTA};
	
	//savings variables
	private int savingsGoal = 0;
	private int currentSaved = 0;
	
	private JProgressBar savingsProgressBar;
	private JTextField txtGoalInput;
	private JTextField txtSavingsChange;
	
	private JButton btnSetGoal;
	private JButton btnUpdateSavings;
	
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // CardLayout components
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainPage(String username) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                MainPage.class.getResource("/spareChange/resources/Spare Change logo-Photoroom.png")));
        setTitle("Spare Change");

        this.username = username;
        initComponents();
        createEvents();
    }

    public void initComponents() {
    	loadSavingsData();
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 128));
        contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), new Color(128, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // -------------------------
        // TOP MENU BAR
        // -------------------------
        JPanel panelMenuPanel = new JPanel();
        panelMenuPanel.setBackground(new Color(251, 236, 51));
        panelMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        contentPane.add(panelMenuPanel, BorderLayout.NORTH);

        btnMainMenu = new JButton("Main Menu");
        btnCalculateFunds = new JButton("Calculate Funds");
        btnSavingsGoal = new JButton("Savings Goal");
        btnLogout = new JButton("Logout");

        for (JButton b : new JButton[]{btnMainMenu, btnCalculateFunds, btnSavingsGoal, btnLogout}) {
            b.setForeground(Color.WHITE);
            b.setBackground(Color.RED);
            panelMenuPanel.add(b);
        }

        // -------------------------
        // CARD LAYOUT PANEL
        // -------------------------
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        contentPane.add(cardPanel, BorderLayout.CENTER);
        
        // -------------------------
        // UPDATE VALUES PAGE
        // -------------------------
        JPanel updatePage = new JPanel(new GridLayout(1, 2, 20, 0));
        updatePage.setBackground(new Color(255, 255, 128));
        
        JPanel currentInputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        currentInputPanel.setBackground(new Color(255, 255, 128));
        
        JLabel lblBills = new JLabel("Bills:");
        txtBills = new JTextField();
        
        JLabel lblSpending = new JLabel("Spending:");
        txtSpending = new JTextField();
        
        JLabel lblSavings = new JLabel("Savings:");
        txtSavings = new JTextField();
        
        btnUpdateCurrent = new JButton("Update Funds");
        
        currentInputPanel.add(lblBills);
        currentInputPanel.add(txtBills);
        currentInputPanel.add(lblSpending);
        currentInputPanel.add(txtSpending);
        currentInputPanel.add(lblSavings);
        currentInputPanel.add(txtSavings);
        currentInputPanel.add(new JLabel());
        currentInputPanel.add(btnUpdateCurrent);
        
        updatePage.add(currentInputPanel);//add to the left side
        
        JPanel planInputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        planInputPanel.setBackground(new Color(255, 255, 128));
        
        JLabel lblIncome = new JLabel("Income:");
        txtIncome = new JTextField();
        
        JLabel lblBillsPlan = new JLabel("Bills:");
        txtBillsPlan = new JTextField();
        
        btnUpdatePlan = new JButton("Update Plan");
        
        planInputPanel.add(lblIncome);
        planInputPanel.add(txtIncome);
        planInputPanel.add(lblBillsPlan);
        planInputPanel.add(txtBillsPlan);
        planInputPanel.add(new JLabel());
        planInputPanel.add(btnUpdatePlan);
        
        updatePage.add(planInputPanel);
        
        cardPanel.add(updatePage, "update");
        

        // -------------------------
        // DASHBOARD (Pie Charts)
        // -------------------------
        JPanel dashboardPage = new JPanel(new BorderLayout());
        dashboardPage.setBackground(new Color(255, 255, 128));

        //Pie chart container
        JPanel panelPieCharts = new JPanel(new GridLayout(1, 2, 20, 0));
        panelPieCharts.setBackground(new Color(251, 236, 51));
        dashboardPage.add(panelPieCharts, BorderLayout.CENTER);

        //data
        loadChartData();
        
        int[] userValues = { lastBills, lastSpending, lastSavings };
        int[] planValues = { lastPlanBills, lastPlanSpending, lastPlanSavings };

        //create charts
        userChart = new PieChart("Your Spending", userValues, userColors);
        planChart = new PieChart("Your Plan", planValues, planColors);

        //wrapper panels
        userChartPanel = new JPanel(new BorderLayout());
        userChartPanel.setBackground(new Color(255, 255, 128));
        planChartPanel = new JPanel(new BorderLayout());
        planChartPanel.setBackground(new Color(255, 255, 128));

        //add charts
        userChartPanel.add(userChart, BorderLayout.CENTER);
        planChartPanel.add(planChart, BorderLayout.CENTER);

        //add legends
        legendPanelUser = createLegend(userLabels, userValues, userColors);
        userChartPanel.add(legendPanelUser, BorderLayout.SOUTH);

        legendPanelPlan = createLegend(planLabels, planValues, planColors);
        planChartPanel.add(legendPanelPlan, BorderLayout.SOUTH);
        
        //add to pie chart container
        panelPieCharts.add(userChartPanel);
        panelPieCharts.add(planChartPanel);

        //add dashboard page to card layout
        cardPanel.add(dashboardPage, "dashboard");

        // -------------------------
        // CALCULATE FUNDS
        // -------------------------
        JPanel calculatePage = new JPanel();
        calculatePage.setBackground(new Color(255, 255, 128));
        calculatePage.add(new JLabel("Calculate Funds Page"));
        cardPanel.add(calculatePage, "calculate");

        // -------------------------
        // SAVINGS GOAL
        // -------------------------
        JPanel savingsWrapper = new JPanel(new BorderLayout());
        savingsWrapper.setBackground(new Color(255, 255, 128));

        JPanel savingsPage = new JPanel();
        savingsPage.setBackground(new Color(255, 255, 128));
        savingsPage.setLayout(new BoxLayout(savingsPage, BoxLayout.Y_AXIS));

        //set goal row
        JPanel goalRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        goalRow.setBackground(new Color(255, 255, 128));

        goalRow.add(new JLabel("Savings Goal:"));

        txtGoalInput = new JTextField(8);
        goalRow.add(txtGoalInput);

        btnSetGoal = new JButton("Set Goal");
        goalRow.add(btnSetGoal);

        savingsPage.add(goalRow);

        //progress bar
        JPanel progressRow = new JPanel(new BorderLayout());
        progressRow.setBackground(new Color(255, 255, 128));

        JLabel lblProgress = new JLabel("Progress:", SwingConstants.CENTER);
        progressRow.add(lblProgress, BorderLayout.NORTH);

        savingsProgressBar = new JProgressBar(0, 100);
       	savingsProgressBar.setPreferredSize(new Dimension(300, 25));
       	progressRow.add(savingsProgressBar, BorderLayout.CENTER);

       	savingsPage.add(progressRow);

       	//add and subtract row
       	JPanel changeRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
       	changeRow.setBackground(new Color(255, 255, 128));
	
       	changeRow.add(new JLabel("Change Savings:"));

       	txtSavingsChange = new JTextField(8);
       	changeRow.add(txtSavingsChange);

       	btnUpdateSavings = new JButton("Update");
		changeRow.add(btnUpdateSavings);

		savingsPage.add(changeRow);

		//add to wrapper
		savingsWrapper.add(savingsPage, BorderLayout.NORTH);
		 
		//add to card layout
		cardPanel.add(savingsWrapper, "savings");
	 
		cardLayout.show(cardPanel,  "dashboard");

		updateProgressBar();
    }

    public void createEvents() {
        // -------------------------
        // MENU BUTTONS
        // -------------------------
    	JPanel menuPanel = (JPanel) contentPane.getComponent(0);
    	
    	JButton btnMainMenu = (JButton) menuPanel.getComponent(0);
    	JButton btnCalculateFunds = (JButton) menuPanel.getComponent(1);
    	JButton btnSavingsGoal = (JButton) menuPanel.getComponent(2);
    	
    	btnMainMenu.addActionListener(e -> cardLayout.show(cardPanel,  "dashboard"));
    	btnCalculateFunds.addActionListener(e -> cardLayout.show(cardPanel,  "update"));
    	btnSavingsGoal.addActionListener(e -> cardLayout.show(cardPanel,  "savings"));
    	
    	// ------------------------
    	// UPDATE CURRENT SPENDING BUTTON
    	// ------------------------
    	btnUpdateCurrent.addActionListener(e -> {
    	    lastBills = Integer.parseInt(txtBills.getText());
    	    lastSpending = Integer.parseInt(txtSpending.getText());
    	    lastSavings = Integer.parseInt(txtSavings.getText());

    	    int[] newValues = {lastBills, lastSpending, lastSavings};
    	    userChart.updateValues(newValues);

    	    userChartPanel.remove(legendPanelUser);
    	    legendPanelUser = createLegend(userLabels, newValues, userColors);
    	    userChartPanel.add(legendPanelUser, BorderLayout.SOUTH);

    	    userChartPanel.revalidate();
    	    userChartPanel.repaint();

    	    saveChartData();

    	    cardLayout.show(cardPanel, "dashboard");
    	});


    	// -----------------------
    	// UPDATE RECOMMENDED PLAN BUTTON
    	// -----------------------
    	btnUpdatePlan.addActionListener(e -> {

    	    lastPlanBills = Integer.parseInt(txtBillsPlan.getText());
    	    int income = Integer.parseInt(txtIncome.getText());
    	    
    	    int remaining = income - lastPlanBills;
    	    
    	    if(remaining < 0)
    	    {
    	    	remaining = 0;
    	    }

    	    int recommendedSavings = remaining / 2;
    	    int recommendedSpending = remaining - recommendedSavings;
    	    
    	    lastPlanSavings = recommendedSavings;
    	    lastPlanSpending = recommendedSpending;

    	    int[] newPlanValues = {lastPlanBills, recommendedSpending, recommendedSavings};
    	    planChart.updateValues(newPlanValues);
    	    
    	    planChartPanel.remove(legendPanelPlan);
    	    legendPanelPlan = createLegend(planLabels, newPlanValues, planColors);
    	    planChartPanel.add(legendPanelPlan, BorderLayout.SOUTH);
    	    
    	    planChartPanel.revalidate();
    	    planChartPanel.repaint();
    	    
    	    saveChartData();

    	    cardLayout.show(cardPanel, "dashboard");
    	});
    	
    	// -----------------------
    	// UPDATE SAVINGS GOAL
    	// -----------------------
    	btnSetGoal.addActionListener(e -> {
    		savingsGoal = Integer.parseInt(txtGoalInput.getText());
    		saveSavingsData();
    		updateProgressBar();
    	});
    	
    	btnUpdateSavings.addActionListener(e -> {
    		int change = Integer.parseInt(txtSavingsChange.getText());
    		currentSaved += change;
    		
    		if(currentSaved < 0)
    		{
    			currentSaved = 0;
    		}
    		
    		saveSavingsData();
    		updateProgressBar();
    	});
    	
    	// -----------------------
    	// LOGOUT
    	// -----------------------
    	btnLogout.addActionListener(e -> {
    		new WelcomeScreen().setVisible(true);
    		dispose();
    	});
    }
    
    private String getChartFileName() {
    	return username + "_Charts.txt";
    }
    
    private void loadChartData() {
        File file = new File(getChartFileName());
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file))
        {
            lastBills = sc.nextInt();
            lastSpending = sc.nextInt();
            lastSavings = sc.nextInt();
            lastPlanBills = sc.nextInt();
            lastPlanSpending = sc.nextInt();
            lastPlanSavings = sc.nextInt();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void saveChartData() {
        try (PrintWriter pw = new PrintWriter(getChartFileName())) 
        {
            pw.println(lastBills);
            pw.println(lastSpending);
            pw.println(lastSavings);
            pw.println(lastPlanBills);
            pw.println(lastPlanSpending);
            pw.println(lastPlanSavings);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private String getSavingsFileName() {
    	return username + "_Savings.txt";
    }

    private JPanel createLegend(String[] labels, int[] values, Color[] colors) {
        JPanel legend = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        legend.setBackground(new Color(255, 255, 128));

        for (int i = 0; i < labels.length; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setBackground(new Color(255, 255, 128));

            JPanel colorBox = new JPanel();
            colorBox.setBackground(colors[i]);
            colorBox.setPreferredSize(new Dimension(15, 15));

            JLabel label = new JLabel(labels[i] + " - $" + values[i]);

            row.add(colorBox);
            row.add(label);
            legend.add(row);
        }

        return legend;
    }
    
    private void loadSavingsData() {
    	try
    	{
    		File file = new File(getSavingsFileName());
    		if(!file.exists())
    		{
    			return;
    		}
    		
    		Scanner scnr = new Scanner(file);
    		while(scnr.hasNextLine())
    		{
    			String line = scnr.nextLine();
    			if(line.startsWith("goal="))
    			{
    				savingsGoal = Integer.parseInt(line.substring(5));
    			}
    			else if(line.startsWith("saved="))
    			{
    				currentSaved = Integer.parseInt(line.substring(6));
    			}
    		}
    		scnr.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private void saveSavingsData() {
    	try
    	{
    		FileWriter fw = new FileWriter(getSavingsFileName());
    		fw.write("goal=" + savingsGoal + "\n");
    		fw.write("saved=" + currentSaved + "\n");
    		fw.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private void updateProgressBar() {
    	if(savingsGoal <= 0)
    	{
    		savingsProgressBar.setValue(0);
    		return;
    	}
    	
    	int percent = (int)(((double)currentSaved / savingsGoal) * 100);
    	
    	if(percent > 100)
    	{
    		percent = 100;
    	}
    	
    	savingsProgressBar.setValue(percent);
    }
}