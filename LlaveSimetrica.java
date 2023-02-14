import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.*;

public class LlaveSimetrica {

	public static SecretKey Llave() {
		KeyGenerator keygen = null;
		try {
			// Generar llave simetrica "DES".
			keygen = KeyGenerator.getInstance("DES"); 
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Ocultar la llave
		SecretKey key = keygen.generateKey();
		return key;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Datos necesarios para entradas de texto/scaner
		Scanner sc = new Scanner(System.in);
		String ruta;
		String nomFic;
		int opcion;
		
		//Datos necesarios para criptografía
		Cipher desCipher;
		byte[] lectura;
		byte[] mensajeCifrado;
		byte[] mensajeDescifrado;

		// Llamar a la clave guardada
		SecretKey key = Llave(); 

		do {
			System.out.println("|-------- CIFRADO SIMETRICO 'DES' --------|");
			System.out.println("|            Opción 1: Cifrar             |");
			System.out.println("|            Opción 2: Descifrar          |");
			System.out.println("|            Opción 3: Salir              |");
			System.out.println("|-----------------------------------------|");

			System.out.print("Opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			if (opcion == 1) {

				// PROCESO DE CIFRADO
				
				// Pedimos al usuario la ruta y el nombre del archivo
				System.out.println("Introduzca la ruta del fichero a encriptar:"); 
				ruta = sc.nextLine();
				System.out.println("Introduzca el nombre del fichero a encriptar:"); 
				nomFic = sc.nextLine();

				Path fichero_a_leer = Paths.get(ruta, nomFic);

				try {
					// Introduzco la llave en el cifrador
					desCipher = Cipher.getInstance("DES"); // ACTIVAR CIFRADOR DES
					desCipher.init(Cipher.ENCRYPT_MODE, key); // Combino cifrador con la clave creada

					// Leo el fichero en una cadena byte.
					lectura = Files.readAllBytes(fichero_a_leer);
					System.out.println("Texto a encriptar: " + new String(lectura));

					// Cifro el contenido del mensaje
					mensajeCifrado = desCipher.doFinal(lectura); // Encripto la cadena

					// Muestro el mensaje cifrado
					System.out.println("Mensaje cifrado: " + new String(mensajeCifrado));

					// Guardo el fichero
					File fcipher = new File(ruta, "CIFRADO_" + nomFic);
					FileOutputStream escritor = new FileOutputStream(fcipher, false);
					escritor.write(mensajeCifrado); // Guardo en el fichero la cadena encriptada
					escritor.close(); // Cierro el buffer
					System.out.println("Fichero guardado en: " + fcipher);
					System.out.println("");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (opcion == 2) {

				// PROCESO DE DESCIFRADO
				
				// Pedimos al usuario la ruta y el nombre del archivo encriptado
				System.out.println("Introduzca la ruta del fichero a desencriptar:"); 
				ruta = sc.nextLine();
				System.out.println("Introduzca el nombre del fichero a desencriptar:"); 
				nomFic = sc.nextLine();

				Path fichero_a_desencriptar = Paths.get(ruta, nomFic);

				try {

					// Introduzco la llave en el descifrador
					desCipher = Cipher.getInstance("DES"); // ACTIVAR DESCIFRADOR DES
					desCipher.init(Cipher.DECRYPT_MODE, key); // Activo el modo descifrador con la llave

					// Leo el fichero binario
					lectura = Files.readAllBytes(fichero_a_desencriptar);
					System.out.println("Texto a desencriptar: " + new String(lectura));

					System.out.println("");

					// Descifro el contenido del mensaje
					mensajeDescifrado = desCipher.doFinal(lectura);

					// Muestro el mensaje cifrado
					System.out.println("Mensaje descifrado: " + new String(mensajeDescifrado));
					System.out.println("");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (opcion < 1 || opcion > 3) {
				System.out.println("Seleccione una opción válida.");
			}

		} while (opcion != 3);

		System.out.println("FIN DEL PROGRAMA.");
		sc.close();
	}
}
