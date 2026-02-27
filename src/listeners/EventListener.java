package listeners;

public interface EventListener<T> {
    void onEvent(T event);
}
