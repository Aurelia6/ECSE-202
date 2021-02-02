/* Aurelia Haas 
 * Assignment 6
 */



//The class Stack allows to create a list where the last string entered will be the first string retired
public class Stack 
{
	public listNode top;
	
	public Stack()                            //Constructor to allocate memory in the class
	{
		super();
		top = null;
	}
	 
	
	//Push is a function that will push a string in the Stack. It works as an exchange of variables (a swap function) to create a array in order with the first element at the end. 
	public void push (String newStr)        
	{
		listNode mynode = new listNode (newStr);
		if(top != null)
		{
			top.next = mynode;
			mynode.previous = top;	
		}
		top = mynode;
	}
	
	
	//Pop is a function deleting the last element entered in the stack.(we do again as a swap function)
	public String pop()
	{
		if(top == null)                // Case of an empty stack
		{
			System.out.println("Error: empty stack");
			return null;
		}
		
		String result_str = top.key_val;
		if(top.previous == null)       // We have only one node in the stack
		{
			top = null;
		}
		else                           // We have more than one node in the stack: swap 
		{			
			listNode pre_top = top.previous;
			pre_top.next = null;
			top = pre_top;		
		}	
		return result_str;
	}
	
	
	// Print_my_stack will print each string of the stack in order beginning by last stack entered 
	public void print_my_stack()
	{
		listNode itr = top;                           //itr is what we use to change the data in listNode; i.e. where the stack of each string appears
		if (itr == null)                              //Case of an empty stack
		{
			System.out.println("Empty Stack");	
		}
		while (itr != null)                           //Case of a non-empty stack; extract and print each string of the stack one by one
		{
			System.out.print(itr.key_val + " ");
			itr = itr.previous;
		}
		System.out.println();	
	}
	
	
	// Top is a function to check if the stack is empty or not; it returns true if the stack is empty and the last element of the stack otherwise
	public String check_top()
	{
		if(top == null)
		{
			System.out.println("Empty Stack");
			return null;
		}
		else
		{
			return top.key_val;
		}
	}	                           	//public boolean IsEmpty (cf in the Queue class)
}
