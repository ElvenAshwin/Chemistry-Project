
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;
import static java.lang.Math.*;

public class ReactionPane extends Pane{
	private Timeline moveAnimation;
	private ArrayList<Ball> balls;
	private ArrayList<Pair<Ball,Ball>> interactions;
	
	public ReactionPane(int width, int height){
		this.setHeight(height);
		this.setWidth(width);
		
		balls = new ArrayList<>();
		
		interactions = new ArrayList<>();
		
		moveAnimation = new Timeline();
		
		for(int i=0; i<10; i++){
			Ball b = new Ball(50, 30, 20, Color.BLACK);
			b.dx = Math.random() * 7 ;
			b.dy = Math.random() * 10 ;
			b.setCenterX(Math.random()*500);
			b.setCenterY(Math.random()*500);
			balls.add(b);
			getChildren().add(balls.get(balls.size()-1));
		}
	}
	public void play() {
		configureMoveBallAnimation();
		moveAnimation.play();
	}

	public void pause() {
		moveAnimation.pause();
	}
	
	private double findCenterDistance(Ball ball, Ball other){
		double yDist = ball.getCenterY() - other.getCenterY();
		double xDist = ball.getCenterX() - other.getCenterX();
		double dist = Math.sqrt(Math.pow(yDist,2) + Math.pow(xDist,2));
		
		return dist;
	}
	
	private void configureMoveBallAnimation() {
		moveAnimation.getKeyFrames().clear();
		interactions.clear();
		
		
		for(Ball ball:balls){
			if (ball.getCenterY() < ball.getRadius() || 
					ball.getCenterY() > getHeight() - ball.getRadius()) {
				ball.dy *= -1; // Change ball move direction
				if(ball.getCenterY()< ball.getRadius() && ball.dy<0){
					ball.dy*=-1;
				}
				if(ball.getCenterY() > getHeight() - ball.getRadius() && ball.dy>0){
					ball.dy*=-1;
				}
			}
			if(ball.getCenterX() < ball.getRadius() || ball.getCenterX() > getWidth()-ball.getRadius()){
				ball.dx *=-1;
				if(ball.getCenterX() < ball.getRadius() && ball.dx<0){
					ball.dx*=-1;
				}
				if(ball.getCenterX() > getWidth()-ball.getRadius() && ball.dx>0){
					ball.dx*=-1;
				}
			}
			
			
			for(int i=0;i<balls.size();i++){
				Ball other = balls.get(i);
				if(other == ball){
					continue;
				}
				double dist = findCenterDistance(ball,other);
				if(dist<ball.getRadius() + other.getRadius()){
					
					boolean interactionDone = interactions.stream().filter(p -> p.getKey()==ball || p.getKey() == other)
						.filter(p -> p.getValue()==ball || p.getValue() == other).count() > 0;
						
						if(interactionDone){
							continue;
						}
					
					//the new dy is add up their dy, and then divide by two
					double newDy = (abs(ball.dy) + abs(other.dy))/2;
					double newDx = (abs(ball.dx) + abs(other.dx))/2;
					
					
					if(ball.getCenterY()>other.getCenterY()){
						ball.dy = newDy;
						other.dy = -newDy;
					}else{
						ball.dy = -newDy;
						other.dy = newDy;
					}
					
					if(ball.getCenterX()>other.getCenterX()){
						ball.dx = newDx;
						other.dx = -newDx;
					}else{
						ball.dx = -newDx;
						other.dx = newDx;
					}
					interactions.add(new Pair<>(ball,other));
				}
			}

			KeyValue xFinal = new KeyValue(ball.centerXProperty(),ball.dx+ball.getCenterX());
			KeyValue yFinal = new KeyValue(ball.centerYProperty(),ball.dy+ball.getCenterY());

			KeyFrame xChange = new KeyFrame(Duration.millis(1),xFinal);
			KeyFrame yChange = new KeyFrame(Duration.millis(1),yFinal);

			moveAnimation.getKeyFrames().addAll(xChange,yChange);
		}

		
		moveAnimation.setCycleCount(0);

		moveAnimation.setOnFinished(e->{
			configureMoveBallAnimation();
			moveAnimation.play();
		});

	}
}
