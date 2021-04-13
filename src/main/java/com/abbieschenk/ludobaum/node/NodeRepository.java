package com.abbieschenk.ludobaum.node;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * A {@link JpaRepository} for {@link Node} entities.
 * 
 * @author abbie
 *
 */
interface NodeRepository extends JpaRepository<Node, Long> {

	/**
	 * Find a {@link Node} by its ID in the database, and load all of its
	 * lazy-loaded collections.
	 * 
	 * @param id The id of the {@link Node}
	 * @return The {@link Node} wrapped in an {@link Optional}
	 */
	@Query("SELECT n " //
			+ "FROM Node n " //
			+ "JOIN FETCH n.attributes AS a " //
			+ "JOIN FETCH n.children " //
			+ "JOIN FETCH a.list l " //
			+ "JOIN FETCH l.elements " //
			+ "WHERE n.id = (:id)")
	Optional<Node> findByIdAndLoad(Long id);

	/**
	 * Find all {@link Node}s in the database, and load all of their lazy-loaded
	 * collections.
	 * 
	 * @return The {@link Node}s in the database.
	 */
	@Query("SELECT DISTINCT n " //
			+ "FROM Node n " //
			+ "LEFT JOIN FETCH n.attributes AS a " //
			+ "LEFT JOIN FETCH n.children " //
			+ "LEFT JOIN FETCH a.list l " //
			+ "LEFT JOIN FETCH l.elements")
	Set<Node> findAllAndLoad();

}
