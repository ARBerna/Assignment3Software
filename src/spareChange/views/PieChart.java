package spareChange.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PieChart extends JPanel {

    private int[] values;
    private Color[] colors;
    private String title;

    public PieChart(String title, int[] values, Color[] colors) {
        this.title = title;
        this.values = values;
        this.colors = colors;
        setPreferredSize(new Dimension(250, 250));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int total = 0;
        for (int v : values) {
            total += v;
        }

        int startAngle = 0;

        for (int i = 0; i < values.length; i++) {
            int arcAngle = (int) Math.round((values[i] * 360.0) / total);
            g.setColor(colors[i]);
            g.fillArc(20, 40, 200, 200, startAngle, arcAngle);
            startAngle += arcAngle;
        }

        g.setColor(Color.BLACK);
        g.drawString(title, 100, 20);
    }
    
    public void updateValues(int[] newValues) {
    	this.values = newValues;
    	repaint();
    }
}