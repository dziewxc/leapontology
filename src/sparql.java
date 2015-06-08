import java.nio.file.Paths;
import java.util.Iterator;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
//import com.hp.hpl.jena.sparql.path.Path;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.vocabulary.XSD;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.vocabulary.RDFS;


public class sparql {

	public static void main(String[] args) {
		
		
		Model leap = ModelFactory.createDefaultModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "vc-db-1.rdf");
		leap.read(input.toUri().toString(), "RDF/XML");
		
		OntModel ontology = ModelFactory.createOntologyModel();
		input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "Ontology1426332736376.owl");
		ontology.read(input.toUri().toString(), "RDF/XML");
		
		ontology.createResource("jdshfj");
		
		String NS = "urn:x-hp-jena:eg/";
		Property p = leap.createProperty(NS, "ilosc lap");
		Property q = leap.createProperty(NS, "ilosc konczyn");
		//ObjectProperty is = leap.createObjectProperty(NS+"is_application_of");
		leap.add(p, RDFS.subPropertyOf, q);
		//p.addRange();
		leap.createResource(NS+"a").addProperty(p, "4");
		
		InfModel inf = ModelFactory.createRDFSModel(leap);
		
		Resource a = inf.getResource(NS+"a");
		System.out.println("Statement: " + a.getProperty(q));
		
		leap.write(System.out, "Turtle");
		
		//walidacja
		
		ValidityReport validity = inf.validate();
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {  //nie wiem czy dobry typ Iterator
		        System.out.println(" - " + i.next());
		    }
		}
		
		String queryString =
				"SELECT ?x " +
				"WHERE { " +
				"?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  \"John Smith\" }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, leap);
		try {
			ResultSet results = qexec.execSelect();
			while(results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				//Literal name = soln.getLiteral("x");
				System.out.println(soln);
			}
		} finally {
				qexec.close();
			}

	}
}


