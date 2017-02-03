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
//Creating Triangle object//
public class Triangle extends TwoDShape
{

    //variables needed for Triangle object//

    private final double _base;
    private final double _height;
    private final double _side1;
    private final double _side2;

    public Triangle(String name, double base, double height, double side1, double side2, String clientId)
    {
        super(3, name, "T", clientId); //setting attributes in class Shape//
        this._base = base;
        this._height = height;
        this._side1 = side1;
        this._side2 = side2;
        //setting up the Triangle//
    }

    @Override
    public double getArea()
    {
        return (_base * _height) / 2;  //area of a Triangle//
    }

    @Override
    public double getPerimeter()
    {
        return _side1 + _base + _side2; //perimiter of a Triangle//
    }

    @Override
    public String displayDescription()
    {
        return super.displayDescription() + ". Height = " + this._height + ". Base = " + this._base + ". Side1 = " + this._side1 + ". Side2 = " + this._side2 + this.getArea() + ". Perimeter = " + this.getPerimeter();
    }

}
