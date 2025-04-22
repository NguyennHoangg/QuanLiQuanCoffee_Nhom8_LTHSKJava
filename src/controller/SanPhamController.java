package controller;

import dao.SanPham_Dao;
import entity.SanPham;

import java.util.List;

public class SanPhamController {

    public List<SanPham> getDsachSanPham() {
        return new SanPham_Dao().getDsachSanPham();
    }
}
