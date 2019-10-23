package game.map;

import com.sun.source.tree.WhileLoopTree;
import game.map.cells.Cell;
import game.map.cells.FillableCell;
import game.map.cells.TerminationCell;
import game.map.cells.Wall;
import game.pipes.Pipe;
import io.Deserializer;
import org.jetbrains.annotations.NotNull;
import util.Coordinate;
import util.Direction;
import util.PipePatterns;
import util.StringUtils;

import java.util.*;

/**
 * Map of the game.
 */
public class Map {

    private final int rows;
    private final int cols;
    @NotNull
    final Cell[][] cells;
    private boolean filledTurn = true;
    private TerminationCell sourceCell;
    private TerminationCell sinkCell;


    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    @NotNull
    private final Set<Coordinate> filledTiles = new HashSet<>();
    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    private int prevFilledTiles = 0;
    /**
     * You can use this variable to implement the {@link Map#fillTiles} method, but it is not necessary
     */
    private Integer prevFilledDistance;

    /**
     * Creates a map with size of rows x cols.
     *
     * <p>
     * You should make sure that the map generated adheres to the specifications as stated in the README.
     * </p>
     *
     * @param rows Number of rows.
     * @param cols Number of columns.
     */
    public Map(int rows, int cols) {
        // TODO - DONE
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        //create walls
        for (int i = 0; i < rows; i++) {
            Coordinate wallCoordUp = new Coordinate(0, i);
            Coordinate wallCoordBot = new Coordinate(rows - 1, i);
            cells[0][i] = new Wall(wallCoordUp);
            cells[rows - 1][i] = new Wall(wallCoordBot);
        }
        for (int j = 0; j < cols; j++) {
            Coordinate wallCoordLeft = new Coordinate(j, 0);
            Coordinate wallCoordRight = new Coordinate(j, cols - 1);
            cells[j][0] = new Wall(wallCoordLeft);
            cells[j][cols - 1] = new Wall(wallCoordRight);
        }
        //fill with fillableCells
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                Coordinate coordFill = new Coordinate(i, j);
                cells[i][j] = new FillableCell(coordFill);
            }
        }
        //Randomly generate the sinkCell
        Random rands = new Random();
        Random randSink = new Random();
        Coordinate coordSink = null;
        switch (rands.nextInt((4 - 1) + 1) + 1) {
            case 1:
                int upx = randSink.nextInt((cols - 2 - 1) + 1) + 1;
                coordSink = new Coordinate(0, upx);
                cells[0][upx] = new TerminationCell(coordSink, Direction.UP, TerminationCell.Type.SINK);
                this.sinkCell = (TerminationCell) cells[0][upx];
                break;
            case 2:
                int downx = randSink.nextInt((cols - 2 - 1) + 1) + 1;
                coordSink = new Coordinate(cols - 1, downx);
                cells[rows - 1][downx] = new TerminationCell(coordSink, Direction.DOWN, TerminationCell.Type.SINK);
                this.sinkCell = (TerminationCell) cells[rows - 1][downx];
                break;
            case 3:
                int leftx = randSink.nextInt((rows - 2 - 1) + 1) + 1;
                coordSink = new Coordinate(leftx, 0);
                cells[leftx][0] = new TerminationCell(coordSink, Direction.LEFT, TerminationCell.Type.SINK);
                this.sinkCell = (TerminationCell) cells[leftx][0];
                break;
            case 4:
                int rightx = randSink.nextInt((rows - 2 - 1) + 1) + 1;
                coordSink = new Coordinate(rightx, cols - 1);
                cells[rightx][cols - 1] = new TerminationCell(coordSink, Direction.RIGHT, TerminationCell.Type.SINK);
                this.sinkCell = (TerminationCell) cells[rightx][cols - 1];
                break;
        }
        //randomly generate the SourceCell
        Random randsc = new Random();
        Random randSourceCol = new Random();
        Random randSourceRow = new Random();
        int randRow = randSourceRow.nextInt((rows - 2 - 1) + 1) + 1;
        int randCol = randSourceCol.nextInt((cols - 2 - 1) + 1) + 1;
        if(randCol==sinkCell.coord.col+sinkCell.pointingTo.getOpposite().getOffset().col && randRow == sinkCell.coord.row + sinkCell.pointingTo.getOpposite().getOffset().row){
            randCol += randCol + sinkCell.pointingTo.getOpposite().getOffset().col;
            randRow += randRow + sinkCell.pointingTo.getOpposite().getOffset().row;
        }
        Direction dirScource = null;
        Coordinate coordSc = new Coordinate(randRow, randCol);
        if (randRow == rows - 2) { dirScource = Direction.UP; }
        else if (randRow == 1) { dirScource = Direction.DOWN; }
        else if (randCol == 1) { dirScource = Direction.RIGHT; }
        else if (randCol == cols-2) { dirScource = Direction.LEFT;}
        else{
            switch (randSourceRow.nextInt((4 - 1) + 1) + 1){
                case 1:
                    dirScource = Direction.UP;
                    break;
                case 2:
                    dirScource = Direction.LEFT;
                    break;
                case 3:
                    dirScource = Direction.RIGHT;
                    break;
                case 4:
                    dirScource = Direction.DOWN;
                    break;

            }
        }
        cells[randRow][randCol] = new TerminationCell(coordSc,dirScource, TerminationCell.Type.SOURCE);
        this.sourceCell = new TerminationCell(coordSc,dirScource, TerminationCell.Type.SOURCE);
    }
    /**
     * Creates a map with the given cells.
     *
     * <p>
     * You should make sure that the map generated adheres to the specifications as stated in the README.
     * </p>
     *
     * @param rows  Number of rows.
     * @param cols  Number of columns.
     * @param cells Cells to fill the map.
     */
    public Map(int rows, int cols, @NotNull Cell[][] cells) {
        // TODO - DONE
        this.rows = rows;
        this.cols = cols;
        this.cells = cells;

        for(int i =0;i<cells.length;i++){
            for(int j=0;j<cells[0].length;j++){
                if(cells[i][j] instanceof TerminationCell){
                    TerminationCell termcell = (TerminationCell) cells[i][j];
                    if(termcell.type == TerminationCell.Type.SOURCE){
                        this.sourceCell=termcell;
                    }
                    else if(termcell.type== TerminationCell.Type.SINK){
                        this.sinkCell=termcell;
                    }
                }
            }
        }
    }

    /**
     * Constructs a map from a map string.
     * <p>
     * This is a convenience method for unit testing.
     * </p>
     *
     * @param rows     Number of rows.
     * @param cols     Number of columns.
     * @param cellsRep String representation of the map, with columns delimited by {@code '\n'}.
     * @return A map with the cells set from {@code cellsRep}.
     * @throws IllegalArgumentException If the map is incorrectly formatted.
     */
    @NotNull
    static Map fromString(int rows, int cols, @NotNull String cellsRep) {
        var cells = Deserializer.parseString(rows, cols, cellsRep);

        return new Map(rows, cols, cells);
    }

    /**
     * Tries to place a pipe at (row, col).
     *
     * @param coord Coordinate to place pipe at.
     * @param pipe  Pipe to place in cell.
     * @return {@code true} if the pipe is placed in the cell, {@code false} otherwise.
     */
    public boolean tryPlacePipe(@NotNull final Coordinate coord, @NotNull final Pipe pipe) {
        return tryPlacePipe(coord.row, coord.col, pipe);
    }

    /**
     * Tries to place a pipe at (row, col).
     *
     * <p>
     * Note: You cannot overwrite the pipe of a cell once the cell is occupied.
     * </p>
     * <p>
     * Hint: Remember to check whether the map coordinates are within bounds, and whether the target cell is a 
     * {@link FillableCell}.
     * </p>
     *
     * @param row One-Based row number to place pipe at.
     * @param col One-Based column number to place pipe at.
     * @param p   Pipe to place in cell.
     * @return {@code true} if the pipe is placed in the cell, {@code false} otherwise.
     */
    boolean tryPlacePipe(int row, int col, @NotNull Pipe p) {
        // TODO -  DONE
        if(row>cells.length || col>cells[0].length) {
            return false;
        }
        if(cells[row][col] instanceof FillableCell){
            FillableCell fillableCell = (FillableCell)this.cells[row][col];
                Coordinate coord = new Coordinate(row,col);
                this.cells[row][col] = new FillableCell(coord,p);
                return true;

        }
        return false;
    }

    /**
     * Displays the current map.
     */
    public void display() {
        final int padLength = Integer.valueOf(rows - 1).toString().length();

        Runnable printColumns = () -> {
            System.out.print(StringUtils.createPadding(padLength, ' '));
            System.out.print(' ');
            for (int i = 0; i < cols - 2; ++i) {
                System.out.print((char) ('A' + i));
            }
            System.out.println();
        };

        printColumns.run();

        for (int i = 0; i < rows; ++i) {
            if (i != 0 && i != rows - 1) {
                System.out.print(String.format("%1$" + padLength + "s", i));
            } else {
                System.out.print(StringUtils.createPadding(padLength, ' '));
            }

            Arrays.stream(cells[i]).forEachOrdered(elem -> System.out.print(elem.toSingleChar()));

            if (i != 0 && i != rows - 1) {
                System.out.print(i);
            }

            System.out.println();
        }

        printColumns.run();
    }

    /**
     * Undoes a step from the map.
     *
     * <p>
     * Effectively replaces the cell with an empty cell in the coordinate specified.
     * </p>
     *
     * @param coord Coordinate to reset.
     * @throws IllegalArgumentException if the cell is not an instance of {@link FillableCell}.
     */
    public void undo(@NotNull final Coordinate coord) {
        // TODO - DONE
        if(cells[coord.row][coord.col] instanceof FillableCell){
            this.cells[coord.row][coord.col] = new FillableCell(coord);
        } else{
            throw new IllegalArgumentException("This is not a valid cell");
        }
    }

    /**
     * Fills the source cell.
     */
    public void fillBeginTile() {
        sourceCell.setFilled();
    }

    /**
     * Fills all pipes that are within {@code distance} units from the {@code sourceCell}.
     * 
     * <p>
     * Hint: There are two ways to approach this. You can either iteratively fill the tiles by distance (i.e. filling 
     * distance=0, distance=1, etc), or you can save the tiles you have already filled, and fill all adjacent cells of 
     * the already-filled tiles. Whichever method you choose is up to you, as long as the result is the same.
     * </p>
     *
     * @param distance Distance to fill pipes.
     */
    public void fillTiles(int distance) {
        // TODO - DONE
            Cell cell =sourceCell;
        System.out.println("fillTiles");
            FillableCell firstFillCell = (FillableCell)cells[sourceCell.pointingTo.getOffset().row+sourceCell.coord.row][sourceCell.pointingTo.getOffset().col+sourceCell.coord.col];
            filledTurn=false;
    if(firstFillCell.getPipe().isPresent()) {
        for (Direction dir : firstFillCell.getPipe().orElseThrow().getConnections()) {
            if (dir.equals(sourceCell.pointingTo.getOpposite())) {
                if (firstFillCell.getPipe().orElseThrow().getFilled() == false) {
                    firstFillCell.getPipe().orElseThrow().setFilled();
                    filledTurn = true;
                } else {
                    filledTurn = false;
                }
                recursiveFill(distance, 0, sourceCell.pointingTo.getOpposite(), firstFillCell);
            }
        }
    }
        System.out.println("fillTilesEnd");
        }

    public void recursiveFill(int distance, int distanceCounter, Direction inDir, Cell cell){
        distanceCounter++;
        System.out.println("recursive distance:" + distance);
        if(distance>=distanceCounter){
            if(cell instanceof FillableCell ){
                FillableCell fillCell = (FillableCell)cells[cell.coord.row][cell.coord.col];
                fillCell.getPipe().orElseThrow().setFilled();
                Direction[] directionsC = fillCell.getPipe().orElseThrow().getConnections();
                for (Direction dir:directionsC) {
                    if(cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col] instanceof TerminationCell){
                        TerminationCell fillCellnext = (TerminationCell) cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col];
                        if (fillCellnext.pointingTo.equals(dir)){
                            fillCellnext.setFilled();
                            filledTurn = true;
                        }
                    }
                    else if(cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col] instanceof FillableCell) {
                        FillableCell fillCellnext = (FillableCell) cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col];
                        if(fillCellnext.getPipe().isPresent()){
                               if (!fillCellnext.getPipe().orElseThrow().getFilled()) {
                                   for (Direction dirNext : fillCellnext.getPipe().orElseThrow().getConnections()) {
                                        if (dirNext.equals(dir.getOpposite()) && !inDir.equals(dir.getOpposite())) {
                                            System.out.println("recursive distance:filled 1 cell");
                                            filledTurn = true;
                                            recursiveFill(distance, distanceCounter, dirNext.getOpposite(), fillCellnext);
                                       }
                                    }
                              }

                            else if (fillCellnext.getPipe().orElseThrow().getFilled()) {
                                    for (Direction dirNext : fillCellnext.getPipe().orElseThrow().getConnections()) {
                                        if (dirNext.equals(dir.getOpposite()) && !inDir.equals(dir.getOpposite())) {
                                            filledTurn = false;
                                            recursiveFill(distance, distanceCounter, dirNext.getOpposite(), fillCellnext);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    /**
     * Checks whether there exists a path from {@code sourceCell} to {@code sinkCell}.
     * 
     * <p>
     * Hint: This problem is similar to finding a specific node in a graph. As stated in the README, one of the ways you
     * could approach this is to use Breadth-First Search.
     * </p>
     *
     * @return {@code true} if a path exists, else {@code false}.
     */
    public boolean checkPath() {
        // TODO - DONE
        Cell cell =sourceCell;
        System.out.println("CheckPath");
        FillableCell firstFillCell = (FillableCell)cells[sourceCell.pointingTo.getOffset().row+sourceCell.coord.row][sourceCell.pointingTo.getOffset().col+sourceCell.coord.col];
        ArrayList<Cell> visitedCells = new ArrayList<Cell>();
        ArrayList<Cell> nextCells = new ArrayList<Cell>();
        if(firstFillCell.toSingleChar()!='.'){
            System.out.println("CheckPath found first cell");
            nextCells.add(firstFillCell);
            visitedCells.add(sourceCell);
        }
        while (!nextCells.isEmpty()){

                FillableCell fillCell = (FillableCell)cells[nextCells.get(0).coord.row][nextCells.get(0).coord.col];
                nextCells.remove(fillCell);
                Direction[] directionsC = fillCell.getPipe().orElseThrow().getConnections();

                for (Direction dir:directionsC) {

                        if (cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col] instanceof TerminationCell) {
                            TerminationCell fillCellnext = (TerminationCell) cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col];
                            if (!visitedCells.contains(fillCellnext) && !nextCells.contains(fillCellnext) && dir == fillCellnext.pointingTo && fillCellnext.type!= TerminationCell.Type.SOURCE) {
                                System.out.println("CheckPath found termcellout");
                                nextCells.add(sinkCell);
                                return true;
                            }
                        } else if (cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col] instanceof FillableCell) {
                            FillableCell fillCellnext = (FillableCell) cells[fillCell.coord.row + dir.getOffset().row][fillCell.coord.col + dir.getOffset().col];
                            if (fillCellnext.getPipe().isPresent()) {
                                System.out.println("CheckPath found fillableCell" + fillCellnext.coord.row +fillCellnext.coord.col);
                                if (!visitedCells.contains(fillCellnext) && !nextCells.contains(fillCellnext) && fillCellnext.getPipe().orElseThrow().toSingleChar() != '.') {
                                    for (Direction dirNext : fillCellnext.getPipe().orElseThrow().getConnections()) {
                                        if (dirNext.equals(dir.getOpposite())) {
                                            nextCells.add(fillCellnext);
                                        }
                                    }
                                }
                            }
                        }
                    }

                visitedCells.add(fillCell);
            }
        System.out.println("CheckPathend");
        if(visitedCells.contains(sinkCell)) return true;
        return false;
    }

    /**
     * <p>
     * Hint: From the README: {@code The game is lost when a round ends and no pipes are filled during the round.} Is
     * there a way to check whether pipes are filled during a round?
     * </p>
     *
     * @return {@code true} if the game is lost.
     */
    public boolean hasLost() {
        // TODO - DONE
        if(filledTurn==true){System.out.println("I'm called");
        return false;}
        return true;
    }
}
