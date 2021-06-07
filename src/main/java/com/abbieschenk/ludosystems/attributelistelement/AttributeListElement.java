package com.abbieschenk.ludosystems.attributelistelement;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.abbieschenk.ludosystems.attributelist.AttributeList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AttributeListElement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String text;

	private Long sortOrder;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_id")
	private AttributeList list;

	public AttributeListElement() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public AttributeList getList() {
		return list;
	}

	public void setList(AttributeList list) {
		this.list = list;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof AttributeListElement)) {
			return false;
		}

		AttributeListElement attributeListElement = (AttributeListElement) o;

		return Objects.equals(this.id, attributeListElement.id)
				&& Objects.equals(this.sortOrder, attributeListElement.sortOrder)
				&& Objects.equals(this.text, attributeListElement.text)
				&& Objects.equals(this.list, attributeListElement.list);

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.sortOrder, this.text, this.list);
	}

	@Override
	public String toString() {
		return "AttributeListElement{id=" + this.id + ", sort-order=" + this.sortOrder + ", text=" + this.text
				+ ", list=" + this.list + "}";
	}

}
