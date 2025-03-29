package com.generator;

public class Generator {

    /**
     * Generates a grid of the specified width and height, initializing all cells to 0.
     */
    public static float[][] generateGrid(int width, int height) {
        float[][] grid = new float[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = 0; // Initialize all cells to 0
            }
        }
        return grid;
    }

    /**
     * Generates noise in the grid by randomly assigning values of 0 or 1 to each cell.
     * This method is useful for creating a random pattern in the grid.
     * @param grid
     */
    public static void generateNoice(float[][] grid) {
        int width = grid.length;
        int height = grid[0].length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = (float) ((int) (Math.random() * 2));
            }
        }
    }

    /**
     * Smooths the grid using a circular averaging method.
     * This method takes a grid and a radius as input, and applies a smoothing effect to the grid.
     * @param grid
     * @param radius
     */
    public static void applySmoothingCircle(float[][] grid, int radius) {
        int width = grid.length;
        int height = grid[0].length;
        float[][] newGrid = new float[width][height];

        // using gridGetAvarageCircle to smooth the grid
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newGrid[y][x] = (float) gridGetAvarageCircle(grid, x, y, radius);
            }
        }

        System.arraycopy(newGrid, 0, grid, 0, width * height);
    }

    /**
     * Adjusts the grid values to pull them towards the specified points within a given range.
     *
     * @param grid  The grid to modify.
     * @param points An array of point values to pull the grid values towards.
     * @param range  The range within which the grid values are allowed to be adjusted.
     * @param strength The strength of the pull towards the points.
     *                A higher value means a stronger pull.
     *                Must be between 0 and 1.
     */
    public static void applyPeakPoints(float[][] grid, float[] points, float range, float strength) throws IllegalArgumentException{
        // Validate the strength parameter
        if (strength < 0 || strength > 1) {
            throw new IllegalArgumentException("Strength must be between 0 and 1.");
        }
       
        // Get the width and height of the grid
        int width = grid.length;
        int height = grid[0].length;

        // Execute the smoothing effect on peak points
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (float point : points) {
                    if (Math.abs(grid[y][x] - point) <= range) {
                        grid[x][y] += (point - grid[y][x]) * strength; // f(x) = x + (point - x) * strength
                    }
                }
            }
        }
    }

    /**
     * Applies a smoothing effect to the grid using a rectangular averaging method.
     * This method takes a grid, width, and height as input, and applies a smoothing effect to the grid.
     * @param grid the grid to apply the smoothing effect to
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public static void applySmoothingRectangle(float[][] grid, int width, int height) {

        // Validate the width and height parameters
        int gridWidth = grid.length;
        int gridHeight = grid[0].length;
        float[][] newGrid = new float[gridWidth][gridHeight];

        // using gridGetAvarageRectangle to smooth the grid
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                newGrid[x][y] = (int) gridGetAvarageRectangle(grid, x, y, width, height);
            }
        }

        // Copy the new grid back to the original grid
        System.arraycopy(newGrid, 0, grid, 0, gridWidth * gridHeight);
    }

    /**
     * Applies a mirroring effect to the grid.
     * This method takes a grid and applies a mirroring effect to it, mirroring slashes in the center of the grid.
     * @param grid the grid to apply the mirroring effect to
     * @param direction 0 for horizontal, 1 for vertical, 2 for both
     */
    public static void applyMirroring(float[][] grid, int direction) throws IllegalArgumentException{

        // Validate the direction parameter
        if (direction < 0 || direction > 2) {
            throw new IllegalArgumentException("Invalid direction. Use 0 for horizontal, 1 for vertical, or 2 for both.");
        }
 
        // Get the width and height of the grid
        int width = grid.length;
        int height = grid[0].length;
        float[][] newGrid = new float[width][height];

        // Apply the mirroring effect based on the specified direction
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (direction == 0) { // Horizontal
                    newGrid[x][y] = grid[x][height - 1 - y];
                } else if (direction == 1) { // Vertical
                    newGrid[x][y] = grid[width - 1 - x][y];
                } else if (direction == 2) { // Both
                    newGrid[x][y] = grid[width - 1 - x][height - 1 - y];
                }
            }
        }

        // Copy the new grid back to the original grid
        System.arraycopy(newGrid, 0, grid, 0, width * height);
    }


    /**
     * Applies a smoothing effect to the grid using a circular averaging method.
     * This method takes a grid and a radius as input, and applies a smoothing effect to the grid.
     * @param grid
     * @param radius
     */
    public static float gridGetAvarageCircle(float[][] grid, int x, int y, int radius) {
        int width = grid.length;
        int height = grid[0].length;
        int sum = 0;
        int count = 0;
    
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radius * radius) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        sum += grid[newX][newY];
                        count++;
                    }
                }
            }
        }
    
        return count > 0 ? (float) sum / count : 0; // Prevent division by zero
    }
    
    /**
     * Calculates the average value of a rectangle in the range of the grid.
     * This method takes a grid, x and y coordinates, width, and height as input,
     * @param grid the grid to calculate the average from
     * @param x the x coordinate of the center of the rectangle
     * @param y the y coordinate of the center of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return the average value of the rectangle in the grid
     */
    public static float gridGetAvarageRectangle(float[][] grid, int x, int y, int width, int height) {
        int sum = 0;
        int count = 0;
        
        // Calculate half dimensions for centering
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        
        // Adjust for odd dimensions
        int startX = x - halfWidth;
        int startY = y - halfHeight;
        int endX = x + halfWidth + (width % 2); // Add 1 for odd width
        int endY = y + halfHeight + (height % 2); // Add 1 for odd height
    
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    sum += grid[i][j];
                    count++;
                }
            }
        }
    
        return count > 0 ? (float) sum / count : 0; // Prevent division by zero
    }

}
