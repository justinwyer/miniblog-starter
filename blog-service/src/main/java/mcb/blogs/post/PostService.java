package mcb.blogs.post;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import mcb.blogs.authentication.JwtAuthenticationToken;
import mcb.blogs.post.restmodel.CreatePostRequest;
import mcb.blogs.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.net.URI;

@Controller
@RequestMapping("/posts")
public class PostService {
    private EntityDataStore<Persistable> dataStore;

    @Autowired
    public PostService(EntityDataStore<Persistable> dataStore) {
        this.dataStore = dataStore;
    }

    @PostMapping
    public ResponseEntity createPost(JwtAuthenticationToken token, @RequestBody CreatePostRequest request) {
        var username = token.getDetails().getSubject();
        var user = dataStore.select(UserEntity.class).where(UserEntity.USERNAME.eq(username));
        PostEntity post = new PostEntity();
//        post.setUser(user.get());
        post.setBody(request.getBody());
        dataStore.insert(post);
        return ResponseEntity.created(URI.create("/posts/" + post.getId())).build();
    }

    @GetMapping
    public Mono<ResponseEntity> allPosts() {
        return Mono.just(ResponseEntity.ok(this.dataStore.select(PostEntity.class).get().toList()));
    }
}
