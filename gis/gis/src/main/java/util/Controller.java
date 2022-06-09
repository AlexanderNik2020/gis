package util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dao.SamplesDAO;
import models.Sample;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import panels.MainWindow;


public class Controller {

    private MainWindow mainWindow = new MainWindow();
    private SamplesDAO dao;
    private ArrayList<String> elements = new ArrayList<String>();
    private HashMap<String,String> elementHashMap = new HashMap<>();
    private String currentElement;
    private String currentMoreThan;
    private Sample currentSample = new Sample("No selected", 0, 0, new ArrayList<>());
    private List<Sample> samples;
    private static Controller instance = new Controller();
    private HashMap<String,String> elementsDictionary = new HashMap<>();

    private Controller(){
        initElements();
        uploadData();
        dao = new SamplesDAO();
        initSamples();
        mainWindow.getCentralPanel().setSamples(samples);
    }

    public void initSamples(){
        samples = new ArrayList<>();
        samples.add(currentSample);
    }

    public static Controller getInstance() {
        return instance;
    }

    public void updateSamples(){
        currentElement = mainWindow.getLeftPanel().getElementsBox().getSelectedItem().toString();
        currentMoreThan = mainWindow.getLeftPanel().getMoreThanBox().getSelectedItem().toString();
        samples = dao.getQueryResult(currentElement,currentMoreThan);
        mainWindow.getCentralPanel().setSamples(samples);
        mainWindow.getRightPanel().getInfoTextArea().setText(currentSample.toString());
        mainWindow.getCentralPanel().update();
    }

    public void updateCurrentSample(Sample newSample){
        if (newSample == null){
            currentSample = new Sample("No selected", 0, 0, new ArrayList<>());
            return;
        }
        currentSample = newSample;
        mainWindow.getRightPanel().getInfoTextArea().setText(currentSample.toString());
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public Sample getCurrentSample() {
        return currentSample;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void uploadData(){
        String URL = "http://irnok.net:3030/api/export_graph?graph=pollution-database&mimetype=text%2Fplain&format=rdfxml";

        File file = new File("src/main/resources/db/db.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Model model = ModelFactory.createDefaultModel();

        InputStream in = RDFDataMgr.open(URL);
        if (in == null){
            throw new IllegalArgumentException("Server isn't working");
        }

        model.read(in, null);
        model.write(out);
    }

    public void initElements(){
        elements = new ArrayList<>();
        elements.add("Все");
        try {
            File file = new File("src/main/resources/dictionary/elementsDictionary.csv");
            Scanner in = new Scanner(file);
            String line;
            String adding = "http://dbpedia.org/resource/";
            while(in.hasNextLine()){
                line = in.nextLine();
                elements.add(line.split(",")[0]);
                elementHashMap.put(line.split(",")[0], adding+line.split(",")[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getElementHashMap() {
        return elementHashMap;
    }

    public SamplesDAO getDao() {
        return dao;
    }
}

