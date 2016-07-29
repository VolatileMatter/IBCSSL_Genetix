import java.util.*;
import java.lang.*;
import java.math.*;
import java.lang.Integer.*;

class Genetix
{      
  public static int name = 1; 
  
  public static String[] GLA;       
  public static String[] momGenome; 
  public static String[] dadGenome;
  
  public static String[] susanGene = {"HH", "Ww", "ff", "ffffff"};
  public static String[] davidGene = {"Hh", "ww", "FF", "000000"};
  public static String[] GLAdef = {"h","w","f"};
  
  public static void main(String args[])
  {       
    String again = "No"; 
    do { Genetix.start(); 
    
    System.out.println();   
    Genetix.printArray(momGenome,"Mom Genome");
    Genetix.printArray(dadGenome,"Dad Genome");  
    System.out.println();
    
    System.out.println("How many kids?"); 
    int kids = getScanInt();  
   for (name=1;name<=kids;name++) {
     Genetix.makeBabies(); }
   System.out.println("------------------------"); System.out.println(); 
   System.out.println("Would you like to run again?"); 
   again = getScanString(); 
   again = again.toUpperCase(); 
    }
    while (again.substring(0,1).equals("Y"));
  }
  
  public static void start() {
    System.out.println("Would you like to use default setting?");
    String ans = getScanString();
    ans = ans.toUpperCase();
    if (ans.substring(0,1).equals("N")) {
      System.out.println("How many different genes do you want?");
      int geneNum = getScanInt();        
      GLA = new String[geneNum+1]; 
      momGenome = new String[geneNum+1]; 
      dadGenome = new String[geneNum+1]; 
      System.out.println("What letters would you like to use? (no spaces; hwf for h, w, & f)");
      String genes = getScanString(); 
      while (genes.length() != geneNum) { System.out.println("Number of genes does not match number of letters entered, please try again."); genes = getScanString(); }
      genes = genes.toLowerCase();
      for (int i=0; i<geneNum; i+=1) {
        GLA[i] = genes.substring(i,i+1);
      }
      Genetix.printArray(GLA, "GLA");
      Genetix.parents();
    }
    else {  
      momGenome = susanGene; 
      dadGenome = davidGene; 
      GLA = GLAdef;
    }
  }
  
  public static void parents() {  
    //First, initialize the mother's genes: 
    System.out.println("Enter the mom genes. (ex: HhwwFF)");
    String scanout = getScanString(); 
    while (scanout.length() != ((GLA.length-1)*2)) { System.out.println("The number of characters doesn't match the number of genes. Please try again."); scanout = getScanString(); } 
    int b = 0; 
    //Move the entered values from the string into the public array: 
    for(int i=0;i<momGenome.length-1;i++) {
      momGenome[i] = scanout.substring(b,b+2);
      b+=2;
    } 
    //The very last value of the array is the hex color value: 
    System.out.println("Now enter the hexadecimal color value."); 
    String hex = getScanString(); 
    while (hex.length() > 6) { System.out.println("That's too many characters. Hex values must be less than 7 characters long."); hex = getScanString(); } 
    while (!(isHex(hex))) { System.out.println("That is not a valid hex code. Please try again."); hex = getScanString(); }
    momGenome[momGenome.length-1] = hex; 
    
    //now change the hex to a decimal value
    String momHex = momGenome[(momGenome.length)-1];  
    
    //Now, do it all again for the father
    System.out.println("Enter the dad genes. (ex: HhwwFF)");
    scanout = getScanString(); 
    b = 0; 
    //Move the entered values from the string into the public array: 
    for(int i=0;i<dadGenome.length-1;i++) {
      dadGenome[i] = scanout.substring(b,b+2);
      b+=2;
    }
    //The very last value of the array is the hex color value: 
    System.out.println("Now enter the hexadecimal color value."); 
    hex = getScanString(); 
    while (hex.length() > 6) { System.out.println("That's too many characters. Hex values must be less than 7 characters long."); hex = getScanString(); } 
    while (!(isHex(hex))) { System.out.println("That is not a valid hex code. Please try again."); hex = getScanString(); }
    dadGenome[dadGenome.length-1] = hex; 
    
    //now change the hex to a decimal value
    String dadHex = dadGenome[(dadGenome.length)-1]; 
    
    
  }
  
  
  public static void makeBabies() {
      String[] GENOM = new String[GLA.length]; 
      System.out.println("Child Number: "+name);      
      for (int i=0; i<momGenome.length-1;i++) {
        GENOM[i] = Gene(i); 
      }
      GENOM[GENOM.length-1] = colorMixer((momGenome[momGenome.length-1]), (dadGenome[dadGenome.length-1])); 
      Genetix.printArray(GENOM, "Genome");
      System.out.println(""); 
  } 
  
  
  
  public static String Gene(int indexx) {
    
    //generate possible genes array
    String[] posGenes = {"xx","xx","xx"};
    posGenes[0] = (GLA[indexx].toUpperCase())+(GLA[indexx].toUpperCase()); 
    posGenes[1] = (GLA[indexx].toUpperCase())+GLA[indexx]; 
    posGenes[2] = GLA[indexx]+GLA[indexx]; 
    String momState = "momstatefail";
    String dadState = "dadstatefail";
    int rng = (int)(Math.random()*100); 
    //array should now read: "RR","Rr","rr" with correct letters
    
    if (momGenome[indexx].equals(dadGenome[indexx])) {
      return momGenome[indexx];
    }
    else {
      if (momGenome[indexx].equals(posGenes[0])) {
        momState = "Homozygous Dominant";  
      }
      if (momGenome[indexx].equals(posGenes[1])) {
        momState = "Heterozygous";
      }
      if (momGenome[indexx].equals(posGenes[2])) {
        momState = "Homozygous Recessive";
      }
      if (dadGenome[indexx].equals(posGenes[0])) {
        dadState = "Homozygous Dominant";  
      }
      if (dadGenome[indexx].equals(posGenes[1])) {
        dadState = "Heterozygous";
      }
      if (dadGenome[indexx].equals(posGenes[2])) {
        dadState = "Homozygous Recessive";
      }
    }
    
    if ((dadState.equals("Homozygous Dominant") && momState.equals("Homozygous Recessive"))) {
      return posGenes[1]; //100% Heterozygous
    }
    else if (momState.equals("Homozygous Dominant") && dadState.equals("Homozygous Recessive")) {
      return posGenes[1]; //100% Heterozygous
    }
    
    else if ((dadState.equals("Heterozygous") && momState.equals("Homozygous Recessive"))) {
      if (rng > 50) {
        return posGenes[1]; //50% Heterozygous
      }
      else return posGenes[2]; //50% HomoRec
    }
    else if (momState.equals("Heterozygous") && dadState.equals("Homozygous Recessive")) {
      if (rng > 50) {
        return posGenes[1]; //50% Heterozygous
      }
      else return posGenes[2]; //50% HomoRec
    }
    
    else if ((dadState.equals("Homozygous Dominant") && momState.equals("Heterozygous"))) {
      if (rng > 50) {
        return posGenes[0]; //50% HomoDom
      }
      else if (rng <= 50) {
        return posGenes[1]; //50% Hetero } 
      }
    }
    else if (momState.equals("Homozygous Dominant") && dadState.equals("Heterozygous")) {
      if (rng > 50) {
        return posGenes[0]; //50% HomoDom
      }
      else return posGenes[1]; //50% Hetero
    }
    
    else if ((dadState.equals("Heterozygous") && momState.equals("Heterozygous"))) {
      if (rng < 25) {
        return posGenes[0]; //25% HomoDom
      }
      else if (rng < 75) {
        return posGenes[1]; //50% Hetero 
      }
      else return posGenes[2]; //25% HomoRec
    }
    if (rng == 20) {
      return "FAIL"; }
    else return "Fail";
  }
  
  //Methods used to make the main code more readable:
  
  public static Boolean isHex(String test) {
    test = test.toUpperCase(); 
    Boolean ans = false;
    String letters = "ABCDEF"; 
    for (int i=0; i<test.length(); i++) {
      char ch = test.charAt(i); 
      if (Character.isLetter(ch)) {
        if (ch == letters.charAt(0)) { ans = true; }
        else if (ch == letters.charAt(1)) { ans = true; }
        else if (ch ==letters.charAt(2)) { ans = true; }
        else if (ch == letters.charAt(3)) { ans = true; }
        else if (ch == letters.charAt(4)) {ans = true; }
        else if (ch == letters.charAt(5)) {ans = true; }
        else {ans = false; } 
      }
      else if (Character.isDigit(ch)) { ans = true; } 
      else { ans = false; } 
    }
    return ans; 
  }
  
  
  public static String colorMixer(String mom1, String dad1) {
    int mom = Integer.parseInt(mom1,16); 
    int dad = Integer.parseInt(dad1, 16);
    int ran = -1; 
    int j = 0; 
    if (mom > dad) {
      ran = (int)((Math.random()*mom)+dad);  
    }
    if (mom < dad) {
      ran = (int)((Math.random()*dad)+mom);  
    }
    if (mom == dad) {
      ran = mom;  
    }
    String ranHex = decimal2hex(ran); 
    if (ranHex.length() == 1) { ranHex = "00000"+ranHex; }
    else if (ranHex.length() == 2) { ranHex = "0000"+ranHex; }
    else if (ranHex.length() == 3) { ranHex = "000"+ranHex; }
    else if (ranHex.length() == 4) { ranHex = "00"+ranHex; }
    else if (ranHex.length() == 5) { ranHex = "0"+ranHex; }
    ranHex = "#"+ranHex; 
    return ranHex;
  }
  
  
  public static int hex2decimal(String valPar) {
    String digits = "0123456789ABCDEF";
    valPar = valPar.toUpperCase();
    int val = 0;
    for (int i = 0; i < valPar.length(); i++) {
      char c = valPar.charAt(i);
      int d = digits.indexOf(c);
      val = 16*val + d;
    }
    return val;
  }
  
  public static String decimal2hex(int d) {
    String digits = "0123456789ABCDEF";
    if (d == 0) return "0";
    String hex = "";
    while (d > 0) {
      int digit = d % 16;                // rightmost digit
      hex = digits.charAt(digit) + hex;  // string concatenation
      d = d / 16;
    }
    return hex;
  }
  
  public static void printArray(String[] arr, String name) {
    System.out.print(name+": ");
    for(int i = 0; i<arr.length; i++) {
      System.out.print(arr[i]+", "); }
    System.out.println(); 
  }
  
  public static String getScanString() {
    String answer = "FALSE"; 
    Scanner scan10 = new Scanner(System.in);
    if (scan10.hasNext()) { answer = scan10.nextLine(); }
    while (answer.equals("FALSE")) { System.out.println("Error, please try again."); answer = getScanString(); }
    scan10.close();
    return answer; 
  }
  public static int getScanInt() {
    int answer = -1; 
    Scanner scan = new Scanner(System.in); 
    if (scan.hasNextInt()) { answer = scan.nextInt(); }
    while (answer == -1) { System.out.println("Error, please try again."); answer = getScanInt(); }
    scan.close();
    return answer; 
  }
}