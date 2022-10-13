package models;

import java.util.Arrays;

public class IMDBTitlePrincipalsRow {
    /*
    title.principals.tsv.gz – Contains the principal cast/crew for titles

    tconst (string) - alphanumeric unique identifier of the title
    ordering (integer) – a number to uniquely identify rows for a given titleId
    nconst (string) - alphanumeric unique identifier of the name/person
    category (string) - the category of job that person was in
    job (string) - the specific job title if applicable, else '\N'
    characters (string) - the name of the character played if applicable, else '\N'
     */

    private String titleId;
    private Integer rowIdOfTitle;
    private String nameId;
    private String jobCategory;
    private String jobTitle;
    private String[] characters;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public Integer getRowIdOfTitle() {
        return rowIdOfTitle;
    }

    public void setRowIdOfTitle(Integer rowIdOfTitle) {
        this.rowIdOfTitle = rowIdOfTitle;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String[] getCharacters() {
        return characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "IMDBTitlePrincipalsRow{" +
                "titleId='" + titleId + '\'' +
                ", rowIdOfTitle=" + rowIdOfTitle +
                ", nameId='" + nameId + '\'' +
                ", jobCategory='" + jobCategory + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", characters=" + Arrays.toString(characters) +
                '}';
    }
}
