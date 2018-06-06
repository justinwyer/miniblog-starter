package mcb.blogs.post;


import io.requery.Column;
import io.requery.Entity;
import io.requery.ForeignKey;
import io.requery.ManyToOne;
import mcb.blogs.user.User;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(model = "blog")
public interface Post {
    @Id
    @GeneratedValue
    Long getId();

    String getBody();

    @ManyToOne
    User getUser();

    String getUserId();
}
