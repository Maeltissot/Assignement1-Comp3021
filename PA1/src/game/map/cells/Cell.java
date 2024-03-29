package game.map.cells;

import game.MapElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Coordinate;
import util.Direction;

/**
 * Representation of a cell in the {@link game.map.Map}.
 */
public abstract class Cell implements MapElement {

    @NotNull
    public final Coordinate coord;

    /**
     * @param coord coordination of {@code this} cell
     */
    Cell(@NotNull Coordinate coord) {
        this.coord = coord;
    }

    /**
     * Parses a {@link Cell} from a character.
     *
     * <p>
     * Refer to README for the list of characters to their corresponding map element. If the character does not
     * represent a {@link TerminationCell}, the {@code terminationType} parameter can be ignored.
     * </p>
     *
     * @param c Character to parse. For example, 'W' refers to a wall.
     * @param coord Coordinate of the newly created cell.
     * @param terminationType If the character is a termination cell, its type. Otherwise, this argument is ignored and
     *                        can be null.
     * @return A cell based on the given creation parameters, or null if the parameters cannot form a valid Cell.
     */
    public static @Nullable Cell fromChar(char c, @NotNull Coordinate coord, @Nullable TerminationCell.Type terminationType) {
        // DONE
        Cell cell = null;
        if(terminationType != null){
            switch(c){
                case '^':
                    cell = new TerminationCell(coord,Direction.UP,terminationType);
                    break;
                case'v':
                    cell = new TerminationCell(coord,Direction.DOWN,terminationType);
                    break;
                case '<':
                    cell = new TerminationCell(coord,Direction.LEFT,terminationType);
                    break;
                case '>':
                    cell = new TerminationCell(coord,Direction.RIGHT,terminationType);
                    break;
                case 'W':
                    cell = new Wall(coord);
                    break;
                case'.':
                    cell = new FillableCell(coord);
                    break;
            }
        } else {
            switch (c){
                case '.':
                    cell = new FillableCell(coord);
                    break;
            }
        }

        return cell;
    }
}
