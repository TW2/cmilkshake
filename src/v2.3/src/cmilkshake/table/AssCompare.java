/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.table;

/**
 *
 * @author util2
 */
public class AssCompare<T> {
    
    private T object = null;
    private CompareColor color = CompareColor.None;

    public AssCompare() {
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public CompareColor getColor() {
        return color;
    }

    public void setColor(CompareColor color) {
        this.color = color;
    }
    
}
