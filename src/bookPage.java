import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

class bookPage extends JFrame {
	static JLayeredPane jpBook = new JLayeredPane();
	searchInterface bookif = new searchInterface();
	static DefaultTableModel bookmodel;
	static ArrayList<String> deptArray = new ArrayList<>();
	static JComboBox cb_dept= new JComboBox();
	
	public bookPage() {
		bookif.setLocation(10, 10);
		bookif.setSize(970, 1000);
		bookif.setBackground(Color.white);

		searchPage.jpSearch.setVisible(false);

		JLabel l_dept = new JLabel("학과:");
		l_dept.setFont(new Font("맑은고딕", Font.BOLD, 20));
		l_dept.setSize(150, 50);
		l_dept.setLocation(50,90);
		jpBook.add(l_dept);
		
		JLabel jlRent = new JLabel("대출하기");
		jlRent.setFont(new Font("맑은고딕", Font.BOLD, 40));
		jlRent.setSize(200, 50);
		jlRent.setLocation(690, 30);
		jpBook.add(jlRent);
		
		JLabel jlId = new JLabel("학번:");
		jlId.setFont(new Font("맑은고딕", Font.BOLD, 20));
		jlId.setSize(150, 50);
		jlId.setLocation(620, 90);
		jpBook.add(jlId);
		
		JTextField jtId = new JTextField();
		jtId.setFont(new Font("맑은고딕", Font.BOLD, 20));
		jtId.setSize(200, 50);
		jtId.setLocation(680, 90);
		jpBook.add(jtId);
		jtId.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				jtId.setText("");
			}
		});
		
		JLabel jlBookName = new JLabel("책이름:");
		jlBookName.setFont(new Font("맑은고딕", Font.BOLD, 20));
		jlBookName.setSize(150, 50);
		jlBookName.setLocation(598, 150);
		jpBook.add(jlBookName);
		
		JTextField jtBookName = new JTextField();
		jtBookName.setFont(new Font("맑은고딕", Font.BOLD, 20));
		jtBookName.setSize(200, 50);
		jtBookName.setLocation(680, 150);
		jpBook.add(jtBookName);
		jtBookName.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				jtBookName.setText("");
			}
		});
		
		JButton jbRent = new JButton("대출");
		jbRent.setSize(155,40);
		jbRent.setLocation(700, 210);
		jbRent.setFont(new Font("맑은고딕", Font.PLAIN, 15));
		jbRent.setForeground(Color.white);
		jbRent.setBackground(new Color(13, 46, 98));
		jpBook.add(jbRent);
		jbRent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempBook="";
				String sql = "select no from books where title ='"+jtBookName.getText()+"'";
				try {
				ResultSet srs = jdbc.stmt.executeQuery(sql);
				while (srs.next()) {
					tempBook = srs.getString("no");
				}
				sql = "insert into bookrent values(bookrentno.nextval,'"+jtId.getText()+"','"+tempBook+"','20200415')";
				System.out.println(sql);
				jdbc.stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "대출하였습니다.", "대출 성공", JOptionPane.INFORMATION_MESSAGE);
				jdbc.stmt.executeUpdate("commit");
				}catch(SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 학번 또는 책입니다.", "대출 실패", JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException e1) {
					e1.printStackTrace();
				}


			}
		});
		

		JLabel jlSearch = new JLabel("대출 목록 조회");
		jlSearch.setFont(new Font("맑은고딕", Font.BOLD, 35));
		jlSearch.setSize(250, 50);
		jlSearch.setLocation(60, 30);
		jpBook.add(jlSearch);
		
		JButton jbReturn = new JButton("반납");
		jbReturn.setSize(100,50);
		jbReturn.setLocation(850, 675);
		jbReturn.setFont(new Font("맑은고딕",Font.BOLD,20));
		jbReturn.setForeground(Color.white);
		jbReturn.setBackground(new Color(186,0,12));
		jpBook.add(jbReturn);
		

		//// 검색 결과 창////
		String columnNames[] = {"No.","학번", "이름", "도서명", "대출일" };
		Object rowData[][] = {};

		bookmodel = new DefaultTableModel(rowData, columnNames);
		JTable booktable = new JTable(bookmodel);
		JScrollPane scrollSearchResult = new JScrollPane(booktable);
		scrollSearchResult.setLocation(15, 275);
		scrollSearchResult.setSize(950, 390);
		scrollSearchResult.getViewport().setBackground(Color.white);
		booktable.getColumnModel().getColumn(0).setPreferredWidth(10);
		booktable.getColumnModel().getColumn(1).setPreferredWidth(170);
		booktable.getColumnModel().getColumn(2).setPreferredWidth(170);
		booktable.getColumnModel().getColumn(3).setPreferredWidth(170);
		booktable.getColumnModel().getColumn(4).setPreferredWidth(170);
		jpBook.add(scrollSearchResult, new Integer(1));
		////////////////
		
		jbReturn.addActionListener(new ActionListener() { ///반납 액션리스너
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = booktable.getSelectedRow();
				String sql = "delete from bookrent where no='" + booktable.getValueAt(n, 0) + "'";
				try {
					jdbc.stmt.executeUpdate(sql);
					bookmodel.removeRow(n);
					JOptionPane.showMessageDialog(null, "반납되었습니다.", "반납 성공", JOptionPane.INFORMATION_MESSAGE);
					jdbc.stmt.executeUpdate("commit");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.print(sql);
			}
		});
		
		

		jpBook.add(bookif);
		jpBook.setLayout(null);
		jpBook.setSize(1000, 1000);
		jpBook.setVisible(false);
		jpBook.setBackground(Color.white);
		haksamain.jpLogined.add(jpBook,new Integer(1));
	}
	
	
	
	static void getlist() {
		if(haksapage.changePage == 2) {
			
			try {
				deptArray.clear();
				ResultSet srs = jdbc.stmt.executeQuery("select distinct department from student");
				deptArray.add("학과를 선택하세요");
				while (srs.next()) {
					deptArray.add(srs.getString("department"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			haksapage.changePage = 0;
		}
		String[] dept = new String[deptArray.size()];
		int size = 0;
		for (String temp : deptArray) {
			dept[size++] = temp;
			System.out.println(temp);
		}
		
		cb_dept.setFont(new Font("맑은고딕", Font.PLAIN, 13));
		cb_dept.setBackground(Color.white);
		cb_dept.setForeground(new Color(0, 62, 142));
		cb_dept.setSize(200, 50);
		cb_dept.setLocation(110, 90);
		cb_dept.addActionListener(new bookRentList());
		
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel(dept);
		cb_dept.removeAllItems();
		cb_dept.setModel(comboModel);
		

		
		jpBook.add(cb_dept,new Integer(2));
		jpBook.revalidate();
		jpBook.repaint();
		
	}

}
