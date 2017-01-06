package jfkdevelopers.movielibrary;

public class Movie {
    private String id;
    private String title;
    private String year;
    private String rating;
    //private String plot;
    private String imgUrl;
    private String genre;
    //public Movie(String id, String title, String year, String rating, String plot, String imgUrl) {

    public Movie(String id, String title, String year, String rating, String imgUrl, String genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        //this.plot = plot;
        this.imgUrl = imgUrl;
        this.genre=genre;
    }

    public Movie(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

/*public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }*/

}

