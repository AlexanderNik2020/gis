package dao;

import models.Sample;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SamplesDAO {

    private final String URL = "http://irnok.net:3030/api/export_graph?graph=pollution-database&mimetype=text%2Fplain&format=rdfxml";

    public SamplesDAO(){}

    public List<Sample> getSamples(String currentElement, String currentMoreThan){
        List<Sample> samples = new ArrayList<>();

        Model model = ModelFactory.createDefaultModel();

        InputStream in = RDFDataMgr.open(URL);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);
        model.write(System.out);

        return samples;
    }

}


/*
public List<Sample> updateData(String query){
        samples = new ArrayList<>();

//        OntModel ontModel = ModelFactory.createOntologyModel();
//        ontModel.read(URL);
//        System.out.println((ontModel.getGraph().toString()));

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open(URL);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + URL + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        //model = model.query(new SimpleSelector(subject, predicate, object));

        // write it to standard out
        //model.write(System.out);

        StmtIterator iter = model.listStatements();
        int count = 0;
        try {
            while ( iter.hasNext() && count<10 ) {
                Statement stmt = iter.next();

                Resource s = stmt.getSubject();
                Resource p = stmt.getPredicate();
                RDFNode o = stmt.getObject();

                if ( s.isURIResource() ) {
                    System.out.print(s.getURI());
                } else if ( s.isAnon() ) {
                    System.out.print("blank");
                }

                if ( p.isURIResource() )
                    System.out.print(p.getURI());

                if ( o.isURIResource() ) {
                    System.out.print(p.getURI());
                } else if ( o.isAnon() ) {
                    System.out.print("blank");
                } else if ( o.isLiteral() ) {
                    System.out.print(p.getProperty(null,"Literal"));
                }

                System.out.println();
            }
        } finally {
            if ( iter != null ) iter.close();
        }

        //create query
        return null;
    }
 */