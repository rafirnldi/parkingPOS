package com.example.parkingfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private volatile boolean stop;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;
    @FXML
    private Button submitButton;

    @FXML
    private TextField plateInput;

    @FXML
    private Label time;

    @FXML
    private Label ticketDisp;
    @FXML
    private Label plateDisp;
    @FXML
    private Label vehicleDisp;

    @FXML
    private Label entry;
    @FXML
    private Label exit;
    @FXML
    private Label duration;
    @FXML
    private Label bill;


    @FXML
    private ChoiceBox<String> vehicleType;

    private String[] vehicles = {"MOTOR","MOBIL"};

    private void displayTicket(){

    }
    @FXML
    private void currentTime(){
        Thread thread = new Thread(() ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (!stop){
                try{
                Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String curtime = LocalDateTime.now().format(formatter);
                Platform.runLater(()->{
                    time.setText(curtime);
                });

            }
        });
                thread.start();
    }

    @FXML
    public String dataSubmit(ActionEvent event) throws IOException {
        String plate = plateInput.getText();
        String vehicle = vehicleType.getValue();
        checkIn(plate,vehicle);
        return plateInput.getText();

    }

    @FXML
    public String checkOutSubmit(ActionEvent event) throws IOException {
        String plate = plateInput.getText();
        checkOut(plate);
        return plateInput.getText();
    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void checkIn(String plate, String vehicle) throws IOException {
        URL url = new URL("http://localhost:8085/checkin");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        String json = String.format("{\"plat\":\" %s\",\"tipe_kendaraan\": \"%s\"}",plate,vehicle);
        System.out.println(json);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }catch (Exception e){
            System.out.println(e);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            ticketDisp.setText("TICKET");
            plateDisp.setText(plate);
            vehicleDisp.setText(vehicle);
            System.out.println(response.toString());
        }catch (Exception e){
            ticketDisp.setText("ALREADY EXIST");
            plateDisp.setText(plate);
            vehicleDisp.setText(vehicle);
            System.out.println(e);
        }

    }

    public void checkOut(String plate) throws IOException {
        URL url = new URL("http://localhost:8085/checkout");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        String json = String.format("{\"plat\":\" %s\"}",plate);
        System.out.println(json);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }catch (Exception e){
            System.out.println(e);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject resp = new JSONObject(response.toString());
            plateDisp.setText(resp.getString("Plate No"));
            entry.setText(resp.getString("Entry Time"));
            exit.setText(resp.getString("Exit Time"));
            duration.setText(resp.getString("Duration"));
            bill.setText(resp.getString("Bill"));
            System.out.println(response.toString());
        }catch (Exception e){
            bill.setText("DOES NOT EXIST");
            plateDisp.setText(plate);
            System.out.println(e);
        }

    }

    public void switchToCheckOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CheckOut.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCheckIn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleType.getItems().addAll(vehicles);
        currentTime();
    }
}