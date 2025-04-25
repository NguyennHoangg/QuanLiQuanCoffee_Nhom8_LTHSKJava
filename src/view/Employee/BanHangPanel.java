package view.Employee;

import controller.HoaDonController;
import controller.UserController;
import entity.LoaiSanPham;
import entity.SanPham;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import controller.SanPhamController;

public class BanHangPanel extends JPanel implements MouseListener, ActionListener {
    private final JPanel mainPanel;
    private CardLayout cardLayout;


    private JButton btnThanhToan, btnThemGioHang;
    private SanPhamController sanPhamController;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField maSanPhamField, tenSanPhamField, soLuongField, tienKhachDuaField;
    private JLabel donGiaLabel, thanhTienLabel, tienTraLaiLabel;

    private HoaDonController hoaDonController;
    private UserController userController;

    private ThanhToanPanel thanhToanPanel;

    public BanHangPanel(UserController userController, JPanel mainPanel, CardLayout cardLayout, SanPhamController sanPhamController, ThanhToanPanel thanhToanPanel) {
        this.userController = userController;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.sanPhamController = sanPhamController;
        this.thanhToanPanel = thanhToanPanel; // Gán tham chiếu panel thanh toán
        setLayout(new BorderLayout());
        add(northPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);
        addProductToTable();

    table.addMouseListener(this);
    soLuongField.addActionListener(e -> calculateValues());
    tienKhachDuaField.addActionListener(e -> calculateValues());
    btnThanhToan.addActionListener(this);
    btnThemGioHang.addActionListener(this);
    this.hoaDonController = new HoaDonController();
}
    public JPanel northPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin bán hàng"));
        panel.setPreferredSize(new Dimension(800, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("BÁN HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(10, 82, 116));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        maSanPhamField = new JTextField();
        tenSanPhamField = new JTextField();
        soLuongField = new JTextField("0");
        tienKhachDuaField = new JTextField("0");

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
        gbc.gridx = 2;
        panel.add(new JLabel("Nhân viên: "), gbc);
        gbc.gridx = 3;
        panel.add(new JLabel(userController.getCurrentUsername()), gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        btnThemGioHang = new JButton("THÊM VÀO GIỎ HÀNG");
        btnThemGioHang.setPreferredSize(new Dimension(200, 35));
        btnThemGioHang.setBackground(new Color(10, 82, 116));
        btnThemGioHang.setForeground(Color.WHITE);
        panel.add(btnThemGioHang, gbc);

        gbc.gridx = 2;
        btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setPreferredSize(new Dimension(200, 35));
        btnThanhToan.setBackground(new Color(10, 82, 116));
        btnThanhToan.setForeground(Color.WHITE);
        panel.add(btnThanhToan, gbc);

        DocumentListener calListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                calculateValues();
            }
        };

        soLuongField.getDocument().addDocumentListener(calListener);
        tienKhachDuaField.getDocument().addDocumentListener(calListener);

        return panel;
    }

    private JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Đơn giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(table)) {
            int row = table.getSelectedRow();
            if (row != -1) {
                String maSP = tableModel.getValueAt(row, 0).toString();
                String tenSP = tableModel.getValueAt(row, 1).toString();
                String donGia = tableModel.getValueAt(row, 3).toString();

                maSanPhamField.setText(maSP);
                tenSanPhamField.setText(tenSP);
                donGiaLabel.setText(donGia);

                soLuongField.requestFocus();
                calculateValues();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void addProductToTable() {
        tableModel.setRowCount(0);
        for (SanPham sp : sanPhamController.getDsachSanPham()) {
            tableModel.addRow(new Object[]{
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getLoaiSanPham().getTenLoaiSanPham(),
                    sp.getGiaBan()
            });
        }
    }

    private void calculateValues() {
        try {
            int soLuong = soLuongField.getText().isEmpty() ? 0 : Integer.parseInt(soLuongField.getText());
            int donGia = donGiaLabel.getText().isEmpty() ? 0 : Integer.parseInt(donGiaLabel.getText());
            int thanhTien = soLuong * donGia;
            thanhTienLabel.setText(String.valueOf(thanhTien));

            int tienKhach = tienKhachDuaField.getText().isEmpty() ? 0 : Integer.parseInt(tienKhachDuaField.getText());
            int tienTra = tienKhach - thanhTien;
            tienTraLaiLabel.setText(String.valueOf(tienTra));
        } catch (NumberFormatException e) {
            thanhTienLabel.setText("0");
            tienTraLaiLabel.setText("0");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnThemGioHang)) {
            try {
                String maSanPham = maSanPhamField.getText();
                String tenSanPham = tenSanPhamField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                double donGia = Double.parseDouble(donGiaLabel.getText());
                LoaiSanPham loaiSanPham = sanPhamController.getLoaiSanPham(maSanPham);

                if (loaiSanPham == null) {
                    JOptionPane.showMessageDialog(this, "Loại sản phẩm không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra trùng mã trong list của controller
                for (SanPham sp : sanPhamController.getSharedProducts()) {
                    if (sp.getMaSanPham().equals(maSanPham)) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong giỏ hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                SanPham sanPham = new SanPham(maSanPham, tenSanPham, donGia, soLuong, loaiSanPham);
                sanPhamController.getSharedProducts().add(sanPham); // Thêm trực tiếp vào list của controller
                JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào giỏ hàng");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(btnThanhToan)) {
            thanhToanPanel.updateData();
            cardLayout.show(mainPanel, "THANH_TOAN");
        }
    }
}