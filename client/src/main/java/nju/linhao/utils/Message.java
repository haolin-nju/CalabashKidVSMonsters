package main.java.nju.linhao.utils;

import main.java.nju.linhao.enums.MessageType;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 3103317843148814898L;
    private MessageType messageType;
    private String messageContent;

    public Message(MessageType messageType){
        this.messageType = messageType;
        this.messageContent = "";
    }

    public Message(MessageType messageType, String messageContent){
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public void setMessageType(MessageType messageType){
        this.messageType = messageType;
    }

    public String getMessageContent(){
        return messageContent;
    }

    public void setMessageContent(String messageContent){
        this.messageContent = messageContent;
    }
}
