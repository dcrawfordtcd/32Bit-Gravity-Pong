class  Ball  {  
    float  x;  float  y;  
    float  dx;  float  dy;  
    int  radius;
    float mass;  
    int collisions;
    color  ballColor;  
    Ball(int radiusArg, float speedFactor, color ballColor, float xStart)  {
        this.ballColor = ballColor;
        x  =  xStart + random(SCREENX/4,  SCREENX/2);  
        y  =  random(SCREENY/4,  SCREENY/2);
        dx  =  random(1, 2) * speedFactor;
        dy  =  random(1, 2) * speedFactor;
        radius  =  radiusArg;
        mass = 2*PI*radius*radius;  
        collisions = 0;
    }
    
      void  move(){
        x  =  x  +  dx;  
        y  =  y  +  dy;
    }
    

    
      void gravity(Ball otherBall){
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
    
      void draw(){
       fill(ballColor);  
       ellipse(int(x),  int(y),  radius,  radius);  
    }
      
      void  collideWalls(){  
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
        
      void  collideAllWalls(){  
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
  
    
  void  collide(Player tp){  
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
