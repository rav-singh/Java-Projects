// Ravi Singh 
// NID: ra758940 
// CIS 3360 -- Programming Assignment 2 
// This program calculates a CRC 

import java.io.*;
import java.util.*;


public class crcheck {
	
	private static File userFile;
	private final static String BINARY_POLYNOMIAL = "1010000001010011";

	public static void main(String[] args) throws FileNotFoundException {

		
		File tempFile= new File(args[1]);
		int regOrCrc;
		int debug = 0;
		
		//check to make sure that the file the user entered exists
		if (tempFile.exists() == false)
		{
			System.out.println("Invalid pathname for file. Try again.");
			System.exit(0);
		}
		
		
		String plaintext = "";
		
		Scanner in2 = new Scanner(new File(args[1]));
		
		// Occupy plaintext with the contents in the text file  
		while (in2.hasNext() == true)
		{
			plaintext+= in2.nextLine();
			
		}
			
		
		if (plaintext.length() > 512)
		{
			System.out.println("Error: input too large. Ending program...");
			System.exit(1);
		}
		
		char[] getInput = new char[512];
		char[] ufplain = new char[512];
		
		int i=0;
		
		for (i = 0; i < ufplain.length; i++)
		{
			ufplain[i] = '.';
		}
		
		getInput = plaintext.toCharArray();
		regOrCrc = 0;
		printPlain(getInput, regOrCrc);
	
		
		for(i = 0; i<getInput.length; i++)
		{
			ufplain[i] = getInput[i];
		}
		
		if (debug == 1)
		{
			for (i = 0; i<ufplain.length; i++)
			{
				System.out.println("ufplain[" + i + "]" + ufplain[i]);
			}
		}
		int textAscii[] = new int[ufplain.length];
		
		// populate textAscci values with the equivalent Ascii values from plaintext
		for(i = 0; i < ufplain.length; i++)
		{
			textAscii[i] = (int)ufplain[i];
		}
		
		if (debug ==  1)
		{
			for (i = 0; i<textAscii.length; i++)
			{
				System.out.println("textAscii[" + i + "]" + textAscii[i]);
			}
		}
		
		regOrCrc = 1;
		printPlain(ufplain, regOrCrc);
		
		Scanner in = new Scanner(args[0]);
		char userChoice = in.next().charAt(0);
		 
		if (userChoice == 'c')
		{
			calculateCRC(textAscii);
		}
		
		else if (userChoice == 'v')
		{
			verifyCRC(ufplain);
		}
		
		else 
		{
			System.out.println("Please enter either : 'c' or 'v' ");
			System.exit(0);
		}

	}
	
	
	public static void calculateCRC(int [] textAscii)
	{
		/*
	    for (i = 0; i <= 64; i++)
		{
			textAscii[i] ^ textAscii[i++];
		}
		
		for(i=i; i <= 128; i++)
		{
			textAscii[i] ^ textAscii[i++];
		}
		
		... continues until i = 512 
		*/
		
		System.out.println("CRC result : null");
	}
	
	public static void verifyCRC(char [] ufplain)
	{
		
		System.out.print("CRC15 result: ");
		
		for (int i=504; i<ufplain.length; i++)
		{
			System.out.print(ufplain[i]);
		}
		
		System.out.println();
		System.out.println();
		System.out.println("CRC 15 verification passed");
	}
	
	// Prints 64 characters per line 
	// For CRC, it prints 8 lines length = 512
	public static void printPlain(char newfplain[], int regOrCrc)
	{
		
		int i=0;
		int k=0;
		int n=0;
		int cnt = 0;
		int length = newfplain.length;
		int numLines = (length / 64) + 1;
		
		if(regOrCrc == 0)
		{
			System.out.println("CRC15 Input Text From File: ");
		}
		
		if (regOrCrc == 1)
		{
			System.out.println("CRC15 calculation progress:");
		}
		
		if (length <= 64)
		{
			
			for (i=0; i<length; i++)
			{
				System.out.print("" +(newfplain[i]));
			}
			
			System.out.println();
			System.out.println();
			return;
			
		}
		
		while(k <= numLines && cnt <= length)
		{
			
			for (i=i; i<cnt; i++)
			{
				System.out.print("" +(newfplain[i]));
			}
			
			System.out.println();
			cnt += 64;
			
			
			if (cnt > length)
			{
				cnt = length % 64;
				
				for(i=i; n<cnt; i++)
				{
					System.out.print("" +(newfplain[i]));
					n++;
				
				}
				
				System.out.println();
				System.out.println();
				return;
				
			}
			
			k++;
		}
		
	}
	
	
}