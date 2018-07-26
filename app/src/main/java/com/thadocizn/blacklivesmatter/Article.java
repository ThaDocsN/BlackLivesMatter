package com.thadocizn.blacklivesmatter;

public class Article {
    private String section;
    private String articleTitle;
    private String webLink;
    private String publicationDate;

    /**
     * create an article object
     *
     * @param section is the section where article came from
     * @param articleTitle = name of the article
     * @param webLink = link to the web page
     * @param publicationDate = date and time when article was published
     */

    public Article(String section, String articleTitle, String webLink, String publicationDate) {
        this.section = section;
        this.articleTitle = articleTitle;
        this.webLink = webLink;
        this.publicationDate = publicationDate;
    }

    public String getSection() {
        return section;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getWebLink() {
        return webLink;
    }
}
