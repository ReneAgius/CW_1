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
//setting a Cylinder object//
public class Cylinder extends ThreeDShape
{

    private final double _height; //variable height needed for a cylinder//
    private final double _radius; //variable radius needed for a cylinder//

    public Cylinder(String name, double height, double radius, String clientId)
    {
        super(3, name, "Y", clientId); //setting class Shape//
        this._height = height; //setting height//
        this._radius = radius; //setting radius//
    }

    @Override
    public double getVolume()
    {
        return Math.PI * _radius * _radius * _height; //volume of a cylinder//
    }

    @Override
    public double getSurfaceArea()
    {
        return ((Math.PI * 2) * _radius * _height) + (2 * Math.PI * _radius * _radius); //surface area of a cylinder//
    }

    @Override
    public String displayDescription()
    {
        return super.displayDescription() + ". Radius = " + this._radius + ". Surface Area = " + this.getSurfaceArea() + ". Height = " + this._height + this.getSurfaceArea() + ". Volume = " + this.getVolume();
    }

}
