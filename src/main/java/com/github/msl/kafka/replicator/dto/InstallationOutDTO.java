package com.github.msl.kafka.replicator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Installation DTO object.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class InstallationOutDTO extends BaseDTO {

	private String contactName;

	private String country;

	private Integer internalInstallationId;

	private String contactPhoneNumber;

	private String email;

	private String installationAddress;

	private String panelPhoneNumber;

	private String externalInstallationId;

	private String panelVersion;

	private String confiaFlag;

}
