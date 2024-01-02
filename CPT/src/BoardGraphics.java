import java.awt.*;
import javax.swing.*;

/* This class controls the entire GUI of the game. This class 
 * updates the Jlabels according to the user's guesses. 
 */

public class BoardGraphics{
    JButton hardBtn, easyBtn, helpBtn;
    JFrame frm;
    JLabel secretWordLbl, numLivesLbl, lettersGuessedLbl, clueLbl, titleLbl;
    JTextField inputTxt;
    JPanel mainPanel, containerPanel, lblPanel, inputPanel, titlePanel, backgroundPanel, btnPanel, panel;
    private static final Dimension FRAME_SIZE = new Dimension(500, 500);
    private Data data;
    private Hangman hangman;
    private FileReader file;

    public BoardGraphics(Hangman hangman, Data data){
        this.hangman = hangman;
        this.data = data;
    }

    // When this method is called in the hangman class the following method is run.
    public void buildGame(){
        // Generates the title page of the game. 
        buildTitlePage();
    }

    // This method creates the title page of the game. 
    private void buildTitlePage(){
        frm = new JFrame("Title Page");
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frm.setSize(FRAME_SIZE);

        titlePanel = new JPanel();
        btnPanel = new JPanel();
        panel = new JPanel();
        titleLbl = new JLabel("HANGMAN GAME!!");
        titleLbl.setFont(new Font("Monospaced", Font.BOLD, 57));
        helpBtn = new JButton("HELP");
        hardBtn = new JButton("HARD");
        easyBtn = new JButton("EASY");
        
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        titleLbl.setLayout(new BoxLayout(titleLbl, BoxLayout.X_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));

        btnPanel.add(BorderLayout.CENTER, hardBtn);
        btnPanel.add(BorderLayout.CENTER, easyBtn);
        btnPanel.add(BorderLayout.CENTER, helpBtn);
        titlePanel.add(titleLbl);
        panel.add(titlePanel);
        panel.add(btnPanel);
        frm.setContentPane(panel);

        hardBtn.addActionListener(e -> {
            // If the user clicks on the hard mode button the hard game mode is generated.
            buildHardMode(Data.hardLives);
        });
        easyBtn.addActionListener(e -> {
            // If the user clicks on the easy mode button the easy game mode is generated.
            buildEasyMode(Data.easyLives);
        });
        helpBtn.addActionListener(e-> {
            // If the user clicks on the help mode button the rules of the game are generated.
            JOptionPane.showMessageDialog(null, "Hangman Rules\nThe Easy mode gives you 6 lives with Array related questions.\nThe Hard mode gives you 3 lives with ArrayList related questions.\nRules:\n1.) Read the clue given near the top\n2.) Input your guess (a single letter, lowercase) in the textfield\n3.) Hint enter to confirm your guess\n4.) If user guesses incorrectly, a piece of the hangman will be displayed, and lives decrease\n5.) Every letter guess will appear near the top of the screen", 
            "Rules", JOptionPane.INFORMATION_MESSAGE);
        });
        frm.setVisible(true);
    }

    // Generates the hard game mode.
    private void buildHardMode(int x){
        frm.setTitle("Hangman");
        // Creates the hard mode hangman background. 
        backgroundPanel = new HardMode(data);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        // Creates the remaining JLabels and Textfields. The number of lives and game Mode character 
        // are passed into this method. 
        buildComponentPanel(x, 'H');
        clueLbl.setText(data.getHardQuestion());
        secretWordLbl.setText(hideWord(data.getHardWord()));
        frm.setContentPane(backgroundPanel);
        frm.setVisible(true);
    }

    // Generates the easy game mode.
    private void buildEasyMode(int x){
        frm.setTitle("Hangman");
        // Creates the easy mode hangman background. 
        backgroundPanel = new EasyMode(data);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        // Creates the remaining JLabels and Textfields. The number of lives and game Mode character 
        // are passed into this method.
        buildComponentPanel(x, 'E');
        clueLbl.setText(data.getEasyQuestion());
        secretWordLbl.setText(hideWord(data.getEasyWord()));
        frm.setContentPane(backgroundPanel);
        frm.setVisible(true);
    }

    // Method creates the buttons, JLabels, and Textfields of the game
    private void buildComponentPanel(int lives, char gameMode){
        clueLbl = new JLabel();
        secretWordLbl = new JLabel();
        lblPanel = new JPanel();
        containerPanel = new JPanel();
        inputPanel = new JPanel(new BorderLayout());
        inputTxt = new JTextField(7);
        lettersGuessedLbl = new JLabel("Guessed Letters: ");
        numLivesLbl = new JLabel("Lives Remaining: " + lives);

        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        containerPanel.setLayout(new BorderLayout());
        lblPanel.setLayout(new BoxLayout(lblPanel, BoxLayout.Y_AXIS));
        
        lblPanel.add(lettersGuessedLbl);
        lblPanel.add(clueLbl);
        lblPanel.add(numLivesLbl);
        lblPanel.add(secretWordLbl);
        inputPanel.add(BorderLayout.WEST, inputTxt);
        containerPanel.add(BorderLayout.NORTH, lblPanel);
        containerPanel.add(BorderLayout.SOUTH, inputPanel);
        backgroundPanel.add(BorderLayout.NORTH, containerPanel);
        
        // If the user makes a guess through the textfield the following method is run.
        inputTxt.addActionListener( e ->{
            // When user makes a guess the checkInput method is run in the hangman class.
            // The guess made in the textfield and a char (which represents the game mode) 
            // are passed in as parameters for this method. 
            hangman.checkInput(inputTxt.getText(), gameMode);
        });
    }

    // This method hides the secret word using the "_" character. 
    public String hideWord(String txt){
        StringBuffer hide = new StringBuffer();
        // For loop runs for the length of the secret word.
        for (int i = 0; i < txt.length(); i++){
            // Generates the "_" characters according to the length of the word.
            hide.append("_ ");
        }
        // returns the new hidden secret word.
        return hide.toString();
    }

    // Prints out the lives remaining. 
    public void printEasyLives(){
        numLivesLbl.setText("Lives Remaining " + String.valueOf(data.getEasyLives()));
    }

    // Prints out the lives remaining.
    public void printHardLives(){
        numLivesLbl.setText("Lives Remaining " + String.valueOf(data.getHardLives()));
    }

    // Redraws the hangman background
    public void updateBoard(){
        backgroundPanel.repaint();
    }

    // This method prints out all the guesses made by the user. 
    public void printIncorrectGuess(){
        StringBuffer letters = new StringBuffer();
        // For loop runs for the length of all the guesses made by the user. 
        for (int i = 0; i < data.getIncorrectGuess().size(); i++){
            // Converts each individual letter into a string and adds it to the StringBuffer
            String c = data.getIncorrectGuess().get(i) + " ";
            letters.append(c);
        }
        // Sets the label with the most updated guesses made by the user
        lettersGuessedLbl.setText("Guessed Letters: " + letters.toString());
    }

    // This method updates the hidden secret word to the most updated version
    // depending on, if the user guesses a letter apart of the secret word. 
    // This method essentially shows which letters the user has guessed, and 
    // which letters the user has yet to guess. 
    public void printEasyHiddenWord(){
        StringBuffer easyHidden = new StringBuffer();
        // For loop runs for the length of the secret word
        for (int i = 0; i < data.getLetterGuessedE().length; i++){
            // If statement checks which letters have been guessed from the secret word depending on 
            // the index of the letters corresponding to their boolean value which have been set to true. 
            if (data.getLetterGuessedE()[i]){
                // Gets the letter the user has guessed correctly and adds it to the StringBuffer
                String c = data.getEasyWord().charAt(i) + " ";
                easyHidden.append(c);
            }
            // If the boolean value is false for that index for that letter than the following code is run.
            else{
                // The "_" character is added to the StringBuffer, since this letter is yet to be guessed
                easyHidden.append("_ ");
            }
        }
        // This method updates the hidden word based on the letters the user has guessed correctly.
        setSecretWordLbl(easyHidden.toString());
    }

    // This method has the same logic as the printEasyHiddenWord method. 
    public void printHardHiddenWord(){
        StringBuffer hardHidden = new StringBuffer();
        for (int i = 0; i < data.getLetterGuessedH().length; i++){
            if (data.getLetterGuessedH()[i]){
                String c = data.getHardWord().charAt(i) + " ";
                hardHidden.append(c);
            }
            else{
                hardHidden.append("_ ");
            }
        }
        setSecretWordLbl(hardHidden.toString());
    }


    // Setters
    public void setText(String txt){
        inputTxt.setText(txt);
    }
    public void setSecretWordLbl(String txt){
        secretWordLbl.setText(txt);
    }
}