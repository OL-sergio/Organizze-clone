package udemy.java.organizze.helper;

import android.util.Base64;


public class Base64Custom {

    //https://www.base64decode.org/

    public static String encryptionBase64( String text ) {
      return Base64.encodeToString(text.getBytes(), Base64.DEFAULT)
              .replaceAll("([\\n\\r])","0")
              .replaceAll("\\.","1")
              .replaceAll("#","2")
              .replaceAll("\\$","3")
              .replaceAll("[\\[\\]]","4");
    }


    public static String decodeBase64( String textEncrypted ) {
       return new String( Base64.decode(textEncrypted, Base64.DEFAULT));
    }

}
