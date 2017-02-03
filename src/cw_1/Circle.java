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
//Creating Circle object//
public class Circle extends TwoDShape
{

    private final double _radius; //setting variable radius to create a Circle//

    public Circle(String name, double radius, String clientId)
    {
        super(1, name, "C", clientId); //Setting attributes in class Shape//
        this._radius = radius;
        //Setting the Circle radius//
    }

    @Override
    public double getArea()
    {
        return _radius * Math.PI * _radius; //area of a circle//

    }

    @Override
    public double getPerimeter()
    {
        return _radius * Math.PI * 2; //circumference of a circle//
    }

    @Override
    public String displayDescription()
    {
        return super.displayDescription() + ". Radius = " + this._radius + ". Area = " + this.getArea() + ". Perimeter = " + this.getPerimeter();
    }
}
