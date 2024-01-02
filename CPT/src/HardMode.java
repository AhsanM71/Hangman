import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/* This class Generates the Hangman back ground for Hard mode.
 * Ths class also controls the paint method, and updates the 
 * hangman background depending on the number of lives remaining. 
 */
public class HardMode extends JPanel{
    private static final int START_X = 300;
    private static final int START_Y = 100;
    private static final int BASE_POS_Y = START_Y + 250;
    private static final int ROPE_LEN = 40;

    private static final int HEAD = 17;
    private static final int BODY_LEN = 50;
    private static final int BODY_X_POS = START_X + 120;
    private static final int ARM_LEN = 25;
    private static final int ARM_Y_POS = START_Y + ROPE_LEN + (2 * HEAD) + 20;
    private static final int LEG_LEN = 30;
    private static final int LEG_Y_POS = START_Y + ROPE_LEN + (2 * HEAD) + 50;
    private Data data;

    public HardMode(Data data){
        this.data = data;
    }

    // These methods create the image of the man
    private void headAndBody(int xPos, int yPos, Graphics g, int x,int y){
        g.drawOval(xPos - 120, yPos, 2 * HEAD, 2 * HEAD);
        g.drawLine(x - 120, y, x - 120, y + BODY_LEN);
    }
    
    private void leftAndRightArm(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos - ARM_LEN - 120, yPos);
        g.drawLine(xPos - 120, yPos, xPos + ARM_LEN - 120, yPos);
    }

    private void leftAndRightLeg(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos - LEG_LEN - 120, yPos + LEG_LEN);
        g.drawLine(xPos - 120, yPos, xPos + LEG_LEN - 120, yPos + LEG_LEN);
    }
    
    // This method creates the overall structure the man hangs from
    private void background(int xPos, int yPos, Graphics g){
        // Base of the frame
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(xPos - 10, BASE_POS_Y, xPos + 150, BASE_POS_Y);
        g2D.setStroke(new BasicStroke(1));
        // right line
        g2D.drawLine(BODY_X_POS, yPos, BODY_X_POS, BASE_POS_Y);
        // slant line
        g2D.drawLine(BODY_X_POS, yPos + 20, BODY_X_POS - 20, yPos);
        // top line
        g2D.drawLine(xPos, yPos, BODY_X_POS, yPos);
        // rope, that the man hangs from
        g2D.drawLine(xPos, yPos, xPos, yPos + ROPE_LEN);
    }

    // This method is run everytime the repaint method is called.
    public void paint(Graphics g){
        super.paint(g);
        // Depending on the number of lives remaining the corresponding 
        // body part of the man will be drawn. 
        if (data.getHardLives() == 3){
            background(START_X, START_Y, g);
        }
        else if (data.getHardLives() == 2){
            headAndBody(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g, BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD));
            background(START_X, START_Y, g);
        }
        else if (data.getHardLives() == 1){
            leftAndRightLeg(BODY_X_POS, LEG_Y_POS, g);
            headAndBody(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g, BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD));
            background(START_X, START_Y, g);
        }
        else if (data.getHardLives() == 0){
            leftAndRightArm(BODY_X_POS, ARM_Y_POS, g);
            leftAndRightLeg(BODY_X_POS, LEG_Y_POS, g);
            headAndBody(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g, BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD));
            background(START_X, START_Y, g);
        }
    }
}