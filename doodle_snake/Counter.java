import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Write a description of class Counter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Counter  extends Actor
{
    private int totalCount = 0;
   
    //This is what sets the display of the counter.
    public Counter()
    {
        setImage(new GreenfootImage("Score: 0", 20, Color.WHITE, Color.BLACK));
    }


    //This increases the number displayed on teh counter by a certain amount, in this case 1.
    public void addscore(int amount)
    {
        totalCount += amount;
        setImage(new GreenfootImage("Score: " + totalCount, 20, Color.WHITE, Color.BLACK));
    }
    
    public void gameover()
    {
        setImage(new GreenfootImage("GAMEOVER \n Score: "+ totalCount, 30, Color.RED, Color.BLUE)); 
    }
}