package test;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;
public class BloomFilter {

    final static int NUMBER_OF_BITS = 6400;

    final static HashFunction h1 = new HashFunction(11, 9);
    final static HashFunction h2 = new HashFunction(17, 15);
    final static HashFunction h3 = new HashFunction(31, 29);
    final static HashFunction h4 = new HashFunction(61, 59);
    
    
    
   static void insert_val(String test,  BitSet bitSet) throws IOException {
	   if(check_value(test,bitSet)) {
		   System.out.println("mail already exists");
	   }
	   
	   else {
		   if( EmailValidator(test)) {
			   
		   
		  bitSet.set(h1.getHashValue(test));
	      bitSet.set(h2.getHashValue(test));
	      bitSet.set(h3.getHashValue(test));
	      bitSet.set(h4.getHashValue(test));
	   //   randomWordList.add(randomWord);
		  	  File file = new File( "C:\\Users\\91630\\Desktop\\dictionary.txt");
		  	  BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
		  	  writer.write(test); 
		        writer.newLine();
		        writer.close();
		        System.out.println("inserted");
	   }
		   else {
			   System.out.println("Email Address is Invalid");
		   }
		   
	   }
        
        
   }
   static boolean EmailValidator(String testmail) {
       
       
       try {
          
          // String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    	   String emailregex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                   "[a-zA-Z0-9_+&*-]+)*@" +
                   "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                   "A-Z]{2,7}$";
           boolean b = testmail.matches(emailregex);
           return b;
       /*    if (b == false) {
               System.out.println("Email Address is Invalid");
               }else if(b == true){
               System.out.println("Email Address is Valid");
           }*/
       }
       catch (Exception e) {
    	   return false;
          // e.printStackTrace();
          // System.out.println(e.getMessage());
       }
   }

   static int size() throws IOException {
	   int sz=0;
	   File file = new File( "C:\\Users\\91630\\Desktop\\dictionary.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));
       while (( br.readLine()) != null) {
    	   sz=sz+1;
       }
       return sz;
   }
   
  /* static void check_size() throws IOException {
	   int sz=0;
	   File file = new File( "C:\\Users\\91630\\Desktop\\dictionary.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));
       while (( br.readLine()) != null) {
    	   sz=sz+1;
       }
	   if(sz==0) {
		   System.out.println("File is Empty");
	   }
	   else {
		   System.out.println("File is not Empty");

	   }
       
   }*/
   
        static boolean check_value( String test,BitSet bitSet) throws IOException {
            List<String> randomWordList = new ArrayList<>();
            File file = new File( "C:\\Users\\91630\\Desktop\\dictionary.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null)
                	randomWordList.add(st);
                for(int i=0; i<randomWordList.size(); i++) {
                    final String randomWord = randomWordList.get(i);
                	  bitSet.set(h1.getHashValue(randomWord));
                      bitSet.set(h2.getHashValue(randomWord));
                      bitSet.set(h3.getHashValue(randomWord));
                      bitSet.set(h4.getHashValue(randomWord));
                }
                
                boolean answerFromBloomFilter =	(bitSet.get(h1.getHashValue(test)) && bitSet.get(h2.getHashValue(test))
                        && bitSet.get(h3.getHashValue(test)) && bitSet.get(h4.getHashValue(test)));
    if (answerFromBloomFilter) {
     return true;
    } 
    else {
    	        return false    ;
        		}       
        }
        


    

    public static void main(String[] args) throws ClassNotFoundException, IOException {
    	   BitSet bitSet = new BitSet(NUMBER_OF_BITS);
    	   char ch;
           /*  Perform bloom filter operations  */
           do    
           {
               System.out.println("\nBloomFilter Operations\n");
               System.out.println("1. insert ");
               System.out.println("2. contains");
               System.out.println("3. check empty");
               System.out.println("4. size");
    
               Scanner cho = new Scanner(System.in);
               int choice = cho.nextInt();            
               switch (choice)
               {
               case 1 : 
            	   
                   System.out.println("Enter integer element to insert");
                   Scanner insert_value = new Scanner(System.in);
                      insert_val(insert_value.next(), bitSet);               
                   break;                          
               case 2 : 
            	   System.out.println("Enter integer element to be checked");
                   Scanner check_val = new Scanner(System.in);
                   String check_value=check_val.next();
                   if( EmailValidator(check_value)) {
                   if(check_value(check_value,bitSet)) {
                	   System.out.println("Search result : This mail is already taken");
                   }
                   else {
                	   System.out.println("Search result : This mail is available");

                   }
                   }
                   else {
                       System.out.println("Email Address is Invalid");
                   }
                   break;                                          
               case 3 : 
            	   if(size()==0) {
            		   System.out.println("File is Empty");
            	   }
            	   else {
            		   System.out.println("File is not Empty");

            	   }
                   break;
               case 4 : 
                   System.out.println("\nSize = "+ size());
                   break;            
               default : 
                   System.out.println("Wrong Entry \n ");
                   break;   
               }    
    
               System.out.println("\nDo you want to continue (Type y or n) \n");
               Scanner scan = new Scanner(System.in);
               ch = scan.next().charAt(0);                        
           } while (ch == 'Y'|| ch == 'y');
       
      
    

    }
 
    private static class HashFunction {

        private long prime;
        private long odd;

        public HashFunction(final long prime, final long odd) {
            this.prime = prime;
            this.odd = odd;
        }

        public int getHashValue(final String word) {
            int hash = word.hashCode();
            if (hash < 0) {
                hash = Math.abs(hash);
            }
            return calculateHash(hash, prime, odd);
        }

        private int calculateHash(final int hash, final long prime, final long odd) {
            return (int)((((hash % NUMBER_OF_BITS) * prime) % NUMBER_OF_BITS) * odd) % NUMBER_OF_BITS;
        }

    }

}