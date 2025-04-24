package view.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import controller.UserController;
import entity.HoaDon;
import controller.HoaDonController;
import entity.NhanVien;

public class HoaDonPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton reFreshBtn;

    private HoaDonController hoaDonController;
    private UserController userController;


    public HoaDonPanel(UserController userController) {
        if (userController == null) {
            throw new IllegalArgumentException("UserController cannot be null");
        }
        this.userController = userController;
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(10, 82, 116));
        add(titleLabel, BorderLayout.NORTH);
        add(createTableScrollPane());
        setData();
        add(createPanel(), BorderLayout.SOUTH);
    }

    public JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã hóa đơn", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian", "Nhân Viên"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(10, 82, 116));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(null);
        return scrollPane;
    }

    public JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        reFreshBtn = new JButton("Làm mới");
        reFreshBtn.setPreferredSize(new Dimension(100, 30));
        reFreshBtn.setBackground(new Color(10, 82, 116));
        reFreshBtn.setForeground(Color.WHITE);
        reFreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        reFreshBtn.addActionListener(e -> {
            setData();
        });
        panel.add(reFreshBtn);
        return panel;
    }

    // Other methods and components can be added here
    public void setData() {
        // Lấy thông tin nhân viên
        NhanVien nhanVien = userController.getNhanVienByTenDangNhap(userController.getCurrentUsername());
        if (nhanVien == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        hoaDonController = new HoaDonController(); // đảm bảo đã khởi tạo
        List<HoaDon> list = hoaDonController.getAllHoaDon(nhanVien.getTaiKhoan().getTenDangNhap());

        tableModel.setRowCount(0); // clear table

        for (HoaDon hd : list) {
            Object[] rowData = {
                    hd.getMaHoaDon(),
                    hd.getSanPham().getTenSanPham(),
                    hd.getSoLuong(),
                    hd.getGiaBan(),
                    hd.getThanhTien(),
                    hd.getNgayLap(),
                    nhanVien.getTenNhanVien() // hoặc lấy tên nhân viên nếu có
            };
            tableModel.addRow(rowData);
        }
    }

}
