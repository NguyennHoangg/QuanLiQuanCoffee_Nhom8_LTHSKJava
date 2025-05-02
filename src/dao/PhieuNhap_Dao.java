package dao;

import connectDB.ConnectDataBase;
import entity.KhoNguyenLieu;
import entity.NguyenLieu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhap_Dao {

    public List<NguyenLieu> printAllNguyenLieu() {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();

        String sql = "SELECT nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, nl.ngayNhap, nl.ngayHetHan, " +
                "knl.maKho, knl.tenKho, knl.diaChi " +
                "FROM NguyenLieu nl " +
                "JOIN ChiTietKhoNguyenLieu ctknl ON nl.maNguyenLieu = ctknl.maNguyenLieu " +
                "JOIN KhoNguyenLieu knl ON ctknl.maKho = knl.maKho";

        try (Connection con = ConnectDataBase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu(
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                        rs.getDate(5), rs.getDate(6),
                        new KhoNguyenLieu(rs.getString(7), rs.getString(8), rs.getString(9))
                );
                dsNguyenLieu.add(nl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    public boolean addNguyenLieu(NguyenLieu nl) {

        String sqlNL = "INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCT = "INSERT INTO ChiTietKhoNguyenLieu (maNguyenLieu, maKho, soLuong) VALUES (?, ?, ?)";

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmtNL = con.prepareStatement(sqlNL);
             PreparedStatement stmtCT = con.prepareStatement(sqlCT)) {

            con.setAutoCommit(false);

            stmtNL.setString(1, nl.getMaNguyenLieu());
            stmtNL.setString(2, nl.getTenNguyenLieu());
            stmtNL.setString(3, nl.getDonViTinh());
            stmtNL.setDouble(4, nl.getGiaNhap());
            stmtNL.setDate(5, nl.getNgayNhap());
            stmtNL.setDate(6, nl.getNgayHetHan());
            stmtNL.executeUpdate();

            stmtCT.setString(1, nl.getMaNguyenLieu());
            stmtCT.setString(2, nl.getKhoNguyenLieu().getMaKho());
            stmtCT.setInt(3, nl.getSoLuong());
            stmtCT.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean deleteNguyenLieu(String maNguyenLieu) {
        String deleteChiTietKho = "DELETE FROM ChiTietKhoNguyenLieu WHERE maNguyenLieu = ?";
        String deleteNguyenLieu = "DELETE FROM NguyenLieu WHERE maNguyenLieu = ?";

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmtChiTiet = con.prepareStatement(deleteChiTietKho);
             PreparedStatement stmtNguyenLieu = con.prepareStatement(deleteNguyenLieu)) {

            //Tắt auto commit để thực hiện transaction
            con.setAutoCommit(false);

            //Xóa chi tiết kho nguyên liệu
            stmtChiTiet.setString(1, maNguyenLieu);
            stmtChiTiet.executeUpdate();

            //Xóa nguyên liệu
            stmtNguyenLieu.setString(1, maNguyenLieu);
            stmtNguyenLieu.executeUpdate();
            //Vì dữ liệu liên quan đến chi tiết ở kho nguyên liệu nên phải xóa trc


            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNguyenLieu(NguyenLieu nl) {
        String sql = "UPDATE NguyenLieu SET tenNguyenLieu = ?, donViTinh = ?, giaNhap = ?, ngayNhap = ?, ngayHetHan = ? WHERE maNguyenLieu = ?";

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nl.getTenNguyenLieu());
            stmt.setString(2, nl.getDonViTinh());
            stmt.setDouble(3, nl.getGiaNhap());
            stmt.setDate(4, nl.getNgayNhap());
            stmt.setDate(5, nl.getNgayHetHan());
            stmt.setString(6, nl.getMaNguyenLieu());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NguyenLieu> searchNguyenLieuByTen(String tenNguyenLieu) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();
        String sql = "SELECT nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, nl.ngayNhap, nl.ngayHetHan, " +
                "knl.maKho, knl.tenKho, knl.diaChi " +
                "FROM NguyenLieu nl " +
                "JOIN ChiTietKhoNguyenLieu ctknl ON nl.maNguyenLieu = ctknl.maNguyenLieu " +
                "JOIN KhoNguyenLieu knl ON ctknl.maKho = knl.maKho " +
                "WHERE nl.tenNguyenLieu LIKE ?";

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, "%" + tenNguyenLieu + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu(
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                        rs.getDate(5), rs.getDate(6),
                        new KhoNguyenLieu(rs.getString(7), rs.getString(8), rs.getString(9))
                );
                dsNguyenLieu.add(nl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    public List<NguyenLieu> searchNguyenLieuByNgayNhap(Date ngayNhap) {
        return searchNguyenLieuByDate("ngayNhap", ngayNhap);
    }

    public List<NguyenLieu> searchNguyenLieuByNgayHetHan(Date ngayHetHan) {
        return searchNguyenLieuByDate("ngayHetHan", ngayHetHan);
    }

    private List<NguyenLieu> searchNguyenLieuByDate(String columnName, Date date) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();
        String sql = "SELECT nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, nl.ngayNhap, nl.ngayHetHan, " +
                "knl.maKho, knl.tenKho, knl.diaChi " +
                "FROM NguyenLieu nl " +
                "JOIN ChiTietKhoNguyenLieu ctknl ON nl.maNguyenLieu = ctknl.maNguyenLieu " +
                "JOIN KhoNguyenLieu knl ON ctknl.maKho = knl.maKho " +
                "WHERE nl." + columnName + " = ?";

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu(
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                        rs.getDate(5), rs.getDate(6),
                        new KhoNguyenLieu(rs.getString(7), rs.getString(8), rs.getString(9))
                );
                dsNguyenLieu.add(nl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }
}
