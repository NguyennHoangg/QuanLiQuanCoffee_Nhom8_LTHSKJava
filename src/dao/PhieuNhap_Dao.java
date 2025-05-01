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


        try (Connection con = ConnectDataBase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, nl.ngayNhap,nl.ngayHetHan, knl.maKho, knl.tenKho, knl.diaChi\n" +
                     "from [dbo].[NguyenLieu] nl\n" +
                     "join [dbo].[ChiTietKhoNguyenLieu] ctknl on nl.maNguyenLieu = ctknl.maNguyenLieu\n" +
                     "join [dbo].[KhoNguyenLieu] knl on ctknl.maKho = knl.maKho")) {

            while (rs.next()) {
                String maNguyenLieu = rs.getString(1);
                String tenNguyenLieu = rs.getString(2);
                String donViTinh = rs.getString(3);
                double giaNhap = rs.getDouble(4);

                Date ngayNhap = rs.getDate(5);
                Date ngayHetHan = rs.getDate(6);
                String maKho = rs.getString(7);
                String tenKho = rs.getString(8);
                String diaChiKho = rs.getString(9);


                KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
                NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan, khoNguyenLieu);
                dsNguyenLieu.add(nguyenLieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    public boolean addNguyenLieu(NguyenLieu nl) {
        Connection con = null;
        PreparedStatement stmtNL = null;
        PreparedStatement stmtKNL = null;
        boolean success = false;


        try {
            con = ConnectDataBase.getConnection();
            if (con == null) return false;

            con.setAutoCommit(false);

            //them kho nguyen lieu
            String sqlKNL = "INSERT INTO [dbo].[KHONGUYENLIEU] (MAKHO, TENKHO, DIACHI, NGAYNHAP, NGAYHETHAN) VALUES (?, ?, ?, ?, ?)";
            stmtKNL = con.prepareStatement(sqlKNL);
            stmtKNL.setString(1, nl.getKhoNguyenLieu().getMaKho());
            stmtKNL.setString(2, nl.getKhoNguyenLieu().getTenKho());
            stmtKNL.setString(3, nl.getKhoNguyenLieu().getDiaChi());
            stmtKNL.setDate(4, nl.getNgayNhap());
            stmtKNL.setDate(5, nl.getNgayHetHan());


            //them nguyen lieu
            String sqlNL = "INSERT INTO [dbo].[NGUYENLIEU] (MAGUYENLIEU, TENGUYENLIEU, DONVITINH, GIANHAP) VALUES (?, ?, ?, ?)";
            stmtNL = con.prepareStatement(sqlNL);
            stmtNL.setString(1, nl.getMaNguyenLieu());
            stmtNL.setString(2, nl.getTenNguyenLieu());
            stmtNL.setString(3, nl.getDonViTinh());
            stmtNL.setDouble(4, nl.getGiaNhap());
            int rowsAffectedNL = stmtNL.executeUpdate();


            con.commit();;
            success = true;


        }catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtNL != null) stmtNL.close();
                if (stmtKNL != null) stmtKNL.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //xóa nguyên liệu
    public boolean deleteNguyenLieu(String maNguyenLieu) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            con = ConnectDataBase.getConnection();
            if (con == null) return false;

            String sql = "DELETE FROM [dbo].[NGUYENLIEU] WHERE MAGUYENLIEU = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNguyenLieu);

            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //cap nhat nguyen lieu
    public boolean updateNguyenLieu(NguyenLieu nl) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            con = ConnectDataBase.getConnection();
            if (con == null) return false;

            String sql = "UPDATE [dbo].[NGUYENLIEU] SET TENGUYENLIEU = ?, DONVITINH = ?, GIANHAP = ?, NGAYNHAP =?, NGAYHETHAN = ? WHERE MAGUYENLIEU = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nl.getTenNguyenLieu());
            stmt.setString(2, nl.getDonViTinh());
            stmt.setDouble(3, nl.getGiaNhap());
            stmt.setString(4, nl.getMaNguyenLieu());
            stmt.setDate(5, nl.getNgayNhap());
            stmt.setDate(6, nl.getNgayHetHan());

            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //tim kiem bang ten nguyen lieu
    public List<NguyenLieu> searchNguyenLieuByTen(String tenNguyenLieu) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT NL.[MAGUYENLIU], NL.[TENGUYENLIU], NL.[DONVITINH], NL.[GIANHAP], NL.[NGAYNHAP], NL.[NGAYHETHAN], KNL.[MAKHO], KNL.[TENKHO], KNL.[DIACHI]\n" +
                     "FROM [dbo].[NGUYENLIEU] NL\n" +
                     "JOIN [dbo].[CHITIETKHONGUYENLIEU] CTKNL ON NL.[MAGUYENLIU] = CTKNL.[MAGUYENLIEU]\n" +
                     "JOIN [dbo].[KHONGUYENLIEU] KNL ON CTKNL.[MAKHO] = KNL.[MAKHO] WHERE TENGUYENLIU LIKE ?")) {

            stmt.setString(1, "%" + tenNguyenLieu + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNguyenLieu = rs.getString(1);
                String tenNguyenLieuResult = rs.getString(2);
                String donViTinh = rs.getString(3);
                double giaNhap = rs.getDouble(4);
                String maKho = rs.getString(5);
                String tenKho = rs.getString(6);
                String diaChiKho = rs.getString(7);
                Date ngayNhap = rs.getDate(8);
                Date ngayHetHan = rs.getDate(9);

                KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
                NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieuResult, donViTinh, giaNhap, ngayNhap, ngayHetHan, khoNguyenLieu);
                dsNguyenLieu.add(nguyenLieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    //tịm kieu bang ngay nhap
    public List<NguyenLieu> searchNguyenLieuByNgayNhap(Date ngayNhap) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT NL.[MAGUYENLIU], NL.[TENGUYENLIU], NL.[DONVITINH], NL.[GIANHAP], NL.[NGAYNHAP], NL.[NGAYHETHAN], KNL.[MAKHO], KNL.[TENKHO], KNL.[DIACHI]\n" +
                     "FROM [dbo].[NGUYENLIEU] NL\n" +
                     "JOIN [dbo].[CHITIETKHONGUYENLIEU] CTKNL ON NL.[MAGUYENLIU] = CTKNL.[MAGUYENLIEU]\n" +
                     "JOIN [dbo].[KHONGUYENLIEU] KNL ON CTKNL.[MAKHO] = KNL.[MAKHO] WHERE NGAYNHAP = ?")) {

            stmt.setDate(1, ngayNhap);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNguyenLieu = rs.getString(1);
                String tenNguyenLieuResult = rs.getString(2);
                String donViTinh = rs.getString(3);
                double giaNhap = rs.getDouble(4);
                String maKho = rs.getString(5);
                String tenKho = rs.getString(6);
                String diaChiKho = rs.getString(7);
                Date ngayNhapResult = rs.getDate(8);
                Date ngayHetHan = rs.getDate(9);

                KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
                NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieuResult, donViTinh, giaNhap, ngayNhapResult, ngayHetHan, khoNguyenLieu);
                dsNguyenLieu.add(nguyenLieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    //tim kiem bang nagy het han
    public List<NguyenLieu> searchNguyenLieuByNgayHetHan(Date ngayHetHan) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();

        try (Connection con = ConnectDataBase.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT NL.[MAGUYENLIU], NL.[TENGUYENLIU], NL.[DONVITINH], NL.[GIANHAP], NL.[NGAYNHAP], NL.[NGAYHETHAN], KNL.[MAKHO], KNL.[TENKHO], KNL.[DIACHI]\n" +
                     "FROM [dbo].[NGUYENLIEU] NL\n" +
                     "JOIN [dbo].[CHITIETKHONGUYENLIEU] CTKNL ON NL.[MAGUYENLIU] = CTKNL.[MAGUYENLIEU]\n" +
                     "JOIN [dbo].[KHONGUYENLIEU] KNL ON CTKNL.[MAKHO] = KNL.[MAKHO] " +
                     "WHERE NGAYHETHAN = ?")) {

            stmt.setDate(1, ngayHetHan);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNguyenLieu = rs.getString(1);
                String tenNguyenLieuResult = rs.getString(2);
                String donViTinh = rs.getString(3);
                double giaNhap = rs.getDouble(4);
                String maKho = rs.getString(5);
                String tenKho = rs.getString(6);
                String diaChiKho = rs.getString(7);
                Date ngayNhap = rs.getDate(8);
                Date ngayHetHanResult = rs.getDate(9);

                KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
                NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieuResult, donViTinh, giaNhap, ngayNhap, ngayHetHanResult, khoNguyenLieu);
                dsNguyenLieu.add(nguyenLieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

}


