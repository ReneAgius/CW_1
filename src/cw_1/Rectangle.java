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
//creating a rectangle object//
public class Rectangle extends TwoDShape
{

    //variables needed for a rectangle//

    private final double _height;
    private final double _width;

    public Rectangle(String name, double height, double width, String clientId)
    {

        super(4, name, "R", clientId); //setting the attributes in class Shape//
        _height = height;
        _width = width;
        //setting up the rectangle//
    }

    /**
     *
     * @return
     */
    @Override
    public double getArea()
    {
        return _width * _height; //area of a rectangle width* height//
    }

    @Override
    public double getPerimeter()
    {
        return _width * 2 + _height * 2; //perimeter of rectantle//
    }

    @Override
    public String displayDescription()
    {
        return super.displayDescription() + ". Height = " + this._height + ". Width = " + this._width + this.getArea() + ". Perimeter = " + this.getPerimeter();
    }

}
