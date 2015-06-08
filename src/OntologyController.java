import java.io.IOException;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

import java.nio.file.Paths;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.StmtIterator;
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
		OntModel ontology = Ontology.main(null);
		
		Resource Reka = ontology.createResource(Ontology.link + "reka");
		OntProperty iloscRak = ontology.getOntProperty(Ontology.link + "iloscRak");
		
		Integer ilosc = frame.hands().count();
		Reka.addProperty(iloscRak, ilosc.toString());

		//ontology.write(System.out, "Turtle");
		
		 Selector selector = new SimpleSelector(Reka, null, 1);
		 StmtIterator iter3 = ontology.listStatements(selector);
		 
		 while(iter3.hasNext())
		 {
			 com.hp.hpl.jena.rdf.model.Statement stmt = iter3.nextStatement();
			 //System.out.print("    subject:     " + stmt.getSubject().toString());
			 //System.out.print("    predykat:     " + stmt.getPredicate().toString());
			 //System.out.println("    object:     " + stmt.getObject().toString());
			 System.out.println("Super, rêka!");
		 }
		
		/*System.out.println("Frame Id:" + frame.id()
				+ ", Timestamp " + frame.timestamp()
				+ ", Hands " + frame.hands().count()
				+ ", Fingers " + frame.fingers().count()
				+ ", Tools " + frame.tools().count()
				+ ", Gestures " + frame.gestures().count()
				);*/
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
		OntModel ontology = Ontology.main(args);
		
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
	
	public static void main(String[] args) {
		//OntModel ontology = Ontology.main(args);
		LeapController leapcontroller = new LeapController();
		//ontology.createResource("ddsfd");
		leapcontroller.main(args);
	}
}

class Ontology {
	static OntModel ontology;
	static String link = "http://leapmotion.com/test/";
	
	public static OntModel main(String[] args) {
		ontology = ModelFactory.createOntologyModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "Ontology1426332736376.owl");
		ontology.read(input.toUri().toString(), "RDF/XML");
		ontology.createResource("ddsfd");
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




