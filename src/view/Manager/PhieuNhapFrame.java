package view.Manager;

import com.toedter.calendar.JDateChooser;
import dao.PhieuNhap_Dao;
import entity.NguyenLieu;
import entity.KhoNguyenLieu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
//import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapFrame extends JPanel implements ActionListener, MouseListener {

    private JTextField txtMa;
    private JTextField txtTen;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JTable tbl;
    private DefaultTableModel tblModel;
    private PhieuNhap_Dao PhieuNhap_Dao = new PhieuNhap_Dao();
    private JTextField txtTim;
    private JDateChooser dateNhap;
    private JDateChooser dateHetHan;
    private JTextField txtMaKho;
    private JTextField txtDV;
    private JTextField txtTenKho;
    private JDateChooser dateNhapTim;
    private JDateChooser dateHetHanTim;
    private JTextField txtDC;

    public PhieuNhapFrame() {
        setLayout(new BorderLayout());
        add(createTextFieldPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);


//        loadDataToTable();
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        tbl.addMouseListener(this);
        dateNhapTim.addMouseListener(this);
        dateHetHanTim.addMouseListener(this);
        btnTim.addActionListener(this);


        loadDataToTable();
    }

    private JPanel createTextFieldPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        // Cột 1 (Trái)
        mainPanel.add(createLeftPanel());

        // Cột 2 (Giữa)
        mainPanel.add(createCenterPanel());

        // Cột 3 (Phải)
        mainPanel.add(createRightPanel());

        return mainPanel;
    }

    private JScrollPane createTableScrollPane() {
     String[] cols = {"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá nhập", "Ngày nhập", "Ngày hết hạn", "Mã kho", "Tên Kho", "Địa chỉ"};
        tblModel = new DefaultTableModel(cols, 0);
        tbl = new JTable(tblModel);
        JScrollPane scroll = new JScrollPane(tbl);

        return new JScrollPane(tbl);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 0.2;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mã nguyên liệu:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtMa = new JTextField();
        txtMa.setPreferredSize(new Dimension(200, 25));
        panel.add(txtMa, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Tên nguyên liệu:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTen = new JTextField();
        txtTen.setPreferredSize(new Dimension(200, 25));
        panel.add(txtTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Đơn vị tính:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtDV = new JTextField();
        txtDV.setPreferredSize(new Dimension(200, 25));
        panel.add(txtDV, gbc);

        // Thêm các nút vào Left Panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        btnThem = createRoundButton("Thêm", "image/addEmp.png");
        btnXoa = createRoundButton("Xóa", "image/removeEmp.png");
        btnSua = createRoundButton("Sửa", "image/repairEmp.png");

        Dimension buttonSize = new Dimension(100, 40);
        btnThem.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);

        panel.add(buttonPanel, gbc);

        return panel;
    }


    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Mã kho :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtMaKho = new JTextField();
        txtMaKho.setPreferredSize(new Dimension(200, 25));
        panel.add(txtMaKho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Tên kho :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTenKho = new JTextField();
        txtTenKho.setPreferredSize(new Dimension(200, 25));
        panel.add(txtTenKho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Ngày nhập :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        dateNhap = new JDateChooser();
        dateNhap.setDateFormatString("dd/MM/yyyy");
        dateNhap.setPreferredSize(new Dimension(200, 25));
        panel.add(dateNhap, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Ngày hết hạn:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        dateHetHan = new JDateChooser();
        dateHetHan.setDateFormatString("dd/MM/yyyy");
        dateHetHan.setPreferredSize(new Dimension(200, 25));
        panel.add(dateHetHan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Địa chỉ:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtDC = new JTextField();
        txtDC.setPreferredSize(new Dimension(200, 25));
        panel.add(txtDC, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm: "));

        searchPanel.add(new JLabel("Ngày nhập:"));
        dateNhapTim = new JDateChooser();
        dateNhapTim.setDateFormatString("dd/MM/yyyy");
        dateNhapTim.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(dateNhapTim);

        searchPanel.add(new JLabel("Ngày hết hạn"));
        dateHetHanTim = new JDateChooser();
        dateHetHanTim.setDateFormatString("dd/MM/yyyy");
        dateHetHanTim.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(dateHetHanTim);

        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTim = new JTextField();
        txtTim.setPreferredSize(new Dimension(200, 25));

        Dimension buttonSize = new Dimension(100, 40);
        btnTim = createRoundButton("Tìm", "image/search.png");
        btnTim.setPreferredSize(buttonSize);

        ImageIcon originalIcon = new ImageIcon("image/search.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnTim.setIcon(new ImageIcon(scaledImage));


        searchFieldPanel.add(txtTim);
        searchFieldPanel.add(btnTim);

        searchPanel.add(searchFieldPanel);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JButton createRoundButton (String text, String iconPath){
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setBackground(new Color(10, 82, 116));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            themNguyenLieu();
        } else if (o.equals(btnXoa)) {
            xoaNguyenLieu();
        } else if (o.equals(btnSua)) {
            suaNguyenLieu();
        } else if (o.equals(btnTim)) {
            timNguyenLieuTheoTen();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tbl.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tblModel.getValueAt(row, 0).toString());
            txtTen.setText(tblModel.getValueAt(row, 1).toString());
            txtDV.setText(tblModel.getValueAt(row, 2).toString());
            txtMaKho.setText(tblModel.getValueAt(row, 6).toString());
            txtTenKho.setText(tblModel.getValueAt(row, 7).toString());
            txtDC.setText(tblModel.getValueAt(row, 8).toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date ngayNhap =  dateFormat.parse(tblModel.getValueAt(row, 4).toString());
                dateNhap.setDate(ngayNhap);
                java.util.Date ngayHetHan = dateFormat.parse(tblModel.getValueAt(row, 5).toString());
                dateHetHan.setDate(ngayHetHan);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void loadDataToTable() {
        tblModel.setRowCount(0);
        List<NguyenLieu> dsNguyenLieu = PhieuNhap_Dao.printAllNguyenLieu();
        for (NguyenLieu nguyenLieu : dsNguyenLieu) {
            Object[] row = {
                    nguyenLieu.getMaNguyenLieu(),
                    nguyenLieu.getTenNguyenLieu(),
                    nguyenLieu.getDonViTinh(),
                    nguyenLieu.getGiaNhap(),
                    nguyenLieu.getNgayNhap(),
                    nguyenLieu.getNgayHetHan(),
                    nguyenLieu.getKhoNguyenLieu().getMaKho(),
                    nguyenLieu.getKhoNguyenLieu().getTenKho(),
                    nguyenLieu.getKhoNguyenLieu().getDiaChi()
            };
            tblModel.addRow(row);
        }
    }

    private void clearInputFields() {
        txtMa.setText("");
        txtTen.setText("");
        txtDV.setText("");
        txtMaKho.setText("");
        txtTenKho.setText("");
        txtDC.setText("");
        dateNhap.setDate(null);
        dateHetHan.setDate(null);
    }



    private NguyenLieu layNguyenLieuTuInput() {
        String maNguyenLieu = txtMa.getText().trim();
        String tenNguyenLieu = txtTen.getText().trim();
        String donViTinh = txtDV.getText().trim();
        Date ngayNhap = (Date) dateNhap.getDate();
        Date ngayHetHan = (Date) dateHetHan.getDate();
        String giaNhapStr = txtMaKho.getText().trim();
        String diaChi = txtDC.getText().trim();
        double giaNhap;

        if (!maNguyenLieu.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Mã nguyên liệu không hợp lệ. Chỉ cho phép chữ và số.");
        }

        if (tenNguyenLieu.isEmpty() || !tenNguyenLieu.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Tên nguyên liệu không hợp lệ. Vui lòng nhập tên hợp lệ.");
        }

        if (donViTinh.isEmpty() || !donViTinh.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Đơn vị tính không hợp lệ. Vui lòng nhập đơn vị hợp lệ.");
        }

        if (!giaNhapStr.matches("^\\d+(\\.\\d{1,2})?$")) {
            throw new IllegalArgumentException("Giá nhập không hợp lệ. Vui lòng nhập số.");
        } else {
            giaNhap = Double.parseDouble(giaNhapStr);
        }

        if (ngayNhap == null || ngayHetHan == null) {
            throw new IllegalArgumentException("Ngày nhập và ngày hết hạn không được để trống.");
        }

        if (ngayHetHan.before(ngayNhap)) {
            throw new IllegalArgumentException("Ngày hết hạn không được trước ngày nhập.");
        }


        KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(txtMaKho.getText().trim(), txtTenKho.getText().trim(), diaChi);
        return new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan, khoNguyenLieu);
    }

    private void themNguyenLieu() {
        try {
            NguyenLieu nguyenLieu = layNguyenLieuTuInput();
            if (PhieuNhap_Dao.addNguyenLieu(nguyenLieu)) {
                JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thành công!");
                loadDataToTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thất bại!");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaNguyenLieu() {
        try {
            NguyenLieu nguyenLieu = layNguyenLieuTuInput();
            if (PhieuNhap_Dao.updateNguyenLieu(nguyenLieu)) {
                JOptionPane.showMessageDialog(this, "Sửa nguyên liệu thành công!");
                loadDataToTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa nguyên liệu thất bại!");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    //xoa nguyen lieu co cau hoi yes no
    private void xoaNguyenLieu() {
        int row = tbl.getSelectedRow();
        if (row != -1) {
            String maNguyenLieu = tblModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nguyên liệu này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (PhieuNhap_Dao.deleteNguyenLieu(maNguyenLieu)) {
                    JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thành công!");
                    loadDataToTable();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để xóa.");
        }
    }


    private void timNguyenLieuTheoTen() {
        String maNguyenLieu = txtTim.getText().trim();
        Date ngayNhap = (Date) dateNhapTim.getDate();
        Date ngayHetHan = (Date) dateHetHanTim.getDate();

        List<NguyenLieu> dsNguyenLieu = PhieuNhap_Dao.searchNguyenLieuByTen(maNguyenLieu);
        tblModel.setRowCount(0);
        for (NguyenLieu nguyenLieu : dsNguyenLieu) {
            Object[] row = {
                    nguyenLieu.getMaNguyenLieu(),
                    nguyenLieu.getTenNguyenLieu(),
                    nguyenLieu.getDonViTinh(),
                    nguyenLieu.getGiaNhap(),
                    nguyenLieu.getNgayNhap(),
                    nguyenLieu.getNgayHetHan(),
                    nguyenLieu.getKhoNguyenLieu().getMaKho(),
                    nguyenLieu.getKhoNguyenLieu().getTenKho(),
                    nguyenLieu.getKhoNguyenLieu().getDiaChi()
            };
            tblModel.addRow(row);
            clearInputFields();
        }
    }

    private void timNguyenLieuTheNgayNhap(Date ngayNhap) {

        List<NguyenLieu> danhSachNguyenLieu = PhieuNhap_Dao.searchNguyenLieuByNgayNhap(ngayNhap);
        List<NguyenLieu> ketQuaTimKiem = new ArrayList<>();

        for (NguyenLieu nguyenLieu : danhSachNguyenLieu) {

            if (nguyenLieu.getNgayNhap().equals(ngayNhap)) {
                ketQuaTimKiem.add(nguyenLieu);
            }
        }
        if (ketQuaTimKiem.isEmpty()) {
            resetTable();
        } else {
            resetTable();
            for (NguyenLieu nl : ketQuaTimKiem) {
                Object[] row = {
                        nl.getMaNguyenLieu(),
                        nl.getTenNguyenLieu(),
                        nl.getDonViTinh(),
                        nl.getGiaNhap(),
                        nl.getNgayNhap(),
                        nl.getNgayHetHan(),
                        nl.getKhoNguyenLieu().getMaKho(),
                        nl.getKhoNguyenLieu().getTenKho(),
                        nl.getKhoNguyenLieu().getDiaChi()
                };
                tblModel.addRow(row);
                dateNhapTim.setDate(null);
            }
        }
    }
    private void resetTable() {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
    }


   private void timNguyenLieuTheNgayHetHan(Date ngayHetHan) {

        List<NguyenLieu> danhSachNguyenLieu = PhieuNhap_Dao.searchNguyenLieuByNgayHetHan(ngayHetHan);
        List<NguyenLieu> ketQuaTimKiem = new ArrayList<>();

        for (NguyenLieu nguyenLieu : danhSachNguyenLieu) {

            if (nguyenLieu.getNgayHetHan().equals(ngayHetHan)) {
                ketQuaTimKiem.add(nguyenLieu);
            }
        }
        if (ketQuaTimKiem.isEmpty()) {
            resetTable();
        } else {
            resetTable();
            for (NguyenLieu nl : ketQuaTimKiem) {
                Object[] row = {
                        nl.getMaNguyenLieu(),
                        nl.getTenNguyenLieu(),
                        nl.getDonViTinh(),
                        nl.getGiaNhap(),
                        nl.getNgayNhap(),
                        nl.getNgayHetHan(),
                        nl.getKhoNguyenLieu().getMaKho(),
                        nl.getKhoNguyenLieu().getTenKho(),
                        nl.getKhoNguyenLieu().getDiaChi()
                };
                tblModel.addRow(row);
                dateHetHanTim.setDate(null);
            }
        }
    }


}
