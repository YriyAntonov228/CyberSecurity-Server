package com.antonov;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.antonov.ui.ChatPanel;
import com.antonov.ui.MainWindow;
import com.antonov.ui.MessagePanel;

public class Join {

	public static MainWindow w;

	public static ServerSocket serverSocket = null;
	public static Socket client = null;

	public static ObjectOutputStream objectOutputStream;
	public static ObjectInputStream objectInputStream;

	public static void main(String[] args) throws Exception {

		String selected = JOptionPane.showInputDialog(null, "Введите порт", "6666");
		if (selected == null)
			System.exit(0);
		int port = Integer.parseInt(selected);

		try {
			serverSocket = new ServerSocket(6666);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Ошибка при открытии порта.");
			System.exit(-1);
		}

		w = new MainWindow();

		w.setSize(600, 400);
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setAlwaysOnTop(true);
		w.setVisible(true);

		try {
			client = serverSocket.accept();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ошибка при подключении клиента.");
		}

		try {
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			objectInputStream = new ObjectInputStream(client.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		w.getSendButton().addActionListener((event) -> {

			if (client == null) {
				System.out.println("no clients available");
				return;
			}
			String messageText = w.getMessageText();

			if (messageText.replaceAll(" ", "") == "")
				return;

			Message message = new Message();
			message.setText(messageText);
			message.setDate(new Date(System.currentTimeMillis()));
			message.setSender("!SERVER!");

			try {
				objectOutputStream.writeObject(message);
			} catch (IOException e) {
				System.out.println("out stream is null");
				return;
			}

			MessagePanel panel = new MessagePanel();
			Date date = new Date(System.currentTimeMillis());
			panel.setMessageText(messageText);
			panel.setName("Я");
			panel.setTime(date.getHours() + ":" + date.getMinutes());
			panel.setFont(new Font("Arial", Font.BOLD, 30));
			panel.setAlignmentX(Component.CENTER_ALIGNMENT);
			w.addMessage(panel);
			panel.revalidate();
			panel.repaint();
			w.clearMsg();
		});

		
		while (true) {
			if (client == null)
				continue;
			Message mess = (Message) objectInputStream.readObject();

			MessagePanel panel = new MessagePanel();
			Date date = mess.getDate();
			panel.setMessageText(mess.getText());
			panel.setName(mess.getSender());
			panel.setTime(date.getHours() + ":" + date.getMinutes());
			panel.setFont(new Font("Arial", Font.BOLD, 30));
			panel.setAlignmentX(Component.CENTER_ALIGNMENT);

			w.addMessage(panel);
			panel.revalidate();
			panel.repaint();
		}

	}

}
