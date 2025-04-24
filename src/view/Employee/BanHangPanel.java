package view.Employee;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.HoaDonController;
import controller.UserController;
import entity.HoaDon;
import entity.NhanVien;
import entity.SanPham;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

import controller.SanPhamController;

public class BanHangPanel extends JPanel implements MouseListener, ActionListener {
    private JButton btnThanhToan, btnThemGioHang;
    private SanPhamController sanPhamController;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField maSanPhamField, tenSanPhamField, soLuongField, tienKhachDuaField;
    private JLabel donGiaLabel, thanhTienLabel, tienTraLaiLabel;

    private HoaDonController hoaDonController;
    private UserController userController;



    public BanHangPanel(UserController userController) {
        this.userController = userController; // Assign the passed UserController
        setLayout(new BorderLayout());
        add(northPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);
        this.sanPhamController = new SanPhamController();
        addProductToTable();

        table.addMouseListener(this);
        soLuongField.addActionListener(e -> calculateValues());
        tienKhachDuaField.addActionListener(e -> calculateValues());
        btnThanhToan.addActionListener(this);
        this.hoaDonController = new HoaDonController();
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

        gbc.gridx = 2;
        panel.add(new JLabel("Nhân viên: "), gbc);
        gbc.gridx = 3;
        panel.add( new JLabel(userController.getCurrentUsername()) ,gbc);


        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setPreferredSize(new Dimension(200, 35));
        btnThanhToan.setBackground(new Color(10, 82, 116));
        btnThanhToan.setForeground(Color.WHITE);
        panel.add(btnThanhToan, gbc);


        donGiaLabel.setText("0");


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

    // Hàm tạo bảng dữ liệu sản phẩm và đưa vào ScrollPane
    private JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Đơn giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    /**
     * Phương thức này xử lý sự kiện khi nhấn chuột vào bảng.
     * Nó sẽ lấy thông tin sản phẩm từ bảng và hiển thị vào các trường nhập liệu.
     * Nếu có lỗi trong việc chuyển đổi số lượng hoặc đơn giá, nó sẽ đặt thành tiền và tiền trả lại về 0.
     * Nếu số lượng hoặc đơn giá không hợp lệ, nó sẽ đặt thành tiền và tiền trả lại về 0.
     * Nếu số lượng hoặc đơn giá không được nhập, nó sẽ đặt thành tiền và tiền trả lại về 0.
     */
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
        for (SanPham sp : sanPhamController.getDsachSanPham()) {
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

    /**
     * Phương thức này tính toán giá trị thành tiền và tiền trả lại dựa trên số lượng và đơn giá.
     * Nếu có lỗi trong việc chuyển đổi số lượng hoặc đơn giá, nó sẽ đặt thành tiền và tiền trả lại về 0.
     * Nếu số lượng hoặc đơn giá không hợp lệ, nó sẽ đặt thành tiền và tiền trả lại về 0.
     * Nếu số lượng hoặc đơn giá không được nhập, nó sẽ đặt thành tiền và tiền trả lại về 0.
     */
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


    /**
     * Phương thức này xử lý sự kiện khi nhấn nút thanh toán.
     * Nó sẽ lấy thông tin từ các trường nhập liệu, kiểm tra tính hợp lệ và lưu hóa đơn vào cơ sở dữ liệu.
     * Nếu thành công, nó sẽ hiển thị thông báo thành công và xóa các trường nhập liệu.
     * Nếu có lỗi, nó sẽ hiển thị thông báo lỗi.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThanhToan)) {
            try {
                // Lấy thông tin từ giao diện
                String maSanPham = maSanPhamField.getText();
                String tenSanPham = tenSanPhamField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                double donGia = Double.parseDouble(donGiaLabel.getText());
                double thanhTien = soLuong * donGia;

                // Kiểm tra thông tin
                if (maSanPham.isEmpty() || tenSanPham.isEmpty() || soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy thông tin nhân viên
                NhanVien nhanVien = userController.getNhanVienByTenDangNhap(userController.getCurrentUsername());
                if (nhanVien == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo hóa đơn
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(hoaDonController.generateMaHoaDon());
                hoaDon.setMaNhanVien(nhanVien.getMaNhanVien());
                hoaDon.setNgayLap(LocalDateTime.now());
                hoaDon.setThanhTien(thanhTien);
                hoaDon.setGiaBan(donGia);
                hoaDon.setSoLuong(soLuong);
                hoaDon.setSanPham(sanPhamController.getThongTinSanPham(maSanPham));

                // Lưu hóa đơn vào cơ sở dữ liệu
                if (hoaDonController.insertHoaDon(hoaDon) && hoaDonController.insertChiTietHoaDon(hoaDon)) {
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    int comfirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất hóa đơn PDF không?", "Xuất hóa đơn", JOptionPane.YES_NO_OPTION);
                    if (comfirm == JOptionPane.YES_OPTION) {
                        xuatHoaDonPDF(hoaDon);
                    }
                    clearFields(); // Xóa các trường nhập liệu
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuatHoaDonPDF(HoaDon hoaDon) {
        try {
            // Create the directory dynamically
            String directory = "HoaDonPDF";
            java.nio.file.Path dirPath = java.nio.file.Paths.get(directory);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

            // Define the file path
            String filename = directory + "/HoaDon_" + hoaDon.getMaHoaDon() + ".pdf";

            // Create the PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Load the font for Vietnamese characters
            String fontPath = "fonts/arial.ttf"; // Ensure this path is correct
            BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontTieuDe = new com.itextpdf.text.Font(unicodeFont, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(unicodeFont, 12);

            // Title with Vietnamese diacritical marks
            Paragraph tieuDe = new Paragraph("Hóa Đơn Bán Hàng", fontTieuDe);
            tieuDe.setAlignment(Element.ALIGN_CENTER); // Center the title
            document.add(tieuDe);

            // Add invoice details
            document.add(new Paragraph("Mã Hóa Đơn: " + hoaDon.getMaHoaDon(), fontNormal));
            document.add(new Paragraph("Ngày Lập:  " + hoaDon.getNgayLap(), fontNormal));
            document.add(new Paragraph("Nhân viên: " + hoaDon.getMaNhanVien(), fontNormal));
            document.add(new Paragraph("------------------------------------------------------"));

            // Add product table
            PdfPTable table = new PdfPTable(4); // 4 columns for product details
            table.setWidthPercentage(100);
            table.addCell("Mã Sản Phẩm");
            table.addCell("Tên Sản Phẩm");
            table.addCell("Số Lượng");
            table.addCell("Đơn Giá");

            // Add product data
            table.addCell(hoaDon.getSanPham().getMaSanPham());
            table.addCell(hoaDon.getSanPham().getTenSanPham());
            table.addCell(String.valueOf(hoaDon.getSoLuong()));
            table.addCell(String.valueOf(hoaDon.getGiaBan()));

            document.add(table);

            // Add total amount
            document.add(new Paragraph("------------------------------------------------------"));
            document.add(new Paragraph("Tổng Tiền: " + hoaDon.getThanhTien(), fontNormal));

            // Close the document
            document.close();

            // Show success message
            JOptionPane.showMessageDialog(this, "Đã xuất hóa đơn ra file: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn PDF");
        }
    }


    private void clearFields() {
        maSanPhamField.setText("");
        tenSanPhamField.setText("");
        soLuongField.setText("0");
        donGiaLabel.setText("0");
        thanhTienLabel.setText("0");
        tienKhachDuaField.setText("0");
        tienTraLaiLabel.setText("0");
    }
}