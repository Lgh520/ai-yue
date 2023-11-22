package com.project.aiyue.dao.po;

import com.project.aiyue.dao.bo.Page;

import java.io.Serializable;
import java.util.Date;

public class BookInfo extends Page implements Serializable {
    private static final long serialVersionUID = -8019949730918822843L;
    private Long bookId;

    private String levelNum;

    private String subTitle;

    private String author;

    private String pubDate;

    private String originTitle;

    private String binding;

    private String pages;

    private String imageMedium;

    private String imageLarge;

    private String publisher;

    private String isbn10;

    private String isbn13;

    private String title;

    private String summary;

    private String price;

    private Integer bookCounts;

    private Long rentCounts;

    private Long goodsId;

    private Date createTime;

    private Date updateTime;
    private Integer ageType;
    private Integer themeType;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(String levelNum) {
        this.levelNum = levelNum == null ? null : levelNum.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate == null ? null : pubDate.trim();
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle == null ? null : originTitle.trim();
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding == null ? null : binding.trim();
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages == null ? null : pages.trim();
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium == null ? null : imageMedium.trim();
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge == null ? null : imageLarge.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10 == null ? null : isbn10.trim();
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13 == null ? null : isbn13.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public Integer getBookCounts() {
        return bookCounts;
    }

    public void setBookCounts(Integer bookCounts) {
        this.bookCounts = bookCounts;
    }

    public Long getRentCounts() {
        return rentCounts;
    }

    public void setRentCounts(Long rentCounts) {
        this.rentCounts = rentCounts;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAgeType() {
        return ageType;
    }

    public void setAgeType(Integer ageType) {
        this.ageType = ageType;
    }

    public Integer getThemeType() {
        return themeType;
    }

    public void setThemeType(Integer themeType) {
        this.themeType = themeType;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookId=" + bookId +
                ", levelNum='" + levelNum + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", originTitle='" + originTitle + '\'' +
                ", binding='" + binding + '\'' +
                ", pages='" + pages + '\'' +
                ", imageMedium='" + imageMedium + '\'' +
                ", imageLarge='" + imageLarge + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                ", bookCounts=" + bookCounts +
                ", rentCounts=" + rentCounts +
                ", goodsId=" + goodsId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", ageType='" + ageType + '\'' +
                ", themeType='" + themeType + '\'' +
                '}';
    }
}