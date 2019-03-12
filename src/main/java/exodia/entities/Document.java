package exodia.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "documents")
@Table
public class Document extends BaseEntity {
    private String title;
    private String content;

    public Document() {
    }

    @Column(name = "title",nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "contents",columnDefinition = "TEXT",nullable = false)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
