package MainTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import TaskA.HistogramATest;
import TaskB.HistogramGroupedTest;
import TaskB.HistogramStackedTest;
import TaskC.HistogramCMain;


public class MainTest {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TestFrame frame = new TestFrame();
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setLocationRelativeTo(null);
	            frame.setVisible(true);
			}
		});
		
	}
}


class TestFrame extends JFrame {
	

	public TestFrame() {
		setTitle("Histogram_Test");
		setSize(600,430);
		
		buttonPanel = new JPanel();
		
        buttonPanel.setLayout(new GridLayout(4,4,30,30));

        A1_Given = new JButton("A1_Given_FontTest");
        A1_Given.addActionListener(e -> {
			new HistogramATest("A1_Given_FontTest.json");
        });

        A1_DefaultTest = new JButton("A1_DefaultTest");
        A1_DefaultTest.addActionListener(e -> {
        	new HistogramATest("A1_DefaultTest.json");
        });

        A2_Given = new JButton("A2_Given");
        A2_Given.addActionListener(e -> {
        	new HistogramATest("A2_Given.json");
        });

        A2_DefaultTest = new JButton("A2_DefaultTest");
        A2_DefaultTest.addActionListener(e -> {
        	new HistogramATest("A2_DefaultTest.json");
        });

        B_Grouped_Price = new JButton("B_Grouped_Price");
        B_Grouped_Price.addActionListener(e -> {
       		new HistogramGroupedTest("B_Price.json");
        });

        B_Grouped_Energy = new JButton("B_Grouped_Energy");
        B_Grouped_Energy.addActionListener(e -> {
        	new HistogramGroupedTest("B_Energy.json");
        });
        
        B_Stacked_Patent = new JButton("B_Stacked_Patent");
        B_Stacked_Patent.addActionListener(e -> {
        	new HistogramStackedTest("B_Patent.json");
        });
        
        B_Stacked_Student = new JButton("B_Stacked_Student");
        B_Stacked_Student.addActionListener(e -> {
        	new HistogramStackedTest("B_Student.json");
        });
        
        C_GDP = new JButton("C_GDP");
        C_GDP.addActionListener(e -> {
            new Thread(() -> new HistogramCMain("C_GDP.json")).start();
        });
        
        C_Population = new JButton("C_Population");
        C_Population.addActionListener(e -> {
            new Thread(() -> new HistogramCMain("C_Population.json")).start();
        });
        
        buttonPanel.add(A1_Given);
        buttonPanel.add(A1_DefaultTest);
        buttonPanel.add(A2_Given);
        buttonPanel.add(A2_DefaultTest);
        buttonPanel.add(B_Grouped_Price);
        buttonPanel.add(B_Stacked_Patent);
        buttonPanel.add(B_Grouped_Energy);
        buttonPanel.add(B_Stacked_Student);
        buttonPanel.add(C_GDP);
        buttonPanel.add(C_Population);
        
		add(buttonPanel);
	}
	
	private JPanel buttonPanel;
	
	
	private JButton A1_Given;
	private JButton A1_DefaultTest;
	private JButton A2_Given;
	private JButton A2_DefaultTest;
	private JButton B_Grouped_Price;
	private JButton B_Stacked_Patent;
	private JButton B_Grouped_Energy;
	private JButton B_Stacked_Student;
	private JButton C_GDP;
	private JButton C_Population;
}