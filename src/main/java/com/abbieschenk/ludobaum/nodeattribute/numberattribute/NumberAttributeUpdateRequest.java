package com.abbieschenk.ludobaum.nodeattribute.numberattribute;

import com.abbieschenk.ludobaum.nodeattribute.NodeAttributeUpdateRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class NumberAttributeUpdateRequest extends NodeAttributeUpdateRequest {

    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long number;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
