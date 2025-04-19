package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HoaDon {
    private String maHoaDon;
    private KhachHang maKhachHang;
    private String maNhanVien;
    private LocalDateTime ngayLap;
    private SanPham sanPham;

    public HoaDon(String maHoaDon, KhachHang maKhachHang, String maNhanVien, LocalDateTime ngayLap, SanPham sanPham) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.sanPham = sanPham;
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

    public KhachHang getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(KhachHang maKhachHang) {
        if (maKhachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được để trống");
        }
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        this.maNhanVien = maNhanVien;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        if (ngayLap == null) {
            throw new IllegalArgumentException("Ngày lập không được để trống");
        }
        this.ngayLap = ngayLap;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm không được để trống");
        }
        this.sanPham = sanPham;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maKhachHang=" + maKhachHang +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", ngayLap=" + ngayLap +
                ", sanPham=" + sanPham +
                '}';
    }

}
