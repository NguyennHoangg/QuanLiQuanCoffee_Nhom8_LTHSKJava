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

    public static boolean insertHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayLap, tongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hoaDon.getMaHoaDon());
            pstmt.setString(2, hoaDon.getMaNhanVien());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(hoaDon.getNgayLap()));
            pstmt.setDouble(4, hoaDon.getThanhTien());

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

    public boolean insertChiTietHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO ChiTietHoaDon ( maHoaDon, maSanPham, soLuong, giaBan, thanhTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hoaDon.getMaHoaDon());
            pstmt.setString(2, hoaDon.getSanPham().getMaSanPham());
            pstmt.setInt(3, hoaDon.getSoLuong());
            pstmt.setDouble(4, hoaDon.getGiaBan());
            pstmt.setDouble(5, hoaDon.getThanhTien());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting ChiTietHoaDon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> getAllDsachHoaDon(String tenDangNhap) {
        List<HoaDon> dsachHoaDon = new ArrayList<>();
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
            pstmt.setString(1, tenDangNhap); // Gán giá trị cho dấu hỏi

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHoaDon");
                String maNV = rs.getString("maNhanVien");
                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
                double tongTien = rs.getDouble("tongTien");
                String maSP = rs.getString("maSanPham");
                int soLuong = rs.getInt("soLuong");
                double giaBan = rs.getDouble("giaBan");
                double thanhTien = rs.getDouble("thanhTien");
                String tenSP = rs.getString("tenSanPham");
                String maLoaiSP = rs.getString("maLoaiSanPham");
                String tenLoaiSP = rs.getString("tenLoaiSanPham");

                SanPham sanPham = new SanPham(maSP, tenSP, giaBan, soLuong, new LoaiSanPham(maLoaiSP, tenLoaiSP));
                HoaDon hoaDon = new HoaDon(maHD, maNV, ngayLap, sanPham, soLuong, giaBan, thanhTien);
                dsachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsachHoaDon;
    }

}
