package dao;

import connectDB.ConnectDataBase;
import entity.LoaiSanPham;
import entity.SanPham;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPham_Dao {

    public List<SanPham> getDsachSanPham() {
      List<SanPham> dsachSanPham = new ArrayList<>();
      List<LoaiSanPham> dsachLoaiSanPham = new ArrayList<>();
      try {
          String sql = "SELECT * FROM SanPham "
                  + "INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham";

          ConnectDataBase.getConnection();
          Statement stmt = ConnectDataBase.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);


          while (rs.next()) {
              String maSP = rs.getString("maSanPham");
              String tenSP = rs.getString("tenSanPham");
              double giaBan = rs.getDouble("giaBan");
              int soLuong = rs.getInt("soLuong");
              // Tạo đối tượng LoaiSanPham từ dữ liệu trong bảng
              String maLoai = rs.getString("maLoaiSanPham");
              String tenLoai = rs.getString("tenLoaiSanPham");
              LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai, tenLoai);

              SanPham sanPham = new SanPham(maSP, tenSP, giaBan, soLuong, loaiSanPham);
              dsachSanPham.add(sanPham);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
        return dsachSanPham;
    }

    // src/dao/SanPham_Dao.java
    public SanPham getThongTinSanPham(String maSanPham) {
        String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSanPham); // Set the value for the placeholder

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPham(rs.getString("maSanPham"));
                    sanPham.setTenSanPham(rs.getString("tenSanPham"));
                    sanPham.setGiaBan(rs.getDouble("giaBan"));
                    // Set other fields as needed
                    return sanPham;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product information: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if no product is found
    }

    public LoaiSanPham getLoaiSanPham(String maSanPham){
        try{
            String sql = "SELECT * FROM SanPham "
                    + "INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham "
                    + "WHERE maSanPham = ?";
            ConnectDataBase.getConnection();
            Statement stmt = ConnectDataBase.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String maLoai = rs.getString("maLoaiSanPham");
                String tenLoai = rs.getString("tenLoaiSanPham");
                return new LoaiSanPham(maLoai, tenLoai);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
