package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        createCredentialsFile();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Image bannerImage = new Image("file:images.jpeg");
        ImageView bannerImageView = new ImageView(bannerImage);
        bannerImageView.setFitHeight(100);
        bannerImageView.setFitWidth(400);
        HBox bannerBox = new HBox(bannerImageView);
        bannerBox.setAlignment(Pos.CENTER);

        Label userLabel = new Label("User Name:");
        TextField userNameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        // Add components to the GridPane
        gridPane.add(bannerBox, 0, 0, 2, 1);
        gridPane.add(userLabel, 0, 1);
        gridPane.add(userNameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 0, 3);
        gridPane.add(exitButton, 1, 3);
        gridPane.add(messageLabel, 0, 4, 2, 1);

        Scene loginScene = new Scene(gridPane, 500, 400);
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        loginButton.setOnAction(e -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            if (validateLoginFromFile(username, password)) {
                showWelcomeScreen(username);
                saveUsernameToFile(username);
            } else {
                messageLabel.setText("Incorrect username or password!");
            }
        });

        exitButton.setOnAction(e -> primaryStage.close());
    }

    private void createCredentialsFile() {
        File file = new File("credentials.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("abeera:123\n");
            writer.write("user1:111\n");
            writer.write("user2:222\n");
            System.out.println("Credentials file created or overwritten.");
        } catch (IOException e) {
            System.err.println("Error creating credentials file: " + e.getMessage());
        }
    }

    private boolean validateLoginFromFile(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length == 2) {
                    String fileUsername = credentials[0].trim();
                    String filePassword = credentials[1].trim();
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
        return false;
    }

    private void showWelcomeScreen(String username) {
        Stage welcomeStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        layout.getChildren().add(welcomeLabel);

        Scene scene = new Scene(layout, 300, 200);
        welcomeStage.setTitle("User Info");
        welcomeStage.setScene(scene);
        welcomeStage.show();
    }

    private void saveUsernameToFile(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt", true))) {
            writer.write("Logged In: " + username);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving username to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
