/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw_1;

import java.io.IOException;

/**
 *
 * @author Rene
 */
public class CW_1
{

    //setting arguments for application to start//

    final static String FORMAT = "--server <port> \n --client <host> <port>";

    public static void main(String[] args) throws IOException
    {
        //validating the input to start the application//
        if (args.length == 0) {
            System.out.println("Invalid arguments provided. Arguments: ");
            System.out.println(FORMAT);
            return;
        }

        //setting the arguments for the server side application//
        if (args[0].equals("--server") && args.length == 2) {
            final Server server = new Server(Integer.parseInt(args[1]));
            server.start();
        } //setting the arguments for the client side application//
        else if (args[0].equals("--client") && args.length == 3) {
            final Client client = new Client(args[1], Integer.parseInt(args[2]));
            client.start();
        } //prompting the user if arguments are incorrect//
        else {
            System.out.println("Invalid arguments provided. Arguments: ");
            System.out.println(FORMAT);
        }
    }
}
