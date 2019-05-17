import java.io.File;
import java.io.IOException;
import java.util.*;

public class Voice {
    private String name;
    private File audio;
    private MarkerList phonemes;
    private Random rng;

    public Voice(String name) {
        this.name = name;
        rng = new Random();
    }

    public void loadAudio(String audioFilename) throws IOException {
        audio = new File(audioFilename);
        if (!audio.isFile())
            throw new IOException("Cannot load " + audioFilename);
    }

    /**
     * loads markers and performs phoneme interpolation
     *
     * @param markersFilename
     * @throws IOException
     */
    public void loadWordMarkers(String markersFilename) throws IOException {
        phonemes = new MarkerList();
        Scanner scanner = new Scanner(new File(markersFilename));
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().replace(",",".").split("\t");
            double wordStart = Double.parseDouble(fields[0]);
            double wordEnd = Double.parseDouble(fields[1]);
            String word = fields[2];
            //System.out.println(String.format("%s [%.2f:%.2f]", word, wordStart, wordEnd));
            // interpolate
            String phonemeList = Phonetizer.phonetize(word);
            for (int i = 0; i < phonemeList.length(); i++) {
                Marker m = new Marker();
                m.phoneme = phonemeList.substring(i, i + 1);
                m.start = wordStart + (double) i / (double) phonemeList.length() * (wordEnd - wordStart);
                m.end = wordStart + ((double) i + 1f) / (double) phonemeList.length() * (wordEnd - wordStart);
                phonemes.add(m);
            }
        }
        scanner.close();
    }

    /*
        private void findAllCombinations(String str, int index, String out, ArrayList<String[]> list) {
            if (index == str.length()) {
                list.add(out.substring(1).split(" "));
            }
            for (int i = index; i < str.length(); i++) {
                findAllCombinations(str, i + 1, out + " " + str.substring(index, i + 1), list);
            }
        }

        public ArrayList<String[]> getAllCombinations(String query) {
            ArrayList<String[]> combinations = new ArrayList<String[]>();
            findAllCombinations(query, 0, "", combinations);
            return combinations;
        }

        public MarkerList findBestCandidates(String query) {
            ArrayList<Integer> scores = new ArrayList<>();

            MarkerList bestPositions = new MarkerList();
            int bestScore = 0;
            for (String[] combination : getAllCombinations(query)) {
    //            System.out.println(Arrays.toString(combination));
                ArrayList<Integer> positions = new ArrayList<>();
                for (String phonemeGroup : combination) {
                    int index = phonemes.indexOf(phonemeGroup);
                    if (index == -1)
                        break;
                    else
                        positions.add(index);
                }
                if (positions.size() == combination.length) {
                    // trouvéééé !
                    int score = Arrays.stream(combination).mapToInt(String::length).min().orElseThrow(NoSuchElementException::new);
                    if (score > bestScore) {
                        bestScore = score;
                        bestPositions = new MarkerList();
                        for (int i = 0; i < positions.size(); i++) {
                            Marker m = new Marker();
                            m.phoneme = combination[i];
                            m.start = phonemes.get(positions.get(i)).start;
                            m.end = phonemes.get(positions.get(i)+combination.length).end;
                            bestPositions.add(m);
                        }
                    }
                }
            }
            if (bestScore == 0) {
                System.out.println("Voice incomplete !!!");
            }
            System.out.println("best score = " + bestScore);
            return bestPositions;
        }*/
    public MarkerList findBestCandidates(String phonemeQuery) {
        MarkerList r = new MarkerList();
        int startIndex = 0;
        while (startIndex < phonemeQuery.length()) {
            for (int endIndex = phonemeQuery.length(); endIndex > startIndex; endIndex--) {
                String phonemeSubstring = phonemeQuery.substring(startIndex, endIndex);
                int phonemeIndex = phonemes.indexOf(phonemeSubstring);
                if (phonemeIndex != -1) {
                    //System.out.println(phonemeIndex + " " + phonemeSubstring);
                    Marker m = new Marker();
                    m.phoneme = phonemeSubstring;
                    m.start = phonemes.get(phonemeIndex).start;
                    m.end = phonemes.get(phonemeIndex+endIndex-startIndex-1).end;
                    r.add(m);

                    startIndex = endIndex;
                }
                else if (endIndex == startIndex +1 ) {
                    System.out.println("oh no ! voice incomplete");
                    startIndex = endIndex;
                }
            }
        }

        return r;
    }

    public void play(Marker m) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        rt.exec(new String[]{"play", audio.getPath(), "trim", ""+m.start, ""+(m.end-m.start)}).waitFor();
    }
}