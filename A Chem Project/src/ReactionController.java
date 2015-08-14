import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ReactionController extends Application{
	private ReactionPane pane;
	public ReactionController(){
		pane = new ReactionPane(500,500);
	}
	@Override
	public void start(Stage s) throws Exception {
		s.setScene(new Scene(pane));
		s.setHeight(500);
		s.setWidth(500);
		s.show();
		pane.play();
	}
	
	public static void main(String... args){
		launch();
	}
	
}
