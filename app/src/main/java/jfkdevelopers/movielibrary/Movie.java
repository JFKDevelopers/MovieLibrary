package jfkdevelopers.movielibrary;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable{

    @SerializedName("adult")
    public boolean adult;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("belongs_to_collection")
    public BelongsToCollection belongsToCollection;
    @SerializedName("budget")
    public long budget;
    @SerializedName("genres")
    public List<Genres> genres;
    @SerializedName("homepage")
    public String homepage;
    @SerializedName("id")
    public int id;
    @SerializedName("imdb_id")
    public String imdbId;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("overview")
    public String overview;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("production_companies")
    public List<ProductionCompanies> productionCompanies;
    @SerializedName("production_countries")
    public List<ProductionCountries> productionCountries;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("revenue")
    public long revenue;
    @SerializedName("runtime")
    public int runtime;
    @SerializedName("spoken_languages")
    public List<SpokenLanguages> spokenLanguages;
    @SerializedName("status")
    public String status;
    @SerializedName("tagline")
    public String tagline;
    @SerializedName("title")
    public String title;
    @SerializedName("video")
    public boolean video;
    @SerializedName("vote_average")
    public double voteAverage;
    @SerializedName("vote_count")
    public int voteCount;
    @SerializedName("credits")
    public Credits credits;

    public Movie(boolean adult, String backdropPath, BelongsToCollection belongsToCollection, int budget,
                 List<Genres> genres, String homepage, int id, String imdbId, String originalLanguage,
                 String originalTitle, String overview, double popularity, String posterPath,
                 List<ProductionCompanies> productionCompanies, List<ProductionCountries> productionCountries,
                 String releaseDate, int revenue, int runtime, List<SpokenLanguages> spokenLanguages,
                 String status, String tagline, String title, boolean video, double voteAverage, int voteCount, Credits credits) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.belongsToCollection = belongsToCollection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.credits = credits;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public BelongsToCollection getBelongsToCollection() {
            return belongsToCollection;
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Genres> getGenres() {
            return genres;
    }

    public void setGenreList(List<Genres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountries> getProductionCountries() {
            return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountries> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguages> getSpokenLanguages() {
            return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguages> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<Cast> getCast() {
        return credits.getCast();
    }

    public List<Crew> getCrew(){
        return credits.getCrew();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", belongsToCollection='" + belongsToCollection + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", imdbId='" + imdbId + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", productionCompanies=" + productionCompanies +
                ", productionCountries=" + productionCountries +
                ", releaseDate='" + releaseDate + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spokenLanguages=" + spokenLanguages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                '}';
    }

    public static class BelongsToCollection implements Serializable{
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("backdrop_path")
        public String backdropPath;
    }

    public static class Genres implements Serializable{
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
    }

    public static class ProductionCompanies implements Serializable{
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public int id;
    }

    public static class ProductionCountries implements Serializable{
        @SerializedName("iso_3166_1")
        public String iso31661;
        @SerializedName("name")
        public String name;
    }

    public static class SpokenLanguages implements Serializable{
        @SerializedName("iso_639_1")
        public String iso6391;
        @SerializedName("name")
        public String name;
    }

    public static class Cast implements Serializable{
        @SerializedName("cast_id")
        public int cast_id;
        @SerializedName("character")
        public String character;
        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("order")
        public int order;
        @SerializedName("profile_path")
        public String profile_path;
    }

    public static class Crew implements Serializable{
        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("department")
        public String department;
        @SerializedName("id")
        public int id;
        @SerializedName("job")
        public String job;
        @SerializedName("name")
        public String name;
        @SerializedName("profile_path")
        public String profile_path;
    }

    public static class Credits implements Serializable{
        @SerializedName("cast")
        public List<Cast> cast;
        @SerializedName("crew")
        public List<Crew> crew;

        public List<Cast> getCast() {
            return cast;
        }

        public List<Crew> getCrew() {
            return crew;
        }
    }
}


