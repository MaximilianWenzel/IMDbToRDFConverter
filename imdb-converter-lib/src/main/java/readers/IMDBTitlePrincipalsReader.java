package readers;

import models.IMDBTitlePrincipalsRow;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import util.IMDBUtils;

import java.io.File;
import java.util.Iterator;

public class IMDBTitlePrincipalsReader extends IMDBReader implements Iterator<IMDBTitlePrincipalsRow> {
    private IMDBTitlePrincipalsRow nextRow = new IMDBTitlePrincipalsRow();

    public IMDBTitlePrincipalsReader(File file) {
        super(file);
    }

    private void initNext() {
        CSVRecord nextLine = getNextLine();
        if (nextLine == null) {
            nextRow = null;
            return;
        }

            /*
            title.principals.tsv.gz – Contains the principal cast/crew for titles

            tconst (string) - alphanumeric unique identifier of the title
            ordering (integer) – a number to uniquely identify rows for a given titleId
            nconst (string) - alphanumeric unique identifier of the name/person
            category (string) - the category of job that person was in
            job (string) - the specific job title if applicable, else '\N'
            characters (string) - the name of the character played if applicable, else '\N'
             */
        nextRow.setTitleId(nextLine.get(0).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(0));
        nextRow.setRowIdOfTitle(nextLine.get(1).equals(IMDBUtils.NULL_FIELD) ? null : Integer.parseInt(nextLine.get(1)));
        nextRow.setNameId(nextLine.get(2).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(2));
        nextRow.setJobCategory(nextLine.get(3).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(3));
        nextRow.setJobTitle(nextLine.get(4).equals(IMDBUtils.NULL_FIELD) ? null : nextLine.get(4));

        String[] characters;
        if (nextLine.get(5).equals(IMDBUtils.NULL_FIELD)) {
            characters = null;
        } else {
            // is json array
            JSONArray jsonArray = new JSONArray(nextLine.get(5));
            characters = jsonArray.toList().stream().map(o -> (String) o).toArray(String[]::new);
        }
        nextRow.setCharacters(characters);
    }


    @Override
    public IMDBTitlePrincipalsRow next() {
        initNext();
        return nextRow;
    }
}
