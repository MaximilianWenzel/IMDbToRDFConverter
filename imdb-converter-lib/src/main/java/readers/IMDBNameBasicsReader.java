package readers;

import models.IMDBNameBasicsRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBNameBasicsReader extends IMDBReader implements Iterator<IMDBNameBasicsRow> {
    private IMDBNameBasicsRow nextRow = new IMDBNameBasicsRow();

    public IMDBNameBasicsReader(File file) {
        super(file);
    }

    private void initNext() {
        CSVRecord nextLine = getNextLine();
        if (nextLine == null) {
            nextRow = null;
            return;
        }
            /*
            https://www.imdb.com/interfaces/
            name.basics.tsv.gz – Contains the following information for names:

            nconst (string) - alphanumeric unique identifier of the name/person
            primaryName (string)– name by which the person is most often credited
            birthYear – in YYYY format
            deathYear – in YYYY format if applicable, else '\N'
            primaryProfession (array of strings)– the top-3 professions of the person
            knownForTitles (array of tconsts) – titles the person is known for
            */
        nextRow.setNameId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setPrimaryPersonName(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(1));
        nextRow.setBirthYear(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(2)));
        nextRow.setDeathYear(nextLine.get(3).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(3)));
        nextRow.setPrimaryProfession(nextLine.get(4).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(4).split(","));
        nextRow.setKnownForTitles(nextLine.get(5).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(5).split(","));
    }


    @Override
    public IMDBNameBasicsRow next() {
        initNext();
        return nextRow;
    }
}
