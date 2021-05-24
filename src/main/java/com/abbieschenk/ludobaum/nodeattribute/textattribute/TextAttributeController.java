package com.abbieschenk.ludobaum.nodeattribute.textattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TextAttributeController.PATH)
public class TextAttributeController extends NodeAttributeController<TextAttributeUpdateRequest> {

    public static final String PATH = NodeAttributeController.PATH + "/text";

    public TextAttributeController(TextAttributeServiceImpl attributeService) {
        super(attributeService);
    }
}
