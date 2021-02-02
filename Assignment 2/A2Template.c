/*

 Assignment 2 ECSE202
 This program assumes that
 a names and IDs data file (location and name) is passed as the first argument
 a marks data files is passed as the second argument
 an ID number is passed as the third argument
 */


#define MAXRECORDS 100
#define MAXNAMELENGTH 15
#include <stdio.h>
#include <stdlib.h>
//Define structure to hold student data
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};


int main(int argc, char * argv[]) {

	struct StudentRecord SRecords[MAXRECORDS];
    	int numrecords, nummarks, recordnum;
	

	//Read in Names and ID data
	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}
	
	numrecords=0;
    	while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords[numrecords].FirstNames[0]),
		      				&(SRecords[numrecords].LastNames[0]),
		      				&(SRecords[numrecords].IDNums)) != EOF) {
	  numrecords++;
 	}
	  
	fclose(NamesIDsDataFile);
	
	//Read in marks data
	FILE * MarksDataFile;
	if((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}
	nummarks=0;
	while(fscanf(MarksDataFile,"%d",&(SRecords[nummarks].Marks)) != EOF) {
	    nummarks++;
	}
	
	fclose(MarksDataFile);
	
	//Print out data as read in
	for(recordnum=0;recordnum<numrecords;recordnum++){
		printf("%s %s %d %d\n",SRecords[recordnum].FirstNames,SRecords[recordnum].LastNames,SRecords[recordnum].IDNums, SRecords[recordnum].Marks);
	}
	printf("A total of %d records printed.\n",numrecords);

	return EXIT_SUCCESS;
}
