package com.abbieschenk.ludobaum.node;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttribute;
import com.abbieschenk.ludobaum.user.LudobaumUser;
import com.fasterxml.jackson.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

/**
 * Represents a node entity in the Ludobaum graph, and in the associated
 * database.
 * <p>
 * Note that {@link #attributes}, {@link #children}, and {@link #parents} are
 * all lazy-loaded. This means the general way to access Nodes should be through
 * a {@link NodeService}.
 *
 * @author abbie
 */
@Entity
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long posX;
    private Long posY;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private LudobaumUser user;

    @JsonManagedReference
    @OneToMany(mappedBy = "node", fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<NodeAttribute> attributes = new ArrayList<>();

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "node_connection", joinColumns = {@JoinColumn(name = "head_node_id")}, inverseJoinColumns = {
            @JoinColumn(name = "tail_node_id")})
    private Set<Node> children = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "children", fetch = FetchType.LAZY)
    private Set<Node> parents = new HashSet<>();

    public Node() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosX() {
        return posX;
    }

    public void setPosX(Long posX) {
        this.posX = posX;
    }

    public Long getPosY() {
        return posY;
    }

    public void setPosY(Long posY) {
        this.posY = posY;
    }

    /**
     * Gets the attributes in the Ludobaum Node graph mapped by the node_connection
     * table. Note that these are lazy-loaded and must be dealt with accordingly,
     * likely with a {@link Transactional} annotation on the method.
     *
     * @return The children of this node.
     */
    public List<NodeAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<NodeAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the children in the Ludobaum Node graph mapped by the node_connection
     * table. Note that these are lazy-loaded and must be dealt with accordingly,
     * likely with a {@link Transactional} annotation on the method.
     *
     * @return The children of this node.
     */
    public Set<Node> getChildren() {
        return children;
    }

    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    /**
     * Add a new child {@link Node} to this {@link Node}.
     *
     * @param node The child {@link Node} to add.
     */
    public void addChild(Node node) {
        this.children.add(node);
    }

    /**
     * Gets the parents in the Ludobaum Node graph mapped by the node_connection
     * table. Note that these are lazy-loaded and must be dealt with accordingly.
     *
     * @return The parents of this node.
     */
    public Set<Node> getParents() {
        return parents;
    }

    public void setParents(Set<Node> parents) {
        this.parents = parents;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Node)) {
            return false;
        }

        Node node = (Node) o;

        return Objects.equals(this.id, node.id) &&
                Objects.equals(this.getPosX(), node.getPosX()) &&
                Objects.equals(this.getPosY(), node.getPosY()) &&
                Objects.equals(this.getUser(), node.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.getPosX(), this.getPosY(), this.getUser());
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + this.id +
                ", pos-x=" + this.getPosX() +
                ", pos-y=" + this.getPosY() +
                ", user=" + this.getUser() +
                "}";
    }

    public LudobaumUser getUser() {
        return user;
    }

    public void setUser(LudobaumUser user) {
        this.user = user;
    }
}
