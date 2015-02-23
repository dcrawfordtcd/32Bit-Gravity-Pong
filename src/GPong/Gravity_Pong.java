import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Gravity_Pong extends PApplet {


class  Ball  {  
    float  x;  float  y;  
    float  dx;  float  dy;  
    int  radius;
    float mass;  
    int collisions;
    int  ballColor;  
    Ball(int radiusArg, float speedFactor, int ballColor, float xStart)  {
        this.ballColor = ballColor;
        x  =  xStart + random(SCREENX/4,  SCREENX/2);  
        y  =  random(SCREENY/4,  SCREENY/2);
        dx  =  random(1, 2) * speedFactor;
        dy  =  random(1, 2) * speedFactor;
        radius  =  radiusArg;
        mass = 2*PI*radius*radius;  
        collisions = 0;
    }
    
      public void  move(){
        x  =  x  +  dx;  
        y  =  y  +  dy;
    }
    

    
      public void gravity(Ball otherBall){
      mass = 2*PI*radius*radius;
      float xDistance = x - otherBall.x;  
      float yDistance = y - otherBall.y;
      float distance = (float)Math.sqrt(xDistance*xDistance + yDistance*yDistance);
      float xForce = xDistance * otherBall.mass * GRAVITY / (distance * distance); 
      float yForce = yDistance * otherBall.mass * GRAVITY / (distance * distance);
      dx -= xForce;
      dy -= yForce;
      //println("Gravity pushing " + xForce + ", " + yForce + ".");
      }
    
      public void draw(){
       fill(ballColor);  
       ellipse(PApplet.parseInt(x),  PApplet.parseInt(y),  radius,  radius);  
    }
      
      public void  collideWalls(){  
      if(x-radius  <=  0)  
        {
          x = 0 +radius;
          dx = -dx;
        }  
      else if(x+radius  >=  SCREENX)  
        {
          dx = -dx;  
          x = SCREENX-radius;
        }
        
        } 
        
      public void  collideAllWalls(){  
      if(x-radius  <=  0)  
        {
          x = radius;
          dx = -dx;
        }  
      else if(x+radius  >=  SCREENX)  
        {
          dx = -dx;  
          x = SCREENX-radius;
        }
        
      if(y-radius <= 0)
        {
          dy = -dy;
          y = radius;
        }
      else if(y + radius >= SCREENY)
        {
          dy = -dy;
          y = SCREENY-radius;
        }
        } 
  
    
  public void  collide(Player tp){  
      if(y+radius >= tp.ypos  &&  
      y-radius < tp.ypos+PADDLEHEIGHT  &&
      x >=  tp.xpos  && 
      x < tp.xpos+PADDLEWIDTH){
        collisions++;  
        //for debugging collision
//        println("collided " + collisions + " times!");  
        dy = -dy;
        dx = dx + (tp.dx * PADDLE_FRICTION);   
        } 
       
    }  
  


}
final int  SCREENX  =  320;  
final int  SCREENY  =  240;  
final int  PADDLEHEIGHT  =  5;  
final int  PADDLEWIDTH  =  50;  
final int  MARGIN  =  30;
final int  STARTING_LIVES = 10;
final float  GRAVITY = 0.008f;
final float  PADDLE_FRICTION = 0.0f;
Player  thePlayer;
Player  theAI;  
Ball  theBall;
Ball  bigBall;
boolean gameStarted = false;
int framesDrawn = 0;
public void setup(){  
size(SCREENX,  SCREENY);
thePlayer  =  new  Player(SCREENY-MARGIN-PADDLEHEIGHT);
theAI = new Player(MARGIN);
theBall = new  Ball(5, 1.5f, color (255,  100,  100), 10);
bigBall = new Ball(10, 0.2f, color (100), 50);
ellipseMode(RADIUS);  
}


public void draw()  {
background(0);
   if(!gameStarted)
   startText();
   
   if(gameStarted){
   if(!gameOver(theAI, thePlayer)){
   theBall.move();
   bigBall.move();
   theBall.gravity(bigBall);
   bigBall.gravity(theBall);
   theAI.AImove(theBall);
   thePlayer.move(mouseX);
   collisionChecks(theBall, false);
   collisionChecks(bigBall, true);
   bigBall.draw();
   theBall.draw();
   theAI.draw();
   thePlayer.draw();
   checkBallsPosition(theBall, theAI, thePlayer);
   printLives(thePlayer, theAI);
   framesDrawn++;
   //incrementDifficulty(framesDrawn, theAI, bigBall);
   }
     
   }
     
   
     
   }

  public void printLives (Player thePlayer, Player theAI){
   textSize(15);
   fill(255);
   //if(Player.lives == 3)
   text(thePlayer.lives, SCREENX - 30, SCREENY - 20);
   text(theAI.lives, 10, 20);
  }
  
   public void startText (){
   textSize(20);
   fill(255);
   text("Welcome to...", SCREENX/2-150, SCREENY/2 - 100);
   textSize(40);
   text("GRAVITY PONG", SCREENX/2-150, SCREENY/2 - 20);
   textSize(15);
   text("Use the mouse to control the paddle,", SCREENX/2-120, SCREENY-40);
   text("only pink ball scores, click to begin!", SCREENX/2-120, SCREENY-20);
    
  }




  public void collisionChecks(Ball theBall, boolean allWalls){
  if(!allWalls)
    theBall.collideWalls();
   else
    theBall.collideAllWalls();
    
   theBall.collide(thePlayer);
   theBall.collide(theAI);
  }

  public int checkLives(Player theAI, Player thePlayer){
  if(theAI.lives <= 0)
    return 1;
    
  if(thePlayer.lives <= 0)
    return 2; 
    
    return 0;    
  }
  
  public boolean gameOver(Player theAI, Player thePlayer){
  switch (checkLives(theAI, thePlayer)) {
   case 1 :
   textSize(32);
   fill(0, 102, 153);
   text("PLAYER WINS!", 20, SCREENY/2);
   return true;
   
   case 2 :
   textSize(32);
   fill(0, 102, 153);
   text("GAME OVER MAN!", 20, SCREENY/2); 
   return true;
   
   default:
   return false;
   }
  }

  public void checkBallsPosition(Ball theBall, Player theAI, Player thePlayer){
     if (theBall.y <= 0 ){
     reset(theBall);
     theAI.loseALife();
     //println("The AI has " + theAI.lives + " lives left!");
   }
   
   if (theBall.y >= SCREENY){
     reset(theBall);
     thePlayer.loseALife();
     //println("The player has " + thePlayer.lives + " lives left!");
  }
  } 
  
  public void resetLives(Player theAI, Player thePlayer){
   thePlayer.resetLives(); 
   theAI.resetLives(); 
  }
  
  public void reset(Ball ball){
  
  {
        ball.x  =  random(SCREENX/4,  SCREENX/2);  
        ball.y  =  random(SCREENY/4,  SCREENY/2);
        ball.dx  =  0;  
        ball.dy  =  0; 
  }
  }
  
    public void restart(Ball ball){
  
  {
        ball.dx  =  0;//random(1,  2);  
        ball.dy  =  0;//random(1,  2); 
  }
  }
  
  
  public void mousePressed(){
      
      gameStarted = true;
    }
    
      
  public void incrementDifficulty(int x, Player theAI, Ball theBall){
   if(x % 512 == 0)
     theAI.AILevel++;
   if(x % 512 == 0){
   if(bigBall.radius < 25)   
   theBall.radius++;
   }
  }
class  Player  {  
    int  xpos;  int  ypos; 
    int lives;
    int dx;
    int AILevel; 
    int  paddlecolor  =  color(255);  
    Player(int  screen_y)  {
        lives = STARTING_LIVES;
        xpos  =  SCREENX/2;  
        ypos  =  screen_y;
        AILevel = 2;  
    }  
    public void  move(int  x)  { 
        dx = x - xpos;   
        if(x  >  SCREENX-PADDLEWIDTH)  
      xpos  =  SCREENX-PADDLEWIDTH;  
        else
      xpos=x;  
    }
    
    public void  AImove(Ball ball)  {
        int oldX = xpos;  
        if(xpos + (PADDLEWIDTH / 2) >  ball.x)  
           xpos-=AILevel;  
        else
           xpos+=AILevel;
           
       dx = xpos - oldX;;
    }  
      
    public void draw()  {  
        fill(paddlecolor);  
        rect(xpos,  ypos,  PADDLEWIDTH,  PADDLEHEIGHT);  
    }
  
    public void loseALife(){
    lives--;
    }  
    
    public void resetLives(){
    lives = STARTING_LIVES;
    } 
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Gravity_Pong" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
