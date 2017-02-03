/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw_1;

/**
 *
 * @author Rene
 */
public abstract class Shape
{

    //creating variables for Shape class//

    final int _numberOfSides;
    final String _name;
    final String _shapeType;
    final String _clientId;

    //setting the Shape class //
    public Shape(int numberOfSides, String name, String shapeType, String clientId)
    {
        _numberOfSides = numberOfSides;
        _name = name;
        _shapeType = shapeType;
        _clientId = clientId;
    }

    public String getShapeType()
    {
        return _shapeType;
    }

    public String getClientId()
    {
        return _clientId;
    }

    public String displayDescription()
    {
        return "Shape Type = " + _shapeType + ". Shape Name = " + _name + ". Number Of Sides = " + _numberOfSides;

    }
}
