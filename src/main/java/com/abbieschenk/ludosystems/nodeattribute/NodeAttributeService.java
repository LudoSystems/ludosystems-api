package com.abbieschenk.ludosystems.nodeattribute;

import com.abbieschenk.ludosystems.node.Node;

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

    /**
     * Create and add a {@link NodeAttribute} to a {@link Node} based on its id. Gets inserted at the end.
     *
     * @param node The {@link Node} to create an attribute on.
     * @return The newly created and added {@link NodeAttribute}
     */
    T createAttribute(Node node);

    /**
     * Delete a {@link NodeAttribute} by id.
     *
     * @param id The id of the {@link NodeAttribute} to delete.
     */
    void deleteAttribute(Long id);
}
