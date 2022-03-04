package com.eduardoprogramador.http;

/*
*   Copyright 2022. Eduardo Programador
*   www.eduardoprogramador.com
*   consultoria@eduardoprogramador.com
*
*   All Rights Reserved
*
*   This class contains method to handle some common Http routines
*   like GET, POST and Download requests.
*   Also, some usefull methods for open a given url on the browser
*   and sor splitting the web link with the host and path part.
*
* */

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Http {

    /*
    *   This class has private constructor.
    *   You cant't initiate it.
    *   Instead, use the static method for build the job.
    * */
    private Http(){}

    /*
    *   This method starts the http object.
    *   From now, you can call the other methods
    * */
    public static Http buildRequest() {
        return new Http();
    }

    /*
    *   Makes an HTTP POST request and returns the result as String,
    *   which contains the server response.
    *
    *   Parameters:
    *
    *   host: the host part of the URL. Eg.: eduardoprogramador.com
    * isHttps: true for HTTPS connections, false otherwise
    *   port: The address port of the server
    *   path: The path inside the host containing the file to access. Eg.: /img/logo.png
    *   parameters: An arraylist of arraylists that holds string arrays of params.
    *       Eg.: ArrayList<ArrayList> params = new ArrayList<>();
    *           ArrayList<String> par = new ArrayList<>();
    *           par.add("key");
    *           par.add("value");
    *           params.add(par);
    *
    *   headers: same type of parameters, but it holds the headers.
    *
    * */
    public String post(String host, boolean isHttps, int port, String path, ArrayList<ArrayList> parameters, ArrayList<ArrayList> headers) {

        StringBuilder answer = new StringBuilder();
        URL url;
        StringBuilder stringBuilder;
        OutputStream outputStream;
        BufferedReader bufferedReader;
        String line;
        byte[] out;
        if (isHttps) {
            try {
                url = new URL("https", host, port, path);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);

                if(headers != null) {

                    for (int i = 0; i < headers.size(); i++) {
                        ArrayList<String> header = headers.get(i);
                        httpsURLConnection.addRequestProperty(header.get(0), header.get(1));
                    }
                }

                stringBuilder = new StringBuilder();

                for (int j = 0; j < parameters.size(); j++) {
                    ArrayList<String> param = parameters.get(j);
                    stringBuilder.append(URLEncoder.encode(param.get(0),"UTF-8") + "=" + URLEncoder.encode(param.get(1),"UTF-8"));
                    if((j+1) < parameters.size()) {
                        stringBuilder.append("&");
                    }
                }

                out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

                httpsURLConnection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
                httpsURLConnection.addRequestProperty("Content-Length","" + out.length);
                httpsURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpsURLConnection.setFixedLengthStreamingMode(out.length);
                outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(out);
                outputStream.flush();
                httpsURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                line = null;

                while((line = bufferedReader.readLine()) != null) {
                    answer.append(line);
                }

                bufferedReader.close();
                httpsURLConnection.disconnect();

                return answer.toString();

            } catch (Exception var14) {
                var14.printStackTrace();
                return null;
            }
        } else {
            try {
                url = new URL("http", host, port, path);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                stringBuilder = new StringBuilder();

                for (int i = 0; i < headers.size(); i++) {
                    ArrayList<String> header =  headers.get(i);
                    httpURLConnection.addRequestProperty(header.get(0),header.get(1));
                }

                stringBuilder = new StringBuilder();

                if(headers != null) {

                    for (int j = 0; j < parameters.size(); j++) {
                        ArrayList<String> param = parameters.get(j);
                        stringBuilder.append(URLEncoder.encode(param.get(0), "UTF-8") + "=" + URLEncoder.encode(param.get(1), "UTF-8"));
                        if ((j + 1) < parameters.size()) {
                            stringBuilder.append("&");
                        }
                    }
                }


                out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

                httpURLConnection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
                httpURLConnection.addRequestProperty("Content-Length","" + out.length);
                httpURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpURLConnection.setFixedLengthStreamingMode(out.length);
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(out);
                outputStream.flush();
                httpURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                line = null;

                while((line = bufferedReader.readLine()) != null) {
                    answer.append(line);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();
                return answer.toString();
            } catch (Exception var15) {
                var15.printStackTrace();
                return null;
            }
        }
    }

    /*
     *   Makes an HTTP POST request,reads the bytes received
     *    and writes them into an output file,
     *
     *   Parameters:
     *
     *   host: the host part of the URL. Eg.: eduardoprogramador.com
     *   isHttps: true for HTTPS connections, false otherwise
     *   port: The address port of the server
     *   path: The path inside the host containing the file to access. Eg.: /img/logo.png
     *   parameters: An arraylist of arraylists that holds string arrays of params.
     *       Eg.: ArrayList<ArrayList> params = new ArrayList<>();
     *           ArrayList<String> par = new ArrayList<>();
     *           par.add("key");
     *           par.add("value");
     *           params.add(par);
     *
     *   headers: same type of parameters, but it holds the headers.
     *   outFile: The path of the new file that will receive the downloaded bytes.
     *
     * */
    public boolean post(String host, boolean isHttps, int port, String path, ArrayList<ArrayList> parameters, ArrayList<ArrayList> headers, String outFile) {

        URL url;
        OutputStream outputStream;
        DataInputStream dataInputStream;
        StringBuilder stringBuilder = new StringBuilder();
        byte[] out;
        byte[] bytes = new byte[1];
        int count;
        if (isHttps) {
            try {

                File file = new File(outFile);
                if(!file.exists())
                    file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                url = new URL("https", host, port, path);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);

                if(headers != null) {

                    for (int i = 0; i < headers.size(); i++) {
                        ArrayList<String> header = headers.get(i);
                        httpsURLConnection.addRequestProperty(header.get(0), header.get(1));
                    }
                }

                stringBuilder = new StringBuilder();

                for (int j = 0; j < parameters.size(); j++) {
                    ArrayList<String> param = parameters.get(j);
                    stringBuilder.append(URLEncoder.encode(param.get(0),"UTF-8") + "=" + URLEncoder.encode(param.get(1),"UTF-8"));
                    if((j+1) < parameters.size()) {
                        stringBuilder.append("&");
                    }
                }

                out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

                httpsURLConnection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
                httpsURLConnection.addRequestProperty("Content-Length","" + out.length);
                httpsURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpsURLConnection.setFixedLengthStreamingMode(out.length);
                outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(out);
                outputStream.flush();
                httpsURLConnection.connect();
                dataInputStream = new DataInputStream(httpsURLConnection.getInputStream());

                while ((count = dataInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }

                fileOutputStream.close();
                dataInputStream.close();
                httpsURLConnection.disconnect();

                return true;

            } catch (Exception var14) {
                var14.printStackTrace();
                return false;
            }
        } else {
            try {

                File file = new File(outFile);
                if(!file.exists())
                    file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                url = new URL("http", host, port, path);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                stringBuilder = new StringBuilder();

                for (int i = 0; i < headers.size(); i++) {
                    ArrayList<String> header =  headers.get(i);
                    httpURLConnection.addRequestProperty(header.get(0),header.get(1));
                }

                stringBuilder = new StringBuilder();

                if(headers != null) {

                    for (int j = 0; j < parameters.size(); j++) {
                        ArrayList<String> param = parameters.get(j);
                        stringBuilder.append(URLEncoder.encode(param.get(0), "UTF-8") + "=" + URLEncoder.encode(param.get(1), "UTF-8"));
                        if ((j + 1) < parameters.size()) {
                            stringBuilder.append("&");
                        }
                    }
                }


                out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

                httpURLConnection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
                httpURLConnection.addRequestProperty("Content-Length","" + out.length);
                httpURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpURLConnection.setFixedLengthStreamingMode(out.length);
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(out);
                outputStream.flush();
                httpURLConnection.connect();
                dataInputStream = new DataInputStream(httpURLConnection.getInputStream());

                while ((count = dataInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }

                dataInputStream.close();
                dataInputStream.close();
                httpURLConnection.disconnect();
                return true;
            } catch (Exception var15) {
                var15.printStackTrace();
                return false;
            }
        }
    }

    /*
     *   Makes an HTTP GET request and returns the result as String,
     *   which contains the server response.
     *
     *   Parameters:
     *
     *   host: the host part of the URL. Eg.: eduardoprogramador.com
     *  isHttps: true for HTTPS connections, false otherwise
     *   port: The address port of the server
     *   path: The path inside the host containing the file to access. Eg.: /img/logo.png
     *   parameters: An arraylist of arraylists that holds string arrays of params.
     *       Eg.: ArrayList<ArrayList> params = new ArrayList<>();
     *           ArrayList<String> par = new ArrayList<>();
     *           par.add("key");
     *           par.add("value");
     *           params.add(par);
     *
     *   headers: same type of parameters, but it holds the headers.
     *
     * */
    public String get(String host, boolean isHttps, int port, String path, ArrayList<ArrayList> parameters, ArrayList<ArrayList> headers) {

        StringBuilder answer = new StringBuilder();
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        OutputStream outputStream;
        BufferedReader bufferedReader;
        String line;
        byte[] out;
        if (isHttps) {
            try {

                if(parameters != null) {
                    for (int j = 0; j < parameters.size(); j++) {
                        ArrayList<String> param = parameters.get(j);
                        stringBuilder.append(URLEncoder.encode(param.get(0), "UTF-8") + "=" + URLEncoder.encode(param.get(1), "UTF-8"));
                        if ((j + 1) < parameters.size()) {
                            stringBuilder.append("&");
                        }
                    }
                }

                String local = (parameters == null) ? path : (path + "?" + stringBuilder.toString());

                url = new URL("https", host, port, local);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                httpsURLConnection.setRequestMethod("GET");

                if(headers != null) {

                    for (int i = 0; i < headers.size(); i++) {
                        ArrayList<String> header = headers.get(i);
                        httpsURLConnection.addRequestProperty(header.get(0), header.get(1));
                    }
                }

                stringBuilder = new StringBuilder();

                httpsURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpsURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                line = null;

                while((line = bufferedReader.readLine()) != null) {
                    answer.append(line);
                }

                bufferedReader.close();
                httpsURLConnection.disconnect();

                return answer.toString();

            } catch (Exception var14) {
                var14.printStackTrace();
                return null;
            }
        } else {
            try {
                stringBuilder = new StringBuilder();

                if(parameters != null) {
                    for (int j = 0; j < parameters.size(); j++) {
                        ArrayList<String> param = parameters.get(j);
                        stringBuilder.append(URLEncoder.encode(param.get(0), "UTF-8") + "=" + URLEncoder.encode(param.get(1), "UTF-8"));
                        if ((j + 1) < parameters.size()) {
                            stringBuilder.append("&");
                        }
                    }
                }

                String local = (parameters == null) ? path : (path + "?" + stringBuilder.toString());

                url = new URL("http", host, port, path + "?" + local);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                if(headers != null) {
                    for (int i = 0; i < headers.size(); i++) {
                        ArrayList<String> header = headers.get(i);
                        httpURLConnection.addRequestProperty(header.get(0), header.get(1));
                    }
                }

                stringBuilder = new StringBuilder();

                httpURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                line = null;

                while((line = bufferedReader.readLine()) != null) {
                    answer.append(line);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();
                return answer.toString();
            } catch (Exception var15) {
                var15.printStackTrace();
                return null;
            }
        }
    }

    /*
    *   Downloads a file from a URL link.
    *
    *   Parameters:
    *
    *   host: The host to connect to. Eg.: eduardoprogramador.com
    *   path: The path of the file on the server side. Eg.: /bigdata/valores.pdf
    *   isHttps: true for HTTPSconnections, false otherwise
    *   outPath: The file path of the destination file on your local machine
    *   port: The server port
    * */
    public static boolean downloadFromLink(String host, String path,boolean isHttps, String outPath,int port) {

        URL url = null;
        int count = 0;
        byte[] bytes = new byte[1];
        int size = -1;
        int progress = 0;


        if (isHttps) {
            try {

                url = new URL("https",host,port,path);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                httpsURLConnection.setRequestMethod("GET");

                httpsURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");
                httpsURLConnection.connect();

                size = httpsURLConnection.getContentLength();

                File file = new File(outPath);
                if(!file.exists())
                    file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                InputStream inputStream = httpsURLConnection.getInputStream();


                while ((count = inputStream.read(bytes)) != -1) {

                    progress++;

                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();

                    System.out.println("Progress: " + progress + " of " + size);

                }



                fileOutputStream.close();

                httpsURLConnection.disconnect();

                return true;


            } catch (Exception var14) {
                var14.printStackTrace();
                return false;
            }
        } else {
            try {
                url = new URL("http",host,port,path);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

                httpURLConnection.connect();

                size = httpURLConnection.getContentLength();

                File file = new File(outPath);
                if(!file.exists())
                    file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                InputStream inputStream = httpURLConnection.getInputStream();
                while ((count = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }

                httpURLConnection.disconnect();
                return true;
            } catch (Exception var15) {
                var15.printStackTrace();
                return false;
            }
        }
    }

    /*
    *   Splits the host and file path of an URL link
    *   and returns a list of strings of size 2.
    *   The first is the server host,
    *   while the second one is the file path on the server side.
    *
    *   Eg.: https://eduardoprogramador.com/bigdata/valores.pdf
    *
    *   res[0] = eduardoprogramador.com
    *   res[1] = /bigdata/valores.pdf
    * */
    public static String[] splitHostAndPath(String link) {
        String[] res = new String[2];
        StringBuilder stringBuilder = null;

        String[] one = link.split("//");

        String[] two = one[1].split("/");

        res[0] = two[0];
        stringBuilder = new StringBuilder();
        for (int i = 1; i < two.length; i++) {
            stringBuilder.append("/" + two[i]);
        }

        res[1] = stringBuilder.toString();

        return res;
    }

    /*
    *   Open an URL link in your default browser
    * */
    public static void openInBrowser(String link) {
        try {
            Desktop.getDesktop().browse(URI.create(link));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

