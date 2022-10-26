package org.upwork.prototype.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * Customer Entity
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
@Document( collection = "OP_CUSTOMER" )
public class OpCustomer implements Serializable
{
    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private String country;

    @Override
    public boolean equals( Object o )
    {
        if( this == o ) return true;
        if( !( o instanceof OpCustomer ) ) return false;
        OpCustomer that = ( OpCustomer ) o;
        return Objects.equals( id, that.getId() )
                       && Objects.equals( this.firstName, that.getFirstName() )
                       && Objects.equals( this.lastName, that.getLastName() )
                       && Objects.equals( this.address, that.getAddress() )
                       && Objects.equals( this.zipCode, that.getZipCode() )
                       && Objects.equals( this.city, that.getCity() )
                       && Objects.equals( this.country, that.getCountry() );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( this.id, this.firstName, this.lastName, this.address, this.zipCode, this.city, this.country );
    }
}
