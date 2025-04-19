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

        try{
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT * FROM TaiKhoan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau);
                dsTaiKhoan.add(taiKhoan);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsTaiKhoan;
    }
}
