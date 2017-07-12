package client;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vo.news;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class omokNews extends JFrame implements ActionListener {
	private JButton exitButton;
	private JScrollPane scrollPane;
	private JTable table;

	public omokNews() {
		getContentPane().setLayout(null);
		ImageIcon image = new ImageIcon("src/images/newsRoom.jpg");
		JLabel label = new JLabel("\u25A3 \uD55C\uAD6D\uC624\uBAA9\uD611\uD68C \uC18C\uC2DD ");
		label.setBounds(12, 10, 188, 31);
		getContentPane().add(label);
		label.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));

		exitButton = new JButton("\uB098\uAC00\uAE30");
		exitButton.setBounds(12, 466, 880, 57);
		getContentPane().add(exitButton);

		JLabel imageBackgroundOnLabel = new JLabel(image);
		imageBackgroundOnLabel.setBounds(0, 0, 903, 533);
		getContentPane().add(imageBackgroundOnLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 51, 856, 396);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		exitButton.addActionListener(this);

		sprite();
		setSize(919, 571);

	}

	public void sprite() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://omok.or.kr/bbs/zboard.php?id=notice").get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements titles = doc.select(".zv3_listBox");
		ArrayList<news> alist = new ArrayList<>();
		// print all titles in main page
		for (Element e : titles) {
			System.out.println("text: " + e.text().replace("?", ""));
			// System.out.println("html: "+ e.html());
			String a = e.text();
			String b = a.substring(a.indexOf(" ")+1, a.lastIndexOf(" "));
			String c = a.substring(a.indexOf(" ")+1, b.lastIndexOf(" "));
			String d = a.substring(a.indexOf(" ")+1, c.lastIndexOf(" "));
			String induce = a.substring(a.indexOf(" "), d.lastIndexOf(" ")+1);
			alist.add(new news(a.substring(0, a.indexOf(" ")), induce));
		}
		ArrayList<String> linklist = new ArrayList<>();
		// print all available links on page
		Elements links = doc.select("a[href]");
		for (Element l : links) {
			System.out.println("link: " + l.attr("abs:href"));
			linklist.add(l.attr("abs:href"));
		}
		Object[][] contents = new Object[alist.size()][2];
		Object[] column = new Object[] { "Ç×¸ñ", "¿À´ÃÀÇ ¼Ò½Ä" };
		for (int index = 0; index < alist.size(); index++) {
			contents[index][0] = alist.get(index).getTitles();
			contents[index][1] = alist.get(index).getText();
		}
		table = new JTable(contents, column);
		table.getColumn("Ç×¸ñ").setMaxWidth(200);
		scrollPane.setViewportView(table);

		// String url = linklist.get(20);
		// String[] cmd = { "CMD.EXE", "/C",
		// "explorer",'\u0022'+linklist.get(96)+'\u0022' };
		// try {
		// Process process = Runtime.getRuntime().exec(cmd);
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.dispose();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new omokNews().setVisible(true);
	}
}