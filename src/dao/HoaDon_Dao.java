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

    // src/dao/HoaDon_Dao.java
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

    public List<HoaDon> getAllDsachHoaDon(String tenDangNhap) {
        Map<String, HoaDon> hoaDonMap = new HashMap<>();

        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT * FROM HoaDon"
                    + " INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien"
                    + " INNER JOIN TaiKhoan ON NhanVien.tenDangNhap = TaiKhoan.tenDangNhap"
                    + " INNER JOIN ChiTietHoaDon ON HoaDon.maHoaDon = ChiTietHoaDon.maHoaDon"
                    + " INNER JOIN SanPham ON ChiTietHoaDon.maSanPham = SanPham.maSanPham"
                    + " INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham"
                    + " WHERE TaiKhoan.tenDangNhap = ?";

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

                SanPham sp = new SanPham(maSP, tenSP, giaBan, soLuong, new LoaiSanPham(maLoaiSP, tenLoaiSP));

                if (!hoaDonMap.containsKey(maHD)) {
                    List<SanPham> dsSP = new ArrayList<>();
                    dsSP.add(sp);
                    HoaDon hd = new HoaDon(maHD, maNV, ngayLap, dsSP, 0, 0, 0); // số lượng, giá bán, thành tiền sẽ tính sau
                    hoaDonMap.put(maHD, hd);
                } else {
                    hoaDonMap.get(maHD).getDsachSanPham().add(sp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(hoaDonMap.values());
    }


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
            System.err.println("Error checking maChiTietHoaDon existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
