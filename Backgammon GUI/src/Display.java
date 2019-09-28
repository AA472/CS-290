import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class Display extends JPanel implements Observer{
    private int width ;
    private int height;
    int pickSlot = -10;
    int dropSlot = -10;
    Game game;
    Board b;
    public Display(){
        game = new Game();
        setLayout(null);
        b = new Board();

        game.register(this);
        game.getBoard(b);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getInput(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    public void getInput(MouseEvent e ){

        if(game.isEnded()){
            System.out.println("The game ended. The winner is: " + game.winner());

        }
        if(!game.hasValidMove(game.getTurn())){
            System.out.println("Has no valid moves.");
            pickSlot=-10;
            dropSlot =-10;
            game.d.roll();
            game.setTurn((game.getTurn() % 2) + 1);
        }
        if(pickSlot!= -10){
            dropSlot = getSlotIndex(e.getX(),e.getY());
            System.out.println("drop = " + dropSlot);
            if(dropSlot==pickSlot){
                dropSlot=-10;
                pickSlot=-10;
            }
            if(game.play(pickSlot,dropSlot)){
                pickSlot=-10;
                dropSlot =-10;
                if(game.d.die1Uses==0 && game.d.die2Uses==0) {
                    game.d.roll();
                    game.setTurn((game.getTurn() % 2) + 1);
                }
            }
            game.notifyObserver();
        }
        else {
            pickSlot = getSlotIndex(e.getX(), e.getY());
            System.out.println("PICK = " + pickSlot);
        }

    }

    public int getSlotIndex(int x, int y){
        int index = -10;
        if(x>=300 && x<=350)
            index = -2;
        else if(y<= 175){
            index = x/50;
            if(index <6)
                index +=12;
            else
                index +=11;
        }
        else if(y>= 380){
            index = x/50;
            if(index<6)
                index = 11 -index;
            else
                index = 12 - index;
        }
        return index;
    }



    @Override
    public void update() {
        repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintSlots(g);

    }
    public void paintSlots(Graphics g) {
        Color bgcolor = Color.decode("#E29A46");
        Color slotColor = Color.decode("#5C2D01");
        setBackground(bgcolor);
        int barwidth = 50;
        g.setColor(slotColor);
        g.fillRect(300, 0, barwidth, 600);
        for (int i = 0; i < 6; i++) {
            int x[] = {i * 50, i * 50 + 25, i * 50 + 50};
            int y[] = {0, 175, 0};
            g.setColor(slotColor);
            g.fillPolygon(x, y, 3);
            g.setColor(b.slots.get(12 + i).color);
            for (int j = 0; j < b.slots.get(12 + i).getNumStones(); j++) {
                g.fillOval(i * 50 + 12, 20 * j, 20, 20);
            }
        }
        for (int i = 6; i < 12; i++) {
            int x[] = {i * 50 + barwidth, i * 50 + 25 + barwidth, i * 50 + 50 + barwidth};
            int y[] = {0, 175, 0};
            g.setColor(slotColor);
            g.fillPolygon(x, y, 3);
            g.setColor(b.slots.get(12 + i).color);
            for (int j = 0; j < b.slots.get(12 + i).getNumStones(); j++) {
                g.fillOval(i * 50 + 12 + barwidth, 20 * j, 20, 20);
            }
        }
        for (int i = 0; i < 6; i++) {
            int x[] = {i * 50, i * 50 + 25, i * 50 + 50};
            int y[] = {565, 390, 565};
            g.setColor(slotColor);
            g.fillPolygon(x, y, 3);
            g.setColor(b.slots.get(11 - i).color);
            for (int j = 0; j < b.slots.get(11 - i).getNumStones(); j++) {
                g.fillOval(i * 50 + 12, 565 - 20 * (j + 1), 20, 20);
            }
        }
        for (int i = 6; i < 12; i++) {
            int x[] = {i * 50 + barwidth, i * 50 + 25 + barwidth, i * 50 + 50 + barwidth};
            int y[] = {565, 390, 565};
            g.setColor(slotColor);
            g.fillPolygon(x, y, 3);
            g.setColor(b.slots.get(11 - i).color);
            for (int j = 0; j < b.slots.get(11 - i).getNumStones(); j++) {
                g.fillOval(i * 50 + 12 + barwidth, 565 - 20 * (j + 1), 20, 20);
            }
        }
        int scoreBoxWidth = 50;
        int scoreBoxHeight = 150;
        g.setColor(slotColor);
        g.drawRect(650, 0, scoreBoxWidth, scoreBoxHeight);
        g.fillRect(650, 0, scoreBoxWidth, 10 * b.score1);
        g.drawRect(650, 415, scoreBoxWidth, scoreBoxHeight);
        g.fillRect(650, 415 + 150 - 10 * b.score2, scoreBoxWidth, 10 * b.score2);

        for(int i =0; i<b.blots.get(0);i++){
            g.setColor(Color.RED);
            g.fillOval(312, 300 - (20 * b.blots.get(0))+i*20, 25, 25);
        }
        for(int i =0; i<b.blots.get(1);i++){
            g.setColor(Color.WHITE);
            g.fillOval(312, 300 + i *20, 25, 25);
        }
        g.drawString("Turn: Player " + game.getTurn(), 725, 25);
        g.drawString("Die1: " + (new Integer(game.d.die1)).toString(), 725, 50);
        g.drawString("Die2: " + (new Integer(game.d.die2)).toString(), 725, 75);
        g.drawString("Die1 left : " + (new Integer(game.d.die1Uses)).toString(), 725, 100);
        g.drawString("Die2 left : " + (new Integer(game.d.die2Uses)).toString(), 725, 125);

    }
}