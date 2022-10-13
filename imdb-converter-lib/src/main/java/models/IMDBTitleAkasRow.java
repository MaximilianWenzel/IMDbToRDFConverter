package models;

import java.util.Arrays;

public class IMDBTitleAkasRow {
    private String titleId;
    private Integer ordering;
    private String title;
    private String region;
    private String language;
    private String[] imdbTypes;
    private String[] attributes;
    private Boolean isOriginalTitle;

    public IMDBTitleAkasRow() {
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getImdbTypes() {
        return imdbTypes;
    }

    public void setImdbTypes(String[] imdbTypes) {
        this.imdbTypes = imdbTypes;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public Boolean getOriginalTitle() {
        return isOriginalTitle;
    }

    public void setOriginalTitle(Boolean originalTitle) {
        isOriginalTitle = originalTitle;
    }

    @Override
    public String toString() {
        return "IMDBTitleAkasRow{" +
                "titleId='" + titleId + '\'' +
                ", ordering=" + ordering +
                ", title='" + title + '\'' +
                ", region='" + region + '\'' +
                ", language='" + language + '\'' +
                ", imdbTypes=" + Arrays.toString(imdbTypes) +
                ", attributes=" + Arrays.toString(attributes) +
                ", isOriginalTitle=" + isOriginalTitle +
                '}';
    }
}
