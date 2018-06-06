package mcb.blogs.user;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import mcb.blogs.authentication.JwtAuthenticationToken;
import mcb.blogs.post.PostEntity;
import mcb.blogs.post.restmodel.CreatePostRequest;
import mcb.blogs.user.restmodel.CreateUserRequest;
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
@RequestMapping("/users")
public class UserService {
    private EntityDataStore<Persistable> dataStore;

    @Autowired
    public UserService(EntityDataStore<Persistable> dataStore) {
        this.dataStore = dataStore;
    }

    @PostMapping
    public ResponseEntity createUser(JwtAuthenticationToken token, @RequestBody CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        dataStore.insert(user);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }

    @GetMapping
    public Mono<ResponseEntity> allUsers() {
        return Mono.just(ResponseEntity.ok(this.dataStore.select(PostEntity.class).get().toList()));
    }
}
