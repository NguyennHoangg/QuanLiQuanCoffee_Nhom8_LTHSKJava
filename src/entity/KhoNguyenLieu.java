package entity;

import java.util.HashMap;
import java.util.Map;

public class KhoNguyenLieu {
    private String maKho;
    private String tenKho;
    private String diaChi;
    private Map<NguyenLieu, Integer> danhSachNguyenLieu; // Nguyên liệu và số lượng

    public KhoNguyenLieu(String maKho, String tenKho, String diaChi) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaChi = diaChi;
        this.danhSachNguyenLieu = new HashMap<>();
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        if (maKho == null || maKho.isEmpty()) {
            throw new IllegalArgumentException("Mã kho không được để trống");
        }
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        if (tenKho == null || tenKho.isEmpty()) {
            throw new IllegalArgumentException("Tên kho không được để trống");
        }
        this.tenKho = tenKho;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        this.diaChi = diaChi;
    }

    public Map<NguyenLieu, Integer> getDanhSachNguyenLieu() {
        return danhSachNguyenLieu;
    }

    public void themNguyenLieu(NguyenLieu nguyenLieu, int soLuong) {
        if (nguyenLieu == null || soLuong <= 0) {
            throw new IllegalArgumentException("Nguyên liệu hoặc số lượng không hợp lệ");
        }
        danhSachNguyenLieu.put(nguyenLieu, danhSachNguyenLieu.getOrDefault(nguyenLieu, 0) + soLuong);
    }

    public void xoaNguyenLieu(NguyenLieu nguyenLieu) {
        danhSachNguyenLieu.remove(nguyenLieu);
    }

    @Override
    public String toString() {
        return "KhoNguyenLieu{" +
                "maKho='" + maKho + '\'' +
                ", tenKho='" + tenKho + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", danhSachNguyenLieu=" + danhSachNguyenLieu +
                '}';
    }

}