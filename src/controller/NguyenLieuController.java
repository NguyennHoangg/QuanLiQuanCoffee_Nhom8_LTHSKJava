package controller;

import dao.PhieuNhap_Dao;
import entity.KhoNguyenLieu;
import entity.NguyenLieu;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class NguyenLieuController {
    private PhieuNhap_Dao phieuNhapDao;
    private Scanner scanner;

    public NguyenLieuController() {
        this.phieuNhapDao = new PhieuNhap_Dao();
        this.scanner = new Scanner(System.in);
    }

    public void displayAllNguyenLieu() {
        List<NguyenLieu> dsNguyenLieu = phieuNhapDao.printAllNguyenLieu();
        for (NguyenLieu nl : dsNguyenLieu) {
            System.out.println(nl);
        }
    }

    public void addNguyenLieu() {
        System.out.print("Nhập mã nguyên liệu: ");
        String maNguyenLieu = scanner.nextLine();
        System.out.print("Nhập tên nguyên liệu: ");
        String tenNguyenLieu = scanner.nextLine();
        System.out.print("Nhập đơn vị tính: ");
        String donViTinh = scanner.nextLine();
        System.out.print("Nhập giá nhập: ");
        double giaNhap = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline

        System.out.print("Nhập mã kho: ");
        String maKho = scanner.nextLine();
        System.out.print("Nhập tên kho: ");
        String tenKho = scanner.nextLine();
        System.out.print("Nhập địa chỉ kho: ");
        String diaChiKho = scanner.nextLine();

        System.out.print("Nhập ngày nhập (yyyy-mm-dd): ");
        Date ngayNhap = Date.valueOf(scanner.nextLine());
        System.out.print("Nhập ngày hết hạn (yyyy-mm-dd): ");
        Date ngayHetHan = Date.valueOf(scanner.nextLine());

        NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan, new KhoNguyenLieu(maKho, tenKho, diaChiKho));

        if (phieuNhapDao.addNguyenLieu(nguyenLieu)) {
            System.out.println("Thêm nguyên liệu thành công!");
        } else {
            System.out.println("Thêm nguyên liệu thất bại!");
        }
    }

    public void deleteNguyenLieu() {
        System.out.print("Nhập mã nguyên liệu cần xóa: ");
        String maNguyenLieu = scanner.nextLine();

        if (phieuNhapDao.deleteNguyenLieu(maNguyenLieu)) {
            System.out.println("Xóa nguyên liệu thành công!");
        } else {
            System.out.println("Xóa nguyên liệu thất bại!");
        }
    }

    public void updateNguyenLieu() {
        System.out.print("Nhập mã nguyên liệu cần cập nhật: ");
        String maNguyenLieu = scanner.nextLine();


        System.out.print("Nhập tên nguyên liệu mới: ");
        String tenNguyenLieu = scanner.nextLine();
        System.out.print("Nhập đơn vị tính mới: ");
        String donViTinh = scanner.nextLine();
        System.out.print("Nhập giá nhập mới: ");
        double giaNhap = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline
        System.out.print("Nhập ngày nhập mới (yyyy-mm-dd): ");
        Date ngayNhap = Date.valueOf(scanner.nextLine());
        System.out.print("Nhập ngày hết hạn mới (yyyy-mm-dd): ");
        Date ngayHetHan = Date.valueOf(scanner.nextLine());

        NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan, null); // Cần thêm kho nếu cần

        if (phieuNhapDao.updateNguyenLieu(nguyenLieu)) {
            System.out.println("Cập nhật nguyên liệu thành công!");
        } else {
            System.out.println("Cập nhật nguyên liệu thất bại!");
        }
    }

    public void searchNguyenLieuByTen() {
        System.out.print("Nhập tên nguyên liệu cần tìm: ");
        String tenNguyenLieu = scanner.nextLine();
        List<NguyenLieu> dsNguyenLieu = phieuNhapDao.searchNguyenLieuByTen(tenNguyenLieu);

        for (NguyenLieu nl : dsNguyenLieu) {
            System.out.println(nl);
        }
    }

    public void searchNguyenLieuByNgayNhap() {
        System.out.print("Nhập ngày nhập (yyyy-mm-dd): ");
        Date ngayNhap = Date.valueOf(scanner.nextLine());
        List<NguyenLieu> dsNguyenLieu = phieuNhapDao.searchNguyenLieuByNgayNhap(ngayNhap);

        for (NguyenLieu nl : dsNguyenLieu) {
            System.out.println(nl);
        }
    }

    public void searchNguyenLieuByNgayHetHan() {
        System.out.print("Nhập ngày hết hạn (yyyy-mm-dd): ");
        Date ngayHetHan = Date.valueOf(scanner.nextLine());
        List<NguyenLieu> dsNguyenLieu = phieuNhapDao.searchNguyenLieuByNgayHetHan(ngayHetHan);

        for (NguyenLieu nl : dsNguyenLieu) {
            System.out.println(nl);
        }
    }
}