package entity;

import java.sql.Date;

public class NguyenLieu {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donViTinh;
    private double giaNhap;
    private Date ngayNhap;
    private Date NgayHetHan;
    private KhoNguyenLieu khoNguyenLieu;

    //contructor
    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String donViTinh, double giaNhap, Date ngayNhap, Date ngayHetHan, KhoNguyenLieu khoNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.NgayHetHan = ngayHetHan;
        this.khoNguyenLieu = khoNguyenLieu;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        if (maNguyenLieu == null || maNguyenLieu.isEmpty()) {
            throw new IllegalArgumentException("Mã nguyên liệu không được để trống");
        }
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        if (tenNguyenLieu == null || tenNguyenLieu.isEmpty()) {
            throw new IllegalArgumentException("Tên nguyên liệu không được để trống");
        }
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        if (giaNhap < 0) {
            throw new IllegalArgumentException("Giá nhập không hợp lệ");
        }
        this.giaNhap = giaNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Date getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        NgayHetHan = ngayHetHan;
    }

    public void setKhoNguyenLieu(KhoNguyenLieu khoNguyenLieu) {
        this.khoNguyenLieu = khoNguyenLieu;
    }

    public KhoNguyenLieu getKhoNguyenLieu() {
        return khoNguyenLieu;
    }
    @Override
    public String toString() {
        return "NguyenLieu{" +
                "maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", giaNhap=" + giaNhap + '\'' +
                ", ngayNhap=" + ngayNhap + '\'' +
                ", NgayHetHan=" + NgayHetHan + '\'' +
                ", khoNguyenLieu=" + khoNguyenLieu +
                '}';
    }
}