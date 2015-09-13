package com.example.android.moviesapp.movieInfo;

/**
 * Created by Samuel on 15-09-11.
 */
public class Movies {
    private int id;
    private String link;
    private String title;
    private double average_count;
    private String date;
    private String overView;

    public Movies(int ID, String name, String Date, String Link, Double rate, String detail )
    {
        this.id = ID;
        this.link = Link;
        this.title = name;
        this.date = Date;
        this.average_count = rate;
        this.overView = detail;
    }

    //GETTERS
    public int getId() {return id;}

    public String getLink() {
        return link;
    }

    public String getTitle() {return title;}

    public String getDate() {return date;}

    public double getAverage_count() {
        return average_count;
    }


    public String getOverView() {
        return overView;
    }
}
