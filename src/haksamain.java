import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class haksamain extends JFrame {
	Container c = getContentPane();
	static JLayeredPane jpLogined = new JLayeredPane();
	static boolean isSearch = false;
	static boolean isEdit = false;
	static boolean isPopup = false;
	static boolean threadStop = true;
	static signUp signup;
	static dialogChart dChart;
	static drawChart drawchart;
	static ArrayList<Integer> countDept = new ArrayList<>();
	static ArrayList<String> nameDept = new ArrayList<>();
	static int chartSum = 0;

	public haksamain() {
		new searchPage();
		new bookPage();
		signup = new signUp(this, "회원가입");
		drawchart = new drawChart();

		signUp.t1.start();

		setTitle("학사관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c.setLayout(null);
		c.setSize(1000, 1000);
		c.setVisible(true);
		c.setLocation(0, 0);
		c.setBackground(Color.white);

		JPanel jpLogin = new JPanel();
		jpLogin.setLayout(null);
		jpLogin.setSize(400, 1000);
		jpLogin.setLocation(300, 0);
		jpLogin.setVisible(true);
		jpLogin.setBackground(new Color(16, 58, 123));
		c.add(jpLogin);

		JLabel jlLogin = new JLabel("ID를 입력해 주세요.");
		jlLogin.setFont(new Font("맑은고딕", Font.ITALIC, 20));
		jlLogin.setSize(350, 35);
		jlLogin.setLocation(27, 450);
		jpLogin.add(jlLogin);

		JTextField jtLogin = new JTextField();
		jtLogin.setSize(360, 40);
		jtLogin.setLocation(20, 450);
		jtLogin.setFont(new Font("굴림", Font.PLAIN, 20));
		jpLogin.add(jtLogin);

		JLabel jlPassword = new JLabel("PASSWORD 를 입력해 주세요.");
		jlPassword.setFont(new Font("맑은고딕", Font.ITALIC, 20));
		jlPassword.setSize(350, 35);
		jlPassword.setLocation(27, 500);
		jpLogin.add(jlPassword);

		JPasswordField jpPassword = new JPasswordField();
		jpPassword.setSize(360, 40);
		jpPassword.setLocation(20, 500);
		jpPassword.setFont(new Font("맑은고딕", Font.ITALIC, 20));
		jpLogin.add(jpPassword);

		JButton jbLogin = new JButton("로그인");
		jbLogin.setForeground(Color.white);
		jbLogin.setFont(new Font("맑은고딕", Font.PLAIN, 15));
		jbLogin.setSize(200, 40);
		jbLogin.setLocation(100, 570);
		jbLogin.setBackground(new Color(13, 46, 98));
		jpLogin.add(jbLogin);

		jbLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(jpPassword.getPassword());
				boolean login = false;
				String sql = "select password from haksamember where id ='" + new String(jtLogin.getText()) + "'";

				try {
					ResultSet rs = jdbc.stmt.executeQuery(sql);
					while (rs.next()) {
						String temp = rs.getString("password");
						if (temp.equals(password)) {
							new haksapage();
							createMenu();
							threadStop = false;
							jpLogin.setVisible(false);
							login = true;
						}
					}
					if (login == false) {
						JOptionPane.showMessageDialog(null, "아이디 혹은 암호를 확인해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton jbSignUp = new JButton("회원가입");
		jbSignUp.setForeground(Color.white);
		jbSignUp.setFont(new Font("맑은고딕", Font.PLAIN, 15));
		jbSignUp.setSize(200, 40);
		jbSignUp.setLocation(100, 620);
		jbSignUp.setBackground(new Color(186, 0, 12));
		jpLogin.add(jbSignUp);

		jbSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				haksamain.signUp.running = true;
				signup.setVisible(true);
			}
		});

		ImageIcon logoImage = new ImageIcon("images/logo.png");
		JLabel logoLabel = new JLabel();
		logoLabel.setSize(200, 132);
		logoLabel.setLocation(100, 200);
		logoLabel.setIcon(logoImage);
		jpLogin.add(logoLabel);

		setSize(1000, 850);
		setVisible(true);

		c.add(jpLogined);
		jpLogined.setVisible(false);

		/// 윈도우 창 닫으면 db 접속 종료////
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if (jdbc.conn != null) {
						jdbc.conn.close();
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

	}

	public void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu StudentMenu = new JMenu("학사");
		JMenu BookMenu = new JMenu("도서");

		dChart = new dialogChart(this, "차트");

		mb.setBackground(new Color(0, 62, 142));
		StudentMenu.setForeground(Color.white);
		BookMenu.setForeground(Color.white);

		JMenuItem search = new JMenuItem("학적관리");
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isSearch == false) {
					haksapage.changePage = 1;
					searchPage.jpSearch.setVisible(true);
					bookPage.jpBook.setVisible(false);
					isSearch = true;
					isEdit = false;
				}
			}
		});
		StudentMenu.add(search);

		JMenuItem edit = new JMenuItem("도서관리");
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isEdit == false) {
					haksapage.changePage = 2;
					searchPage.jpSearch.setVisible(false);
					bookPage.jpBook.setVisible(true);
					bookPage.getlist();
					isSearch = false;
					isEdit = true;
				}
			}
		});
		BookMenu.add(edit);

		JMenuItem chart = new JMenuItem("학과별 대출 현황");
		chart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// add(haksamain.drawchart);
				String sql = "select department,count(*) as count\r\n" + "from  bookRent, student,books \r\n"
						+ "where bookRent.id=student.id \r\n" + "and bookRent.bookNo=books.no group by department";

				try {
					countDept.clear();
					nameDept.clear();
					chartSum = 0;
					ResultSet srs = jdbc.stmt.executeQuery(sql);
					while (srs.next()) {
						countDept.add(srs.getInt("count"));
						nameDept.add(srs.getString("department"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < countDept.size(); i++) {
					chartSum += countDept.get(i);
				}
				revalidate();
				repaint();
				dChart.setVisible(true);
			}
		});
		BookMenu.add(chart);

		mb.add(StudentMenu);
		mb.add(BookMenu);
		this.setJMenuBar(mb);
	}

	public static void main(String[] args) {
		new haksamain();
		new jdbc();

	}

	static class dialogChart extends JDialog {

		public dialogChart(JFrame frame, String title) {
			super(frame, title, true);



			this.setLocationRelativeTo(null);
			setBackground(Color.white);
			add(haksamain.drawchart);
			setSize(450, 450);
			setVisible(false);
		}

	}

	class drawChart extends JPanel {
		drawChart() {
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.red);
			g.fillArc(95, 45, 250, 250, 0, (360 / haksamain.chartSum) * haksamain.countDept.get(0) + 5);
			g.drawString(haksamain.nameDept.get(0), 185
					+ (int) (170 * Math.cos(Math.toRadians((360 / haksamain.chartSum) * haksamain.countDept.get(0)))),
					155 - (int) (160
							* Math.sin(Math.toRadians((360 / haksamain.chartSum) * haksamain.countDept.get(0)))));
			g.drawString(
					Math.round(((double) haksamain.countDept.get(0) / (double) haksamain.chartSum) * 100) + "% ("
							+ haksamain.countDept.get(0) + "권)",
					185 + (int) (170
							* Math.cos(Math.toRadians((360 / haksamain.chartSum) * haksamain.countDept.get(0)))),
					172 - (int) (160
							* Math.sin(Math.toRadians((360 / haksamain.chartSum) * haksamain.countDept.get(0)))));

			g.setColor(Color.DARK_GRAY);
			int angle = (360 / haksamain.chartSum) * haksamain.countDept.get(0) + 5;
			for (int i = 1; i < haksamain.countDept.size(); i++) {
				g.fillArc(95, 45, 250, 250, (int) Math.ceil(angle),
						(int) Math.ceil((360 / haksamain.chartSum) * haksamain.countDept.get(i)) + 5);
				angle = angle + (360 / haksamain.chartSum) * haksamain.countDept.get(i);
				g.drawString(haksamain.nameDept.get(i) + "", 185 + (int) (170 * Math.cos(Math.toRadians(angle - 10))),
						155 - (int) (160 * Math.sin(Math.toRadians(angle - 10))));

				g.drawString(
						Math.round((((double) haksamain.countDept.get(i) / (double) haksamain.chartSum)) * 100) + "% ("
								+ haksamain.countDept.get(i) + "권)",
						185 + (int) (170 * Math.cos(Math.toRadians(angle - 10))),
						172 - (int) (160 * Math.sin(Math.toRadians(angle - 10))));
				g.setColor(new Color(255 / (4 * i), 160, 255 - (20 * i)));

			}
			g.setColor(Color.black);
			g.setFont(new Font("맑은고딕", Font.BOLD, 20));
			g.drawString("대출 현황: 총 "+chartSum+" 권", 15, 400);

		}
	}

	static class signUp extends JDialog {
		static JTextField jtSignId = new JTextField();
		static JLabel jlIdChk = new JLabel("");
		static JPasswordField jtSignPassword = new JPasswordField();
		static JPasswordField jtCheckSignPassword = new JPasswordField();
		static JLabel levelPassword = new JLabel("");
		static JLabel checkPassword = new JLabel("");
		static thread1 t1 = new thread1();
		static boolean running = true;

		public signUp(JFrame frame, String title) {
			super(frame, title, true);

			JPanel sign = new JPanel();
			sign.setLayout(null);
			sign.setBackground(Color.white);
			add(sign);

			jlIdChk.setLocation(110, 180);
			jlIdChk.setSize(170, 35);
			jlIdChk.setForeground(Color.red);
			sign.add(jlIdChk);

			JLabel signId = new JLabel("　 　　아이디:");
			signId.setFont(new Font("맑은고딕", Font.BOLD, 15));
			signId.setSize(200, 35);
			signId.setLocation(10, 50);
			sign.add(signId);

			jtSignId.setSize(120, 35);
			jtSignId.setLocation(130, 50);
			sign.add(jtSignId);

			JLabel signPassword = new JLabel(" 　　비밀번호:");
			signPassword.setFont(new Font("맑은고딕", Font.BOLD, 15));
			signPassword.setSize(200, 35);
			signPassword.setLocation(10, 90);
			sign.add(signPassword);

			jtSignPassword.setSize(120, 35);
			jtSignPassword.setLocation(130, 90);
			sign.add(jtSignPassword);

			levelPassword.setFont(new Font("맑은고딕", Font.PLAIN, 13));
			levelPassword.setSize(200, 35);
			levelPassword.setLocation(260, 90);
			sign.add(levelPassword);

			JLabel signCheckPassword = new JLabel("비밀번호 확인:");
			signCheckPassword.setFont(new Font("맑은고딕", Font.BOLD, 15));
			signCheckPassword.setSize(200, 35);
			signCheckPassword.setLocation(10, 130);
			sign.add(signCheckPassword);

			checkPassword.setFont(new Font("맑은고딕", Font.PLAIN, 13));
			checkPassword.setSize(200, 35);
			checkPassword.setLocation(260, 130);
			sign.add(checkPassword);

			jtCheckSignPassword.setSize(120, 35);
			jtCheckSignPassword.setLocation(130, 130);
			sign.add(jtCheckSignPassword);

			JButton jbCheckId = new JButton("중복확인");
			jbCheckId.setSize(100, 35);
			jbCheckId.setBackground(new Color(13, 46, 98));
			jbCheckId.setFont(new Font("맑은고딕", Font.PLAIN, 13));
			jbCheckId.setForeground(Color.white);
			jbCheckId.setLocation(260, 50);
			sign.add(jbCheckId);
			jbCheckId.addActionListener(new signUpChkId());

			JButton jbSign = new JButton("가입요청");
			jbSign.setSize(120, 35);
			jbSign.setLocation(130, 220);
			jbSign.setBackground(new Color(186, 0, 12));
			jbSign.setForeground(Color.white);
			jbSign.setFont(new Font("맑은고딕", Font.PLAIN, 13));
			sign.add(jbSign);

			jbSign.addActionListener(new signUpClick());

			this.setLocationRelativeTo(null);
			setSize(450, 300);
			setVisible(false);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					haksamain.signUp.jtSignId.setText("");
					haksamain.signUp.jtSignPassword.setText("");
					haksamain.signUp.jtCheckSignPassword.setText("");
					signUp.running = false;
				}
			});
		}

	}

}

class thread1 extends Thread {
	static String password;
	static boolean yesPassWord = false;

	@Override
	public void run() {
		super.run();
		while (haksamain.threadStop) {
			int i = 0;
			try {
				thread1.sleep(100);
			} catch (InterruptedException e1) {
			}
			while (haksamain.signUp.running) {
				try {
					thread1.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				password = new String(haksamain.signUp.jtSignPassword.getPassword());
				String passwordCheck = new String(haksamain.signUp.jtCheckSignPassword.getPassword());

				int len = password.length();
				int len2 = passwordCheck.length();

				if (len > 0 && len < 4) {
					haksamain.signUp.levelPassword.setForeground(new Color(229, 67, 37));
					haksamain.signUp.levelPassword.setText("보안레벨: 하");
				} else if (len >= 4 && len < 7) {
					haksamain.signUp.levelPassword.setForeground(new Color(164, 100, 48));
					haksamain.signUp.levelPassword.setText("보안레벨: 중");
				} else if (len >= 7) {
					haksamain.signUp.levelPassword.setForeground(new Color(3, 205, 108));
					haksamain.signUp.levelPassword.setText("보안레벨: 상");
				} else if (len == 0) {
					haksamain.signUp.levelPassword.setText("");
					haksamain.signUp.checkPassword.setText("");
				}
				if (len > 0 && len2 > 0 && !password.equals(passwordCheck)) {
					haksamain.signUp.checkPassword.setForeground(new Color(229, 67, 37));
					haksamain.signUp.checkPassword.setText("비밀번호가 일치하지 않습니다.");
					yesPassWord = false;
				}
				if (len > 0 && len2 > 0 && password.equals(passwordCheck)) {
					haksamain.signUp.checkPassword.setForeground(new Color(3, 205, 108));
					haksamain.signUp.checkPassword.setText("비밀번호가 일치합니다.");
					yesPassWord = true;
				}

			}
		}

	}

}
