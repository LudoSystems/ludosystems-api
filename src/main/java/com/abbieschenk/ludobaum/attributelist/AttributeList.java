package com.abbieschenk.ludobaum.attributelist;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.abbieschenk.ludobaum.attributelistelement.AttributeListElement;

@Entity
public class AttributeList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "list", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<AttributeListElement> elements = new HashSet<>();

	public AttributeList() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AttributeListElement> getElements() {
		return elements;
	}

	public void setElements(Set<AttributeListElement> elements) {
		this.elements = elements;
	}

	public void addElement(AttributeListElement element) {
		this.elements.add(element);
		element.setList(this);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}

		if (!(o instanceof AttributeList)) {
			return false;
		}

		AttributeList attributeList = (AttributeList) o;

		return Objects.equals(this.id, attributeList.id) && Objects.equals(this.name, attributeList.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString() {
		return "AttributeList{id=" + this.id + ", name='" + this.name + "'}";
	}

}
