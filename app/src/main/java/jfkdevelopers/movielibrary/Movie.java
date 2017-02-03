package jfkdevelopers.movielibrary;

import android.util.Log;
import android.util.SparseArray;

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
    @SerializedName("belongs_to_collection_string")
    public String belongsToCollectionString;
    @SerializedName("budget")
    public int budget;
    @SerializedName("genres")
    public List<Genres> genres;
    @SerializedName("genres_string")
    public String genreString;
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
    @SerializedName("production_companies_string")
    public String productionCompaniesString;
    @SerializedName("production_countries")
    public List<ProductionCountries> productionCountries;
    @SerializedName("production_countries_string")
    public String productionCountriesString;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("revenue")
    public int revenue;
    @SerializedName("runtime")
    public int runtime;
    @SerializedName("spoken_languages")
    public List<SpokenLanguages> spokenLanguages;
    @SerializedName("spoken_languages_string")
    public String spokenLanguagesString;
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

    public Movie(boolean adult, String backdropPath, BelongsToCollection belongsToCollection, int budget,
                 List<Genres> genres, String homepage, int id, String imdbId, String originalLanguage,
                 String originalTitle, String overview, double popularity, String posterPath,
                 List<ProductionCompanies> productionCompanies, List<ProductionCountries> productionCountries,
                 String releaseDate, int revenue, int runtime, List<SpokenLanguages> spokenLanguages,
                 String status, String tagline, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.belongsToCollection = belongsToCollection;
        this.belongsToCollectionString = getBelongsToCollection();
        this.budget = budget;
        this.genres = genres;
        this.genreString = getGenres();
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.productionCompaniesString = getProductionCompanies();
        this.productionCountries = productionCountries;
        this.productionCountriesString = getProductionCountries();
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.spokenLanguagesString = getSpokenLanguages();
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public Movie(int id, boolean adult, String backdropPath, String belongsToCollection, int budget,
                 String genres, String homepage, String imdbId, String originalLanguage,
                 String originalTitle, String overview, double popularity, String posterPath,
                 String productionCompanies, String productionCountries,
                 String releaseDate, int revenue, int runtime, String spokenLanguages,
                 String status, String tagline, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.belongsToCollectionString = belongsToCollection;
        this.budget = budget;
        this.genreString = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompaniesString = productionCompanies;
        this.productionCountriesString = productionCountries;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguagesString = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
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

    public String getBelongsToCollection() {
            if (belongsToCollection != null) {
                return this.belongsToCollection.name;
            } else return "";
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getGenres() {
            if (genres != null && genres.size()>0) {
                SparseArray<String> genreMap = new SparseArray<>();
                genreMap.put(28, "Action");
                genreMap.put(12, "Adventure");
                genreMap.put(16, "Animation");
                genreMap.put(35, "Comedy");
                genreMap.put(80, "Crime");
                genreMap.put(99, "Documentary");
                genreMap.put(18, "Drama");
                genreMap.put(10751, "Family");
                genreMap.put(14, "Fantasy");
                genreMap.put(36, "History");
                genreMap.put(27, "Horror");
                genreMap.put(10402, "Music");
                genreMap.put(9648, "Mystery");
                genreMap.put(10749, "Romance");
                genreMap.put(878, "Science Fiction");
                genreMap.put(10770, "TV Movie");
                genreMap.put(53, "Thriller");
                genreMap.put(10752, "War");
                genreMap.put(37, "Western");
                String out = "";
                for (Genres g : genres) {
                    out = out + genreMap.get(g.id) + ", ";
                }
                return out.substring(0, out.length() - 2);
            } else return "";
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

    public String getProductionCompanies() {
            if (productionCompanies != null && productionCompanies.size()>0) {
                String out = "";
                for (ProductionCompanies p : this.productionCompanies) {
                    out = out + p.name + ", ";
                }
                return out.substring(0, out.length() - 2);
            } else return "";
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getProductionCountries() {
            if (productionCountries != null && productionCountries.size()>0) {
                String out = "";
                for (ProductionCountries p : this.productionCountries) {
                    out = out + p.name + ", ";
                }
                return out.substring(0, out.length() - 2);
            } else return "";
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

    public int getRevenue() {
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

    public String getSpokenLanguages() {
            if (spokenLanguages != null && spokenLanguages.size()>0) {
                String out = "";
                for (SpokenLanguages s : this.spokenLanguages) {
                    out = out + s.name + ", ";
                }
                return out.substring(0, out.length() - 2);
            } else return "";
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
}


