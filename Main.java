package ceaserCypherDecrypter;

import static sbcc.Core.*;

import java.io.*;
import java.util.*;

import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

/**
 * 
 * @CodyCole
 *
 */
public class Main {

	public static String Encrypt(String message, int key) {
		// For each character
		// Add 3
		// If larger than 122, modulus char.
		StringBuilder encryptedMsg = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			int c = message.charAt(i);

			// Ignore spaces
			if (c == 32) {
				encryptedMsg.append(" ");
				continue;
			}

			c += key;
			if (c > 122) {
				int modulo = c % 122;
				c = 96 + modulo;
			}

			// Convert int to char and append to encrypted message
			char ch = (char) c;
			encryptedMsg.append(ch);
		}
		return encryptedMsg.toString();
	}


	public static String Decrypt(String message, int dec) {
		StringBuilder decryptedMsg = new StringBuilder();

		for (int i = 0; i < message.length(); i++) {
			int c = message.charAt(i);

			// Ignore spaces
			if (c == 32) {
				decryptedMsg.append(" ");
				continue;
			}

			c -= dec;
			if (c < 97) {
				int remainder = 96 - c;
				c = 122 - remainder;
			}

			// Convert int to char and append to decrypted message
			char ch = (char) c;
			decryptedMsg.append(ch);
		}
		return decryptedMsg.toString();
	}


	public static int FindDecryptKey(String message, int cap) throws IOException {
		// Create hash table and load dictionary file
		HashMap dictionary = new HashMap();
		List<String> words = readFileAsLines("dictionary.txt");

		for (String word : words) {
			dictionary.put(word, word);
		}
		println("Uploaded Successfully");

		// Increment key until decrypted word is found in dictionary
		for (int i = 1; i < cap; i++) {
			String word = Decrypt(message, i);
			if (dictionary.containsKey(word)) {
				return i;
			}
		}
		return 0;
	}


	public static void main(String[] args) throws IOException {
		String message = "tomorrow";
		String encryptedMessage = Encrypt(message, 6);
		String decryptedMesssage = Decrypt(encryptedMessage, 6);
		println("Encrypted message: " + encryptedMessage);
		println("Decrypted message: " + decryptedMesssage);
		println("Encrypted key is: " + FindDecryptKey(encryptedMessage, 10));
	}

}
