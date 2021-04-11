package com.antonov.ui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame{
	
	private ChatPanel chatPanel = new ChatPanel();
	
	public MainWindow() {
		getContentPane().add(chatPanel);
	}

	public void addMessage(MessagePanel panel) {
		chatPanel.addMessage(panel);
		chatPanel.revalidate();
		chatPanel.repaint();
		panel.revalidate();
		panel.repaint();
		revalidate();
		repaint();
	}

	public JButton getSendButton() { 
		return chatPanel.getSendButton();
	}
	
	public String getMessageText() {
		return chatPanel.getMessageText();
	}
	
	public void clearMsg() {
		chatPanel.clearMsg();
	}
}
