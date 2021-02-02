/* Tlookup.c
 *
 *      Name: Aurelia Haas
 *      Assignment 3
 *
 */



#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#define MAXRECORDS 50
#define MAXNAMELENGTH 10



// This structure will contain every data of each student: Last names, First names, IDs and Grades.
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};



// This structure will permit to create a lexicographic tree.
struct LexTree
{
	char *lastnames;
	char *firstnames;
	int IDs;
	int grades;
	struct LexTree *left;
	struct LexTree *right;
};





//The function addnode is able to create exactly the number of nodes where we will put each last name in the LexTree and in the alphabetical order with there first names, grades and IDs corresponding.
void addnode (struct LexTree **root , char *lastnames , char *firstnames , int ID , int grades)
{
	if (*root == NULL)
	{
		struct LexTree* cur_root = (struct LexTree*) malloc (sizeof (struct LexTree) );      //We create in LexTree memory enough place to put every data as a lexicographic tree.
		cur_root-> lastnames = lastnames;
		cur_root-> firstnames = firstnames;
		cur_root-> IDs = ID;
		cur_root-> grades = grades;
		cur_root-> left = 0;
		cur_root-> right = 0;
		*root = cur_root;
	}

	if (strcmp ((*root)-> lastnames , lastnames) > 0)                           //This will create the tree in the good order thanks to a name comparison, and each node will contain mainly the last name and then the first name, the ID and the grade.
	{
		addnode (&((*root)-> left) , lastnames , firstnames , ID , grades);
	}
	else if (strcmp ((*root)-> lastnames , lastnames) < 0)
	{
		addnode (&((*root)-> right) , lastnames , firstnames , ID , grades);
	}
	else
	{
		return;
	}
}





// find_someone is a function to find the student we are looking for even if we enter the name with a different typography as the current data is.
void find_someone (char name[MAXNAMELENGTH])
{
	int i = 0;                                       //i is the place of each letter in the name.
	name[0] = toupper(name[0]);                      //Put the first letter of the name as a capital letter (function toupper from ctype.h).
	for (i = 1 ; i < MAXNAMELENGTH ; i++)            //A loop to change every other letters as lower cases (function tolower from ctype.h).
	{
		name[i] = tolower (name[i]);
	}
}





//ptree is the function of searching of each name with all of it complementary data. This function does a quick research, thanks to last name's comparison,then we will continue on the right or on the left to find the person we are looking for more efficiently.
void ptree (struct LexTree *root , char name[MAXNAMELENGTH])
{
	find_someone (name);                            //We do this function just to find the person we are looking for in the tree without traversing by everyone and every tree's node.

	if (root == NULL)                               //Case if the name is correctly placed.
	{
		return;
	}

	if (root->left != NULL)                         //Case: if the name searched is before in the alphabetical order, go to the left tree's node.
	{
		ptree ( root->left , name);
	}

	if (root->right != NULL)                        //Case: if the name searched is after, in the alphabetical order, go to the right tree's node.
	{
		ptree (root->right , name);
	}

	if ( strcmp (root->lastnames , name) == 0)      //If we find the good person, print all of his data (name is correctly written which can match with a name in the tree).
	{
		printf ("The following record was found:\nName: %s %s\nStudent ID: %d\nStudent Grade: %d\n", root->firstnames , root->lastnames , root->IDs , root->grades);
	}
	return;
}





//This is the function to open all the data send in the assignment (NamesIDs.txt and marks.txt and then put them in the LexTree such that we can create the lexicographic tree.
void open_files (char record[15] , char grades[15] , char name[MAXNAMELENGTH] , struct StudentRecord SRecords[MAXRECORDS])
{
	int index1 = 0 , index2 = 0 , index3 = 0;
	char false_name[MAXNAMELENGTH];
	strcpy (false_name , name);                     //It serves only to print the name as we wrote it in the terminal if the person does not belong to the tree.

	FILE *SRecord1 = NULL, *SRecord2 = NULL;
		SRecord1 = fopen (record , "r");            //SRecord1 contains Students' first names, last names and IDs.
		SRecord2 = fopen (grades , "r");            //SRecord2 contains Students' Grades.

		if (SRecord1 == NULL || SRecord2 == NULL)
		{
			printf ("No file found\n");
		}

		struct LexTree *root = NULL;             //Put one by one, until the end of each file, each data about everyone in the StudentRecord structure.
		find_someone (name);
		while (fscanf (SRecord1 , "%s%s%d" , &(SRecords[index1].FirstNames[0]) ,  &(SRecords[index1].LastNames[0]) ,  &(SRecords[index1].IDNums)) != EOF && fscanf(SRecord2 , "%d" ,  &(SRecords[index1].Marks)) != EOF)      //To put in each part of StudenRecord the same index for each data of the same person.
		{
			index1++;
			if (strcmp (name , &(SRecords[index3].LastNames[0])) != 0)
			{
				index3++;
				if (index3 == MAXRECORDS)
				{
					printf ("No record found for student with last name %s.\n" , false_name);
				}
			}
		}
		fclose (SRecord1);
		fclose (SRecord2);

		while (index2 < index1)                     //It will do the addnode for everyone
		{
			addnode (&root , &(SRecords[index2].LastNames[0]) , &(SRecords[index2].FirstNames[0]) , (SRecords[index2].IDNums) , (SRecords[index2].Marks));
			index2 ++;
		}
		ptree (root ,name);
}





// main is the principal function, it will execute each function above in the great order to print what we want to
int main (int argc , char *argv[])
{
	struct StudentRecord SRecords[MAXRECORDS];
	open_files (argv[1] , argv[2] , argv[3] , SRecords);  //this function contains the execution of all others so I don't need to execute each function in the main
	return 0;
}



