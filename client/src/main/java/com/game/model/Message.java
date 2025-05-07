package com.game.model;

/**
 * Represents a simple game control message.
 */
public class Message {
    public enum Type {
        START_GAME,
        END_GAME
    }

    private Type type;

    /**
     * Default constructor required for serialization frameworks.
     */
    public Message() {
    }

    /**
     * Creates a Message with the given type.
     * @param type the message type
     */
    public Message(Type type) {
        this.type = type;
    }

    /**
     * @return the message type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the message type.
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }
}

