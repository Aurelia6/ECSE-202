/* Aurelia Haas  
 * Assignment 4
 */

import java.util.StringTokenizer;
import acm.program.ConsoleProgram;	

public class In2pJ extends ConsoleProgram
{
	private static final long serialVersionUID = 1L;  //To make the extend of ConsoleProgram clear and working well
	
	public void run()                                 //Kind of main function here
	{                                                             
		
		while (true)                                 
		{
			Queue input = new Queue();                    //The First queue with our input calculation
			Queue output = new Queue();                   //The Second queue with the final output
			Stack stack = new Stack();                    //The only stack with each kind of operator : +,-,*,/,()
			
			String str = readLine("Enter string: ");                                //Print in the applet viewer
			StringTokenizer st = new StringTokenizer(str , "+-*/()!" , true);       //Separates each operands and operators to make the selection and the rest of the program easier
			
			String token , operator , result , unary = "unary", toprint;            //Token will contain one by one each token of the First queue; operator will contain the operator at the moment when we need to put it in the Second queue; result allows to make the final print; unary will be used only for all the cases of calculation with unary operators; toprint is important only if we begin the calculation with + or -, other_operator it's important if we have an operator before a parenthesis 
			int priority = 0 ,not_parenthesis = 0;                                  //priority goes to 0 to 2 to do the good part of the code following the laws of calculation; not_parenthis permits to put the operator before a parenthesis at the right place in the second queue if we have another operator after the queue with a more important priority                                                     //Priority between parenthesis more important than *,/ which are more important than +,-
			
			
			
			while (st.hasMoreTokens())           //Put all the tokens in the First queue
				input.Enqueue(st.nextToken());     	
			while (!input.IsEmpty()) 
			{
				token = input.Dequeue();           //eliminate each token one by one and do the right action with 
				
				switch (token)                     //switch function because there are different cases +,-,*,/,(,). I wanted to use the function if but for some reason I don't understand (because it should work similarly as the switch) it does not work
				{ 
		        case "+": case "-":                //Cases + and - work exactly in the same way for the final print
		        	switch (unary)               
					{
					case "unary":                 //Cases we begin with a unary operator or there is a unary operator after a parenthesis or another operator
						switch (token)
						{
						case "+":
							token = "#";         //On the print we put "#" as the symbol of unary +
							break;
						case "-":          
							token = "_";         //On the print we put "_" as the symbol of unary -
							break;
						}
						stack.push(token);           
						unary = "firstunary";      //This serves for the if in the default case of the first switch(token)
						break;
					
						
					default:                     //Case of a normal operator
						loop:                    //The loop is just to go out of the function while if "operator is "(" because else, the print would not be in the good order
						if(priority == 1)        //Case with * and / to put in the Second queue
						{
							while (stack.check_top() != null)   //The operators still in the stack are in order to be enter in the Second queue. 
							{
								operator = stack.pop();
								switch(operator)
								{
								case "(":            //if we have a left parenthesis we want to stop because we have to do again the function with the parenthesis before printing the calculation before it 
									if (not_parenthesis != 0)
										stack.push(operator);
									break loop;
								default:
									output.Enqueue(operator);
									break;
								}
							}
						}
						else if (priority == 2)     //Case with + and - to put in the Second queue. This is the same as priority 1 except we just want to print one operator not all of them in the stack because it would break the priority.
						{
							operator = stack.pop();
							switch(operator)
							{
							case "(":
								if (not_parenthesis != 0)
									stack.push(operator);
								break;
							default:
								output.Enqueue(operator);
								break;
							}
		
						}
						stack.push(token);
						priority = 2;                          //Priority of the + and -
						unary = "unary";
						break;
					}
		        	
					break;		
				
					
						
				case "*": case "/":                              //Cases * and / work exactly in the same way for the final print
					if (priority == 1)                           
					{
						operator = stack.pop();
						output.Enqueue(operator);
					}
					stack.push(token);
					priority = 1;                               //Priority of the * and /
					unary = "unary";
					break;
		
					
					
				case "(":                                        //Case ( , we want to calculate what's inside the parenthesis before the calculations outside
					stack.push(token);
					priority = 0;                                //The most important priority. This operator has an impact on what happens next and the ifs 
					not_parenthesis ++;
					unary = "unary";
					break;
				
					
				
				case ")":
					loop:
					while (stack.check_top() != null)       //to print before we are going out of the parenthesis the operators that stay in the stack (and which are parts of the calculation inside the parenthesis)
					{
						operator = stack.pop();             
						switch (operator) 
						{
						case "(":                          //Case we have others parenthesis inside parenthesis 
							not_parenthesis --;
							break loop;
						default:
							output.Enqueue(operator);
							break;
						}			
					}
					priority = 0;                        //Priority of the parenthesis
					unary = "not_unary";                 // an operator can't be unary after this kind of string
					break;
				
					
					
				default:                               
					output.Enqueue(token);
					switch(unary)
					{
					case "firstunary":                   //The case with a unary operator, to print the token corresponding after the number or the parenthesis
						toprint = stack.pop();
						output.Enqueue(toprint);
						break;
					}
					unary = "not_unary";
					break;
				}
				
				
				
		   	}
	
			while (stack.check_top() != null)      //every time some operators will stay in the good order into the stack so until the stack is not empty we put them in the queue
			{
				operator = stack.pop();
				switch(operator)
				{
				case"(":
					break;
				default:
					output.Enqueue(operator);
					break;	
				}		
			}
			
			if (output.IsEmpty())                 //Case we don't input anything
				print("There is no input.");
			if (!output.IsEmpty())                //Case we input something it will be print after the "Postfix :"
				print("Postfix: ");
			
			while (!output.IsEmpty())             //Final print, we use a string because we can't directly print the function of the queue
			{
				result = output.Dequeue();
				if (result != null)
					print(result + " ");
			}
			println();
		}
	}
}
						
					




