package game;

import game.pipes.Pipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class encapsulating the pipe queue.
 */
class PipeQueue {

    /**
     * Maximum number of pipes to display in the queue.
     */
    private static final int MAX_GEN_LENGTH = 5;

    @NotNull
    private final LinkedList<Pipe> pipeQueue;

    /**
     * Creates a pipe queue with {@link PipeQueue#MAX_GEN_LENGTH} number of pipes.
     *
     * <p>
     * This method should also populate the queue until it has {@link PipeQueue#MAX_GEN_LENGTH} number of pipes in it.
     * </p>
     */
    PipeQueue() {
        // TODO - DONE
        this.pipeQueue=new LinkedList<Pipe>();
        int x = 0;
        while(x<MAX_GEN_LENGTH){
            this.pipeQueue.add(generateNewPipe());
            x++;
        }

    }

    /**
     * Creates a pipe queue with pipes already filled in the queue. Ultimately, it should contain
     * {@link PipeQueue#MAX_GEN_LENGTH} number of pipes.
     *
     * <p>
     * This method should also populate the queue until it has {@link PipeQueue#MAX_GEN_LENGTH} number of pipes in it.
     * </p>
     *
     * @param pipes List of pipes to display before generated pipes.
     */
    PipeQueue(@Nullable List<Pipe> pipes) {
        // TODO - DONE
        this.pipeQueue = new LinkedList<Pipe>();
        for (int i = 0; i < pipes.size(); i++) {
            this.pipeQueue.add(pipes.get(i));
        }
        int j = 0;
        while(pipes.size()+j < MAX_GEN_LENGTH){
            this.pipeQueue.add(generateNewPipe());
            j++;
        }
    }

    /**
     * Peeks the next pipe.
     *
     * @return The next pipe in the queue.
     * @throws IllegalStateException if there are no pipes in the queue.
     */
    @NotNull Pipe peek() {
        // TODO  - DONE
        if(pipeQueue.get(0)==null){
            throw new IllegalStateException("The Queue has no more elements to see");
        }
        return pipeQueue.get(0);
    }

    /**
     * Consumes the next pipe.
     *
     * This method removes the pipe from the queue, and generate new ones if the queue has less elements than
     * {@link PipeQueue#MAX_GEN_LENGTH}.
     */
    void consume() {
        // TODO - DONE
        pipeQueue.removeFirst();
        if (pipeQueue.size() < MAX_GEN_LENGTH){
            while(pipeQueue.size() < MAX_GEN_LENGTH){
                pipeQueue.add(generateNewPipe());
            }
        }
    }

    /**
     * Undoes a step by inserting {@code pipe} into the front of the queue.
     *
     * @param pipe Pipe to insert to front of queue.
     */
    void undo(@NotNull final Pipe pipe) {
        // TODO - DONE
        pipeQueue.addFirst(pipe);
    }

    /**
     * Displays the current queue.
     */
    void display() {
        System.out.print("Next Pipes:  ");
        for (var p : pipeQueue) {
            System.out.print(p.toSingleChar() + "    ");
        }
        System.out.println();
    }

    /**
     * Generates a new pipe.
     *
     * <p>
     * Hint: Use {@link java.util.Random#nextInt(int)} to generate random numbers.
     * </p>
     *
     * @return A new pipe.
     */
    @NotNull private static Pipe generateNewPipe() {
        // TODO - DONE
        int x = new Random().nextInt(7);
        switch(x){
            case 0:
                return new Pipe(Pipe.Shape.BOTTOM_LEFT);
            case 1:
                return new Pipe(Pipe.Shape.CROSS);
            case 2:
                return new Pipe(Pipe.Shape.VERTICAL);
            case 3:
                return new Pipe(Pipe.Shape.BOTTOM_RIGHT);
            case 4:
                return new Pipe(Pipe.Shape.TOP_RIGHT);
            case 5:
                return new Pipe(Pipe.Shape.TOP_LEFT);
            case 6:
                return new Pipe(Pipe.Shape.HORIZONTAL);
        }
        return new Pipe(Pipe.Shape.CROSS);
    }
}
