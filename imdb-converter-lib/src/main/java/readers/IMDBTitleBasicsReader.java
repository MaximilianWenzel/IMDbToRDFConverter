package readers;

import models.IMDBTitleBasicsRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBTitleBasicsReader extends IMDBReader implements Iterator<IMDBTitleBasicsRow> {

    // https://www.imdb.com/interfaces/

    private IMDBTitleBasicsRow nextRow = new IMDBTitleBasicsRow();

    public IMDBTitleBasicsReader(File file) {
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
            title.basics.tsv.gz - Contains the following information for titles:

            tconst (string) - alphanumeric unique identifier of the title
            titleType (string) – the type/format of the title (e.g. movie, short, tvseries, tvepisode, video, etc)
            primaryTitle (string) – the more popular title / the title used by the filmmakers on promotional materials at the point of release
            originalTitle (string) - original title, in the original language
            isAdult (boolean) - 0: non-adult title; 1: adult title
            startYear (YYYY) – represents the release year of a title. In the case of TV Series, it is the series start year
            endYear (YYYY) – TV Series end year. ‘\N’ for all other title types
            runtimeMinutes – primary runtime of the title, in minutes
            genres (string array) – includes up to three genres associated with the title
         */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setTitleType(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(1));
        nextRow.setPrimaryTitle(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(2));
        nextRow.setOriginalTitle(nextLine.get(3).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(3));
        nextRow.setAdult(nextLine.get(4).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(4).equals("1"));
        nextRow.setStartYear(nextLine.get(5).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(5)));
        nextRow.setEndYear(nextLine.get(6).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(6)));
        nextRow.setRuntimeMinutes(nextLine.get(7).equals(IMDBUtils.NULL_FIELD) ? null : Double.parseDouble(nextLine.get(7)));
        nextRow.setGenres(nextLine.get(8).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(8).split(","));
    }


    @Override
    public IMDBTitleBasicsRow next() {
        initNext();
        return nextRow;
    }
}
