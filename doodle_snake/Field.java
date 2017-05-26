import greenfoot.*;

/**
 * Write a description of class Field here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Field extends World
{

    /**
     * Constructor for objects of class Field.
     * 
     */
    public Counter score;//object of counter class
    public Snake snake_mouth[];
    public Food food;
    Counter counter = new Counter();
    public int turn_count;
    public boolean eaten;
    public int state;
    int i = 0;

    public static final int FOOD_CHECKER=15; // used to keep a check that food items are placed in definite rows 

    public Field()//constructor
    {    
        // initialization
        super(53*FOOD_CHECKER,40*FOOD_CHECKER,1);
        state=0;                  // denotes different phases of the game
        eaten=false;              // whether food has been eaten
        turn_count=0;              // to store the movement of turn_count
    }

    /**
     * Game starts from here.
     */
    public void act() 
    {
        if(state==0)
        {   i+=1;
            int x = (int)(i/5);
            if(x==0)
            {
                setBackground("Default.png");    
            }
            else if (x==1)
            {
                setBackground("Default2.png");    
            }
            else if(x==2)
            {
                setBackground("Default3.png");    
            }
            else if(x==3)
            {
                setBackground("Default4.png");    
            }
            else if(x==4)
            {
                setBackground("Default5.png");    
            }
            else if(x==5)
            {
                setBackground("Default6.png");    
            }
            else if(x==6)
            {
                state=1;    
            }
            return;
        }
        else if(state==1)
        {   /**
            initializing the objects in the world
             */
            setBackground("corkboard.jpg");
            
            food=new Food();
            addObject(food,0,0);
            //counter and add counter
            addObject(counter,700,25);
            snake_mouth =new Snake[3];
            for(int i=0;i<snake_mouth.length;++i)//sets image for the body by calling snake functiom
            {
                snake_mouth[i]=new Snake(i==0);//iff i=0 this gives a true value i.e i=0 has the head of the snake
                addObject(snake_mouth[i],6*FOOD_CHECKER+(snake_mouth.length-i)*FOOD_CHECKER,4*FOOD_CHECKER);
            }

            Foodlocation();
            state=2;
            return;
        }
        else if(state==-1)//gameover
        {

            counter.setLocation(375,300);
            counter.gameover();                    // display gameover
            return; 
        }

        if(eaten)
        {
            Foodlocation();
        }

        if(Greenfoot.isKeyDown("right"))
        {
            if(turn_count==1 || turn_count==3)
            {
                turn_count=0;
            }
        }
        else if(Greenfoot.isKeyDown("left"))
        {
            if(turn_count==1 || turn_count==3)
            {
                turn_count=2;
            }
        }
        else if(Greenfoot.isKeyDown("up"))
        {
            if(turn_count==0 || turn_count==2)
            {
                turn_count=3;
            }
        }
        else if(Greenfoot.isKeyDown("down"))
        {
            if(turn_count==0 || turn_count==2)
            {
                turn_count=1;
            }
        }

        int Rotation=snake_mouth[0].getRotation();//gives info about snake head
        int prev_x=snake_mouth[0].getX();
        int prev_y=snake_mouth[0].getY();

        snake_mouth[0].setRotation(turn_count*90);
        snake_mouth[0].move(FOOD_CHECKER);

        if(snake_mouth[0].getX()!=prev_x || snake_mouth[0].getY()!=prev_y)
        {
            for(int i=1;i<snake_mouth.length;++i)
            {
                int tempRotation=snake_mouth[i].getRotation();
                snake_mouth[i].setRotation(Rotation);

                prev_x=snake_mouth[i].getX();//move the snake step by step
                prev_y=snake_mouth[i].getY();
                snake_mouth[i].move(FOOD_CHECKER);
                Rotation=tempRotation;

            }

            if(snake_mouth[0].getX()==food.getX() && snake_mouth[0].getY()==food.getY())
            {
                snakelength(prev_x,prev_y,Rotation);
                counter.addscore(5);
                Foodlocation();
                //updatescore
            }
            
            
            for(int i=1;i<snake_mouth.length;++i)
            {
                if(snake_mouth[0].getX()==snake_mouth[i].getX() && snake_mouth[0].getY()==snake_mouth[i].getY())
                {
                    state=-1;

                }

            }
        }
        else//it hit the wall
        {
            turn_count = Rotation/90;
            snake_mouth[0].setRotation(turn_count*90);
            state=-1;
        }
               
    }    

    public void snakelength(int x,int y,int rotation)
    {
        Snake s=new Snake(false);//temp object for extending body
        Snake OldSnake[]=new Snake[snake_mouth.length];//temp snake
        for(int i=0;i<snake_mouth.length;++i)
        {
            OldSnake[i]=snake_mouth[i];
        }
        snake_mouth=new Snake[snake_mouth.length+1];//size of grown up snake
        for(int i=0;i<snake_mouth.length-1;++i)
        {
            snake_mouth[i]=OldSnake[i];
        }
        snake_mouth[snake_mouth.length-1]=s;//new element added
        snake_mouth[snake_mouth.length-1].setRotation(rotation);
        addObject(snake_mouth[snake_mouth.length-1],x,y);
    }

    public void Foodlocation()
    {
        int x;
        int y;
        boolean overlap=true;//initialized
        eaten=false;//set to default

        while(overlap)
        {
            x=Greenfoot.getRandomNumber(getWidth()/FOOD_CHECKER);
            y=Greenfoot.getRandomNumber(getHeight()/FOOD_CHECKER);

            for(int i=0;i<snake_mouth.length;++i)
            {
                if(x!=snake_mouth[i].getX() || y!=snake_mouth[i].getY())
                {
                    overlap=false;//accepted position
                    break;
                }
            }
            
            food.setLocation(x*FOOD_CHECKER,y*FOOD_CHECKER);
         }

    }

    

}
