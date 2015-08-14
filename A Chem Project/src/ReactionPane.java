
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ReactionPane extends Pane{
	private Timeline moveAnimation;
	private Ball ball;
	
	public ReactionPane(int width, int height){
		this.setHeight(height);
		this.setWidth(width);
		
		moveAnimation = new Timeline();
		
		ball = new Ball(50, 30, 20, Color.BLACK);
		getChildren().add(ball);
	}
	public void play() {
		configureMoveBallAnimation();
		moveAnimation.play();
	}

	public void pause() {
		moveAnimation.pause();
	}
	
	private void configureMoveBallAnimation() {
		moveAnimation.getKeyFrames().clear(); 

		if (ball.getCenterY() < ball.getRadius() || 
				ball.getCenterY() > getHeight() - ball.getRadius()) {
			ball.dy *= -1; // Change ball move direction
		}
		if(ball.getCenterX() < ball.getRadius() || ball.getCenterX() > getWidth()-ball.getRadius()){
			ball.dx *=-1;
		}

		KeyValue xFinal = new KeyValue(ball.centerXProperty(),ball.dx+ball.getCenterX());
		KeyValue yFinal = new KeyValue(ball.centerYProperty(),ball.dy+ball.getCenterY());

		KeyFrame xChange = new KeyFrame(Duration.millis(1),xFinal);
		KeyFrame yChange = new KeyFrame(Duration.millis(1),yFinal);

		moveAnimation.getKeyFrames().addAll(xChange,yChange);
		moveAnimation.setCycleCount(0);

		moveAnimation.setOnFinished(e->{
			configureMoveBallAnimation();
			moveAnimation.play();
		});

	}
}
