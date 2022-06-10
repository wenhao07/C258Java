import java.util.Random;
import java.util.Scanner;

public class DogGenetics {
    public static void main(String[] args){
        String name;
        String[] breeds = {"St. Bernard", "Chihuahua", "Dramatic RedNosed Asian Pug", "Common Cur", "King Doberman"};
        int[] percentages = new int[breeds.length];

        Scanner sc = new Scanner(System.in);
        System.out.println("What is your dog's name?");
        name = sc.nextLine();
        System.out.println("Well then, I have this highly reliable report on " + name+ "'s prestigious background right here.\n\n" + name + " is:\n");

        Random rng = new Random();
        int percentage;
        int sum = 0;
        for(int i=0; i<percentages.length-1; i++){
            percentage = rng.nextInt(100 - sum) + 1;
            sum += percentage;
            percentages[i] = percentage;
        }
        percentages[percentages.length - 1] = 100 - sum;
        for(int i=0; i< breeds.length; i++){
            System.out.println(percentages[i] + "% " + breeds[i]);
        }
        System.out.println("\nWow, that's QUITE the dog!");
    }
}
