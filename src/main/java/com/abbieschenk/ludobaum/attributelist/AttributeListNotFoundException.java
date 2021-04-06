package com.abbieschenk.ludobaum.attributelist;

class AttributeListNotFoundException extends RuntimeException {
	
	public AttributeListNotFoundException(Long id) {
		super("Could not find Attribute List " + id);
	}

}
