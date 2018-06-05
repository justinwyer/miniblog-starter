package mcb.blogs.publisher;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserList {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BlogPostList> posts;

    protected UserList() {
    }

    public UserList(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BlogPostList> getPosts() {
        return posts;
    }
}
