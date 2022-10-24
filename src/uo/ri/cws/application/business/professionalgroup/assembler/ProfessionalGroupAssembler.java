package uo.ri.cws.application.business.professionalgroup.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

public class ProfessionalGroupAssembler {
	
	public static Optional<ProfessionalGroupBLDto> toBLDto(Optional<ProfessionalGroupDALDto> arg) {
		Optional<ProfessionalGroupBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toProfessionalGroupDto(arg.get()));
		return result;
	}

	public static List<ProfessionalGroupBLDto> toDtoList(List<ProfessionalGroupDALDto> arg) {
		List<ProfessionalGroupBLDto> result = new ArrayList<ProfessionalGroupBLDto>();
		for (ProfessionalGroupDALDto mr : arg)
			result.add(toProfessionalGroupDto(mr));
		return result;
	}

	public static ProfessionalGroupDALDto toDALDto(ProfessionalGroupBLDto arg) {
		ProfessionalGroupDALDto result = new ProfessionalGroupDALDto();
		result.id = arg.id;
		result.version = arg.version;
		result.name = arg.name;
		result.productivity_bonus_percentage = arg.productivityRate;
		result.triennium_payment = arg.trieniumSalary;
		return result;
	}

	private static ProfessionalGroupBLDto toProfessionalGroupDto(ProfessionalGroupDALDto arg) {

		ProfessionalGroupBLDto result = new ProfessionalGroupBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.name = arg.name;
		result.productivityRate = arg.productivity_bonus_percentage;
		result.trieniumSalary = arg.triennium_payment;
		return result;
	}
}
