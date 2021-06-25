package com.github.msl.kafka.replicator.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Incidence type
 *
 */
public enum IncidenceType {
    INTRUSION("RO"),
    PERIMETRAL("RP"),
    CENTRAL_HIGH_PRIORITY("CEN"),
    TAMPER("TA");


    private static final Map<String, IncidenceType> robberyLookup = new HashMap<>();

    static {
        robberyLookup.put(INTRUSION.getValue(), INTRUSION);
        robberyLookup.put(PERIMETRAL.getValue(), PERIMETRAL);
        robberyLookup.put(CENTRAL_HIGH_PRIORITY.getValue(), CENTRAL_HIGH_PRIORITY);
    }

    private String value;

    IncidenceType(String alarmType) {
        this.value = alarmType;
    }

    /**
     * Generates a alarmType enum instance from string value
     *
     * @param alarmType string value
     * @return Generated alarmType
     */
    public static IncidenceType get(String alarmType) {
        return robberyLookup.get(alarmType);
    }

    /**
     * Checks if the passed incidence type is a robbery one
     *
     * @param incidenceType value to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isRobbery(IncidenceType incidenceType) {
        return robberyLookup.containsValue(incidenceType);
    }

    public String getValue() {
        return value;
    }


}
