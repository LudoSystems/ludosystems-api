package com.abbieschenk.ludobaum.node;

import com.abbieschenk.ludobaum.user.LudobaumUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
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

        final EntityModel<Node> entityModel = assembler.toModel(nodeService.addNode(node));

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping(PATH_ID)
    public EntityModel<Node> one(@PathVariable("id") Long id) {
        return assembler.toModel(nodeService.getNode(id));
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<?> replaceNode(@RequestBody Node node, @PathVariable Long id) {
        EntityModel<Node> entityModel = assembler.toModel(nodeService.replaceNode(node, id));

        return ResponseEntity.created(
                entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PatchMapping(path = PATH_ID, consumes = "application/json-patch+json")
    public ResponseEntity<?> updateNode(@RequestBody JsonPatch patch, @PathVariable Long id) {

        try {
            Node node = nodeService.getNode(id);
            Node patched = this.applyPatchToNode(patch, node);

            nodeService.updateNode(patched);

            return ResponseEntity.ok(patched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);

        return ResponseEntity.noContent().build();
    }

    private Node applyPatchToNode(JsonPatch patch, Node node) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(node, JsonNode.class));
        return objectMapper.treeToValue(patched, Node.class);
    }
}
