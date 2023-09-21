package com.userlogin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	 private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JButton loginButton;

	    public LoginFrame() {
	        setTitle("Login Form");
	        setSize(300, 150);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(3, 2));

	        JLabel usernameLabel = new JLabel("Username:");
	        usernameField = new JTextField();
	        JLabel passwordLabel = new JLabel("Password:");
	        passwordField = new JPasswordField();
	        loginButton = new JButton("Login");

	        panel.add(usernameLabel);
	        panel.add(usernameField);
	        panel.add(passwordLabel);
	        panel.add(passwordField);
	        panel.add(loginButton);
	        loginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = usernameField.getText();
	                String password = String.valueOf(passwordField.getPassword());

	                if (isValidUser(username, password)) {
	                    JOptionPane.showMessageDialog(null, "Login Successful");
	                } else {
	                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
	                }
	            }
	        });

	        add(panel);
	    }
	    private boolean isValidUser(String username, String password) {
	        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
	        String dbUser = "hr";
	        String dbPassword = "oracle";

	        try {
	            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

	            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, username);
	            statement.setString(2, password);
	            

	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                resultSet.close();
	                statement.close();
	                connection.close();
	                return true;
	            }

	            resultSet.close();
	            statement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new LoginFrame().setVisible(true);
	            }
	        });
	    }
}
