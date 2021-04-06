package com.abbieschenk.ludobaum.nodeattribute;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(NumberAttribute.TYPE)
public class NumberAttribute extends NodeAttribute {
	
	protected static final String TYPE = "NUMBER";

	private Long number;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (!(super.equals(o) || o instanceof NumberAttribute)) {
			return false;
		}

		NumberAttribute attribute = (NumberAttribute) o;

		return Objects.equals(this.number, attribute.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.number);
	}

	@Override
	public String toString() {
		return "NumberAttribute{" + this.getAttributeString() + ", number=" + this.number + "}";
	}
}
