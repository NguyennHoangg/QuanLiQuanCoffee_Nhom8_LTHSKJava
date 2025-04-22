package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductFrame extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;

    public ProductFrame() {
        setLayout(new BorderLayout());

        add(createTextFieldPanel(), BorderLayout.NORTH);
        add(createToolbarPanel(), BorderLayout.SOUTH);
        add(createTableScrollPane(), BorderLayout.CENTER);
    }

    // Hàm tạo các ô text, button và combobox
    /**
     * Phương thức này tạo một panel chứa các ô text, button và combobox để nhập thông tin sản phẩm.
     * @return JPanel chứa các thành phần nhập liệu.
     */
    private JPanel createTextFieldPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 20, 5, 5); // Add padding to the left
    gbc.anchor = GridBagConstraints.WEST;

    panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel maSPLabel = new JLabel("Mã sản phẩm:");
    JTextField maSPField = new JTextField();
    maSPField.setPreferredSize(new Dimension(350, 25)); // Updated size
    panel.add(maSPLabel, gbc);
    gbc.gridx = 1;
    panel.add(maSPField, gbc);

    gbc.gridx = 2;
    JLabel tenSPLabel = new JLabel("Tên sản phẩm:");
    JTextField tenSPField = new JTextField();
    tenSPField.setPreferredSize(new Dimension(350, 25)); // Updated size
    panel.add(tenSPLabel, gbc);
    gbc.gridx = 3;
    panel.add(tenSPField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel loaiSPLabel = new JLabel("Loại sản phẩm:");
    JComboBox<String> loaiSPComboBox = new JComboBox<>(new String[]{"Coffee", "Nước Ngọt", "Sinh tố", "Trà", "Thuốc Lá"});
    loaiSPComboBox.setPreferredSize(new Dimension(350, 30)); // Updated size
    panel.add(loaiSPLabel, gbc);
    gbc.gridx = 1;
    panel.add(loaiSPComboBox, gbc);

    gbc.gridx = 2;
    JLabel soLuongLabel = new JLabel("Số lượng:");
    JTextField soLuongField = new JTextField();
    soLuongField.setPreferredSize(new Dimension(350, 25)); // Updated size
    panel.add(soLuongLabel, gbc);
    gbc.gridx = 3;
    panel.add(soLuongField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel donGiaLabel = new JLabel("Đơn giá:");
    JTextField donGiaField = new JTextField();
    donGiaField.setPreferredSize(new Dimension(350, 25)); // Updated size
    panel.add(donGiaLabel, gbc);

    gbc.gridx = 1;
    panel.add(donGiaField, gbc);

    return panel;
}
    // Hàm tạo thanh công cụ chức năng và tìm kiếm
    /**
     * Phương thức này tạo một thanh công cụ với các nút chức năng và tìm kiếm.
     * @return JPanel chứa thanh công cụ.
     */
    private JPanel createToolbarPanel() {
        JPanel toolbar = new JPanel(new GridLayout(1, 2));
        toolbar.add(createFunctionToolbar());
        toolbar.add(createSearchToolbar());
        return toolbar;
    }

    // Hàm tạo phần các nút chức năng: Thêm, Xóa, Sửa, Xem chi tiết, Xuất PDF
    /**
     * Phương thức này tạo một thanh công cụ với các nút chức năng như Thêm, Xóa, Sửa, Xem chi tiết và Xuất PDF.
     * @return JPanel chứa các nút chức năng.
     */
    private JPanel createFunctionToolbar() {
        JPanel toolbar1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        String[] btnNames = {"Thêm", "Xóa", "Sửa", "Xem chi tiết", "Xuất PDF"};

        for (String name : btnNames) {
            JButton btn = createStyledButton(name);
            toolbar1.add(btn);
        }

        toolbar1.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        toolbar1.setPreferredSize(new Dimension(700, 60));
        return toolbar1;
    }

    // Hàm tạo phần tìm kiếm: ComboBox, TextField và Button
    /**
     * Phương thức này tạo một thanh công cụ tìm kiếm với các thành phần như JComboBox, JTextField và JButton.
     * @return JPanel chứa các thành phần tìm kiếm.
     */
    private JPanel createSearchToolbar() {
        JPanel toolbar2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JComboBox<String> searchComboBox = new JComboBox<>(new String[]{"Tìm kiếm theo mã", "Tìm kiếm theo tên"});
        JTextField searchField = new JTextField(20);
        JButton searchButton = createStyledButton("Tìm kiếm");
        searchButton.setPreferredSize(new Dimension(100, 30));

        toolbar2.add(searchComboBox);
        toolbar2.add(searchField);
        toolbar2.add(searchButton);

        toolbar2.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        toolbar2.setPreferredSize(new Dimension(500, 60));
        return toolbar2;
    }

    // Hàm tạo bảng dữ liệu sản phẩm và đưa vào ScrollPane
    /**
     * Phương thức này tạo một bảng JTable với các cột được định nghĩa và đưa vào một JScrollPane.
     * @return JScrollPane chứa bảng JTable.
     */
    private JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số Lượng", "Đơn giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    // Hàm tạo nút với kiểu dáng chuẩn
    /**
     * Phương thức này tạo một nút JButton với kiểu dáng chuẩn.
     * @param text Văn bản hiển thị trên nút.
     * @return Nút JButton đã được định dạng.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(110, 30));
        button.setFocusPainted(false);
        button.setBorder(null);
        button.setBackground(new Color(10, 82, 116));
        button.setForeground(Color.WHITE);
        return button;
    }

    // Hàm thêm sản phẩm vào bảng

    /**
     * Phương thức này kiểm tra tính hợp lệ của các thông tin sản phẩm trước khi thêm vào bảng.
     * Nếu thông tin hợp lệ, nó sẽ thêm sản phẩm vào bảng và làm mới bảng.
     * @param maSP
     * @param tenSP
     * @param loaiSP
     * @param soLuong
     * @param donGia
     * @return true nếu thông tin hợp lệ và sản phẩm được thêm thành công, false nếu không.
     */
    public boolean validateInput(String maSP, String tenSP, String loaiSP, String soLuong, String donGia) {
        if (maSP.isEmpty() || tenSP.isEmpty() || loaiSP.isEmpty() || soLuong.isEmpty() || donGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(soLuong);
            Double.parseDouble(donGia);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


}
