package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Manager_GUI {
    public Manager_GUI() {
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("Coffee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // === Sidebar trái ===
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // === Panel chính bên phải (chứa toolbar và bảng) ===
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel toolbar = createToolbar();
        JScrollPane tablePanel = createProductTable();

        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 82, 116));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel hiLabel = new JLabel("HI! Admin");
        hiLabel.setForeground(Color.WHITE);
        hiLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        hiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        sidebar.add(hiLabel);

        String[] upperMenuItems = {
                "SẢN PHẨM", "NHÀ CUNG CẤP", "NHẬP HÀNG", "PHIẾU NHẬP",
                "XUẤT HÀNG", "PHIẾU XUẤT", "TỒN KHO", "TÀI KHOẢN", "THỐNG KÊ"
        };

        String[] bottomMenuItems = {
                "ĐỔI THÔNG TIN", "ĐĂNG XUẤT"
        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);

        // Icon paths
        String[] iconPaths = {
                "src/image/products.png", "src/image/supplier.png", "src/image/import_export.png",
                "src/image/list.png", "src/image/import_export.png", "src/image/list.png",
                "src/image/inventory.png", "src/image/profile_2.png", "src/image/thongke.png"
        };

        // Upper menu buttons with icons
        for (int i = 0; i < upperMenuItems.length; i++) {
            JButton btn = new JButton(upperMenuItems[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setPreferredSize(new Dimension(180, 40));
            btn.setFont(btnFont);
            btn.setBackground(new Color(10, 82, 116));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            btn.setFocusPainted(false);

            // Set icon
            Image imageScale = new ImageIcon(iconPaths[i]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(imageScale);
            btn.setIcon(icon);


            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(btn);
        }

        sidebar.add(Box.createVerticalGlue()); // Push bottom group to the bottom

        //Icon
        String[] bottomIconPaths = {
                "src/image/update.png", "src/image/logout.png"
        };
        // Bottom menu buttons without icons
        for (int i = 0; i < bottomMenuItems.length; i++) {
            JButton btn = new JButton(bottomMenuItems[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setPreferredSize(new Dimension(180, 40));
            btn.setFont(btnFont);
            btn.setBackground(new Color(10, 82, 116));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            btn.setFocusPainted(false);
            sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

            // Set icon
            Image imageScale = new ImageIcon(bottomIconPaths[i]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(imageScale);
            btn.setIcon(icon);

            sidebar.add(btn);
        }

        return sidebar;
    }


    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new GridLayout(1, 2, 0, 20));
        toolbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel toolbar1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // khoảng cách giữa các nút
        String[] btnNames = {"Thêm", "Xóa", "Sửa", "Xem chi tiết", "Xuất PDF"};




        for (String name : btnNames) {
            JButton btn = new JButton(name);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // cỡ chữ vừa phải
            btn.setMargin(new Insets(5, 10, 5, 10)); // padding trong nút
            btn.setPreferredSize(new Dimension(110, 30)); // giảm chiều cao
            btn.setFocusPainted(false); // bỏ viền khi nhấn
            btn.setBorder(null); // bỏ viền
            btn.setBackground(new Color(10, 82, 116)); // màu nền
            btn.setForeground(Color.WHITE); // màu chữ
            toolbar1.add(btn);
        }

        toolbar1.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        toolbar1.setPreferredSize(new Dimension(700, 60));

        JPanel toolbar2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        String[] searchOptions = {"Tìm kiếm theo mã", "Tìm kiếm theo tên"};
        JComboBox<String> searchComboBox = new JComboBox<>(searchOptions);
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");

        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMargin(new Insets(5, 10, 5, 10));
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        toolbar2.add(searchComboBox);
        toolbar2.add(searchField);
        toolbar2.add(searchButton);
        toolbar2.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        toolbar2.setPreferredSize(new Dimension(500, 60));

        toolbar.add(toolbar1);
        toolbar.add(toolbar2);

        return toolbar;
    }


    private JScrollPane createProductTable() {
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số Lượng", "Đơn giá"};
        Object[][] data = {
                {"CF1", "Cà Phê Đá", "Đồ uống", 50, 20000},
                {"CF2", "Cà Phê Sữa", "Đồ uống", 30, 25000},
                {"CF3", "Cà Phê Trứng", "Đồ uống", 20, 30000},
                {"CF4", "Cà Phê Đen", "Đồ uống", 40, 22000},
                {"CF5", "Cà Phê Sữa Đá", "Đồ uống", 25, 27000}
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }
}
