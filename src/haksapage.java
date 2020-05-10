import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class haksapage extends JFrame {
	static int changePage = 0;
	
	public haksapage(){
		haksamain.jpLogined.setLayout(null);
		haksamain.jpLogined.setVisible(true);
		haksamain.jpLogined.setSize(1000,1000);
		haksamain.jpLogined.setLocation(0, 0);
		haksamain.jpLogined.setBackground(Color.white);
	}

}

class searchPage extends JFrame{
	static JLayeredPane jpSearch = new JLayeredPane();
	searchInterface searchif = new searchInterface();
	static JTextField jtName = new JTextField();
	static JTextField jtId = new JTextField();
	static JTextField jtDept = new JTextField();
	static JTextField jtAdr = new JTextField();
	
	static String columnNames[] = null;
	static Object rowData[][] = new Object[4][];
	static JTable searchResult;
	static DefaultTableModel model ;
	
	public searchPage() {
		JButton jbInsert = new JButton("ÀÔ·Â");
		jbInsert.setSize(100,150);
		jbInsert.setLocation(850, 50);
		jbInsert.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jbInsert.setBackground(new Color(0,62,142));
		jbInsert.setForeground(Color.white);
		jpSearch.add(jbInsert);
		
		JButton jbSearch = new JButton("Á¶È¸");
		jbSearch.setSize(100,150);
		jbSearch.setLocation(740, 50);
		jbSearch.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jbSearch.setBackground(new Color(0,62,142));
		jbSearch.setForeground(Color.white);
		jpSearch.add(jbSearch);
		
		JButton jbUpdate = new JButton("¼öÁ¤");
		jbUpdate.setSize(100,50);
		jbUpdate.setLocation(25, 675);
		jbUpdate.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jbUpdate.setBackground(new Color(0,62,142));
		jbUpdate.setForeground(Color.white);
		jpSearch.add(jbUpdate);
		
		JButton jbDelete = new JButton("»èÁ¦");
		jbDelete.setSize(100,50);
		jbDelete.setLocation(850, 675);
		jbDelete.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jbDelete.setBackground(new Color(186,0,12));
		jbDelete.setForeground(Color.white);
		jpSearch.add(jbDelete);
		
		JLabel jlName= new JLabel("ÀÌ¸§: ");
		jlName.setLocation(50, 70);
		jlName.setSize(100,50);
		jlName.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jlName);
		
		jtName.setLocation(110, 70);
		jtName.setSize(150,50);
		jtName.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jtName);
		jtName.addMouseListener(new MouseListener() {
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
				jtName.setText("");
			}
		});
		
		
		JLabel jlId= new JLabel("ÇÐ¹ø: ");
		jlId.setLocation(380, 70);
		jlId.setSize(100,50);
		jlId.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jlId);
		
		jtId.setLocation(440, 70);
		jtId.setSize(150,50);
		jtId.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jtId);
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
		
		JLabel jlDept= new JLabel("ÇÐ°ú: ");
		jlDept.setLocation(50, 130);
		jlDept.setSize(100,50);
		jlDept.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jlDept);
		
		jtDept.setLocation(110, 130);
		jtDept.setSize(150,50);
		jtDept.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jtDept);
		jtDept.addMouseListener(new MouseListener() {
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
				jtDept.setText("");
			}
		});
		
		
		JLabel jlAdr= new JLabel("ÁÖ¼Ò: ");
		jlAdr.setLocation(380, 130);
		jlAdr.setSize(100,50);
		jlAdr.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jlAdr);
		
		jtAdr.setLocation(440, 130);
		jtAdr.setSize(150,50);
		jtAdr.setFont(new Font("¸¼Àº°íµñ",Font.BOLD,20));
		jpSearch.add(jtAdr);
		jtAdr.addMouseListener(new MouseListener() {
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
				jtAdr.setText("");
			}
		});
		
		
		ImageIcon logoImage2 = new ImageIcon("images/logo2.png");
		JLabel logoLabel2 = new JLabel();
		logoLabel2.setIcon(logoImage2);
		logoLabel2.setSize(407,69);
		logoLabel2.setLocation(370, 670);
		jpSearch.add(logoLabel2,new Integer(2));
		ImageIcon logoImage3 = new ImageIcon("images/logo2.png");
		JLabel logoLabel3 = new JLabel();
		logoLabel3.setIcon(logoImage2);
		logoLabel3.setSize(407,69);
		logoLabel3.setLocation(370, 670);
		bookPage.jpBook.add(logoLabel3,new Integer(2));
		ImageIcon logoImage4 = new ImageIcon("images/logo3.png");
		JLabel logoLabel4 = new JLabel();
		logoLabel4.setIcon(logoImage4);
		logoLabel4.setSize(606, 776);
		logoLabel4.setLocation(210, 20);
		haksamain.jpLogined.add(logoLabel4,new Integer(0));

		
		
		/////¹öÆ° ¾×¼Ç ¸®½º³Ê »ý¼º//////
		jbInsert.addActionListener(new haksaInsert());
		jbSearch.addActionListener(new haksaSearch());
		jbDelete.addActionListener(new haksaDelete());
		jbUpdate.addActionListener(new haksaUpdate());
		
		/////°Ë»ö °á°ú Ã¢////////
		String columnNames[] =
			{ "ÇÐ¹ø", "ÀÌ¸§", "ÇÐ°ú", "ÁÖ¼Ò" };
		Object rowData[][] = {};

		
		model = new DefaultTableModel(rowData,columnNames);
		searchResult = new JTable(model);
		JScrollPane scrollSearchResult = new JScrollPane(searchResult);
		scrollSearchResult.setLocation(15, 275);
		scrollSearchResult.setSize(950, 390);
		scrollSearchResult.getViewport().setBackground(Color.white);
		jpSearch.add(scrollSearchResult,new Integer(1));
		
		haksamain.jpLogined.add(jpSearch,new Integer(1));
		
		//////¸¶¿ì½º ¾×¼Ç ¸®½º³Ê//////
		searchResult.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int n = searchResult.getSelectedRow();
				String id = (String)searchResult.getValueAt(n, 0);
				jtId.setText(id);
				String name = (String)searchResult.getValueAt(n, 1);
				jtName.setText(name);
				String department = (String)searchResult.getValueAt(n, 2);
				jtDept.setText(department);
				String address = (String)searchResult.getValueAt(n, 3);
				jtAdr.setText(address);
			}
		});
		
		///////////////////////
		bookPage.jpBook.setVisible(false);
		jpSearch.setLayout(null);
		jpSearch.setSize(1000,1000);
		jpSearch.setLocation(0, 0);
		jpSearch.setVisible(true);
		jpSearch.setBackground(Color.white);
		searchif.setLocation(10, 10);
		searchif.setSize(970,1000);
		searchif.setBackground(Color.white);
		jpSearch.add(searchif);
		
	}
}



class searchInterface extends JPanel{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(186,0,12));
		g.drawRect(0, 0, 960, 250);
		g.drawRect(0, 260, 960, 500);
	}
}
