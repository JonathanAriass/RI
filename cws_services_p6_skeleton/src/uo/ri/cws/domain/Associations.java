package uo.ri.cws.domain;

public class Associations {

	public static class Own {

		public static void link(Client client, Vehicle vehicle) {
		}

		public static void unlink(Client client, Vehicle vehicle) {
		}

	}

	public static class Classify {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			// TODO Auto-generated method stub
		}

		public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
			// TODO Auto-generated method stub
		}

	}

	public static class Pay {

		public static void link(Client client, PaymentMean pm) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Client client, PaymentMean pm) {
			// TODO Auto-generated method stub
		}

	}

	public static class Fix {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
			// TODO Auto-generated method stub
		}

	}

	public static class ToInvoice {

		public static void link(Invoice invoice, WorkOrder workOrder) {

		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {

		}
	}

	public static class ToCharge {

		public static void link(PaymentMean pm, Charge charge, Invoice inovice) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Charge charge) {
			// TODO Auto-generated method stub
		}

	}

	public static class Assign {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			// TODO Auto-generated method stub
		}

	}

	public static class Intervene {

		public static void link(WorkOrder workOrder, Intervention intervention,
				Mechanic mechanic) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Intervention intervention) {
			// TODO Auto-generated method stub
		}

	}

	public static class Substitute {

		public static void unlink(SparePart spare, Substitution sustitution,
				Intervention intervention) {
			// TODO Auto-generated method stub
		}

		public static void unlink(Substitution sustitution) {
			// TODO Auto-generated method stub
		}

	}

}
