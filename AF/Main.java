import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        AF af = new AF();

        System.out.println("CITIRE ELEMENTE AF:\n1. Din fisier\n2. De la tastatura\n");
        Integer input = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (input.equals(1)) {
            menuFromFile(af);
        } else if (input.equals(2)) {
            menuFromConsole(af);
        }else {
            System.out.println("Alegeti o optiune: 1,2");
        }

        System.out.println("AFISARE ELEMENTE AF:\n1. Da\n2. Nu\n");

        try {
            input = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (input.equals(1)) {
            showAF(af);
        } else if (input.equals(2)) {

        }else {
            System.out.println("Alegeti o optiune: 1,2");
        }

        System.out.println("VERIFICA O SECVENTA:\n1. Da\n2. Nu\n");

        try {
            input = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String secventa = "";
        if (input.equals(1)) {
            while(true) {
                System.out.println("INTRODUCETI SECVENTA: ");
                try {
                    secventa = reader.readLine();
                    if(secventa.equals("x"))
                        return;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String prefix = af.prefix(secventa);
                if (prefix.equals(secventa)) {
                    System.out.println("Secventa este acceptata!\n");
                } else {
                    System.out.println("Secventa nu este acceptata!\n");
                }

                System.out.println("Cel mai lung prefix este: " + prefix);
            }
        } else if (input.equals(2)) {
            return;
        }else {
            System.out.println("Alegeti o optiune: 1,2");
        }
    }

    public static void showAF(AF af){
        Integer input;
        do {
            System.out.println("ALEGETI O OPTIUNE:\n1. Multimea starilor\n2. Alfabetul\n3. Tranzitiile\n4. Multimea starilor finale\n5. EXIT\n");

            input = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (input.equals(1)) {
                af.afiseazaStari();
            } else if (input.equals(2)) {
                af.afiseazaAlfabet();
            } else if (input.equals(3)) {
                af.afiseazaTranzitii();
            } else if (input.equals(4)) {
                af.afiseazaStariFinale();
            } else if (input.equals(5)) {
                return;
            }else{
                System.out.println("Alegeti o optiune: 1-5");
            }
        }
        while(!input.equals(6));
    }

    public static void menuFromConsole(AF af){
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        map.put(1,0);
        map.put(2,0);
        map.put(3,0);
        map.put(4,0);
        map.put(5,0);

        Integer input;
        do {
            System.out.println("INTRODUCETI:\n1. Multimea starilor\n2. Alfabetul\n3. Tranzitiile\n4. Starea initiala \n5. Multimea starilor finale\n");

            input = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (input.equals(1)) {
                List<String> list = adaugaStari(af);
                af.setStari(list);
                map.put(1,1);
            } else if (input.equals(2)) {
                List<String> list = adaugaAlfabet(af);
                af.setAlfabet(list);
                map.put(2,1);
            } else if (input.equals(3)) {
                List<List<String>> list = adaugaTranzitii(af);
                af.setTranzitii(list);
                map.put(3,1);
            } else if (input.equals(4)) {
                String stare = adaugaStareInitiala(af);
                af.setStareaInitiala(stare);
                map.put(4,1);
            }else if (input.equals(5)) {
                List<String> list = adaugaStariFinale(af);
                af.setStariFinale(list);
                map.put(5,1);
            }

            List<Integer> lista = new LinkedList<>();
            for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                if (entry.getValue().equals(0))
                    lista.add(entry.getKey());
            }
            System.out.println("OPTIUNILE RAMASE: " + lista+ "\n");
        } while(map.containsValue(0));
    }

    public static void menuFromFile(AF af) {
        System.out.println("INTRODUCETI NUMELE FISIERULUI: ");

        String file = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            file = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        af.readAF(file);
    }

    public static List<String> adaugaStari(AF af){
        List<String> list = new LinkedList<>();
        System.out.println("INTRODUCETI NUMARUL DE STARI: ");

        Integer input = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(reader.readLine());

            String stare;
            for (int i = 0; i < input; i++) {
                System.out.println("INTRODUCETI STAREA: ");
                stare = reader.readLine();
                list.add(stare);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> adaugaAlfabet(AF af){
        List<String> list = new LinkedList<>();
        System.out.println("INTRODUCETI NUMARUL DE SIMBOLURI: ");

        Integer input = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(reader.readLine());

            String simbol;
            for (int i = 0; i < input; i++) {
                System.out.println("INTRODUCETI SIMBOLUL: ");
                simbol = reader.readLine();
                list.add(simbol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<List<String>> adaugaTranzitii(AF af){
        List< List<String>> list = new LinkedList<>();
        System.out.println("INTRODUCETI NUMARUL DE TRANZITII: ");

        Integer input = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(reader.readLine());

            List<String> lista;
            for (int i = 0; i < input; i++) {
                System.out.println("INTRODUCETI TRANZITIA SUB FORMA: simbol,starea1,starea2\n");
                lista = Arrays.stream(reader.readLine().split(",")).collect(Collectors.toList());
                list.add(lista);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> adaugaStariFinale(AF af){
        List<String> list = new LinkedList<>();
        System.out.println("INTRODUCETI NUMARUL DE STARI FINALE: ");

        Integer input = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(reader.readLine());

            String stare;
            for (int i = 0; i < input; i++) {
                System.out.println("INTRODUCETI STAREA FINALA: ");
                stare = reader.readLine();
                list.add(stare);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String adaugaStareInitiala(AF af){
        String stare = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("INTRODUCETI STAREA INITIALA: ");
            stare = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stare;
    }

}
