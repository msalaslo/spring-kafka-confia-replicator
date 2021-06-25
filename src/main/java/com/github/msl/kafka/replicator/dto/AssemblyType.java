package com.github.msl.kafka.replicator.dto;

import java.util.HashMap;
import java.util.Map;

import com.github.msl.kafka.replicator.config.ReplicatorConfig;

public enum AssemblyType {

    TOTAL_PERIMETER("A", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_1),
    PARCIAL_PERIMETER("B", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PARCIAL_NIGHT_PERIMETER("C", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    DISASSEMBLE("D", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PERIMETER("E", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    TOTAL_PERIMETER_ANNEXED("F", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_1),
    PARCIAL_PERIMETER_ANNEXED("G", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PARCIAL_NIGHT_PERIMETER_ANNEXED("H", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    UNKNOWN("N", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    TOTAL_ANNEXED("O", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_1),
    PARCIAL("P", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PARCIAL_NIGHT("Q", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PARCIAL_ANNEXED("R", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PARCIAL_NIGHT_ANNEXED("S", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    TOTAL("T", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_1),
    ANNEXED("X", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2),
    PERIMETER_ANNEXED("Y", ReplicatorConfig.NIGHT_SHUTDOWN_TIMETABLE_2);

    private static final Map<String, AssemblyType> lookup = new HashMap<>();

    static {
        for (AssemblyType z : AssemblyType.values()) {
            lookup.put(z.getValue(), z);
        }
    }

    private final String value;
    private final Integer timeTableId;

    AssemblyType(String assemblyType, Integer timeTableId) {
        this.value = assemblyType;
        this.timeTableId = timeTableId;
    }

    /**
     * Generates a assemblyType enum instance from string value
     *
     * @param assemblyType string value
     * @return Generated assemblyType
     */
    public static AssemblyType get(String assemblyType) {
        return lookup.get(assemblyType);
    }

    /**
     * Checks if a string is a valid enum value
     *
     * @param assemblyType String value to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String assemblyType) {
        return lookup.containsKey(assemblyType);
    }

    public String getValue() {
        return value;
    }

    public Integer getTimeTableId() {
        return timeTableId;
    }
}
