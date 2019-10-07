/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalclock;

/**
 *
 * @author Ian Gortan
 */
public class Message {
    private int pid;
    private int clock;
    private String messageID;
    
    /**
     * Constructor
     * @param pid
     * @param clock
     * @param message 
     */
    public Message (int pid, int clock, String message) {
        this.pid = pid;
        this.clock = clock;
        this.messageID = message;
    }
    /**
     *  Copy constructor
     */
    public Message (Message aMessage) {
        this.pid = aMessage.getPid();
        this.clock = aMessage.getClock();
        this.messageID = aMessage.getMessageID();
    }

    /**
     * @return the pid
     */
    public int getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(int pid) {
        this.pid = pid;
    }

    /**
     * @return the clock
     */
    public int getClock() {
        return clock;
    }

    /**
     * @param clock the clock to set
     */
    public void setClock(int clock) {
        this.clock = clock;
    }

    /**
     * @return the messageID
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * @param messageID the messageID to set
     */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    
}
