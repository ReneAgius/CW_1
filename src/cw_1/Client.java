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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rene
 */
public class Client
{

    private String _host; //variable to hold the host ip address//

    private int _port; //variable to hold the port number being used for connection//

    private String _clientId;

    private List<Shape> shapes = new ArrayList<Shape>(); //creating an ArrayList to contain shapes in it//

    Client(String host, int port)
    {
        this._host = host;
        this._port = port;
    }

    public void start() throws IOException
    {

        try {

            final Socket client = new Socket("127.0.0.1", 5555); //opening a socket using the local host and port 5555//
            final PrintWriter out = new PrintWriter(client.getOutputStream(), true); //send data to server//
            final Scanner in = new Scanner(client.getInputStream()); //receive data from server//
            final Scanner userIn = new Scanner(System.in); //taking user input through the scanner form command line//
            
            // wait for server to give you your client id
            while(in.hasNextLine()){
                _clientId = in.nextLine();
                break;
            }
            
            while (true) {
                System.out.println("1. Create 2D Shapes");
                System.out.println("2. Create 3D Shapes");
                System.out.println("3. Send Shapes");       //this is the menu presented to the user//
                System.out.println("4. Receive Shapes");
                System.out.println("5. Exit");

                System.out.println("Choose option: "); //prompt user to choose an option of which shape is needed//
                
                String userInput = userIn.nextLine(); //reading user input to act accordingly//
                
                if (userInput.equals("1")) {
                    System.out.println("Rectangle (R)"); //if 1 which is 2D shapes is selected the menu will display the  R,C,T options//
                    System.out.println("Circle (C)");
                    System.out.println("Triangle (T)");

                    String shapeChosen = userIn.nextLine();
                    if (shapeChosen.equals("R")) { //user selection is R referencing a rectangle//
                        System.out.println("Enter the name of the shape"); //ask for name//
                        String shapeName = userIn.nextLine();
                        System.out.println("Enter the height of the shape"); //ask for height//
                        double shapeHeight = userIn.nextDouble();
                        System.out.println("Enter the width of the shape"); //ask for width//
                        double shapeWidth = userIn.nextDouble();

                        Shape rectangle = new Rectangle(shapeName, shapeHeight, shapeWidth, _clientId); //create the rectangle object setup by user//

                        shapes.add(rectangle); //add the rectangle setup by user to Shape Arraylist//
                        
                        System.out.println("Rectangle added successfully!");
                        userIn.nextLine();// needed to consume whole line 
                    } else if (shapeChosen.equals("T")) { //user selection is T referencing a triangle//
                        System.out.println("Enter the name of the shape"); //ask for name//
                        String shapeName = userIn.nextLine();
                        System.out.println("Enter the height of the shape"); //ask for height//
                        double shapeHeight = userIn.nextDouble();
                        System.out.println("Enter the base of the shape"); //ask for base width being a triangle//
                        double shapeBase = userIn.nextDouble();
                        System.out.println("Enter the side1 of the shape"); //ask for side 1 height//
                        double shapeSide1 = userIn.nextDouble();
                        System.out.println("Enter the side2 of the shape"); //ask for side 2 height//
                        double shapeSide2 = userIn.nextDouble();

                        Shape triangle = new Triangle(shapeName, shapeHeight, shapeBase, shapeSide1, shapeSide2, _clientId); //creating the triangle object setup by user//

                        shapes.add(triangle); //add the triangle setup by user to Shape Arraylist//
                        
                        System.out.println("Triangle added successfully!");
                        userIn.nextLine();
                    } else if (shapeChosen.equals("C")) { //user selection is C referencing a circle//

                        System.out.println("Enter the name of the shape"); //ask for name//
                        String shapeName = userIn.nextLine();
                        System.out.println("Enter the radius"); //ask for radius//
                        double shapeRadius = userIn.nextDouble();

                        Shape circle = new Circle(shapeName, shapeRadius, _clientId); //create circle setup by user//

                        shapes.add(circle); //add the circle setup by user to Shape Arraylist//
                        
                        System.out.println("Circle added successfully!");
                        userIn.nextLine();// needed to consume whole line 
                    }

                } else if (userInput.equals("2")) { //user selects 2 referencing 3D shapes//
                    System.out.println("Sphere (S)"); //offer user selection Sphere and enter letter S//
                    System.out.println("Cylinder (Y)"); //offer user selection Cylinder and enter letter Y//

                    String shapeChosen = userIn.nextLine();
                    if (shapeChosen.equals("S")) { //user selection is S referencing Sphere//
                        System.out.println("Enter the name of the shape"); //enter name for the shape//
                        String shapeName = userIn.nextLine();
                        System.out.println("Enter the radius"); //enter the radius for the shape//
                        double shapeRadius = userIn.nextDouble();

                        Shape sphere = new Sphere(shapeName, shapeRadius, _clientId); //create sphere object//

                        shapes.add(sphere); //add the sphere created by user to Shape Arraylist//
                        
                        System.out.println("Sphere added successfully!");
                        userIn.nextLine();// needed to consume whole line 

                    } else if (shapeChosen.equals("Y")) { //user selects  referencing a Cylinder//
                        System.out.println("Enter the name of the shape"); //enter a name for the cylinder//
                        String shapeName = userIn.nextLine();
                        System.out.println("Enter the radius"); //enter the radius//
                        double shapeRadius = userIn.nextDouble();
                        System.out.println("Enter the height"); //enter the height//
                        double shapeHeight = userIn.nextDouble();

                        Shape cylinder = new Cylinder(shapeName, shapeRadius, shapeHeight, _clientId); //instansiate cylinder object//

                        shapes.add(cylinder); //add the cylinder created by user to Shape Arraylist//
                        
                        System.out.println("Cylinder added successfully!");
                        userIn.nextLine(); // needed to consume whole line 
                    }

                } else if (userInput.equals("3")) {
                    // start creating a json object to act as our payload
                    JsonObject payload = new JsonObject();
                    
                    // add the client's id to the payload
                    payload.addProperty("client_id", _clientId);
                    
                    // convert array list of shapes into a json array (string representation)
                    final Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .create();
                    String listOfShapes = gson.toJson(shapes);

                    // add json array of shapes to payload
                    payload.addProperty("shapes", listOfShapes);
                    
                    // add type of command to payload
                    payload.addProperty("command", "save");
                    
                    // send payload to server and wait for response
                    out.println(payload);
                    
                    // reset shapes list after sending to server
                    shapes = new ArrayList<Shape>();
                    
                    // wait for server's response
                    while (in.hasNext()) {
                        final String serverResponse = in.nextLine();
                        // when server response arrives, print it out
                        if (!serverResponse.isEmpty()) {
                            System.out.println(serverResponse);
                            break;
                        }
                    }
                } else if (userInput.equals("4")) {
                    // display menu to user for him to choose a filter
                    System.out.println("Rectangle (R)");
                    System.out.println("Circle (C)");
                    System.out.println("Triangle (T)");
                    System.out.println("Cylinder (Y)");
                    System.out.println("Sphere (S)");
                    System.out.println("All (A)");
                                        
                    userInput = userIn.nextLine();
                    
                    // start creating json payload
                    JsonObject payload = new JsonObject();
                    
                    // add client's id to payload
                    payload.addProperty("client_id", _clientId);
                    
                    // add type of command to payload
                    payload.addProperty("command", "read");
                    
                    // add chosen filter to payload
                    payload.addProperty("shape_type", userInput);
                    
                    // send payload to server and wait for response
                    out.println(payload);
                    while (in.hasNext()) {
                        final String serverResponse = in.nextLine();

                        // when response arrives
                        if (!serverResponse.isEmpty()) {

                            Gson gson = new GsonBuilder().create();

                            // convert json array string to a json array object
                            JsonArray jsonArray = gson.fromJson(serverResponse, JsonArray.class);

                            List<Shape> clientShapes = new ArrayList<Shape>();

                            // if json array is empty, print message to user
                            if(jsonArray.size() == 0){
                                System.out.println("No results found!");
                            }
                            
                            // loop over shapes in json array
                            for (int i = 0; i < jsonArray.size(); i++) {
                                // get each shape in json array
                                JsonObject shape = jsonArray.get(i).getAsJsonObject();

                                // get type of shape in json array
                                String shapeType = shape.get("_shapeType").getAsString();

                                // cast shape to appropriate shape class depending on the shapeType variable
                                if (shapeType.equals("R")) {
                                    clientShapes.add(gson.fromJson(shape, Rectangle.class));
                                } else if (shapeType.equals("C")) {
                                    clientShapes.add(gson.fromJson(shape, Circle.class));
                                } else if (shapeType.equals("Y")) {
                                    clientShapes.add(gson.fromJson(shape, Cylinder.class));
                                } else if (shapeType.equals("S")) {
                                    clientShapes.add(gson.fromJson(shape, Sphere.class));
                                } else if (shapeType.equals("T")) {
                                    clientShapes.add(gson.fromJson(shape, Triangle.class));
                                }
                            }
                            
                            // loop over returned shapes and print their description
                            for(Shape shape : clientShapes){
                                System.out.println(shape.displayDescription());
                            }

                            break;
                        }
                    }
                } else if (userInput.equals("5")) {
                    // if 5 is chosen, disconnect client
                    break;
                }

            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
}
