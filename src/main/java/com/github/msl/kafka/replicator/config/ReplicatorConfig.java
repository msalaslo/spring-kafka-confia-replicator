package com.github.msl.kafka.replicator.config;


import java.time.Duration;

public class ReplicatorConfig {
	public static final String INCIDENCE_STATE_STORE = "kstream-robo-incidence-state";
	public static final String EVENT_POLLING_STATE_STORE = "kstream-robo-event-polling-state";
	public static final String DEDUPLICATION_STORE = "kstream-robo-deduplication-store";
	public static final String INSTALLATION_DATA_STATE_STORE = "kstream-robo-installation-data-state";
    public static final Duration WINDOW_SIZE_PER = Duration.ofSeconds(35);
    public static final Duration DEDUPLICATION_WINDOW_SIZE_PER = WINDOW_SIZE_PER.multipliedBy(2);
    public static final int SECONDS_IGNORED_UNNECESSARY_LOGS = 10;
    public static final int NIGHT_SHUTDOWN_TIMETABLE_1 = 1;
    public static final int NIGHT_SHUTDOWN_TIMETABLE_2 = 2;
    //1 of January of 1980 at 00:00 timestamp
    public static final long TIMESTAMP_0101198012PM = 315532800000L;

    private ReplicatorConfig() {

    }
}
