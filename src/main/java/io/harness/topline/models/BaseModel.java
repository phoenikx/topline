package io.harness.topline.models;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class BaseModel<U> {
  @CreatedBy @Indexed private U createdBy;

  @CreatedDate @Indexed private Date createdDate;

  @LastModifiedBy private U lastModifiedBy;

  @LastModifiedDate private Date lastModifiedDate;
}