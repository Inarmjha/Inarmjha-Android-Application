package com.codon.minorproject;

public interface TCPListener {
    /*Interfaces are created as listeners which do assigned work if an event is triggered in the background*/
    public void onTCPMessageRecieved(String message);
    public void onTCPConnectionStatusChanged(boolean isConnectedNow);
}
