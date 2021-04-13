package com.abbieschenk.ludobaum.node;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for {@link Node} entities.
 *
 * @author abbie
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping(NodeController.PATH)
@RestController
class NodeController {
    private final NodeService service;
    private final NodeModelAssembler assembler;

    public static final String REL = "nodes";
    public static final String PATH = "/" + REL;

    private static final String PATH_ID = PATH + "/{id}";

    public NodeController(NodeService service, NodeModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Node>> all() {
        List<EntityModel<Node>> nodes;

        nodes = service.getNodes().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(nodes,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).all()).withSelfRel());
    }

    @PostMapping("/add")
    public ResponseEntity<?> newNode(@RequestBody Node node) {
        EntityModel<Node> entityModel = assembler.toModel(service.addNode(node));

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping(PATH_ID)
    public EntityModel<Node> one(@PathVariable Long id) {
        return assembler.toModel(service.getNode(id));
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<?> replaceNode(@RequestBody Node node, @PathVariable Long id) {
        EntityModel<Node> entityModel = assembler.toModel(service.replaceNode(node, id));

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        service.deleteNode(id);

        return ResponseEntity.noContent().build();
    }
}
