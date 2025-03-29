package com.generator;

public class GridProperties {

    // Grid properties
    private Size size;
    private float[][] grid;
    private Smoothing smoothing;

    /**
     * Class to represent the smoothing properties of the grid.
     * It contains properties such as radius, width, height, strength, and type.
     */
    private class Smoothing {
        public int radius;
        public int width;
        public int height;
        public float strength;
        public int type;

        /**
         * Constructor to create a Smoothing object with specified properties.
         * @param radius radius of the smoothing area
         * @param width width of smoothing area
         * @param height height of smoothing area
         * @param strength strength of the smoothing effect
         * @param type type of smoothing (0 for circle, 1 for rectangle)
         */
        public Smoothing(int radius, int width, int height, float strength, int type) {
            this.radius = radius;
            this.width = width;
            this.height = height;
            this.strength = strength;
            this.type = type;
        }
    }

    /**
     * Class to represent the size of the grid.
     * It contains properties such as width and height.
     * This class is used to store the dimensions of the grid.
     */
    private class Size {
        public int width;
        public int height;

        /**
         * Constructor to create a Size object with specified width and height.
         * @param width width of the grid
         * @param height height of the grid
         */
        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    /**
     * Default constructor to create a grid with default size and properties.
     * The default size is set to 100x100 and the grid is initialized with default values.
     */
    public GridProperties() {
        // Default constructor
        this.size = new Size(100, 100);
        this.grid = new float[0][0];
        this.smoothing = new Smoothing(0, 0, 0, 0, 0);
    }

    /**
     * Constructor to create a grid with specified width and height.
     * The grid is generated using the Generator class.
     * @param width width of the grid
     * @param height height of the grid
     * @throws IllegalArgumentException if width or height is less than or equal to 0
     */
    public GridProperties(int width, int height) throws IllegalArgumentException {

        // Validate width and height
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0.");
        }

        // Initialize grid with specified width and height
        this.size = new Size(width, height);
        this.grid = new float[getSize().height][getSize().width];
        this.smoothing = new Smoothing(4, width, height, .5f, 0);
        
    }

    /**
     * Constructor to create a grid with specified width, height, and smoothing properties.
     * The grid is generated using the Generator class.
     * @param width width of the grid
     * @param height height of the grid
     * @param radius radius of the smoothing area
     * @param strength strength of the smoothing effect
     * @param type type of smoothing (0 for circle, 1 for rectangle)
     * @throws IllegalArgumentException if width or height is less than or equal to 0.
     * or if radius is less than or equal to 0.
     * or if strength is less than 0 or greater than 1.
     */
    public GridProperties(int width, int height, int radius, float strength, int type) throws IllegalArgumentException {

        // Validate width and height
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0.");
        }

        // Validate radius
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0.");
        }

        // Validate strength
        if (strength < 0 || strength > 1) {
            throw new IllegalArgumentException("Strength must be between 0 and 1.");
        }

        // Initialize grid with specified width and height
        this.size = new Size(width, height);
        this.grid = new float[getSize().height][getSize().width];
        this.smoothing = new Smoothing(radius, width, height, strength, type);
    }

    /**
     * Method to set the grid values using the Generator class.
     * @return the generated grid
     */
    public float[][] getGrid() {
        return grid;
    }

    /**
     * Method that gets the grid width and height.
     * @return size object containing width and height of the grid
     */
    public Size getSize() {
        return size;
    }

    /**
     * Method to get all properties surrounding smoothing options and values
     * @return smoothing object containing smoothing properties
     */
    public Smoothing getSmoothing() {
        return smoothing;
    }

    @Override
    public String toString() {
        /* 
         * return a representation of the grid properties in a readable format.
         * - : > 0.25
         * + : > 0.5
         * * : > 0.75
         * # : >= 1.0
        */
        StringBuilder sb = new StringBuilder();
        sb.append("Grid Properties:\n");
        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                if (grid[i][j] > 1.0) {
                    sb.append("# ");
                } else if (grid[i][j] > 0.75) {
                    sb.append("* ");
                } else if (grid[i][j] > 0.5) {
                    sb.append("+ ");
                } else if (grid[i][j] > 0.25) {
                    sb.append("- ");
                } else {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
