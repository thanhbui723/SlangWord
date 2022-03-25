import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class SlangWord {
    private String word;
    private String meaning;
    

    public void show() {
        System.out.println(word + " - " + meaning);
    }
    // Cau truc luu
    // #1 slang(word, meaning)
    // #2 keyWordMap(WordsOfMeaning, ...listword...)
    public static void readDataFromFile(HashMap<String, String> slang, HashMap<String, ArrayList<String>> keyWordMap,
            String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String buffer = sc.nextLine();

            String[] temp = buffer.split("\\`");
            if (temp.length > 1) {
                SlangWord tmp = new SlangWord();
                tmp.word = temp[0];
                tmp.meaning = temp[1];
                slang.put(temp[0], temp[1]);

                String[] wordsOfMeaning = temp[1].split("\\s|\\,|\\?|\\.|\\|");
                for (int i = 0; i < wordsOfMeaning.length; i++) {
                    if (keyWordMap.get(wordsOfMeaning[i]) == null)
                        keyWordMap.put(wordsOfMeaning[i], new ArrayList<>());
                    // {wordOfMeaning,[...listWord...]}
                    keyWordMap.get(wordsOfMeaning[i]).add(temp[0]);
                    keyWordMap.put(wordsOfMeaning[i], keyWordMap.get(wordsOfMeaning[i]));
                }
            }
        }
        sc.close();
    }

    public static void main(String[] args) throws FileNotFoundException, java.lang.NullPointerException {
        try {

            HashMap<String, String> slang = new HashMap<>();
            HashMap<String, ArrayList<String>> keyWordMap = new HashMap<>();
              
            File f = new File("currentSlang.txt");
            if (f.exists() && !f.isDirectory()) {
                readDataFromFile(slang, keyWordMap, "currentSlang.txt");
            } else {
                readDataFromFile(slang, keyWordMap, "slang.txt");
            }

            Scanner sc = new Scanner(System.in);
            while (true) {

                System.out.print("Slang Word\n");
                System.out.flush();
                System.out.println("\t\t\t\t\t\t\t 1. Find meaning by slang word ");
                System.out.println("\t\t\t\t\t\t\t 2. Find slang word by keyword ");
                

                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    System.out.print("Enter slang word you want to know meaning: ");
                    String w = sc.nextLine();
                    if (slang.get(w) != null)
                        System.out.println("Meaning: "+ slang.get(w));
                    else
                        System.out.println("No meaning founded!");
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                } else if (choice == 2) {
                    System.out.print("Enter keyword you want to find: ");
                    String keyword = sc.nextLine();
                    if (keyWordMap.get(keyword) != null)
                        System.out.println(
                                "Slangs have this keyword: "+ keyWordMap.get(keyword));
                    else
                        System.out.println("No slang founded!");
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                
                }

                else
                    break;
            }
        } catch (Exception NullPointerException) {
            System.out.println(NullPointerException.getMessage());
        }
    }
}