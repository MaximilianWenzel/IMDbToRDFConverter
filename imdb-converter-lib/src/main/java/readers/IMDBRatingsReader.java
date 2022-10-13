package readers;

import models.IMDBRatingsRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBRatingsReader extends IMDBReader implements Iterator<IMDBRatingsRow> {
    private IMDBRatingsRow nextRow = new IMDBRatingsRow();

    public IMDBRatingsReader(File file) {
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
        title.ratings.tsv.gz – Contains the IMDb rating and votes information for titles

        tconst (string) - alphanumeric unique identifier of the title
        averageRating – weighted average of all the individual user ratings
        numVotes - number of votes the title has received
         */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setAverageRating(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : Float.parseFloat(nextLine.get(1)));
        nextRow.setNumVotes(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(2)));
    }


    @Override
    public IMDBRatingsRow next() {
        initNext();
        return nextRow;
    }
}

