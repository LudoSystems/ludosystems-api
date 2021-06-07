package com.abbieschenk.ludosystems.attributelist;

import java.util.List;

/**
 * Service used to retrieve and interact with {@link AttributeList} entities.
 * 
 * @author abbie
 *
 */
public interface AttributeListService {

	/**
	 * Retrieve all {@link AttributeList}s.
	 * 
	 * @return
	 */
	public List<AttributeList> getAttributeLists();

	/**
	 * Retrive an {@link AttributeList} by its ID.
	 * 
	 * @param id The ID of the {@link AttributeList} to find
	 * @return The {@link AttributeList}
	 */
	public AttributeList getAttributeList(Long id);

	/**
	 * Delete the {@link AttributeList} by its ID.
	 * 
	 * @param id The ID of the {@link AttributeList} to delete.
	 */
	public void deleteAttributeList(Long id);

	/**
	 * Add a new {@link AttributeList} and persist it to the database.
	 * 
	 * @param attributeList The {@link AttributeList} to save to the database
	 * @return The saved {@link AttributeList}
	 */
	public AttributeList addAttributeList(AttributeList attributeList);

	/**
	 * Replace an existing {@link AttributeList} with a new AttributeList by its ID.
	 * If no AttributeList with the ID exists, this will add the AttributeList as a
	 * new AttributeList with the provided ID.
	 * 
	 * @param attributeList The new {@link AttributeList}
	 * @param id            The existing {@link AttributeList}'s ID
	 * @return The replaced {@link AttributeList}
	 */
	public AttributeList replaceAttributeList(AttributeList attributeList, Long id);
}
