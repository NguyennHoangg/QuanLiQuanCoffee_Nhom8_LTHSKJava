package view.Employee;

import view.Manager.ProductFrame;
import view.Manager.ThongKeFrame;
import view.login.fLogin;

import javax.swing.*;
import java.awt.*;

public class Employeer_GUI {
    private boolean isShiftOpen = false; // Trạng thái ca làm việc
    private JFrame frame;
    private JPanel mainPanel;      // Panel chứa các màn hình
    private CardLayout cardLayout; // Bộ quản lý layout chuyển panel

    public Employeer_GUI() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Coffee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // === Sidebar trái ===
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // === Main panel với CardLayout ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm các panel tương ứng vào mainPanel
        CaLamViecPanel caLamViecPanel = new CaLamViecPanel();
        caLamViecPanel.setShiftListener(() -> {
            isShiftOpen = true; // Cập nhật trạng thái ca làm việc
            cardLayout.show(mainPanel, "BAN_HANG"); // Chuyển sang Panel bán hàng
    });
        mainPanel.add(caLamViecPanel, "CA_LAM_VIEC");
        mainPanel.add(new BanHangPanel(), "BAN_HANG");
//

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 82, 116));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel hiLabel = new JLabel("Xin chào, Employeer!");
        hiLabel.setForeground(Color.WHITE);
        hiLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        hiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(hiLabel);

        // Thêm các nút vào sidebar
        addSidebarButton(sidebar, "BÁN HÀNG", "image/products.png", "BAN_HANG");
        addSidebarButton(sidebar, "HÓA ĐƠN", "image/list.png", "HOA_DON");

        sidebar.add(Box.createVerticalGlue());

        addSidebarButton(sidebar, "ĐÓNG CA", "image/thongke.png", "DONG_CA");
        addSidebarButton(sidebar, "ĐỔI THÔNG TIN", "image/update.png", "UPDATE");
        addSidebarButton(sidebar, "ĐĂNG XUẤT", "image/logout.png", "DANG_XUAT");

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String iconPath, String frameToOpen) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(10, 82, 116));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(scaledImg));

        if (frameToOpen != null) {
            if (frameToOpen.equals("DANG_XUAT")) {
                btn.addActionListener(e -> handleLogout());
            } else {
                btn.addActionListener(e -> openFrame(frameToOpen));
            }
        }

        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void openFrame(String frameName) {
        if (frameName.equals("BAN_HANG") && !isShiftOpen) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Vui lòng mở ca làm việc trước khi vào Bán hàng!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        cardLayout.show(mainPanel, frameName);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose(); // Đóng cửa sổ hiện tại
            new fLogin();
        }
    }


}
