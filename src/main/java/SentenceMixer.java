import java.io.IOException;

public class SentenceMixer {
    private Voice voice;
    public SentenceMixer(Voice voice) {
        this.voice = voice;
    }

    public void speak(String s) throws IOException, InterruptedException {
        String phonemes = Phonetizer.phonetize(s);
        System.out.println(phonemes);
        MarkerList markers = voice.findBestCandidates(phonemes);
        System.out.println(markers.size());
        for (Marker m : markers) {
            System.out.println(m.phoneme);
            voice.play(m);
        }
    }

}
