import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

//this is the Mines object
public class Mines extends DrawableObject
{
	//takes in its position
   public Mines(float x, float y)
   {
      super(x,y);
   }
   
   Random r = new Random();
   private int shade = r.nextInt(255);//random number between 1-255
   private int shadeChange = 1;
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.WHITE);
      gc.fillOval(x-7,y-7,13,13);
      gc.setFill(Color.rgb(255, shade, shade));
      gc.fillOval(x-4,y-4,7,7);
   }
   //color change from red to white
   public void gradient()
   {
      if (shade == 255)
      {
         shadeChange = -1;
      }
      if (shade == 0)
      {
         shadeChange = 1;
      }
      shade += shadeChange;
   }
   
   
}
