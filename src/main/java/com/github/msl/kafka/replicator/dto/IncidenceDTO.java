package com.github.msl.kafka.replicator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IncidenceDTO extends BaseDTO {

    private String country;

    private Integer id;

    private Integer internalInstallationId;

    private IncidenceType type;

    private Long creationTimestamp;
    
    private Integer priority;
}

