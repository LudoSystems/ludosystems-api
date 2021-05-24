package com.abbieschenk.ludobaum.node;

import java.util.Set;

/**
 * Service used to retrieve and interact with {@link Node} entities.
 *
 * @author abbie
 */
public interface NodeService {

    /**
     * Retrieve all {@link Node}s.
     *
     * @return A list of all {@link Node}s.
     */
    Set<Node> getNodes();

    /**
     * Retrieve all root {@link Node}s without parents.
     *
     * @return A list of all root {@link Node}s.
     */
    Set<Node> getRoots();

    /**
     * Retrieve a {@link Node} by its ID.
     *
     * @param id The ID of the {@link Node} to find.
     * @return The {@link Node}.
     */
    Node getNode(Long id);

    /**
     * Delete the {@link Node} by its ID.
     *
     * @param id The ID of the {@link Node} to delete.
     */
    void deleteNode(Long id);

    /**
     * Add a new {@link Node} and persist it to the database.
     *
     * @param node The {@link Node} to save to the database
     * @return The saved {@link Node}
     */
    Node addNode(Node node);

    /**
     * Replace an existing {@link Node} with a new Node by its ID. If no Node with the ID exists, this will add the
     * Node as a new Node with the provided ID.
     *
     * @param node The new {@link Node}
     * @param id   The existing {@link Node}'s ID
     * @return The replaced {@link Node}
     */
    Node replaceNode(Node node, Long id);

    /**
     * Update the x and y position of a {@link Node}
     *
     * @param id   The id of the {@link Node} to update.
     * @param posX The new x position of the {@link Node}. If this is null, the position will not be updated. If this is
     *             not desired behaviour, it should be handled elsewhere, such as in the request body.
     * @param posY The new y position of the {@link Node}. If this is null, the position will not be updated. If this is
     *             not desired behaviour, it should be handled elsewhere, such as in the request body.
     * @return The node that was updated.
     */
    Node updateNodePosition(Long id, Long posX, Long posY);

    /**
     * Connect the parent {@link Node} to the child {@link Node}.
     *
     * @param id      The id of the parent {@link Node}.
     * @param childId The id of the child {@link Node}.
     * @return The parent {@link Node} that a child was connected to.
     */
    Node connectNodes(Long id, Long childId);

    /**
     * Disconnect the parent {@link Node} from the child {@link Node}.
     *
     * @param id      The id of the parent {@link Node}.
     * @param childId The id of the child {@link Node}.
     * @return The parent {@link Node} that a child was disconnected from.
     */
    Node disconnectNodes(Long id, Long childId);
}
