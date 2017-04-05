package com.codon.minorproject;



import java.util.Arrays;
import java.util.List;

public class EnumsAndStatics {
    /*This class is a shared preference in android which stores strings or variables which are used in overall project*/
    public enum MessageTypes{MessageFromServer,MessageFromClient,REGISTRATION_APPROVED}
    public static final String MESSAGE_TYPE_FOR_JSON ="messageType";
    public static final String MESSAGE_CONTENT_FOR_JSON ="messageContent";

    public static final String SERVER_IP_PREF ="pref_ip";
    public static final String SERVER_PORT_PREF ="pref_port";

    public static MessageTypes getMessageTypeByString(String messageInString)
    {
        if(messageInString.equals(MessageTypes.MessageFromServer.toString()))
            return MessageTypes.MessageFromServer;
        if(messageInString.equals(MessageTypes.MessageFromClient.toString()))
            return MessageTypes.MessageFromClient;
        if(messageInString.equals(MessageTypes.REGISTRATION_APPROVED.toString()))
            return MessageTypes.REGISTRATION_APPROVED;
        return null;
    }
}
