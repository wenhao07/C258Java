import java.util.Scanner;

public class HealthyHearts {
    public static void main(String[] args){
        int age;
        int maximum;
        int lowTarget;
        int highTarget;
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your age? ");
        age = sc.nextInt();
        maximum = 220-age;
        lowTarget = maximum/2;
        highTarget = (int) Math.round(maximum*85.0/100);

        System.out.println("Your maximum heart rate should be " + maximum + " beats per minute");
        System.out.println("Your target HR Zone is " + lowTarget + " - " + highTarget + " bests per minute");

    }
}
