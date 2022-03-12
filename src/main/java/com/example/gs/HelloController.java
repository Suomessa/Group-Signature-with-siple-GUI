package com.example.gs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private VBox vbox_messages;

    @FXML
    private ScrollPane sp_main;

    private static final Random rand = new Random();

    private static int SCH = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        KeyService.generateData();
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true) {
                   try {
                       Thread.sleep(5000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   User user = KeyService.getUsers().get(rand.nextInt(KeyService.getUsers().size()));

                   String message = "Hello! Im user from " + user.getGroup() + ". This is " + SCH + " message.";
                   SCH++;
                   System.out.println(message);

                   try {
                       byte[] encryptedMessage = KeyService.encryptMessage(user.getPrivateKeys().get(rand.nextInt(user.getPrivateKeys().size())),
                               message);
                       System.out.println(encryptedMessage);
                       addLabel(encryptedMessage, vbox_messages);
                   } catch (InvalidKeyException e) {
                       e.printStackTrace();
                   } catch (IllegalBlockSizeException e) {
                       e.printStackTrace();
                   } catch (BadPaddingException e) {
                       e.printStackTrace();
                   }
               }
            }
        }).start();
    }




    public static void addLabel(byte[] msgFromServer, VBox vBox) throws InvalidKeyException {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        DecryptedMessage decryptedMessage = KeyService.decryptMessage(KeyService.getPublicKeysPojo(), msgFromServer);
        Text text = new Text(decryptedMessage.getMessage() + " group ::: " + decryptedMessage.getGroup());
        TextFlow textFLow = new TextFlow(text);
        hBox.getChildren().add(textFLow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}