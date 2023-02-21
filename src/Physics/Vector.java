package Physics;

public class Vector {
    public static final Vector ZERO = new Vector(0, 0);

    public final int x;
    public final int y;


    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Vector addition.
     * @param vec vector to add
     * @return the vector this + vec
     */
    public Vector add(Vector vec) {
        return new Vector(this.getX() + vec.getX(), this.getY() + vec.getY());
    }

    /** Vector negation
     * @return the vector -this
     */
    public Vector negate() {
        return new Vector(-this.getX(), -this.getY());
    }

    /** Vector subtraction
     * @param removed vector to subtract
     * @return the vector this - removed
     */
    public Vector subtract(Vector removed) {
        return this.add(removed.negate());
    }

    /** Scalar multiplication of vectors
     * @param scalar a scalar
     * @return the vector scalar * this
     */
    public Vector multiply(int scalar) {
        return new Vector(scalar * this.getX(), scalar * this.getY());
    }

    /** Dotproduct of vectors
     * @param vec a vector
     * @return the product this . vec
     */
    public int dotProduct(Vector vec) {
        return this.getX() * vec.getX() + this.getY() * vec.getY();
    }


    /**
     * @return the square of the norm of this
     */
    public int norm2() {
        return this.dotProduct(this);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

}
