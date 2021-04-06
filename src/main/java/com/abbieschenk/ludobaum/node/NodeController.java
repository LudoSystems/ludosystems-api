package com.abbieschenk.ludobaum.node;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for {@link Node} entities.
 *
 * @author abbie
 */
@RestController
class NodeController {

    private final NodeService service;
    private final NodeModelAssembler assembler;

    static final String REL = "nodes";
    private static final String PATH = "/" + REL;
    private static final String PATH_ID = PATH + "/{id}";

    public NodeController(NodeService service, NodeModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(PATH)
    public CollectionModel<EntityModel<Node>> all() {

        List<EntityModel<Node>> nodes;

        nodes = service.getNodes().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(nodes,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).all()).withSelfRel());
    }

    @PostMapping(PATH)
    public ResponseEntity<?> newNode(@RequestBody Node node) {
        EntityModel<Node> entityModel;

        entityModel = assembler.toModel(service.addNode(node));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping(PATH_ID)
    EntityModel<Node> one(@PathVariable Long id) {
        return assembler.toModel(service.getNode(id));
    }

    @PutMapping(PATH_ID)
    ResponseEntity<?> replaceNode(@RequestBody Node node, @PathVariable Long id) {
        EntityModel<Node> entityModel = assembler.toModel(service.replaceNode(node, id));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping(PATH_ID)
    ResponseEntity<?> deleteNode(@PathVariable Long id) {
        service.deleteNode(id);

        return ResponseEntity.noContent().build();
    }
}
