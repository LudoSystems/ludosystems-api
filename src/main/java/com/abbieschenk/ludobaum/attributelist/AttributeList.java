package com.abbieschenk.ludobaum.attributelist;

import com.abbieschenk.ludobaum.attributelistelement.AttributeListElement;
import com.abbieschenk.ludobaum.user.LudobaumUser;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class AttributeList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@OneToMany(mappedBy = "list", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<AttributeListElement> elements = new HashSet<>();

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private LudobaumUser user;

	public AttributeList() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = title;
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

		return Objects.equals(this.id, attributeList.id) && Objects.equals(this.title, attributeList.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title);
	}

	@Override
	public String toString() {
		return "AttributeList{id=" + this.id + ", name='" + this.title + "'}";
	}

	public LudobaumUser getUser() {
		return user;
	}

	public void setUser(LudobaumUser user) {
		this.user = user;
	}
}
