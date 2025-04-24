package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HoaDon {
    private String maHoaDon;
    private String maNhanVien;
    private LocalDateTime ngayLap;
    private SanPham sanPham;
    private int soLuong;
    private double giaBan;
    private double thanhTien;



    public HoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, SanPham sanPham, int soLuong, double giaBan, double thanhTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    public HoaDon() {

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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", ngayLap=" + ngayLap +
                ", sanPham=" + sanPham +
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                ", thanhTien=" + thanhTien +
                '}';
    }

}
