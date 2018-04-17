package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.RentModel;

public class RentView extends JPanel implements ActionListener{
	JTextField tfRentTel, tfRentCustName, tfRentVideoNum;
	JButton bRent;

	JTextField tfReturnVideoNum;
	JButton bReturn;

	JTable tableRecentList;
	rentTableModel tbRentModel;
	
	RentModel model;

	// ==============================================
	// 생성자 함수
	public RentView() {
		addLayout();
		eventProc();
		conDB();
		searchIsReturnList();
	}
	void conDB(){
		try {
			model = new RentModel();
			System.out.println("렌트 디비 연결");
		} catch (Exception e) {
			System.out.println("렌트 디비 연결 실패");
			e.printStackTrace();
		}
		
	}
	
	void eventProc(){
		tfRentTel.addActionListener(this);
		bRent.addActionListener(this);
		bReturn.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		if(evt == bRent){
			String tel = tfRentTel.getText();
			String videoNum = tfRentVideoNum.getText();
			try {
				model.rentVideo(tel,videoNum);
				System.out.println("대여성공");
				searchIsReturnList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}if(evt ==bReturn){
			String tel = tfReturnVideoNum.getText();
			try {
				int result = model.returnVideo(tel);
				if(result == 1){
					System.out.println("반납성공");
					searchIsReturnList();
				}else{
					System.out.println(result+": 반납실패");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	void searchIsReturnList(){
		try {
			ArrayList list = model.searchIsReturnList();

			tbRentModel.data = list;
			tableRecentList.setModel(tbRentModel);
			tbRentModel.fireTableDataChanged();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addLayout(){
		
		tfRentTel = new JTextField();
		tfRentCustName = new JTextField();
		tfRentVideoNum = new JTextField();
		bRent = new JButton("대여");

		tfReturnVideoNum = new JTextField(20);
		bReturn = new JButton("반납");
		
		tbRentModel = new rentTableModel();
		tableRecentList = new JTable(tbRentModel);
		
		 // 위쪽
        JPanel p_north = new JPanel();
        p_north.setLayout(new GridLayout(1, 2));
        // 위쪽 왼쪽
        JPanel p_north_west = new JPanel();
        p_north_west.setBorder(new TitledBorder("대여"));
        p_north_west.setLayout(new GridLayout(4, 2));
        p_north_west.add(new JLabel("전 화 번 호"));
        p_north_west.add(tfRentTel);
        p_north_west.add(new JLabel("고 객 명"));
        p_north_west.add(tfRentCustName);
        p_north_west.add(new JLabel("비디오 번호"));
        p_north_west.add(tfRentVideoNum);
        p_north_west.add(bRent);
        // 위에 오른쪽
        JPanel p_north_east = new JPanel();
        p_north_east.setLayout(new FlowLayout());
        p_north_east.setBorder(new TitledBorder("반납"));
        p_north_east.add(new JLabel("비디오 번호"));
        p_north_east.add(tfReturnVideoNum);
        p_north_east.add(bReturn);
        p_north.add(p_north_west);
        p_north.add(p_north_east);
        // 화면구성 - 오른쪽영역
        // 테이블을 붙일때에는 반드시 JScrollPane() 이렇게 해야함
        // 전체 화면에 왼쪽 오른쪽 붙이기
        setLayout(new BorderLayout());
        add(p_north, BorderLayout.NORTH);
        add(new JScrollPane(tableRecentList), BorderLayout.CENTER);
	
	}
	
	class rentTableModel extends AbstractTableModel{

		ArrayList data = new ArrayList();
		String[] columnNames = { "비디오번호", "제목", "고객명", "전화번호", "반납예정일","반납여부" };
		
		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList) data.get(row);
			return temp.get(col);
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}
		
	}

	

}
