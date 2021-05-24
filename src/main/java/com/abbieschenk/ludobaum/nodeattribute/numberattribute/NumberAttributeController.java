package com.abbieschenk.ludobaum.nodeattribute.numberattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NumberAttributeController.PATH)
public class NumberAttributeController extends NodeAttributeController<NumberAttributeUpdateRequest> {

    public static final String PATH = NodeAttributeController.PATH + "/number";

    public NumberAttributeController(NumberAttributeServiceImpl attributeService) {
        super(attributeService);
    }
}