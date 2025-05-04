package view.Manager;

import javax.swing.*;
import java.awt.*;

public class ThongTinPanel extends JPanel {

    public ThongTinPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Thông tin cá nhân");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel thongTinPanel = showThongTin();
        add(thongTinPanel, BorderLayout.CENTER);
    }

    public JPanel showThongTin() {
        JPanel panel = new JPanel(new BorderLayout());

        // Avatar
        JLabel avatar = new JLabel(new ImageIcon("image/profile_thongtin.png"));
        avatar.setHorizontalAlignment(JLabel.CENTER);
        JPanel avatarPanel = new JPanel(new BorderLayout());
        avatarPanel.add(avatar, BorderLayout.CENTER);
        panel.add(avatarPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        JLabel nameLabel = new JLabel(name);
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        JLabel dobLabel = new JLabel(dob);
        formPanel.add(dobLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        JLabel genderLabel = new JLabel(gender);
        formPanel.add(genderLabel, gbc);




        panel.add(formPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        return panel;
    }
}
