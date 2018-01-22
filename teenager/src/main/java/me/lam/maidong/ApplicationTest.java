package me.lam.maidong;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    public static void main(String[] args) {

        String token="88";
      //  MyToash.Log(token);
        byte [] bytes=token.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            System.out.print("bytes["+i+"]="+bytes[i]+"");
        }
    }


    //monkey –p me.lam.maidong –v –v –v 50000 –throttle 5000>D:\monkey\test.txt
}