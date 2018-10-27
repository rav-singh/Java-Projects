import java.io.*;
import java.util.*;
import java.awt.Point;



public class hack
{
	private static Random randNum;
	private static int boardSize;
	private static ArrayList<Point> zombies;
	private static int userRow, userCol;
	
	// R is regular zombie
	// A is advanced zombie (teleport to location after killing
	// S is special zombie (can only kill if adjacent)
	// E is explosive zombie (explodes entire row & col)
	private static char [] visuals = new char[] {(char)183, '*', 'R', 'A', 'S', 'E'}; 
	
	public static void main(String [] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("What size board would you like to play on?");
		boardSize = in.nextInt();
		int startingPosition = boardSize / 2;
		Point tempZom = new Point();
		boolean playing = true;
		
		int [][] board = new int[boardSize][boardSize];
		randNum = new Random();
		
		for(int i = 1; i <= boardSize; i++)
		{	
			userRow = startingPosition;
			userCol = startingPosition;
			zombies = new ArrayList<>();
			board = resetBoard(board, startingPosition);
			board = addZombies(board, i);
			int numZombies = i;
			int rowInput = -1;
			int colInput = -1;
			int option = -1;
			int moveOption;
			
			System.out.println("Round " + i);
			printBoard(board);
			
			while(numZombies > 0)
			{
				System.out.println("Your turn:");
				
				boolean validInput = false;
				
				while(!validInput)
				{
					System.out.println("Move (0) or Attack (1)?");
					option = in.nextInt();
					if(option == 1)
					{
					
						System.out.println("Select a location to bomb.. <row> <col>");
						rowInput = in.nextInt();
						colInput = in.nextInt();
				
						if(rowInput >= boardSize || colInput >= boardSize || 
							rowInput < 0 || colInput < 0)
						{
							System.out.println("Invalid input. Please try again..");
						}
						else if(board[rowInput][colInput] == 0)
							System.out.println("Invalid input. Please try again..");
						else
							validInput = true;
					}
					else
					if(option == 0)
					{
						System.out.println("To move up enter 0\nTo move right enter 1\nTo move down enter 2\nTo move left enter 3");
						moveOption = in.nextInt();
						if(moveOption == 0)
						{
							if(userRow > 0 && board[userRow-1][userCol] == 0)
							{
								board[userRow][userCol] = 0;
								board[userRow-1][userCol] = 1;
								userRow--;
								validInput = true;
							}
							else
								System.out.println("Invalid input. Please try again..");
						}
						if(moveOption == 1)
						{
							if(userCol < boardSize-1 && board[userRow][userCol+1] == 0)
							{
								board[userRow][userCol] = 0;
								board[userRow][userCol+1] = 1;
								userCol++;
								validInput = true;
							}
							else
								System.out.println("Invalid input. Please try again..");
						}
						if(moveOption == 2)
						{
							if(userRow < boardSize-1 && board[userRow+1][userCol] == 0)
							{
								board[userRow][userCol] = 0;
								board[userRow+1][userCol] = 1;
								userRow++;
								validInput = true;
							}
							else
								System.out.println("Invalid input. Please try again..");
						}
						if(moveOption == 3)
						{
							if(userCol > 0 && board[userRow][userCol-1] == 0)
							{
								board[userRow][userCol] = 0;
								board[userRow][userCol-1] = 1;
								userCol--;
								validInput = true;
							}
							else
								System.out.println("Invalid input. Please try again..");
						}
						
						
					}
					
				}
				
				if(option == 1)
				{
					if(board[rowInput][colInput] == 2)
					{
						board[rowInput][colInput] = 0;
						numZombies--;
					}
				
					if(board[rowInput][colInput] == 3)
					{
						board[userRow][userCol] = 0;
						board[rowInput][colInput] = 1;
						numZombies--;
						userRow = rowInput;
						userCol = colInput;
					}
					
					if(board[rowInput][colInput] == 4)
					{
						if(Math.max(Math.abs(rowInput - userRow), Math.abs(colInput - userCol)) <= 1)
						{
							board[rowInput][colInput] = 0;
							numZombies--;
						}
						else
							System.out.println("This zombie is too far to kill! (sorry)");
						
					}
					
					if(board[rowInput][colInput] == 5)
					{
						for(int loop = 0; loop < board.length; loop++)
						{
							if(board[rowInput][loop] != 0)// && board[rowInput][loop] != 1)
							{
								board[rowInput][loop] = 0;
								numZombies--;
							}
							if(board[loop][colInput] != 0)// && board[loop][colInput] != 1)
							{
								board[loop][colInput] = 0;
								numZombies--;
							}
							
						}
						
					}
					
				}
				
				for(Iterator<Point> zom = zombies.iterator(); zom.hasNext();)
				{
					Point element = zom.next();
System.out.println("x is: " + element.x + " and y is: " + element.y);
					if(element.x == rowInput && element.y == colInput)
					{
System.out.println("flag");
						zom.remove();
					}
				}
System.out.println(numZombies);
 
				
				
				printBoard(board);
				
				if(numZombies > 0)
				{
					System.out.println("Zombies' turn:");
					for(Point temp : zombies)
					{
System.out.println("processing a zombie");
						// Same row
						int type = board[temp.x][temp.y];
						if(temp.x == userRow)
						{
							if(temp.y < userCol)// && board[temp.x][temp.y] == 2)
							{
								board[temp.x][temp.y] = 0;
								temp.setLocation(temp.x, temp.y + 1);
								board[temp.x][temp.y] = type;
							}
							else if(temp.y > userCol)// && board[temp.x][temp.y] == 2)
							{
								board[temp.x][temp.y] = 0;
								temp.setLocation(temp.x, temp.y - 1);
								board[temp.x][temp.y] = type;
							}
						
						}
					
						// Same col
						else if(temp.y == userCol)
						{
							if(temp.x < userRow)// && board[temp.x][temp.y] == 2)
							{
								board[temp.x][temp.y] = 0;
								temp.setLocation(temp.x + 1, temp.y);
								board[temp.x][temp.y] = type;
							}
							else if(temp.x > userRow)// && board[temp.x][temp.y] == 2)
							{
								board[temp.x][temp.y] = 0;
								temp.setLocation(temp.x - 1, temp.y);
								board[temp.x][temp.y] = type;
							}
						}
					
						else 
						{
							int move = Math.abs(randNum.nextInt() % 2);
System.out.println("move is: " + move);						
							// Move to different row
							if(move == 0)
							{
								if(temp.x > userRow)// && board[temp.x][temp.y] == 2)
								{
									board[temp.x][temp.y] = 0;
									temp.setLocation(temp.x - 1, temp.y);
									board[temp.x][temp.y] = type;
								}
								else if(temp.x < userRow)// && board[temp.x][temp.y] == 2)
								{
									board[temp.x][temp.y] = 0;
									temp.setLocation(temp.x + 1, temp.y);
									board[temp.x][temp.y] = type;
								}
							}
						
							// Move to different col
							else if(move == 1)
							{
								if(temp.y > userCol)// && board[temp.x][temp.y] == 2)
								{
									board[temp.x][temp.y] = 0;
									temp.setLocation(temp.x, temp.y - 1);
									board[temp.x][temp.y] = type;
								}
								else if(temp.y < userCol)// && board[temp.x][temp.y] == 2)
								{
									board[temp.x][temp.y] = 0;
									temp.setLocation(temp.x, temp.y + 1);
									board[temp.x][temp.y] = type;
								}
							}
							
						}
					
					
					}	
					printBoard(board);
				
				
				}
				
				if(board[userRow][userCol] != 1)
				{
					System.out.println("YOU LOSE, MAN!");
					return;
				}
				
			}
			System.out.println("You got all the zombies in Round " + i + "!\n");
			
		}
		System.out.println("You beat every Round!");
		
	}
	
	public static int[][] addZombies(int [][] array, int numZombies)
	{
		for(int i = 0; i < numZombies; i++)
		{
			int zomRow = Math.abs(randNum.nextInt() % boardSize);
			int zomCol;
			
			if(zomRow == 0 || zomRow == boardSize - 1)
			{
				zomCol = Math.abs(randNum.nextInt() % boardSize);
			}
			else
			{
				zomCol = Math.abs(randNum.nextInt() % 2);
				if(zomCol == 1)
					zomCol = boardSize - 1;
			}
			if(array[zomRow][zomCol] == 0)
			{
				Point temp = new Point();
				temp.x = zomRow;
				temp.y = zomCol;
				zombies.add(temp);
				
				int type = Math.abs(randNum.nextInt() % 4) + 2;
				array[zomRow][zomCol] = type;
			}
			else
				i--;
		}
		
		return array;
	}
	
	public static int[][] resetBoard(int [][] array, int start)
	{
		for(int i = 0; i < array.length; i++)
			Arrays.fill(array[i], 0);
		array[start][start] = 1;
		
		return array;
	}
	
	public static void printBoard(int [][] array)
	{
		// Prints # of col along the top
		System.out.print("      ");
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(i + " " + ((i < 10) ? " " : ""));
		}
		System.out.println();
		
		// Prints top border
		System.out.print("   +-");
		for(int i = 0; i < array.length; i++)
		{
			System.out.print("---");
		}
		System.out.println("+");
		
		// Prints contents and side borders
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(i  + ((i < 10) ? " " : "") + " | ");
			
			for(int j = 0; j < array.length; j++)
			{
				System.out.print(" " + visuals[array[i][j]] + " ");
			}
			System.out.println("|");
		}
		
		// Prints bottom border
		System.out.print("   +-");
		for(int i = 0; i < array.length; i++)
		{
			System.out.print("---");
		}
		System.out.println("+");
		

		
	}
	
}