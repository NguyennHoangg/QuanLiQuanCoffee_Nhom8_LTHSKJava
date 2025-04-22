package view.Employee;

import entity.SanPham;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import controller.SanPhamController;

public class BanHangPanel extends JPanel implements MouseListener {
    private SanPhamController sanPhamController;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField maSanPhamField, tenSanPhamField, soLuongField, tienKhachDuaField;
    private JLabel donGiaLabel, thanhTienLabel, tienTraLaiLabel;


    public BanHangPanel() {
        setLayout(new BorderLayout());
        add(northPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);
        this.sanPhamController = new SanPhamController();
        addProductToTable();

        table.addMouseListener(this);
    }

    public JPanel northPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin bán hàng"));
        panel.setPreferredSize(new Dimension(800, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL; // Thành phần sẽ giãn theo chiều ngang


        JLabel titleLabel = new JLabel("BÁN HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(10, 82, 116));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Chiếm toàn bộ chiều ngang
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Reset constraints
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;


        maSanPhamField = new JTextField();
        tenSanPhamField = new JTextField();
        soLuongField = new JTextField("0"); // Giá trị mặc định là 0
        tienKhachDuaField = new JTextField("0"); // Giá trị mặc định là 0

        donGiaLabel = new JLabel("0");
        thanhTienLabel = new JLabel("0");
        tienTraLaiLabel = new JLabel("0");

        maSanPhamField.setPreferredSize(new Dimension(250, 25));
        tenSanPhamField.setPreferredSize(new Dimension(250, 25));
        soLuongField.setPreferredSize(new Dimension(250, 25));
        tienKhachDuaField.setPreferredSize(new Dimension(250, 25));


        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        panel.add(maSanPhamField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 3;
        panel.add(tenSanPhamField, gbc);


        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        panel.add(soLuongField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 3;
        panel.add(donGiaLabel, gbc);


        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Thành tiền:"), gbc);
        gbc.gridx = 1;
        panel.add(thanhTienLabel, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Tiền khách đưa:"), gbc);
        gbc.gridx = 3;
        panel.add(tienKhachDuaField, gbc);


        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(new JLabel("Tiền trả lại:"), gbc);
        gbc.gridx = 1;
        panel.add(tienTraLaiLabel, gbc);


        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4; // Nút chiếm toàn bộ chiều ngang
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setPreferredSize(new Dimension(200, 35));
        btnThanhToan.setBackground(new Color(10, 82, 116));
        btnThanhToan.setForeground(Color.WHITE);
        panel.add(btnThanhToan, gbc);


        donGiaLabel.setText("0");


        DocumentListener calListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                calculateValues();
            }
        };

        soLuongField.getDocument().addDocumentListener(calListener);
        tienKhachDuaField.getDocument().addDocumentListener(calListener);

        soLuongField.addActionListener(e -> calculateValues());
        tienKhachDuaField.addActionListener(e -> calculateValues());

        return panel;
    }

    // Hàm tạo bảng dữ liệu sản phẩm và đưa vào ScrollPane
    private JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Đơn giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(table)) {
            int row = table.getSelectedRow();
            if (row != -1) {
                // Lấy thông tin từ bảng
                String maSP = tableModel.getValueAt(row, 0).toString();
                String tenSP = tableModel.getValueAt(row, 1).toString();
                String loaiSP = tableModel.getValueAt(row, 2).toString();
                String donGia = tableModel.getValueAt(row, 3).toString(); // Sửa cột thành 3 (Đơn giá)

                // Hiển thị thông tin sản phẩm đã chọn
                maSanPhamField.setText(maSP);
                tenSanPhamField.setText(tenSP);
                donGiaLabel.setText(donGia);

                // Tự động focus vào ô nhập số lượng
                soLuongField.requestFocus();

                // Tính toán thành tiền nếu số lượng đã được nhập
                try {
                    int soLuong = Integer.parseInt(soLuongField.getText());
                    int thanhTien = soLuong * Integer.parseInt(donGia);
                    thanhTienLabel.setText(String.valueOf(thanhTien));

                    // Tính tiền trả lại nếu có
                    try {
                        int tienKhach = Integer.parseInt(tienKhachDuaField.getText());
                        int tienTra = tienKhach - thanhTien;
                        tienTraLaiLabel.setText(String.valueOf(tienTra));
                    } catch (NumberFormatException ignored) {
                        tienTraLaiLabel.setText("0");
                    }
                } catch (NumberFormatException ignored) {
                    thanhTienLabel.setText("0");
                    tienTraLaiLabel.setText("0");
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addProductToTable() {
        tableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại
        // Thêm sản phẩm vào bảng
        for(SanPham sp : sanPhamController.getDsachSanPham()) {
           tableModel.addRow(new Object[]{
                   sp.getMaSanPham(),
                   sp.getTenSanPham(),
                   sp.getLoaiSanPham().getTenLoaiSanPham(),
                   sp.getGiaBan()
           });
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại
    }

    private void calculateValues() {
        try {
            // Kiểm tra số lượng
            int soLuong = soLuongField.getText().isEmpty() ? 0 : Integer.parseInt(soLuongField.getText());
            // Kiểm tra đơn giá
            int donGia = donGiaLabel.getText().isEmpty() ? 0 : Integer.parseInt(donGiaLabel.getText());
            // Tính thành tiền
            int thanhTien = soLuong * donGia;
            thanhTienLabel.setText(String.valueOf(thanhTien));

            // Tính tiền trả lại nếu có
            try {
                int tienKhach = tienKhachDuaField.getText().isEmpty() ? 0 : Integer.parseInt(tienKhachDuaField.getText());
                int tienTra = tienKhach - thanhTien;
                tienTraLaiLabel.setText(String.valueOf(tienTra));
            } catch (NumberFormatException ignored) {
                tienTraLaiLabel.setText("0");
            }
        } catch (NumberFormatException e) {
            thanhTienLabel.setText("0");
            tienTraLaiLabel.setText("0");
        }
    }
}