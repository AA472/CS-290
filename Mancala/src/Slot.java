public class Slot {
    int balls = 4;

    public void setBalls(int balls) {
        this.balls = balls;
    }
    public void addBalls(int num) {
        balls = balls + num;
    }

    public int getBalls() {
        return balls;
    }
}
