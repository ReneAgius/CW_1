/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw_1;

import java.io.Serializable;

/**
 *
 * @author Rene
 */
//setting up a Sphere object//
public class Sphere extends ThreeDShape implements Serializable
{

    private final double radius; //setting variable needed to create a sphere//

    public Sphere(String name, double radius, String clientId)
    {
        super(1, name, "S", clientId); //setting attributes in class Shape//
        this.radius = radius;
    }

    @Override
    public double getVolume()
    {
        return (4 / 3) * Math.PI * radius * radius * radius; //volume of a sphere//
    }

    @Override
    public double getSurfaceArea()
    {
        return 4 * Math.PI * radius * radius; //surface area of a sphere//
    }

    @Override
    public String displayDescription()
    {
        return super.displayDescription() + ". Radius = " + this.radius + ". Surface Area = " + this.getSurfaceArea() + ". Volume = " + this.getVolume();
    }

}
