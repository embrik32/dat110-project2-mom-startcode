package no.hvl.dat110.messages;

public class PublishMsg extends Message {
    
    private String topic;
    private String message;
    
    // Constructor
    public PublishMsg(String user, String topic, String message) {
        super(MessageType.PUBLISH, user);
        this.topic = topic;
        this.message = message;
    }
    
    // Getter for topic
    public String getTopic() {
        return topic;
    }
    
    // Setter for topic
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    // Getter for message
    public String getMessage() {
        return message;
    }
    
    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }
    
    // toString method
    @Override
    public String toString() {
        return "PublishMsg [type=" + getType() + ", user=" + getUser() + ", topic=" + topic + ", message=" + message + "]";
    }
}

