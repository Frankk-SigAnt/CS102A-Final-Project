package MainTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


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
		
		Container container = getContentPane();
		
        buttonPanel.setLayout(new GridLayout(4,4,30,30));

        A1_Given = new JButton("A_Given");
        A1_Given.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
        });
        A1_FontTest = new JButton("A1_FontTest");
        A1_FontTest.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        A2_Given = new JButton("A_Given");
        A2_Given.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        A2_DefaultTest = new JButton("A2_DefaultTest");
        A2_DefaultTest.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        B_Grouped_Price = new JButton("B_Grouped_Price");
        B_Grouped_Price.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        B_Grouped_Energy = new JButton("B_Grouped_Energy");
        B_Grouped_Energy.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        
        B_Stacked_Price = new JButton("B_Stacked_Price");
        B_Stacked_Price.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        
        B_Stacked_Energy = new JButton("B_Stacked_Energy");
        B_Stacked_Energy.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        
        C_GDP = new JButton("C_GDP");
        C_GDP.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        
        C_Population = new JButton("C_Population");
        C_Population.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        	}
        });
        
        buttonPanel.add(A1_Given);
        buttonPanel.add(A1_FontTest);
        buttonPanel.add(A2_Given);
        buttonPanel.add(A2_DefaultTest);
        buttonPanel.add(B_Grouped_Price);
        buttonPanel.add(B_Stacked_Price);
        buttonPanel.add(B_Grouped_Energy);
        buttonPanel.add(B_Stacked_Energy);
        buttonPanel.add(C_GDP);
        buttonPanel.add(C_Population);
        
		add(buttonPanel);
	}
	
	private JPanel buttonPanel;
	
	
	private JButton A1_Given;
	private JButton A1_FontTest;
	private JButton A2_Given;
	private JButton A2_DefaultTest;
	private JButton B_Grouped_Price;
	private JButton B_Stacked_Price;
	private JButton B_Grouped_Energy;
	private JButton B_Stacked_Energy;
	private JButton C_GDP;
	private JButton C_Population;
}