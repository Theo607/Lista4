import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class PascalProcess {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pascal's Triangle");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(250, 250, 250));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightPanel.setBackground(new Color(240, 240, 240));

        ImageIcon icon = new ImageIcon("pascal.png");
        icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon);

        JTextField textField = new JTextField(20);
        textField.setText("5"); // default value
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setPreferredSize(new Dimension(300, 40));

        JButton button = new JButton("Generate Triangle");
        button.setFont(new Font("Arial", Font.PLAIN, 20));

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(textField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(button);

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        frame.add(splitPane);

        button.addActionListener((ActionEvent e) -> {
            String input = textField.getText();
            try {
                int rows = Integer.parseInt(input.trim());

                // Update with the path to your compiled C++ executable
                String executablePath = "./pascal"; // or "pascal.exe" on Windows

                ProcessBuilder pb = new ProcessBuilder(executablePath, String.valueOf(rows));
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine(); // expecting a single line of output
                process.waitFor();

                JTextArea textArea = new JTextArea(20, 40);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
                textArea.setEditable(false);

                if (line != null && !line.isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    for (int i = 0; i < parts.length; i++) {
                        sb.append(parts[i]).append(" ");
                        count++;
                        if (count == i + 1) {
                            sb.append("\n");
                            count = 0;
                        }
                    }
                    textArea.setText(sb.toString());
                } else {
                    textArea.setText("No output from process.");
                }

                rightPanel.removeAll();
                rightPanel.add(textArea);
                rightPanel.revalidate();
                rightPanel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}
