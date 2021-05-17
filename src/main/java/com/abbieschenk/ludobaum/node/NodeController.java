package com.abbieschenk.ludobaum.node;

import com.abbieschenk.ludobaum.user.LudobaumUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RequestMapping(NodeController.PATH)
@RestController
class NodeController {
    private final ObjectMapper objectMapper;

    private final NodeService nodeService;
    private final LudobaumUserService userService;
    private final NodeModelAssembler assembler;

    public static final String REL = "nodes";
    public static final String PATH = "/" + REL;

    private static final String PATH_ID = "/{id}";

    public NodeController(NodeService nodeService,
                          LudobaumUserService userService,
                          NodeModelAssembler assembler) {
        this.nodeService = nodeService;
        this.userService = userService;
        this.assembler = assembler;

        this.objectMapper = new ObjectMapper();
    }

    @GetMapping()
    public CollectionModel<EntityModel<Node>> all() {
        List<EntityModel<Node>> nodes;

        nodes = nodeService.getNodes().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(nodes,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).all()).withSelfRel());
    }

    @GetMapping("/roots")
    public CollectionModel<EntityModel<Node>> roots() {
        final List<EntityModel<Node>> nodes;

        nodes = nodeService.getRoots().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(nodes,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).all()).withSelfRel());
    }

    @PostMapping("/add")
    public ResponseEntity<?> newNode(@RequestBody Node node) {
        if (node.getUser() == null) {
            node.setUser(this.userService.getCurrentUser());
        }

        return this.createResponseEntity(nodeService.addNode(node));
    }

    @PatchMapping("/connect" + PATH_ID)
    public ResponseEntity<?> connectNodes(@RequestBody Long childId, @PathVariable Long id) {
        return this.createResponseEntity(nodeService.connectNodes(id, childId));
    }

    @PatchMapping("/disconnect" + PATH_ID)
    public ResponseEntity<?> disconnectNodes(@RequestBody Long childId, @PathVariable Long id) {
        return this.createResponseEntity(nodeService.disconnectNodes(id, childId));
    }

    @GetMapping(PATH_ID)
    public EntityModel<Node> one(@PathVariable("id") Long id) {
        return assembler.toModel(nodeService.getNode(id));
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<?> replaceNode(@RequestBody Node node, @PathVariable Long id) {
        return this.createResponseEntity(nodeService.replaceNode(node, id));
    }

    @PatchMapping("/address" + PATH_ID)
    public ResponseEntity<?> updateNodeAddress(@RequestBody NodePositionUpdateRequest request, @PathVariable Long id) {
        return this.createResponseEntity(nodeService.updateNodePosition(id, request.getPosX(), request.getPosY()));
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Creates a response entity with links to itself from a single {@link Node} object.
     *
     * @param node The {@link Node} to create the response entity for.
     * @return The response entity for the {@link Node}
     */
    private ResponseEntity<?> createResponseEntity(Node node) {
        final EntityModel<Node> entityModel = assembler.toModel(node);

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}


