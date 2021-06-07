package com.abbieschenk.ludosystems.node;

class NodeNotFoundException extends RuntimeException {
	
	public NodeNotFoundException(Long id) {
		super("Could not find node " + id);
	}

}
