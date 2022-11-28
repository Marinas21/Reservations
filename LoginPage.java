import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPage implements Paint{


    @Override
    public void paint(Stage primaryStage) {
        GridPane gridPane=new GridPane();
        Label userLabel=new Label("Username");
        TextField userTextField=new TextField();
        Label passwordLabel=new Label("Password");
        TextField passwordTextField=new TextField();
        Button login=new Button("Login");
        Button register=new Button("Register");
        Button forgotPassword=new Button("Forgot Password");
        gridPane.getChildren().addAll(userLabel,userTextField,passwordLabel,passwordTextField,login,register,forgotPassword);
        gridPane.setConstraints(userLabel,0,0);
        gridPane.setConstraints(userTextField,1,0);
        gridPane.setConstraints(passwordLabel,0,1);
        gridPane.setConstraints(passwordTextField,1,1);
        gridPane.setConstraints(login,0,2);
        gridPane.setConstraints(register,0,3);
        gridPane.setConstraints(forgotPassword,1,3);
        forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ForgotPassword forgotPassword1=new ForgotPassword();
                forgotPassword1.paint(primaryStage);
            }
        });
        passwordTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.ENTER){
                    String username=userTextField.getText();
                    String password=passwordTextField.getText();
                    Statement statement;
                    try {
                        statement=MySqlConnection.connection();
                        String query=String.format("select * from users where username='%s' and password='%s'",username,password);
                        ResultSet resultSet=statement.executeQuery(query);
                        if(resultSet.next()){

                            Users.username=(resultSet.getString("username"));
                            Users.mail=(resultSet.getString("mail"));
                            Users.id=(resultSet.getInt("user_id"));
                            DashBord dashBord=new DashBord();
                            dashBord.paint(primaryStage);
                        }
                        else{
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Invalid Username or Password");
                            alert.show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username=userTextField.getText();
                String password=passwordTextField.getText();
                Statement statement;
                try {
                    statement=MySqlConnection.connection();
                    String query=String.format("select * from users where username='%s' and password='%s'",username,password);
                    ResultSet resultSet=statement.executeQuery(query);
                    if(resultSet.next()){

                        Users.username=(resultSet.getString("username"));
                        Users.mail=(resultSet.getString("mail"));
                        Users.id=(resultSet.getInt("user_id"));
                        DashBord dashBord=new DashBord();
                        dashBord.paint(primaryStage);
                    }
                    else{
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid Username or Password");
                        alert.show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Register register=new Register();
                register.paint(primaryStage);
            }
        });
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Scene scene=new Scene(gridPane,300,400);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
