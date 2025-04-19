package entity;

public class ChiTietHoaDon {
    private String maChiTietHoaDon;
    private String maHoaDon;
    private String maSanPham;
    private int soLuong;
    private double giaBan;
    private double thanhTien;

    public ChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, String maSanPham, int soLuong, double giaBan) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = soLuong * giaBan;
    }

    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }
    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        if (maChiTietHoaDon == null || maChiTietHoaDon.isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết hóa đơn không được để trống");
        }
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        if (maHoaDon == null || maHoaDon.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        this.maHoaDon = maHoaDon;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        if (maSanPham == null || maSanPham.isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được để trống");
        }
        this.maSanPham = maSanPham;
    }


    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ. Số lượng phải lớn hơn hoặc bằng 0");
        }
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        if (giaBan < 0) {
            throw new IllegalArgumentException("Giá bán không hợp lệ. Giá bán phải lớn hơn hoặc bằng 0");
        }
        this.giaBan = giaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        if (thanhTien < 0) {
            throw new IllegalArgumentException("Thành tiền không hợp lệ. Thành tiền phải lớn hơn hoặc bằng 0");
        }
        this.thanhTien = thanhTien;
    }
    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maChiTietHoaDon='" + maChiTietHoaDon + '\'' +
                ", maHoaDon='" + maHoaDon + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
