package mcb.blogs.publisher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BlogPost {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String body;

    protected BlogPost() {
    }

    public BlogPost(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
