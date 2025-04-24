package controller;

import dao.HoaDon_Dao;
import dao.SanPham_Dao;
import entity.HoaDon;
import entity.SanPham;

import java.util.List;
import java.util.Random;

public class HoaDonController {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private SanPham sanPham = new SanPham();
    private HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private UserController userController = new UserController();


    public String getMaNhanVien(){
        return (userController.getNhanVienByTenDangNhap(userController.getCurrentUsername()).getMaNhanVien());
    }

    public String getTenDangNhap(){
        return userController.getCurrentUsername();
    }

    // src/controller/HoaDonController.java
    public String generateMaHoaDon() {
        String maHoaDon;
        do {
            maHoaDon = "HD" + (1000 + new Random().nextInt(9000)); // Generate random ID in range HD1000-HD9999
        } while (hoaDonDao.isMaHoaDonExists(maHoaDon)); // Check if it already exists
        return maHoaDon;
    }




    public boolean insertHoaDon(HoaDon hoadon) {
        return hoaDonDao.insertHoaDon(hoadon);
    }

    public boolean insertChiTietHoaDon(HoaDon hoadon) {
        return hoaDonDao.insertChiTietHoaDon(hoadon);
    }

    public List<HoaDon> getAllHoaDon(String tenDangNhap) {
        return hoaDonDao.getAllDsachHoaDon(tenDangNhap);
    }
}
