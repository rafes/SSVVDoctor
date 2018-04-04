
import repository.Repository;
import ui.DoctorUI;
import controller.DoctorController;

public class App {

	public static void main(String[] args) {
		String patients = "FilePatients.txt";
		String consultations = "FileConsultations.txt";
		Repository repo = new Repository("C:\\Users\\Valeriu\\Desktop\\SSVV\\SSVVDoctor\\src\\Patients.txt", "C:\\Users\\Valeriu\\Desktop\\SSVV\\SSVVDoctor\\src\\Consultations.txt");
		DoctorController ctrl = new DoctorController(repo);
		
		DoctorUI console = new DoctorUI(ctrl);
		console.Run();
	}
}
