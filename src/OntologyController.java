import java.io.IOException;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

import java.nio.file.Paths;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
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
		
		Integer ilosc = frame.hands().count();
		
		
		OntProperty iloscDloni = ontology.getOntProperty(Ontology.link + "iloscDloni");
		OntProperty iloscRak = ontology.createOntProperty(Ontology.link + "iloscRak");
		ontology.add(iloscDloni, RDFS.subPropertyOf, iloscRak);
		
		Reka.addProperty(iloscDloni, ilosc.toString());
		
		InfModel inf = ModelFactory.createRDFSModel(ontology);
		Resource InfReka = inf.getResource(Ontology.link + "reka");
		
		 Selector selector = new SimpleSelector(Reka, null, 1);
		 StmtIterator iter = ontology.listStatements(selector);
		 
		 while(iter.hasNext())
		 {
			 com.hp.hpl.jena.rdf.model.Statement stmt = iter.nextStatement();
			 //System.out.print("    subject:     " + stmt.getSubject().toString());
			 //System.out.print("    predykat:     " + stmt.getPredicate().toString());
			 System.out.print("    iloœæ d³oni:     " + stmt.getObject().toString());
			 System.out.print("    Iloœæ r¹k: " + (InfReka.getProperty(iloscRak)).getObject().toString());
			 System.out.println("    Super, rêka!");
		 }
		 
		 Selector selector2 = new SimpleSelector(Reka, null, 2);
		 StmtIterator iter2 = ontology.listStatements(selector2);
		 
		 while(iter2.hasNext())
		 {
			 com.hp.hpl.jena.rdf.model.Statement stmt = iter2.nextStatement();
			 //System.out.print("    subject:     " + stmt.getSubject().toString());
			 //System.out.print("    predykat:     " + stmt.getPredicate().toString());
			 System.out.print("    iloœæ d³oni:     " + stmt.getObject().toString());
			 System.out.print("      Iloœæ r¹k: " + (InfReka.getProperty(iloscRak)).getObject().toString());
			 System.out.println("    WOW WSZYSTKIE RÊCE");
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
		LeapController leapcontroller = new LeapController();
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
		
		//ontology.write(System.out, "Turtle");
		OntClass Person = ontology.createClass( link + "Person" );
		Person.setLabel( "person", "en" );
		
		ObjectProperty iloscDloni = ontology.createObjectProperty(link + "iloscDloni");
	    iloscDloni.setDomain(Person);
		
		OntClass Palec = ontology.createClass( link + "Palec" );
		Palec.setLabel( "palec", "en" );
		
		//leapontology.write(System.out, "Turtle");
		return ontology;
	}
} 




