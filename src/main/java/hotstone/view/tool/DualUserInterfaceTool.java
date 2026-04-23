package hotstone.view.tool;

import frds.broker.IPCException;
import hotstone.framework.Game;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class DualUserInterfaceTool extends NullTool {
    private final Tool theNullTool;
    private final Drawing model;
    private Tool state;
    private DrawingEditor editor;
    private Game game;

    public DualUserInterfaceTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
        model = editor.drawing();
        state = theNullTool = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        // Find the figure below mouse (x,y)
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
        // Iff that figure is associated with our HotStone
        // (All MiniDraw figures that handle HotStone graphics are
        // implementing this role interface).
        if (figureAtPosition instanceof HotStoneFigure) {
            HotStoneFigure hsf = (HotStoneFigure) figureAtPosition;
            // [OK] TODO: Complete this state selection
            if (hsf.getType() == HotStoneFigureType.CARD_FIGURE) {
                state = new PlayCardTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.TURN_BUTTON ||
                    hsf.getType() == HotStoneFigureType.SWAP_BUTTON) {
                state = new EndTurnTool(editor, game);
            } else if (hsf.getType() == HotStoneFigureType.MINION_FIGURE) {
                // [OK] TODO: Handle moving minions -> attacks
                state = new MinionAttackTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.HERO_FIGURE) {
                // [OK]TODO: Handle clicking heroes -> use power
                state = new UsePowerTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.WIN_BUTTON) {
                // Clicking the 'won button' should do nothing!
                state = theNullTool; // User have to close the window to restart.
            } else if (hsf.getType() == HotStoneFigureType.OPPONENT_ACTION_BUTTON) {
                state = new UpdateGuiTool(model);
            }

        }
        state.mouseDown(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        try{
            state.mouseUp(e,x,y);
            if(state != theNullTool){
                System.out.println("--> bruteforce redraw");
                model.requestUpdate();
            }
        } catch (IPCException exc){
            throw new IPCException("IPC exception occured " + exc);
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        state.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        state.mouseMove(e, x, y);
    }


}
