package controller;

import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import java.util.List;

public class UserController {

    /**
     * Phương thức này kiểm tra xem tài khoản có hợp lệ hay không.
     * @param username Tên đăng nhập của tài khoản.
     * @param password Mật khẩu của tài khoản.
     * @return true nếu tài khoản hợp lệ, false nếu không.
     */
    public boolean checkLogin(String username, String password) {
        List<TaiKhoan> dsTaiKhoan = TaiKhoan_DAO.getAllTaiKhoan();
        for(TaiKhoan taiKhoan : dsTaiKhoan) {
            if(taiKhoan.getTenDangNhap().equals(username) && taiKhoan.getMatKhau().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Phương thức này kiểm tra xem tài khoản có quyền admin hay không.
     * @param username Tên đăng nhập của tài khoản.
     * @param password Mật khẩu của tài khoản.
     * @return true nếu tài khoản có quyền admin, false nếu không.
     */
    public boolean checkAdmin(String username, String password) {
        List<TaiKhoan> dsTaiKhoan = TaiKhoan_DAO.getAllTaiKhoan();
        for(TaiKhoan taiKhoan : dsTaiKhoan) {
            if(taiKhoan.getTenDangNhap().equals(username) && taiKhoan.getMatKhau().equals(password) && taiKhoan.isQuyenHan()) {
                return true;
            }
        }
        return false;
    }
}
