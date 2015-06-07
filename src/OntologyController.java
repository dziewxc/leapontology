import java.io.IOException;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

import java.nio.file.Paths;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Listener;

class LeapListener extends Listener {
	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		String link = "http://leapmotion.com/test/";
		
		OntModel ontology = OntologyController.ontology;
		//ontology.write(System.out, "Turtle");
		Resource Reka = ontology.createResource(link + "reka");   //tworze arbitralna abstrakcyjna reke
		//Reka ma typ Hand

		//ontology.add(resource, property, RDFNode);
		
		System.out.println("Frame Id:" + frame.id()
				+ ", Timestamp " + frame.timestamp()
				+ ", Hands " + frame.hands().count()
				+ ", Fingers " + frame.fingers().count()
				+ ", Tools " + frame.tools().count()
				+ ", Gestures " + frame.gestures().count()
				);
	}
	
	public void onDisconnect(Controller controller) {
		System.out.println("Motion Sensor DisConnected");
	}
	
	public void OnExit(Controller controller) {
		System.out.println("Exited");
	}
}


class LeapController {
	
	public void main(String[] args) {
		LeapListener listener = new LeapListener();	  //tworzymy listener - obserwuje zmiany w urz¹dzeniu
		Controller controller = new Controller();    //tworzymy controller
		
		controller.addListener(listener);     //dodajemy listenera do controllera
		
		System.out.println("Press enter to quit");
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		controller.removeListener(listener);

	}
}

public class OntologyController {
	static OntModel ontology;
	
	public static void main(String[] args) {
		OntologyController.ontology = Ontology.main(args);
		
		ontology.write(System.out, "Turtle");
		
		LeapController leapcontroller = new LeapController();
		//leapcontroller.main(args);
	}
}

class Ontology {
	static OntModel ontology;
	static String link = "http://leapmotion.com/test/";
	
	public static OntModel main(String[] args) {
		OntModel ontology = ModelFactory.createOntologyModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "Ontology1426332736376.owl");
		ontology.read(input.toUri().toString(), "RDF/XML");
		
		OntClass Person = ontology.createClass( link + "Person" );
		Person.setLabel( "person", "en" );
		
		ObjectProperty iloscRak = ontology.createObjectProperty(link + "iloscRak");
	    iloscRak.setDomain(Person);
		
		OntClass Palec = ontology.createClass( link + "Palec" );
		Palec.setLabel( "palec", "en" );
		
		//leapontology.write(System.out, "Turtle");
		return ontology;
	}
} 




