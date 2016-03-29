package co.programla.todomore;

import java.net.URL;
import java.text.MessageFormat;
import static java.text.MessageFormat.format;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Kenan ERARSLAN <kenan at enginaar.com>
 */
public class MainFormController implements Initializable, EventHandler<ActionEvent> {

    private Timeline timeline;
    @FXML
    private Label lblCounter;
    @FXML
    private ProgressIndicator prgTimer;
    @FXML
    private Button btnCount;
    @FXML
    private VBox cntTimeline;

    private int countdown;
    private State state;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reset(State.INITIAL);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                countdown--;
                refreshUI();
            }
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setOnFinished(this);
        timeline.setCycleCount(countdown);
    }

    public void count() {
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
        timeline.setCycleCount(countdown);
    }

    public void reset(State stt) {
        cntTimeline.getChildren().add(new Label(MessageFormat.format("{0} state changed to {1}", state, stt)));
        state = stt;
        countdown = state.timeAsSecond();
    }

    public void rst() {
        reset(state);
        refreshUI();
    }

    public void shortBreak() {
        reset(State.SHORT_BREAK);
        refreshUI();
    }

    public void longBreak() {
        reset(State.LONG_BREAK);
        refreshUI();
    }

    public void workRegular() {
        reset(State.WORKING);
        refreshUI();
    }

    public void workRandomly() {
        reset(State.JUST_WORK);
        refreshUI();
    }

    public void breakRandomly() {
        reset(State.JUST_BREAK);
        refreshUI();
    }

    public void handle(ActionEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Time is up");
                a.setContentText(format("Your time for {1} is up, please go on next step", state));
                a.showAndWait();
            }
        });

    }
}
