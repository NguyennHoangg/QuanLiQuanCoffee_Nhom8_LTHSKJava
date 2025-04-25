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
    private JButton thanhToanButton;
    private HoaDonController hoaDonController;
    private UserController userController;

    public ThanhToanPanel(SanPhamController sanPhamController, UserController userController, HoaDonController hoaDonController) {
        this.sanPhamController = sanPhamController;
        this.userController = userController;
        this.hoaDonController = hoaDonController;
        setLayout(new BorderLayout());
        add(createTable(), BorderLayout.CENTER);
        add(createTongTien(), BorderLayout.SOUTH);
        updateData();
        thanhToanButton.addActionListener(this);
    }

    // Method to create the table and add it to the panel
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
        panel.add(tongTienLabel, BorderLayout.WEST);
        panel.add(thanhToanButton, BorderLayout.EAST);

        return panel;
    }

    public void updateData() {
        modelTable.setRowCount(0);
        double tongTien = 0;
        System.out.println("Số lượng sản phẩm trong sharedProducts: " + sanPhamController.getSharedProducts().size());
        for (SanPham sp : sanPhamController.getSharedProducts()) {
            System.out.println("SP: " + sp.getMaSanPham() + " - " + sp.getTenSanPham());
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

                String maHoaDon = hoaDonController.generateMaHoaDon();
                String maNhanVien = nhanVien.getMaNhanVien();
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

                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuatHoaDonPDF(String maHoaDon, LocalDateTime ngayLap, String tenNhanVien, double tongTien) {
        try {
            // Create the directory dynamically
            String directory = "HoaDonPDF";
            java.nio.file.Path dirPath = java.nio.file.Paths.get(directory);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

            // Define the file path
            String filename = directory + "/HoaDon_" + maHoaDon + ".pdf";

            // Create the PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Load the font for Vietnamese characters
            String fontPath = "fonts/arial.ttf"; // Ensure this path is correct
            BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontTieuDe = new com.itextpdf.text.Font(unicodeFont, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(unicodeFont, 12);
            com.itextpdf.text.Font fontTableHeader = new com.itextpdf.text.Font(unicodeFont, 12, com.itextpdf.text.Font.BOLD); // Font cho header bảng

            // Title with Vietnamese diacritical marks
            Paragraph tieuDe = new Paragraph("Hóa Đơn Bán Hàng", fontTieuDe);
            tieuDe.setAlignment(Element.ALIGN_CENTER); // Center the title
            document.add(tieuDe);



            // Add invoice details
            document.add(new Paragraph("Mã Hóa Đơn: " + maHoaDon, fontNormal));
            document.add(new Paragraph("Ngày Lập:  " + ngayLap, fontNormal));
            document.add(new Paragraph("Nhân viên: " + tenNhanVien, fontNormal));
            document.add(new Paragraph("------------------------------------------------------"));

            // Add product table
            PdfPTable table = new PdfPTable(4); // 4 columns for product details
            table.setWidthPercentage(100);

            // Add table header with font
            table.addCell(new com.itextpdf.text.Phrase("Mã Sản Phẩm", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Tên Sản Phẩm", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Số Lượng", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Đơn Giá", fontTableHeader));

            // Add product data with font
            for (SanPham sp : sanPhamController.getSharedProducts()) {
                table.addCell(new com.itextpdf.text.Phrase(sp.getMaSanPham(), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(sp.getTenSanPham(), fontNormal)); // Áp dụng font
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getSoLuong()), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getGiaBan()), fontNormal));
            }

            document.add(table);

            // Add total amount
            document.add(new Paragraph("------------------------------------------------------"));
            document.add(new Paragraph("Tổng Tiền: " + tongTien , fontNormal));

            // Close the document
            document.close();

            // Show success message
            JOptionPane.showMessageDialog(this, "Đã xuất hóa đơn ra file: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn PDF");
        }
    }
}






