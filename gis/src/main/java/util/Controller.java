package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.SamplesDAO;
import models.Sample;
import panels.MainWindow;


public class Controller {

    private MainWindow mainWindow = MainWindow.getInstance();
    private SamplesDAO dao;
    private List<String> elements;
    private String currentElement;
    private String currentMoreThan;
    private Sample currentSample = new Sample("UGS-01", 21,31, new ArrayList<>());
    private List<Sample> samples;
    private static Controller instance = new Controller();

    private Controller(){
        dao = new SamplesDAO();
        initElements();
    }

    public static Controller getInstance() {
        return instance;
    }

    public void updateSamples(){
        samples = dao.getSamples(currentElement, currentMoreThan);
    }

    public void updateCurrentSample(){
        currentSample = new Sample("123", 123,31, new ArrayList<>());
    }

    public void initElements(){
        elements = new ArrayList<>();
        elements.add("Все");
        try {
            File file = new File("src/main/resources/dictionary/elementsDictionary.csv");
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                elements.add(in.nextLine().split(",")[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getElements() {
        return elements;
    }

    public Sample getCurrentSample() {
        return currentSample;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}

