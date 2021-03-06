package com.abbieschenk.ludosystems.nodeattribute.textattribute;

import com.abbieschenk.ludosystems.node.Node;
import com.abbieschenk.ludosystems.nodeattribute.NodeAttributeNotFoundException;
import com.abbieschenk.ludosystems.nodeattribute.NodeAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TextAttributeServiceImpl implements NodeAttributeService<TextAttribute, TextAttributeUpdateRequest> {

    private final TextAttributeRepository repository;

    public TextAttributeServiceImpl(TextAttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TextAttribute updateAttribute(Long id, TextAttributeUpdateRequest updateRequest) {
        TextAttribute attribute = repository.getOne(id);

        attribute.setTitle(updateRequest.getTitle());
        attribute.setText(updateRequest.getText());

        return repository.save(attribute);
    }

    @Override
    public TextAttribute getAttribute(Long id) {
        final TextAttribute attribute = repository.getOne(id);

        if(attribute == null) {
            throw new NodeAttributeNotFoundException(id);
        }

        return attribute;
    }

    @Override
    @Transactional
    public TextAttribute createAttribute(Node node) {
        final TextAttribute attribute = new TextAttribute();

        attribute.setTitle("Text");
        attribute.setText("");
        attribute.setSortOrder(node.getMaxAttributeSortOrder() + 1);
        attribute.setNode(node);

        repository.save(attribute);

        return attribute;
    }

    @Override
    @Transactional
    public void deleteAttribute(Long id) {

        // TODO I'd like to reset the sortorders on the
        // attributes after deleting. But this would
        // also have to update in the React app

        repository.delete(repository.getOne(id));
    }
}
