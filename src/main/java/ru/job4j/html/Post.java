package ru.job4j.html;

import java.util.Date;

public class Post {
    private final String description;
    private final Date createdDate;

    public Post(String description, Date createdDate) {
        this.description = description;
        this.createdDate = createdDate;
    }

    public String getMsgBody() {
        return description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Post{"
                + "description='" + description + '\''
                + ", createdDate=" + createdDate + '}';
    }
}
