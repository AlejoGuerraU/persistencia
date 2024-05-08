
package com.mycompany.persistencia;
import java.util.Scanner; 
/*Integrantes
Alejandro Guerra Urrego - Sebastian Lopeara Orrego
dependencia gson-2.10.1.jar
  <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version> 
   </dependency>
*/

public class Persistencia {

    public static void main(String[] args) {
        Stock stock = new Stock();
      
             Scanner scanner = new Scanner(System.in);
        stock.options(scanner);
     
       stock.saveProductsToJSON();
      
    }
}