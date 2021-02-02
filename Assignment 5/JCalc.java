/* Aurelia Haas 
 * Assignment 5
 */

import java.util.StringTokenizer;
import acm.program.ConsoleProgram;	

public class JCalc extends ConsoleProgram
{
	private static final long serialVersionUID = 1L;  //To make the extend of ConsoleProgram clear and working well
	
	public void run()                                 //Kind of main function here
	{                                                             
		
		while (true)                                 
		{
			Queue input = new Queue();                    //The First queue with our input calculation
			Queue output = new Queue();                   //The Second queue with the final output
			Stack stack = new Stack();                    //The only stack with each kind of operator : +,-,*,/,()
			
			String str = readLine("Enter expression: ");                                //Print in the applet viewer
			StringTokenizer st = new StringTokenizer(str , "+-*/()!" , true);       //Separates each operands and operators to make the selection and the rest of the program easier
			
			String token , operator , result , unary = "unary", toprint;            
			/*Token will contain one by one each token of the First queue
			 * operator will contain the operator at the moment when we need to put it in the Second queue
			 * result allows to make the final print; 
			 * unary will be used only for all the cases of calculation with unary operators
			 * toprint is important only if we begin the calculation with + or -
			 * other_operator it's important if we have an operator before string_num1 parenthesis 
			*/
			
			int priority = 0 ,not_parenthesis = 0;                                 
			/*priority goes to 0 to 2 to do the good part of the code following the laws of calculation; 
			 * not_parenthis permits to put the operator before string_num1 parenthesis 
			 * at the right place in the second queue 
			 * if we have another operator after the queue with string_num1 more important priority                                      
			 * Priority between parenthesis more important than *,/ which are more important than +,-
			 */
			
			
			
			while (st.hasMoreTokens())           //Put all the tokens in the First queue
				{
				String op = st.nextToken();
				input.Enqueue(op); 
				print (op);
				}
	
			while (!input.IsEmpty()) 
			{
				token = input.Dequeue();           //eliminate each token one by one and do the right action with 
				
				switch (token)                     //switch function because there are different cases +,-,*,/,(,). 
				{ 
		        case "+": case "-":                //Cases + and - work exactly in the same way for the final print
		        	switch (unary)               
					{
					case "unary":                 //Case we begin with a unary operator or there is a unary operator after a parenthesis or an operator
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
					
						
					default:                     //Case of string_num1 normal operator
						loop:                   
						/*loop is just to go out of the function while if operator is "(" because else,
						 *  the print would not be in the good order
						 */
						if(priority == 1)        //Case with * and / to put in the Second queue
						{
							while (stack.check_top() != null)   //The operators still in the stack are in order to be enter in the Second queue. 
							{
								operator = stack.pop();
								switch(operator)
								{
								case "(":            
									/*if we have string_num1 to the left of the parenthesis we want to stop 
									 * because we have to do again the function with the parenthesis before printing the calculation 
									 */
									if (not_parenthesis != 0)
										stack.push(operator);
									break loop;
								default:
									output.Enqueue(operator);
									break;
								}
							}
						}
						else if (priority == 2)     
							/*Case with + and - to put in the Second queue. 
							 * This is the same as priority 1 except we just want to print one operator in the stack
							 * not all of them in the stack because it would break the priority.
							 */
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
					while (stack.check_top() != null)      
						/*to print before we are going out of the parenthesis the operators that stay in the stack
						 *  (and which are parts of the calculation inside the parenthesis)
						 */
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
					case "firstunary":                   //case with a unary operator, print the token corresponding after the number or the parenthesis
						toprint = stack.pop();
						output.Enqueue(toprint);
						break;
					}
					unary = "not_unary";
					break;
				}
				
				
				
		   	}
	
			while (stack.check_top() != null)      //some operators stay in the good order in the stack, until the stack is empty, put them in the queue
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

			
			
			//From here the algorithm is different than the one in the Assignment 4
			
			Queue Shunting_yard = new Queue();                                              //Shunting_yard stack will contain the result of what I did in the Assignment 4 
			Stack calculus = new Stack();                                                   //In the Calculate stack we will put the numbers of the Shunting Yard to do the input operation
			String string_num1 , string_num2 , string_total;                               
			/*string_num1 is corresponding with double_num1 and same for num2 and total ; 
			 * this serves for the passage string to double and vice versa
			 */
			Double double_num1 , double_num2 , double_total = null , sign = null;           
			/*sign serves to put the unary sign "+" or "-" before the number or the parenthesis concerned 
			 * and then do the appropriate operation
			 */
			
			if (output.IsEmpty())                 //Case we don't input anything
				print("There is no input.");
			
			while (!output.IsEmpty())             //We put the Shunting yard result (the Assignment 4 result) into the Shunting_yard queue 
			{
				result = output.Dequeue();
				if (result != null)
					Shunting_yard.Enqueue(result);	
			}
			
			
			
			while (!Shunting_yard.IsEmpty())           //Loop while to calculate the input by studying one by one the element of the stack
			{
				String Shunting_Yard_element = Shunting_yard.Dequeue();   
				
				switch(Shunting_Yard_element)
				{
				case "+": case "-": case "*": case "/":                
					/* +,-,*,/ functiun exactly the same way when the calculation is as a Shunting Yard: 
					 * when the element in the stack is an operator, we have to take the 2 numbers before the operator:
					 *  num2 which is just before the operator and num1 which is before num1 and do: num1 operator num2
					 */
					String op = Shunting_Yard_element;                  //op for operator
	
					string_num1 = calculus.pop();                         //num1 is a String
					double_num1 = Double.parseDouble(string_num1);        //We change num1 into a double because if it is not we can't do any operation
					string_num2 = calculus.pop();                         //Same for num2
					double_num2 = Double.parseDouble(string_num2);
					
					switch(op)
					{
					case "+":                                         //if op is a + do an addition
						double_total = double_num2 + double_num1;     
						break;
					case "-":                                         //if op is a - do a subtraction in the right order (num1 and num2 must be placed well here)
						double_total = double_num2 - double_num1;
						break;
					case "*":                                         //if op is a * do a multiplication
						double_total = double_num2 * double_num1;
						break;
					case "/":                                         //if op is a / do a division in the right order (num1 and num2 must be placed well here)
						double_total = double_num2 / double_num1;
						break;
					}
					string_total = String.valueOf(double_total);     //Put the result of the operation here as a String in the calculus stack 
					calculus.push(string_total);
					break;
				

					
				case "_": case "#":                                 
					/*Case of unary operator almost the same as the case below 
					 *we just put the right sign before the last number in the stack (functions with parenthesis or other operators)
					 */
					String unary_sign = Shunting_Yard_element;
				    
					string_num1 = calculus.pop();
					double_num1 = Double.parseDouble(string_num1);
					switch(unary_sign)
					{
					case "_":
						sign = - double_num1;
						break;
					case "#":
						sign = + double_num1;
						break;
					}
					string_total = String.valueOf(sign);           //Put the number with his unary operator as a String in the calculus stack
					calculus.push(string_total);
					break;
				
					
				default:                                          //Put any number in the calculus stack
					calculus.push(Shunting_Yard_element);
					break;
				}
			}
			println(" = " + double_total);		
		}
	}
}
								