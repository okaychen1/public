package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.net.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private static final int PORT = 8080;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					startClient();
				}
				catch (IOException ex) {
					
				}
			}
		});
		btnStart.setBounds(76, 22, 93, 23);
		contentPane.add(btnStart);
	}
	private void startClient() throws IOException{
		InetAddress addr = InetAddress.getByName(null);
		System.out.println("addr " + addr);
		Socket socket = new Socket(addr, PORT);
		try {
			System.out.println("socket = " + socket);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())), true);
			for (int i = 0; i < 10; i++) {
				System.out.println("Send Message 'howdy" + i + "'");
				out.println("howdy" + i);
				String str = in.readLine();
				System.out.println(str);
			}
			out.println("END");
					
		}
		finally {
			socket.close();
		}
	}
}
