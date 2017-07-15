package com.akaiha.mcuuidfetch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.NameHistory;
import com.mojang.api.profiles.Profile;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main {

	private JFrame frmMcUuidAnd;
	private JTextField nameField;
	private JTextArea namesHistoryField;
	private JLabel uuidField;
	DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmMcUuidAnd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		initialize();
	}
	
	private void initialize() {
		frmMcUuidAnd = new JFrame();
		frmMcUuidAnd.setResizable(false);
		frmMcUuidAnd.setForeground(Color.LIGHT_GRAY);
		frmMcUuidAnd.setBackground(Color.LIGHT_GRAY);
		frmMcUuidAnd.setTitle("MC UUID and Names History Fetcher");
		frmMcUuidAnd.setBounds(100, 100, 640, 480);
		frmMcUuidAnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMcUuidAnd.getContentPane().setLayout(null);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameField.setToolTipText("MC Name");
		nameField.setBounds(12, 42, 418, 29);
		frmMcUuidAnd.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel nameLabel = new JLabel(" MC Name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLabel.setBounds(12, 13, 191, 29);
		frmMcUuidAnd.getContentPane().add(nameLabel);
		
		JButton fetchButton = new JButton("Fetch");
		fetchButton.setFont(new Font("Tahoma", Font.ITALIC, 18));
		fetchButton.setForeground(Color.RED);
		fetchButton.setBounds(460, 42, 115, 30);
		fetchButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	uuidField.setText("");
		    	namesHistoryField.setText("");
		    	if (!nameField.getText().isEmpty()) {
		    		fetch();
		    	}
		    }
		});
		frmMcUuidAnd.getContentPane().add(fetchButton);
		
		JLabel uuidLabel = new JLabel(" MC UUID:");
		uuidLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		uuidLabel.setBounds(12, 77, 191, 29);
		frmMcUuidAnd.getContentPane().add(uuidLabel);
		
		uuidField = new JLabel("");
		uuidField.setHorizontalAlignment(SwingConstants.CENTER);
		uuidField.setForeground(Color.BLUE);
		uuidField.setBackground(Color.WHITE);
		uuidField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		uuidField.setBounds(12, 108, 598, 29);
		uuidField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmMcUuidAnd.getContentPane().add(uuidField);
		
		namesHistoryField = new JTextArea();
		namesHistoryField.setBackground(Color.WHITE);
		namesHistoryField.setLineWrap(true);
		namesHistoryField.setFont(new Font("Monospaced", Font.PLAIN, 15));
		namesHistoryField.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(namesHistoryField, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 150, 598, 270);
		frmMcUuidAnd.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Author: Akaiha");
		lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 10));
		lblNewLabel.setBounds(542, 429, 92, 16);
		frmMcUuidAnd.getContentPane().add(lblNewLabel);
	}
	
	public void fetch() {
		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(nameField.getText());
		uuidField.setText(profile[0].getId());
		NameHistory[] history = new HttpProfileRepository("minecraft").findNameHistoryByUUID(profile[0].getId());
		for (int i = history.length; i > 0; i--) {
			if (history[i-1].getChangedToAt() == 0) {
				namesHistoryField.append("Original Name: " + history[i-1].getName() + "\n");
			} else {
				namesHistoryField.append("Date: " + dateFormat.format(new Date(history[i-1].getChangedToAt())) + ", Name: " + history[i-1].getName() + "\n");
			}
		}
	}
}
