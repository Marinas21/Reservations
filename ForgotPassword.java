import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

public class ForgotPassword implements Paint{
    @Override
    public void paint(Stage primaryStage) {
        GridPane gridPane=new GridPane();

        Label mailLabel=new Label("Mail");
        TextField mailTextField=new TextField();
        Label newPasswordLabel=new Label("New Password");
        TextField newPasswordField=new TextField();
        Button sendRequest=new Button("Reset");
        Button back=new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginPage loginPage=new LoginPage();
                loginPage.paint(primaryStage);
            }
        });

        sendRequest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String mail=mailTextField.getText();
                String newPassword=newPasswordField.getText();
                String query=String.format("update users set password='%s' where mail='%s'",newPassword,mail);
                try {
                    Statement statement=MySqlConnection.connection();
                    statement.executeUpdate(query);
                    LoginPage loginPage=new LoginPage();
                    loginPage.paint(primaryStage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.getChildren().addAll(mailLabel,mailTextField,newPasswordLabel,newPasswordField,sendRequest,back);
        gridPane.setConstraints(mailLabel,0,0);
        gridPane.setConstraints(mailTextField,1,0);
        gridPane.setConstraints(newPasswordLabel,0,1);
        gridPane.setConstraints(newPasswordField,1,1);
        gridPane.setConstraints(sendRequest,0,2);
        gridPane.setConstraints(back,1,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Scene scene=new Scene(gridPane,300,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
