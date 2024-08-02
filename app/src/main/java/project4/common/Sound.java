package project4.common;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;

public class Sound {
    public static void horseSound(){
        String filePath = "app/src/main/resources/horse1.mp3"; // MP3 파일 경로를 설정하세요
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Player player = new Player(fileInputStream);
            player.play();
        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void horseSound2(){
        String filePath = "app/src/main/resources/horse2.mp3"; // MP3 파일 경로를 설정하세요
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Player player = new Player(fileInputStream);
            player.play();
        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }
}
