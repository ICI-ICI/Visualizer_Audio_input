import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class Audio {

    // array of amplitude from wav file <file_name>
    // <array_length> - sample number
    static public int[] getAmplitude(String file_name, int maximum_array_length) throws UnsupportedAudioFileException, IOException {
        File path = new File(file_name);
        int[] final_amplitudes;

        try (AudioInputStream in = AudioSystem.getAudioInputStream(path)) {
            final int BUFFER_SIZE = 4096;

            byte[] buffer = new byte[BUFFER_SIZE];

            final_amplitudes = new int[maximum_array_length];
            int available = in.available();
            int samples_per_second = available / maximum_array_length;

            int current_sample_counter = 0;
            int array_cell_position = 0;
            float current_cell_value = 0.0f;

            int array_cell_value;

            while (in.read(buffer, 0, BUFFER_SIZE) > 0) {
                for (int i = 0; i < buffer.length - 1; i+=2) {
                    array_cell_value = (((buffer[i+1] << 8) | buffer[i] & 0xff) << 16) / 32767;

                    if (current_sample_counter != samples_per_second) {
                        ++current_sample_counter;
                        current_cell_value += Math.abs(array_cell_value);
                    } else {
                        if (array_cell_position != maximum_array_length) {
                            final_amplitudes[array_cell_position] = final_amplitudes[array_cell_position+1] = (int) current_cell_value / samples_per_second;

                            current_sample_counter = 0;
                            current_cell_value = 0;
                            array_cell_position +=2;
                        }
                    }
                }
            }
        }
        return final_amplitudes;
    }

    // Music player
    public static class Player extends Thread {
        Clip control;   // for the control
        long delay;     // start delay

        // <delay> - start delay
        // file_name - wav file
        public Player(long delay, String file_name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            this.delay = delay;
            File path = new File(file_name);

            try (AudioInputStream in = AudioSystem.getAudioInputStream(path);) {
                control = AudioSystem.getClip();
                control.open(in);
                control.setFramePosition(0);
            }
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Задержка воспроизведения клипа отрицательная Audio.Player.run.delay");
            }

            control.start();

            try {
                Thread.sleep(control.getMicrosecondLength()/1000);
            } catch (InterruptedException e) {
                System.out.println("Отрицательный таймер длины клипа Audio.Player.run()");
            }

            control.stop();
            control.close();
        }

        //length of current clip
        public long getTime() {
            return control.getMicrosecondLength();
        }

        //Debug del
        public long getPosition() {
            return control.getMicrosecondPosition();
        }
    }
}
