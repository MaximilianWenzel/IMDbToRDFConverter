package readers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Iterator;

public abstract class IMDBReader {
    protected File file;
    private CSVParser csvReader;
    private Iterator<CSVRecord> csvIt;

    public IMDBReader(File file) {
        this.file = file;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        Reader reader = Files.newBufferedReader(file.toPath());
        CSVFormat csvFormat = CSVFormat.newFormat('\t');
        this.csvReader = new CSVParser(reader, csvFormat);

        // skip header
        csvIt = csvReader.iterator();
        csvIt.next();
    }


    public boolean hasNext() {
        return csvIt.hasNext();
    }

    public CSVRecord getNextLine() {
        return csvIt.next(); // TODO: prevent unnecessary conversion
    }

}
