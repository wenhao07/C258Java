import java.util.Random;
import java.util.Scanner;

/* Flow
1. Declare variables to store player choice, computer choice, number of rounds, replay choice
2. Start loop
    1. Initialise tracker for ties/wins/losses
    2. Prompt user to input 1 <= number <= 10 of rounds to play
        1. if number outside specified range, print error message and quit
    3. Start loop for number of rounds chosen
        1. Prompt user for choice of 1/2/3 -- > Rock/Paper/Scissors
        2. Random generate computer's choice
        3. Compare choices and display results
        4. Increment the correct tracker for ties/wins/losses
    4. After rounds are played, declare winner based on side with more wins
    5. Prompt user if they want to play again
        1. If Yes, back to Step 2
        2. If No, print message and exit
 */
public class RockPaperScissors {
    public static int checkResults(int a, int b){
        int result = 0; //1 for tie, 2 for player win, 3 for player loss
        if(a == b){
            result = 1;
            return result;
        }
        if(a==1){
            if (b==2){
                result = 3;
            }else{
                result = 2;
            }
        }else if (a==2){
            if(b==1){
                result = 2;
            }else{
                result = 3;
            }
        }else{
            if(b==1){
                result = 3;
            }else{
                result = 2;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int player;
        int computer;
        int rounds;

        int results;
        String replay;

        Scanner sc = new Scanner(System.in);
        Random rng = new Random();
        do{
            int ties = 0;
            int wins = 0;
            int losses = 0;
            // Prompt for number of rounds
            System.out.println("Please enter number of round you wish to play (between 1 and 10 inclusive): ");
            rounds = sc.nextInt();
            if(rounds<1 || rounds>10){
                System.out.println("Error: Number of rounds out of specified range, exiting.");
                System.exit(0);
            }

            // Loop for specified number of rounds
            for(int i=0; i<rounds; i++){
                System.out.println("Please pick your move, 1 = Rock, 2 = Paper, 3 = Scissors :");
                player = sc.nextInt();
                computer = rng.nextInt(3)+1; //rng.nextInt(3) returns 0,1,2, add 1 to match player's moves
                results = checkResults(player, computer);
                if(results==1){
                    ties += 1;
                    System.out.println("This round is a tie!");
                }else if(results==2){
                    wins += 1;
                    System.out.println("Player has won this round!");
                }else{
                    losses += 1;
                    System.out.println("Computer has won this round!");
                }
            }
            System.out.println("--- Final Results ---");
            System.out.println("Ties: " + ties);
            System.out.println("Player wins: " + wins);
            System.out.println("Computer wins: " + losses);
            if(wins<losses){
                System.out.println("Computer is the overall winner!");
            }else if(wins>losses){
                System.out.println("Player is the overall winner!");
            }else{
                System.out.println("Overall result is a tie!");
            }

            // Check if player wants to play again
            System.out.println("Do you want to play again? Yes to restart, No to end.");
            replay = sc.next();
            } while(replay.equals("Yes"));
    }
}
