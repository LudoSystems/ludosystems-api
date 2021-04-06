package com.abbieschenk.ludobaum.node;

class NodeNotFoundException extends RuntimeException {
	
	public NodeNotFoundException(Long id) {
		super("Could not find node " + id);
	}

}
