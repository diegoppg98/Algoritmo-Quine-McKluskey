import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
	private static final int MAXTERMS = 2;
	private static final int MINTERMS = 3;
	
	//Devuelve el numero de ceros del string que se le pase como argumento
	public static int countZeros(String a){
	    int numOfZeros = 0;
	    for(int i = a.length() - 1; i >= 0; i--)
	        if (a.charAt(i) == '0') numOfZeros ++;
	    return numOfZeros;   
	}

	//Devuelve el numero de unos del string que se le pase como argumento
	public static int countOnes(String a){
	    int numOfOnes = 0;
	    for(int i = a.length() - 1; i >= 0; i--)
	        if (a.charAt(i) == '1') numOfOnes ++;
	    return numOfOnes;   
	}
	
	//Devuelve el numero binario con numberSize cifras del numero entero pasado como argumento
	public static String termBinary(int numberSize, String numberInteger){
		String numberBinary = String.format("%0"+numberSize+"d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(numberInteger))));
	    return numberBinary;   
	}
	
	//Ordena por número de ceros la lista pasada como argumento 
	public static ArrayList<ArrayList<String>> sortByZeros(ArrayList<String> array, int size){
		ArrayList<ArrayList<String>> sortedArray = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<size+1; i++){
			sortedArray.add(new ArrayList<String>());
		}
		for(int i=0; i<array.size(); i++){
			int numOfZeros = countZeros(array.get(i));
			sortedArray.get(numOfZeros).add(array.get(i)+"#"+Integer.parseInt(array.get(i), 2));
		}
	    return sortedArray;    
	}
	
	//Ordena por número de unos la lista pasada como argumento 
	public static ArrayList<ArrayList<String>> sortByOnes(ArrayList<String> array, int size){
		ArrayList<ArrayList<String>> sortedArray = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<size+1; i++){
			sortedArray.add(new ArrayList<String>());
		}
		for(int i=0; i<array.size(); i++){
			int numOfOnes = countOnes(array.get(i));
			sortedArray.get(numOfOnes).add(array.get(i)+"#"+Integer.parseInt(array.get(i), 2));
		}
	    return sortedArray;    
	}
	
	//Devuelve true si todos los arrays están vacíos de la lista y false en caso contrario 
	public static boolean isEmptyArrayList(ArrayList<ArrayList<String>> array){
		boolean isEmpty = true;
		for(int i=0; i<array.size(); i++){
			if(!array.get(i).isEmpty()) {
				isEmpty = false;
			}
		}
	    return isEmpty;    
	}
	
	//Devuelve true si algún array de la lista contiene element y false en caso contrario 
	public static boolean arrayListContains(ArrayList<ArrayList<String>> array, String element){
		boolean isContains = false;
		for(int i=0; i<array.size(); i++){
			for(int j=0; j<array.get(i).size(); j++){
				String[] elementArray = array.get(i).get(j).split("#");
				if(elementArray[0].equals(element)) {
					isContains = true;
				}
			}
		}
	    return isContains;    
	}
	
	//Devuelve un array list minimizado a partir del array list con términos ordenados por grupos.
	public static ArrayList<ArrayList<ArrayList<String>>> minimizeArray(ArrayList<ArrayList<String>> array){
		ArrayList<ArrayList<ArrayList<String>>> arrays = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> minimizedArray = new ArrayList<ArrayList<String>>();
		for(int i=0; i<array.size()-1; i++){
			minimizedArray.add(new ArrayList<String>());
		}
		int pos = 0, count = 0;
		String[] nuevoElement;
		ArrayList<ArrayList<String>> elementsMinimized = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<array.size(); i++){
			for(int j=0; j<array.get(i).size(); j++){
				if((i+1)<array.size()) {
					for(int k=0; k<array.get(i+1).size(); k++) {
						count = 0;
						String[] element1 = array.get(i).get(j).split("#");
						String[] element2 = array.get(i+1).get(k).split("#");
						for(int l = element1[0].length() - 1; l >= 0; l--) {
						   //Si los dos elementos de los términos son iguales se aumenta el contador
						   if (element1[0].charAt(l) != element2[0].charAt(l)) {
							   pos = l;
							   count ++;
						   }
						}
						nuevoElement=element1[0].split("");
						nuevoElement[pos] = "_";
						//Si los términos solo se diferencian en un elemento se marca ese elemento con "_".
						//Y se añade el nuevo término y los elementos involucrados a la lista de términos y elementos minificados. 
						String element = Arrays.toString(nuevoElement).replace(", ", "").replaceAll("[\\[\\]]", "");
						if(count == 1) {
							ArrayList<String> elementMinimized1 = new ArrayList<String>();
							ArrayList<String> elementMinimized2 = new ArrayList<String>();	
							elementMinimized1.add(array.get(i).get(j));
							elementMinimized2.add(array.get(i+1).get(k));
							if(!elementsMinimized.contains(elementMinimized1))
								elementsMinimized.add(elementMinimized1);
							if(!elementsMinimized.contains(elementMinimized2))
								elementsMinimized.add(elementMinimized2);							
						}
						if(count == 1 && !arrayListContains(minimizedArray,element)) {							
							minimizedArray.get(i).add(element+"#"+element1[1]+","+element2[1]);
						}
					}
				}				
			}
		}
		
		//Se devuelve un array con los términos simplificados y otro con los elementos marcados. 
		arrays.add(elementsMinimized);
		arrays.add(minimizedArray);
	    return arrays;   
	}
	
	//Se devuelve un array list con el string pasado como parámetro minimizado.
	public static ArrayList<String> minimizarPetrick(String array){
		ArrayList<String> arrayMinimizado = new ArrayList<String>();	
		String [] termino = null;
		String [] terminos = array.split("#");
		
		//Se eliminan los elementos iguales en un mismo término: XX = X.
		for(int i=0; i<terminos.length; i++){
			termino = terminos[i].split("");
			for(int j=0; j<termino.length; j++){
				for(int k=0; k<termino.length; k++){
					if(j!=k && termino[j].equals(termino[k])) {
						termino[k] = "";
					}
				}
			}
			String nuevoTermino = Arrays.toString(termino).replace(", ", "").replaceAll("[\\[\\]]", "");
			terminos[i] = nuevoTermino;
		}
		
		//Se minimizan los términos mediante el teorema de la absorción: XY + X = X.
		boolean igual = true;
		int longitud = 0;
		for(int i=0; i<terminos.length; i++){
			for(int j=0; j<terminos.length; j++){	
				if(!terminos[j].equals("_")) {
					String[] terminoI = terminos[i].split("");
					longitud = terminoI.length;
					igual = true;
					while(longitud > 0 && igual == true) {
						igual = false;
						if(i!=j && terminos[j].contains(terminoI[longitud-1]) ) {
							longitud--;
							igual = true;
						}	
						if(igual == true && longitud == 0) {
							terminos[j] = "_";
						}
					}
				}
			}			
		}
		for(int i=0; i<terminos.length; i++){
			if(!terminos[i].equals("_")) {
				arrayMinimizado.add(terminos[i]);
			}
		}
		
	    return arrayMinimizado;    
	}
	
	//Se minimizan y eligen los términos mediante el método de Petrick.
	public static ArrayList<String> Petrick(ArrayList<String> implicantes, ArrayList<String> terminos){
		ArrayList<String> implicantesResultantes = new ArrayList<String>();
		ArrayList<ArrayList<ArrayList<String>>> productoSumas = new ArrayList<ArrayList<ArrayList<String>>>();
		for(int i=0; i<terminos.size(); i++){
			productoSumas.add(new ArrayList<ArrayList<String>>());
		}	
		String[] implicante,elemento;
		//Se añaden a productoSumas todos los términos
		for(int i=0; i<terminos.size(); i++){
			for(int j=0; j<implicantes.size(); j++){
				implicante = implicantes.get(j).split("#");
				elemento=implicante[1].split(",");
				for(int k=0; k<elemento.length; k++){
					if(terminos.get(i).equals(elemento[k])) {
						productoSumas.get(i).add(new ArrayList<String>());
						productoSumas.get(i).get(productoSumas.get(i).size()-1).add(Integer.toString(j));
					}
				}
			}			
		}

		//Se añade a distributiva el primer término
		ArrayList<ArrayList<String>> distributiva = new ArrayList<ArrayList<String>>();
		if(!(productoSumas.get(0).size() == 0)) {
			for(int j=0; j<productoSumas.get(0).size(); j++){
				distributiva.add(productoSumas.get(0).get(j));
			}
		}
		
		//Se multiplican los términos mediante la propiedad distributiva
		for(int i=1; i<productoSumas.size(); i++){
			ArrayList<ArrayList<String>> ultimoArray = new ArrayList<ArrayList<String>>();			
			for(int j=0; j<productoSumas.get(i).size(); j++){					
				for(int x=0; x<distributiva.size(); x++){
					ultimoArray.add(new ArrayList<String>());
					ultimoArray.get(ultimoArray.size()-1).addAll(distributiva.get(x));		
					ultimoArray.get(ultimoArray.size()-1).addAll(productoSumas.get(i).get(j));
				}
			}
			distributiva = ultimoArray;
		}
		
		String terminosSinSimplificar = "";
		for(int x=0; x<distributiva.size(); x++){
			for(int y=0; y<distributiva.get(x).size(); y++){
				terminosSinSimplificar+=distributiva.get(x).get(y);			
			}	
			if(x+1 < distributiva.size())
				terminosSinSimplificar+="#";		
		}
		
		//Se pasa el resultado de la multiplicación para que se minimice
		ArrayList<String> resultmin = Utils.minimizarPetrick(terminosSinSimplificar);
		
		//Se selecciona el elemento con menor número de términos 
		String simplificacion = null;
		if(resultmin.size() > 0)
			simplificacion = resultmin.get(0);
		for(int i=0; i<resultmin.size(); i++){
			if(resultmin.get(i).length() < simplificacion.length()) {
				simplificacion = resultmin.get(i);
			}
			//Si el número de términos es el mismos se comprueba el número de literales
			else if(resultmin.get(i).length() == simplificacion.length()) {
				String [] terminoNuevo = resultmin.get(i).split("");
				String [] terminoSimplificacion = simplificacion.split("");
				int numTermNuevo = 0;int numTermSimplificado = 0;
				for(int j=0; j<terminoNuevo.length; j++) {	
					if(!terminoNuevo[j].equals("")) {
						numTermNuevo+=implicantes.get(Integer.parseInt(terminoNuevo[j])).split("#")[0].length();
					}
						
				}
				for(int j=0; j<terminoSimplificacion.length; j++) {	
					if(!terminoSimplificacion[j].equals("")) {
						numTermSimplificado+=implicantes.get(Integer.parseInt(terminoSimplificacion[j])).split("#")[0].length();
					}
						
				}
				if(numTermNuevo < numTermSimplificado)
					simplificacion = resultmin.get(i);
			}
		}

		String [] terminoSimplificacion = simplificacion.split("");
		for(int j=0; j<terminoSimplificacion.length; j++) {		
			if(!terminoSimplificacion[j].equals(""))
				implicantesResultantes.add(implicantes.get(Integer.parseInt(terminoSimplificacion[j])));
		}
		
	    return implicantesResultantes;    
	}
	
	//Se devuelve un arraylist con los implicantes en forma no canónica.
	public static ArrayList<String> implicantesToNoCanonica(ArrayList<String> array, int forma){
		ArrayList<String> formaNoCanonica = new ArrayList<String>();	
		String element; 
		String negativo;
		
		if(forma == MINTERMS)
			negativo = "0";
		else
			negativo = "1";
		for(int i=0; i<array.size(); i++){
			element = array.get(i).split("#")[0];
			String [] elements = element.split("");
			String elementNoCanonico = "";
			for(int j=0; j<elements.length; j++) {
				if(!elements[j].equals("_")) {
					elementNoCanonico+=String.valueOf((char)(j+ 'A'));	
				}
				if(elements[j].equals(negativo)) {
					elementNoCanonico+="'";	
				}
			}
			formaNoCanonica.add(elementNoCanonico);		
		}
		
		return formaNoCanonica;
	}
	
	//Comprueba si la mejor forma es minTerms o maxTerms.
	public static int mejorForma(ArrayList<String> min ,ArrayList<String> max){		
		int implicantesMin = min.size(); int implicantesMax = max.size();
		//Se selecciona la forma con menor número de términos 
		if(implicantesMin < implicantesMax)
			return MINTERMS;
		if(implicantesMax < implicantesMin)
			return MAXTERMS;
		//Si el número de términos es el mismos se comprueba el número de literales
		else {
			int termMin = 0; int termMax = 0;
			int termMinNeg = 0; int termMaxNeg = 0;
			for(int i=0; i<min.size(); i++) {
				String[] elementosMinterm= min.get(i).split("");
				for(int j=0; j<elementosMinterm.length; j++) {
					if(elementosMinterm[j].equals("'"))
						termMinNeg++;
					else
						termMin++;
				}
			}
			for(int i=0; i<max.size(); i++) {
				String[] elementosMaxterm= max.get(i).split("");
				for(int j=0; j<elementosMaxterm.length; j++) {
					if(elementosMaxterm[j].equals("'"))
						termMaxNeg++;
					else
						termMax++;			
				}
			}

			if(termMin < termMax)
				return MINTERMS;
			if(termMax < termMin)
				return MAXTERMS;
			//Si el número de literales es el mismos se comprueba el número de literales negados
			else {
				if(termMinNeg < termMaxNeg)
					return MINTERMS;
				else if(termMaxNeg < termMinNeg)
					return MAXTERMS;	
				else 
					return 0;
			}
		}
	}
	
	//Se cuenta el numero de terminos.
	public static int countTerms(String array){
		String [] terminos = array.split("");
		int num=0;
		for(int i=0;i<terminos.length;i++){
			if(!terminos[i].equals("'"))
				num++;
		}	
		return num;
	}
	
	//Se eliminan los terminos duplicados
	public static ArrayList<String> eliminarDuplicados(ArrayList<String> array){
		ArrayList<String> arraySinRepetidos = new ArrayList<String>();
		for(int i=0;i<array.size();i++){
			if(!arraySinRepetidos.contains(array.get(i)))
				arraySinRepetidos.add(array.get(i));
		}	
		return arraySinRepetidos;
	}
	
	//Se devuelve un arraylist con los implicantes en forma canónica.
	public static ArrayList<String> implicantesToCanonica(String array, int numTerm){
		ArrayList<String> formaCanonica = new ArrayList<String>();	
		ArrayList<String> terminoCanonico = new ArrayList<String>();
		ArrayList<String> terminosCanonicos = new ArrayList<String>();
		int fin = 0;
		char negativo = '0';
		char positivo = '1';
		String separador="[+]";
		String replaceArray=array;
		String [] terminosMax = array.split("[*]");
		if(!(terminosMax.length == 1)) {
			//Si son maxterms se cambian los valores
			replaceArray=array.replace("+","");
			separador="[*]";
			negativo = '1';
			positivo = '0';
		}
		
		String [] terminosMin = replaceArray.split(separador);
		for(int i=0; i<terminosMin.length; i++) {
			fin = 0;
			terminoCanonico.clear();
			terminoCanonico.add(terminosMin[i]);
			if(countTerms(terminoCanonico.get(0)) == numTerm) {
				fin = 1;
				String sorted = Stream.of(terminoCanonico.get(0).split(""))
                .sorted(Comparator.comparingInt(o -> Character.toLowerCase(o.charAt(0))))
                .collect(Collectors.joining());
				terminosCanonicos.add(sorted);
			}		
			
			while(fin==0) {
				ArrayList<String> canonico = new ArrayList<String>();
				String termAñadido ="";
				for(int j=0;j<numTerm;j++) {
					if(!terminoCanonico.get(0).toUpperCase().contains(String.valueOf((char)(j+ 'A')))) {
						termAñadido=String.valueOf((char)(j+ 'A'));
						break;
					}
				}
				for(int k=0;k<terminoCanonico.size();k++) {
					canonico.add(terminoCanonico.get(k)+termAñadido.toLowerCase());
					canonico.add(terminoCanonico.get(k)+termAñadido.toUpperCase());
				}
				terminoCanonico=canonico;
				
				if(countTerms(terminoCanonico.get(0)) == numTerm) {
					fin = 1;
					for(int n=0;n<terminoCanonico.size();n++){
						String sorted = Stream.of(terminoCanonico.get(n).split(""))
                        .sorted(Comparator.comparingInt(o -> Character.toLowerCase(o.charAt(0))))
                        .collect(Collectors.joining());
						terminosCanonicos.add(sorted);
					}
				}				
			}
		}
		terminosCanonicos=eliminarDuplicados(terminosCanonicos);
		for(int i=0;i<terminosCanonicos.size();i++){
			String []term = terminosCanonicos.get(i).split("");
			char [] termBinary = new char[term.length];
			
			for(int j=0;j<term.length;j++) {
				char letra = term[j].charAt(0);
			    if(Character.isUpperCase(letra))
			    	termBinary[j]=negativo;
			    else
			    	termBinary[j]=positivo;		
			}
			formaCanonica.add(String.valueOf(termBinary));
		}
		return formaCanonica;
	}

}
