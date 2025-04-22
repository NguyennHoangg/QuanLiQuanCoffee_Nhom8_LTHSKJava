package view;

import javax.swing.*;
import java.awt.*;

public class ProductFrame extends JPanel {
    public ProductFrame() {
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel(new GridLayout(1, 2));
        JPanel toolbar1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        String[] btnNames = {"Thêm", "Xóa", "Sửa", "Xem chi tiết", "Xuất PDF"};

        for (String name : btnNames) {
            JButton btn = new JButton(name);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(110, 30));
            btn.setFocusPainted(false);
            btn.setBorder(null);
            btn.setBackground(new Color(10, 82, 116));
            btn.setForeground(Color.WHITE);
            toolbar1.add(btn);
        }
        toolbar1.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        toolbar1.setPreferredSize(new Dimension(700, 60));

        JPanel toolbar2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JComboBox<String> searchComboBox = new JComboBox<>(new String[]{"Tìm kiếm theo mã", "Tìm kiếm theo tên"});
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");

        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        toolbar2.add(searchComboBox);
        toolbar2.add(searchField);
        toolbar2.add(searchButton);
        toolbar2.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        toolbar2.setPreferredSize(new Dimension(500, 60));

        toolbar.add(toolbar1);
        toolbar.add(toolbar2);

        add(toolbar, BorderLayout.NORTH);

        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số Lượng", "Đơn giá"};
        Object[][] data = {
                {"CF1", "Cà Phê Đá", "Đồ uống", 50, 20000},
                {"CF2", "Cà Phê Sữa", "Đồ uống", 30, 25000},
                {"CF3", "Cà Phê Trứng", "Đồ uống", 20, 30000},
                {"CF4", "Cà Phê Đen", "Đồ uống", 40, 22000},
                {"CF5", "Cà Phê Sữa Đá", "Đồ uống", 25, 27000}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }
}
