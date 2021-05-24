package com.abbieschenk.ludobaum.nodeattribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

public abstract class NodeAttributeController<U extends NodeAttributeUpdateRequest> {

    protected static final String PATH = "/attributes";
    private static final String PATH_ID = "/{id}";

    @Autowired
    private NodeAttributeModelAssembler assembler;

    private final NodeAttributeService attributeService;

    public NodeAttributeController(NodeAttributeService attributeService) {
        this.attributeService = attributeService;
        Assert.notNull(this.attributeService, "attributeService must be set.");
    }

    @GetMapping(PATH_ID)
    public EntityModel<NodeAttribute> one(@PathVariable("id") Long id) {
        return assembler.toModel(attributeService.getAttribute(id));
    }

    @PatchMapping(PATH_ID)
    public ResponseEntity updateAttribute(@PathVariable Long id, @RequestBody U updateRequest) {
        return this.createResponseEntity(attributeService.updateAttribute(id, updateRequest));
    }

    protected ResponseEntity<?> createResponseEntity(NodeAttribute attribute) {
        final EntityModel<NodeAttribute> entityModel = assembler.toModel(attribute);

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
