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
//Setting up a ThreeeDShape to be used by 3D shapes//
public abstract class ThreeDShape extends Shape
{

    public ThreeDShape(int numberOfSides, String name, String shapeType, String clientId)
    {
        super(numberOfSides, name, shapeType, clientId); //inheriting from class Shape//
    }

    public abstract double getVolume(); //volume needed for 3D shapes//

    public abstract double getSurfaceArea(); //surface area needed for 3D shapes//
}
