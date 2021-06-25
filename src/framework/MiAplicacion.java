package framework;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MiAplicacion {
    private final List<Accion> acciones;

    public MiAplicacion() {
        acciones = new ArrayList<>();
        cargar();
    }

    private void cargar() {
        Set<Class<? extends Accion>> clases = new Reflections("").getSubTypesOf(Accion.class);
        clases.forEach(clase -> {
            try {
                acciones.add(clase.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("");
            }
        });
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int valor, fin;
        System.out.println("Bienvenido!\nEstas son sus opciones: ");
        do {
            int i = 0;
            for (Accion accion : acciones) {
                System.out.println(i + ". " + accion.nombreItemMenu() + ": " + accion.descripcionItemMenu());
                i++;
            }
            fin = i;
            System.out.println(fin + ". Salir");
            System.out.println("Ingrese su opción: ");
            valor = scanner.nextInt();
            if (valor == fin) {
                System.out.println("Fin de la ejecución.");
            } else {
                try {
                    acciones.get(valor).ejecutar();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Opción invalida.\n");
                }
            }
        } while (valor != fin);
    }
}
