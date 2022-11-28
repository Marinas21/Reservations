import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

public class MakeReservation implements Paint{
    @Override
    public void paint(Stage primaryStage) {
        GridPane gridPane=new GridPane();
        Label startDate=new Label("From");
        Label endDate=new Label("To");
        Button goBack=new Button("Back");
        Button makeReservation=new Button("Reserve");
        DatePicker start=new DatePicker();
        DatePicker end=new DatePicker();
        gridPane.getChildren().addAll(startDate,endDate,goBack,makeReservation,start,end);
        gridPane.setConstraints(startDate,0,0);
        gridPane.setConstraints(start,1,0);
        gridPane.setConstraints(endDate,0,1);
        gridPane.setConstraints(end,1,1);
        gridPane.setConstraints(makeReservation,0,2);
        gridPane.setConstraints(goBack,1,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DashBord dashBord=new DashBord();
                dashBord.paint(primaryStage);
            }
        });
        makeReservation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate startDate=start.getValue();
                LocalDate endDate=end.getValue();
                System.out.println(startDate);
                System.out.println(endDate);
                String query=String.format("insert into reservations values(null,%s,%s,'%s','%s')",Rooms.id,Users.id,startDate,endDate);
                try{
                    Statement statement=MySqlConnection.connection();
                    statement.executeUpdate(query);
                    DashBord dashBord=new DashBord();
                    dashBord.paint(primaryStage);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });
        Scene scene=new Scene(gridPane,300,400);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
