package view.Employee;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.HoaDonController;
import controller.SanPhamController;
import controller.UserController;
import dao.HoaDon_Dao;
import entity.NhanVien;
import entity.SanPham;
import entity.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;

public class ThanhToanPanel extends JPanel implements ActionListener {

    private SanPhamController sanPhamController;
    private JTable table;
    private JLabel tongTienLabel;
    private DefaultTableModel modelTable;
    private JButton thanhToanButton, xoaButton;
    private HoaDonController hoaDonController;
    private UserController userController;

    /**
     * Constructor của panel thanh toán
     * @param sanPhamController
     * @param userController
     * @param hoaDonController
     */
    public ThanhToanPanel(SanPhamController sanPhamController, UserController userController, HoaDonController hoaDonController) {
        this.sanPhamController = sanPhamController;
        this.userController = userController;
        this.hoaDonController = hoaDonController;
        setLayout(new BorderLayout());
        add(createTable(), BorderLayout.CENTER);
        add(createTongTien(), BorderLayout.SOUTH);
        updateData();
        thanhToanButton.addActionListener(this);
        xoaButton.addActionListener(this);
    }

    /**
     * Phương thức này tạo một JScrollPane chứa bảng hóa đơn.
     * @return JScrollPane chứa bảng hóa đơn.
     */
    public JScrollPane createTable() {
        JLabel titleLabel = new JLabel("THANH TOÁN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(10, 82, 116));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        modelTable = new DefaultTableModel(columnNames, 0);
        table = new JTable(modelTable);
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    /**
     * Phương thức này tạo một JPanel chứa tổng tiền và nút thanh toán.
     * @return JPanel chứa tổng tiền và nút thanh toán.
     */
    public JPanel createTongTien(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));

        tongTienLabel = new JLabel("Tổng tiền: ");
        tongTienLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tongTienLabel.setForeground(new Color(10, 82, 116));

        thanhToanButton = new JButton("Thanh toán");
        thanhToanButton.setPreferredSize(new Dimension(150, 30));
        thanhToanButton.setBackground(new Color(10, 82, 116));
        thanhToanButton.setForeground(Color.WHITE);
        thanhToanButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        thanhToanButton.setFocusPainted(false);

        xoaButton = new JButton("Xóa sản phẩm");
        xoaButton.setPreferredSize(new Dimension(150, 30));
        xoaButton.setBackground(new Color(10, 82, 116));
        xoaButton.setForeground(Color.WHITE);
        xoaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        xoaButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(xoaButton);
        buttonPanel.add(thanhToanButton);
        buttonPanel.setBackground(Color.WHITE);
        panel.add(tongTienLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    /**
     * Phương thức này cập nhật dữ liệu trong bảng thanh toán.
     */
    public void updateData() {
        modelTable.setRowCount(0);
        double tongTien = 0;
        for (SanPham sp : sanPhamController.getSharedProducts()) {
            double thanhTien = sp.getGiaBan() * sp.getSoLuong();
            tongTien += thanhTien;
            modelTable.addRow(new Object[]{
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getLoaiSanPham().getTenLoaiSanPham(),
                    sp.getSoLuong(),
                    sp.getGiaBan(),
                    thanhTien
            });
        }
        tongTienLabel.setText("Tổng tiền: " + tongTien);

    }

    /**
     * Phương thức này xử lý sự kiện khi nút thanh toán được nhấn.
     * @param e Sự kiện ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(thanhToanButton)) {
            try {
                boolean hoaDonInsert = false;
                boolean chiTietHoaDonInsert = false;
                // Lấy thông tin nhân viên
                NhanVien nhanVien = userController.getNhanVienByTenDangNhap(userController.getCurrentUsername());
                if (nhanVien == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String maHoaDon = hoaDonController.generateMaHoaDon(); //lấy mã hóa đơn
                String maNhanVien = nhanVien.getMaNhanVien(); //lay mã nhân viên
                LocalDateTime ngayLap = LocalDateTime.now();
                double tongTien = 0;
                if(hoaDonController.insertHoaDon(maHoaDon, maNhanVien, ngayLap, tongTien)){
                    hoaDonInsert = true;
                }
                for(SanPham sp : sanPhamController.getSharedProducts()){
                    String maChiTietHoaDon = hoaDonController.generateMaChiTietHoaDon();
                    String maSanPham = sp.getMaSanPham();
                    int soLuong = sp.getSoLuong();
                    double donGia = sp.getGiaBan();
                    double thanhTien = soLuong * donGia;
                    tongTien += thanhTien;
                    SanPham sanPham = sanPhamController.getThongTinSanPham(maSanPham);
                    if(hoaDonController.insertChiTietHoaDon(maChiTietHoaDon, maHoaDon, maSanPham, soLuong, donGia, thanhTien)){
                        chiTietHoaDonInsert = true;
                    }
                }



                // Lưu hóa đơn vào cơ sở dữ liệu
                if (hoaDonInsert && chiTietHoaDonInsert) {
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    int comfirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất hóa đơn PDF không?", "Xuất hóa đơn", JOptionPane.YES_NO_OPTION);
                    if (comfirm == JOptionPane.YES_OPTION) {
                        xuatHoaDonPDF(maHoaDon, ngayLap, nhanVien.getTenNhanVien(), tongTien);
                    }
                    modelTable.setRowCount(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o.equals(xoaButton)) {
            int index = table.getSelectedRow();
            sanPhamController.getSharedProducts().remove(index);
            modelTable.removeRow(index);
            JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi giỏ hàng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            updateData();
        }
    }

    /**
     * Phương thức này xuất hóa đơn ra file PDF.
     * @param maHoaDon Mã hóa đơn.
     * @param ngayLap Ngày lập hóa đơn.
     * @param tenNhanVien Tên nhân viên lập hóa đơn.
     * @param tongTien Tổng tiền hóa đơn.
     */
    private void xuatHoaDonPDF(String maHoaDon, LocalDateTime ngayLap, String tenNhanVien, double tongTien) {
        try {
            // Lưu file ở thư mục HoaDonPDF
            String directory = "HoaDonPDF";
            java.nio.file.Path dirPath = java.nio.file.Paths.get(directory);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

           //Tao ten file pdf
            String filename = directory + "/HoaDon_" + maHoaDon + ".pdf";

           //Tạo mới file PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            //Set font chữ Việt Nam
            String fontPath = "fonts/arial.ttf";
            BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontTieuDe = new com.itextpdf.text.Font(unicodeFont, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(unicodeFont, 12);
            com.itextpdf.text.Font fontTableHeader = new com.itextpdf.text.Font(unicodeFont, 12, com.itextpdf.text.Font.BOLD); // Font cho header bảng

            // Tạo tiêu đề
            Paragraph tieuDe = new Paragraph("Hóa Đơn Bán Hàng", fontTieuDe);
            tieuDe.setAlignment(Element.ALIGN_CENTER);
            document.add(tieuDe);



            // Thêm thông tin hóa đơn
            document.add(new Paragraph("Mã Hóa Đơn: " + maHoaDon, fontNormal));
            document.add(new Paragraph("Ngày Lập:  " + ngayLap, fontNormal));
            document.add(new Paragraph("Nhân viên: " + tenNhanVien, fontNormal));
            document.add(new Paragraph("------------------------------------------------------"));

            // Tạo bảng cho thông tin sản phẩm
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            // Set font chữ cho cacs cột
            table.addCell(new com.itextpdf.text.Phrase("Tên Sản Phẩm", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Số Lượng", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Đơn Giá", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Thành Tiền", fontTableHeader));

            // Add product data with font
            for (SanPham sp : sanPhamController.getSharedProducts()) {
                table.addCell(new com.itextpdf.text.Phrase(sp.getTenSanPham(), fontNormal)); // Áp dụng font
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getSoLuong()), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getGiaBan()), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getSoLuong() * sp.getGiaBan()), fontNormal));
            }

            document.add(table);

            // Thêm tổng tiền
            document.add(new Paragraph("------------------------------------------------------"));
            document.add(new Paragraph("Tổng Tiền: " + tongTien , fontNormal));

            // Đóng document
            document.close();

            // Hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Đã xuất hóa đơn ra file: " + filename);

            // Mở file PDF sau khi xuất
            java.awt.Desktop.getDesktop().open(new java.io.File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn PDF");
        }
    }
}






