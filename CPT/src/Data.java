import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/* This class is a FileReader, and essentially controls, 
 * reading the files and storing the words/clues into
 * Arrays. This class also controls most of the setters/getters 
 * of the program. 
 */
public class Data extends FileReader{
    // These 2 variables are static beacause we need to keep track 
    // of lives remaining, depending on user's guesses
    public static int easyLives = 6;
    public static int hardLives = 3;
    private FileReader fileReader;
    private BoardGraphics gui;
    private String easyWord, hardWord, easyQues, hardQues;
    private String[] easyList, hardList;
    private String[] easyQList, hardQList;
    private boolean[] lettersGuessedE, lettersGuessedH;
    private ArrayList<Character> incorrectGuess = new ArrayList<Character>();
    private ArrayList<Character> alphabet = new ArrayList<Character>();
    int temp;

    public Data(FileReader fileReader){
        this.fileReader = fileReader;
    }

    // This method overrides the method in the parent class and reads two files and 
    // stores the words and clues in their corresponding arrays.
    public void readFiles(String easy, String hard) {
        try(BufferedReader input = new BufferedReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(easy)))){
            easyList = input.readLine().split(":");
            easyQList = input.readLine().split(";");
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
        try(BufferedReader input = new BufferedReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(hard)))){
            hardList = input.readLine().split(":");
            hardQList = input.readLine().split(";");
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // This method generates the random secret word for both easy and hard modes.
    // It also generates the random clue corresponding to the secret word for both
    // game modes. 
    public void buildWord(){
        int w = (int)(Math.random() * easyList.length);
        temp = w;
        setEasyWord(rndEasyWord());
        setEasyQuestion(rndEasyQuestion());
        setHardWord(rndHardWord());
        setHardQuestion(rndHardQuestion());
        lettersGuessedE = new boolean[getEasyWord().length()];
        lettersGuessedH = new boolean[getHardWord().length()];
    }


    // This method adds the guesses made by the user to the ArrayList. 
    public void addNewLetter(char letterGuessed){
        incorrectGuess.add(letterGuessed);
    }

    // This method generates the alphabet and adds the characters to the Arraylist. 
    public void buildAlphabet(){
        for (char c = 'a';  c <= 'z'; c++){
            alphabet.add(c);
        }
    }

    // These methods return the easy/hard words/clues. 
    public String rndEasyWord(){
        return easyList[temp];
    }
    public String rndEasyQuestion(){
        return easyQList[temp];
    }
    public String rndHardWord(){
        return hardList[temp];
    }
    public String rndHardQuestion(){
        return hardQList[temp];
    }


    // Setters
    public void setEasyWord(String word){
        easyWord = word;
    }
    public void setEasyQuestion(String str){
        easyQues = str;
    }
    public void setHardWord(String word){
        hardWord = word;
    }
    public void setHardQuestion(String str){
        hardQues = str;
    }
    public void setShownLettersE(int i){
        lettersGuessedE[i] = true;
    }
    public void setShownLettersH(int i){
        lettersGuessedH[i] = true;
    }
    public void setLivesRemainingE(int lives){
        easyLives = lives;
    }
    public void setLivesRemainingH(int lives){
        hardLives = lives;
    }


    // Getters
    public String getEasyWord(){
        return easyWord;
    }
    public String getEasyQuestion(){
        return easyQues;
    }
    public String getHardWord(){
        return hardWord;
    }
    public String getHardQuestion(){
        return hardQues;
    }
    public int getEasyLives(){
        return easyLives;
    }
    public int getHardLives(){
        return hardLives;
    }
    public ArrayList<Character> getIncorrectGuess(){
        return incorrectGuess;
    }
    public ArrayList<Character> getAlphabet(){
        return alphabet;
    }
    public boolean[] getLetterGuessedE(){
        return lettersGuessedE;
    }
    public boolean[] getLetterGuessedH(){
        return lettersGuessedH;
    }
}