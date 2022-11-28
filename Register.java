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

public class Register implements Paint{


    @Override
    public void paint(Stage primaryStage) {
        GridPane gridPane=new GridPane();
        Label usernameLabel=new Label("Username");
        Label mailLabel=new Label("Mail");
        Label passwordLabel=new Label("Password");
        TextField usernameTextField=new TextField();
        TextField mailTextField=new TextField();
        TextField passwordTextField=new TextField();
        Button register=new Button("Register");
        Button back=new Button("Back");
        gridPane.getChildren().addAll(usernameLabel,usernameTextField,passwordLabel,passwordTextField,mailLabel,mailTextField,
                register,back);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginPage loginPage=new LoginPage();
                loginPage.paint(primaryStage);
            }
        });
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Statement statement=MySqlConnection.connection();
                    String username=usernameTextField.getText();
                    String password=passwordTextField.getText();
                    String mail=mailTextField.getText();
                    String query=String.format("insert into users values(null,'%s','%s','%s')",username,password,mail);
                    statement.executeUpdate(query);
                    DashBord dashBord=new DashBord();
                    dashBord.paint(primaryStage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.setConstraints(usernameLabel,0,0);
        gridPane.setConstraints(usernameTextField,1,0);
        gridPane.setConstraints(mailLabel,0,1);
        gridPane.setConstraints(mailTextField,1,1);
        gridPane.setConstraints(passwordLabel,0,2);
        gridPane.setConstraints(passwordTextField,1,2);
        gridPane.setConstraints(register,0,3);
        gridPane.setConstraints(back,1,3);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Scene scene=new Scene(gridPane,300,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
