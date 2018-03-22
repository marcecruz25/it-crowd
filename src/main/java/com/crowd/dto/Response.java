package com.crowd.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by marce on 3/21/18.
 */
@Data
@Builder
public class Response<T> {
    private String status;
    private String message;
    private T details;
}
