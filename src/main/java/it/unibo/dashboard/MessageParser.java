package it.unibo.dashboard;

import java.util.ArrayList;
import java.util.List;

import it.unibo.dashboard.api.State;

public class MessageParser {

    /*
     * A very basic implementation assuming that the message is formatted this way: STATE:IDLE|TEMP:22|LEVEL:75
     */
    public static Message parse(final String message) {
        if (!message.matches("\\bSTATE:[^\\|]+\\|TEMP:[^\\|]+\\|LEVEL:[^\\|]+\\b")) {
            // Whatever message that does not match gets printed in the console instead of the GUI
            System.out.println(message);
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
