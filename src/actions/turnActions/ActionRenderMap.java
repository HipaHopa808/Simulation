package actions.turnActions;

import entities.Entity;
import map.Coordinates;
import map.Map;

import javax.swing.*;
import java.awt.*;

public class ActionRenderMap {
    private JFrame frame;
    private JLabel mapRender;
    private int RENDER_WINDOWS_WIDTH = 600;
    private int RENDER_WINDOWS_HEIGHT = 500;

    public ActionRenderMap() {
        frame = new JFrame("Симуляция");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(RENDER_WINDOWS_WIDTH, RENDER_WINDOWS_HEIGHT);
        frame.getContentPane().setBackground(Color.BLACK);
        mapRender = new JLabel();
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        mapRender.setFont(emojiFont);
        mapRender.setForeground(Color.CYAN);
        frame.getContentPane().add(mapRender);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void renderMap(Map map) {
        mapRender.setText(getRenderText(map));
    }

    private String getRenderText(Map map) {
        StringBuilder renderText = new StringBuilder();
        renderText.append("<html><body>");
        int rows = map.getRows();
        int cols = map.getCols();


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Entity entity = map.get(new Coordinates(i, j));
                EntityRender entityRender = EntityRender.getEntityRender(entity);
                renderText.append(String.format("<font color='%s'>", entityRender.getColor()));
                renderText.append(entityRender.getSymbol());
                renderText.append("</font>");
            }
            renderText.append("<br>");
        }
        renderText.append("</body></html>");

        return renderText.toString();

    }
}



