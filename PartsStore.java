import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.*;

public class PartsStore {

	private List<String> allParts = new ArrayList<>();

	public PartsStore(){
		String txtFileName = "pcparts.csv";


		try (Stream<String> stream = Files.lines(Paths.get(txtFileName))) {
 

			allParts = stream.collect(Collectors.toList());
 			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		//System.out.println("all:");
		//System.out.println(allParts.size());


  }
  public void FindPartsWithBrand(String type, String brand){
  	allParts.stream().filter(line->(type==null || line.startsWith(type))).map(line->{
  		Stream< String > words = Arrays.stream( line.split( "," ) );
		List<String> newLine = new ArrayList<>();
		words.forEach( (word)->{
			newLine.add(word);
		} );
		return newLine;
  	}).filter(nline->nline.get(1).equals(brand)).map(nline->{
  		String joined = String.join(",",nline);
  		return joined;
  	}).forEach(System.out::println);


  }

  public void TotalPrice(String type, String brand, String model){
  	//double totalPrice = 0.0;
  	double totalPrice = allParts.stream().filter(line->type==null ||line.startsWith(type)).map(line->{
  		Stream< String > words = Arrays.stream( line.split( "," ) );
		List<String> newLine = new ArrayList<>();
		words.forEach( (word)->{
			newLine.add(word);
		} );
		return newLine;
  	}).filter(nline->brand==null ||(nline.get(1).equals(brand))).filter(nline->model==null ||nline.get(2).equals(model)).mapToDouble(word -> Double.parseDouble(word.get(word.size()-1).substring(0,word.get(word.size()-1).length()-4)))
  	.reduce(0, (a, b) -> a + b);
  	System.out.printf("%.2f USD%n",totalPrice);

  }

  public void UpdateStock(){
  	int oldSize = allParts.size();
  	int newSize,removed;
  	allParts = allParts.stream().map(line->{
  		Stream< String > words = Arrays.stream( line.split( "," ) );
		List<String> newLine = new ArrayList<>();
		words.forEach( (word)->{
			newLine.add(word);
		} );
		return newLine;
  	}).filter(nline->!nline.get(nline.size()-1).equals("0.00 USD")).map(nline->{
  		String joined = String.join(",",nline);
  		return joined;
  	}).collect(Collectors.toList());
  	newSize = allParts.size();
  	removed = oldSize - newSize;
  	System.out.printf("%s items removed.%n",removed);

  }

  public void FindCheapestMemory(int capacity){
  	List<String> defaultVal = new ArrayList<String>();
  	defaultVal.add("No item found with that capacity.");
  	List<String> cheapest = allParts.stream().filter(line->line.startsWith("Memory")).map(line->{
  		Stream< String > words = Arrays.stream( line.split( "," ) );
		List<String> newLine = new ArrayList<>();
		words.forEach( (word)->{
			newLine.add(word);
		} );
		return newLine;
  	}).filter(nline->capacity<=Integer.parseInt(nline.get(4).substring(0,nline.get(4).length()-2)))
  	.min(Comparator.comparing(nline->Double.parseDouble(nline.get(nline.size()-1).substring(0,nline.get(nline.size()-1).length()-4)))).orElse(defaultVal);
  	String cs = String.join(",",cheapest);
  	System.out.println(cs);

  }

  public void FindFastestCPU(){
  	List<String> defaultVal = new ArrayList<String>();
  	defaultVal.add("No item found.");
  	List<String> fastest = allParts.stream().filter(line->line.startsWith("CPU")).map(line->{
  		Stream< String > words = Arrays.stream( line.split( "," ) );
		List<String> newLine = new ArrayList<>();
		words.forEach( (word)->{
			newLine.add(word);
		} );
		return newLine;
  	}).max(Comparator.comparing(nline->
  		(Double.parseDouble(nline.get(nline.size()-3)))*(Double.parseDouble(nline.get(nline.size()-2).substring(0,nline.get(nline.size()-2).length()-3))))).orElse(defaultVal);
  	String fs = String.join(",",fastest);
  	System.out.println(fs);

  }
}
