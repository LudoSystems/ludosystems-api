package com.abbieschenk.ludosystems.node;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.Set;

/**
 * A {@link JpaRepository} for {@link Node} entities.
 * <p>
 * TODO Annoyingly, Spring does not support {@link Optional} in Spring Expression Language. If it ever does, the
 * returned values below should be updated as appropriate.
 *
 * @author abbie
 */
@PreAuthorize("hasAuthority('USER')")
interface NodeRepository extends JpaRepository<Node, Long> {

    @Override
    @PostAuthorize("returnObject.user?.name == authentication.name")
    Node getOne(Long id);

    /**
     * Find a {@link Node} by its ID in the database, and load all of its
     * lazy-loaded collections.
     *
     * @param id The id of the {@link Node}
     * @return The {@link Node} wrapped in an {@link Optional}
     */
    @Query("SELECT n " //
            + "FROM Node n " //
            + "LEFT JOIN FETCH n.attributes AS a " //
            + "LEFT JOIN FETCH n.children " //
            + "LEFT JOIN FETCH a.list l " //
            + "LEFT JOIN FETCH l.elements " //
            + "WHERE n.id = (:id)")
    @PostAuthorize("returnObject.user?.name == authentication.name")
    Node findByIdAndLoad(@Param("id") Long id);

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
    @PostFilter("filterObject.user?.name == authentication.name")
    Set<Node> findAllAndLoad();

    /**
     * Find all root {@link Node}s in the database, i.e. those without any
     * parents, or without any node_connections.
     *
     * @return A set of root {@link Node}s.
     */
    @Query("SELECT DISTINCT n " //
            + "FROM Node n " //
            + "LEFT JOIN FETCH n.attributes AS a " //
            + "LEFT JOIN FETCH n.children " //
            + "LEFT JOIN FETCH a.list l " //
            + "LEFT JOIN FETCH l.elements " //
            + "WHERE n.parents IS EMPTY")
    @PostFilter("filterObject.user?.name == authentication.name")
    Set<Node> findRoots();

    @Override
    @PreAuthorize("#node?.user?.name == authentication?.name")
    void delete(@Param("node") Node node);

    @Override
    @PreAuthorize("#node?.user?.name == authentication?.name")
    Node save(@Param("node") Node node);

    @Override
    @PreAuthorize("@nodeRepository.getOne(#id).user?.name == authentication?.name")
    void deleteById(@Param("id") Long id);
}
