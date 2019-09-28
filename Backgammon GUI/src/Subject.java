public interface Subject {
    void register(Observer observer);
    void notifyObserver();
}
