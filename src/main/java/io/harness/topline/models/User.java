package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("users")
public class User {
    @Id private String id;
    private String name;
    @Indexed(unique = true) private String email;
    private String profilePicture;
    private Boolean admin;

    public boolean isAdmin() {
        return Boolean.TRUE.equals(admin);
    }
}
