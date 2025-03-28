/*
 * Copyright 2022 Prasad Madusanka Basnayaka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.upwork.prototype.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import javax.ws.rs.BadRequestException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Response Wrapper
 *
 * @author prasadm
 * @since 29 May 2022
 */

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( { "status", "error", "pageData", "data", "version" } )
public class ResponseWrapper<T>
{
    private static final String API_ERROR_CODE = "API Error Code : ";
    private static final String API_ERROR_CODE_SEPARATOR = " - ";
    private static final String API_VERSION = "v1.0";

    @Getter
    @Setter
    private PageData pageData = new PageData();

    @Getter
    @Setter
    private List<T> data;

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private Error error;

    @Getter
    @Setter
    private HttpStatus httpStatus;

    @Getter
    @Setter
    private String version = API_VERSION;

    public ResponseWrapper( Response<T> response )
    {
        if( response.getResponseError() != null )
        {
            onError( response.getResponseError() );
        }
        else
        {
            this.status = new Status();
            if( response.getStatus() != null )
            {
                this.status.code = response.getStatus().code;
                this.status.message = response.getStatus().message;
            }
            this.status.description = response.getMessage();
            this.httpStatus = response.getHttpStatus();
            this.data = response.getData();
            this.pageData = response.getPageData();
        }

    }

    public ResponseWrapper( Exception exception )
    {
        onError( exception );
    }

    private static void addErrorDescription( APIError errorCode, List<String> errors )
    {
        errors.add( API_ERROR_CODE + errorCode.code + API_ERROR_CODE_SEPARATOR + errorCode.details );
    }

    private void onError( Exception exception )
    {
        ResponseError responseError;
        if( !( exception instanceof ResponseError ) )
        {
            responseError = new ResponseError( exception );
        }
        else
        {
            responseError = ( ResponseError ) exception;
        }
        this.status = new Status();
        if( responseError.getStatus() != null )
        {
            this.status.code = responseError.getStatus().code;
            this.status.message = responseError.getStatus().message;
        }
        this.status.description = responseError.getMessage();
        this.httpStatus = responseError.getHttpStatus();
        this.pageData = null;
        this.data = null;
        fillErrorInfo( responseError );
    }

    private void fillErrorInfo( ResponseError responseError )
    {
        mapErrorCode( responseError );
    }

    private void mapErrorCode( ResponseError responseError )
    {
        Error newError = new Error();
        long errorCode = ( long ) responseError.getErrorSource().code;
        errorCode = errorCode * 10L + ( long ) responseError.getErrorLayer().code;
        if( responseError.getApiError() == null && responseError.getException() != null )
        {
            Exception ex = responseError.getException();
            ex.printStackTrace();

            Throwable cause = ex.getCause();
            if( cause != null )
            {
                if( cause instanceof SQLException )
                {
                    responseError.setApiError( APIError.SAVING_SQL_EXCEPTION );
                    responseError.setMessage( APIError.SAVING_SQL_EXCEPTION.message );
                }
                else if( cause instanceof RemoteException )
                {
                    responseError.setApiError( APIError.REMOTE_EXCEPTION );
                    responseError.setMessage( APIError.REMOTE_EXCEPTION.message );
                }
                else if( cause instanceof NullPointerException )
                {
                    responseError.setApiError( APIError.NULL_POINTER_EXCEPTION );
                    responseError.setMessage( APIError.NULL_POINTER_EXCEPTION.message );
                }
                else if( cause instanceof NumberFormatException )
                {
                    responseError.setApiError( APIError.NUMBER_FORMAT_EXCEPTION );
                    responseError.setMessage( APIError.NUMBER_FORMAT_EXCEPTION.message );
                }
                else if( cause instanceof IllegalArgumentException )
                {
                    responseError.setApiError( APIError.ILLEGAL_ARGUMENT_EXCEPTION );
                    responseError.setMessage( APIError.ILLEGAL_ARGUMENT_EXCEPTION.message );
                }
                else if( cause instanceof IndexOutOfBoundsException )
                {
                    responseError.setApiError( APIError.INDEX_OUT_OF_BOUNDS_EXCEPTION );
                    responseError.setMessage( APIError.INDEX_OUT_OF_BOUNDS_EXCEPTION.message );
                }
                else if( cause instanceof NoSuchMethodException )
                {
                    responseError.setApiError( APIError.NO_SUCH_METHOD_EXCEPTION );
                    responseError.setMessage( APIError.NO_SUCH_METHOD_EXCEPTION.message );
                }
                else if( cause instanceof IllegalAccessException )
                {
                    responseError.setApiError( APIError.ILLEGAL_ACCESS_EXCEPTION );
                    responseError.setMessage( APIError.ILLEGAL_ACCESS_EXCEPTION.message );
                }
                else if( cause instanceof InvocationTargetException )
                {
                    responseError.setApiError( APIError.INVOCATION_TARGET_EXCEPTION );
                    responseError.setMessage( APIError.INVOCATION_TARGET_EXCEPTION.message );
                }
                else if( cause instanceof BadRequestException )
                {
                    responseError.setApiError( APIError.BAD_REQUEST_EXCEPTION );
                    responseError.setMessage( APIError.BAD_REQUEST_EXCEPTION.message );
                }
                else if( cause instanceof InvalidDataAccessResourceUsageException )
                {
                    responseError.setApiError( APIError.INVALID_DATA_ACCESS_RESOURCE_EXCEPTION );
                    responseError.setMessage( APIError.INVALID_DATA_ACCESS_RESOURCE_EXCEPTION.message );
                }
                else
                {
                    responseError.setApiError( APIError.UNKNOWN_EXCEPTION );
                    responseError.setMessage( APIError.UNKNOWN_EXCEPTION.message );
                }
            }
        }

        errorCode = errorCode * 10000L + ( long ) ( responseError.getApiError() == null ? 0 : responseError.getApiError().code );
        List<String> errors = new ArrayList<>( 2 );
        if( responseError.getApiError() != null && responseError.getApiError().code > 0 )
        {
            addErrorDescription( responseError.getApiError(), errors );
            newError.setDetails( responseError.getApiError().details );
            newError.setMessage( responseError.getApiError().message );
            if( this.status != null && ( this.status.description == null || this.status.description.isEmpty() ) )
            {
                this.status.description = responseError.getApiError().message;
            }
        }

        newError.setErrors( errors );
        newError.setCode( errorCode );
        this.setError( newError );
    }

    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Getter
    @Setter
    private static class Status
    {
        int code;
        String message;
        String description;
    }
}
