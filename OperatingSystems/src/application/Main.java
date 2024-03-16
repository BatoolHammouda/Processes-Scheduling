package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;




public class Main extends Application {
		@Override
	  public void start(Stage primaryStage) throws Exception {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml")) ;
				Parent root = loader.load();
				Scene scene = new Scene(root, 1002, 746);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Multi Level Queu");
				primaryStage.show();
				
		    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
