import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class recargaTarjetasTranscaribe {
    private static final int MAX_TARJETAS = 3000; // Máximo número de tarjetas permitidas
    private static String[][] tarjetas = new String[MAX_TARJETAS][3]; // Matriz para almacenar las tarjetas (número, saldo, fecha)
    private static int totalTarjetas = 0; // Contador de tarjetas

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    agregarTarjeta(scanner);
                    break;
                case 2:
                    recargarTarjeta(scanner);
                    break;
                case 3:
                    consultarSaldo(scanner);
                    break;
                case 4:
                    eliminarTarjeta(scanner);
                    break;
                case 5:
                    mostrarTodasLasTarjetas();
                    break;
                case 6:
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("\nOpción inválida. Por favor, intenta nuevamente.");
                    break;
            }

            pausa();
            limpiarPantalla();
        } while (opcion != 6);
    }

    public static void mostrarMenu() {
        System.out.println("----- MENÚ -----");
        System.out.println("1. Agregar tarjeta");
        System.out.println("2. Recargar tarjeta");
        System.out.println("3. Consultar saldo");
        System.out.println("4. Eliminar tarjeta");
        System.out.println("5. Mostrar todas las tarjetas");
        System.out.println("6. Salir");
        System.out.print("Selecciona una opción: ");
    }

    public static void agregarTarjeta(Scanner scanner) {
        if (totalTarjetas >= MAX_TARJETAS) {
            System.out.println("Error: Se ha superado el límite máximo de tarjetas.");
            return;
        }

        System.out.print("\nIngrese el número de identificación de la tarjeta: ");
        String numeroTarjeta = scanner.nextLine();
        int indiceTarjeta = buscarTarjeta(numeroTarjeta);
        if (indiceTarjeta != -1) {
            System.out.println("Error: el número de identificación de la tarjeta ya existe.");
            return;
        }

        System.out.print("Ingrese el saldo inicial: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        String fechaRecarga = obtenerFechaActual();

        tarjetas[totalTarjetas][0] = numeroTarjeta;
        tarjetas[totalTarjetas][1] = String.valueOf(saldoInicial);
        tarjetas[totalTarjetas][2] = fechaRecarga;

        totalTarjetas++;
        System.out.println("Tarjeta agregada exitosamente.");
    }

    public static void recargarTarjeta(Scanner scanner) {
        System.out.print("\nIngrese el número de identificación de la tarjeta: ");
        String numeroTarjeta = scanner.nextLine();

        int indiceTarjeta = buscarTarjeta(numeroTarjeta);
        if (indiceTarjeta == -1) {
            System.out.println("Tarjeta no encontrada.");
            return;
        }

        System.out.print("Ingrese el monto de la recarga: ");
        double montoRecarga = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        double saldoActual = Double.parseDouble(tarjetas[indiceTarjeta][1]);
        double nuevoSaldo = saldoActual + montoRecarga;
        tarjetas[indiceTarjeta][1] = String.valueOf(nuevoSaldo);

        String fechaRecarga = obtenerFechaActual();
        tarjetas[indiceTarjeta][2] = fechaRecarga;

        System.out.println("Recarga realizada exitosamente.");
    }

    public static void consultarSaldo(Scanner scanner) {
        System.out.print("\nIngrese el número de identificación de la tarjeta: ");
        String numeroTarjeta = scanner.nextLine();
        int indiceTarjeta = buscarTarjeta(numeroTarjeta);
        if (indiceTarjeta == -1) {
            System.out.println("Tarjeta no encontrada.");
            return;
        }

        double saldo = Double.parseDouble(tarjetas[indiceTarjeta][1]);
        System.out.println("El saldo de la tarjeta " + numeroTarjeta + " es: " + saldo);
    }

    public static void eliminarTarjeta(Scanner scanner) {
        System.out.print("\nIngrese el número de identificación de la tarjeta: ");
        String numeroTarjeta = scanner.nextLine();

        int indiceTarjeta = buscarTarjeta(numeroTarjeta);
        if (indiceTarjeta == -1) {
            System.out.println("Tarjeta no encontrada.");
            return;
        }

        for (int i = indiceTarjeta; i < totalTarjetas - 1; i++) {
            tarjetas[i] = tarjetas[i + 1];
        }

        totalTarjetas--;
        System.out.println("Tarjeta eliminada exitosamente.");
    }

    public static void mostrarTodasLasTarjetas() {
        if (totalTarjetas == 0) {
            System.out.println("No hay tarjetas registradas.");
            return;
        }

        System.out.println("\n----- TARJETAS REGISTRADAS -----");
        for (int i = 0; i < totalTarjetas; i++) {
            String numeroTarjeta = tarjetas[i][0];
            double saldo = Double.parseDouble(tarjetas[i][1]);
            String fechaRecarga = tarjetas[i][2];

            System.out.println("- Tarjeta: " + numeroTarjeta + ", Saldo: " + saldo + ", Fecha de recarga: " + fechaRecarga);
        }
    }

    public static int buscarTarjeta(String numeroTarjeta) {
        for (int i = 0; i < totalTarjetas; i++) {
            if (tarjetas[i][0].equals(numeroTarjeta)) {
                return i;
            }
        }
        return -1; // Tarjeta no encontrada
    }

    public static void pausa() {
        try {
            System.out.print("Presione Enter para continuar...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String obtenerFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatoFecha.format(fechaActual);
    }
}