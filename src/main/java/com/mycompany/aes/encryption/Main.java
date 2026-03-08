/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes.encryption;

import java.util.Scanner;

/**
 *
 * @author Riju Bidani
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to encrypt plaintext or decrypt ciphertext?");
	System.out.print("Enter D for decryption or E for encryption: ");
	char c = Character.toUpperCase(sc.next().charAt(0));
	sc.nextLine();
        
        switch (c) {
            case 'D' ->                 {
                    System.out.print("Please enter the ciphertext: ");
                    String ciphertext = sc.nextLine();
                    System.out.print("Please enter the encryption key: ");
                    String key = sc.nextLine();
                    String decrypted = AES128Decryption.decrypt(ciphertext, key);
                    System.out.println();
                    System.out.println("Decrypted text is: " + decrypted);
                }
            case 'E' ->                 {
                    System.out.print("Please enter the plaintext: ");
                    String plaintext = sc.nextLine();
                    System.out.print("Please enter the encryption key: ");
                    String key = sc.nextLine();
                    String encrypted = AES128Encryption.encrypt(plaintext, key);
                    System.out.println();
                    System.out.println("Encrypted text is: " + encrypted);
                }
            default -> System.out.println("Invalid option. Please enter D or E.");
        }
    }
}
