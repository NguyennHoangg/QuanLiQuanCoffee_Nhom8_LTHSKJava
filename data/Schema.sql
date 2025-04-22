create database QuanLiCoffee;

use QuanLiCoffee


-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
                          tenDangNhap NVARCHAR(50) PRIMARY KEY, -- Tên đăng nhập (khóa chính)
                          matKhau NVARCHAR(50) NOT NULL,        -- Mật khẩu
                          quyen bit NOT NULL                    -- Quyền (admin, nhân viên)
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

CREATE TABLE NhanVien (
                          maNhanVien NVARCHAR(50) PRIMARY KEY,     -- Mã nhân viên (khóa chính)
                          tenNhanVien NVARCHAR(100) NOT NULL,      -- Tên nhân viên
                          tuoi INT NOT NULL,                       -- Tuổi
                          diaChi NVARCHAR(200) NOT NULL,           -- Địa chỉ
                          soDienThoai NVARCHAR(15) NOT NULL,       -- Số điện thoại
                          tenDangNhap NVARCHAR(50) NOT NULL,       -- Tên đăng nhập (khóa ngoại)
                          FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
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

CREATE TABLE NguyenLieu (
                            maNguyenLieu NVARCHAR(50) PRIMARY KEY,   -- Mã nguyên liệu (khóa chính)
                            tenNguyenLieu NVARCHAR(100) NOT NULL,    -- Tên nguyên liệu
                            donViTinh NVARCHAR(20) NOT NULL,         -- Đơn vị tính (kg, lít, cái, ...)
                            giaNhap DECIMAL(10, 2) NOT NULL          -- Giá nhập nguyên liệu
);

CREATE TABLE KhoNguyenLieu (
                               maKho NVARCHAR(50) PRIMARY KEY,          -- Mã kho (khóa chính)
                               tenKho NVARCHAR(100) NOT NULL,           -- Tên kho
                               diaChi NVARCHAR(200) NOT NULL            -- Địa chỉ kho
);

CREATE TABLE ChiTietKhoNguyenLieu (
                                      maKho NVARCHAR(50),                      -- Mã kho (khóa ngoại)
                                      maNguyenLieu NVARCHAR(50),               -- Mã nguyên liệu (khóa ngoại)
                                      soLuong INT NOT NULL,                    -- Số lượng nguyên liệu trong kho
                                      PRIMARY KEY (maKho, maNguyenLieu),
                                      FOREIGN KEY (maKho) REFERENCES KhoNguyenLieu(maKho),
                                      FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu)
);


CREATE TABLE NhaCungCap (
                            maNhaCungCap NVARCHAR(50) PRIMARY KEY,   -- Mã nhà cung cấp (khóa chính)
                            tenNhaCungCap NVARCHAR(100) NOT NULL,    -- Tên nhà cung cấp
                            diaChi NVARCHAR(200) NOT NULL,           -- Địa chỉ nhà cung cấp
                            soDienThoai NVARCHAR(15) NOT NULL        -- Số điện thoại nhà cung cấp
);

CREATE TABLE ChiTietNhaCungCap (
                                   maNhaCungCap NVARCHAR(50),               -- Mã nhà cung cấp (khóa ngoại)
                                   maNguyenLieu NVARCHAR(50),               -- Mã nguyên liệu (khóa ngoại)
                                   PRIMARY KEY (maNhaCungCap, maNguyenLieu),
                                   FOREIGN KEY (maNhaCungCap) REFERENCES NhaCungCap(maNhaCungCap),
                                   FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu)
);

CREATE TABLE CaLamViec (
                           maCaLamViec NVARCHAR(50) PRIMARY KEY,    -- Mã ca làm việc (khóa chính)
                           tenDangNhap NVARCHAR(50) NOT NULL,        -- Mã nhân viên (khóa ngoại)
                           thoiGianBatDau DATETIME NOT NULL,        -- Thời gian bắt đầu
                           thoiGianKetThuc DATETIME NOT NULL,       -- Thời gian kết thúc
                           tienMoCa DECIMAL(10, 2) NOT NULL,        -- Tiền mở ca
                           tienDongCa DECIMAL(10, 2) NOT NULL,      -- Tiền đóng ca
                           FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);

INSERT INTO TaiKhoan(tenDangNhap, matKhau, quyen) VALUES ( N'admin', N'admin', 1),
                                                         (N'hoang', N'hoang123', 0),
                                                         (N'yen', N'yen123', 0),
                                                         (N'khavy', N'khavy123', 0);

--Data
INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maLoaiSanPham) VALUES
                                                                                ('SP01', N'Coffee Đen', 25000, 100, 'LSP01'),
                                                                                ('SP02', N'Coffee Sữa', 28000, 120, 'LSP01'),
                                                                                ('SP03', N'Latte', 32000, 80, 'LSP01'),
                                                                                ('SP04', N'Cappuccino', 35000, 90, 'LSP01'),

                                                                                ('SP05', N'Coca Cola', 15000, 200, 'LSP02'),
                                                                                ('SP06', N'Sprite', 15000, 180, 'LSP02'),
                                                                                ('SP07', N'Pepsi', 15000, 160, 'LSP02'),
                                                                                ('SP08', N'7Up', 15000, 170, 'LSP02'),

                                                                                ('SP09', N'Sinh Tố Bơ', 30000, 70, 'LSP03'),
                                                                                ('SP10', N'Sinh Tố Dâu', 30000, 60, 'LSP03'),
                                                                                ('SP11', N'Sinh Tố Xoài', 30000, 65, 'LSP03'),
                                                                                ('SP12', N'Sinh Tố Mãng Cầu', 32000, 55, 'LSP03'),

                                                                                ('SP13', N'Trà Đào', 25000, 90, 'LSP04'),
                                                                                ('SP14', N'Trà Vải', 25000, 85, 'LSP04'),
                                                                                ('SP15', N'Trà Sữa Trân Châu', 28000, 95, 'LSP04'),
                                                                                ('SP16', N'Trà Gừng', 22000, 75, 'LSP04'),

                                                                                ('SP17', N'Thuốc Lá Jet', 25000, 50, 'LSP05'),
                                                                                ('SP18', N'Thuốc Lá Hero', 22000, 40, 'LSP05'),
                                                                                ('SP19', N'Thuốc Lá Craven A', 27000, 45, 'LSP05'),
                                                                                ('SP20', N'Thuốc Lá Esse', 30000, 35, 'LSP05');


INSERT INTO LoaiSanPham (maLoaiSanPham, tenLoaiSanPham) VALUES
                                                            ('LSP01', N'Coffee'),
                                                            ('LSP02', N'Nước Ngọt'),
                                                            ('LSP03', N'Sinh Tố'),
                                                            ('LSP04', N'Trà'),
                                                            ('LSP05', N'Thuốc Lá');
