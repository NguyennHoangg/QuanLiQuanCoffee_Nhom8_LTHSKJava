package dao;

import connectDB.ConnectDataBase;
import entity.HoaDon;
import entity.LoaiSanPham;
import entity.SanPham;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDon_Dao {

    public static Map<String, Integer> getDoanhThuTheoThang(int thang, int nam) {
        Map<String, Integer> doanhThu = new HashMap<>();
        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT MONTH(ngayLap) AS thang, YEAR(ngayLap) AS nam, SUM(tongTien) AS doanhThu " +
                    "FROM HoaDon " +
                    "WHERE MONTH(ngayLap) = ? AND YEAR(ngayLap) = ? " +
                    "GROUP BY MONTH(ngayLap), YEAR(ngayLap)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, thang);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doanhThu;
    }

    /**
     * Phương thức này sẽ lấy danh sách hóa đơn từ cơ sở dữ liệu
     * @return true nếu thêm đc vào csdl và ngược lại
     */
    public static boolean insertHoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, double tongTien) {
        String sql = "INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayLap, tongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHoaDon);
            pstmt.setString(2, maNhanVien);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(ngayLap));
            pstmt.setDouble(4, tongTien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting HoaDon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

   /**
     * Phương thức này sẽ kiểm tra xem mã hóa đơn đã tồn tại trong cơ sở dữ liệu hay chưa
     * @param maHoaDon
     * @return true nếu mã hóa đơn đã tồn tại, false nếu không
     */
    public boolean isMaHoaDonExists(String maHoaDon) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking maHoaDon existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức này sẽ thêm chi tiết hóa đơn vào cơ sở dữ liệu
     * @param maChiTietHoaDon
     * @param maHoaDon
     * @param maSanPham
     * @param soLuong
     * @param giaBan
     * @param thanhTien
     * @return true nếu thêm đc vào csdl và ngược lại
     */
    public boolean insertChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, String maSanPham, int soLuong, double giaBan, double thanhTien) {
        String sql = "INSERT INTO ChiTietHoaDon ( maChiTietHoaDon, maHoaDon, maSanPham, soLuong, giaBan, thanhTien) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maChiTietHoaDon);
            pstmt.setString(2, maHoaDon);
            pstmt.setString(3, maSanPham);
            pstmt.setInt(4, soLuong);
            pstmt.setDouble(5, giaBan);
            pstmt.setDouble(6, thanhTien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting ChiTietHoaDon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức này sẽ lấy danh sách hóa đơn từ cơ sở dữ liệu
     * @param tenDangNhap
     * @return danh sách hóa đơn
     */
    public List<HoaDon> getAllDsachHoaDon(String tenDangNhap) {
        List<HoaDon> dsachHoaDon = new ArrayList<>();
        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT * FROM HoaDon " +
                    "INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien " +
                    "INNER JOIN TaiKhoan ON NhanVien.tenDangNhap = TaiKhoan.tenDangNhap " +
                    "INNER JOIN ChiTietHoaDon ON HoaDon.maHoaDon = ChiTietHoaDon.maHoaDon " +
                    "INNER JOIN SanPham ON ChiTietHoaDon.maSanPham = SanPham.maSanPham " +
                    "INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham " +
                    "WHERE TaiKhoan.tenDangNhap = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenDangNhap);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHoaDon");
                String maNV = rs.getString("maNhanVien");
                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();

                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                double giaBan = rs.getDouble("giaBan");
                int soLuong = rs.getInt("soLuong");
                double thanhTien = rs.getDouble("thanhTien");

                String maLoaiSP = rs.getString("maLoaiSanPham");
                String tenLoaiSP = rs.getString("tenLoaiSanPham");

                LoaiSanPham loai = new LoaiSanPham(maLoaiSP, tenLoaiSP);
                SanPham sp = new SanPham(maSP, tenSP, giaBan, soLuong, loai);
                List<SanPham> danhSachSanPham = new ArrayList<>();
                danhSachSanPham.add(sp);

                HoaDon hoaDon = new HoaDon(maHD, maNV, ngayLap, danhSachSanPham, soLuong, giaBan, thanhTien);
                dsachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsachHoaDon;
    }


    /**
     * Phương thức này sẽ kiểm tra xem mã chi tiết hóa đơn đã tồn tại trong cơ sở dữ liệu hay chưa
     * @param maChiTietHoaDon
     * @return true nếu mã chi tiết hóa đơn đã tồn tại, false nếu không
     */
    public boolean isMaChiTietHoaDonExists(String maChiTietHoaDon) {
        String sql = "SELECT COUNT(*) FROM ChiTietHoaDon WHERE maChiTietHoaDon = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maChiTietHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
