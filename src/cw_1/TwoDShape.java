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
//setting class TwoDShape for 2D shapes//
public abstract class TwoDShape extends Shape
{

    public TwoDShape(int numberOfSides, String name, String shapeType, String clientId)
    {
        super(numberOfSides, name, shapeType, clientId); //inheriting for class Shape//
    }

    public abstract double getArea(); //Area needed for 2D shape//

    public abstract double getPerimeter(); //Perimeter needed for 2D shape//
}
