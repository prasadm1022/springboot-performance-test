package org.upwork.prototype.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Database Sequence
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
@Document( collection = "database_sequences" )
public class DatabaseSequence
{
    @Id
    private String id;
    private long seq;
}
