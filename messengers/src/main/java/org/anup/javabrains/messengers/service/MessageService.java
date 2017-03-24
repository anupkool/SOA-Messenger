package org.anup.javabrains.messengers.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.anup.javabrains.messenger.database.DatabaseClass;
import org.anup.javabrains.messenger.database.MessageUpdateObject;
import org.anup.javabrains.messenger.exception.DataNotFoundException;
import org.anup.javabrains.messenger.model.Message;

public class MessageService {

MessageUpdateObject uo = new MessageUpdateObject();
public List<Message> getAllMessages(){
//Message s1 = new Message(1L, "Hello World", "Anup");
//Message s2 = new Message(2L, "Hello Jersey", "Anup");
//List<Message> list = new ArrayList<>();
//list.add(s1);
//list.add(s2);
//return new ArrayList<Message>(messages.values());
return uo.getAllMessages();
}

public List<Message> getAllMessagesForYear(int year) {
	List<Message> messagesForYear = new ArrayList<>();
	List<Message> messages = new ArrayList<>();
	messages = uo.getAllMessages();
	Calendar cal = Calendar.getInstance();
	for(Message message : messages){
	cal.setTime(message.getCreated());
	if(cal.get(Calendar.YEAR) == year)
	messagesForYear.add(message);
	}
	return messagesForYear;
	}
public List<Message> getAllMessagesPaginated(int start, int size){
List<Message> list = new ArrayList<Message>(uo.getAllMessages());
if(start + size > list.size())
return new ArrayList<Message>();
return list.subList(start, start+size);
}
public Message getMessage(long id){
//List<Message> msg = new ArrayList<>();
	
	//msg = uo.getAllMessages();
	Message message = uo.getMessage(id);
	if(message == null){
	throw new DataNotFoundException("Message with id "+id+" not found");
	}
	return message;
	}
	public Message addMessage(Message message){
	//message.setId(messages.size() + 1);
	//messages.put(message.getId(), message);
	uo.insertMessage(message);
	return message;
	}
	public Message updateMessage(Message message){
	if (message.getId() <= 0){
	return null;
	}
	//messages.put(message.getId(), message);
	uo.updateMessage(message);
	return message;
	}
	public void removeMessage(long id){
	//return messages.remove(id);
	uo.deleteMessage(id);
	}


		
	}
