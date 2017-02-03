/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw_1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rene
 */
//setting up the server side of application//
public class Server implements Saveable, Readable
{

    private int _port;

    private int _numberOfClientsConnected;

    private final static List<Shape> shapesList = Collections.synchronizedList(new ArrayList<Shape>());

    private final String filePath = "C:\\Users\\Rene\\Documents\\NetBeansProjects\\CW_1";

    Server(int port)
    {
        this._port = port;
        this._numberOfClientsConnected = 0;
    }

    public void start()
    {

        try {
            System.out.println("Server Started "); //prompt user server is working//
            final ServerSocket server = new ServerSocket(_port);
            System.out.println("Server waiting for client "); //prompt user server is waitng for client//
            while (true) {
                final Socket clientConnection = server.accept();

                // generate a thread for each client connected
                final Thread clientThread = new Thread(() -> {
                    execute(clientConnection);
                });
                clientThread.start();
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void execute(final Socket clientConnection)
    {
        try {

            System.out.println("Client connected to server");

            final PrintWriter out = new PrintWriter(clientConnection.getOutputStream(), true);
            final Scanner in = new Scanner(clientConnection.getInputStream());
            System.out.println("Server is listening to requests");

            // return client id to connected client
            out.println(_numberOfClientsConnected);
            _numberOfClientsConnected++;

            // wait for clients' requests
            while (in.hasNext()) {

                final String clientRequest = in.nextLine();
                //if client request arrives
                if (!clientRequest.isEmpty()) {
                    // initialize a json parser to convert from string to a json object
                    JsonParser parser = new JsonParser();

                    // parse request received from client to a json object
                    JsonObject request = parser.parse(clientRequest).getAsJsonObject();

                    // extract type of command required by client
                    String command = request.get("command").getAsString();

                    if (command.equals("save")) {
                        // initialize a gson object to help in extracting information
                        Gson gson = new GsonBuilder().create();

                        // extract shapes json array string from json request
                        String shapesAsPrimitive = request.get("shapes").getAsJsonPrimitive().getAsString();

                        // convert json array string to a json array object
                        JsonArray jsonArray = gson.fromJson(shapesAsPrimitive, JsonArray.class);

                        // loop over shapes in json array
                        for (int i = 0; i < jsonArray.size(); i++) {
                            // get each shape in json array
                            JsonObject shape = jsonArray.get(i).getAsJsonObject();

                            // get type of shape in json array
                            String shapeType = shape.get("_shapeType").getAsString();

                            // cast shape to appropriate shape class depending on the shapeType variable
                            if (shapeType.equals("R")) {
                                shapesList.add(gson.fromJson(shape, Rectangle.class));
                            } else if (shapeType.equals("C")) {
                                shapesList.add(gson.fromJson(shape, Circle.class));
                            } else if (shapeType.equals("Y")) {
                                shapesList.add(gson.fromJson(shape, Cylinder.class));
                            } else if (shapeType.equals("S")) {
                                shapesList.add(gson.fromJson(shape, Sphere.class));
                            } else if (shapeType.equals("T")) {
                                shapesList.add(gson.fromJson(shape, Triangle.class));
                            }
                        }

                        // save list of all shapes to file
                        SaveToFile(filePath);

                        // send message to client that shapes were saved successfully
                        out.println("Shapes saved successfully!");
                    } else if (command.equals("read")) {
                        // get shapes json string loaded from file
                        String allShapes = LoadFromFile(filePath);

                        // convert json string into a json object
                        final Gson gson = new GsonBuilder().create();
                        JsonObject allShapesObject = gson.fromJson(allShapes, JsonObject.class);

                        // get shapeType from payload
                        String shapeType = request.get("shape_type").getAsString();

                        // get client id from payload
                        String clientId = request.get("client_id").getAsString();

                        // get shapes array as a string to help in casting later on
                        String shapesAsPrimitive = allShapesObject.get("shapes").getAsJsonPrimitive().getAsString();

                        // convert json array string to a json array object
                        JsonArray jsonArray = gson.fromJson(shapesAsPrimitive, JsonArray.class);

                        // array holds filtered results
                        JsonArray filteredResults = new JsonArray();

                        // loop over the shapes and return only those that match the type and client id
                        for (int i = 0; i < jsonArray.size(); i++) {
                            // get particular shape in json array by index
                            JsonObject shape = jsonArray.get(i).getAsJsonObject();
                            // get shape's shape type
                            String currentShapeType = shape.get("_shapeType").getAsString();
                            // get shape's client id
                            String currentShapeClientId = shape.get("_clientId").getAsString();
                            // if current shape's shape type is equal to the one chosen by the client, (or if the chosen shape type is All)
                            // and the shape's client id matches the client id of the requesting client, add to the filtered list
                            if ((currentShapeType.equals(shapeType) || shapeType.equals("A")) && currentShapeClientId.equals(clientId)) {
                                filteredResults.add(shape);
                            }
                        }

                        // send shapes array to server
                        out.println(filteredResults);
                    }
                }
            }
        } catch (final Exception ex) {

            System.out.println("ERROR: " + ex.getMessage());
        }

        System.out.println("Client disconnected");

    }

    @Override
    public void SaveToFile(String filePath)
    {
        final File file = new File(filePath + "\\shapes.json");
        try {
            // create json object to hold all shapes
            JsonObject shapesJson = new JsonObject();

            // create file writer to write into file
            FileWriter writer = new FileWriter(file);

            // create gson object to serialize list
            final Gson gson = new GsonBuilder().create();

            // generate type of list to use in serialization
            Type listType = new TypeToken<List<Shape>>()
            {
            }.getType();

            shapesJson.addProperty("shapes", gson.toJson(shapesList, listType));

            // write json object to file
            gson.toJson(shapesJson, writer);
            writer.close();

        } catch (IOException e) {
        }
    }

    @Override
    public String LoadFromFile(String filePath)
    {
        File file = new File(filePath + "/shapes.json");
        try {
            // create file reader to read from file
            FileReader reader = new FileReader(file);

            // create json object to deserialize file
            final Gson gson = new GsonBuilder().create();

            // load shapes json from file
            JsonObject allShapesJson = gson.fromJson(reader, JsonObject.class);

            reader.close();

            // return shapes array
            return allShapesJson.toString();
        } catch (IOException e) {

        }
        return "";
    }

}
