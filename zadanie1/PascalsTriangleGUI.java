import javax.swing.*;
import java.awt.*;

class PascalTriangle {
    private int[][] triangle;
    private int rows;

    public PascalTriangle(int rows) {
        this.rows = rows;
        triangle = new int[rows][rows];
        generateTriangle();
    }

    private void generateTriangle() {
        for (int i = 0; i < rows; i++) {
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
    }
    public int[][] getTriangle() {
        return triangle;
    }
    public int[] getRow(int row) {
        return triangle[row];
    }
    public int getElement(int row, int col) {
        return triangle[row][col];
    }
    public int getRows() {
        return rows;
    }
}

public class PascalsTriangleGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pascal's Triangle");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel rightPanel = new JPanel();

        Color backgroundColor = new Color(250, 250, 250);
        leftPanel.setBackground(backgroundColor);
        ImageIcon icon = new ImageIcon("pascal.png");
        icon.setImage(icon.getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon);
        JTextField textField = new JTextField(20);
        textField.setText("Enter number of rows: ");
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton button = new JButton("Generate Triangle");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        leftPanel.add(iconLabel);
        leftPanel.add(textField);
        leftPanel.add(button);
        button.addActionListener(e -> {
            String input = textField.getText();
            int rows = Integer.parseInt(input);
            PascalTriangle pascalTriangle = new PascalTriangle(rows);
            int[][] triangle = pascalTriangle.getTriangle();
            JTextArea textArea = new JTextArea(20, 40);
            textArea.setFont(new Font("Arial", Font.PLAIN, 20));
            textArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <= i; j++) {
                    sb.append(triangle[i][j]).append(" ");
                }
                sb.append("\n");
            }
            textArea.setText(sb.toString());
            rightPanel.removeAll();
            rightPanel.add(textArea);
            rightPanel.revalidate();
            rightPanel.repaint();
        });
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        backgroundColor = new Color(240, 240, 240);
        rightPanel.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        frame.add(splitPane);

        frame.setVisible(true);
    }
}
