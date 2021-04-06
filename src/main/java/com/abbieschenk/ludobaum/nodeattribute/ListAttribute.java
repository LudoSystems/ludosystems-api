package com.abbieschenk.ludobaum.nodeattribute;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.abbieschenk.ludobaum.attributelist.AttributeList;
import com.abbieschenk.ludobaum.attributelistelement.AttributeListElement;

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
