/*
 * lookup.c
 *
 *      Author: aureliahaas
 *      Name: Aurelia Haas
 *      Assignment 2
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXNAMELENGTH 51


// This structure will contain every data of each student: Lastnames, Firstnames, IDs and Grades.
struct StudentRecord{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};


// This function permits with the 2 files given in the assignment, to fulfill each part of the structure above
void put_into_struct (char record[15] ,char grade[15] , struct StudentRecord tab[MAXNAMELENGTH] , int *size){
	FILE *SRecord1 = NULL, *SRecord2 = NULL;
	SRecord1 = fopen (record , "r");    //SRecord1 contains Students' Firstnames, Lastnames and IDs
	SRecord2 = fopen (grade , "r");     //SRecord2 contains Students' Grades
	int i = 0 , ID , Marks;             // i and j are the indexes to put elements in different arrays (same index for the four records about one person)

	if (SRecord1 == NULL || SRecord2 == NULL){
		printf ("No file found\n");
	}
	else {
		 while (!feof (SRecord1) || !feof(SRecord2)){          //recover every data until the end of the files
			 fflush (stdin);                                    //clearing out the memory
			 fscanf (SRecord1 , "%s\t" , tab[i].FirstNames);    //Put each Firstnames in the array FirstNames of the structure StudentRecord
			 fflush (stdin);                                    //clearing out the memory
			 fscanf (SRecord1 , "%s\n" , tab[i].LastNames);     //Put each Lastnames in the array LastNames of the structure StudentRecord
			 fflush (stdin);                                    //clearing out the memory
			 fscanf (SRecord1 , "%d\n" , &ID);
			 tab[i].IDNums = ID;                              //Put each IDs in the array IDNums of the structure StudentRecord
			 fscanf (SRecord2 , "%d\n" , &Marks);
			 tab[i].Marks = Marks;    			                           //Put each grades in the array Marks of the structure StudentRecord
			 i++;
		 }
		 fclose (SRecord1);
		 fclose (SRecord2);
		 *size = i;                                             //Size permits to know how much elements are in the arrays
    }
}



// This function allows to classify the array LastNames by alphabetical order and then puts the same index on the different arrays for the Firstname, ID and grade of the same person
void quicksort (struct StudentRecord tab[MAXNAMELENGTH] , int size){
    int i , order , ids , grades;                                       //order will be negative if the 2 lastnames compared are in the right order and will be positive for the contrary
    char lastname[MAXNAMELENGTH] , firstname[MAXNAMELENGTH];
    int inter;
    do{
       inter = 0;
       for (i=0 ; i<(size-1) ; i++){
           order = strcmp(tab[i].LastNames , tab[i+1].LastNames);
           if (order > 0){
              strcpy(lastname , tab[i].LastNames);                      //strcpy is to change 2 strings of an array
              strcpy(firstname , tab[i].FirstNames);
              ids = tab[i].IDNums;
              grades = tab[i].Marks;

              strcpy (tab[i].LastNames , tab[i+1].LastNames);
              strcpy (tab[i].FirstNames , tab[i+1].FirstNames);
              tab[i].IDNums = tab[i+1].IDNums;
              tab[i].Marks = tab[i+1].Marks;

              strcpy (tab[i+1].LastNames , lastname);
              strcpy (tab[i+1].FirstNames , firstname);
              tab[i+1].IDNums = ids;
              tab[i+1].Marks = grades;

              inter = 1;
            }
        }
    }
    while (inter);
}


//Find the entire student's file that we are looking for or not if the student does not exist
void pickup_someone(char student [MAXNAMELENGTH] ,struct StudentRecord tab[MAXNAMELENGTH] , int size){
	int i , index = 0;
	for (i = 0 ; i < size ; i++){
		if (strcmp(student,tab[i].LastNames)==0){     //strcmp compares the name put in the terminal with the LastNames array to find the good one
			printf("The following record was found:\nName: %s %s\nStudent ID: %d\nStudent Grade: %d\n", tab[i].FirstNames, tab[i].LastNames, tab[i].IDNums, tab[i].Marks);
			index = 1;
		}
	}
	if (index == 0){
		printf ("No record found for student with last name %s.\n",student);
	}
}



//Principal function that will make functionning every others
int main(int argc , char*argv[]){
	char record[15] , grade[15] , student[MAXNAMELENGTH];
	sscanf(argv[1] , "%s" , record);
	sscanf(argv[2] , "%s" , grade);
    sscanf(argv[3] , "%s" , student);


    struct StudentRecord tab[MAXNAMELENGTH];
    int size = 0;

    fflush(stdin); //clearing out the memory

    put_into_struct(record , grade , tab , &size);
    quicksort(tab , size);
    pickup_someone(student , tab , size);

    return 0;
}


