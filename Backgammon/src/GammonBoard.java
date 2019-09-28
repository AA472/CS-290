import java.util.ArrayList;
import java.util.Arrays;

public class GammonBoard {
    ArrayList<GammonSlot> gammonSlots = new ArrayList<>();
    public GammonBoard() {
        for(int i = 0; i<24; i++){
            GammonSlot s = new GammonSlot();
            gammonSlots.add(s);
        }

        gammonSlots.get(0).setNumStones(2); gammonSlots.get(0).setPlayer(1);
        gammonSlots.get(5).setNumStones(5); gammonSlots.get(5).setPlayer(2);
        gammonSlots.get(7).setNumStones(3); gammonSlots.get(7).setPlayer(2);
        gammonSlots.get(11).setNumStones(5); gammonSlots.get(11).setPlayer(1);
        gammonSlots.get(12).setNumStones(5); gammonSlots.get(12).setPlayer(2);
        gammonSlots.get(16).setNumStones(3); gammonSlots.get(16).setPlayer(1);
        gammonSlots.get(18).setNumStones(5); gammonSlots.get(18).setPlayer(1);
        gammonSlots.get(23).setNumStones(2); gammonSlots.get(23).setPlayer(2);
    }

    public void drawBoard(ArrayList<Integer> blots, int score1, int score2) {
        char[] arr = new char[17-blots.get(0)];
        char[] arr2 = new char[17-blots.get(1)];
        char[] blots1 = new char[blots.get(0)];
        char[] blots2 = new char[blots.get(1)];
        char[] border = new char[37];
        Arrays.fill(arr,' ');
        Arrays.fill(arr2,' ');
        Arrays.fill(blots1,'o');
        Arrays.fill(blots2,'x');
        Arrays.fill(border,0,2, ' ');Arrays.fill(border,3,37, '-');

        System.out.println(border); // 34 dashes
        for(int i = 0; i< gammonSlots.size()/2; i++){
            if(i== gammonSlots.size()/4) {
                System.out.println(border); // 34 dashes
                System.out.print("-1-|"); System.out.print(arr);System.out.print(blots1);
                System.out.print(blots2);System.out.print(arr2);System.out.println("|- ");
                System.out.println(border); // 34 dashes
            }
            drawLine(i);
        }
        System.out.println(border); // 34 dashes
        drawScoreBoxes(score1,score2);
    }
    public void drawLine(int slotNum){
        if((12-slotNum)<10)
          System.out.print((12-slotNum)+" -|");
        else
            System.out.print((12-slotNum)+"-|");

        gammonSlots.get(11-slotNum).drawSlot();
        for(int i = 30; i>= gammonSlots.get(11-slotNum).getNumStones() + gammonSlots.get(12 + slotNum).getNumStones(); i--)
            System.out.print(' ');
        gammonSlots.get(12 + slotNum).drawSlot(); //The slot opposing slotNum (on the same line)
        System.out.println("|- " +(13+ slotNum));
    }
    public void drawScoreBoxes(int score1,int score2){
        char[] border = new char[16];
        Arrays.fill(border,0,8, ' ');Arrays.fill(border,9,16, '-');
        int player1Score = score1;
        int player2Score = score2;
        System.out.print(border);System.out.println(border);

        for(int j=0;j<3;j++) {
            if(j == 1)
                System.out.print("       0-|");
            else
                System.out.print("         |");
            for(int i =0;i<5;i++) {
                if(player2Score>0) {
                    System.out.print('x');
                    player2Score--;
                }
                else System.out.print(' ');
            }
            if(j==1)
                System.out.print("|      25-");
            else
                System.out.print("|         ");
            System.out.print('|');
            for(int i =0;i<5;i++) {
                if(player1Score>0) {
                    System.out.print('o');
                    player1Score--;
                }
                else System.out.print(' ');
            }
            System.out.println('|');
        }
        System.out.print(border); System.out.println(border);
    }
}


























