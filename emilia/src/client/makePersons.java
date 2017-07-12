package client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;
import javax.swing.JPanel;

public class makePersons extends JFrame implements  MouseListener{
	public makePersons() {
		
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 435, 502);
		getContentPane().add(panel);
		panel.setLayout(null);
		ImageIcon logos = new ImageIcon("src/images/pastecat.png");
		ImageIcon logos2 = new ImageIcon("src/images/orbitgirl_library_w.png");
		JLabel companyLabel = new JLabel("\uC81C\uC791\uD68C\uC0AC");
		companyLabel.setBounds(12, 10, 57, 15);
		panel.add(companyLabel);

		JLabel imageLiberLabel = new JLabel("\uC774\uBBF8\uC9C0\uBAA8\uB4C8");
		imageLiberLabel.setBounds(12, 190, 113, 15);
		panel.add(imageLiberLabel);

		JLabel lblNewLabel = new JLabel(logos);
		lblNewLabel.setBounds(27, 41, 383, 128);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(logos2);
		lblNewLabel_1.setBounds(12, 220, 411, 150);
		panel.add(lblNewLabel_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(27, 398, 383, 94);
		panel.add(textArea);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		textArea.setEditable(false);
		textArea.setText(
				"\uBCF8 \uD504\uB85C\uADF8\uB7A8\uC740 \uC548\uBCD1\uD638, \uAE40\uC0C1\uD6C8\uC774 \uC81C\uC791\uD558\uC600\uC2B5\uB2C8\uB2E4.\r\n\r\nSC IT MASTER \uC790\uC720\uACFC\uC81C\uB85C \uC81C\uC791\uD558\uC600\uC73C\uBA70\r\n\uC678\uBD80\uBAA8\uB4C8\uC740 JSOUP(\uD55C\uAD6D\uC624\uBAA9\uD611\uD68C \uACF5\uC9C0\uC0AC\uD56D)\r\n\uC640 JFX(\uAD6C\uAE00 \uAD11\uACE0\uBAA8\uB4C8)\uB97C \uC0AC\uC6A9\uD558\uC600\uC2B5\uB2C8\uB2E4. \r\n");
		addKeyListener(new klistener());
		addMouseListener(this);
		setSize(451, 540);
	}
	
	public class klistener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyPressed(e);
			System.out.println(e.getKeyCode());
			if(e.getKeyCode() ==  KeyEvent.VK_F2){
				System.out.println("비밀의 문");
				new kirinyaga().setVisible(true);
				
			}
		}
	}
//	public static void main(String[] args) {
//		new makePersons().setVisible(true);
//		
//	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount() ==42){
			System.out.println("비밀의 문");
			new kirinyaga().setVisible(true);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
