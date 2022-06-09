package dao;

import models.Element;
import models.Sample;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.LiteralImpl;
import org.apache.jena.riot.RDFDataMgr;
import util.Controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SamplesDAO {

    private final String PATH = "src/main/resources/db/db.txt";
    private final Model model = ModelFactory.createDefaultModel();
    private HashMap<String, String> elementHashMap;

    public SamplesDAO(){}

    public List<Sample> getQueryResult(String currentElement, String currentMoreThan){
        List<Sample> samples = new ArrayList<>();

        InputStream in = RDFDataMgr.open(PATH);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);

        QuerySolutionMap initialBindings = new QuerySolutionMap();

        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                        "PREFIX http: <http://www.w3.org/2011/http#>" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>" +
                        "PREFIX ns2: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
                        "SELECT * WHERE {" +
                        "  ?sub a dbr:Sample_\\(material\\) ." +
                        "  ?sub rdfs:label ?name ." +
                        "  ?sub ns2:lat ?lat ." +
                        "  ?sub ns2:long ?long ." +
                        "} ";

        Query query = QueryFactory.create(queryString);

        QueryExecution exec = QueryExecutionFactory.create(query, model, initialBindings);
        ResultSet results = exec.execSelect();
        QuerySolution solution;
        List<Element> bufferElements = new ArrayList<>();
        while (results.hasNext()){
            solution = results.next();
            bufferElements = getElementsOfSample(solution.getResource("sub").getURI(), currentElement, currentMoreThan);
            if (bufferElements.size()!=0){
                samples.add(new Sample(solution.getLiteral("name").toString(),
                    solution.getLiteral("lat").getFloat(),
                    solution.getLiteral("long").getFloat(),
                    bufferElements));
            }
        }
        return samples;
    }

    public List<Element> getElementsOfSample(String resource, String currentElement, String currentMoreThan){
        List<Element> elements = new ArrayList<>();

        InputStream in = RDFDataMgr.open(PATH);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);

        String filter = "";
        RDFNode node = model.createResource(resource);

        QuerySolutionMap initialBindings = new QuerySolutionMap();
        initialBindings.add("resource", node);


        if (!currentElement.equals("Все")){
            RDFNode nodeEl = model.createResource(elementHashMap.get(currentElement));
            initialBindings.add("nodeEl", nodeEl);
        }

        if (!currentMoreThan.equals("Неважно")){
            filter = "FILTER (?amount > \"" + currentMoreThan + "\"^^xsd:float)";
        }

        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                        "PREFIX http: <http://www.w3.org/2011/http#>" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>" +
                        "PREFIX ns1: <http://irnok.net/ontology/geopollution#>" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "SELECT * WHERE {" +
                        "  ?resource ns1:contains ?sub ." +
                        "  ?sub ns1:pollutedBy ?nodeEl ." +
                        "  ?sub ns1:amount ?amount ." +
                        filter +
                        "} ";

        Query query = QueryFactory.create(queryString);

        QueryExecution exec = QueryExecutionFactory.create(query, model, initialBindings);
        ResultSet results = exec.execSelect();
        QuerySolution solution;
        while (results.hasNext()){
            solution = results.next();
            elements.add(new Element(solution.getLiteral("amount").getFloat(),
                                     solution.getResource(  "nodeEl").toString()));
        }
        return elements;
    }

    public void setElementHashMap(HashMap<String, String> elementHashMap) {
        this.elementHashMap = elementHashMap;
    }
}
