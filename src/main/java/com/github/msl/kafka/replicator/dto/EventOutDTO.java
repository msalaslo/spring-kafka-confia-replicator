package com.github.msl.kafka.replicator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventOutDTO extends BaseDTO {

    private Integer incidenceId;

    private Integer internalInstallationId;

    private String name;

    private String zone;

    private EventType type;

    private Long creationTimestamp;

    private String creationTime;

    private String creationDate;

    private AssemblyType assemblyType;

    private String operatorId;
    
    private Integer priority;

}
