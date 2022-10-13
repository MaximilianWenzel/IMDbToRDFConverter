package models;

import java.util.Arrays;

public class IMDBNameBasicsRow {
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
    private String nameId;
    private String primaryPersonName;
    private Integer birthYear;
    private Integer deathYear;
    private String[] primaryProfession;
    private String[] knownForTitles;

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getPrimaryName() {
        return primaryPersonName;
    }

    public void setPrimaryPersonName(String primaryPersonName) {
        this.primaryPersonName = primaryPersonName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String[] getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(String[] primaryProfession) {
        this.primaryProfession = primaryProfession;
    }

    public String[] getKnownForTitles() {
        return knownForTitles;
    }

    public void setKnownForTitles(String[] knownForTitles) {
        this.knownForTitles = knownForTitles;
    }

    @Override
    public String toString() {
        return "IMDBNameBasicsRow{" +
                "nameId='" + nameId + '\'' +
                ", primaryPersonName='" + primaryPersonName + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", primaryProfession=" + Arrays.toString(primaryProfession) +
                ", knownForTitles=" + Arrays.toString(knownForTitles) +
                '}';
    }
}
