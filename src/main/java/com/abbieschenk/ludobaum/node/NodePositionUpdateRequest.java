package com.abbieschenk.ludobaum.node;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Request class for updating {@link Node} x y positions.
 *
 * @author abbie
 */
public class NodePositionUpdateRequest {

    @NotBlank
    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long posX;

    @NotBlank
    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long posY;

    public Long getPosX() {
        return posX;
    }

    public void setPosX(Long posX) {
        this.posX = posX;
    }

    public Long getPosY() {
        return posY;
    }

    public void setPosY(Long posY) {
        this.posY = posY;
    }
}
