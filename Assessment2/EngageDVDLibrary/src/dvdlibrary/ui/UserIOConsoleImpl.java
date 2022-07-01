package dvdlibrary.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{
    final private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while(invalidInput){
            try {
                String input = readString(prompt);
                num = Integer.parseInt(input);
                invalidInput = false;
            }catch(NumberFormatException e){
                this.print("Please input a number.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int num = 0;
        do{
            num = readInt(prompt);
        }while(num<min || num > max);
        return num;
    }

    @Override
    public double readDouble(String prompt) {
        boolean invalidInput = true;
        double num = 0;
        while(invalidInput){
            try {
                String input = readString(prompt);
                num = Double.parseDouble(input);
                invalidInput = false;
            }catch(NumberFormatException e){
                this.print("Please input a number.");
            }
        }
        return num;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double num = 0;
        do {
            num = readDouble(prompt);
        } while (num < min || num > max);
        return num;
    }

    @Override
    public float readFloat(String prompt) {
        boolean invalidInput = true;
        float num = 0;
        while(invalidInput){
            try {
                String input = readString(prompt);
                num = Float.parseFloat(input);
                invalidInput = false;
            }catch(NumberFormatException e){
                this.print("Please input a number.");
            }
        }
        return num;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float num = 0;
        do {
            num = readFloat(prompt);
        } while (num < min || num > max);
        return num;
    }

    @Override
    public long readLong(String prompt) {
        boolean invalidInput = true;
        long num = 0;
        while(invalidInput){
            try {
                String input = readString(prompt);
                num = Long.parseLong(input);
                invalidInput = false;
            }catch(NumberFormatException e){
                this.print("Please input a number.");
            }
        }
        return num;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long num = 0;
        do {
            num = readLong(prompt);
        } while (num < min || num > max);
        return num;
    }
}