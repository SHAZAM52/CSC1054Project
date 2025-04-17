import javafx.scene.paint.*;
import javafx.scene.canvas.*;

//this is Player object
public class Player extends DrawableObject
{
	//takes in its position
   public Player(float x, float y)
   {
      super(x,y);
   }
   
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.GRAY);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GREEN);
      gc.fillOval(x-10, y-10, 19,19);
   }
}
