package re.etiand.sentencemixer;

import java.util.Objects;

public class Marker {
    double start;
    double end;
    String phoneme;

    @Override
    public String toString() {
        return this.phoneme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marker)) return false;
        Marker marker = (Marker) o;
        return Double.compare(marker.start, start) == 0 &&
                Double.compare(marker.end, end) == 0 &&
                Objects.equals(phoneme, marker.phoneme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, phoneme);
    }
}
