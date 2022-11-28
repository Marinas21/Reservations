import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class MyReservationsPage implements Paint{
    @Override
    public void paint(Stage primaryStage) {
        BorderPane borderPane=new BorderPane();
        GridPane gridPane=new GridPane();
        int pos=0;
        try {
            Statement statement = MySqlConnection.connection();
            int userId=Users.id;
            String query=String.format("select * from reservations where user_id=%s ",userId);
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                HBox hBox=new HBox();
                hBox.setSpacing(30);
                Date start=resultSet.getDate("start_date");
                Date end=resultSet.getDate("end_date");
                LocalDate startLocalDate=start.toLocalDate();
                LocalDate endLocalDate=end.toLocalDate();
                String roomQuery=String.format("select * from rooms where room_id=%s",resultSet.getInt("room_id"));
                ResultSet roomSet=statement.executeQuery(roomQuery);
                String descriprion=roomSet.getString("description");
                int price=roomSet.getInt("price");
                hBox.getChildren().addAll(new Label("Start:"+startLocalDate+""),new Label("Finish:"+endLocalDate+""),
                        new Label("Description: "+descriprion),new Label("Price: "+price+"/Night"));

                gridPane.getChildren().add(hBox);
                GridPane.setConstraints(hBox,0,pos);
                pos++;

            }
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("ceva");
        }
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        borderPane.setCenter(gridPane);
        MenuBar menu=new MenuBar();
        Menu account=new Menu("Account");
        Menu reservations=new Menu("Reservations");
        menu.getMenus().addAll(account,reservations);
        MenuItem singOut=new MenuItem("Sing Out");
        MenuItem settings=new MenuItem("Settings");
        MenuItem reservationsItem=new MenuItem("Reservations");
        reservationsItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DashBord dashBord=new DashBord();
                dashBord.paint(primaryStage);
            }
        });
        singOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginPage loginPage=new LoginPage();
                loginPage.paint(primaryStage);
            }
        });
        account.getItems().addAll(singOut,settings);
        reservations.getItems().addAll(reservationsItem);
        borderPane.setTop(menu);
        borderPane.setCenter(gridPane);
        Scene scene=new Scene(borderPane,700,500);
        scene.getStylesheets().addAll("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
