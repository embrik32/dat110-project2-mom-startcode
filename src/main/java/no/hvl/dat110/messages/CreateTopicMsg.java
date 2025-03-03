package no.hvl.dat110.messages;

public class CreateTopicMsg extends Message {
    
    private String topic;
    
    // Constructor
    public CreateTopicMsg(String user, String topic) {
        super(MessageType.CREATETOPIC, user);
        this.topic = topic;
    }
    
    // Getter for topic
    public String getTopic() {
        return topic;
    }
    
    // Setter for topic
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    // toString method
    @Override
    public String toString() {
        return "CreateTopicMsg [type=" + getType() + ", user=" + getUser() + ", topic=" + topic + "]";
    }
}
