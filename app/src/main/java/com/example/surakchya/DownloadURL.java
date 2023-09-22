package com.example.surakchya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadURL {
   public String readUrl(String myUrl) throws IOException{
       String data="";
       InputStream inputStream = null;
       HttpURLConnection urlConnection = null;

       try {
           URL url = new URL(myUrl);
           urlConnection = (HttpURLConnection) url.openConnection();
           urlConnection.connect();


           // reading data from url
           inputStream = urlConnection.getInputStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
           StringBuffer sb = new StringBuffer();

           // read each line using while loop and append it to the String Buffer
           String line ="";
           while ((line = br.readLine()) != null){
               sb.append(line);
           }
           // String Buffer to String
           data = sb.toString();
           br.close();
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       finally {
           inputStream.close();
           urlConnection.disconnect();
       }
   return data;
   }

}
