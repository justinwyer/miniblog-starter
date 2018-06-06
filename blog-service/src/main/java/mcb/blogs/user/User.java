package mcb.blogs.user;

import io.requery.Entity;
import io.requery.OneToMany;
import io.requery.query.Result;
import mcb.blogs.post.Post;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity(model = "blog")
public interface User {
    @Id
    @GeneratedValue
    Long getId();

    String getUsername();

    @OneToMany
    Result<Post> getPosts();
}
