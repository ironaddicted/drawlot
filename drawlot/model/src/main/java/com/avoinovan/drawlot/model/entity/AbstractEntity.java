package com.avoinovan.drawlot.model.entity;

import org.springframework.data.annotation.Id;

/**
 * @author by avoinovan
 */
public class AbstractEntity {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
