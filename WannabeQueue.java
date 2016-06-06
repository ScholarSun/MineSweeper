/*
Scholar Sun + Bing Li
Jan 18, 2014
The "WannabeQueue" class
Created for Scholar's Minesweeper program
FINAL RELEASE (No more development)
*/
import java.awt.*;

/*
Type: FIFO
Implement using: Array...
Functions included:
    - void push();
    - void pop();
    - int front();
    - boolean isEmpty();
This is very primitive, but enough for the summative
*/
class WannabeQueue
{
    private int array[];
    private int front,back;
    
    // Constructor
    public WannabeQueue(int size) 
    {
	array = new int[size];
	front = 0;
	back  = 0;
    }
    
    // Check it array is empty
    public boolean isEmpty() 
    {
	return front==back;
    }

    // Returns the first "available" element, the increments first
    public int front() 
    {
	return array[front];
    }

    // Add new element to the back
    public void push(int item) 
    {
	array[back++] = item;
    }
    
    // Not actually popping... but close enough
    public void pop() 
    {
	front++;
    }
}
