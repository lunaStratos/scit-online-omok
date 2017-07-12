package client;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;

public class kirinyaga extends JFrame implements ActionListener {
	private JLabel lblNewLabel;
	private JButton btnNewButton;

	public kirinyaga() { // 이 기능은 그냥 에스터에그로
		getContentPane().setLayout(null);

		btnNewButton = new JButton("오늘의 로또번호 ");
		btnNewButton.setBounds(161, 137, 169, 40);
		getContentPane().add(btnNewButton);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(
				"1. \"\uB354 \uC774\uC0C1 \uB0A0 \uC218 \uC5C6\uB294 \uC0C8\uB294 \uBAA8\uB450 \uC8FD\uB098\uC694?\"\r\n\"\uB300\uAC1C\uB294 \uADF8\uB807\uB2E8\uB2E4. \uBA87\uBA87\uC740 \uC548\uC804\uD55C \uC0C8\uC7A5\uC744 \uC88B\uC544\uD558\uC9C0\uB9CC \uB300\uAC1C\uB294 \uC2E4\uB9DD\uD574 \uC8FD\uACE0 \r\n\uB9CC\uB2E8\uB2E4. \uD558\uB298 \uB9DB\uC744 \uBCF4\uAC8C \uB418\uBA74 \uB0A0 \uC218 \uC5C6\uB2E4\uB294 \uC0AC\uC2E4\uC744 \uCC38\uC544 \uB0B4\uC9C0 \uBABB\uD558\uC9C0.\" \r\n\r\n\"\uADF8\uB7FC \uC6B0\uB9B0 \uC65C \uC0C8\uC7A5\uC744 \uB9CC\uB4DC\uB294 \uAC70\uC8E0? \r\n\uC0C8\uC7A5 \uB54C\uBB38\uC5D0 \uC0C8\uB4E4\uC758 \uAE30\uBD84\uC774 \uC88B\uC544\uC9C0\uC9C0 \uBABB\uD55C\uB2E4\uBA74\uC694.\"\r\n\"\uC65C\uB0D0\uD558\uBA74 <\uC6B0\uB9AC\uB4E4> \uAE30\uBD84 \uC88B\uC544\uC9C0\uAC70\uB4E0.\"\r\n\r\n2. \uB098\uB294 \uC65C \uC0C8\uC7A5\uC5D0 \uAC07\uD78C \uC0C8\uAC00 \uC8FD\uB294\uC9C0 \uC544\uB124-\r\n\uC65C\uB0D0\uD558\uBA74 \uADF8 \uC0C8\uB4E4\uCC98\uB7FC \uB098, \uD558\uB298 \uB9DB\uC744 \uBCF4\uC558\uAE30\uC5D0.\r\n\r\n - \uD0A4\uB9AC\uB0D0\uAC38, \uB9C8\uC774\uD074 \uB808\uC2AC\uB9AD");
		textArea.setBounds(12, 187, 468, 267);
		getContentPane().add(textArea);

		lblNewLabel = new JLabel("오늘의 당신의 행운은?");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 22));
		lblNewLabel.setBounds(121, 43, 321, 72);
		getContentPane().add(lblNewLabel);
		btnNewButton.addActionListener(this);
		setSize(511, 506);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int[] lottoNumbers = new int[6];
		Random rnd = new Random();

		for (int i = 0; i < lottoNumbers.length; i++) {
			lottoNumbers[i] = rnd.nextInt(45) + 1;

			// 중복된 값이 있으면 다시 랜덤값 구하기 위해 확인해서 index 값 줄여준다.
			for (int j = 0; j < i; j++) {
				if (lottoNumbers[i] == lottoNumbers[j]) {
					i--;
					break;
				}
			}
		}
		Arrays.sort(lottoNumbers); // sort
		if (e.getSource() == btnNewButton) {

			lblNewLabel.setText(lottoNumbers[0] + " " + lottoNumbers[1] + " " + lottoNumbers[2] + " " + lottoNumbers[3]
					+ " " + lottoNumbers[4] + " " + lottoNumbers[5]);
		}

	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new kirinyaga();
//
//	}

}
