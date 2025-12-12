package util;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class IMDBUtilsTest {

    @Test
    public void testGetGZipName() {
        File file = new File("test.tsv");
        File expected = new File("test.tsv.gz");
        assertEquals(expected.getPath(), IMDBUtils.getGZipName(file).getPath());
    }

    @Test
    public void testGetFilePaths() {
        File baseDir = new File("data");

        assertEquals(new File("data", "name.basics.tsv").getPath(),
                IMDBUtils.getNameBasicsFile(baseDir).getPath());

        assertEquals(new File("data", "title.akas.tsv").getPath(),
                IMDBUtils.getTitleAkasFile(baseDir).getPath());
    }

    @Test
    public void testNullFieldConstant() {
        assertEquals("\\N", IMDBUtils.NULL_FIELD);
    }
}
