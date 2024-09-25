package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DownloadFileModel implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@NotNull(message = "id may not be null")
	private Long id;
}
