package org.anup.javabrains.messengers.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.anup.javabrains.messenger.model.Message;
import org.anup.javabrains.messenger.resources.beans.MessageFilterBean;
import org.anup.javabrains.messengers.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
MessageService messageservice = new MessageService();
@GET
public List<Message> getMessages(@QueryParam("year") int year,
		@QueryParam("start") int
		start,
		@QueryParam("size") int
		size){
		if(year>0)
		{
		return messageservice.getAllMessagesForYear(year);
		}
		if(start>=0 && size>0){
		return messageservice.getAllMessagesPaginated(start, size);
		}
		return messageservice.getAllMessages();
		}
		@GET
		@Path("/{messageId}")
		public Message getMessage(@PathParam("messageId") Long messageId, @Context
		UriInfo uriInfo){
		Message message = messageservice.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComment(uriInfo, message), "comment");
		return message;
		}
		private String getUriForComment(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(MessageResource.class, "getCommentResource")
		.path(CommentResource.class)
		.resolveTemplate("messageId", message.getId())
		.build()
		.toString();
		}
		private String getUriForProfile(UriInfo uriInfo, Message message) {
			return uriInfo.getBaseUriBuilder()
			.path(ProfileResource.class)
			.path(message.getAuthor())
			.build()
			.toString();
			}
			public String getUriForSelf(UriInfo uriInfo, Message message) {
			return uriInfo.getBaseUriBuilder()
			.path(MessageResource.class)
			.path(Long.toString(message.getId()))
			.build()
			.toString();
			}
			@PUT
			@Path("/{messageId}")
			public Message updateMessage(@PathParam("messageId") Long messageId, Message
			message){
			message.setId(messageId);
			return messageservice.updateMessage(message);
			}
			@POST
			public Response addMessage(Message message, @Context UriInfo uriInfo){
			Message newMessage = messageservice.addMessage(message);
			String newId = String.valueOf(newMessage.getId());
			URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
			return Response.created(uri)
			.entity(newMessage)
			.build();
			}
			
			@DELETE
			@Path("/{messageId}")
			public void deleteMessage(@PathParam("messageId") Long messageId){
			messageservice.removeMessage(messageId);
			}
			@Path("/{messageId}/comments")
			public CommentResource getCommentResource(){
			return new CommentResource();
			}
			}