package com.abbieschenk.ludobaum.nodeattribute.numberattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeNotFoundException;
import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NumberAttributeServiceImpl implements NodeAttributeService<NumberAttribute, NumberAttributeUpdateRequest> {

    private final NumberAttributeRepository repository;

    public NumberAttributeServiceImpl(NumberAttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public NumberAttribute updateAttribute(Long id, NumberAttributeUpdateRequest updateRequest) {
        NumberAttribute attribute = repository.getOne(id);

        attribute.setName(updateRequest.getName());
        attribute.setNumber(updateRequest.getNumber());

        return repository.save(attribute);
    }

    @Override
    public NumberAttribute getAttribute(Long id) {
        final NumberAttribute attribute = repository.getOne(id);

        if (attribute == null) {
            throw new NodeAttributeNotFoundException(id);
        }

        return attribute;
    }
}
