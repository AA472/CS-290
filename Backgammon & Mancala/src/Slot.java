public class Slot {
    int numStones = 4;
    char shape = 'o';
    public void setNumStones(int numStones) {
        this.numStones = numStones;
    }
    public void addStones(int num) {
        numStones = numStones + num;
    }

    public int getNumStones() {
        return numStones;
    }

    public void drawSlot(){
        for(int i =0; i < numStones; i++)
            System.out.print(shape);
    }
}
