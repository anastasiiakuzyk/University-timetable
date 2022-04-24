package sample.actions.ObserverForRozklad;

public interface IObservable {
    void addObserver(IObserver observer);
    void notifyObservers();
}
