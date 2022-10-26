package org.upwork.prototype.domain.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response Error
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
public class ResponseError extends Exception
{
    private HttpStatus httpStatus;
    private Status status;
    private String message;
    private APIError apiError;
    private Exception exception;
    private ErrorLayer errorLayer;
    private ErrorSource errorSource;

    public ResponseError( Exception exception )
    {
        this.status = Status.ERROR;
        this.exception = exception;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = exception.getMessage();
        this.errorSource = ErrorSource.SERVER_ERROR;
        this.errorLayer = ErrorLayer.API_LAYER;
    }

    public ResponseError( APIError apiError )
    {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.status = Status.ERROR;
        this.apiError = apiError;
        this.errorSource = ErrorSource.CLIENT_ERROR;
        this.errorLayer = ErrorLayer.API_LAYER;
    }

    public ResponseError( ErrorSource errorSource, APIError apiError, HttpStatus httpStatus )
    {
        this.httpStatus = httpStatus;
        this.status = Status.ERROR;
        this.apiError = apiError;
        this.errorSource = errorSource;
        this.errorLayer = ErrorLayer.API_LAYER;
    }
}
