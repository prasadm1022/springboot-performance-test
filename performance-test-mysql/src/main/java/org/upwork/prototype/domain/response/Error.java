package org.upwork.prototype.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
public class Error
{
    private long code;
    private String message;
    private String details;
    private List<String> errors;

    public Error()
    {
        errors = new ArrayList<>();
    }
}
