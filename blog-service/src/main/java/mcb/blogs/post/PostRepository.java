package mcb.blogs.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
public class PostRepository {
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Post create(Long userId, String body) {
        var insert = new SimpleJdbcInsert(this.dataSource)
                .withTableName("posts")
                .usingGeneratedKeyColumns("id");
        var parameters = new HashMap<String, Object>(2);
        parameters.put("user_id", userId);
        parameters.put("body", body);
        var id = insert.executeAndReturnKey(parameters);
        return new Post(id, body, userId);
    }

    public List<Post> getAll() {
        return new JdbcTemplate(this.dataSource)
                .query("SELECT id, body, user_id FROM posts",
                        (rs, rowNum) -> new Post(rs.getLong("id"), rs.getString("body"), rs.getLong("user_id")));
    }
}
