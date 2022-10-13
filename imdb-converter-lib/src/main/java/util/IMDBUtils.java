package util;

import java.io.File;
import java.nio.file.Paths;

public class IMDBUtils {

    public static final String NULL_FIELD = "\\N";

    public static File getNameBasicsFile(File baseDir) {
        return Paths.get(baseDir.toString(), "name.basics.tsv").toFile();
    }

    public static File getTitleAkasFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.akas.tsv").toFile();
    }

    public static File getTitleBasicsFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.basics.tsv").toFile();
    }

    public static File getTitleCrewFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.crew.tsv").toFile();
    }

    public static File getTitleEpisodeFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.episode.tsv").toFile();
    }

    public static File getTitlePrincipalsFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.principals.tsv").toFile();
    }

    public static File getTitleRatingsFile(File baseDir) {
        return Paths.get(baseDir.toString(), "title.ratings.tsv").toFile();
    }

    public static File getGZipName(File notCompressedFile) {
        return new File(notCompressedFile.toString() + ".gz");
    }

}
