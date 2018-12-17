package zadanie_slonie;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Main {
	
//	// funkcja - wczytanie sciezki z klawiatury
//	private static String loadPathFromKeyboard () throws IOException {
//		
//		Scanner scanner = new Scanner(System.in);
//		String inputString = scanner.next();		
//		scanner.close();
//
//		
//		return inputString;	
//	}
		
		
	// funkcja - wczytywanie pliku linia po linii 
	@SuppressWarnings("resource")
	private static ArrayList<String> loadData () throws IOException {
		
		ArrayList<String> inputLines = new ArrayList<String>();

//		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
//		String tmpLine = "";
//		
//		while (scanner.hasNextLine()) {
//			tmpLine = scanner.nextLine();
//			inputLines.add(tmpLine);
//		}
//
//		// close the scanner
//		scanner.close();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = bufferedReader.readLine()) != null)
			inputLines.add(line);

		bufferedReader.close();
		
		return inputLines;
	}
	
	// funkcja - zapis wyniku do pliku 
//	public static void writeToFile(String solution) throws IOException {
//		
//		FileWriter file = new FileWriter("./io/output.txt"); // plik
//		BufferedWriter writer = new BufferedWriter(file);
//		writer.append(solution);
//
//		writer.close();
//	}
	
	// funkcja - wyciaganie liczb z wczytanych linii 
	private static int[][] parseData (ArrayList<String> inputLines){

		int elephNumber = Integer.parseInt(inputLines.get(0));
		inputLines.remove(0);
		
		String[] elephWeigthsStr = inputLines.get(0).split(" ");
		String[] elephPositionsStr = inputLines.get(1).split(" ");
		String[] elephTargetPositionsStr = inputLines.get(2).split(" ");
		
		//System.out.println("Eleph number: "+elephNumber);
		int elephArray[][] = new int[3][elephNumber];
		
		for (int i = 0; i < elephNumber; i++) {
			elephArray[0][i] = Integer.parseInt(elephWeigthsStr[i]);
			elephArray[1][i] = Integer.parseInt(elephPositionsStr[i]);
			elephArray[2][i] = Integer.parseInt(elephTargetPositionsStr[i]);

		}		
		
		
		return elephArray;
	}
	
	
	// funkcja - permutacja
	private static int[] extractPermut (int arraySize, int inputArray1[], int inputArray2[]) {
		
		int permutArray[] = new int[arraySize];
		
		// tworzenie permutacji 
//		for(int i=0; i<arraySize; i++) {
//			int tempVal = inputArray1[i];
//			for(int j=0; j<arraySize; j++) {
//				if (tempVal == inputArray2[j]) {
//					permutArray[tempVal-1] = inputArray1[j];
//					break;
//				}
//			}
//		}	
		
		// tworzenie permutacji 
		for (int i = 0; i < arraySize; i++) {
			int tempVal = inputArray1[i];
			permutArray[tempVal-1] = inputArray2[i];
		}
	
		

		return permutArray;
	}
	
	// funkcja - rozklad na cykle
	private static ArrayList<Cycle> spreadToCycles (int inputPermutArray[]){
		
		ArrayList<Cycle> cyclesList = new ArrayList<Cycle>();
		int arraySize = inputPermutArray.length;
		boolean boolArray1[] = new boolean[arraySize];
		
		// tablica odw
		for(int i=0; i<arraySize; i++) {
			boolArray1[i] = false;
		}
		
		// rozklad na cykle
		int x=0;
		ArrayList<Integer> tempArrayList1;
		
		for(int i=0; i<arraySize; i++) {
			if(!boolArray1[i]) {
				tempArrayList1 = new ArrayList<Integer>();				
				x=i+1;
				while(!boolArray1[x-1]) {
					boolArray1[x-1]=true;
					tempArrayList1.add(x);
					x = inputPermutArray[x-1];
				}
				Cycle cycle1 = new Cycle();
				cycle1.setElements(tempArrayList1);
				cyclesList.add(cycle1);
			}
		}
		
		return cyclesList;
	}
	
	// funkcja - obliczenie parametrow cyklu
	private static void calculateCyclesParams (ArrayList<Cycle> inutCycles, int elephWeights[]) {
		
		long sumWeigth = 0;
		int minWeigth = 9999;
		int pointer = 0;
		ArrayList<Integer> cyclesElems;
		
		for(int i=0; i<inutCycles.size(); i++) {
			Cycle cycle1 = inutCycles.get(i);
			cyclesElems = cycle1.getElements();
			sumWeigth = 0;    // zerowanie sumy
			minWeigth = 9999; // max dla slonia to 6500
			for(Integer elem1 : cyclesElems) {
				pointer = elem1-1;
				int elephWeigth = elephWeights[pointer]; // sumowanie wag 
				sumWeigth += elephWeigth;
				// szukanie minimalnej wagi w cyklu
				minWeigth = Math.min(elephWeigth, minWeigth);
			}
//			System.out.println("Min weight: "+minWeigth);
//			System.out.println("Sum weight: "+sumWeigth);
			cycle1.setMinWeigth(minWeigth);
			cycle1.setSumWeigth(sumWeigth);
			inutCycles.set(i, cycle1);
		}
	}
	
	// funkcja - wyciaganie minimalnej wagi slonia
	private static int getElephMinWeigth (int elephWeights[]) {
		
		int minWeight = 9999;
		for(int i=0; i<elephWeights.length; i++) {
			minWeight = Math.min(minWeight, elephWeights[i]);
		}
		return minWeight;		
	}
	
	// metoda 1
	private static long method1Func(Cycle inputCycle) {
		
		int numberOfElems = inputCycle.getElements().size();
		int minWeight = inputCycle.getMinWeigth();
		long sumWeight = inputCycle.getSumWeigth();
		long cost = sumWeight + (numberOfElems-2)*minWeight;
		
		return cost;
	}
	
	// metoda 2
	private static long method2Func(Cycle inputCycle, long minimumArg) {
		
		int numberOfElems = inputCycle.getElements().size();
		int minWeight = inputCycle.getMinWeigth();
		long sumWeight = inputCycle.getSumWeigth();
		long cost = sumWeight + minWeight + (numberOfElems+1)*minimumArg;
		
		return cost;
	}
	
	// funkcja z algorytmem 
	private static long solutionFunction (int elephWeights[], int elephPositions[], int elephTargetPositions[]) {
		long solution1 = 0;
		
		// permutacja
		int permutArray1[] = extractPermut(elephPositions.length, elephPositions, elephTargetPositions);
		
		// cykle
		ArrayList<Cycle> cyclesList = spreadToCycles(permutArray1);
		
		// obliczanie parametrow cyklu 
		calculateCyclesParams(cyclesList, elephWeights);
		
		// minimalna waga slonia
		int minimumWeigth = getElephMinWeigth(elephWeights);
		
		long method1Val = 0;
		long method2Val = 0;
		long methodsMin = Integer.MAX_VALUE;
		
		for(Cycle elem1 : cyclesList) {
			method1Val = method1Func(elem1);
			method2Val = method2Func(elem1, minimumWeigth);
			
			methodsMin = Math.min(method1Val, method2Val);
			solution1+=methodsMin;
		}
		
		
		return solution1;
	}
	

	public static void main(String[] args) {
		
		ArrayList<String> inputLines = new ArrayList<String>();
		//String filePath = "E:\\java\\zadanie_implicode\\dane\\slo10b.in";
		
		try {
			// String filePath = loadPathFromKeyboard ();
			inputLines = loadData();
		} catch (IOException e) {
			System.out.println("B³¹d czytania pliku!");
			e.printStackTrace();
		}
		int rawData1[][] = parseData(inputLines);    // rozbicie linii na pojedyncze liczby
		
		int elephWeights[] = rawData1[0].clone();         // wagi sloni 
		int elephPositions[] = rawData1[1].clone();       // ustawienie poczatkowe
		int elephTargetPositions[] = rawData1[2].clone(); // ustawienie koncowe
		
		// rozwiazanie
		long solution = solutionFunction(elephWeights, elephPositions, elephTargetPositions);
		System.out.println(solution);	
		
//		// zapis wyniku do pliku 
//		String s = String.valueOf(solution);
//		try {
//			writeToFile(s);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
