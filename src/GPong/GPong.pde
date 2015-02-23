Player  thePlayer;
Player  theAI;  
Ball  theBall;
Ball  bigBall;
boolean gameStarted = false;
int framesDrawn = 0;
void setup(){  
size(SCREENX,  SCREENY);
thePlayer  =  new  Player(SCREENY-MARGIN-PADDLEHEIGHT);
theAI = new Player(MARGIN);
theBall = new  Ball(5, 1.5, color (255,  100,  100), 10);
bigBall = new Ball(10, 0.2, color (100), 50);
ellipseMode(RADIUS);  
}


void draw()  {
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

  void printLives (Player thePlayer, Player theAI){
   textSize(15);
   fill(255);
   //if(Player.lives == 3)
   text(thePlayer.lives, SCREENX - 30, SCREENY - 20);
   text(theAI.lives, 10, 20);
  }
  
   void startText (){
   textSize(20);
   fill(255);
   text("Welcome to...", SCREENX/2-150, SCREENY/2 - 100);
   textSize(40);
   text("GRAVITY PONG", SCREENX/2-150, SCREENY/2 - 20);
   textSize(15);
   text("Use the mouse to control the paddle,", SCREENX/2-120, SCREENY-40);
   text("only pink ball scores, click to begin!", SCREENX/2-120, SCREENY-20);
    
  }




  void collisionChecks(Ball theBall, boolean allWalls){
  if(!allWalls)
    theBall.collideWalls();
   else
    theBall.collideAllWalls();
    
   theBall.collide(thePlayer);
   theBall.collide(theAI);
  }

  int checkLives(Player theAI, Player thePlayer){
  if(theAI.lives <= 0)
    return 1;
    
  if(thePlayer.lives <= 0)
    return 2; 
    
    return 0;    
  }
  
  boolean gameOver(Player theAI, Player thePlayer){
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

  void checkBallsPosition(Ball theBall, Player theAI, Player thePlayer){
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
  
  void resetLives(Player theAI, Player thePlayer){
   thePlayer.resetLives(); 
   theAI.resetLives(); 
  }
  
  void reset(Ball ball){
  
  {
        ball.x  =  random(SCREENX/4,  SCREENX/2);  
        ball.y  =  random(SCREENY/4,  SCREENY/2);
        ball.dx  =  0;  
        ball.dy  =  0; 
  }
  }
  
    void restart(Ball ball){
  
  {
        ball.dx  =  0;//random(1,  2);  
        ball.dy  =  0;//random(1,  2); 
  }
  }
  
  
  void mousePressed(){
      
      gameStarted = true;
    }
    
      
  void incrementDifficulty(int x, Player theAI, Ball theBall){
   if(x % 512 == 0)
     theAI.AILevel++;
   if(x % 512 == 0){
   if(bigBall.radius < 25)   
   theBall.radius++;
   }
  }
