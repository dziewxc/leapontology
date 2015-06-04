import java.nio.file.Paths;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.StmtIterator;
//import com.hp.hpl.jena.sparql.path.Path;
import com.hp.hpl.jena.vocabulary.VCARD;


public class sparql {

	public static void main(String[] args) {
		Model leap = ModelFactory.createDefaultModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "vc-db-1.rdf");
		leap.read(input.toUri().toString(), "RDF/XML");
		
		leap.write(System.out, "Turtle");

	}

	
	
	
	
	
	
	
	
}
