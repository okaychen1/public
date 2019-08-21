package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
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
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 146, 79);
		contentPane.add(textArea);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(166, 74, 93, 23);
		contentPane.add(btnStop);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					startListen();
				}
				catch (IOException ex) {
					System.out.println("Exception : " + ex.getMessage());
				}
			}
		});
		btnStart.setBounds(166, 10, 93, 23);
		contentPane.add(btnStart);
	}
	
	private void startListen() throws IOException {
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("Start " + server);
		try {
			Socket socket = server.accept();
			try {
				
				System.out.println("Connection accepted " + socket);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				PrintWriter out = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(socket.getOutputStream())), true);
				
				System.out.println("Begin to read...");
				
				while (true) {
					String str = in.readLine();
					if (str.equals("END"))
						break;
					System.out.println("Echoing : " + str);
					out.println(str);
				}
			}
			finally {
				socket.close();
			}
			
		}
		finally{
			server.close();
		}
	}
}
