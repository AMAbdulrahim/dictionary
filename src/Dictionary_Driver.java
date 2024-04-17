import java.io.File;
import java.util.Scanner;

public class Dictionary_Driver {
    public static void main(String[] args) {
        Dictionary dictionary = null;
        Scanner sc = new Scanner(System.in);

        while (dictionary == null) {
            try {
                System.out.print("Enter filename> ");
                dictionary = new Dictionary(new File(sc.next()));
                // dictionary = new Dictionary(new File("mydictionary.txt"));
                System.out.println("Dictionary loaded successfully.");
            } catch (Exception e) {
                System.out.println("Error loading dictionary: " + e.getMessage());
            }
        }

        while (true) {
            System.out.print("\nchoose operation no. ( 1.add, 2.remove, 3.find, 4.similar, 5.save )> ");
            
            int ops = sc.nextInt();

            if (ops==1) {
                System.out.print("add new word> ");
                String word = sc.next().toLowerCase();

                try {
                    dictionary.addWord(word);
                    System.out.println("Word added successfully.");
                } catch (WordAlreadyExistsException e) {
                    System.out.println("Exception: Word already found.");
                }

            } 
            else if (ops==2) {
                System.out.print("remove word> ");
                String word = sc.next().toLowerCase();

                try {
                    dictionary.deleteWord(word);
                    System.out.println("Word removed successfully.");
                } catch (WordNotFoundException e) {
                    System.out.println("Exception: Word not found.");
                }

            } 
            else if (ops==3) {
                System.out.print("check word> ");
                String word = sc.next().toLowerCase();

                if (dictionary.findWord(word)) 
                    System.out.println("Word found.");
                else 
                    System.out.println("Word not found.");
                

            } 
            else if (ops==4) {
                System.out.print("search for similar/s to> ");
                String word = sc.next().toLowerCase();
                String[] simWrds = dictionary.findSimilar(word);

                if (simWrds.length > 0) 
                    System.out.println(String.join(", ", simWrds) + ".");
                else 
                    System.out.println("No similar words found.");

            } 
            else if (ops==5) {
                System.out.print("Save Updated Dictionary (Y/N)> ");
                String ans = sc.next().toLowerCase();

                if (ans.equals("y")) {
                    System.out.print("Enter filename> ");

                    dictionary.saveToFile(new File(sc.next()));
                    System.out.println("Dictionary saved successfully.");
                    System.out.println("Exiting program.");
                    break;
                }
                else
                    if (ans.equals("n")){
                        System.out.println("Dictionary not saved.");
                        System.out.println("Exiting program.");
                        break;
                    }
                    else 
                        System.out.println("Invalid save option.");  
            } 
            else 
                System.out.println("Invalid operation.");  
        }

        sc.close();
    }
}