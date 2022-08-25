/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trainsandtowns;

/**
 *
 * @author Paul
 */
public class Route
{
    private final int from;
    private final int to;
    private final int distance;
    
    public Route(final int from, final int to, final int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
    
    public int from() {
        return this.from;
    }
    
    public int to() {
        return this.to;
    }
    
    public int distance() {
        return this.distance;
    }
}