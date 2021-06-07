package com.abbieschenk.ludosystems.nodeattribute.numberattribute;

import com.abbieschenk.ludosystems.node.Node;
import com.abbieschenk.ludosystems.nodeattribute.NodeAttributeNotFoundException;
import com.abbieschenk.ludosystems.nodeattribute.NodeAttributeService;
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

        attribute.setTitle(updateRequest.getTitle());
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

    @Override
    @Transactional
    public NumberAttribute createAttribute(Node node) {
        final NumberAttribute attribute = new NumberAttribute();

        attribute.setTitle("Number");
        attribute.setNumber(0L);
        attribute.setSortOrder(node.getMaxAttributeSortOrder() + 1);
        attribute.setNode(node);

        repository.save(attribute);

        return attribute;
    }

    @Override
    @Transactional
    public void deleteAttribute(Long id) {
        repository.deleteById(id);
    }
}
