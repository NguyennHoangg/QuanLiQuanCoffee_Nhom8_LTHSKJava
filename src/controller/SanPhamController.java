package controller;

import dao.SanPham_Dao;
import entity.LoaiSanPham;
import entity.SanPham;

import java.util.List;

public class SanPhamController {

    private SanPham_Dao sanPhamDao = new SanPham_Dao();

    public SanPham getSanPham(SanPham sanPham) {
        return sanPham;
    }

    public List<SanPham> getDsachSanPham() {
        return new SanPham_Dao().getDsachSanPham();
    }

    public SanPham getThongTinSanPham(String maSanPham) {
        return sanPhamDao.getThongTinSanPham(maSanPham);
    }

    public LoaiSanPham getLoaiSanPham(String maSanPham) {
        return sanPhamDao.getLoaiSanPham(maSanPham);
    }


}


