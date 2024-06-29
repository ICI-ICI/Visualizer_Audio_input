import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        LineVisualisation(""); //Audiofile there
    }

    static void LineVisualisation(String file_name) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        //Create giu
        Video giu = new Video();
        giu.CreateWindow();

        //Create audio thread
        Audio.Player player = new Audio.Player(0, file_name);

        int[] amplitude = Audio.getAmplitude(file_name, 10000);
        int max_ampl = Arrays.stream(amplitude).max().getAsInt();
        long step =(player.getTime() / (amplitude.length / 2));

        player.start();

        //Line visualisation
        long next_pause_time = 0;
        int i = 0;
        while (i != amplitude.length - 1) {
            float l = (float)amplitude[i] / (float)max_ampl;
            if (player.getPosition() >= next_pause_time) {
                giu.drawPoint(2, l, 20);
                i+=2;
                next_pause_time += step;
            } else
                Thread.sleep(1);
        }
    }
}