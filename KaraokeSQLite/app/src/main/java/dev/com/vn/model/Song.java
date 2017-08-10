package dev.com.vn.model;

/**
 * Created by Linh(^0^)Nguyen on 4/18/2017.
 */

public class Song {
    private String code, title, detail, title_raw, detail_raw, isFavorite;

    public Song(String code, String title, String detail, String title_raw, String detail_raw, String isFavorite) {
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.title_raw = title_raw;
        this.detail_raw = detail_raw;
        this.isFavorite = isFavorite;
    }

    public Song() {
    }

    public Song(String code, String title, String detail, String isFavorite) {
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.isFavorite = isFavorite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle_raw() {
        return title_raw;
    }

    public void setTitle_raw(String title_raw) {
        this.title_raw = title_raw;
    }

    public String getDetail_raw() {
        return detail_raw;
    }

    public void setDetail_raw(String detail_raw) {
        this.detail_raw = detail_raw;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }
}
