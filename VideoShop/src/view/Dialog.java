package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class Dialog extends JDialog implements ActionListener {

	JPanel panel1, panel2;
	JCheckBox cb;
	JButton bSelect, bCancel;
	
	public Dialog(){
		 panel1 = new JPanel();
	        panel1.setLayout(new BorderLayout());
	        add(panel1);
	        // 
	        panel1.add(cb, BorderLayout.CENTER);
	        panel2 = new JPanel();
	        panel1.add(panel2, BorderLayout.SOUTH);

	        bSelect = new JButton("OK");
	        bSelect.addActionListener(this);
	        panel2.add(bSelect);
	        bCancel = new JButton("CANCEL");
	        bCancel.addActionListener(this);
	        panel2.add(bCancel);
	        setSize(300, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dispose();
	}
}
