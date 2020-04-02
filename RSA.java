import java.util.*;
public class RSA {

 private Random rnd= new Random();
     
      public long gcd(long a, long b) {
         if (b==0) 
            return a;
         return gcd(b,a%b);
      } 
   
      public long find_inverse(long a, long m)
      {    
         for (long i=1; i<m; i++)  
         {
            long test=i*a;
            if ((test%m)==1)
               return i;
         }
         return 0;
      }
   
      public int random_prime()
      {
         boolean found=false;
         int p=0;
         while (!found)
         {
            p=rnd.nextInt(10000);
            found= isPrime(p);
         }
         return p;      
      }
   
      public boolean isPrime(int p)
      {
         if (p>1){
            for (int i=2; i<= Math.sqrt(p); i++)
            {
               if (p%i==0)
                  return false;
            }
            return true;
         }
         return false;
      }
      static long t=0;

      public long[] generate_keys()
      {
         long[] n_e_d= new long[3]; 
         int p = random_prime();
         int q = random_prime();
   
         long n = p*q;
   
         long euler = (p-1)*(q-1);
         t = euler;
         int e = random_prime();
         long GCD = gcd (e, euler);
   
         while ( e > euler && e < 1 && GCD != 1 )
            e = random_prime();
   
         long d = find_inverse(e, euler);
   
         n_e_d[0] = n;
         n_e_d[1] = e;
         n_e_d[2] = d;
         return n_e_d;
      }
   
      public long modular_exponentiation(long b, long n, long m)
      {
         long x=1;
         long pow=b%m;
       String B = Long.toBinaryString(n);
       
       for(int i=B.length()-1;i>=0;i--){
       char a=B.charAt(i);
       if(a=='1')
          x=(x*pow)%m;
       pow=(pow*pow)%m;
       }//end for
         
         return x;
      }
      
      public long string_to_int(String text)
      {
         long num = 0;
         String concat ="";
   
         for (int i=0; i<text.length(); i++)
         {
            char c = text.charAt(i);
            String rep = String.valueOf( c - 65 );
   
            if ( rep.length() < 2 )
               rep = "0" + rep;
   
            concat = concat + rep ;
   
            num = Long.parseLong(concat);
         }
         return num;  
      }
   
  
      public String int_to_String(long inttext)
      {
      String text="";
      long num=0;
      while(inttext>1){
      num= inttext%100;
      text = String.valueOf((char)(num+65))+text;
      inttext = inttext/100;
      }
      return text;

      }
   
   
      public int intoBlocks(String s){
      int len=0;
      int c=0;
   
      if(s.length()%2==0)
      len = s.length()/2;
      else
      len=(s.length()/2)-1;
   
     // System.out.println(len);
   
   
      return len;
      }
      public String[] ArrayinBlocks(String s, int x){
      String arr[] = new String[s.length()/x];
      int j=0;
   
      for(int i=0;i<s.length();i+=x){
      arr[j]=s.substring(i,Math.min(s.length(),(i+x)));
      j++;}
   
      return arr;
      }
   
    
      public long[] encrypt(String plaintext, long e, long n)
      {long[] ciphertext;
   
         int block=intoBlocks(plaintext); 
         int t = block;  
         if(block%2==0){
         t = block/2;
         ciphertext=new long[block/2];}
         else
         ciphertext=new long[block];
         long C;
         String arr[] = ArrayinBlocks(plaintext,intoBlocks(plaintext));
   
   
         for(int i=0;i<t;i++)
         ciphertext[i] = string_to_int(arr[i]);
         System.out.println(ciphertext[0] + " " + ciphertext[1]);
   
         for(int i=0;i<arr.length;i++)
         ciphertext[i] = modular_exponentiation(ciphertext[i], e, n);
   
         return ciphertext;
      }
   
   
      public String decrypt(long[] ciphertext, long d, long n){
      String plaintext="";
      for(int i=0;i<ciphertext.length;i++)
      plaintext+=int_to_String(modular_exponentiation(ciphertext[i],d,n));
      return plaintext;
      }
      public static void main(String[] args)
      {
   
         RSA cipher= new RSA(); 
   
         long[] n_e_d= cipher.generate_keys();
         System.out.println("Modulus n is: "+n_e_d[0]);
         System.out.println("Public key e is: "+n_e_d[1]);
         System.out.println("Private key d is: "+ n_e_d[2]);
         long[] ciphertext=cipher.encrypt("HebahNoor", n_e_d[1],n_e_d[0]);
         

         //------------


         System.out.println(" ciphertext blocks");
         for (long block: ciphertext)
            System.out.print (block+ " ");
         
         String plaintext=cipher.decrypt(ciphertext, n_e_d[2], n_e_d[0]);
         System.out.println("\n Decrypting ciphertext we get: "+plaintext);
   
      
      
      }
   
   }
