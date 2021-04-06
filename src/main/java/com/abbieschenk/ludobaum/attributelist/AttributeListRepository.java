package com.abbieschenk.ludobaum.attributelist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abbieschenk.ludobaum.attributelistelement.AttributeListElement;

/**
 * A {@link JpaRepository} for {@link AttributeList} entities.
 * 
 * @author abbie
 *
 */
interface AttributeListRepository extends JpaRepository<AttributeList, Long> {

	/**
	 * Find an {@link AttributeList} by its ID in the database, and load all of its
	 * lazy-loaded {@link AttributeListElement}s.
	 * 
	 * @param id The id of the {@link AttributeList}
	 * @return The {@link AttributeList} wrapped in an {@link Optional}
	 */
	@Query("SELECT a " //
			+ "FROM AttributeList a " //
			+ "JOIN FETCH a.elements " //
			+ "WHERE a.id = (:id)")
	public Optional<AttributeList> findByIdAndLoad(Long id);

	/**
	 * Find all {@link AttributeList}s in the database, and load all of their
	 * lazy-loaded {@link AttributeListElement}s.
	 * 
	 * @return The {@link AttributeList}s in the database.
	 */
	@Query("SELECT a " //
			+ "FROM AttributeList a " //
			+ "JOIN FETCH a.elements")
	public List<AttributeList> findAllAndLoad();

}
