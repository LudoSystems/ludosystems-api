package com.abbieschenk.ludobaum.nodeattribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RequestMapping(NodeAttributeController.PATH)
public abstract class NodeAttributeController<T extends NodeAttribute, U extends NodeAttributeUpdateRequest> {
    private final NodeAttributeService attributeService;

    // TODO ugly
    @Autowired
    private NodeAttributeModelAssembler assembler;

    public static final String REL = "attributes";
    public static final String PATH = "/" + REL;

    private static final String PATH_ID = "/{id}";

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
