package uo.ri.cws.application.service.professionalgroup.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.domain.ProfessionalGroup;

public class ProfessionalGroupAssembler {

	public static ProfessionalGroupBLDto toOptionalDto(
			ProfessionalGroup group) {
		ProfessionalGroupBLDto result = null;

		if (group != null) {
			result = new ProfessionalGroupBLDto();
			result.id = group.getId();
			result.name = group.getName();
			result.trieniumSalary = group.getTrienniumPayment();
			result.productivityRate = group.getProductivityBonusPercentage();
			result.version = group.getVersion();
		}

		return result;
	}

	public static List<ProfessionalGroupBLDto> toListDto(
			List<ProfessionalGroup> l) {
		List<ProfessionalGroupBLDto> result = new ArrayList<>();
		for (ProfessionalGroup group : l) {
			result.add(toOptionalDto(group));
		}
		return result;
	}

}
