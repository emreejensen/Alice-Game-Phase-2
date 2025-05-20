package model;
public class Puzzle {
    private String puzzleID;
    private String description;
    private String solution;

    public Puzzle(String puzzleID, String description, String solution) {
        this.puzzleID = puzzleID;
        this.description = description;
        this.solution = solution;
    }

    // Getters and setters (if needed)
    public String getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(String puzzleID) {
        this.puzzleID = puzzleID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}