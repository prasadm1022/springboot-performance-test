package org.upwork.prototype.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Customer DTO
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
public class Customer
{
    private long id;
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
        if( !( o instanceof Customer ) ) return false;
        Customer that = ( Customer ) o;
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
