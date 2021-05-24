package com.abbieschenk.ludobaum.nodeattribute;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Abstract request class for updating {@link NodeAttribute}s.
 *
 * @author abbie
 * @author abbie
 */
public abstract class NodeAttributeUpdateRequest {

    @NotNull
    @Size(max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
