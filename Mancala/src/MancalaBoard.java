import java.util.ArrayList;

public class MancalaBoard {

    ArrayList<Slot> slots;
    MancalaBoard() {
        slots = new ArrayList<Slot>();
        for (int i = 0; i < 14; i++) {
            Slot s = new Slot();
            slots.add(s);
        }
        slots.get(6).setBalls(0);
        slots.get(13).setBalls(0);
    }

    public void printScoreSlot(Slot s,int player){
        System.out.println("------------------------");
        System.out.println("    Player "+player+"'s cup");
        int lefttoprint = s.balls;
        for (int i=0;i<4;i++){
            System.out.print("   ");
            for (int j =0; j<12; j++){
                if (lefttoprint!=0){
                    System.out.print('o');
                    lefttoprint--;
                }
                else
                    System.out.print(' ');
            }
            System.out.println();
        }
    }
    public void printSlot(int slotNum, Slot p1Slot,Slot p2Slot){
        int slot1YetToPrint = p1Slot.balls;
        int slot2YetToPrint = p2Slot.balls;

        for (int line = 1; line<=4; line++){
            if(line==2)
                System.out.print((slotNum+1)+"-   |");
            else
                System.out.print("     |");
            for(int j = 0; j<5; j++){
                if (slot1YetToPrint!=0){
                    System.out.print('o');
                    slot1YetToPrint--;
                }
                else
                    System.out.print(' ');
            }
            System.out.print(" | ");
            for(int j = 0; j<5; j++){
                if (slot2YetToPrint!=0){
                    System.out.print('o');
                    slot2YetToPrint--;
                }
                else
                    System.out.print(' ');
            }
            if(line==2)
                System.out.println("|     -"+(7-slotNum-1));
            else
                System.out.println('|');
        }
        System.out.println("     ------ ------");
    }

    public void print(){
        this.printScoreSlot(slots.get(13),2);
        System.out.println("------------------------");
        System.out.println("    Player1 Player2");
        System.out.print("     ------ ------");
        System.out.println();
        for (int i = 0; i < 6; i++) {
            this.printSlot(i, slots.get(i), slots.get(12 - i));
        }
        System.out.println();
        this.printScoreSlot(slots.get(6),1);
        System.out.println("------------------------");
    }
}

