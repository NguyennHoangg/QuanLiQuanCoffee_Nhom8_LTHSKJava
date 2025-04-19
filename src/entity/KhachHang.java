package entity;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private int tuoi;
    private String diaChi;
    private String soDienThoai;

    public KhachHang(String maKhachHang, String tenKhachHang, int tuoi, String diaChi, String soDienThoai) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.tuoi = tuoi;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {

        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        if (tenKhachHang == null || tenKhachHang.isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        this.tenKhachHang = tenKhachHang;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        if (tuoi < 0 || tuoi > 120) {
            throw new IllegalArgumentException("Tuổi không hợp lệ. Tuổi phải từ 0 đến 120");
        }
        this.tuoi = tuoi;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        this.soDienThoai = soDienThoai;
    }
}
