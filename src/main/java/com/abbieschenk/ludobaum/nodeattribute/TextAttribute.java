package com.abbieschenk.ludobaum.nodeattribute;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(TextAttribute.TYPE)
public class TextAttribute extends NodeAttribute {

	protected static final String TYPE = "TEXT";

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if (!(super.equals(o) || o instanceof TextAttribute)) {
			return false;
		}

		TextAttribute attribute = (TextAttribute) o;

		return Objects.equals(this.text, attribute.text);
	}

	@Override
	public String toString() {
		return "TextAttribute{" + this.getAttributeString() + ", text='" + this.text + "'}";
	}

}
