import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// Description : This class will implement Runnable and checkpoint7_Soldier_Movement.(may need to rename)
public class A1083305_checkpoint7_Soldier extends JLabel implements Runnable, A1083305_checkpoint7_Soldier_Movement {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : the normal image size.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    // Description : The image of progress to grow up.
    private ImageIcon[] growingIcons = new ImageIcon[6];
    // Description : The root frame.
    private A1083305_checkpoint7_GameFrame parentFrame;
    // Description : To check if this soldier is selected.
    public boolean isSelected;
    // Description : To check if this soldier is a grown up.
    private boolean isGrown;
    // Description : To store the route to get to the destination.
    private A1083305_checkpoint7_RouteLinkedList route = new A1083305_checkpoint7_RouteLinkedList();
    private ArrayList<A1083305_checkpoint7_Block> routeList = new ArrayList<A1083305_checkpoint7_Block>();
    // Description : The grid location of destination.
    private int destinationX, destinationY;
    //Description : UCS : 2, BFS : 1, DFS : 0
    private int algorithm;

    public A1083305_checkpoint7_Soldier(int locationX, int locationY, int scaler, int horizontalAlignment, int algorithm) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/soldier.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);
        this.isSelected = false;
        this.isGrown = false;
        this.algorithm = algorithm; 
        setParentFrame();

        for(int i = 0; i < 6; i++)
        {
            String number = String.valueOf(i);
            this.growingIcons[i] = new ImageIcon("Resource/baby"+number+".png");
            Image img2 = this.growingIcons[i].getImage().getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
            this.growingIcons[i].setImage(img2);
        }
        this.setIcon(growingIcons[0]);

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setParentFrame();
                if(isGrown == false)
                    return;
                else if(!isSelected)
                {
                    for(A1083305_checkpoint7_Soldier soldier : parentFrame.gamePanel.getSoldierList())
                    {
                        soldier.isSelected = false;
                        soldier.repaint();
                    }
                    isSelected = true;
                    repaint();
                    setSelectedSoldier();
                }
                else
                {
                    isSelected = false;
                    resetSelectedSoldier();
                    repaint();
                }
            }
        });

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }

    @Override
    public void run()
    {
        for(int i = 1; i < 6; i++)
        {
            doNothing(500);
            this.setIcon(growingIcons[i]);
        }
        doNothing(500);
        this.setIcon(icon);
        isGrown = true;

        while(true)
        {
            synchronized(this)
            {
                try
                {
                    this.wait();
                    detectRoute();
                    startMove();
                }
                catch(InterruptedException e2){}
            }
        }
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
    
    @Override
    public void detectRoute()
    {
        A1083305_checkpoint7_Block target = parentFrame.getMap()[destinationX][destinationY];
        A1083305_checkpoint7_Block root = parentFrame.getMap()[locationX][locationY];
        A1083305_checkpoint7_RouteFinder routeFinder = new A1083305_checkpoint7_RouteFinder(parentFrame,target,root,algorithm,parentFrame.getMap());
        routeList = routeFinder.createRoute();
    }

    @Override
    // @Override
    public void startMove()
    {
        isSelected = false;
        repaint();
        for(A1083305_checkpoint7_Block onestep : routeList)
        {
            if(parentFrame.locationVarify(onestep.getX(), onestep.getY(), false) == false)
            {
                doNothing(500);
                locationX = onestep.getX();
                locationY = onestep.getY();
                parentFrame.gamePanel.repaint();
            }
            else
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected && isGrown) //Draw a rectangle border around the selected soldier.
            g.drawRect(0, 0, getImage().getWidth(null) - 1, getImage().getHeight(null) - 1);
    }

    // Description : Set the root frame.
    public void setParentFrame() {
        this.parentFrame = (A1083305_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }

    // Description : Set the destination in grid location format.
    public void setDestination(int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    // Description : Set selected soldier.
    public void setSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = this;
    }

    // Description : Reset selected soldier.
    public void resetSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = null;
    }
    // Description : Return current position X.
    public int getlocationX() {
        return this.locationX;
    }
    // Description : Set current position X.
    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }
    // Description : Return current position Y.
    public int getlocationY() {
        return this.locationY;
    }
    // Description : Set current position Y.
    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }
    // Description : Return self.
    public A1083305_checkpoint7_Soldier getSelf(){
        return this;
    }

}
