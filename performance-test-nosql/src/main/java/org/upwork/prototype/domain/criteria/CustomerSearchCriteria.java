package org.upwork.prototype.domain.criteria;

import lombok.Getter;
import lombok.Setter;
import org.upwork.prototype.domain.enums.SortDirection;

import java.util.List;

/**
 * Customer Search Criteria
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
public class CustomerSearchCriteria
{
    private List<Long> ids;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private int start;
    private int page;
    private int size;
    private String sortBy;
    private SortDirection sortDirection;

    public CustomerSearchCriteria()
    {
        this.size = 10;
        this.page = 0;
        this.start = 0;
    }

    public CustomerSearchCriteria( int start, int page, int size, String sortBy, SortDirection sortDirection )
    {
        this.start = start;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
}
