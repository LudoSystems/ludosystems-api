package com.abbieschenk.ludobaum.nodeattribute;

import java.util.SortedSet;

public interface NodeAttributeService<T extends NodeAttribute, U extends NodeAttributeUpdateRequest> {

    /**
     * Update the {@link NodeAttribute} by processing the sent update request that implements
     * {@link NodeAttributeUpdateRequest}, as inferred.
     *
     * @param id            The id of the {@link NodeAttribute} to update.
     * @param updateRequest The {@link NodeAttributeUpdateRequest} implementation.
     * @return The updated {@link NodeAttribute}.
     */
    T updateAttribute(Long id, U updateRequest);

    /**
     * Retrieve a {@link NodeAttribute} by its ID.
     *
     * @param id The ID of the {@link NodeAttribute} to find.
     * @return The {@link NodeAttribute}.
     */
    T getAttribute(Long id);

}
