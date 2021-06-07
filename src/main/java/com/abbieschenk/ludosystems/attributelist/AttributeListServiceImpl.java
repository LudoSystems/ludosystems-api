package com.abbieschenk.ludosystems.attributelist;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AttributeListService}.
 * 
 * @author abbie
 *
 */
@Service
@Transactional(readOnly = true)
public class AttributeListServiceImpl implements AttributeListService {

	private final AttributeListRepository repository;

	public AttributeListServiceImpl(AttributeListRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<AttributeList> getAttributeLists() {
		return repository.findAllAndLoad();
	}

	@Override
	public AttributeList getAttributeList(Long id) {
		return repository.findByIdAndLoad(id).orElseThrow(() -> new AttributeListNotFoundException(id));
	}

	@Override
	public void deleteAttributeList(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public AttributeList addAttributeList(AttributeList attributeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeList replaceAttributeList(AttributeList attributeList, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
