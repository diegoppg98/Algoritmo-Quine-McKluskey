import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	private static final int MAXTERMS = 2;
	private static final int MINTERMS = 3;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);  
		ArrayList<String> minTerms = new ArrayList<String>();
		ArrayList<String> maxTerms = new ArrayList<String>();
		ArrayList<String> finalTerms = new ArrayList<String>();
		ArrayList<String> primerosImplicantes = new ArrayList<String>();
		ArrayList<String>  primerosImplicantesEsenciales = new ArrayList<String>();
		String[] noNi = null;
		String []terms = null;
		ArrayList<String> termsNoNi = new ArrayList<String>();
		int varNumber = -1, optionFunction = -1;
		
		System.out.println("¿Cuántas variables tiene la función?");
		
		try{
			varNumber = sc.nextInt();
		}catch (InputMismatchException exception) {
			System.out.println("La entrada debe ser un integer");
			System.exit(0);
		}		
		
		if(varNumber < 1) {
			System.out.println("La entrada debe ser mayor que cero");
			System.exit(0);
		}
		System.out.println("¿Cómo se va a introducir la función?");
		System.out.println("  1.Forma no canónica");
		System.out.println("  Forma canónica:");
		System.out.println("    2.Maxterms");
		System.out.println("    3.Minterms");
		
		try{
			optionFunction = sc.nextInt();
		}catch (InputMismatchException exception) {
			System.out.println("La entrada debe ser un integer");
			System.exit(0);
		}	
		
		if(optionFunction<1 || optionFunction>3) {
			System.out.println("La entrada debe ser un integer entre 1 y 3");
			System.exit(0);
		}
		int resultado = (int) Math.pow(2, varNumber);
		
		if(optionFunction == 2 || optionFunction == 3) {
			sc.nextLine();
			System.out.println("Introduce los términos separados por comas. Entre 0 y "+(resultado-1));	
			String t = sc.nextLine();       
			terms = t.split(",");
			
			//Se añaden a termsNoNi los terminos
			for(int j=0; j<terms.length; j++) {
				if(!terms[j].equals("")) {
					int termEntrada = -1;
					try{
						termEntrada = Integer.parseInt(terms[j]);
					}catch (NumberFormatException exception) {
						System.out.println("La entrada de los terminos deben ser integer separados por comas");
						System.exit(0);
					}	
					if(termEntrada<0 || termEntrada>resultado-1) { 
						System.out.println("La entrada debe ser un integer entre 0 y "+(resultado-1));
						System.exit(0);
					}
					termsNoNi.add(terms[j]);
				}				
			}
			if(termsNoNi.isEmpty()) {
				System.out.println("La entrada la terminos no puede ser vacia");
				System.exit(0);
			}
			
			System.out.println("Introduce los términos NO/NI separados por comas. Entre 0 y "+(resultado-1));	
			String noni = sc.nextLine();	
	        
	        noNi = noni.split(",");
	      //Se añaden a termsNoNi los terminos no ocurre/no importa 
			for(int i=0; i<noNi.length; i++) {
				if(!termsNoNi.contains(noNi[i]) && !noNi[i].equals("")) {
					int termEntrada = -1;
					try{
						termEntrada = Integer.parseInt(noNi[i]);
					}catch (NumberFormatException exception) {
						System.out.println("La entrada de los terminos deben ser integer separados por comas");
						System.exit(0);
					}	
					if(termEntrada<0 || termEntrada>resultado-1) { 
						System.out.println("La entrada debe ser un integer entre 0 y "+(resultado-1));
						System.exit(0);
					}
					termsNoNi.add(noNi[i]);
				}	
			}
			
			if(optionFunction == 2) {
				//Se añaden los maxterms
				for(int i=0; i<termsNoNi.size(); i++){
					maxTerms.add(Utils.termBinary(varNumber, termsNoNi.get(i)));
				}
				//Se añaden los minterms complementarios
				for(int i=0; i <resultado;i++) {
					String element = Utils.termBinary(varNumber, Integer.toString(i));
					if(!maxTerms.contains(element)) {
						minTerms.add(element);
					}
				}
				for(int i=0; i<noNi.length; i++) {
					if(!noNi[i].equals(""))
						minTerms.add(Utils.termBinary(varNumber, noNi[i]));
				}
			}
			else {
				//Se añaden los minterms
				for(int i=0; i<termsNoNi.size(); i++){
					minTerms.add(Utils.termBinary(varNumber, termsNoNi.get(i)));
				}
				//Se añaden los maxterms complementarios
				for(int i=0; i <resultado;i++) {
					String element = Utils.termBinary(varNumber, Integer.toString(i));
					if(!minTerms.contains(element)) {
						maxTerms.add(element);
					}
				}
				for(int i=0; i<noNi.length; i++) {
					if(!noNi[i].equals(""))
						maxTerms.add(Utils.termBinary(varNumber, noNi[i]));
				}
			}
		}
		
		else if(optionFunction == 1) {
			sc.nextLine();
			System.out.println("Introduce la forma no canonica (Para los términos negados utilice la variable en mayúscula). Entre A y "+String.valueOf((char)(varNumber-1+ 'A')));	
			System.out.println("Forma de introducir suma de productos: ab+abc+a");
			System.out.println("Forma de introducir producto de sumas: a+b*a+b+c*a");
			ArrayList<String> opcionEntradas = new ArrayList<String>();
			for(int i=0;i<varNumber;i++){
				opcionEntradas.add(String.valueOf((char)(i+ 'A')));
			}
			String formaNoCanonica = sc.nextLine(); 
			String [] ComprobacionformaNoCanonica= formaNoCanonica.split("");
			for(int i=0;i<ComprobacionformaNoCanonica.length;i++) {
				if(!ComprobacionformaNoCanonica[i].equals("") && !ComprobacionformaNoCanonica[i].equals("*") && !ComprobacionformaNoCanonica[i].equals("+")) {
					if(!opcionEntradas.contains(ComprobacionformaNoCanonica[i].toUpperCase())) {
						System.out.println("La entrada debe ser entre A y "+String.valueOf((char)(varNumber-1+ 'A')));
						System.exit(0);
					}	
				}
			}

			ArrayList<String> formaCanonica = new ArrayList<String>();
			//Se transforma la entrada en forma canonica
			formaCanonica=Utils.implicantesToCanonica(formaNoCanonica,varNumber);
			terms = new String[formaCanonica.size()];
			String [] formaNoCanonicaMax = formaNoCanonica.split("[*]");
			if(!(formaNoCanonicaMax.length == 1)) {
				//Se añaden los minterms complementarios y los terminos
				maxTerms = formaCanonica;
				optionFunction = MAXTERMS;
				int n=0;
				for(int i=0; i <resultado;i++) {
					String element = Utils.termBinary(varNumber, Integer.toString(i));
					if(!maxTerms.contains(element)) {
						minTerms.add(element);
					}
					else {
						terms[n]=String.valueOf(i);
						n++;
					}		
				}
			}
			else {
				//Se añaden los maxterms complementarios y los terminos
				optionFunction = MINTERMS;
				minTerms = formaCanonica;
				int n=0;
				for(int i=0; i <resultado;i++) {
					String element = Utils.termBinary(varNumber, Integer.toString(i));
					if(!minTerms.contains(element)) {
						maxTerms.add(element);
					}
					else {
						terms[n]=String.valueOf(i);
						n++;
					}		
				}
			}

		}
			
		System.out.println();
		sc.close();
		ArrayList<ArrayList<String>> sortedArrayZeros = Utils.sortByZeros(maxTerms, varNumber);
		ArrayList<ArrayList<String>> sortedArrayOnes = Utils.sortByOnes(minTerms, varNumber);				
		ArrayList<String> formaFinalMaxTerms  = new ArrayList<String>();
		ArrayList<String> formaFinalMinterms  = new ArrayList<String>();		
        ArrayList<ArrayList<String>> ultimoArray = new ArrayList<ArrayList<String>>();
        
        //Se realizara el proceso dos veces, una para minterms y otra para maxterms
        for(int forma=0; forma<2; forma++) { 
        	int fin = 0;
        	finalTerms.clear(); primerosImplicantes.clear(); primerosImplicantesEsenciales.clear();
        	if(forma == 0) {
        		if(optionFunction == MINTERMS)
        			ultimoArray = sortedArrayOnes;
            	else 
            		ultimoArray = sortedArrayZeros;
        	}
        	else {
            	if (optionFunction == MINTERMS)
            		ultimoArray = sortedArrayZeros;
            	else
            		ultimoArray = sortedArrayOnes;
        	}
        	
            //Se minimizan los términos por grupos
    		while(fin == 0) {
    			ArrayList<ArrayList<ArrayList<String>>> min = Utils.minimizeArray(ultimoArray);
    			if(Utils.isEmptyArrayList(min.get(1))) {
    				fin = 1;
    			}		
    			for(int j=0; j<min.get(0).size(); j++){
    				for(int k=0; k<min.get(0).get(j).size(); k++){
    					if(!finalTerms.contains(min.get(0).get(j).get(k)))
    						finalTerms.add(min.get(0).get(j).get(k));
    				}				
    			}
    			for(int i=0; i<ultimoArray.size(); i++){
    				for(int j=0; j<ultimoArray.get(i).size(); j++){
    					if(!finalTerms.contains(ultimoArray.get(i).get(j))) {
    						primerosImplicantes.add(ultimoArray.get(i).get(j));
    					}
    				}
    			}				
    			ultimoArray = min.get(1);    			
    		}
    	
    		ArrayList<String> implicantes = new ArrayList<String>();
    		ArrayList<String> terminos = new ArrayList<String>();		
    		ArrayList<String> terminosImplicantesEsenciales = new ArrayList<String>();
    		ArrayList<String> terminosImplicantesNoEsenciales = new ArrayList<String>();
    		ArrayList<String> terminosNoCubiertos = new ArrayList<String>();
    		String [] terminoprimerImplicanteEsencial = null;
    		String [] primerImplicante = null;
    		int countImplicante = 0;
    		int pos = 0, simplificado = 1;		
    		implicantes = primerosImplicantes;
    		
    		for(int i=0; i<terms.length; i++){
    			if(!terms[i].equals(""))
    				terminos.add(terms[i]);
    		}
    		
        	if(forma != 0) {
        		ArrayList<String> terminosContrarios = new ArrayList<String>();	
    			for(int i=0; i <resultado;i++) {
    				if(!terminos.contains(Integer.toString(i))) {
    					terminosContrarios.add(Integer.toString(i));
    				}
    			}
    			terminos = terminosContrarios;
        	}
        	
        	if(noNi!=null) {
        		for(int i=0; i<noNi.length; i++){
        			if(!noNi[i].equals(""))
        				terminos.remove(noNi[i]);
        		}
        	}
    		//Se buscan los implicantes esenciales
    		while(terminos.size()>0 && simplificado == 1) {
    			terminosNoCubiertos.clear();
    			terminosImplicantesNoEsenciales.clear();
    			simplificado = 0;
    			for(int i=0; i<terminos.size(); i++){
    				countImplicante = 0;
    				for(int j=0; j<implicantes.size(); j++){
    					primerImplicante = implicantes.get(j).split("#")[1].split(",");
    					for(int k=0; k<primerImplicante.length; k++){
    						if(primerImplicante[k].equals(terminos.get(i))) {
    							countImplicante++;	
    							pos = j;
    						}					
    					}
    				}
    				//Si el termino solo se encuentra en un implicante se selecciona el implicante
    				if(countImplicante == 1) {
    					if(!primerosImplicantesEsenciales.contains(implicantes.get(pos))) {
    						simplificado = 1;
    						primerosImplicantesEsenciales.add(implicantes.get(pos));
    						
    						terminoprimerImplicanteEsencial = implicantes.get(pos).split("#")[1].split(",");
    						for(int k=0; k<terminoprimerImplicanteEsencial.length; k++){
    							if(!terminosImplicantesEsenciales.contains(terminoprimerImplicanteEsencial[k])) {
    								terminosImplicantesEsenciales.add(terminoprimerImplicanteEsencial[k]);
    							}					
    						}
    					}
    				}
    			}		
    			for(int i=0; i<terminos.size(); i++){
    				if(!terminosImplicantesEsenciales.contains(terminos.get(i))) {
    					terminosNoCubiertos.add(terminos.get(i));
    				}
    			}	
    			for(int i=0; i<implicantes.size(); i++){
    				if(!primerosImplicantesEsenciales.contains(implicantes.get(i))) {			
    					terminosImplicantesNoEsenciales.add(implicantes.get(i));
    				}		
    			}
    			terminos = terminosNoCubiertos;
    			implicantes = terminosImplicantesNoEsenciales;
    		}		
    	    		
    		terminosNoCubiertos.clear();
    		terminosImplicantesNoEsenciales.clear();
    		  		
			for(int i=0; i <resultado;i++) {
				String element = Utils.termBinary(varNumber, Integer.toString(i));
				if(!maxTerms.contains(element)) {
					minTerms.add(element);
				}
			}
			
			ArrayList<String> terminosCompletos = new ArrayList<String>();	
    		for(int i=0; i<terms.length; i++){
    			if(!terms[i].equals(""))
    				terminosCompletos.add(terms[i]);
    		}
    		
        	if(forma != 0) {
        		ArrayList<String> terminosContrarios = new ArrayList<String>();	
    			for(int i=0; i <resultado;i++) {
    				if(!terminosCompletos.contains(Integer.toString(i))) {
    					terminosContrarios.add(Integer.toString(i));
    				}
    			}
    			terminosCompletos = terminosContrarios;
        	}		
			
    		for(int i=0; i<terminosCompletos.size(); i++){
    			if(!terminosImplicantesEsenciales.contains(terminosCompletos.get(i))) {			
    				terminosNoCubiertos.add(terminosCompletos.get(i));
    			}		
    		}
    		if(noNi!=null) {
        		for(int i=0; i<noNi.length; i++){
        			if(!noNi[i].equals(""))
        				terminosNoCubiertos.remove(noNi[i]);
        		}	
    		}
    		for(int i=0; i<primerosImplicantes.size(); i++){
    			if(!primerosImplicantesEsenciales.contains(primerosImplicantes.get(i))) {			
    				terminosImplicantesNoEsenciales.add(primerosImplicantes.get(i));
    			}		
    		}
    		    		
    		//Si no se han cubiertos todas las variables se llama a petrick para obtener el resto de implicantes
    		if(simplificado == 0) {
    			ArrayList<String> implicantesNoEsenciales  = new ArrayList<String>();
    			implicantesNoEsenciales = Utils.Petrick(terminosImplicantesNoEsenciales, terminosNoCubiertos);
    			primerosImplicantesEsenciales.addAll(implicantesNoEsenciales);
    		}	
    		
    		//Se pasa a forma no canonica
        	if(forma == 0) {
        		if(optionFunction == MINTERMS)
        			formaFinalMinterms = Utils.implicantesToNoCanonica(primerosImplicantesEsenciales, MINTERMS);
            	else 
            		formaFinalMaxTerms = Utils.implicantesToNoCanonica(primerosImplicantesEsenciales, MAXTERMS);
        	}
        	else {
            	if (optionFunction == MINTERMS)
            		formaFinalMaxTerms = Utils.implicantesToNoCanonica(primerosImplicantesEsenciales, MAXTERMS);
            	else
            		formaFinalMinterms = Utils.implicantesToNoCanonica(primerosImplicantesEsenciales, MINTERMS);
        	}
        }
            
        //Se comprueba si están vacíos (todos son unos o ceros), y en caso afirmativo se añade el valor 1
        if(formaFinalMaxTerms.isEmpty() || (!formaFinalMaxTerms.isEmpty() && formaFinalMaxTerms.get(0).equals(""))) {
        	formaFinalMaxTerms.clear();
        	formaFinalMaxTerms.add("1");
        }
        if(formaFinalMinterms.isEmpty() || (!formaFinalMinterms.isEmpty() && formaFinalMinterms.get(0).equals(""))) {
        	formaFinalMinterms.clear();
        	formaFinalMinterms.add("1");
        }
        System.out.println("MAXTERMS");
		for(int i=0; i<formaFinalMaxTerms.size(); i++){
			System.out.print("(");
			String[] elementosMaxterm= formaFinalMaxTerms.get(i).split("");
			for(int j=0; j<elementosMaxterm.length; j++) {
				if(!elementosMaxterm[j].equals("'")) {
					System.out.print(elementosMaxterm[j]);
					if(j+1 < elementosMaxterm.length) {
						if(elementosMaxterm[j+1].equals("'"))
							System.out.print("'");
					    if(!(elementosMaxterm[j+1].equals("'") && (j+2>= elementosMaxterm.length)))
					    	System.out.print("+");	
					}
				}
			}
			System.out.print(")");
			if(i+1 < formaFinalMaxTerms.size())
				System.out.print("*");
		}
		System.out.println();
		System.out.println("MINTERMS");
		for(int i=0; i<formaFinalMinterms.size(); i++){
			System.out.print("(");
			System.out.print(formaFinalMinterms.get(i));
			System.out.print(")");
			if(i+1 < formaFinalMinterms.size())
				System.out.print("+");
		}
		System.out.println();
        int mejorForma = Utils.mejorForma(formaFinalMinterms, formaFinalMaxTerms);
        
        if(mejorForma == MINTERMS) {
        	System.out.println("La forma con minterms esta más simplificada");
        }
        else if(mejorForma == MAXTERMS) {
        	System.out.println("La forma con maxterms esta más simplificada");
        }
        else
        	System.out.println("Las dos formas estan igual de simplificadas");
        		
	}

	public Main() {
		super();
	}

}