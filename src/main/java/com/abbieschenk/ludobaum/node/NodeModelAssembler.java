package com.abbieschenk.ludobaum.node;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
class NodeModelAssembler implements RepresentationModelAssembler<Node, EntityModel<Node>> {

	@Override
	public EntityModel<Node> toModel(Node node) {
		
		return EntityModel.of(node,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).one(node.getId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NodeController.class).all())
						.withRel(NodeController.REL));
	}

}
