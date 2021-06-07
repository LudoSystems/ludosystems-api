package com.abbieschenk.ludosystems.nodeattribute.listattribute;

import com.abbieschenk.ludosystems.attributelist.AttributeList;
import com.abbieschenk.ludosystems.attributelistelement.AttributeListElement;
import com.abbieschenk.ludosystems.nodeattribute.NodeAttribute;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(ListAttribute.TYPE)
public class ListAttribute extends NodeAttribute {
	
	protected static final String TYPE = "LIST";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_id")
	private AttributeList list;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_element_id")
	private AttributeListElement currentElement;

	public AttributeList getList() {
		return list;
	}

	public void setList(AttributeList list) {
		this.list = list;
	}

	public AttributeListElement getCurrentElement() {
		return currentElement;
	}

	public void setCurrentElement(AttributeListElement currentElement) {
		this.currentElement = currentElement;
	}

	@Override
	public String getType() {
		return ListAttribute.TYPE;
	}

	@Override
	public boolean equals(Object o) {
		if (!(super.equals(o) || o instanceof ListAttribute)) {
			return false;
		}

		ListAttribute attribute = (ListAttribute) o;

		return Objects.equals(this.list, attribute.list)
				&& Objects.equals(this.currentElement, attribute.currentElement);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.list, this.currentElement);
	}

	@Override
	public String toString() {
		return "ListAttribute{" + this.getAttributeString() + ", list=" + this.list + ", current-element="
				+ this.currentElement + "}";
	}
}
