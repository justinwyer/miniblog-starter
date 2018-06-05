package mcb.blogs.publisher;

import mcb.blogs.publisher.restmodel.CreateUserRequest;
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
public class BlogService {
    private UserListRepository repository;

    @Autowired
    public BlogService(UserListRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Mono<ResponseEntity> createUser(@RequestBody Mono<CreateUserRequest> request) {
        return request.map(r -> this.repository.save(new UserList(r.getName())))
                .map(UserList::getId)
                .map(id -> ResponseEntity.created(URI.create("/users/" + id)).build());
    }

    @GetMapping
    public ResponseEntity fetchTodoList() {
        return ResponseEntity.ok(repository.findAll());
    }
}