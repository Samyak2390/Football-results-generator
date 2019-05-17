package footBall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class ID_77202779{
	public static void main(String[] args) {
		//Declaring required scanners
		Scanner scan2 = null;
		Scanner scan3 = null;
		Scanner scan4 = null;
		Scanner scan5 = null;
		//Declaring required arrays and other variables
		String[] lines;
		String[] home_team_name;
		String[] away_team_name;
		String[] home_score;
		String[] away_score;
		//number of delimiters in each line
		int[] delimiter_sum;
		//stores index of error free lines
		int[] error_free_lines;
		//If any error is found in any line, index of that line is stored in array list "errors_in_line"
		List<Integer> errors_in_line;
		int count = 0;
		int countErrors = 0;
		
		try {
			//Prompting user to input file name
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the name of file along with its extension: ");
			String name = scan.next();
			File footballFile = new File(name);
			scan2 = new Scanner(footballFile);
			//loop to count total number of lines
			while(scan2.hasNextLine()){
				scan2.nextLine();
				count ++;
			}
			
			//Initializing required arrays
			lines = new String[count];
			home_team_name = new String[count];
			away_team_name = new String[count];
			home_score = new String[count];
			away_score = new String[count];
			delimiter_sum = new int[count];
			errors_in_line = new ArrayList<Integer>();
			scan3 = new Scanner(footballFile);
			int i = 0;
			//loop that stores each line from file into an array called lines
			while(i < count) {
				lines[i] = scan3.nextLine();
				i++;
			}
			//loop that checks if a line has less than three delimiters
			for(int b = 0; b<count; b++) {	
				int countDelimiters = 0;
				//loop that counts total number of ":" and finds invalid delimiters
				for(int a = 0; a < lines[b].length() ; a++) {
					char character = lines[b].charAt(a);
					if(character == ':') {
						countDelimiters ++;
					}
					
					String str = Character.toString(character);
					if(!(str.chars().allMatch(Character::isLetterOrDigit) || character==':' || character==' ')) {
						System.out.println("Error: " + str + " is not appropriate delimiter in line " + (b+1));
						errors_in_line.add(b);
						countErrors ++;
					}
				}
				if(countDelimiters < 3) {
					System.out.println("Error: One or more Delimiter is missing in line " + (b+1));
					errors_in_line.add(b);
					countErrors ++;
				}
				delimiter_sum[b] = countDelimiters;
			}
			
			int j = 0;
			//main loop that assigns values to respective arrays
			for(String x:lines) {
				if(delimiter_sum[j] == 3) {
					int split_count = 0;
					String[] splitted = x.split(":");
					//loop to find missing values 
					for(String y: splitted) {
						y = y.replaceAll("\\s+", "");
						if(split_count == 0 && (y.equals(""))) {
							System.out.println("Error: Home Team Name in line " + (j+1) + " of the file is missing.");
							errors_in_line.add(j);
							countErrors ++;
						}
						if(split_count == 1 && (y.equals("") )) {
							System.out.println("Error: Away Team Name in line " + (j+1) + " of the file is missing.");
							errors_in_line.add(j);
							countErrors ++;
						}
						if(split_count == 2 && (y.equals("") )) {
							System.out.println("Error: Home Team Score in line " + (j+1) + " of the file is missing.");
							errors_in_line.add(j);
							countErrors ++;
						}
						if(split_count == 3 && (y.equals("") )) {
							System.out.println("Error: Away Team Score in line " + (j+1) + " of the file is missing.");
							errors_in_line.add(j);
							countErrors ++;
						}
						split_count++;
					}
					scan4 = new Scanner(x);
					scan4.useDelimiter(":");
					String team1 = scan4.next();
					home_team_name[j] = team1.trim();
					
					String team2 = scan4.next();
					away_team_name[j] = team2.trim();
					
					String team1Score = scan4.next();
					team1Score = team1Score.trim();
					if(!team1Score.equals("")) {
						int intgoals;
						//Finding if home score is valid number
						try {
							intgoals = Integer.parseInt(team1Score);
							//Finding if home score is negative
							if(intgoals < 0) {
								System.out.println("Error: Home Team Score in line " + (j+1) + " of the file is in negative.");
								errors_in_line.add(j);
								countErrors ++;
							}
							
						}
						catch(NumberFormatException e){
							System.out.println("Error: Home Team Score '"+ team1Score +"' in line " + (j+1) + " of the file is invalid.");
							errors_in_line.add(j);
							countErrors ++;
						}
						
					}
					home_score[j] = team1Score;
					
					//Finding if away score is valid number
					// this try function throws NoSuchElementException because there may be lines with missing values
					try {
						String team2Score = scan4.next();
						team2Score = team2Score.trim();
						if(!team2Score.equals("")) {
							int intgoals2;
							try {
								intgoals2 = Integer.parseInt(team2Score);
								//Finding if away score is negative
								if(intgoals2 < 0) {
									System.out.println("Error: Away Team Score in line " + (j+1) + " of the file is in negative.");
									errors_in_line.add(j);
									countErrors ++;
								}
								
							}
							catch(NumberFormatException e){
								System.out.println("Error: Away Team Score '"+ team2Score +"' in line " + (j+1) + " of the file is invalid.");
								errors_in_line.add(j);
								countErrors ++;
							}
						}
						away_score[j] = team2Score;
					}
					//catches NoSuchElementException and ignores it because we are just printing lines with no errors
					catch(NoSuchElementException e){}
					
				}
				j++;
			}
			//converting array list into set to prevent from existing duplicate numbers
			Set<Integer> set = new HashSet<>(errors_in_line);
			//Printing some statistics about the games
			System.out.println("--------------------------------------------------------------------");
			System.out.printf("|   Total Number of Errors: %-39d|", countErrors );
			System.out.printf("%n|   Total Number of games Excluded (Corrupted lines): %-13d|", set.size());
			System.out.printf("%n|   Total Number of games Included (Non Corrupted lines): %-9d|%n", (count-set.size()));
			System.out.println("--------------------------------------------------------------------");
			//Printing in said format
			System.out.printf("%n%n%-20s%-20s%-20s%-20s%n", "Home team", "Score", "Away team", "Score");
			System.out.printf("%-20s%-20s%-20s%-20s%n", "=========", "=====", "=========", "======");
			
			int error_free_lines_count = 0;
			
			error_free_lines = new int [count - set.size()];
			//Filtering lines without error before printing
			for(int print = 0, error_free = 0; print < count; print++) {
				int state = 0;
				for(int y: set) {
					if(print == y) {
						state = 1;	
					}
				}
				if(state == 0){
					if(delimiter_sum[print] == 3) {
						error_free_lines[error_free] = print;
						error_free ++;
						error_free_lines_count++;
						System.out.printf("%-22s%-18s%-22s%-20s%n", home_team_name[print], home_score[print], away_team_name[print], away_score[print]);
					}
				}
			}
			scan5 = new Scanner(System.in);
			//Showing options for various cases
			System.out.println("\n\n-----Advanced Features-----\nEnter a number to: \n1. Display all matches played by a team.");
			System.out.println("2. Display all matches won by a team. \n3. Display all matches lost by a team. \n4. Display all time high Score.");
			System.out.println("5. Display all matches that were draw.\n6. Display total goals scored by a team.");
			System.out.println("7. Display total goals conceded by a team.");
			
			String decision = scan5.nextLine();
			int dec = Integer.parseInt(decision);
			
			switch(dec) {
			//Display all matches played by a team.
			case 1:
				int countSearch = 0;
				System.out.println("Enter a team's name: ");
				String teamSearch = scan5.nextLine();
				System.out.printf("%n%n%-20s%-20s%-20s%-20s%n", "Home team", "Score", "Away team", "Score");
				System.out.printf("%-20s%-20s%-20s%-20s%n", "=========", "=====", "=========", "======");
	
				for(int k = 0; k < error_free_lines_count; k++) {
					
					int EF_line = error_free_lines[k];
					if(teamSearch.toLowerCase().equals(home_team_name[EF_line].toLowerCase()) || teamSearch.toLowerCase().equals(away_team_name[EF_line].toLowerCase())) {
						System.out.printf("%-22s%-18s%-22s%-20s%n", home_team_name[EF_line], home_score[EF_line], away_team_name[EF_line], away_score[EF_line]);
						countSearch ++;
					}
				}
				System.out.println(countSearch == 0 ? "\n The team " + teamSearch +" was not found." : "\n" + countSearch + " results were found.");
				break;
				
			case 2:
				//Display all matches won by a team
				int countSearch1 = 0;
				System.out.println("Enter a team's name: ");
				String teamSearch1 = scan5.nextLine();
				System.out.printf("%n%n%-20s%-20s%-20s%-20s%n", "Home team", "Score", "Away team", "Score");
				System.out.printf("%-20s%-20s%-20s%-20s%n", "=========", "=====", "=========", "======");
				
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if((teamSearch1.toLowerCase().equals(home_team_name[EF_line].toLowerCase()) &&(Integer.parseInt(home_score[EF_line]) > Integer.parseInt(away_score[EF_line]))) || (teamSearch1.toLowerCase().equals(away_team_name[EF_line].toLowerCase()) &&(Integer.parseInt(away_score[EF_line]) > Integer.parseInt(home_score[EF_line])))) {
						System.out.printf("%-22s%-18s%-22s%-20s%n", home_team_name[EF_line], home_score[EF_line], away_team_name[EF_line], away_score[EF_line]);
						countSearch1 ++;
					}
				}
				System.out.println(countSearch1 == 0 ? "\n The team " + teamSearch1 +" has not won any matches." : "\n" + countSearch1 + " results were found.");
				break;
			
			case 3:
				//Display all matches lost by a team
				int countSearch2 = 0;
				System.out.println("Enter a team's name: ");
				String teamSearch2 = scan5.nextLine();
				System.out.printf("%n%n%-20s%-20s%-20s%-20s%n", "Home team", "Score", "Away team", "Score");
				System.out.printf("%-20s%-20s%-20s%-20s%n", "================", "======", "===============", "=======");
				
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if((teamSearch2.toLowerCase().equals(home_team_name[EF_line].toLowerCase()) &&(Integer.parseInt(home_score[EF_line]) < Integer.parseInt(away_score[EF_line]))) || (teamSearch2.toLowerCase().equals(away_team_name[EF_line].toLowerCase()) &&(Integer.parseInt(away_score[EF_line]) < Integer.parseInt(home_score[EF_line])))) {
						System.out.printf("%-22s%-18s%-22s%-20s%n", home_team_name[EF_line], home_score[EF_line], away_team_name[EF_line], away_score[EF_line]);
						countSearch2 ++;
					}
				}
				System.out.println(countSearch2 == 0 ? "\n The team " + teamSearch2 +" has not lost any matches." : "\n" + countSearch2 + " results were found.");
				break;
			
			case 4:
				//Display all time high Score.
				int max_away_score = 0;
				int max_home_score = 0;
				String max_home_score_team = "";
				String max_away_score_team = "";
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if(Integer.parseInt(home_score[EF_line]) > max_home_score) {
						max_home_score = Integer.parseInt(home_score[EF_line]);
						max_home_score_team = home_team_name[EF_line];
						
					}
					if(Integer.parseInt(away_score[EF_line]) > max_away_score) {
						max_away_score = Integer.parseInt(away_score[EF_line]);
						max_away_score_team = away_team_name[EF_line];
						
					}
				}
				if(max_home_score > max_away_score) {
					System.out.println("\nHighest Score of " + max_home_score_team+ ": " + max_home_score);
				}
				else {
					System.out.println("\nHighest Score of " + max_away_score_team+ ": " + max_away_score);
				}
				
				
				break;
				
			case 5:
				//Display all matches that were draw.
				int countSearch3 = 0;
				System.out.printf("%n%n%-20s%-20s%-20s%-20s%n", "Home team", "Score", "Away team", "Score");
				System.out.printf("%-20s%-20s%-20s%-20s%n", "=========", "=====", "=========", "======");
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if(Integer.parseInt(away_score[EF_line]) == Integer.parseInt(home_score[EF_line])) {
						System.out.printf("%-22s%-18s%-22s%-20s%n", home_team_name[EF_line], home_score[EF_line], away_team_name[EF_line], away_score[EF_line]);
						countSearch3++;
					}
				}
				System.out.println(countSearch3 == 0 ? "\nNo Matches are draw." : "\n" + countSearch3 + " results were found.");
				break;
				
			case 6:
				//Display total goals scored by a team 
				int countSearch5 = 0;
				int countSearch4 = 0;
				int HG_sum = 0;
				int AG_sum = 0;
				System.out.println("Enter a team's name: ");
				String teamSearch3 = scan5.nextLine();
				
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if(teamSearch3.toLowerCase().equals(home_team_name[EF_line].toLowerCase()) ) {
						HG_sum += Integer.parseInt(home_score[EF_line]);
						countSearch4 ++;
					}
					if(teamSearch3.toLowerCase().equals(away_team_name[EF_line].toLowerCase()) ) {
						AG_sum += Integer.parseInt(away_score[EF_line]);
						countSearch5 ++;
					}
				}
				teamSearch3 = teamSearch3.toUpperCase();
				if(countSearch4 == 0 && countSearch5 == 0) {
					System.out.println("\n The team " + teamSearch3 +" was not found.");
				}
				else {
					System.out.println("\n"+teamSearch3+" scored "+ HG_sum + " goals at home.");
					System.out.println(teamSearch3+" scored "+ AG_sum + " goals at away.");
					System.out.println(teamSearch3+" scored "+ (HG_sum+AG_sum) + " goals in total.");
				}
				break;
				
			case 7:
				//Display total goals conceded by a team 
				int countSearch6 = 0;int countSearch7 = 0;
				int HG_sum1 = 0; int AG_sum1 = 0;
				System.out.println("Enter a team's name: ");
				String teamSearch5 = scan5.nextLine();
				
				for(int k = 0; k < error_free_lines_count; k++) {
					int EF_line = error_free_lines[k];
					if(teamSearch5.toLowerCase().equals(home_team_name[EF_line].toLowerCase()) ) {
						HG_sum1 += Integer.parseInt(away_score[EF_line]);
						countSearch6 ++;
					}
					if(teamSearch5.toLowerCase().equals(away_team_name[EF_line].toLowerCase()) ) {
						AG_sum1 += Integer.parseInt(home_score[EF_line]);
						countSearch7 ++;
					}
				}
				teamSearch5 = teamSearch5.toUpperCase();
				if(countSearch6 == 0 && countSearch7 == 0) {
					System.out.println("\n The team " + teamSearch5 +" was not found.");
				}
				else {
					System.out.println("\n"+teamSearch5+" conceded "+ HG_sum1 + " goals at home.");
					System.out.println(teamSearch5+" conceded "+ AG_sum1 + " goals at away.");
					System.out.println(teamSearch5+" conceded "+ (HG_sum1+AG_sum1) + " goals in total.");
				}
				break;
			
			}
				
			System.out.println("Program complete");
		}
		catch(IOException e) {
			System.out.println("Name of the file you entered is not found.\nRemember! to provide filename with extension.");
		}
	}
}
