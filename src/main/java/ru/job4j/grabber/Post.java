package ru.job4j.grabber;

import java.util.Date;

public class Post {
    private int id;
    private final String title;
    private final String link;
    private final String description;
    private final Date createdDate;

    public Post(String title, String description, String link, Date createdDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.createdDate = createdDate;
    }

    public Post(int id, String title, String link, String description, Date createdDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id
                + "title='" + title + '\''
                + ", link='" + link + '\''
                + ", description='" + description + '\''
                + ", createdDate=" + createdDate + '}'
                + System.lineSeparator();
    }
}
