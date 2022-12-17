
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

    private double startTime;

    // mode that is selected by the user
    // 0: corners and edges
    // 1: corners only
    // 2: edges only
    // more modes to come
    public Trainer(int mode) {
        if (mode < 0 || mode > 2) {
            new Exception("Invalid training mode inputted").printStackTrace();
        }
        this.mode = mode;

        // create all pieces based on the memo scheme

    }

    private void startTraining() {

    }

    public void getRandomPiece() {

    }

//    private Sticker chooseRandomSticker() {
//
//    }

}
