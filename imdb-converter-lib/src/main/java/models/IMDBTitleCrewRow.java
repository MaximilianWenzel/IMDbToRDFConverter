package models;

import java.util.Arrays;

public class IMDBTitleCrewRow {
    /*
    https://www.imdb.com/interfaces/
    title.crew.tsv.gz – Contains the director and writer information for all the titles in IMDb. Fields include:

    tconst (string) - alphanumeric unique identifier of the title
    directors (array of nconsts) - director(s) of the given title
    writers (array of nconsts) – writer(s) of the given title
     */
    private String titleId;
    private String[] directors;
    private String[] writers;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getWriters() {
        return writers;
    }

    public void setWriters(String[] writers) {
        this.writers = writers;
    }

    @Override
    public String toString() {
        return "IMDBTitleCrewRow{" +
                "titleId='" + titleId + '\'' +
                ", directors=" + Arrays.toString(directors) +
                ", writers=" + Arrays.toString(writers) +
                '}';
    }
}
