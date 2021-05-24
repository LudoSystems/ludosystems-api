package com.abbieschenk.ludobaum.nodeattribute;

public class NodeAttributeNotFoundException extends RuntimeException {
    public NodeAttributeNotFoundException(Long id) {
        super("Could not find node attribute " + id);
    }
}
