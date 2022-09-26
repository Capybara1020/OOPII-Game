import javax.swing.*;
import java.awt.*;

public class A1083305_checkpoint7_Barrack extends JLabel implements Runnable {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : the normal image size.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    private boolean understructure;
    private int jfScaler;
    private int maximum;
    // Description : The number of this type of building.
    private String produced_num;
    // Description : The image of under constructing.
    private ImageIcon constructingIcon;

    @Override
    public void run()
    {
        for(int i = 0; i <= 80; i=i+20)
            {
                this.setText(Integer.toString(i) + "%"); // counter
                doNothing(500);
            }
            this.setText(produced_num);
            this.setIcon(icon);   
    }

    // Description : Stop the thread in milliseconds.
    private static void doNothing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Unexpected interruption");
            System.exit(0);
        }
    }

    public A1083305_checkpoint7_Barrack(int locationX, int locationY, String text, int scaler, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.locationX = locationX;
        this.locationY = locationY;
        this.understructure = true;
        this.jfScaler = scaler;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/barrack.png");
        this.maximum = 5;
        this.produced_num = text;
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);

        // read constructing.png
        this.constructingIcon = new ImageIcon("Resource/constructing.png");
        Image img2 = constructingIcon.getImage().getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        constructingIcon.setImage(img2);
        this.setIcon(constructingIcon);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }

    public void produce_people(int maximum, int produced_num) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getlocationX() {
        return this.locationX;
    }

    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getlocationY() {
        return this.locationY;
    }

    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }

}
