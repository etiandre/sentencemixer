import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Phonetizer {
    public static String phonetize(String word) throws IOException {
        Process p = Runtime.getRuntime().exec(
                new String[]{"espeak", "-v", "fr", "-x", "-q", word}
        );
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String out = input.readLine();
        input.close();
        return out.trim().replaceAll("[!\\|,;:~\\-\"'\\s]", "");
    }
}
