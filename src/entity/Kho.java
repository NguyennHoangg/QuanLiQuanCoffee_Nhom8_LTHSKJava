package entity;

public class Kho {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private int soLuong;
    private NhaCungCap nhaCungCap;

    public Kho(String maNguyenLieu, String tenNguyenLieu, int soLuong) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuong = soLuong;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ. Số lượng phải lớn hơn hoặc bằng 0");
        }
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "Kho{" +
                "maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }
}
