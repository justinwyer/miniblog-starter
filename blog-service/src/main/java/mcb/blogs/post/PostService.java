package mcb.blogs.post;

import mcb.blogs.authentication.JwtAuthenticationToken;
import mcb.blogs.post.restmodel.CreatePostRequest;
import mcb.blogs.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/posts")
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity createPost(JwtAuthenticationToken token, @RequestBody CreatePostRequest request) {
        return userRepository.getByUsername(token.getDetails().getSubject())
                .map(user -> {
                    var post = postRepository.create(user.getId(), request.getBody());
                    return ResponseEntity.created(URI.create("/posts/" + post.getId())).build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity allPosts() {
        return ResponseEntity.ok(postRepository.getAll());
    }
}
