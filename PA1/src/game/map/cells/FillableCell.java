package game.map.cells;

import game.MapElement;
import game.pipes.Pipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Coordinate;

import java.util.Optional;

/**
 * Represents a {@link Cell} which can contain a pipe.
 */
public class FillableCell extends Cell implements MapElement {

    @Nullable
    private final Pipe pipe;

    /**
     * Constructs a {@link FillableCell}.
     *
     * @param coord Coordinate where the cell resides in the map.
     */
    public FillableCell(@NotNull Coordinate coord) {
        // TODO - DONE
        super(coord);
        pipe = null;
    }

    /**
     * Constructs a {@link FillableCell} with a pipe already contained in it.
     *
     * @param coord Coordinate where the cell resides in the map.
     * @param pipe The pipe inside this cell.
     */
    public FillableCell(@NotNull Coordinate coord, @Nullable Pipe pipe) {
        // TODO - DONE
        super(coord);
        this.pipe = pipe;
    }

    /**
     * @return An {@link java.util.Optional} representing the pipe in this tile.
     */
    public @NotNull Optional<Pipe> getPipe() {
        // DONE
        return Optional.ofNullable(this.pipe);
    }

    /**
     * @return The character representation of the pipe, or {@code '.'} if the cell is empty.
     */
    @Override
    public char toSingleChar() {
        // DONE
        if (pipe != null) return pipe.toSingleChar();
        return '.';
    }
}