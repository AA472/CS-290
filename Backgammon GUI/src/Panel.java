import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel extends JPanel {
    private JPanel root;
    private static final int WIDTH = 820;
    private static final int HEIGHT = 600;
    private Display display;
    //int pickSlot = -10;
    //int dropSlot = -10;

    public static void main(String[] args) {
        JFrame frame = new JFrame("BackGammon");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //frame.setContentPane(new Panel().root);
        frame.add(new Display());
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
   /* public Panel(){


        display.game.register(this);
        display.game.getBoard(display.b);

        display.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("GOT HERE");
                getInput(e);
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}


        });
    }
    public void getInput(MouseEvent e){
        System.out.println("GOT HERE");
        if(pickSlot!= -10){
            dropSlot = getSlotIndex(e.getX(),e.getY());
            if(display.game.play(pickSlot,dropSlot)){
                pickSlot=-10;
                dropSlot =-10;
                display.game.setTurn((display.game.getTurn()%2)+1);
            }
        }
        else
            pickSlot = getSlotIndex(e.getX(),e.getY());

    }
    public int getSlotIndex(int x, int y){
        int index = -10;
        if(y<= 175){
            index = x/50;
            if(index <6)
                index +=12;
            else if(index==6)
                index = -1;
            else
                index +=11;
        }
        else if(y>= 380){
            index = x/50;
            if(index<6)
                index = 11 -index;
            else if(index == 6)
                index = -1;
            else
                index = 12 - index;
        }
        return index;
    }

    }*/
