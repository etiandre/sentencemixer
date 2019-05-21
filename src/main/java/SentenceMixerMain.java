import java.io.*;
import java.util.Scanner;

public class SentenceMixerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            System.out.println("Usage: " + System.getProperty("sun.java.command") + " <voice directory>");
            return;
        }
        System.out.println("Reading voice " + args[0]);

        Voice voice = new Voice(args[0]);
        voice.loadAudio("voices/" + args[0] + "/audio.wav");
        voice.loadWordMarkers("voices/" + args[0] + "/wordMarkers.txt");

        SentenceMixer sm = new SentenceMixer(voice);
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();
            System.out.println(s);
            sm.speak(s);
        }
    }
}
