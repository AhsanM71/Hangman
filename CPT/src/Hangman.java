import java.util.ArrayList;
import javax.swing.JOptionPane;

/* This class controls the logic of the game. Checks if 
 * the user input is correct, and when the game is over
 */
public class Hangman {
    private BoardGraphics gui;
    private Data data;
    private FileReader fileReader;

    public Hangman(){
        data = new Data(fileReader);
        gui = new BoardGraphics(this, data);
    }
    // When the game starts the following method is run
    public void startGame(){
        // Reads the two text files
        data.readFiles("easyWords.txt", "hardWords.txt");
        // Generates the random words and Questions
        data.buildWord();
        // Generates an ArrayList which contains the alphabet
        data.buildAlphabet();
        // Generates the actual GUI of the game in the BoardGraphics class
        gui.buildGame();
    }


    /* This method runs after every input from the textfield from user.
     * This method controls the logic of the game. Parameters are game mode
     * and the user's guess (the letter).
     */
    public void checkInput(String input, char mode){
        // Helper function used to determine if the user input is valid.
        if (checkValid(input)){
            // If the game mode is easy the following methods get run.
            if (mode == 'E'){
                // This method checks if the user has guessed any of the letters in the secret word. 
                updateEasyWord(input);
                // This method prints out the new secret word with the correct guesses being revealed. 
                gui.printEasyHiddenWord();
            }
            // if the game mode is hard the following methods get run.
            else{
                // This method checks if the user has guessed any of the letters in the secret word. 
                updateHardWord(input);
                // This method prints out the new secret word with the correct guesses being revealed. 
                gui.printHardHiddenWord();
            }
            // Checks if the user has made any incorrect guesses 
            if (data.getIncorrectGuess().size() != 0){
                // Prints out all the guesses the user had made through the method in BoardGraphics class
                gui.printIncorrectGuess();
            }
            // Helper function used to check if either the easy game mode is won or lost.
            if (easyGameWon()){
                JOptionPane.showMessageDialog(null, "Congratulation you guessed the easy word " + data.getEasyWord(), 
                "GAME WON!!!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            else if (data.getEasyLives() == 0){
                JOptionPane.showMessageDialog(null, "Sorry you ran out of lives the secret word was " + data.getEasyWord(), 
                "GAME LOST !!!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            // Helper function used to check if either the hard game mode is won or lost. 
            if (hardGameWon()){
                JOptionPane.showMessageDialog(null, "Congratulation you guessed the hard word " + data.getHardWord(), 
                "GAME WON !!!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            else if (data.getHardLives() == 0){
                JOptionPane.showMessageDialog(null, "Sorry you ran out of lives the secret word was " + data.getHardWord(), 
                "GAME LOST !!!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }


    /* This method compares the user's guess with each letter
     * in the secret word to see if the user has guessed correctly.
     * Then using an array of booleans that has the same length as 
     * the secret word, we essentially set an element of the array 
     * to true depending on which letter the user guessed correctly.
     */
    public void updateEasyWord(String g){
        // ArrayList used to keep track of which letters the user has guessed correctly. 
        ArrayList<Integer> change = new ArrayList<Integer>();
        // If statement checks if the user guess letter is in the secret word.
        if(data.getEasyWord().contains(g)){
            // For loops runs for the length of the secret word.
            for (int i = 0; i < data.getEasyWord().length(); i++){
                // Checking each letter of the secret word and comparing it to user's guess
                if (data.getEasyWord().charAt(i) == g.charAt(0)){
                    // If a letter in the secret word is guessed correctly the 
                    // index of that letter is stored in the ArrayList. 
                    change.add(i);
                }
            }
            // For loop runs for the number of letters guessed correctly. 
            for (int i = 0; i < change.size(); i++){
                /* The parameter being passed into this method is the index,
                 * of the correctly guessed letter. The method in the data class 
                 * takes the index of the correctly guessed letter and uses the
                 * array of boolean and changes the boolean value (to true) for
                 * that index value.
                 */
                data.setShownLettersE(change.get(i));
            }
        }
        // if user guesses incorrectly the following methods are run.
        else{
            // These methods subtract the current lives and set the remaining lives.
            data.setLivesRemainingE(data.getEasyLives() - 1);
            // This method prints out the new remaining lives.
            gui.printEasyLives();
            // This method runs the paint method in the EasyMode class and redraws the hangman graphic
            gui.updateBoard();
        }
    }

    // This methods uses the same logic as the updateEasyWord method. 
    public void updateHardWord(String g){
        ArrayList<Integer> change = new ArrayList<Integer>();
        if(data.getHardWord().contains(g)){
            for (int i = 0; i < data.getHardWord().length(); i++){
                if (data.getHardWord().charAt(i) == g.charAt(0)){
                    change.add(i);
                }
            }
            for (int i = 0; i < change.size(); i++){
                data.setShownLettersH(change.get(i));
            }
        }
        else{
            data.setLivesRemainingH(data.getHardLives() - 1);
            gui.printHardLives();
            gui.updateBoard();
        }
    }


    // This method checks if the user's guess is valid
    public boolean checkValid(String guess){
        /* The if statement checks if the user has inputted a single character.
         * Then the if statment checks if the guess is apart of the already guessed 
         * characters made by the user. Finally the if statement checks if the guess
         * is part of the alphabet. If these conditions are met, then the guess is valid.
         */
        if (guess.length() == 1 && !data.getIncorrectGuess().contains(guess.charAt(0)) && data.getAlphabet().contains(guess.charAt(0))){
            // Sets the textfield to null.
            gui.setText(null);
            // These methods take the users guess and add it to the ArrayList of the already guesses made by the user.
            data.addNewLetter(guess.charAt(0));
            // returns true if the guess is valid
            return true;
        }
        // returns false if the guess is invalid
        return false;
    }


    // Helper function checks if the easy game mode is won
    public boolean easyGameWon(){
        // for loop runs for the length of the secret word through the array of booleans
        for (int i = 0; i < data.getLetterGuessedE().length; i++){
            // If statement checks the boolean value of each of the index for each letter
            if (!data.getLetterGuessedE()[i]){
                // If anyone of the letters have not been guessed yet the function returns as false.
                // Since a letter at a specific index has not been guessed yet, the boolean value
                // at that index is false and using the not makes the statement true, therefore returning false. 
                return false;
            }
        }
        // If all the letters at their specific index have been guessed (all boolean values are true), the method returns true.
        return true;
    }
    // This helper function has the same logic as the easy game mode function.
    public boolean hardGameWon(){
        for (int i = 0; i < data.getLetterGuessedH().length; i++){
            if (!data.getLetterGuessedH()[i]){
                return false;
            }
        }
        return true;
    }
}