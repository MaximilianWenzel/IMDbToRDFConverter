import util.ConsoleUtils;
import util.IMDBUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class IMDBDataPreprocessor {
    public static Logger log = ConsoleUtils.getLogger();
    private File imdbDirectory;

    public IMDBDataPreprocessor(File imdbDirectory) {
        this.imdbDirectory = imdbDirectory;
    }

    public void preprocess() {
        File f = IMDBUtils.getNameBasicsFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitleAkasFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitleBasicsFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitleCrewFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitleEpisodeFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitlePrincipalsFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);

        f = IMDBUtils.getTitleRatingsFile(imdbDirectory);
        unzip(IMDBUtils.getGZipName(f), f);
    }

    private void unzip(File source, File target) {
        log.info("Unzipping " + source + " to " + target + "...");
        try (GZIPInputStream gis = new GZIPInputStream(
                new FileInputStream(source));
             FileOutputStream fos = new FileOutputStream(target)) {
            gis.transferTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
