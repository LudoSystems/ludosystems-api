package com.abbieschenk.ludobaum.nodeattribute.textattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeNotFoundException;
import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeService;
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

        attribute.setName(updateRequest.getName());
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

}
