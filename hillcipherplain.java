// Ravi Singh 
// NID: ra758940 
// CIS 3360 -- Programming Assignment 1 
// This program transfers plain text into cipher text using a Hill Cipher encryption 


import java.util.*;
import java.io.*;

public class hillcipherplain 
{

	public static void main(String [ ] args) throws IOException
	{
		
		Scanner in = new Scanner(new File(args[0]));
		int N = in.nextInt();
		int i, j = 0;
		int dimension = N;
		int newLength = 0;
		
		// 2-D Array to hold key values 
		int key[][] = new int[N][N];
	
		// Add all integers to key array 
		for (i = 0; i < key.length; i++)
		{
			for(j = 0; j < key.length; j++)
			{
				N = in.nextInt();
				key[i][j] = N;	
			}
		}
		
		System.out.println();
		System.out.println("Corresponding Key from File (Shown as a matrix) : ");
		System.out.println();
		
		for(i = 0; i<key.length; i++)
		{
		    for(j = 0; j<key.length; j++)
		    {
		        System.out.print(" " + key[i][j]);
		    }
		    
		    System.out.println();
		}
		
		System.out.println();

		String plaintext= "";
		
		Scanner in2 = new Scanner(new File(args[1]));
		
		// Occupy plaintext with the contents in the text file  
		while (in2.hasNext() == true)
			plaintext+= in2.next();
		
		if (plaintext.length() > 10000)
		{
			System.out.println("Error: input too large. Ending program...");
			System.exit(1);
		}
		
		// ufplain[] holds lower case text from text file 
		// fplain[] holds filtered characters
		char[] ufplain= plaintext.trim().toLowerCase().toCharArray();
		char[] fplain= new char[ufplain.length];

		j = 0;
		
		for (i= 0; i < ufplain.length; i++)
		{
			//strip away all non letters
			if (ufplain[i] < 'a' || ufplain[i] > 'z')
				continue;
			//add letters to the final filtered plain array
			else
			{
				fplain[j++]= ufplain[i];
				newLength++;
			}
				
		}
		
		// new array of the appropriate size eliminating unnecessary characters 
		char newfplain[] = new char[newLength];
		
		// copy all letters into new array 
		for(i=0;i<newfplain.length;i++)
		{
			newfplain[i] = fplain[i];
		}
		
		System.out.println();
		
		// initial size of array 
		int sizeoftextAscii = newfplain.length;
		
		// keep incrementing size until we get to a size that is divisible by the length of the array 
		while(sizeoftextAscii % dimension != 0)
		{
			sizeoftextAscii++;
		}
		
		int textAscii[] = new int[sizeoftextAscii]; // array to hold ASCII values 
		int temp[] = new int[dimension]; // Secondary matrix array to do multiplication 
		int tempresults2[] = new int[textAscii.length]; // array to hold results of multiplication 
		int cntFlag = 0;
		int k = 0;
		
		// initially set all values of textAscii to 23 (X) for padding then overwrite
		// leaving the remaining indexes not used still 23
		for(i = 0; i < textAscii.length; i++)
		{
			textAscii[i] = 23;
		}
		
		// populate textAscci values with the equivalent Ascii values from plaintext
		for(i = 0; i < newfplain.length; i++)
		{
			textAscii[i] = newfplain[i] - 'a';
		}
		
		// Print out the plain text 
		printPlain(textAscii);
		
		while(cntFlag < textAscii.length)
		{
			
			// populate each N letters into temp array for multiplication 
			for (i = 0; i < temp.length; i++)
			{
				temp[i] = textAscii[cntFlag];
				cntFlag++;
			}

			
			// do multiplication with key and temp
			if( k < tempresults2.length)
			{
				for(i=0;i<key.length; i++)
				{
					
					for(j=0;j<key.length;j++)
					{
						tempresults2[k] += key[i][j] * temp[j];	
					}
					
					tempresults2[k] = tempresults2[k] % 26;
					k++;
				}
			
			}
		}
		
		// function to print out in given format 
		printCipher(tempresults2);
		
		System.exit(0);
		
	}
	
	public static void printPlain(int newfplain[])
	{
		
		int i=0;
		int k=0;
		int n=0;
		int cnt = 0;
		int length = newfplain.length;
		int numLines = (length / 80) + 1;
		
		System.out.println("Plain Text: ");
		
		if (length <= 80)
		{
			
			for (i=0; i<length; i++)
			{
				System.out.print("" +(char)('a' + newfplain[i]));
			}
			
			System.out.println();
			System.out.println();
			return;
			
		}
		
		while(k <= numLines && cnt <= length)
		{
			
			for (i=i; i<cnt; i++)
			{
				System.out.print("" +(char)('a' + newfplain[i]));
			}
			
			System.out.println();
			cnt += 80;
			
			
			if (cnt > length)
			{
				cnt = length % 80;
				
				for(i=i; n<cnt; i++)
				{
					System.out.print("" +(char)('a' + newfplain[i]));
					n++;
				
				}
				
				System.out.println();
				System.out.println();
				return;
				
			}
			
			k++;
		}
		
	}
			
	public static void printCipher(int tempresults2[])
	{
		
		int i=0;
		int k=0;
		int n=0;
		int cnt = 0;
		int length = tempresults2.length;
		int numLines = (length / 80) + 1;
		
		System.out.println();
		System.out.println("Cipher Text: ");
		
		if (length <= 80)
		{
			for (i=0; i<length; i++)
			{
				System.out.print("" +(((char)('a' + tempresults2[i]))));
			}
			
			System.out.println();
			System.out.println();
			return;
		}
		
		while(k <= numLines && cnt <= length)
		{
			
			for (i=i; i<cnt; i++)
			{
				System.out.print("" +(((char)('a' + tempresults2[i]))));
			}
			
			System.out.println();
			cnt += 80;
			
			
			if (cnt > length)
			{
				cnt = length % 80;
				
				for(i=i; n<cnt; i++)
				{
					
					System.out.print("" +(((char)('a' + tempresults2[i]))));
					n++;
				
				}
				
				System.out.println();
				System.out.println();
				return;
			}
			
			
		}
			k++;
	}
	
		
}

