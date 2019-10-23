package game.pipes;

import game.MapElement;
import org.jetbrains.annotations.NotNull;
import util.Coordinate;
import util.Direction;
import util.PipePatterns;

import java.util.ArrayList;

public class Pipe implements MapElement {

    @NotNull
    private final Shape shape;
    private boolean filled = false;

    /**
     * Creates a new pipe with a given shape.
     *
     * @param shape Shape of the pipe.
     */
    public Pipe(@NotNull Shape shape) {
        // TODO - DONE
        this.shape=shape;
    }

    /**
     * Sets the pipe as filled.
     */
    public void setFilled() {
        // TODO - DONE
        this.filled=true;
    }

    /**
     * @return Whether this pipe is filled.
     */
    public boolean getFilled() {
        // TODO - DONE
        return filled;
    }

    /**
     * @return List of connections for this pipe.
     * @throws IllegalStateException if {@code this} pipe cannot be identified.
     */
    public @NotNull Direction[] getConnections() {
        // TODO - DONE
        Direction[] connections;
        switch (shape.getCharByState(true)){
            case PipePatterns.Filled.BOTTOM_LEFT:
                connections= new Direction[2];
                connections[0] = Direction.DOWN;
                connections[1] = Direction.LEFT;
                break;
            case PipePatterns.Filled.BOTTOM_RIGHT:
                connections= new Direction[2];
                connections[0] = Direction.RIGHT;
                connections[1] = Direction.DOWN;
                break;
            case PipePatterns.Filled.CROSS:
                connections= new Direction[4];
                connections[0] = Direction.RIGHT;
                connections[1] = Direction.DOWN;
                connections[2] = Direction.LEFT;
                connections[3] = Direction.UP;
                break;
            case PipePatterns.Filled.HORIZONTAL:
                connections= new Direction[2];
                connections[0] = Direction.LEFT;
                connections[1] = Direction.RIGHT;
                break;
            case PipePatterns.Filled.TOP_LEFT:
                connections= new Direction[2];
                connections[0] = Direction.LEFT;
                connections[1] = Direction.UP;
                break;
            case PipePatterns.Filled.TOP_RIGHT:
                connections= new Direction[2];
                connections[0] = Direction.RIGHT;
                connections[1] = Direction.UP;
                break;
            case PipePatterns.Filled.VERTICAL:
                connections= new Direction[2];
                connections[0] = Direction.UP;
                connections[1] = Direction.DOWN;
                break;
            default:
                throw new IllegalStateException("Unkown Pipe");

        }
        return connections;
    }

    /**
     * @return The character representation of this pipe. Note that the representation is different for filled and
     * unfilled pipes.
     */
    @Override
    public char toSingleChar() {
        // TODO - DONE
        if(filled){
            return shape.filledChar;
        } else {
            return shape.unfilledChar;
        }
    }

    /**
     * Converts a String to a Pipe.
     *
     * <p>
     * Refer to README for the list of ASCII representation to the pipes.
     * </p>
     *
     * @param rep String representation of the pipe. For example, "HZ" corresponds to a pipe of horizontal shape.
     * @return Pipe identified by the string.
     * @throws IllegalArgumentException if the String does not represent a known pipe.
     */
    public static @NotNull Pipe fromString(@NotNull String rep) {
        // TODO - DONE
        Pipe returnedPipe;
        switch (rep){
            case "HZ":
                return returnedPipe= new Pipe(Shape.HORIZONTAL);
            case "TL":
                return returnedPipe= new Pipe(Shape.TOP_LEFT);
            case "TR":
                return returnedPipe= new Pipe(Shape.TOP_RIGHT);
            case "BL":
                return returnedPipe= new Pipe(Shape.BOTTOM_LEFT);
            case "BR":
                return returnedPipe= new Pipe(Shape.BOTTOM_RIGHT);
            case "VT":
                return returnedPipe= new Pipe(Shape.VERTICAL);
            case "CR":
                return returnedPipe= new Pipe(Shape.CROSS);
            default:
                throw new IllegalStateException("Unknown direction");
        }
    }

    public enum Shape {
        HORIZONTAL(PipePatterns.Filled.HORIZONTAL, PipePatterns.Unfilled.HORIZONTAL),
        VERTICAL(PipePatterns.Filled.VERTICAL, PipePatterns.Unfilled.VERTICAL),
        TOP_LEFT(PipePatterns.Filled.TOP_LEFT, PipePatterns.Unfilled.TOP_LEFT),
        TOP_RIGHT(PipePatterns.Filled.TOP_RIGHT, PipePatterns.Unfilled.TOP_RIGHT),
        BOTTOM_LEFT(PipePatterns.Filled.BOTTOM_LEFT, PipePatterns.Unfilled.BOTTOM_LEFT),
        BOTTOM_RIGHT(PipePatterns.Filled.BOTTOM_RIGHT, PipePatterns.Unfilled.BOTTOM_RIGHT),
        CROSS(PipePatterns.Filled.CROSS, PipePatterns.Unfilled.CROSS);

        final char filledChar;
        final char unfilledChar;

        /**
         *
         * @param filled Representation of {@code this} shape when filled
         * @param unfilled Representation of {@code this} shape when unfilled
         */
        Shape(char filled, char unfilled) {
            this.filledChar = filled;
            this.unfilledChar = unfilled;
        }

        /**
         *
         * @param isFilled The filling state of {@code this} shape
         * @return The character representation of {@code this} shape according to the filling state
         */
        char getCharByState(boolean isFilled) {
            // TODO - DONE
            if(isFilled == true){
                return filledChar;
            } else {
                return unfilledChar;
            }
        }
    }
}
