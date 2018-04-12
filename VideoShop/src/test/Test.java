package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame implements ActionListener{
    public static void main(String[] args) {
        new Test();
    }

    //FIELDS
    private JPanel panel;
    private JButton btn1, btn2;
    private ExamDialog examDialog;

    Test() {
        setTitle("DialogTest");
        setSize(300, 300);
        panel = new JPanel();
        add(panel, "Center");
        btn1 = new JButton("다이얼로그 열기");
        btn2 = new JButton("나가기");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        panel.add(btn1);
        panel.add(btn2);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            if (examDialog == null) {
                examDialog = new ExamDialog(this, "Exam Dialog");
            }
            examDialog.setVisible(true);
            btn1.requestFocus();
        }else if (e.getSource() == btn2) {
            System.exit(0);
        }
    }
}
