package me.internalizable.VoiceIdentification;

import com.sun.prism.impl.Disposer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import me.internalizable.VoiceIdentification.recording.RecordingState;
import me.internalizable.VoiceIdentification.recording.SoundRecorder;
import me.internalizable.VoiceIdentification.utils.VoiceAnalyzer;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class Core extends Application implements EventHandler<ActionEvent> {

    private Button button;
    private File wavFile = new File("E:/Identification/temp.wav");
    private static SoundRecorder recorder;

    public static void main(String[] args) {
        RecordingState.setState(RecordingState.NOT_RECORDING);
        recorder = new SoundRecorder();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Voice Identification");

        button = new Button("Initiate Voice Recognition");
        button.setOnAction(this);

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {

        if(RecordingState.getState() == RecordingState.NOT_RECORDING) {
            button.setText("Recognizing Voice...");

            new Thread(() -> {

                try {
                    System.out.println("Start recording...");
                    recorder.start();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }

            }).start();

            RecordingState.setState(RecordingState.RECORDING);
        } else {
            button.setText("Analyzing Voice...");

            try {
                recorder.stop();
                recorder.save(wavFile);
                System.out.println("STOPPED");
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            VoiceAnalyzer analyzer = new VoiceAnalyzer();

            float analysisScore = analyzer.analysisScore();
            try {
                Thread.sleep(3000);
            } catch(InterruptedException exception) {
                System.out.println("Exception occurred whilst sleeping main thread: ");
                System.out.println(exception);
            }

            button.setText("Voice Similarity: " + analysisScore);

        }

    }
}
