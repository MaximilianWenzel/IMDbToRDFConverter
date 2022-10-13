import picocli.CommandLine;
import util.ConsoleUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class IMDBToTurtleCLApp implements Runnable {
    public static Logger log = ConsoleUtils.getLogger();

    @CommandLine.Option(names = {"-d", "--imdbDirectory"},
            required = true)
    private File imdbDirectory;

    @CommandLine.Option(names = {"-f", "--fetchFiles"},
            required = false)
    private boolean fetchFiles;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new IMDBToTurtleCLApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (fetchFiles) {
            downloadFiles();
            decompressFiles();
        }
        IMDBToTurtleConverter converter = new IMDBToTurtleConverter(imdbDirectory);
        converter.start();
    }

    private void decompressFiles() {
        IMDBDataPreprocessor preprocessor = new IMDBDataPreprocessor(imdbDirectory);
        preprocessor.preprocess();
    }

    private void downloadFiles() {
        String baseIRI = "https://datasets.imdbws.com/";
        String[] fileNames = {
                "name.basics.tsv.gz",
                "title.akas.tsv.gz",
                "title.basics.tsv.gz",
                "title.crew.tsv.gz",
                "title.episode.tsv.gz",
                "title.principals.tsv.gz",
                "title.ratings.tsv.gz",
        };
        try {
            for (String fileName : fileNames) {
                log.info("Downloading " + fileName + "...");
                Files.copy(new URL(baseIRI + fileName).openStream(),
                        Paths.get(imdbDirectory.toString(), fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
