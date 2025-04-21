create database QuanLiCoffee;

use QuanLiCoffee


-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
    tenDangNhap NVARCHAR(50) PRIMARY KEY, -- Tên đăng nhập (khóa chính)
    matKhau NVARCHAR(50) NOT NULL        -- Mật khẩu
);

-- Bảng LoaiSanPham
CREATE TABLE LoaiSanPham (
    maLoaiSanPham NVARCHAR(50) PRIMARY KEY, -- Mã loại sản phẩm (khóa chính)
    tenLoaiSanPham NVARCHAR(100) NOT NULL  -- Tên loại sản phẩm
);

-- Bảng SanPham
CREATE TABLE SanPham (
    maSanPham NVARCHAR(50) PRIMARY KEY,      -- Mã sản phẩm (khóa chính)
    tenSanPham NVARCHAR(100) NOT NULL,       -- Tên sản phẩm
    giaBan DECIMAL(10, 2) NOT NULL,          -- Giá bán
    soLuong INT NOT NULL,                    -- Số lượng
    maLoaiSanPham NVARCHAR(50) NOT NULL,     -- Mã loại sản phẩm (khóa ngoại)
    FOREIGN KEY (maLoaiSanPham) REFERENCES LoaiSanPham(maLoaiSanPham)
);

-- Bảng NhanVien
CREATE TABLE NhanVien (
    maNhanVien NVARCHAR(50) PRIMARY KEY,     -- Mã nhân viên (khóa chính)
    tenNhanVien NVARCHAR(100) NOT NULL,      -- Tên nhân viên
    tuoi INT NOT NULL,                       -- Tuổi
    diaChi NVARCHAR(200) NOT NULL,           -- Địa chỉ
    soDienThoai NVARCHAR(15) NOT NULL        -- Số điện thoại
);

-- Bảng KhachHang
CREATE TABLE KhachHang (
    maKhachHang NVARCHAR(50) PRIMARY KEY,    -- Mã khách hàng (khóa chính)
    tenKhachHang NVARCHAR(100) NOT NULL,     -- Tên khách hàng
    tuoi INT NOT NULL,                       -- Tuổi
    diaChi NVARCHAR(200) NOT NULL,           -- Địa chỉ
    soDienThoai NVARCHAR(15) NOT NULL        -- Số điện thoại
);

-- Bảng HoaDon
CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(50) PRIMARY KEY,       -- Mã hóa đơn (khóa chính)
    maKhachHang NVARCHAR(50) NOT NULL,       -- Mã khách hàng (khóa ngoại)
    maNhanVien NVARCHAR(50) NOT NULL,        -- Mã nhân viên (khóa ngoại)
    ngayLap DATETIME NOT NULL,               -- Ngày lập hóa đơn
    maSanPham NVARCHAR(50) NOT NULL,         -- Mã sản phẩm (khóa ngoại)
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    FOREIGN KEY (maSanPham) REFERENCES SanPham(maSanPham)
);

-- Bảng ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    maChiTietHoaDon NVARCHAR(50) PRIMARY KEY, -- Mã chi tiết hóa đơn (khóa chính)
    maHoaDon NVARCHAR(50) NOT NULL,          -- Mã hóa đơn (khóa ngoại)
    maSanPham NVARCHAR(50) NOT NULL,         -- Mã sản phẩm (khóa ngoại)
    soLuong INT NOT NULL,                    -- Số lượng
    giaBan DECIMAL(10, 2) NOT NULL,          -- Giá bán
    thanhTien DECIMAL(10, 2) NOT NULL,       -- Thành tiền
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maSanPham) REFERENCES SanPham(maSanPham)
);

INSERT INTO TaiKhoan(tenDangNhap, matKhau) VALUES ( N'admin', N'admin');