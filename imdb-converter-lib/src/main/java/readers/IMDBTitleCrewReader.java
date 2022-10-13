package readers;

import models.IMDBTitleCrewRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBTitleCrewReader extends IMDBReader implements Iterator<IMDBTitleCrewRow> {
    private IMDBTitleCrewRow nextRow = new IMDBTitleCrewRow();

    public IMDBTitleCrewReader(File file) {
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

            title.crew.tsv.gz – Contains the director and writer information for all the titles in IMDb. Fields include:

            tconst (string) - alphanumeric unique identifier of the title
            directors (array of nconsts) - director(s) of the given title
            writers (array of nconsts) – writer(s) of the given title
            */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setDirectors(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(1).split(","));
        nextRow.setWriters(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(2).split(","));
    }


    @Override
    public IMDBTitleCrewRow next() {
        initNext();
        return nextRow;
    }
}
