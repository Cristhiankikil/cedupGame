package effects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class Sons {

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    private String resourcePath;
    private Clip clip;

    public Sons(String resourcePath) {
        this.resourcePath = resourcePath;
        carregarSom();
    }

    private void carregarSom() {
        try (InputStream soundFile = getClass().getResourceAsStream(resourcePath)) {
            if (soundFile == null) {
                System.out.println("Arquivo de áudio não encontrado: " + resourcePath);
                return;
            }

            // Converte o InputStream em um ByteArrayInputStream
            byte[] buffer = soundFile.readAllBytes();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais)) {

                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInputStream);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Tocar() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Interrompe o som se estiver tocando
            }
            clip.setFramePosition(0); // Recomeça o som do início
            clip.start();
        }
    }

    public void Parar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
