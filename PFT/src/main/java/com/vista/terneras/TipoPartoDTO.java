package com.vista.terneras;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoPartoDTO {

	private int idParto;
	
	private String tipoParto;
}
