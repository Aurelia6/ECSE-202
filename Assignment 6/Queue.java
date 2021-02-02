/* Aurelia Haas 
 * Assignment 6
 */



//The class Queue allows to create a list where the first string entered will be the first string retired
public class Queue
{	
	public listNode back;
	public listNode front;
	
	public Queue()            //Constructor to allocate memory in the class
	{
		super();
		back = null;
		front = null;
	}
	
	//IsEmpty returns true if the queue is empty and false otherwise
	public boolean IsEmpty()                 
	{
		return back == null;
	}
	
	
	
	//Enqueue adds an element to the queue by the same idea of the stack push function (swap method)
	public void Enqueue(String newStr)
	{
		listNode mynode = new listNode(newStr);
		if (back == null)                    //Case of an empty queue
		{
			back = mynode;
			front = mynode;
		}	
		else                                 //Case of a non-empty queue (swap)
		{
			mynode.next = back;
			back.previous = mynode;
			back = mynode;
		}
	}
	
	
	//Dequeue takes out an element of the queue
	public String Dequeue() 
	{
		if (front == null)                                      //Case of an empty queue (return true)
		{
			System.out.println("Error : the queue is empty.");
			return null;
		}		
		String result_str = front.key_val;	                   
		if(front.previous == null)                             //Case of only one node in the queue
		{
			front = null;
			back = null;
		}	
		else                                                  //Case of few nodes in the queue, use a swap function to delete one by one the strings of the queue
		{
			listNode pre_front = front.previous;
			pre_front.next = null;
			front = pre_front;
		}		
		return result_str;                                   //return the element that we deleted to use it maybe with another class or a print
	}
	
	
	//print-my-queue prints each node of the queue in order, beginning with the last string entered
	public void print_my_queue()
	{
		listNode itr = back;
		if (IsEmpty())                                    //Case of an empty queue, i.e. IsEmpty returns true
			System.out.println("Empty Queue");
		
		while (itr != null)                               //Case of a non-empty stack
		{
			System.out.print(itr.key_val + " ");
			itr = itr.next;			
		}		
		System.out.println();
	}	
}
