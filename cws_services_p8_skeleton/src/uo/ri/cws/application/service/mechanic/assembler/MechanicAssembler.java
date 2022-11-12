package uo.ri.cws.application.service.mechanic.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.domain.Mechanic;

public class MechanicAssembler {

	public static MechanicDto toOptionalDto(Mechanic m) {
		MechanicDto result = null;
		
		if (m != null) {
			result = new MechanicDto();
			result.id = m.getId();
			result.dni = m.getDni();
			result.name = m.getName();
			result.surname = m.getSurname();
			result.version = m.getVersion();
		}
				
		return result;
	}

	public static List<MechanicDto> toListDto(List<Mechanic> l) {
		List<MechanicDto> result = new ArrayList<>();
		for (Mechanic m : l) {
			result.add(toOptionalDto(m));
		}
		return result;
	}

}
