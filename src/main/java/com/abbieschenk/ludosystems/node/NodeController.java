package com.abbieschenk.ludosystems.node;

import com.abbieschenk.ludosystems.user.LudoSystemsUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private final LudoSystemsUserService userService;
    private final NodeModelAssembler assembler;

    public static final String REL = "nodes";
    public static final String PATH = "/" + REL;

    private static final String PATH_ID = "/{id}";

    public NodeController(NodeService nodeService,
                          LudoSystemsUserService userService,
                          NodeModelAssembler assembler) {
        this.nodeService = nodeService;
        this.userService = userService;
        this.assembler = assembler;

        this.objectMapper = new ObjectMapper();
    }

    @GetMapping(PATH_ID)
    public EntityModel<Node> one(@PathVariable("id") Long id) {
        return assembler.toModel(nodeService.getNode(id));
    }

    @GetMapping()
    public CollectionModel<EntityModel<Node>> all() {
        final List<EntityModel<Node>> nodes;

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

    @GetMapping("/export-json")
    public ResponseEntity<?> exportJson() {
        final Set<Node> nodes = nodeService.getRoots();

        final List<byte[]> nodeBytes = new ArrayList<>();

        int arraySize = 0;
        for(Node node : nodes) {
            try {
                final byte[] mapped = objectMapper.writeValueAsBytes(node);

                nodeBytes.add(mapped);
                arraySize += mapped.length;

            } catch (JsonProcessingException e) {
                throw new RuntimeException("Could not write value of " + node + " to JSON");
            }
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(arraySize);

        final byte[] openingTag = "{ \"ludoNodes\":[".getBytes();
        final byte[] comma = ",".getBytes();
        final byte[] closingTag = "]}".getBytes();

        baos.write(openingTag, 0, openingTag.length);

        for (byte[] node : nodeBytes) {
            baos.write(node, 0, node.length);
            baos.write(comma, 0, comma.length);
        }

        baos.write(closingTag, 0, closingTag.length);

        final byte[] bytes = baos.toByteArray();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=ludo-nodes.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(bytes.length)
                .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
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

    // @PutMapping(PATH_ID)
    // TODO This is currently disabled as it has been neither used nor tested.
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


