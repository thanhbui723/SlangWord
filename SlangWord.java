import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;

import java.lang.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    public static void showHistory() throws FileNotFoundException {
        System.out.print("\t\t\t\t\t\t" + "Slang");
        for (int i = 4; i < 58; i++)
            System.out.print(" ");
        System.out.println("Time");
        File file = new File("history.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String buffer = sc.nextLine();
            System.out.println("\t\t\t\t\t\t" + buffer);
        }
    }

    public static void writeToHistory(String word) throws IOException {
        FileWriter fw = new FileWriter("history.txt", true);
        fw.write(word);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        for (int i = word.length(); i < 50; i++)
            fw.write(".");
        fw.write(dtf.format(now) + "\n");
        fw.close();
    }

    static void clearHistory() throws IOException {
        FileWriter fw = new FileWriter("history.txt");
        fw.close();
        System.out.println("Clear Complete!");
    }

    static void addSlang(HashMap<String, String> slang, HashMap<String, ArrayList<String>> keyWordMap) {
        SlangWord w = new SlangWord();
        System.out.print("Enter Slang: ");
        Scanner sc = new Scanner(System.in);
        w.word = sc.nextLine();
        String buffer = new String();
        if (slang.get(w.word) == null) {
            System.out.print("Enter meaning: ");
            buffer = sc.nextLine() + "| ";
            while (true) {
                System.out.println("Do you want to enter another meaning?");
                System.out.println("1. Yes\t\t 2.No");
                int c = Integer.parseInt(sc.nextLine());
                if (c == 1) {
                    System.out.print("Enter meaning: ");
                    buffer += sc.nextLine() + "| ";
                } else if (c == 2) {
                    buffer = buffer.substring(0, buffer.length() - 2);
                    slang.put(w.word, buffer);
                    break;
                }
            }
        } else {
            System.out.println("This slang has existed!");
            System.out.println("What do you want to handle: ");
            System.out.println("1. Duplicate\t 2. Overwrite");
            String s = sc.nextLine();
            int choice = Integer.parseInt(s);

            if (choice == 1) {
                System.out.print("Enter meaning: ");
                w.meaning = sc.nextLine();
                buffer = slang.get(w.word) + "| " + w.meaning;
                slang.put(w.word, buffer);
            } else {
                System.out.print("Enter meaning: ");
                buffer = sc.nextLine() + "| ";
                while (true) {
                    System.out.println("Do you want to enter another meaning?");
                    System.out.println("1. Yes\t\t 2.No");
                    int c = Integer.parseInt(sc.nextLine());
                    if (c == 1) {
                        System.out.print("Enter meaning: ");
                        buffer += sc.nextLine() + "| ";
                    } else if (c == 2) {
                        buffer = buffer.substring(0, buffer.length() - 2);
                        slang.put(w.word, buffer);
                        break;
                    }
                }
            }

        }
        String[] wordsOfMeaning = buffer.split("\\s|\\,|\\?|\\.|\\|");
        for (int i = 0; i < wordsOfMeaning.length; i++) {
            if (keyWordMap.get(wordsOfMeaning[i]) == null)
                keyWordMap.put(wordsOfMeaning[i], new ArrayList<>());
            keyWordMap.get(wordsOfMeaning[i]).add(w.word);
            keyWordMap.put(wordsOfMeaning[i], keyWordMap.get(wordsOfMeaning[i]));
        }
        System.out.println("Successfully!");
    }

    static void edit(HashMap<String, String> slang, HashMap<String, ArrayList<String>> keyWordMap) {
        SlangWord w = new SlangWord();
        System.out.print("Enter Slang: ");
        Scanner sc = new Scanner(System.in);
        w.word = sc.nextLine();
        String buffer = new String();
        if (slang.get(w.word) == null) {
            System.out.println("No slang founded!");
            sc.nextLine();
            return;
        } else {
            System.out.print("Enter meaning: ");
            buffer = sc.nextLine() + "| ";
            while (true) {
                System.out.println("Do you want to enter another meaning?");
                System.out.println("1. Yes\t\t 2.No");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    System.out.print("Enter meaning: ");
                    buffer += sc.nextLine() + "| ";
                } else if (choice == 2) {
                    buffer = buffer.substring(0, buffer.length() - 2);
                    slang.put(w.word, buffer);
                    break;
                }
            }

        }
        String[] wordsOfMeaning = buffer.split("\\s|\\,|\\?|\\.|\\|");
        for (int i = 0; i < wordsOfMeaning.length; i++) {
            if (keyWordMap.get(wordsOfMeaning[i]) == null)
                keyWordMap.put(wordsOfMeaning[i], new ArrayList<>());
            keyWordMap.get(wordsOfMeaning[i]).add(w.word);
            keyWordMap.put(wordsOfMeaning[i], keyWordMap.get(wordsOfMeaning[i]));
        }
    }
    public static void deleteSlang(HashMap<String, String> slang, HashMap<String, ArrayList<String>> keyWordMap) {
        System.out.print("Enter slang you want to delete: ");
        Scanner sc = new Scanner(System.in);
        String del = sc.nextLine();
        if (slang.get(del) == null) {
            System.out.println("Your slang you entered doesn't existed!");
            sc.nextLine();
            return;
        } else {
            System.out.println("Are you sure?");
            System.out.println("1.Yes\t\t2.No");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                String[] wordsOfMeaning = slang.get(del).split("\\s|\\,|\\?|\\.|\\|");
                for (String s : wordsOfMeaning)
                    keyWordMap.get(s).remove(del);
                slang.remove(del);
            } else
                return;
        }
    }
    //reset slang word
    static void reset(HashMap<String, String> slang, HashMap<String, ArrayList<String>> keyWordMap) 
        throws FileNotFoundException {
        readDataFromFile(slang, keyWordMap, "slang.txt");
    }

    //random slang
    public static SlangWord randomSlangWord(HashMap<String, String> slang) {
        SlangWord w = new SlangWord();
        int count = 0;
        Random rng = new Random();
        int limit = rng.nextInt(slang.size());

        for (Map.Entry<String, String> entry : slang.entrySet()) {
            if (count > limit)
                break;
            w.word = entry.getKey();
            w.meaning = entry.getValue();
            count++;
        }
        return w;
    }
    //check dap an
    static boolean checkDuplicate(ArrayList<String> lis, String needCheck){
        for(int i = 0; i < lis.size(); i++)
            if(needCheck.equals(lis.get(i)))
                return true;
        return false;
    }
    static boolean quiz1(HashMap<String, String> slang) {
        SlangWord w = randomSlangWord(slang);
        ArrayList<String> listAnswer = new ArrayList<>();
        listAnswer.add(w.meaning);
        for (int i = 0; i < 3; i++) {
            SlangWord temp = randomSlangWord(slang);
            while(checkDuplicate(listAnswer, temp.meaning))
                temp = randomSlangWord(slang);
            listAnswer.add(temp.meaning);
        }
        Collections.shuffle(listAnswer);
        System.out.println("What does " + w.word + " mean?");
        for (int i = 1; i <= 4; i++)
            System.out.println("\t\t\t\t" + i + ". " + listAnswer.get(i - 1));
        System.out.print("Your choice: ");
        Scanner sc = new Scanner(System.in);
        int answer = Integer.parseInt(sc.nextLine());
        boolean isCorrect = false;
        if (listAnswer.get(answer - 1).equals(w.meaning)) {
            System.out.println("Correct!");
            isCorrect = true;
        } else
            System.out.println("Uncorrect! The anwer is: " + w.meaning);
        return isCorrect;
    }
    static void quiz2(HashMap<String, String> slang) {
        SlangWord w = randomSlangWord(slang);
        ArrayList<String> listAnswer = new ArrayList<>();
        listAnswer.add(w.word);

        for (int i = 1; i <= 3; i++) {
            SlangWord temp = randomSlangWord(slang);
            while(checkDuplicate(listAnswer, temp.word))
                temp = randomSlangWord(slang);
            listAnswer.add(temp.word);
        }

        Collections.shuffle(listAnswer);
        System.out.println("What does the slang for \"" + w.meaning + "\" ?");
        for (int i = 1; i <= 4; i++)
            System.out.println("\t\t\t\t" + i + ". " + listAnswer.get(i - 1));
        System.out.print("Your choice: ");
        Scanner sc = new Scanner(System.in);
        int answer = Integer.parseInt(sc.nextLine());
        if (listAnswer.get(answer - 1).equals(w.word))
            System.out.println("Correct!");
        else
            System.out.println("Uncorrect! The anwer is: " + w.word);
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
                System.out.println("\t\t\t\t\t\t\t 3. Show history");
                System.out.println("\t\t\t\t\t\t\t 4. Clear history");
                System.out.println("\t\t\t\t\t\t\t 5. Add a slang word");
                System.out.println("\t\t\t\t\t\t\t 6. Edit slang word");
                System.out.println("\t\t\t\t\t\t\t 7. Delete slang word");
                System.out.println("\t\t\t\t\t\t\t 8. Reset to list slang word at begin");
                System.out.println("\t\t\t\t\t\t\t 9. Random a slang word");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    System.out.print("Enter slang word you want to know meaning: ");
                    String w = sc.nextLine();
                    writeToHistory(w);
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
                
                }else if (choice == 3) {
                    System.out.print("history");
                    System.out.flush();
                    //
                    showHistory();
                    sc.nextLine();
                }
                else if (choice == 4) {
                    clearHistory();
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                }
                else if (choice == 5) {
                    addSlang(slang, keyWordMap);
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                } else if (choice == 6) {
                    edit(slang, keyWordMap);
                    System.out.println("Successfully!");
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                }
                else if (choice == 7) {
                    deleteSlang(slang, keyWordMap);
                    System.out.println("Remove Successfully!");
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                }
                else if (choice == 8) {
                    slang = new HashMap<>();
                    keyWordMap = new HashMap<>();
                    reset(slang, keyWordMap);
                    System.out.println("Reset Successfully!");
                    System.out.println("Press ENTER to continue!");
                    sc.nextLine();
                }
                else if (choice == 9) {
                    SlangWord w = randomSlangWord(slang);
                    w.show();
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