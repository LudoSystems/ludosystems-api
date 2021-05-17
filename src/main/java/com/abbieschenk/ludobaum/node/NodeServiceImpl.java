package com.abbieschenk.ludobaum.node;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Implementation of {@link NodeService}.
 *
 * @author abbie
 */
@Service
@Transactional(readOnly = true)
public class NodeServiceImpl implements NodeService {
    private final NodeRepository repository;

    public NodeServiceImpl(NodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Node> getNodes() {
        return repository.findAllAndLoad();
    }

    @Override
    public Set<Node> getRoots() {
        return repository.findRoots();
    }

    @Override
    public Node getNode(Long id) {
        final Node node = repository.findByIdAndLoad(id);

        if (node == null) {
            throw new NodeNotFoundException(id);
        }

        return node;
    }

    @Override
    @Transactional
    public void deleteNode(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Node addNode(Node node) {
        return repository.save(node);
    }

    @Override
    @Transactional
    public Node replaceNode(Node node, Long id) {
        return repository.findById(id).map(existingNode -> {
            existingNode.setPosX(node.getPosX());
            existingNode.setPosY(node.getPosY());
            existingNode.setUser(node.getUser());

            // TODO: each of the attributes may need to have its parents updated here.
            existingNode.setAttributes(node.getAttributes());
            existingNode.setChildren(node.getChildren());

            return repository.save(existingNode);
        }).orElseGet(() -> {
            node.setId(id);
            return this.addNode(node);
        });
    }

    @Override
    @Transactional
    public Node updateNodePosition(Long id, Long posX, Long posY) {
        final Node node = this.getNode(id);

        if (posX != null) {
            node.setPosX(posX);
        }

        if (posY != null) {
            node.setPosY(posY);
        }

        return repository.save(node);
    }
}
