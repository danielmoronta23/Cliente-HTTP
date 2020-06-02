import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Scanner;

public class Main {
    private static String URL = "";
    private static Document doc;
    private static Connection conect;

    public static void main(String[] args) {
        System.out.println("Esta es la primera practica de ISC-415-DANIEL P. MORONTA-2016-1226");
        System.out.println("Incerte la URL: ");
        URL = new Scanner(System.in).nextLine();
        System.out.println(URL);
        if(validandoURL(URL)==1) {
            System.out.println("Realizando Operaciones...");
            System.out.println("a) Cantidad de linea = " + cantLineas(URL));
        }

    }
    public static int validandoURL(String URL){
        try {
            doc =  Jsoup.connect(URL).get();
            Jsoup.connect(URL).timeout(5000);
            System.out.println("Coneccion establecida! ");
            return 1;
        } catch (Exception e) {
            System.out.println("La coneccion no se puedo establecer");
            System.exit(0);
            return 0;
        }
    }
    public static int cantLineas(String URL)  {
        int cant = 0;
        try{
            Connection.Response conecta = Jsoup.connect(URL).execute();
            cant = conecta.body().split("\n").length;
        }catch (Exception e){

        }
        return cant;
    }
}
