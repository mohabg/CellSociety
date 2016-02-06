package src.controller;

/**
 * Created by davidyan on 2/4/16.
 */
public interface EventListener {
    void playAnimation();
    void pauseAnimation();
    void stepAnimation();
    void onSliderMove(int toUseValue);
}