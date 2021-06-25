package com.github.msl.kafka.replicator.dto;


import java.util.HashMap;
import java.util.Map;

/**
 * Event Type
 *
 * @author vicrerdgz
 */
public enum EventType {
    INTRUSION("RO"),
    PERIMETRAL("RP"),
    CENTRAL_HIGH_PRIORITY("CEN"),
    VIDEO("VD"),
    DISCONNECTION("US"),
    DISCONNECTION_APP("CEB"),
    TAMPER("TA");


    private static final Map<String, EventType> validLookup = new HashMap<>();
    private static final Map<String, EventType> lookup = new HashMap<>();

    static {
        validLookup.put(INTRUSION.getValue(), INTRUSION);
        validLookup.put(PERIMETRAL.getValue(), PERIMETRAL);
        validLookup.put(CENTRAL_HIGH_PRIORITY.getValue(), CENTRAL_HIGH_PRIORITY);
        validLookup.put(VIDEO.getValue(), VIDEO);
        validLookup.put(DISCONNECTION.getValue(), DISCONNECTION);
        validLookup.put(DISCONNECTION_APP.getValue(), DISCONNECTION_APP);

        for (EventType z : EventType.values()) {
            lookup.put(z.getValue(), z);
        }
    }

    private String value;

    EventType(String alarmType) {
        this.value = alarmType;
    }

    /**
     * Generates a EventType enum instance from string value
     *
     * @param alarmType string value
     * @return Generated alarmType
     */
    public static EventType get(String alarmType) {
        return lookup.get(alarmType);
    }

    /**
     * Checks if a string is a valid enum value
     *
     * @param eventType String value to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String eventType) {
        return validLookup.containsKey(eventType);
    }

    /**
     * Checks if a string is a valid enum value
     *
     * @param eventType String value to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValid(EventType eventType) {
        return validLookup.containsValue(eventType);
    }

    public String getValue() {
        return value;
    }


}
