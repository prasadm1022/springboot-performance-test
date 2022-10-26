package org.upwork.prototype.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Page Data
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageData
{
    private int count;
    private int total;
}
