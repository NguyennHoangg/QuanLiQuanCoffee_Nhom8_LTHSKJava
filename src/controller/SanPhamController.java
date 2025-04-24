package controller;

import dao.SanPham_Dao;
import entity.LoaiSanPham;
import entity.SanPham;

import java.util.List;

public class SanPhamController {

    /**
     * Phương thức này sẽ lấy thông tin sản phẩm từ đối tượng SanPham
     * @param sanPham
     * @return
     */
    private SanPham_Dao sanPhamDao = new SanPham_Dao();

    public SanPham getSanPham(SanPham sanPham) {
        return sanPham;
    }

    /**
     * Phương thức này sẽ lấy danh sách sản phẩm từ cơ sở dữ liệu
     * @return dsachSanPham
     */
    public List<SanPham> getDsachSanPham() {
        return new SanPham_Dao().getDsachSanPham();
    }

    /**
     * Phương thức này lấy thông tin sản phẩm từ cơ sở dữ liệu
     * @param maSanPham
     * @return sanPham
     */
    public SanPham getThongTinSanPham(String maSanPham) {
        return sanPhamDao.getThongTinSanPham(maSanPham);
    }

    /**
     * Phương thức này sẽ lấy danh sách loại sản phẩm từ cơ sở dữ liệu
     * @return loaiSanPham
     */
    public LoaiSanPham getLoaiSanPham(String maSanPham) {
        return sanPhamDao.getLoaiSanPham(maSanPham);
    }


}


