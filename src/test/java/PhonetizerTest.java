import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PhonetizerTest {

    @Test
    void phonetize() throws IOException {
        String pho = Phonetizer.phonetize("il y a des produits");
        assertEquals(pho, "iliadeprOdyi");
    }
}