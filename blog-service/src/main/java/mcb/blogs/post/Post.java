package mcb.blogs.post;

public class Post {
    private final Number id;
    private String body;
    private Long userId;

    public Post(Number id, String body, Long userId) {
        this.id = id;
        this.body = body;
        this.userId = userId;
    }

    public Number getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Long getUserId() {
        return userId;
    }
}
