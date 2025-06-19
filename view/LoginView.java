package view;

import controller.LoginController;
import model.UserLoginModel;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
    private JPasswordField passwordField;

    public LoginView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 842, 537);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Log In");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 36));
        lblTitle.setBounds(363, 98, 133, 50);
        contentPane.add(lblTitle);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmail.setBounds(253, 186, 68, 14);
        contentPane.add(lblEmail);

        textField = new JTextField();
        textField.setBounds(331, 185, 244, 20);
        contentPane.add(textField);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(253, 243, 68, 14);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(331, 242, 244, 20);
        contentPane.add(passwordField);

        JButton btnLogin = new JButton("Log in");
        btnLogin.setBounds(487, 303, 89, 23);
        btnLogin.addActionListener(e -> {
            String email = textField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            UserLoginModel user = new UserLoginModel(email, password);

            // ✅ Let the controller handle success and redirection
            LoginController.login(user);
        });
        contentPane.add(btnLogin);

        JButton btnSignUp = new JButton("SignUp");
        btnSignUp.setBounds(254, 303, 89, 23);
        btnSignUp.addActionListener(e -> {
            new SignUpView().setVisible(true);
            dispose();
        });
        contentPane.add(btnSignUp);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(487, 337, 89, 23);
        btnClose.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Do you really want to close the application?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        contentPane.add(btnClose);

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Button.background"));
        panel.setBounds(228, 87, 383, 315);
        contentPane.add(panel);

        JLabel background = new JLabel();
        background.setFont(new Font("Tahoma", Font.BOLD, 36));
        background.setBounds(0, 0, 830, 502);
        background.setIcon(new ImageIcon("C:\\Users\\Thisara\\Desktop\\resort.jpg")); // optional: use getResource() for portability
        contentPane.add(background);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginView frame = new LoginView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
