import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;


public class Main extends Application
{
   FlowPane fp;//pane
   
   Canvas theCanvas = new Canvas(600,600);//for drawing
   
   Player thePlayer = new Player(300, 300);//player
   
   Player start = new Player(300, 300);//starting point
   
   ArrayList<Mines> mines = new ArrayList<Mines>();//ArryList for Mines
   
   Scanner reader;//read from file
   double highScore;
   double score;
   
   boolean playerLoss = false;

   public void start(Stage stage)
   {
      
      //create FlowPane
      fp = new FlowPane();
      
      //add Canvas to FlowPane
      fp.getChildren().add(theCanvas);
      
      //for drawing
      gc = theCanvas.getGraphicsContext2D();
      
      //drawBackground
      drawBackground(300,300,gc);
      
      try//create a scanner that reads a file
      {
         reader = new Scanner(new File("highscore.txt"));
      }
      catch (FileNotFoundException fnfe)//in case file is not found
      {
         System.out.println("File not found!");
      }
      
      
      highScore = reader.nextDouble();//records the highscore
      
      
            
      //set Scene and Stage
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      //start animation
      AnimationHandler ah = new AnimationHandler();
      ah.start();
      /*if (playerLoss)
      {
         ah.stop();
      }*/
      
      //keyboard events
      theCanvas.setOnKeyPressed(new KeyListenerDown());
      theCanvas.setOnKeyReleased(new KeyListenerUp());
      
      //put focus on the Canvas
      theCanvas.requestFocus();
      
      
   }
   
   GraphicsContext gc;
   
   
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
	//figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	  //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   
   
   
   
   
   Random r = new Random();
   int lastGridX = 0;
   int lastGridY = 0;
   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 

         //lose if player and mine get close
         for (int i = 0; i<mines.size(); i++)
         {
            if (thePlayer.distance(mines.get(i)) <= 20)
            {
               mines.remove(i);
               playerLoss = true;
               stop();
            }
         }
         
         //player grid position
         int cgridx = ((int)thePlayer.getX())/100;
         int cgridy = ((int)thePlayer.getY())/100;
         
         //amount of mines per grid
         int mineNum = (int) thePlayer.distance(start)/1000;
                          
         
         //if player changes grid add mines to ArrayList        
         if (lastGridX != cgridx || lastGridY != cgridy)
            {
            //right side of the minefield
            for (int j = 0; j<mineNum; j++)
            {
               for (int i = 0; i<9; i++)
               {
                  int chance = r.nextInt(10);
                  int locateX = r.nextInt(99);
                  int locateY = r.nextInt(99);
                  if(chance < 3)
                  {
                     mines.add(new Mines(((cgridx+4)*100)+locateX, (((cgridy-4)+i)*100)+locateY));
                  }
               }
            }
            //left side of the minefiled
            for (int j = 0; j<mineNum; j++)
            {
               for (int i = 0; i<9; i++)
               {
                  int chance = r.nextInt(10);
                  int locateX = r.nextInt(99);
                  int locateY = r.nextInt(99);
                  if(chance < 3)
                  {
                     mines.add(new Mines(((cgridx-5)*100)+locateX, (((cgridy+3)-i)*100)+locateY));
                  }
               }
            }
            //top of the minefiled
            for (int j = 0; j<mineNum; j++)
            {
               for (int i = 0; i<9; i++)
               {
                  int chance = r.nextInt(10);
                  int locateX = r.nextInt(99);
                  int locateY = r.nextInt(99);
                  if(chance < 3)
                  {
                     mines.add(new Mines((((cgridx-4)+i)*100)+locateX, ((cgridy-5)*100)+locateY));
                  }
               }
            }
            //bottom of the minefiled
            for (int j = 0; j<mineNum; j++)
            {
               for (int i = 0; i<9; i++)
               {
                  int chance = r.nextInt(10);
                  int locateX = r.nextInt(99);
                  int locateY = r.nextInt(99);
                  if(chance < 3)
                  {
                     mines.add(new Mines((((cgridx+3)+i)*100)+locateX, ((cgridy+4)*100)+locateY));
                  }
               }
            }
            //old grid becomes new grid
            lastGridX = cgridx;
            lastGridY = cgridy;
         }
         
         //oscillates the mines' color from red to white
         for (int i = 0; i<mines.size(); i++)
         {
            
            mines.get(i).gradient();
          
         } 
         
         //draw mines in cnavas
         for (int i = 0; i<mines.size(); i++)
         {
            mines.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
            //removes mines
            if (thePlayer.distance(mines.get(i)) >= 800)
            {
               mines.remove(i);
            }
         } 
         
         
         
         //the player's call for draw
         if (playerLoss == false)
         {
            thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         }
         
         //move the player
         thePlayer.act(aPressed, sPressed, dPressed, wPressed, up, down, left, right);
         
         //when not moving speed slows down
         if (!aPressed)
         {
            left = left + 0.025f;
            if (left > -0.25)
            {
               left = 0;
            }
            thePlayer.setX(left);
         }
         if (!dPressed)
         {
            right = right - 0.025f;
            if (right < 0.25)
            {
               right = 0;
            }
            thePlayer.setX(right);
         }
         if (!sPressed)
         {  
            down = down - 0.025f;
            if (down < 0.25)
            {
               down = 0;
            }
            thePlayer.setY(down);  
         }
         if (!wPressed)
         {
            up = up + 0.025f;
            if (up > -0.25)
            {
               up = 0;
            }
            thePlayer.setY(up);
         }
         System.out.println("force left: " + left);
         System.out.println("force right: " + right);
         System.out.println("force down: " + down);
         System.out.println("force up: " + up);
         
         //display score and high score
         gc.setFill(Color.WHITE);
         gc.fillText("Score is: " + (int)thePlayer.distance(start), 10,25);
         gc.fillText("High Score is: " + (int)highScore, 10,40);
         
         //save highscore in txt doc
         score = thePlayer.distance(start);
         if (score > highScore)
         {
            try
            {
               //create new text file
               FileOutputStream fso = new FileOutputStream("highscore.txt", false);
               
               //write in the new document
               PrintWriter pw = new PrintWriter(fso);
               
               //write level in the new document
               pw.print(score);
               pw.close();//close document
            }
            catch(FileNotFoundException fnfe)//in case file is not found
            {
               System.out.println("File Not Found");
            }
         }
            
                  
         
      }
   }
   
   boolean aPressed = false;
   boolean sPressed = false;
   boolean dPressed = false;
   boolean wPressed = false;
   float up = 0;
   float down = 0;
   float right = 0;
   float left = 0;
   
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      {
         KeyCode savedKey = event.getCode();
         //if A is pressed
         if (event.getCode() == KeyCode.A) 
         {
            aPressed = true;
            left = left - .1f;
            if (left <= -5)
            {
               left = -5;
            }
         }//if S is pressed
         if (event.getCode() == KeyCode.S) 
         {
            sPressed = true;
            down = down + .1f;
            if (down >= 5)
            {
               down = 5;
            }
         }
         //if D is pressed
         if (event.getCode() == KeyCode.D) 
         {
            dPressed = true;
            right = right + .1f;
            if (right >= 5)
            {
               right = 5;
            }
         }
         //if W is pressed
         if (event.getCode() == KeyCode.W) 
         {
            wPressed = true;
            up = up - .1f;
            if (up <= -5)
            {
               up = -5;
            }
         }
      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      {
         KeyCode savedKey = event.getCode();
         //if A is released
         if (event.getCode() == KeyCode.A) 
         {
            aPressed = false;
         }//if S is released
         if (event.getCode() == KeyCode.S) 
         {
            sPressed = false;
         }
         //if D is released
         if (event.getCode() == KeyCode.D) 
         {
            dPressed = false;
         }
         //if W is released
         if (event.getCode() == KeyCode.W) 
         {
            wPressed = false;
         }
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

