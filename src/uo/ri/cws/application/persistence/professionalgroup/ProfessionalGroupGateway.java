package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;


public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupDALDto> {

	Optional<ProfessionalGroupDALDto> findByName(String name);
	
	public class ProfessionalGroupDALDto {

		public String id;
		public Long version;
		
		public String name;
		public double triennium_payment;
		public double productivity_bonus_percentage;

	}
	
}
