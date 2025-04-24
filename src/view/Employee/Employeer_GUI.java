package view.Employee;

import controller.HoaDonController;
import controller.UserController;
import view.login.fLogin;
import entity.CaLamViec;

import javax.swing.*;
import java.awt.*;


public class Employeer_GUI {
    private UserController userController;
    private HoaDonController hoaDonController;



    private boolean isShiftOpen = false; // Trạng thái ca làm việc
    private JFrame frame;
    private JPanel mainPanel;      // Panel chứa các màn hình
    private CardLayout cardLayout; // Bộ quản lý layout chuyển panel
    private CaLamViec currentShift; // Lưu thông tin ca làm việc hiện tại

    public Employeer_GUI(UserController userController) {
        this.userController = userController;
        initUI();
    }

    /**
     * Phương thức khởi tạo giao diện người dùng.
     */
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
        caLamViecPanel.setShiftListener(tienMoCa -> {
            try {
                String maCaLamViec = userController.generateShiftId(); // Tạo mã ca làm việc
                CaLamViec caLamViec = userController.createShift(maCaLamViec, tienMoCa); // Truyền đúng kiểu dữ liệu

                if (userController.saveShift(caLamViec)) {
                    currentShift = caLamViec; // Lưu thông tin ca làm việc hiện tại
                    isShiftOpen = true; // Cập nhật trạng thái ca làm việc
                    cardLayout.show(mainPanel, "BAN_HANG"); // Chuyển sang Panel bán hàng
                } else {
                    JOptionPane.showMessageDialog(frame, "Lỗi khi lưu ca làm việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        mainPanel.add(caLamViecPanel, "CA_LAM_VIEC");
        mainPanel.add(new BanHangPanel(userController), "BAN_HANG");
        mainPanel.add(new HoaDonPanel(userController), "HOA_DON");


        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    /**
     * Phương thức tạo sidebar bên trái.
     * @return JPanel sidebar
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 82, 116));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel hiLabel = new JLabel("Wind's Coffee Shop");
        hiLabel.setForeground(Color.WHITE);
        hiLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        hiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(hiLabel);

        // Thêm các nút vào sidebar
        addSidebarButton(sidebar, "BÁN HÀNG", "image/products.png", "BAN_HANG");
        addSidebarButton(sidebar, "HÓA ĐƠN", "image/list.png", "HOA_DON");

        sidebar.add(Box.createVerticalGlue());

        addSidebarButton(sidebar, "ĐÓNG CA", "image/dongca.png", "DONG_CA");
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
            } else if (frameToOpen.equals("DONG_CA")) {
                btn.addActionListener(e -> handleShiftClose());
            } else {
                btn.addActionListener(e -> openFrame(frameToOpen));
            }
        }

        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    /**
     * Phương thức mở frame tương ứng với nút được nhấn.
     * @param frameName Tên frame cần mở.
     */
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


    /**
     * Phương thức xử lý sự kiện đăng xuất.
     */
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

    /**
     * Phương thức xử lý sự kiện đóng ca làm việc.
     */
    private void handleShiftClose() {
        if (!isShiftOpen || currentShift == null) {
            JOptionPane.showMessageDialog(frame, "Không có ca làm việc nào đang mở!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Bạn có chắc chắn muốn đóng ca làm việc?",
                "Xác nhận đóng ca",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Nhập số tiền đóng ca:");
                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double soTienDongCa = Double.parseDouble(input.trim());
                if (soTienDongCa < 0) {
                    JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userController.closeShift(currentShift, soTienDongCa)) {
                    JOptionPane.showMessageDialog(frame,
                            "Ca làm việc đã được đóng thành công!\n" +
                                    "Thời gian kết thúc: " + currentShift.getThoiGianKetThuc() + "\n" +
                                    "Số tiền đóng ca: " + currentShift.getTienDongCa(),
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    isShiftOpen = false;
                    cardLayout.show(mainPanel, "CA_LAM_VIEC");
                } else {
                    JOptionPane.showMessageDialog(frame, "Lỗi khi đóng ca làm việc! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không hợp lệ! Vui lòng nhập một số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
