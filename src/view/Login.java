package view;

        import controller.CoffeeController;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;



public class Login implements ActionListener {
            private JTextField txtUsername;
            private JPasswordField txtPassword;
            private JButton btnLogin; // Class-level button
            private CoffeeController controller = new CoffeeController();

            public Login() {
                init();
            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    new Login();
                });
            }

            public void init() {
                JFrame fr = new JFrame("Login");
                fr.setLayout(new BorderLayout());
                fr.setSize(700, 400);
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fr.setLocationRelativeTo(null);
                fr.setResizable(false);


                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridy = 0;
                gbc.gridx = 1;
                JLabel titleLabel = new JLabel("Đăng Nhập", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
                panel.add(titleLabel, gbc);

                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(10, 10, 10, 10);
                JLabel lblUsername = new JLabel("Username:");
                panel.add(lblUsername, gbc);

                gbc.gridx = 1;
                txtUsername = new JTextField();
                txtUsername.setPreferredSize(new Dimension(200, 30));
                panel.add(txtUsername, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel lblPassword = new JLabel("Password:");
                panel.add(lblPassword, gbc);

                gbc.gridx = 1;
                txtPassword = new JPasswordField();
                txtPassword.setPreferredSize(new Dimension(200, 30));
                panel.add(txtPassword, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                btnLogin = new JButton("Login"); // Use the class-level button
                gbc.fill = GridBagConstraints.CENTER;
                panel.add(btnLogin, gbc);
                fr.getRootPane().setDefaultButton(btnLogin);

                btnLogin.addActionListener(this);

                JPanel panel2 = new JPanel();
                panel2.setBackground(new Color(Color.HSBtoRGB(0.5f, 0.5f, 0.5f)));
                panel2.setPreferredSize(new Dimension(370, 400));
                panel2.setLayout(new BorderLayout());

                fr.add(panel2, BorderLayout.WEST);
                fr.add(panel, BorderLayout.EAST);
                fr.setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();

                if (obj.equals(btnLogin)) {
                    String username = txtUsername.getText();
                    String password = new String(txtPassword.getPassword());

                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String userName = txtUsername.getText().trim();
                        String passWord = new String(txtPassword.getPassword()).trim();

                        if (!controller.checkLogin(userName, passWord)) {
                            JOptionPane.showMessageDialog(null, "Đăng nhập không thành công!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }