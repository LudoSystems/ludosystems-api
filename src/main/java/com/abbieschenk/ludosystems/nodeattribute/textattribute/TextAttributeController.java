package com.abbieschenk.ludosystems.nodeattribute.textattribute;

import com.abbieschenk.ludosystems.nodeattribute.NodeAttributeController;
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
