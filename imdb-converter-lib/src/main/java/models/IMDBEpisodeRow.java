package models;

public class IMDBEpisodeRow {
    /*
    https://www.imdb.com/interfaces/
    title.episode.tsv.gz – Contains the tv episode information. Fields include:

    tconst (string) - alphanumeric identifier of episode
    parentTconst (string) - alphanumeric identifier of the parent TV Series
    seasonNumber (integer) – season number the episode belongs to
    episodeNumber (integer) – episode number of the tconst in the TV series
    */

    private String titleId;
    private String parentTitleId;
    private Integer seasonNumber;
    private Integer episodeNumber;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getParentTitleId() {
        return parentTitleId;
    }

    public void setParentTitleId(String parentTitleId) {
        this.parentTitleId = parentTitleId;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String toString() {
        return "IMDBEpisodeRow{" +
                "titleId='" + titleId + '\'' +
                ", parentTitleId='" + parentTitleId + '\'' +
                ", seasonNumber=" + seasonNumber +
                ", episodeNumber=" + episodeNumber +
                '}';
    }
}
