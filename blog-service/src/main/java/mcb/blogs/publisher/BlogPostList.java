package mcb.blogs.publisher;

import javax.persistence.*;
import java.util.List;

@Entity
public class BlogPostList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BlogPost> posts;

    protected BlogPostList() {
    }

    public BlogPostList(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BlogPost> getItems() {
        return posts;
    }
}
