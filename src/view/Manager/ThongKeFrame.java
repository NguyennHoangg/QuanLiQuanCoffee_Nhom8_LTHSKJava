package view.Manager;

            import javax.swing.*;
            import java.awt.*;
            import com.toedter.calendar.JDateChooser;

            public class ThongKeFrame extends JPanel {
                private JRadioButton ngayButton, tuanButton, thangButton, namButton;

                public ThongKeFrame() {
                    setLayout(new BorderLayout());
                    add(createTextFieldPanel(), BorderLayout.NORTH);
                    add(LineChart(), BorderLayout.CENTER);
                }

                /**
                 * Tao panel chứa các trường văn bản và nút bấm.
                 *
                 * @return JPanel chứa các trường văn bản và nút bấm.
                 */
                private JPanel createTextFieldPanel() {
                    // Create panel with GridBagLayout
                    JPanel panel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.insets = new Insets(5, 20, 5, 5);
                    gbc.anchor = GridBagConstraints.WEST;

                    panel.setBorder(BorderFactory.createTitledBorder("Thống kê"));

                    // Row 0: Title
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.CENTER; // Center the title
                    JLabel titleLabel = new JLabel("THỐNG KÊ");
                    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                    titleLabel.setForeground(new Color(10, 82, 116));
                    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    panel.add(titleLabel, gbc);

                    // Row 1: "Thống kê theo"
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    JLabel thongKeLabel = new JLabel("Thống kê theo: ");
                    panel.add(thongKeLabel, gbc);

                    gbc.gridx = 1;
                    ngayButton = new JRadioButton("Ngày");
                    ngayButton.setSelected(true);
                    panel.add(ngayButton, gbc);

                    gbc.gridx = 2;
                    tuanButton = new JRadioButton("Tuần");
                    panel.add(tuanButton, gbc);

                    gbc.gridx = 3;
                    thangButton = new JRadioButton("Tháng");
                    panel.add(thangButton, gbc);

                    gbc.gridx = 4;
                    namButton = new JRadioButton("Năm");
                    panel.add(namButton, gbc);

                    ButtonGroup buttonGroup = new ButtonGroup();
                    buttonGroup.add(ngayButton);
                    buttonGroup.add(tuanButton);
                    buttonGroup.add(thangButton);
                    buttonGroup.add(namButton);

                    // Row 2: "Từ ngày"
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 5;
                    JLabel tuNgayLabel = new JLabel("Từ ngày: ");
                    panel.add(tuNgayLabel, gbc);

                    gbc.gridx = 1;
                    JDateChooser tuNgayField = new JDateChooser();
                    tuNgayField.setDateFormatString("dd/MM/yyyy");
                    tuNgayField.setPreferredSize(new Dimension(350, 25));
                    panel.add(tuNgayField, gbc);

                    // Row 3: "Đến ngày"
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    JLabel denNgayLabel = new JLabel("Đến ngày: ");
                    panel.add(denNgayLabel, gbc);

                    gbc.gridx = 1;
                    JDateChooser denNgayField = new JDateChooser();
                    denNgayField.setDateFormatString("dd/MM/yyyy");
                    denNgayField.setPreferredSize(new Dimension(350, 25));
                    panel.add(denNgayField, gbc);

                    // Row 4: "Thống kê"
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.gridwidth = 1;
                    gbc.anchor = GridBagConstraints.CENTER; // Center the button
                    JButton thongKeButton = new JButton("Thống kê");
                    thongKeButton.setPreferredSize(new Dimension(100, 25));
                    thongKeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    thongKeButton.setBackground(new Color(10, 82, 116));
                    thongKeButton.setForeground(Color.WHITE);
                    thongKeButton.setFocusPainted(false);
                    panel.add(thongKeButton, gbc);

                    return panel;
                }

                private JPanel LineChart() {
                    JPanel chartPanel = new JPanel();
                    chartPanel.setLayout(new BorderLayout());
                    chartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ"));

                    // Placeholder for the chart
                    JLabel placeholderLabel = new JLabel("Biểu đồ sẽ hiển thị ở đây");
                    placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    placeholderLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    chartPanel.add(placeholderLabel, BorderLayout.CENTER);

                    return chartPanel;
                }
            }