package es.upm.dit.adsw.practica1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class PruebaCallejero {
	
protected static final String fichero="VialesVigentes_20201220.csv";

@Test
public void test1() {
	
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
		Callejero callejero1  = new Callejero(viales,lineas-1);
		callejero1.ordenaVias();
		Via vias[] = callejero1.getVias();
		Assert.assertArrayEquals(vias, callejero1.getVias());
		callejero1.printViales();
		
	} catch(IOException e) {
		e.printStackTrace();
	}
	
}

@Test
public void test2() {

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
			Callejero callejero2  = new Callejero(viales,lineas-1);
			callejero2.ordenaVias();
			Via vias[] = callejero2.getVias();

			for(int i=0; i<vias.length-1;i++) {
			
				Assert.assertEquals(null, vias[i].getViaComienzo());
				Assert.assertEquals(null, vias[i].getViaTermina());

			}
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}

}

@Test
public void test3() {
	try {
		boolean comprobacion = true;

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
		Callejero callejero3  = new Callejero(viales,lineas-1);
		Via vias []= callejero3.getVias();
		long time_ref = System.nanoTime(); 
		callejero3.ordenaVias();
		long time1 = System.nanoTime()-time_ref;
		
		for (int i = 0; i < vias.length - 1; i++) {
			int m = i;
			for (int j = i+1; j < vias.length; j++) {
			if (vias[j].getCodigo() < vias[m].getCodigo())
			m = j;
			}
			Via aux=vias[i];
			vias[i]=vias[m];
			vias[m]=aux;
			}
		
		long time2 = System.nanoTime()-time1;
		
		if(time2>2*time1) {
			System.out.println("");
			System.out.println("TEST 3: ordenaVias()");
			System.out.println("Tiempo mergeSort: " + time1 + " ns");
			System.out.println("Tiempo selectionSort: " + time2 + " ns");
			System.out.println("");
			comprobacion = true;
		}else {
			comprobacion = false;
		}
		Assert.assertTrue(comprobacion);
		
	} catch(IOException e) {
		e.printStackTrace();
	}
	
}

@Test
public void test4() {

	try {
		boolean comprobacion = true;

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
		Callejero callejero3  = new Callejero(viales,lineas-1);
		Via vias []= callejero3.getVias();
		long time_ref = System.nanoTime(); 
		callejero3.ordenaViasPorNombre();
		long time1 = System.nanoTime()-time_ref;
		
		for (int i = 0; i < vias.length - 1; i++) {
			int m = i;
			for (int j = i+1; j < vias.length; j++) {
			if (vias[j].getCodigo() < vias[m].getCodigo())
			m = j;
			}
			Via aux=vias[i];
			vias[i]=vias[m];
			vias[m]=aux;
			}
		
		long time2 = System.nanoTime()-time1;
		
		if(time2>2*time1) {
			System.out.println("");
			System.out.println("TEST 4: ordenaViasPorNombre()");
			System.out.println("Tiempo mergeSort: " + time1 + " ns");
			System.out.println("Tiempo selectionSort: " + time2 + " ns");
			System.out.println("");

			comprobacion = true;
		}else {
			comprobacion = false;
		}
		Assert.assertTrue(comprobacion);
		
	} catch(IOException e) {
		e.printStackTrace();
	}
	
}

}
