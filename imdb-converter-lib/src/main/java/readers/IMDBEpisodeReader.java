package readers;

import models.IMDBEpisodeRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBEpisodeReader extends IMDBReader implements Iterator<IMDBEpisodeRow> {
    private IMDBEpisodeRow nextRow = new IMDBEpisodeRow();

    public IMDBEpisodeReader(File file) {
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
            title.episode.tsv.gz – Contains the tv episode information. Fields include:

            tconst (string) - alphanumeric identifier of episode
            parentTconst (string) - alphanumeric identifier of the parent TV Series
            seasonNumber (integer) – season number the episode belongs to
            episodeNumber (integer) – episode number of the tconst in the TV series
            */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setParentTitleId(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(1));
        nextRow.setSeasonNumber(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(2)));
        nextRow.setEpisodeNumber(nextLine.get(3).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(3)));

    }


    @Override
    public IMDBEpisodeRow next() {
        initNext();
        return nextRow;
    }
}
