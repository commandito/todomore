package co.programla.todomore;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Kenan ERARSLAN <kenan at enginaar.com>
 */
public class MainFormController implements Initializable {

    private Timeline timeline;
    @FXML
    private Label lblCounter;
    @FXML
    private ProgressIndicator prgTimer;
    @FXML
    private Button btnCount;

    private int countdown;
    private State state;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state = State.INITIAL;
        reset();        
    }

    public void count() {
        if (timeline == null) {
            timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    countdown--;
                    refreshUI();
                }
            }), new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(countdown);

        }

        Animation.Status status = timeline.getStatus();
        switch (status) {
            case PAUSED:
            case STOPPED:
                timeline.play();
                btnCount.setText("Pause (esc)");
                break;
            case RUNNING:
                timeline.pause();
                btnCount.setText("Start (enter)");
                break;
        }
    }

    private void refreshUI() {
        lblCounter.setText(countdown / 60 + ":" + countdown % 60);
        prgTimer.setProgress((double) (state.timeAsSecond() - countdown) / state.timeAsSecond());
    }

    public void reset() {
        countdown = state.timeAsSecond();
        refreshUI();
    }
    
    public void shortBreak() {
        state = State.SHORT_BREAK;
        reset();
    }
    
    public void longBreak() {
        state = State.LONG_BREAK;
        reset();
    }
   
    public void workRegular() {
        state = State.WORKING;
        reset();
    }
    
    public void workRandomly() {
        state = State.JUST_WORK;
        reset();
    }
    
    public void breakRandomly() {
        state = State.JUST_BREAK;
        reset();
    }
}
