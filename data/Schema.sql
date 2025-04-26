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


-- Bảng HoaDon
CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(50) PRIMARY KEY,       -- Mã hóa đơn (khóa chính)
    maNhanVien NVARCHAR(50) NOT NULL,        -- Mã nhân viên (khóa ngoại)
    ngayLap DATETIME NOT NULL,               -- Ngày lập hóa đơn
    tongTien DECIMAL(10, 2) NOT NULL DEFAULT 0, -- Tổng tiền
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

-- Bảng ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
							   maChiTietHoaDon NVARCHAR(50) NOT NULL,
                               maHoaDon NVARCHAR(50) NOT NULL,          -- Mã hóa đơn (khóa ngoại)
                               maSanPham NVARCHAR(50) NOT NULL,         -- Mã sản phẩm (khóa ngoại)
                               soLuong INT NOT NULL,                    -- Số lượng
                               giaBan DECIMAL(10, 2) NOT NULL,          -- Giá bán
                               thanhTien DECIMAL(10, 2) NOT NULL,       -- Thành tiền
                               PRIMARY KEY (maChiTietHoaDon),       -- Khóa chính mới
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
INSERT INTO LoaiSanPham (maLoaiSanPham, tenLoaiSanPham) VALUES
                                                            ('LSP01', N'Coffee'),
                                                            ('LSP02', N'Nước Ngọt'),
                                                            ('LSP03', N'Sinh Tố'),
                                                            ('LSP04', N'Trà'),
                                                            ('LSP05', N'Thuốc Lá'),
															('LSP06', N'Bánh Ngọt'),
															('LSP07', N'Đồ Ăn Vặt');
INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maLoaiSanPham) VALUES
-- LSP01: Coffee
('SP21', N'Americano', 27000, 85, 'LSP01'),
('SP22', N'Mocha', 34000, 75, 'LSP01'),
('SP23', N'Macchiato', 33000, 65, 'LSP01'),
('SP24', N'Coffee Sữa Đá', 29000, 110, 'LSP01'),

-- LSP02: Nước ngọt
('SP25', N'Mirinda Cam', 15000, 190, 'LSP02'),
('SP26', N'Fanta Nho', 15000, 175, 'LSP02'),
('SP27', N'Sarsi', 16000, 140, 'LSP02'),
('SP28', N'Lipton Ice Tea', 17000, 130, 'LSP02'),



-- LSP03: Sinh tố
('SP29', N'Sinh Tố Dưa Hấu', 28000, 60, 'LSP03'),
('SP30', N'Sinh Tố Mít', 31000, 55, 'LSP03'),
('SP31', N'Sinh Tố Chuối', 27000, 70, 'LSP03'),
('SP32', N'Sinh Tố Kiwi', 32000, 50, 'LSP03'),

-- LSP04: Trà
('SP33', N'Trà Chanh', 22000, 100, 'LSP04'),
('SP34', N'Trà Sữa Matcha', 29000, 90, 'LSP04'),
('SP35', N'Trà Sữa Socola', 30000, 85, 'LSP04'),
('SP36', N'Trà Tắc Mật Ong', 23000, 80, 'LSP04'),

-- LSP05: Thuốc lá
('SP37', N'Thuốc Lá Marlboro', 35000, 30, 'LSP05'),
('SP38', N'Thuốc Lá 555', 36000, 25, 'LSP05'),
('SP39', N'Thuốc Lá Zest', 24000, 45, 'LSP05'),
('SP40', N'Thuốc Lá Vinataba', 26000, 50, 'LSP05');

INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maLoaiSanPham) VALUES
-- LSP01: Coffee
('SP41', N'Coffee Cold Brew', 33000, 70, 'LSP01'),
('SP42', N'Coffee Hazelnut', 34000, 65, 'LSP01'),
('SP43', N'Coffee Caramel', 35000, 75, 'LSP01'),

-- LSP02: Nước ngọt
('SP44', N'Nước Cam C2', 12000, 200, 'LSP02'),
('SP45', N'Number One', 14000, 210, 'LSP02'),
('SP46', N'Sting Dâu', 15000, 190, 'LSP02'),

-- LSP03: Sinh tố
('SP47', N'Sinh Tố Dứa', 29000, 60, 'LSP03'),
('SP48', N'Sinh Tố Mãng Cầu Xiêm', 31000, 50, 'LSP03'),
('SP49', N'Sinh Tố Táo', 28000, 55, 'LSP03'),

-- LSP04: Trà
('SP50', N'Trà Sữa Hồng Trà', 27000, 80, 'LSP04'),
('SP51', N'Trà Sữa Oolong', 29000, 85, 'LSP04'),
('SP52', N'Trà Hoa Cúc', 24000, 90, 'LSP04'),

-- LSP05: Thuốc lá
('SP53', N'Thuốc Lá Dunhill', 37000, 20, 'LSP05'),
('SP54', N'Thuốc Lá Kent', 38000, 15, 'LSP05'),

-- LSP06: Bánh ngọt
('SP55', N'Bánh Su Kem', 18000, 120, 'LSP06'),
('SP56', N'Bánh Mì Bơ Tỏi', 22000, 100, 'LSP06'),
('SP57', N'Bánh Flan', 20000, 110, 'LSP06'),
('SP58', N'Bánh Tiramisu', 35000, 70, 'LSP06'),
('SP59', N'Bánh Donut Socola', 25000, 90, 'LSP06'),

-- LSP07: Đồ ăn vặt
('SP60', N'Khoai Tây Chiên', 30000, 130, 'LSP07'),
('SP61', N'Xúc Xích Đức', 28000, 100, 'LSP07'),
('SP62', N'Nem Chua Rán', 25000, 95, 'LSP07'),
('SP63', N'Cá Viên Chiên', 27000, 115, 'LSP07'),
('SP64', N'Bắp Xào', 22000, 105, 'LSP07'),
('SP65', N'Phô Mai Que', 26000, 100, 'LSP07'),

-- Một số món tổng hợp thêm:
('SP66', N'Coffee Vanilla', 34000, 60, 'LSP01'),
('SP67', N'Trà Sữa Thái Xanh', 29000, 85, 'LSP04'),
('SP68', N'Nước Ép Cam', 27000, 90, 'LSP02'),
('SP69', N'Sinh Tố Mâm Xôi', 31000, 50, 'LSP03'),
('SP70', N'Bánh Plan Caramel', 23000, 100, 'LSP06');

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

INSERT INTO NhanVien (maNhanVien, tenNhanVien, tuoi, diaChi, soDienThoai, tenDangNhap) VALUES
                                                                                           ('NV01', N'Hoàng', 25, N'Hà Nội', '0123456789', N'hoang'),
                                                                                           ('NV02', N'Yến', 22, N'TP.Ho Chi Minh', '0987654321', N'yen'),
                                                                                           ('NV03', N'Khavy', 28, N'TP.Ho Chi Minh', '0912345678', N'khavy');

																						   