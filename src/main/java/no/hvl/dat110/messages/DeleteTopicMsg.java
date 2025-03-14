package no.hvl.dat110.messages;

public class DeleteTopicMsg extends Message {

	// message sent from client to create topic on the broker
	private String topic;

    public DeleteTopicMsg(String user, String topic) {
		super(MessageType.DELETETOPIC, user);
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
