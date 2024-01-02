import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/* This class Generates the Hangman back ground for Easy mode.
 * Ths class also controls the paint method, and updates the 
 * hangman background depending on the number of lives remaining. 
 */

public class EasyMode extends JPanel{

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

    public EasyMode(Data data){
        this.data = data;
    }

    // These methods create the image of the man
    private void Head(int xPos, int yPos, Graphics g){
        g.drawOval(xPos - 120, yPos, 2 * HEAD, 2 * HEAD);
    }

    private void Body(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos - 120, yPos + BODY_LEN);
    }

    private void LeftArm(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos - ARM_LEN - 120, yPos);
    }

    private void LeftLeg(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos - LEG_LEN - 120, yPos + LEG_LEN);
    }

    private void RightArm(int xPos, int yPos, Graphics g){
        g.drawLine(xPos - 120, yPos, xPos + ARM_LEN - 120, yPos);
    }

    private void RightLeg(int xPos, int yPos, Graphics g){
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
        if (data.getEasyLives() == 6){
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 5){
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 4){
            Body(BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD), g);
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 3){
            RightLeg(BODY_X_POS, LEG_Y_POS, g);
            Body(BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD), g);
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 2){
            LeftLeg(BODY_X_POS, LEG_Y_POS, g);
            RightLeg(BODY_X_POS, LEG_Y_POS, g);
            Body(BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD), g);
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 1){
            RightArm(BODY_X_POS, ARM_Y_POS, g);
            LeftLeg(BODY_X_POS, LEG_Y_POS, g);
            RightLeg(BODY_X_POS, LEG_Y_POS, g);
            Body(BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD), g);
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
        else if (data.getEasyLives() == 0){
            LeftArm(BODY_X_POS, ARM_Y_POS, g);
            RightArm(BODY_X_POS, ARM_Y_POS, g);
            LeftLeg(BODY_X_POS, LEG_Y_POS, g);
            RightLeg(BODY_X_POS, LEG_Y_POS, g);
            Body(BODY_X_POS, START_Y + ROPE_LEN + (2 * HEAD), g);
            Head(BODY_X_POS - HEAD, START_Y + ROPE_LEN, g);
            background(START_X, START_Y, g);
        }
    }
}