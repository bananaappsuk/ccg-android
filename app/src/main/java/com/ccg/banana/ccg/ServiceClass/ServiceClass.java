package com.ccg.banana.ccg.ServiceClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Dell1 on 27-May-17.
 */

public class ServiceClass {

    public Report getJsonArrayResponse(String cURL) {

    Report report = new Report();
    HttpURLConnection client = null;
    JSONArray response = null;
    StringBuilder result;
    OutputStreamWriter writer;
    InputStream input;
    String output, line;
    BufferedReader reader;

    try {
        URL url = new URL(cURL);

        client = (HttpURLConnection) url.openConnection();
        client.setRequestMethod("GET");
        client.connect();
        // Read the input stream into temples_list_row String
        InputStream inputStream = client.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
            return null;
        }
        output = buffer.toString();
        response = new JSONArray(output);
        report.setStatus("true");
        report.setJsonArray(response);
    } catch (ConnectException e) {
        report.setStatus("false");
        report.setErr_code(401);
        report.setMessage("Connection timed out, please try again");
    } catch (SocketTimeoutException e) {
        report.setStatus("false");
        report.setErr_code(404);
        report.setMessage("Connection timed out, please try again");
    }
    catch (Exception e) {
        report.setStatus("false");
        report.setErr_code(500);
        report.setMessage("Unknown error occurred, please try again");
    } finally {
        try {
            client.disconnect();
        } catch (NullPointerException ex) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        }
    }
    return report;
}

    public Report getJsonObjectResponse(JSONObject jsonObject, String urlLink) {
        HttpURLConnection client = null;
        JSONObject responseObj = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;
        Report report = new Report();

        try {
            URL url = new URL(urlLink);
            client = (HttpURLConnection) url.openConnection();
            client.setConnectTimeout(10000);
            client.setReadTimeout(10000);
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Content-Type", "application/json");
            client.setRequestMethod("POST");
            client.connect();
            writer = new OutputStreamWriter(client.getOutputStream());
            output = jsonObject.toString();
            writer.write(output);
            writer.flush();
            writer.close();
            input = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            responseObj = new JSONObject(result.toString());
            report.setStatus("true");
            report.setJsonObject(responseObj);

        } catch (ConnectException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (SocketTimeoutException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (Exception e) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {
                report.setStatus("false");
                report.setErr_code(500);
                report.setMessage("Unknown error occurred, please try again");
            }
        }
        return report;
    }



    public Report getJsonObjectResponsePost(String cURL) {

        Report report = new Report();
        HttpURLConnection client = null;
        JSONObject response = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;

        try {
            URL url = new URL(cURL);

            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.connect();
            // Read the input stream into temples_list_row String
            InputStream inputStream = client.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            output = buffer.toString();
            response = new JSONObject(output);
            report.setStatus("true");
            report.setJsonObject(response);
        } catch (ConnectException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (SocketTimeoutException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (Exception e) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {
                report.setStatus("false");
                report.setErr_code(500);
                report.setMessage("Unknown error occurred, please try again");
            }
        }
        return report;
    }

    public Report getJsonObjectResponse(String cURL) {

        Report report = new Report();
        HttpURLConnection client = null;
        JSONObject response = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;

        try {
            URL url = new URL(cURL);

            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.connect();
            // Read the input stream into temples_list_row String
            InputStream inputStream = client.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            output = buffer.toString();
            response = new JSONObject(output);
            report.setStatus("true");
            report.setJsonObject(response);
        } catch (ConnectException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (SocketTimeoutException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (Exception e) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {
                report.setStatus("false");
                report.setErr_code(500);
                report.setMessage("Unknown error occurred, please try again");
            }
        }
        return report;
    }

    public Report getJsonObjectResponseTest(String cURL) {

        Report report = new Report();
        HttpURLConnection client = null;
        JSONObject response = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;

        try {
            URL url = new URL(cURL);

            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.connect();
            int responseCode= client.getResponseCode();
            // Read the input stream into temples_list_row String
            if(responseCode==200) {
                InputStream inputStream = client.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                output = buffer.toString();
                response = new JSONObject(output);
                report.setStatus("true");
                report.setJsonObject(response);
            }
            else{
                InputStream inputStream = client.getErrorStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                output = buffer.toString();
                response = new JSONObject(output);
                report.setStatus("true");
                report.setJsonObject(response);
            }

        } catch (ConnectException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (SocketTimeoutException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (Exception e) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {
                report.setStatus("false");
                report.setErr_code(500);
                report.setMessage("Unknown error occurred, please try again");
            }
        }
        return report;
    }

    public Report getJsonObjectResponseTest(JSONObject jsonObject, String urlLink) {
        HttpURLConnection client = null;
        JSONObject responseObj = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;
        Report report = new Report();

        try {

            URL url = new URL(urlLink);
            client = (HttpURLConnection) url.openConnection();
            client.setConnectTimeout(10000);
            client.setReadTimeout(10000);
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Content-Type", "application/json");
            client.setRequestMethod("POST");
            client.connect();
                writer = new OutputStreamWriter(client.getOutputStream());
                output = jsonObject.toString();
                writer.write(output);
                writer.flush();
                writer.close();
            int responseCode= client.getResponseCode();
            // Read the input stream into temples_list_row String
            if(responseCode==200) {
                input = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
                result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                responseObj = new JSONObject(result.toString());
                report.setStatus("true");
                report.setJsonObject(responseObj);
            }

            else
            {
             /*   input = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
                result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                responseObj = new JSONObject(result.toString());
                report.setStatus("true");
                report.setJsonObject(responseObj);*/

                InputStream inputStream = client.getErrorStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                output = buffer.toString();
                responseObj = new JSONObject(output);
                report.setStatus("true");
                report.setJsonObject(responseObj);
            }
        } catch (ConnectException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (SocketTimeoutException e) {
            report.setStatus("false");
            report.setErr_code(401);
            report.setMessage("Connection timed out, please try again");
        } catch (Exception e) {
            report.setStatus("false");
            report.setErr_code(500);
            report.setMessage("Unknown error occurred, please try again");
        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {
                report.setStatus("false");
                report.setErr_code(500);
                report.setMessage("Unknown error occurred, please try again");
            }
        }
        return report;
    }
}

