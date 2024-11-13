package it.unibo.dashboard;

import java.util.ArrayList;
import java.util.List;

import it.unibo.dashboard.api.State;

/*
 * A class containing functions for parsing and returning incoming messages;
 */
public class MessageParser {

    private static String regexPattern = "STATE:[^\\|]+\\|TEMP:[^\\|]+\\|LEVEL:[^\\|]+\\n?";

    /*
     * A very basic implementation assuming that the message is formatted like this: STATE:IDLE|TEMP:22|LEVEL:75
     */
    public static Message parse(final String message) {
        if (!message.matches(regexPattern)) {
            return null;
        }

        final String[] parts = message.split("\\|");

        final List<String> values = new ArrayList<>();
        for (final String part : parts) {
            final String[] entry = part.split(":");
            values.add(entry[1]);
        }
        final Message output = new Message();
        output.setState(State.valueOf(values.get(0)));
        output.setTemperature(values.get(1));
        output.setLevel(values.get(2));
        output.setTimeStamp();
        return output;
    }
}
