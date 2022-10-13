package readers;

import models.IMDBTitleAkasRow;
import org.apache.commons.csv.CSVRecord;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBTitleAkasReader extends IMDBReader implements Iterator<IMDBTitleAkasRow> {
    private IMDBTitleAkasRow nextRow = new IMDBTitleAkasRow();

    public IMDBTitleAkasReader(File file) {
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
            title.akas.tsv.gz - Contains the following information for titles:

            titleId (string) - a tconst, an alphanumeric unique identifier of the title
            ordering (integer) – a number to uniquely identify rows for a given titleId
            title (string) – the localized title
            region (string) - the region for this version of the title
            language (string) - the language of the title
            types (array) - Enumerated set of attributes for this alternative title. One or more of the following: "alternative", "dvd", "festival", "tv", "video", "working", "original", "imdbDisplay". New values may be added in the future without warning
            attributes (array) - Additional terms to describe this alternative title, not enumerated
            isOriginalTitle (boolean) – 0: not original title; 1: original title

             */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setOrdering(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(1)));
        nextRow.setTitle(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(2));
        nextRow.setRegion(nextLine.get(3).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(3));
        nextRow.setLanguage(nextLine.get(4).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(4));
        nextRow.setImdbTypes(nextLine.get(5).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(5).split("\u0002")); // are separated by STX char
        nextRow.setAttributes(nextLine.get(6).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(6).split(","));
        nextRow.setOriginalTitle(nextLine.get(7).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(7).equals("1"));
    }


    @Override
    public IMDBTitleAkasRow next() {
        initNext();
        return nextRow;
    }
}
