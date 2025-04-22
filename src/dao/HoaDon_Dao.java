package dao;

import connectDB.ConnectDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
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
}
