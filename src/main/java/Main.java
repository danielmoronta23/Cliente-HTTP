import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            System.out.println("a) Cantidad de linea        = " + cantLineas(URL));
            System.out.println("b) Cantidad de de Parrafos  = "+ canParrafos(doc));
            System.out.println("c) Cantidad de Imagenes     = "+ cantImagenes(doc));
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
    public static int canParrafos(Document doc){

        Elements paragraphs = doc.getElementsByTag("p");//un arrreglo con de las etiqueta <<p>>
        return paragraphs.size();//la cant de veces que esta la etiqueta.

    }
    public static int cantImagenes(Document doc){

        Elements parrafos = doc.getElementsByTag("p");

        int cantImagen=0;

        for (Element p: parrafos) {
            cantImagen += p.getElementsByTag("img").size();
        }
        return cantImagen;
    }
}
