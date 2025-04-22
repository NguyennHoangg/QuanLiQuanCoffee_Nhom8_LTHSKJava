package dao;

import connectDB.ConnectDataBase;
import entity.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {

    public static List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();

        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT * FROM TaiKhoan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                boolean quyenHan = rs.getBoolean("quyen");
                TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, quyenHan);
                dsTaiKhoan.add(taiKhoan);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsTaiKhoan;
    }
}

//    public boolean checkCaLamViec(String tenDangNhap) {
//        try {
//            Connection conn = ConnectDataBase.getConnection();
//            String sql = "INSERT INTO Ca;
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.next()) {
//                return true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
//}
