package entity;

public class NguyenLieu {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donViTinh; // Ví dụ: kg, lít, cái
    private double giaNhap; // Giá nhập nguyên liệu

    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String donViTinh, double giaNhap) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
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

    @Override
    public String toString() {
        return "NguyenLieu{" +
                "maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", giaNhap=" + giaNhap +
                '}';
    }
}