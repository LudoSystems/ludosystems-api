package com.abbieschenk.ludobaum.nodeattribute;

import java.util.Objects;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.abbieschenk.ludobaum.node.Node;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "attribute_type")
public abstract class NodeAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id")
	private Node node;

	private Long sortOrder;

	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

		return Objects.equals(this.id, attribute.id) && Objects.equals(this.name, attribute.name)
				&& Objects.equals(this.node, attribute.node) && Objects.equals(this.sortOrder, attribute.sortOrder);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.node, this.sortOrder);
	}

	protected String getAttributeString() {
		return "id=" + this.id + ", name='" + this.name + "', node=" + this.node + ", sort-order=" + this.sortOrder;
	}
}
