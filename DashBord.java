
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class DashBord implements Paint{
    @Override
    public void paint(Stage primaryStage)  {
        BorderPane borderPane=new BorderPane();
        GridPane gridPane=new GridPane();
        try {
            Statement statement=MySqlConnection.connection();
            ResultSet resultSet=statement.executeQuery("select * from rooms");
            int i=0;
            while (resultSet.next()) {
                int id=resultSet.getInt("room_id");
                double price = resultSet.getDouble("price");
                int numberOfPeople = resultSet.getInt("number_of_people");
                int numberOfRooms = resultSet.getInt("number");
                String description = resultSet.getNString("description");
                VBox hBox = new VBox();
                Label descriptionLabel = new Label(description);
                Label priceLabel = new Label("Price: " + price + " Ron");
                Label numberOfRoomsLabel = new Label("Available: " + numberOfRooms);
                Label numberOfPeopleLabel = new Label("For " + numberOfPeople + " people");
                hBox.getChildren().addAll(descriptionLabel, priceLabel, numberOfRoomsLabel, numberOfPeopleLabel);

                hBox.getStyleClass().add("room");
                gridPane.getChildren().addAll(hBox);
                gridPane.setConstraints(hBox, i, 0);
                i++;
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MakeReservation makeReservation=new MakeReservation();
                        makeReservation.paint(primaryStage);
                        Rooms.id=id;
                    }
                });
            }


        } catch (Exception e){
                System.out.println(e);
        }
        MenuBar menu=new MenuBar();
        Menu account=new Menu("Account");
        Menu reservations=new Menu("Reservations");
        menu.getMenus().addAll(account,reservations);
        MenuItem singOut=new MenuItem("Sing Out");
        MenuItem settings=new MenuItem("Settings");
        MenuItem reservationsItem=new MenuItem("My Reservations");
        singOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginPage loginPage=new LoginPage();
                loginPage.paint(primaryStage);
            }
        });
        reservations.getItems().addAll(reservationsItem);
        reservationsItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MyReservationsPage myReservationsPage=new MyReservationsPage();
                myReservationsPage.paint(primaryStage);
            }
        });
        account.getItems().addAll(singOut,settings);
        borderPane.setTop(menu);
        borderPane.setCenter(gridPane);
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);
        Scene scene=new Scene(borderPane,1200,500);
        scene.getStylesheets().addAll("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
