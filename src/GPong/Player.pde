class  Player  {  
    int  xpos;  int  ypos; 
    int lives;
    int dx;
    int AILevel; 
    color  paddlecolor  =  color(255);  
    Player(int  screen_y)  {
        lives = STARTING_LIVES;
        xpos  =  SCREENX/2;  
        ypos  =  screen_y;
        AILevel = 2;  
    }  
    void  move(int  x)  { 
        dx = x - xpos;   
        if(x  >  SCREENX-PADDLEWIDTH)  
      xpos  =  SCREENX-PADDLEWIDTH;  
        else
      xpos=x;  
    }
    
    void  AImove(Ball ball)  {
        int oldX = xpos;  
        if(xpos + (PADDLEWIDTH / 2) >  ball.x)  
           xpos-=AILevel;  
        else
           xpos+=AILevel;
           
       dx = xpos - oldX;;
    }  
      
    void draw()  {  
        fill(paddlecolor);  
        rect(xpos,  ypos,  PADDLEWIDTH,  PADDLEHEIGHT);  
    }
  
    void loseALife(){
    lives--;
    }  
    
    void resetLives(){
    lives = STARTING_LIVES;
    } 
}
