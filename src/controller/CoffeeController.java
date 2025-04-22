package controller;

import dao.TaiKhoan_DAO;
import entity.SanPham;
import entity.TaiKhoan;

import java.util.List;

public class CoffeeController {
    public boolean checkLogin(String username, String password) {
        List<TaiKhoan> dsTaiKhoan = TaiKhoan_DAO.getAllTaiKhoan();
        for(TaiKhoan taiKhoan : dsTaiKhoan) {
            if(taiKhoan.getTenDangNhap().equals(username) && taiKhoan.getMatKhau().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Ham tao maSanPham
    /**
     * Phương thức này sẽ tạo mã sản phẩm mới dựa trên số lượng sản phẩm hiện có trong danh sách sản phẩm.
     * Mã sản phẩm sẽ có định dạng "SP" + số thứ tự sản phẩm (bắt đầu từ 1).
     * @return Mã sản phẩm mới.
     */

    public String generateMaSanPham() {
        // Fetch the list of products (SanPham) from the correct DAO
        List<SanPham> dsSanPham = SanPham_DAO.getAllSanPham(); // Ensure SanPham_DAO exists and is implemented
        int soLuongSanPham = dsSanPham.size();
        String maSanPham = "SP" + (soLuongSanPham + 1);

        // Check for duplicate product codes and adjust if necessary
        for (SanPham sp : dsSanPham) {
            if (sp.getMaSanPham().equals(maSanPham)) {
                soLuongSanPham++;
                maSanPham = "SP" + soLuongSanPham;
            }
        }
        return maSanPham;
    }
}
