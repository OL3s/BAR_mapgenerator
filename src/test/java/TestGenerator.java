import com.generator.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class TestGenerator {

    private static GridProperties gridProperties;

    @BeforeAll
    public static void setup() {
        gridProperties = new GridProperties(10, 10);
    }

    @Test
    public void testGenerateGrid() {
        float[][] grid = Generator.generateGrid(5, 5);
        
        // Test dimensions
        Assertions.assertEquals(5, grid.length);
        Assertions.assertEquals(5, grid[0].length);
        
        // Test initialization to 0
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Assertions.assertEquals(0, grid[y][x]);
            }
        }
    }
    
    @Test
    public void testGenerateNoise() {
        float[][] grid = Generator.generateGrid(5, 5);
        Generator.generateNoice(grid);
        
        // Test that values are either 0 or 1
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Assertions.assertTrue(grid[y][x] == 0 || grid[y][x] == 1);
            }
        }
    }
    
    @Test
    public void testApplySmoothingCircle() {
        // Create a test grid with alternating 0s and 1s
        float[][] grid = new float[5][5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                grid[x][y] = (x + y) % 2;
            }
        }
        
        // Apply smoothing
        Generator.applySmoothingCircle(grid, 1);
        
        // Each cell should now have a value between 0 and 1
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Assertions.assertTrue(grid[x][y] >= 0 && grid[x][y] <= 1);
            }
        }
    }
    
    @Test
    public void testApplyPeakPoints() {
        float[][] grid = Generator.generateGrid(5, 5);
        // Set all values to 0.4
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                grid[x][y] = 0.4f;
            }
        }
        
        float[] points = {0.5f};
        float range = 0.2f;
        float strength = 0.5f;
        
        Generator.applyPeakPoints(grid, points, range, strength);
        
        // Values should be pulled toward 0.5
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Assertions.assertTrue(grid[x][y] > 0.4f && grid[x][y] < 0.5f);
            }
        }
    }
    
    @Test
    public void testApplyPeakPointsInvalidStrength() {
        float[][] grid = Generator.generateGrid(5, 5);
        float[] points = {0.5f};
        float range = 0.2f;
        
        // Test that an exception is thrown when strength is out of bounds
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Generator.applyPeakPoints(grid, points, range, 1.5f);
        });
    }
    
    @Test
    public void testApplyMirroring() {
        float[][] grid = new float[3][3];
        // Set up grid with unique values - using [y][x] consistently
        grid[0][0] = 0.1f; grid[0][1] = 0.2f; grid[0][2] = 0.3f;
        grid[1][0] = 0.4f; grid[1][1] = 0.5f; grid[1][2] = 0.6f;
        grid[2][0] = 0.7f; grid[2][1] = 0.8f; grid[2][2] = 0.9f;
        
        // Create a copy for comparison
        float[][] original = new float[3][3];
        for (int y = 0; y < 3; y++) {
            System.arraycopy(grid[y], 0, original[y], 0, 3);
        }
        
        // Test horizontal mirroring
        Generator.applyMirroring(grid, 0);
        // After horizontal mirroring, top becomes bottom: [0][x] should equal [2][x]
        Assertions.assertEquals(original[2][0], grid[0][0]);
        Assertions.assertEquals(original[2][1], grid[0][1]);
        Assertions.assertEquals(original[2][2], grid[0][2]);
        
        // Reset grid
        for (int y = 0; y < 3; y++) {
            System.arraycopy(original[y], 0, grid[y], 0, 3);
        }
        
        // Test vertical mirroring
        Generator.applyMirroring(grid, 1);
        // After vertical mirroring, left becomes right: [y][0] should equal [y][2]
        Assertions.assertEquals(original[0][2], grid[0][0]);
        Assertions.assertEquals(original[1][2], grid[1][0]);
        Assertions.assertEquals(original[2][2], grid[2][0]);
    }
    
    @Test
    public void testGridGetAverageCircle() {
        float[][] grid = new float[5][5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                grid[x][y] = 1.0f;
            }
        }
        
        float average = Generator.gridGetAvarageCircle(grid, 2, 2, 1);
        Assertions.assertEquals(1.0f, average);
        
        // Change one value and test again
        grid[2][2] = 0.0f;
        average = Generator.gridGetAvarageCircle(grid, 2, 2, 1);
        Assertions.assertTrue(average < 1.0f && average >= 0.0f);
    }
}