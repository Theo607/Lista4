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
        
                String executablePath = "./pascal"; // or "pascal.exe" on Windows
        
                JTextArea textArea = new JTextArea(20, 40);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
                textArea.setEditable(false);
        
                StringBuilder sb = new StringBuilder();
        
                for (int i = 0; i < rows; i++) {
                    ProcessBuilder pb = new ProcessBuilder(executablePath, String.valueOf(i + 1));
                    pb.redirectErrorStream(true);
                    Process process = pb.start();
        
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = reader.readLine(); // Expecting the line with the Pascal row
                    process.waitFor();
        
                    // Format the row with indentation
                    if (line != null && !line.isEmpty()) {
                        int spaces = rows - i;
                        sb.append(" ".repeat(spaces * 2)); // simple centering
                        sb.append(line.trim()).append("\n");
                    }
                }
        
                textArea.setText(sb.toString());
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
