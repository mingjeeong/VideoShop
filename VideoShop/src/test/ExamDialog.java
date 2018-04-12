package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExamDialog extends JDialog implements ActionListener {

    //FIELDS
    private JPanel panel1, panel2;
    private JLabel label;
    private JButton okBtn;
    private JButton cancelBtn;

    //CONSTRUCTOR
    ExamDialog(JFrame frame, String str) {
        super(frame, str, true);
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        add(panel1);

        label = new JLabel("Exam Dialog ~~ Good !", JLabel.CENTER);
        panel1.add(label, BorderLayout.CENTER);
        panel2 = new JPanel();
        panel1.add(panel2, BorderLayout.SOUTH);

        okBtn = new JButton("OK");
        okBtn.addActionListener(this);
        panel2.add(okBtn);
        cancelBtn = new JButton("CANCEL");
        cancelBtn.addActionListener(this);
        panel2.add(cancelBtn);
        setSize(300, 300);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose(); //다이얼로그 제거
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose(); //다이얼로그 제거
    }
}

