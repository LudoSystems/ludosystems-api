package com.abbieschenk.ludobaum.node;

import java.util.Set;

/**
 * Service used to retrieve and interact with {@link Node} entities.
 * 
 * @author abbie
 *
 */
public interface NodeService {

	/**
	 * Retrieve all {@link Node}s.
	 * 
	 * @return A list of all {@link Node}s.
	 */
	Set<Node> getNodes();

	/**
	 * Retrieve a {@link Node} by its ID.
	 * 
	 * @param id The ID of the {@link Node} to find
	 * @return The {@link Node}
	 * 
	 */
	Node getNode(Long id);

	/**
	 * Delete the {@link Node} by its ID.
	 * 
	 * @param id The ID of the {@link Node} to delete
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
	 * Replace an existing {@link Node} with a new Node by its ID. If no Node with
	 * the ID exists, this will add the Node as a new Node with the provided ID.
	 * 
	 * @param node The new {@link Node}
	 * @param id   The existing {@link Node}'s ID
	 * @return The replaced {@link Node}
	 */
	Node replaceNode(Node node, Long id);
}
