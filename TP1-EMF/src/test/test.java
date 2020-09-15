package test;

import maps.*;

import java.util.Scanner;

import org.eclipse.emf.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//Je charge l'instance map.xmi du méta-modèle maps.ecore
		Resource resource = chargerModele("model/exo4.xmi", MapsPackage.eINSTANCE);
		if (resource == null) System.err.println(" Erreur de chargement du modèle");
		
		
		//Instruction récupérant le modèle sous forme d'arbre à partir de la classe racine "map"
		map maMap = (map) resource.getContents().get(0);
						
		System.out.println("Ma map : " + maMap.getName());
		
		System.out.println("Les rues de ma map :");
		for(int i = 0; i < maMap.getRoad().size();i++) {
			Road maRoute = (Road) maMap.getRoad().get(i);
			if(maRoute instanceof Street) {
				System.out.println(" - " + maRoute.getName());
			}
		}
		
		System.out.println("Les rues piétonnes de plus d'1 km de ma map :");
		for(int i = 0; i < maMap.getRoad().size();i++) {
			Road maRoute = (Road) maMap.getRoad().get(i);
			if((maRoute instanceof Pedestrian) && (maRoute.getLength() > 1000)) {
				System.out.println(" - " + maRoute.getName());
			}
		}
		
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nom de la route recherchée :");
		String route_recherchee = sc.nextLine();
		for(int i = 0; i < maMap.getRoad().size();i++) {
			Road maRoute = (Road) maMap.getRoad().get(i);
			if(maRoute.getName().equals(route_recherchee)) {
				EList<Road> routeAdj = maRoute.getMeet();
				System.out.println("Est adjacente à :");
				for(Road r : routeAdj) {
					System.out.println(" - " + r.getName());
					}
				}
			}
		
}

		

	
	
	public static Resource chargerModele(String uri, EPackage pack) {
		   Resource resource = null;
		   try {
		      URI uriUri = URI.createURI(uri);
		      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		      resource = (new ResourceSetImpl()).createResource(uriUri);
		      XMLResource.XMLMap xmlMap = new XMLMapImpl();
		      xmlMap.setNoNamespacePackage(pack);
		      java.util.Map options = new java.util.HashMap();
		      options.put(XMLResource.OPTION_XML_MAP, xmlMap);
		      resource.load(options);
		   }
		   catch(Exception e) {
		      System.err.println("ERREUR chargement du modèle : "+e);
		      e.printStackTrace();
		   }
		   return resource;
		}

}