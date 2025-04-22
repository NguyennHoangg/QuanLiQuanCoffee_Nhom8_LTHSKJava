package view.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class CaLamViecPanel extends JPanel implements ActionListener {
    private JTextField soTienField;
    private ShiftListener shiftListener;
    JButton openShiftButton;
    public CaLamViecPanel() {
        setLayout(new BorderLayout());

        // Tiêu đề phía trên
        add(createTitlePanel(), BorderLayout.NORTH);

        // Form ở giữa màn hình
        add(createFormCenterPanel(), BorderLayout.CENTER);
    }

    // Panel chứa tiêu đề
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Vui lòng mở ca làm việc", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(new Color(10, 82, 116));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // Panel chứa form ở giữa màn hình
    private JPanel createFormCenterPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout()); // để căn giữa toàn bộ panel form
        JPanel formPanel = new JPanel(new GridBagLayout());  // panel chứa các trường nhập
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Padding giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Số tiền mở ca:"), gbc);

        gbc.gridx = 1;
        soTienField = new JTextField();
        soTienField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(soTienField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        openShiftButton = new JButton("Mở ca làm việc");
        openShiftButton.setPreferredSize(new Dimension(150, 30));
        openShiftButton.setBackground(new Color(10, 82, 116));
        openShiftButton.setForeground(Color.WHITE);

        openShiftButton.addActionListener(this);
        formPanel.add(openShiftButton, gbc);

        // Đưa form vào giữa bằng GridBagLayout
        outerPanel.add(formPanel);
        return outerPanel;
    }

    /**
     * Phương thức này trả về số tiền mở ca từ ô nhập liệu.
     * @param soTien Số tiền mở ca.
     * @return Giá trị số tiền mở ca.
     */

    public double getTienMoCa(double soTien) {
        return soTienField.getText().isEmpty() ? 0 : Double.parseDouble(soTienField.getText());
    }

    /**
     * Phương thức này trả về thời gian bắt đầu ca làm việc.
     * @param startTime Thời gian bắt đầu ca làm việc.
     * @return Thời gian hiện tại.
     */
    public LocalDateTime getStartTime(LocalDateTime startTime) {
        return LocalDateTime.now();
    }


    public interface ShiftListener {
        void onShiftOpened(); // Callback khi ca làm việc được mở
    }


    /**
     * Phương thức này thiết lập listener cho sự kiện mở ca làm việc.
     * @param shiftListener Listener cho sự kiện mở ca làm việc.
     */
    public void setShiftListener(ShiftListener shiftListener) {
        this.shiftListener = shiftListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(openShiftButton)) {
            LocalDateTime startTime = LocalDateTime.now();
            if (shiftListener != null) {
                shiftListener.onShiftOpened(); // Gọi callback khi mở ca
            }
            JOptionPane.showMessageDialog(this, "Ca làm việc đã được mở!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
