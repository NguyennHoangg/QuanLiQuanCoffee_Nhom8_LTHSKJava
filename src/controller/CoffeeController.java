package controller;

import dao.TaiKhoan_DAO;
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
}
