import java.beans.Statement;
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

public class jena {

	public static void main(String[] args) 
	{
		Model rodzina = ModelFactory.createDefaultModel();
		String link = "http://example.com/test/";
		
		Resource rodzicMama = rodzina.createResource(link + "rodzicMama");
		Resource rodzicTata = rodzina.createResource(link + "rodzicTata");
		Resource brat = rodzina.createResource(link + "brat");
		
		Property wiek = rodzina.createProperty(link + "wiek");
		
		rodzicMama.addProperty(wiek, "50");
		brat.addProperty(wiek, "34");
		rodzicTata.addProperty(wiek, "55");
		rodzicMama.addProperty(VCARD.FN, "Irena", XSDDatatype.XSDstring);
		rodzicMama.addProperty(VCARD.NICKNAME, "mama").
			  addProperty(VCARD.NICKNAME, "ika");
		rodzicTata.addProperty(VCARD.NICKNAME, "heniu");
		
		rodzina.write(System.out, "Turtle");
		/*
		Model leap = ModelFactory.createDefaultModel();
		java.nio.file.Path input = Paths.get("C:/Users/Ziemniak/Desktop/praca magisterska1/Ontologies", "Ontology1426332736376.owl");
		leap.read(input.toUri().toString(), "RDF/XML");
		
		leap.write(System.out, "Turtle");
	
		StmtIterator iter = rodzina.listStatements();
	
		while (iter.hasNext()) {
		    com.hp.hpl.jena.rdf.model.Statement stmt      = iter.nextStatement();  
		    Resource  subject   = stmt.getSubject();
		    Property  predicate = stmt.getPredicate();
		    RDFNode   object    = stmt.getObject();
	
		    System.out.print("Stwierdzenie: " + subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // object is a literal
		        System.out.print(" \"" + object.toString() + "\"");
		    }
	
		    System.out.println(" .");
		} 
		
		 Model m = ModelFactory.createDefaultModel();
		 String nsA = "http://somewhere/else#";
		 String nsB = "http://nowhere/else#";
		 Resource root = m.createResource( nsA + "root" );
		 Property P = m.createProperty( nsA + "P" );
		 Property Q = m.createProperty( nsB + "Q" );
		 Resource x = m.createResource( nsA + "x" );
		 Resource y = m.createResource( nsA + "y" );
		 Resource z = m.createResource( nsA + "z" );
		 m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
		 System.out.println( "# -- no special prefixes defined" );
		 m.write( System.out );
		 System.out.println( "# -- nsA defined" );
		 m.setNsPrefix( "nsA", nsA );  
		 m.write( System.out );
		 System.out.println( "# -- nsA and cat defined" );
		 m.setNsPrefix( "cat", nsB );
		 m.write( System.out );
		 */
		 Resource mama = rodzina.getResource("http://example.com/test/rodzicMama");
		 String pierwszeimie = mama.getProperty(VCARD.FN).getString();
		 String nickname = mama.getProperty(VCARD.NICKNAME).getString();		 			   
		 
		 System.out.println("Imiê: " + pierwszeimie);
		 System.out.println("Nick: " + nickname);
		 
		 System.out.println("The nicknames of \""
                 + pierwszeimie + "\" are:");
		 
		 StmtIterator iter = mama.listProperties(VCARD.NICKNAME);
		 while(iter.hasNext())
		 {
			 System.out.println(iter.nextStatement().
									 getObject().
									 toString());
		 }
		 
		 ResIterator iter2 = rodzina.listSubjectsWithProperty(VCARD.NICKNAME); //mo¿na te¿ w drugim argumencie daæ wartoœæ
		 while(iter2.hasNext())
		 {
			 Resource r = iter2.nextResource();
			 System.out.println("subject: " + r);
		 }
		 
		 Selector selector = new SimpleSelector(null, null, (RDFNode)null);
		 StmtIterator iter3 = rodzina.listStatements(selector);
		 
		 while(iter3.hasNext())
		 {
			 com.hp.hpl.jena.rdf.model.Statement stmt = iter3.nextStatement();
			 System.out.print("    subject:     " + stmt.getSubject().toString());
			 System.out.print("    predykat:     " + stmt.getPredicate().toString());
			 System.out.println("    object:     " + stmt.getObject().toString());
		 }
		 
		 StmtIterator iter4 = rodzina.listStatements(new SimpleSelector(null, VCARD.NICKNAME, (RDFNode) null) 
		 {
			 public boolean selects(com.hp.hpl.jena.rdf.model.Statement s)
			 {
				 return s.getString().endsWith("ika");
			 }
		 });  
		 
	}
}
