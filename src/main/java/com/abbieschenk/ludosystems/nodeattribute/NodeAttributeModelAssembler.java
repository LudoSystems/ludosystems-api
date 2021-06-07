package com.abbieschenk.ludosystems.nodeattribute;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
class NodeAttributeModelAssembler implements RepresentationModelAssembler<NodeAttribute, EntityModel<NodeAttribute>> {

    @Override
    public EntityModel<NodeAttribute> toModel(NodeAttribute attribute) {
        return EntityModel.of(attribute,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeAttributeController.class).one(attribute.getId()))
                        .withSelfRel());
    }
}
