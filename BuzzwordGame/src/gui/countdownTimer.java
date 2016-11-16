package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

import javafx.util.Duration;


/**
 * Created by ishmam on 11/13/2016.
 */
public class countdownTimer implements Runnable {
    private int duration;
    private IntegerProperty currentTime;
    private Label remainingTime, seconds;
    private final int delay = 100;
    static Timeline timer;
    public countdownTimer(int sec){
        duration = sec*100;
        currentTime = new SimpleIntegerProperty(duration);
        timer = new Timeline(new KeyFrame(Duration.seconds(sec), ae -> reduce()));

        remainingTime.setText("Remaining Time: ");
        seconds.textProperty().bind(currentTime.divide(100).asString());
    }

    public Label getRemainingTime() {
        return remainingTime;
    }

    public void reduce(){

    }
    @Override
    public void run() {
        timer.play();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
