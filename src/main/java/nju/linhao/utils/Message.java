package main.java.nju.linhao.utils;

import main.java.nju.linhao.enums.MessageType;
import main.java.nju.linhao.team.Team;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 3103317843148814898L;
    private MessageType messageType;
    private Object messageContent;

    public Message(MessageType messageType){
        this.messageType = messageType;
        this.messageContent = null;
    }

    public Message(MessageType messageType, Object object) {
        this.messageType = messageType;
        this.messageContent = object;
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public void setMessageType(MessageType messageType){
        this.messageType = messageType;
    }

    public Object getMessageContent(){
        return messageContent;
    }

    public void setMessageContent(Object messageContent){
        this.messageContent = messageContent;
    }

}
