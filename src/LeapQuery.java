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
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
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


public class LeapQuery {

	public static void main(String[] args) {
		Model newmodel = ModelFactory.createDefaultModel();
		String NS = "ns/";
		Property paw_number = newmodel.createProperty(NS, "ilosc lap");
		Property limb_number = newmodel.createProperty(NS, "ilosc konczyn");
		
		newmodel.add(paw_number, RDFS.subPropertyOf, limb_number);
		newmodel.createResource(NS + "a").addProperty(paw_number, "4");
		
		InfModel inf = ModelFactory.createRDFSModel(newmodel);
		Resource Azor = inf.getResource(NS + "a");
		System.out.println("Statement: " + Azor.getProperty(limb_number));
		query();
	}
	
	public static void query()
	{
		Model leap = ModelFactory.createDefaultModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "leapmotion.owl");
		leap.read(input.toUri().toString(), "RDF/XML");
		
		leap.createResource("circlegesture");
		InfModel inf = ModelFactory.createRDFSModel(leap);
		
		Resource gest = inf.getResource("circlegesture");
		Resource circleGesture = inf.getResource("CircleGesture");
		Statement s = ResourceFactory.createStatement(gest, "rdf:type", circleGesture);  //
		leap.add(s);
		
		
		String queryString =
				"SELECT ?x " +
				"WHERE { " +
				"?x  a  \"John Smith\" }";
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







