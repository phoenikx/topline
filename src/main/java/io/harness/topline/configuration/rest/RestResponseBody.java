package io.harness.topline.configuration.rest;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RestResponseBody {
    private Object data;
    private String path;
    private String requestId;
}
