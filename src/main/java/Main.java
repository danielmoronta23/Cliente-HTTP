import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String URL = "";
    private static Document doc;
    private static Connection conect;

    public static void main(String[] args) {
        System.out.println("Esta es la primera practica de ISC-415-DANIEL P. MORONTA-2016-1226");
        System.out.println("Incerte la URL: ");
        URL = new Scanner(System.in).nextLine();
        System.out.println("La URL incertada es: "+ URL + "\n");
        if(validandoURL(URL)==1) {
            System.out.println("Realizando Operaciones..............................................\n");

            System.out.println("a) Cantidad de linea               = " + cantLineas(URL)+"\n");
            System.out.println("b) Cantidad de de Parrafos         = " + canParrafos(doc)+"\n");
            System.out.println("c) Imagenes dentro de parrafos     = " + cantImagenes(doc)+"\n");
            System.out.println("d) Cantidad de Formulario          = " + ElementosFormulario(doc).size() + "   GET=" + CanFormularioGET(doc) + "    POST=" + CantFormularioPOST(doc)+"\n");
            System.out.println("e) Cantidad de Imagenes            = " + cantImagenes(doc)+"\n");
            System.out.println("f) Entra formulario:" + entradas(doc));

            try {
                System.out.println("F) Repuesta del Server : \n" + repuestaPost(doc)+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        Elements parrafos = doc.getElementsByTag("p");//un arrreglo con de las etiqueta <<p>>
        return parrafos.size();//la cant de veces que esta la etiqueta.

    }
    public static int cantImagenes(Document doc){

        Elements parrafos = doc.getElementsByTag("p");

        int cantImagen=0;

        for (Element p: parrafos) {
            cantImagen += p.getElementsByTag("img").size();
        }
        return cantImagen;
    }
    private static Elements ElementosFormulario(Document doc){
        Elements formulario = doc.getElementsByTag("form");
        return formulario;
    }
    private static int CanFormularioGET(Document doc) {

        Elements formulario = ElementosFormulario(doc);
        int cantGET = 0;
        for (Element form: formulario) {
            if (form.attr("method").equalsIgnoreCase("GET"))
                cantGET++;
        }
        return cantGET++;
    }
    private static int CantFormularioPOST(Document doc) {
        int cantPOST = 0;
        Elements formulario = ElementosFormulario(doc);

        for (Element form: formulario) {
            if (form.attr("method").equalsIgnoreCase("POST"))
                cantPOST++;
        }
        return cantPOST;
    }
    private static String entradas(Document doc) {
        String entrada  = "";
        int cantFormulario = 1;

        for (Element form: ElementosFormulario(doc)) {
            int cantAux= 0;
            entrada +="\n" +"Formulario numero: "+(cantFormulario++)+":\n";
            for (Element entre: form.getElementsByTag("input")) {
                entrada += (++cantAux) + "->Tipo de etiqueta entrada: " + entre.attr("type").toString() + "\n";
            }
        }
        return entrada;
    }
    private static String repuestaPost(Document doc) throws IOException {
        String repuesta = "";
        for (Element form: ElementosFormulario(doc)) {
            if (form.attr("method").equalsIgnoreCase("POST")){
                Connection.Response responseText = ((FormElement) form)
                        .submit()
                        .data("asignatura", "practica1")
                        .header("matricula","2016-1226")
                        .execute();
                repuesta += responseText.body() + "\n";
            }
        }
        return repuesta;
    }
}
