package es.upm.dit.adsw.practica1;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase para representar contenidos de los ficheros de viales que distribuye
 * el ayuntamiento de Madrid
 * 
 * @author mmiguel
 *
 */
public class Callejero {

	private static final int COD_VIA=0;
	private static final int VIA_CLASE=1;
	private static final int VIA_PAR=2;
	private static final int VIA_NOMBRE=3;
	private static final int VIA_NOMBRE_ACENTOS=4;
	private static final int COD_VIA_COMIENZA=5;
	private static final int CLASE_COMIENZA=6;
	private static final int PARTICULA_COMIENZA=7;
	private static final int NOMBRE_COMIENZA=8;
	private static final int NOMBRE_ACENTOS_COMIENZA=9;
	private static final int COD_VIA_TERMINA=10;
	private static final int CLASE_TERMINA=11;
	private static final int PARTICULA_TERMINA=12;
	private static final int NOMBRE_TERMINA=13;
	private static final int NOMBRE_ACENTOS_TERMINA=14;

	protected static final String fichero="VialesVigentes_20201220.csv";
	protected Via[] vias;

	/**
	 * Constructor de callejero a partir de algun tipo de stream que 
	 * incluye las vias del callejero. Ese stream esta soportado con un Scanner. 
	 * El scanner incluye el contenido del callejero y el constructor lee el stream 
	 * que debe estar en formato csv
	 * 
	 * @param viales scanner del que extraemos el contenido del callejero
	 * @param numViales numero de viales que incluye el scanner
	 */
	public Callejero(Scanner viales,int numViales) {
		vias=new Via[numViales];
		String[] vias_csv;
		for (int i=0; i < numViales; i++) {
			String linea=viales.nextLine();
			vias_csv=linea.split(";");
			vias[i]=new Via(Integer.parseInt(vias_csv[COD_VIA]),vias_csv[VIA_CLASE],vias_csv[VIA_PAR],
					vias_csv[VIA_NOMBRE],vias_csv[VIA_NOMBRE_ACENTOS],
					Integer.parseInt(vias_csv[COD_VIA_COMIENZA]),Integer.parseInt(vias_csv[COD_VIA_TERMINA]));
			if ((i == numViales-1 && viales.hasNext()) || (i < numViales-1 && !viales.hasNext()))
				throw new RuntimeException("Formato fichero errorneo");
		}
		viales.close();
		ordenaViasPorNombre();
	}
	
	/**
	 * Metodo que ordena las vias en función del código de via.
	 * Debe ser utilizado unicamente para hacer pruebas
	 */
	public void ordenaVias() {
		// TODO
		mergeCodigo(vias,0,(vias.length-1)/2,vias.length-1);
		mergeSortCodigo(vias,0,vias.length-1);
		
	}
	
	/**
	 * Imprime en salida estandar todos los viales del callejero
	 */
	public void printViales() {
		for (Via via : vias) {
			via.formatPrint();
			System.out.println();
		}
	}
	
	/**
	 * Devuelve las vias del callejero
	 * @return vias del callejero
	 */
	public Via[] getVias() {
		ordenaVias();
		return vias;
	}
	
	/**
	 * Fija las vias del callejero. Debe estar completamente inicializada
	 * @param vias nuevas vias del callejero
	 */
	public void setVias(Via[] vias) {
		this.vias = vias;
	}
	
	/**
	 * Devuelve el cojunto de vias del callejero ordenadas por nombre
	 * 
	 * @return conjunto de vias ordenadas por nombre 
	 */
	public Via[] ordenaViasPorNombre() {
		// TODO
		Via vias_aux[]= vias;
		mergeNombre(vias,0,(vias_aux.length-1)/2,vias_aux.length-1);
		mergeSortNombre(vias_aux,0,vias.length-1);
		return vias_aux;
	}
	
	/**
	 * Algoritmo MergeSort. Complejidad O(nlog(n)) en todos los casos
	 * http://puntocomnoesunlenguaje.blogspot.com/2014/10/java-mergesort.html
	 */

	private static void mergeCodigo(Via vias[], int izq, int m, int der) {
		int i, j, k;
		Via[] B = new Via[vias.length]; // array auxiliar
		for (i = izq; i <= der; i++) // copia ambas mitades en el array auxiliar
			B[i] = vias[i];

		i = izq;
		j = m + 1;
		k = izq;

		while (i <= m && j <= der) // copia el siguiente elemento más grande
			if (B[i].getCodigo() <= B[j].getCodigo())
				vias[k++] = B[i++];
			else
				vias[k++] = B[j++];

		while (i <= m) // copia los elementos que quedan de la
			vias[k++] = B[i++]; // primera mitad (si los hay)
	}

	private static void mergeNombre(Via vias[], int izq, int m, int der) {
		int i, j, k;
		Via[] B = new Via[vias.length]; // array auxiliar
		for (i = izq; i <= der; i++) // copia ambas mitades en el array auxiliar
			B[i] = vias[i];

		i = izq;
		j = m + 1;
		k = izq;

		while (i <= m && j <= der) // copia el siguiente elemento más grande
			if (B[i].getNombre().compareTo(B[j].getNombre()) <= 0) // <=
				vias[k++] = B[i++];
			else
				vias[k++] = B[j++];

		while (i <= m) // copia los elementos que quedan de la
			vias[k++] = B[i++]; // primera mitad (si los hay)
	}
	
	private static void mergeSortCodigo(Via vias[],int izq, int der){
	    if (izq < der){
	            int m=(izq+der)/2;
	            mergeSortCodigo(vias,izq, m);
	            mergeSortCodigo(vias,m+1, der);                                                                                
	            mergeCodigo(vias,izq, m, der);                                                                                 
	    }
	}
	
	private static void mergeSortNombre(Via vias[],int izq, int der){
	    if (izq < der){
	            int m=(izq+der)/2;
	            mergeSortNombre(vias,izq, m);
	            mergeSortNombre(vias,m+1, der);                                                                                
	            mergeNombre(vias,izq, m, der);                                                                                 
	    }
	}

	public static void main(String[] args) {
		
		try {
			FileInputStream fi=new FileInputStream(fichero);
			Scanner viales=new Scanner(fi);
			int lineas=0;
			while (viales.hasNext()) {
				lineas++;
				viales.nextLine();
			}
			viales.close();
			fi=new FileInputStream(fichero);
			viales=new Scanner(fi);
			viales.nextLine(); // nos saltamos las cabeceras del fichero
			Callejero c=new Callejero(viales,lineas-1);
			c.printViales();
		} catch(IOException e) {
			e.printStackTrace();
		}
		//ordenaVias();
		
	}
}
