import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AF {
    List<String> stari = new LinkedList<>();
    String stareInitiala;
    List<String> stariFinale = new LinkedList<>();
    List<String> alfabet = new LinkedList<>();
    Map<Map.Entry<String, String>, String> tranzitii = new HashMap<>();

    public AF(){
    }

    public AF(List<String> stari, String stareInitiala, List<String> stariFinale, List<String> alfabet, Map<Map.Entry<String, String>, String> tranzitii){
        this.stari =stari;
        this.stareInitiala=stareInitiala;
        this.stariFinale=stariFinale;
        this.alfabet=alfabet;
        this.tranzitii=tranzitii;
    }

    public void readAF(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/laura-elena.olaru/Downloads/LFTC/Lab3/src/main/java/data/"+file))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (int i = 0; i < lines.size(); i++) {
                List<String> lineElements = Arrays.stream(lines.get(i).split(",")).collect(Collectors.toList());
                if (i == 0) {
                    stari.addAll(lineElements);
                }
                if (i == 1) {
                    stareInitiala = lineElements.get(0);
                }
                if (i == 2) {
                    stariFinale.addAll(lineElements);
                }
                if (i == 3) {
                    alfabet.addAll(lineElements);
                }
                if (i >= 4) {
                    addTranzitie(lineElements);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afiseazaStari() {
        System.out.println("Stari:");
        stari.forEach(System.out::println);
    }

    public void afiseazaStareaInitiala() {
        System.out.println("Stare initiala:");
        System.out.println(stareInitiala);
    }

    public void afiseazaStariFinale() {
        System.out.println("Stari finale:");
        stariFinale.forEach(System.out::println);
    }

    public void afiseazaAlfabet() {
        System.out.println("Alfabet:");
        alfabet.forEach(System.out::println);
    }

    public void afiseazaTranzitii(){
        System.out.println("Tranzitii:");
        tranzitii.entrySet().forEach(e-> System.out.println("("+e.getKey().getKey()+","+e.getKey().getValue()+")->"+e.getValue()));
    }

    public void setStari(List<String> stari) {
        this.stari.addAll(stari);
    }

    public void setStareaInitiala(String stareInitiala) {
        this.stareInitiala = stareInitiala;
    }

    public void setStariFinale(List<String> stariFinale) {
        this.stariFinale = stariFinale;
    }

    public void setAlfabet(List<String> alfabet) {
        this.alfabet = alfabet;
    }

    public void addTranzitie(List<String> tranzitie){
        tranzitii.put(Map.entry(tranzitie.get(0), tranzitie.get(1)), tranzitie.get(2));
    }

    public void setTranzitii(List<List<String>> tranzitii){
        //this.tranzitii = tranzitii;
        tranzitii.forEach(this::addTranzitie);
    }

    public String prefix(String secv){
        String prefix = "";
        String processed = "";
        String stareCurenta = this.stareInitiala;

        if(secv.length()==0){
            if(stariFinale.contains(stareInitiala))
                return "";
            else
                return "Secventa vida nu este acceptata";
        }

        for (char c : secv.toCharArray()) {
            String simbol = String.valueOf(c);
            if(!alfabet.contains(simbol))
                return "";
            if(stariFinale.contains(stareCurenta))
            {
                if(secv.length()==processed.length())
                    return processed;
                else
                    prefix = processed;
            }
            processed+=simbol;

            if(!tranzitii.containsKey(Map.entry(simbol, stareCurenta)))
                return prefix;
            stareCurenta=tranzitii.get(Map.entry(simbol, stareCurenta));
        }
        if(stariFinale.contains(stareCurenta))
        {
            if(secv.length()==processed.length())
                return processed;
            else
                prefix = processed;
        }
        return prefix;
    }
}
