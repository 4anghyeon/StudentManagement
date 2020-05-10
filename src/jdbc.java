import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class jdbc {
	static Connection conn = null;
	ResultSet srs = null;
	static Statement stmt;

	jdbc() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle", "ora_user", "hong");
			System.out.println("DB연결 완료");
			stmt = conn.createStatement();
			stmt.executeUpdate("commit");
		}
		catch(ClassNotFoundException e) {
			System.out.println("드라이브 로더 에러");
		}
		catch (SQLException e) {
			System.out.println("DB연결 에러");
			e.printStackTrace();
		}

	}

}

class haksaInsert implements ActionListener {
	String toSQLname;
	String toSQLid;
	String toSQLdepartment;
	String toSQLaddress;

	public haksaInsert() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			toSQLname = searchPage.jtName.getText();
			toSQLid = searchPage.jtId.getText();
			toSQLaddress = searchPage.jtAdr.getText();
			toSQLdepartment = searchPage.jtDept.getText();

			haksamain.isPopup = false;
			if (toSQLid.equals("") && haksamain.isPopup == false) {
				JOptionPane.showMessageDialog(null, "학번을 입력해 주세요", "입력 실패", JOptionPane.ERROR_MESSAGE);
				haksamain.isPopup = true;
				return;
			}
			if (toSQLname.equals("") && haksamain.isPopup == false) {
				JOptionPane.showMessageDialog(null, "이름을 입력해 주세요", "입력 실패", JOptionPane.ERROR_MESSAGE);
				haksamain.isPopup = true;
				return;
			}
			if (toSQLdepartment.equals("") && haksamain.isPopup == false) {
				JOptionPane.showMessageDialog(null, "학과를 입력해 주세요", "입력 실패", JOptionPane.ERROR_MESSAGE);
				haksamain.isPopup = true;
				return;
			}
			if (toSQLaddress.equals(""))
				toSQLaddress = "없음";

			String sql = "INSERT INTO student VALUES('" + toSQLid + "','" + toSQLname + "', '" + toSQLdepartment + "','"
					+ toSQLaddress + "')";
			jdbc.stmt.executeUpdate(sql);
			
			Object tempData[] = { toSQLid, toSQLname, toSQLdepartment, toSQLaddress };
			searchPage.model.addRow(tempData);
			JOptionPane.showMessageDialog(null, "입력되었습니다.");
			jdbc.stmt.executeUpdate("commit");

		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "입력 실패 - 존재하는 학번입니다.", "입력 실패", JOptionPane.ERROR_MESSAGE);
		}

	}
}

class haksaSearch implements ActionListener {
	String toSQLname;
	String toSQLid;
	String toSQLdepartment;
	String toSQLaddress;

	static String fromSQLname;
	static String fromSQLid;
	static String fromSQLdepartment;
	static String fromSQLaddress;

	public haksaSearch() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			toSQLname = "='" + searchPage.jtName.getText() + "'";
			toSQLid = "='" + searchPage.jtId.getText() + "'";
			toSQLaddress = searchPage.jtAdr.getText();
			toSQLdepartment = "='" + searchPage.jtDept.getText() + "'";

			searchPage.model.setNumRows(0);
			String first = "is not null";
			String second = "is not null";
			String third = "is not null";

			if (!toSQLid.equals("=''"))
				first = toSQLid;
			if (!toSQLname.equals("=''"))
				second = toSQLname;
			if (!toSQLdepartment.equals("=''"))
				third = toSQLdepartment;

			String sql = "select * from student where id " + first + " and name " + second + " and department " + third
					+ " and address like'" + toSQLaddress + "%'";

			ResultSet srs = jdbc.stmt.executeQuery(sql);

			while (srs.next()) {
				Object tempData[] = { srs.getObject("id"), srs.getObject("name"), srs.getObject("department"),
						srs.getObject("address") };
				searchPage.model.addRow(tempData);
			}
			

		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "입력 실패 - 존재하는 학번입니다.", "입력 실패", JOptionPane.ERROR_MESSAGE);
		}

	}
}

class haksaDelete implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
		if (result == JOptionPane.CANCEL_OPTION) {

		} else if (result == JOptionPane.YES_OPTION) {
			try {

				int n = searchPage.searchResult.getSelectedRow();

				String sql = "delete from student where id='" + searchPage.searchResult.getValueAt(n, 0) + "'";
				jdbc.stmt.executeUpdate(sql);
			
					
				searchPage.jtId.setText("");
				searchPage.jtAdr.setText("");
				searchPage.jtDept.setText("");
				searchPage.jtName.setText("");

				searchPage.model.removeRow(n);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				jdbc.stmt.execute("commit");
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		} else {
		}
	}
}

class haksaUpdate implements ActionListener {
	String toSQLname;
	String toSQLid;
	String toSQLdepartment;
	String toSQLaddress;

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
		if (result == JOptionPane.CANCEL_OPTION) {

		} else if (result == JOptionPane.YES_OPTION) {
			try {
				int n = searchPage.searchResult.getSelectedRow();

				toSQLname = searchPage.jtName.getText();
				toSQLid = searchPage.jtId.getText();
				toSQLaddress = searchPage.jtAdr.getText();
				toSQLdepartment = searchPage.jtDept.getText();

				String sql = "update student set name='" + toSQLname + "', department='" + toSQLdepartment
						+ "', address='" + toSQLaddress + "', id='" + toSQLid + "' where id ='"
						+ searchPage.searchResult.getValueAt(n, 0) + "'";
				jdbc.stmt.executeQuery(sql);

				Object tempData[] = { toSQLid, toSQLname, toSQLdepartment, toSQLaddress };
				searchPage.model.addRow(tempData);
				searchPage.model.removeRow(n);

				JOptionPane.showMessageDialog(null, "수정되었습니다.");
				jdbc.stmt.executeUpdate("commit");

			} catch (SQLException e3) {
				JOptionPane.showMessageDialog(null, "수정 실패 - 존재하는 학번입니다.", "수정 실패", JOptionPane.ERROR_MESSAGE);
			}
		} else {
		}
	}

}

class signUpChkId implements ActionListener{
	String temp;
	static boolean yesId = false;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String tempId = haksamain.signUp.jtSignId.getText();

			String sql ="select id from haksamember where id='"+tempId+"'";

			ResultSet rs = jdbc.stmt.executeQuery(sql);
			while (rs.next()) {
				temp= rs.getString("id") ;
				System.out.println(temp);
			}
			try {
				if(temp.equals(tempId)) {
					haksamain.signUp.jlIdChk.setText("아이디가 중복 되었습니다.");
					haksamain.signUp.jlIdChk.setForeground(Color.red);
					yesId = false;
				}
				else {
					haksamain.signUp.jlIdChk.setText("사용 가능한 아이디 입니다.");
					haksamain.signUp.jlIdChk.setForeground(Color.GREEN);
					yesId = true;
				}
			}catch(Exception e4) {
				haksamain.signUp.jlIdChk.setText("사용 가능한 아이디 입니다.");
				haksamain.signUp.jlIdChk.setForeground(Color.GREEN);
				yesId = true;
			}

		} catch (SQLException e3) {
			JOptionPane.showMessageDialog(null, "수정 실패 - 존재하는 학번입니다.", "수정 실패", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}

class signUpClick implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String tempId = haksamain.signUp.jtSignId.getText();
		String tempPassword = new String(haksamain.signUp.jtSignPassword.getPassword());
		String sql ="insert into haksamember values('"+tempId+"','"+tempPassword+"')";
		if(signUpChkId.yesId == true && thread1.yesPassWord == false) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.", "요청 실패", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(signUpChkId.yesId == true && thread1.yesPassWord == true) {
			try {
				jdbc.stmt.executeUpdate(sql);
				jdbc.stmt.execute("commit");
				JOptionPane.showMessageDialog(null, "가입되었습니다.", "요청 성공",JOptionPane.PLAIN_MESSAGE);
				haksamain.signUp.jtSignId.setText("");
				haksamain.signUp.jtSignPassword.setText("");
				haksamain.signUp.jtCheckSignPassword.setText("");
				haksamain.signup.setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "아이디 중복확인을 주세요.", "요청 실패", JOptionPane.ERROR_MESSAGE);
			}
		}
		else JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.", "요청 실패", JOptionPane.ERROR_MESSAGE);

	}
	
}

class bookRentList implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		bookPage.bookmodel.setNumRows(0);
		JComboBox cb=(JComboBox)e.getSource();
		
		String sql = "select bookRent.no,student.id, student.name, books.title, bookRent.rDate\r\n" + 
				"from  bookRent, student,books \r\n" + 
				"where bookRent.id=student.id \r\n" + 
				"and bookRent.bookNo=books.no and student.department = '"+cb.getSelectedItem()+"' order by bookRent.no";
		try {
			ResultSet srs = jdbc.stmt.executeQuery(sql);

			while (srs.next()) {
				Object tempData[] = {srs.getObject("no") ,srs.getObject("id"), srs.getObject("name"), srs.getObject("title"),
						srs.getObject("rdate") };
				bookPage.bookmodel.addRow(tempData);
			}
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
