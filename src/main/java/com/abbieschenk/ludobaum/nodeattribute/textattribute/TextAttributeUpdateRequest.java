package com.abbieschenk.ludobaum.nodeattribute.textattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeUpdateRequest;

import javax.validation.constraints.Size;

public class TextAttributeUpdateRequest extends NodeAttributeUpdateRequest {

    @Size(max = 4095)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
