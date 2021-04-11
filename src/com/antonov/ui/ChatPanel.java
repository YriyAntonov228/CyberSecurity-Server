package com.antonov.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class ChatPanel extends JPanel{

	private DownPanel downPanel = new DownPanel();
	
	// ===========================================
	private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
													 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JPanel contentPanel = new JPanel();
	
	// ===========================================
	public ChatPanel() {
		setLayout(new BorderLayout());
		
		
		contentPanel.setLayout(new VerticalLayout());
		
		
		scrollPane.setViewportView(contentPanel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(downPanel, BorderLayout.SOUTH);
	}
	
	
	
	
	public void addMessage(MessagePanel panel) {
		contentPanel.add(panel);
		JScrollBar sb = scrollPane.getVerticalScrollBar();
		sb.setValue( sb.getMaximum() );
	}
	
	public JButton getSendButton() {
		return downPanel.getSendButton();
	}
	
	public String getMessageText() {
		return downPanel.getMessageText();
	}
	
	public void clearMsg() {
		downPanel.clear();
	}
	
}
