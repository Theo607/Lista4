package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Priority;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;


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

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pascal's Triangle");
        stage.setWidth(1280);
        stage.setHeight(720);

        // Left Panel (for icon, input, and button)
        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.CENTER);
        Color backgroundColor = Color.rgb(250, 250, 250);
        leftPanel.setStyle("-fx-background-color: rgb(250, 250, 250);");

        // Image icon
        Image icon = new Image("file:pascal.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(400);
        iconView.setFitHeight(400);

        // Text field and button
        TextField textField = new TextField();
        textField.setText("Enter number of rows: ");
        textField.setFont(Font.font("Arial", 20));
        textField.setPrefWidth(300);
        Button button = new Button("Generate Triangle");
        button.setFont(Font.font("Arial", 20));

        leftPanel.getChildren().addAll(iconView, textField, button);

        // Right Panel (for the triangle display)
        VBox rightPanel = new VBox(20);
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setStyle("-fx-background-color: rgb(240, 240, 240);");

        // ScrollPane for right panel
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rightPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Set up the split pane
        HBox splitPane = new HBox(leftPanel, scrollPane);
        splitPane.setHgrow(scrollPane, Priority.ALWAYS);

        // Button action to generate Pascal's Triangle
        button.setOnAction(e -> {
            String input = textField.getText();
            int rows = Integer.parseInt(input);
            PascalTriangle pascalTriangle = new PascalTriangle(rows);
            int[][] triangle = pascalTriangle.getTriangle();
            TextArea textArea = new TextArea();
            textArea.setFont(Font.font("Arial", 20));
            textArea.setEditable(false);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <= i; j++) {
                    sb.append(triangle[i][j]).append(" ");
                }
                sb.append("\n");
            }
            textArea.setText(sb.toString());

            // Update right panel with the new text area
            rightPanel.getChildren().clear();
            rightPanel.getChildren().add(textArea);
        });

        // Set up scene and stage
        Scene scene = new Scene(splitPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
