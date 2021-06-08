package io.harness.topline.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointOfContact {
    private String name;
    private String email;
}
