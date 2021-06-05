package com.abbieschenk.ludobaum.nodeattribute;

import com.abbieschenk.ludobaum.node.Node;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "attribute_type")
public abstract class NodeAttribute implements Comparable<NodeAttribute> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id")
	private Node node;

	private Long sortOrder;

	private String title;

	public abstract String getType();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof NodeAttribute)) {
			return false;
		}

		NodeAttribute attribute = (NodeAttribute) o;

		return Objects.equals(this.id, attribute.id) && Objects.equals(this.title, attribute.title)
				&& Objects.equals(this.node, attribute.node) && Objects.equals(this.sortOrder, attribute.sortOrder);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title, this.node, this.sortOrder);
	}

	protected String getAttributeString() {
		return "id=" + this.id + ", title='" + this.title + "', node=" + this.node + ", sort-order=" + this.sortOrder;
	}

	public int compareTo(NodeAttribute other) {
		return this.sortOrder.compareTo(other.sortOrder);
	}
}
