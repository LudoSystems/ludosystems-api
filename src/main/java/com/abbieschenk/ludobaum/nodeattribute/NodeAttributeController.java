package com.abbieschenk.ludobaum.nodeattribute;

import com.abbieschenk.ludobaum.node.Node;
import com.abbieschenk.ludobaum.node.NodeService;
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

    @Autowired
    private NodeService nodeService;

    private final NodeAttributeService attributeService;

    public NodeAttributeController(NodeAttributeService attributeService) {
        this.attributeService = attributeService;
        Assert.notNull(this.attributeService, "attributeService must be set.");
    }

    @GetMapping(PATH_ID)
    public EntityModel<NodeAttribute> one(@PathVariable("id") Long id) {
        return assembler.toModel(attributeService.getAttribute(id));
    }

    // I'm using /create instead of /add because we're letting the app build the new attribute for a node, rather
    // than adding an attribute based on the request body — which we might want to make possible in the future.
    @PostMapping("/create/{nodeId}")
    public ResponseEntity<?> newAttribute(@PathVariable("nodeId") Long nodeId) {
        final Node node = nodeService.getNode(nodeId);

        return this.createResponseEntity(attributeService.createAttribute(node));
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

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteAttribute(@PathVariable Long id) {
        attributeService.deleteAttribute(id);

        return ResponseEntity.noContent().build();
    }
}
