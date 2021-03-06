import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CubeNetPanel extends JPanel implements MouseListener, KeyListener {

    // array of cube faces that correspond to the six face of the cube
    // indexed 0 - 5: [0: U, 1: L, 2: F, 3: R, 4: B, 5: D]
    private CubeFace[] cubeFaces;
    // indexed 0 - 5: [0: White, 1: Red, 2: Green, 3: Orange, 4: Blue, 5: Yellow
    public static final Color[] defaultColorScheme = new Color[]
            {Color.WHITE, // white
                    new Color(235, 41, 2), // red
                    new Color(9, 207, 2), // green
                    new Color(255, 135, 8), // orange
                    new Color(8, 119, 255), // blue
                    Color.YELLOW}; // yellow
    private Dimension panelDimension;
    private int singleFaceDimension;
    private int singleStickerDimension;
    private int stickerBorderThickness;

    // for mouseListener
    private Sticker pressedSticker;

    private StickerType memoEditMode;
    private boolean editingMemo = false;

    public static final char[][] DEFAULT_MEMOS = new char[][]{
            {'A', 'B', 'C', 'D'},
            {'E', 'F', 'G', 'H'},
            {'I', 'J', 'K', 'L'},
            {'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T'},
            {'U', 'V', 'W', 'X'}};

    /**
     * Instantiates a CubeNetPanel object with default CubeFace colors
     *
     */
    public CubeNetPanel() {
        super();
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                turnOffEditMode(pressedSticker);
            }
        });
        cubeFaces = new CubeFace[6];
        for (int i = 0; i < 6; i++) {
            cubeFaces[i] = new CubeFace(defaultColorScheme[i]);
        }
        setMemoSchemeToSaved();
        setMemoEditMode(StickerType.CORNER);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setOpaque(true);
                setFocusable(true);
                requestFocusInWindow();
            }
        });
    }

    public void setMemoSchemeToDefault() {
        if (memoEditMode == StickerType.CORNER) {
            setCornerMemoSchemeToDefault();
        } else if (memoEditMode == StickerType.EDGE) {
            setEdgeMemoSchemeToDefault();
        }
    }

    public void setEdgeMemoSchemeToDefault() {
        for (int i = 0; i < 6; i++) {
            cubeFaces[i].setEdgeMemosToDefault(i);
        }
        saveMemoScheme();
        setMemoSchemeToSaved();
    }

    public void setCornerMemoSchemeToDefault() {
        for (int i = 0; i < 6; i++) {
            cubeFaces[i].setCornerMemosToDefault(i);
        }
        saveMemoScheme();
        setMemoSchemeToSaved();
    }

    public void saveMemoScheme() {
        File f = new File("src/Memos");
        PrintWriter pw;
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        for (CubeFace face : cubeFaces) {
            Sticker[] cornerStickers = face.getCornerStickers();
            Sticker[] edgeStickers = face.getEdgeStickers();
            for (int i = 0; i < cornerStickers.length; i++) {
                pw.print(cornerStickers[i].getMemo());
                if (cornerStickers[i].getConflicted()) {
                    pw.print("1");
                } else {
                    pw.print("0");
                }
                pw.print(edgeStickers[i].getMemo());
                if (edgeStickers[i].getConflicted()) {
                    pw.print("1");
                } else {
                    pw.print("0");
                }
            }
            pw.println();
        }
        pw.close();
    }

    public void setMemoSchemeToSaved() {
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader("src/Memos"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (line == null) {
            return;
        }
        for (CubeFace face : cubeFaces) {
            for (int i = 0; i < 4; i++) {
                face.setCornerMemo(i, line.charAt(i * 4));
                if (line.charAt(i * 4 + 1) == '1') {
                    face.getCornerStickers()[i].setConflicted(true);
                } else {
                    face.getCornerStickers()[i].setConflicted(false);
                }
                face.setEdgeMemo(i, line.charAt(i * 4 + 2));
                if (line.charAt(i * 4 + 3) == '1') {
                    face.getEdgeStickers()[i].setConflicted(true);
                } else {
                    face.getEdgeStickers()[i].setConflicted(false);
                }
            }
            try {
                line = bfr.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePanelDimension() {
        panelDimension = getSize();
        singleFaceDimension = panelDimension.width / 5;
        if (panelDimension.height / 3.5 < singleFaceDimension) {
            singleFaceDimension = (int) (panelDimension.height / 3.5);
        }
        singleStickerDimension = singleFaceDimension / 3;
        stickerBorderThickness = singleFaceDimension / 30;
    }

    public void setFaceColor(int faceIndex, Color color) {
        cubeFaces[faceIndex].setColor(color);
    }

    public void setMemoEditMode(StickerType memoEditMode) {
        this.memoEditMode = memoEditMode;
        for (CubeFace face : cubeFaces) {
            face.setMemoEditMode(memoEditMode);
        }
    }

    public ArrayList<Sticker> findStickerConflicts(Sticker possibleConflict) {
        ArrayList<Sticker> conflicts = new ArrayList<>();
        StickerType st = possibleConflict.getStickerType();
        char conflictMemo = possibleConflict.getMemo();
        if (st == StickerType.CORNER) {
            for (CubeFace face : cubeFaces) {
                for (Sticker s : face.getCornerStickers()) {
                    if (s.getMemo() == conflictMemo) {
                        conflicts.add(s);
                    }
                }
            }
        } else if (st == StickerType.EDGE) {
            for (CubeFace face : cubeFaces) {
                for (Sticker s : face.getEdgeStickers()) {
                    if (s.getMemo() == conflictMemo) {
                        conflicts.add(s);
                    }
                }
            }
        }
        return conflicts;
    }

    // Painting the JPanel methods
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updatePanelDimension();
        setBackground(new Color(175, 208, 191));
        paintCubeNet(g);
    }

    private void paintCubeNet(Graphics g) {
        int centerVertTopLeftCornerX = (panelDimension.width / 2) - (singleFaceDimension * 2); // centered for face 1
        int centerVertTopLeftCornerY = (panelDimension.height / 2) - (singleFaceDimension / 2); // centered for face 1
        int nextCubeFaceDistance = singleFaceDimension;
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        //hacky extra paintFace needed to fix the weird paint issue
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        paintFace(g, centerVertTopLeftCornerX, centerVertTopLeftCornerY, 1);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY, 2);
        paintFace(g, centerVertTopLeftCornerX + 2 * nextCubeFaceDistance, centerVertTopLeftCornerY, 3);
        paintFace(g, centerVertTopLeftCornerX + 3 * nextCubeFaceDistance, centerVertTopLeftCornerY, 4);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY + nextCubeFaceDistance, 5);
        //paintClicks(g);
    }

    private void paintFace(Graphics g, int faceStartX, int faceStartY, int faceIndex) {
        g.setColor(Color.BLACK);
        CubeFace cubeFace = cubeFaces[faceIndex];
        cubeFace.setBounds(faceStartX, faceStartY,
                singleFaceDimension + stickerBorderThickness,
                singleFaceDimension + stickerBorderThickness);
        g.fillRect(cubeFace.x, cubeFace.y, cubeFace.width, cubeFace.height);
        for(int i = 0; i < 9; i++) {
            Sticker sticker = cubeFace.getSticker(i);
            int stickerStartX = faceStartX + ((i % 3) * singleFaceDimension / 3);
            int stickerStartY = faceStartY + ((i / 3) * singleFaceDimension / 3);
            paintSticker(g, stickerStartX, stickerStartY, sticker);
//            if (i != 4 && memoEditMode != null) {
//                if (memoEditMode.equals("Corners")  && i % 2 == 0) {
//                    paintMemo(g, sticker);
//                } else if (memoEditMode.equals("Edges")  && i % 2 == 1) {
//                    paintMemo(g, sticker);
//                }
//            }
        }
    }

    private void paintSticker (Graphics g, int stickerStartX, int stickerStartY, Sticker sticker) {
        Color displayColor = sticker.getDisplayColor();
        g.setColor(displayColor);
//        sticker.setLocation(stickerStartX + stickerBorderThickness, stickerStartY - stickerBorderThickness);
//        sticker.setSize(singleStickerDimension - stickerBorderThickness, singleStickerDimension - stickerBorderThickness);

        sticker.setBounds(stickerStartX + stickerBorderThickness, stickerStartY + stickerBorderThickness,
                singleFaceDimension / 3 - stickerBorderThickness,
                singleFaceDimension / 3 - stickerBorderThickness);
        g.fillRect(sticker.x, sticker.y, sticker.width, sticker.height);
        paintMemo(g, sticker);
    }

    private void paintMemo(Graphics g, Sticker sticker) {
        if (sticker.getStickerType() != memoEditMode) {
            return;
        }
        FontMetrics fm = g.getFontMetrics();
        String memo = sticker.getMemo() + "";
        int stringWidth = fm.stringWidth(memo);
        int stringHeight = fm.getAscent();
        if (sticker.getConflicted()) {
            g.setColor(new Color(250, 94, 94));
            g.setFont(new Font("Helvetica", Font.BOLD, singleStickerDimension / 2));
        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.PLAIN, singleStickerDimension / 2));
        }
        g.drawString(memo, sticker.x + (singleStickerDimension - stickerBorderThickness) / 2
                        - stringWidth / 2,
                sticker.y + (singleStickerDimension - stickerBorderThickness) / 2
                        + stringHeight / 2);
    }

    public void turnOffEditMode(Sticker sticker) {
        if (sticker != null) {
            sticker.setDisplayColor(sticker.getTrueColor());
            sticker.turnOffEditMode();
            //System.out.println("turned off edit mode");
            editingMemo = false;
            pressedSticker = null;
        }
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocus();
        if (pressedSticker != null) {
            pressedSticker.setDisplayColor(pressedSticker.getTrueColor());
            turnOffEditMode(pressedSticker);
            return;
        }
        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);
        Sticker clickedSticker;
        //repaint();
        //clickedPoints.add(clickLoc);
        for (CubeFace face : cubeFaces) {
            if (face.contains(clickLoc)) {
                clickedSticker = face.findClickedSticker(clickLoc);
                if (clickedSticker == null || pressedSticker == clickedSticker) {
                    return;
                }
                pressedSticker = clickedSticker;
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);
        Sticker clickedSticker;
        //repaint();
        //clickedPoints.add(clickLoc);
        for (CubeFace face : cubeFaces) {
            if (face.contains(clickLoc)) {
                clickedSticker = face.findClickedSticker(clickLoc);
                if (clickedSticker == null || clickedSticker != pressedSticker) {
                    return;
                }
                clickedSticker.turnOnEditMode();
                editingMemo = true;
                    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("change now" + changeCount++);

                            if (clickedSticker.getDisplayColor() == clickedSticker.getTrueColor()) {
                                clickedSticker.setDisplayColor(Sticker.BLINK_COLOR);
                            } else {
                                clickedSticker.setDisplayColor(clickedSticker.getTrueColor());
                            }
                            repaint();
                            if (!clickedSticker.getEditModeOn()) {
                                turnOffEditMode(clickedSticker);
                                repaint();
                                executorService.shutdownNow();
                            }
                        }
                    }, 0, 500, TimeUnit.MILLISECONDS);
                    //clickedSticker.setColor(Color.CYAN);

                return;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
        if (editingMemo) {
            String s = e.getKeyChar() + "";
            s = s.toUpperCase();
            char c = s.charAt(0);
            if ((c >= 65 && c <= 90) && c != pressedSticker.getMemo()) {
                // if c is different from what it was previously, check if any conflicts
                // are solved
                ArrayList<Sticker> persistentConflicts = findStickerConflicts(pressedSticker);
                // if there are more than 2 conflicts, others still remain conflicted
                boolean conflictPersist = persistentConflicts.size() > 2;
                for (Sticker sticker : persistentConflicts) {
                    sticker.setConflicted(conflictPersist);
                }

                pressedSticker.setMemo(c);
                // check for conflict stickers
                ArrayList<Sticker> newConflicts = findStickerConflicts(pressedSticker);
                // if this is the only sticker with the new memo, no new conflicts
                boolean newConflict = newConflicts.size() > 1;
                for (Sticker sticker : newConflicts) {
                    sticker.setConflicted(newConflict);
                }
                saveMemoScheme();
                repaint();
            }
            turnOffEditMode(pressedSticker);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
