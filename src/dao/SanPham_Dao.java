package dao;

import connectDB.ConnectDataBase;
import entity.LoaiSanPham;
import entity.SanPham;

import java.sql.ResultSet;
import java.sql.Statement;
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
}
