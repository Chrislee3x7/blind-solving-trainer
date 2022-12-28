
/* This class runs the logic to the training game
    Upon instantiating by Jpanel, should randomly pick a
    sticker, get the piece that corresponds to that sticker.
    Send the piece information to the Training Panel to display
    Wait for training panel to return information of user's response
    Depending on the response of the use and the sticker that was
    randomly chosen, add a point.
    Keeps track of #correct and num attempted
    Depending on the response of the answer provided by the user,
    return if they got it correct or wrong

    Extra: store information locally, keep track of average and stuff etc
    Give options to do edge memos only or corner memos only or both
*/
public class Trainer {

    private int mode;

    private TrainingPanel tp;

    private Cube cube;

    private Sticker answerSticker;

    private double recogStartTime;

    private double recogTimeElapsed;

    private boolean isPaused = true;



    // mode that is selected by the user
    // 0: corners and edges
    // 1: corners only
    // 2: edges only
    // more modes to come
    public Trainer(int mode, Cube cube, TrainingPanel tp) {
        if (mode < 0 || mode > 2) {
            new Exception("Invalid training mode inputted").printStackTrace();
        }
        this.mode = mode;
        this.cube = cube;
        this.tp = tp;

        startPiece();
    }

    public void startPiece() {
        if (!isPaused) {
            return;
        }
        //System.out.println("Next quiz:");

        // choose a random piece, orientation, and sticker and store them
        CubePiece currPiece = getRandomPiece();
        int answerStickerIndex = chooseRandomStickerIndex(currPiece);
        answerSticker =  currPiece.getPieceStickers()[answerStickerIndex];
        int orientation = chooseRandomStickerIndex(currPiece);

        // display the piece with orientation, and correctStickerIndex
        tp.setPieceToBeDisplayed(answerStickerIndex, orientation, currPiece);
        isPaused = false; // start allowing answer checks

        // start stopwatch
        recogStartTime = System.currentTimeMillis();

    }


    public boolean checkAnswer(char answer) {
        if (isPaused) {
            // should never be here
            return false;
        }
        recogTimeElapsed = System.currentTimeMillis() - recogStartTime;
        isPaused = true; // do not allow answer checks
        System.out.println(recogTimeElapsed/1000 + " seconds");
        System.out.println("Correct answer : " + answerSticker.getMemo() + " " + answerSticker.getDisplayColor());
        System.out.println("press any key to continue");
        return answer == answerSticker.getMemo();
    }

    public CubePiece getRandomPiece() {
        // should practice edges and corners equally, total same num of stickers
        // so should use 50% chance
        int random = (int) (Math.random() * 2);
        random = 0; // for testing how corner pieces look
        if (random == 0) {
            random = (int) (Math.random() * 8);
            return cube.getCornerPieces().get(random);
        } else {
            random = (int) (Math.random() * 12);
            return cube.getEdgePieces().get(random);
        }
    }


    // Used for 2 functions, orients the piece and also picks the sticker to guess
    // return corresponds
    private int chooseRandomStickerIndex(CubePiece cubePiece) {
        for (int i = 0; i < cubePiece.getPieceStickers().length; i++) {
            System.out.println(cubePiece.getPieceStickers()[i] + "---");
        }
        if (cubePiece.getPieceType() == PieceType.CORNER) {
            return (int) (Math.random() * 3);
        } else if (cubePiece.getPieceType() == PieceType.EDGE) {
            return (int) (Math.random() * 2);
        } else {
            new Exception("Center is not a valid cubePiece to choose random sticker from").printStackTrace();
            return -1;
        }
    }

    public boolean isPaused() {
        return isPaused;
    }
}
