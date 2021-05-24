package com.abbieschenk.ludobaum.nodeattribute.textattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextAttributeController extends NodeAttributeController<TextAttribute, TextAttributeUpdateRequest> {

    public TextAttributeController(TextAttributeServiceImpl attributeService) {
        super(attributeService);
    }
}
