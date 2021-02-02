/*
 * dec2base.c
 *      Author: aureliahaas
 *      Name: Aurelia Haas
 */

#include <stdio.h>

int main(int argc, char *argv[]) {
	int a, b;

	if(argc!=2 && argc!=3){
		printf("Wrong number of arguments\n");
	}

	else {
		 if (argc == 2) {
		 b=2;
		 sscanf(argv[1],"%d",&a);
	     }
		 else {
			 sscanf(argv[1],"%d",&a);    // a= number on base 10
			 sscanf(argv[2],"%d",&b);    // b= Base that we want to convert to
	     }

		 int  z,j=0;
		 char x[j+1],tab[36]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		 z=a;                       //x[] will get every digits of the answer expected
		 while (a!=0){
			 x[j]=tab[a%b];        // Use the rest of the Euclidean division
			 j++;
			 a=a/b;                // Use the quotient of the Euclidean division
			 printf("");
		 }                         // We want every rest in the reverse order
		 printf("The Base-%d form of %d is: ",b,z);
		 while(j>0){
			 j--;
			 printf("%c",x[j]);
		 }
		 printf("\n");
	}
}
