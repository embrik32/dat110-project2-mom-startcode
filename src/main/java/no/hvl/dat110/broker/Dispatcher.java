package no.hvl.dat110.broker;

import java.util.Set;
import java.util.Collection;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.common.Stopable;
import no.hvl.dat110.messages.*;
import no.hvl.dat110.messagetransport.Connection;

public class Dispatcher extends Stopable {

    private Storage storage;

    public Dispatcher(Storage storage) {
        super("Dispatcher");
        this.storage = storage;
    }

    @Override
    public void doProcess() {
        Collection<ClientSession> clients = storage.getSessions();
        Logger.lg(".");
        for (ClientSession client : clients) {
            if (client.hasData()) {
                Message msg = client.receive();
                if (msg != null) {
                    dispatch(client, msg);
                }
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dispatch(ClientSession client, Message msg) {
        switch (msg.getType()) {
            case DISCONNECT -> onDisconnect((DisconnectMsg) msg);
            case CREATETOPIC -> onCreateTopic((CreateTopicMsg) msg);
            case DELETETOPIC -> onDeleteTopic((DeleteTopicMsg) msg);
            case SUBSCRIBE -> onSubscribe((SubscribeMsg) msg);
            case UNSUBSCRIBE -> onUnsubscribe((UnsubscribeMsg) msg);
            case PUBLISH -> onPublish((PublishMsg) msg);
            default -> Logger.log("broker dispatch - unhandled message type");
        }
    }

    public void onConnect(ConnectMsg msg, Connection connection) {
        Logger.log("onConnect:" + msg);
        storage.addClientSession(msg.getUser(), connection);
    }

    public void onDisconnect(DisconnectMsg msg) {
        Logger.log("onDisconnect:" + msg);
        storage.removeClientSession(msg.getUser());
    }

    public void onCreateTopic(CreateTopicMsg msg) {
        Logger.log("onCreateTopic:" + msg);
        storage.createTopic(msg.getTopic());
    }

    public void onDeleteTopic(DeleteTopicMsg msg) {
        Logger.log("onDeleteTopic:" + msg);
        storage.deleteTopic(msg.getTopic());
    }

    public void onSubscribe(SubscribeMsg msg) {
        Logger.log("onSubscribe:" + msg);
        storage.addSubscriber(msg.getUser(), msg.getTopic());
    }

    public void onUnsubscribe(UnsubscribeMsg msg) {
        Logger.log("onUnsubscribe:" + msg);
        storage.removeSubscriber(msg.getUser(), msg.getTopic());
    }

    public void onPublish(PublishMsg msg) {
        Logger.log("onPublish:" + msg);
        Set<String> subscribers = storage.getSubscribers(msg.getTopic());
        if (subscribers != null) {
            for (String user : subscribers) {
                ClientSession session = storage.getSession(user);
                if (session != null) {
                    session.send(msg);
                }
            }
        }
    }
}
