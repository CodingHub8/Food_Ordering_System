package food_ordering_system.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {

    private final int width = 800;
    private final int heigth = 400;
    private final int padding = 25;
    private final int labelPadding = 25;
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color pointColor = new Color(100, 100, 100, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int pointWidth = 4;
    private final int numberYDivisions = 10;
    private List<Double> values;
    private String yAxisLabel;
    private String xAxisLabel;

    public GraphPanel(List<Double> values, String yAxisLabel, String xAxisLabel) {
        this.values = values;
        this.yAxisLabel = yAxisLabel;
        this.xAxisLabel = xAxisLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (values.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxValue() - getMinValue());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxValue() - values.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // Draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // Create Y-Axis grid and labels
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (!values.isEmpty()) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinValue() + (getMaxValue() - getMinValue()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Create X-Axis grid and labels
        for (int i = 0; i < values.size(); i++) {
            if (values.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (values.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((values.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = Integer.toString(i);
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // Create X and Y Axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        // Draw graph line
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - pointWidth / 2;
            int y = graphPoint.y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

        // ADDITIONAL LABELS

        // Y-Axis Label rotated 90 degrees
        String yAxisLabel = getyAxisLabel();
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics yMetrics = g2.getFontMetrics();
        int yLabelWidth = yMetrics.stringWidth(yAxisLabel);
        g2.rotate(-Math.PI / 2);
        g2.drawString(yAxisLabel, -getHeight() / 2 - yLabelWidth / 2, padding - 15);
        g2.rotate(Math.PI / 2);

        // X-Axis Label
        String xAxisLabel = getxAxisLabel();
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics xMetrics = g2.getFontMetrics();
        int xLabelWidth = xMetrics.stringWidth(xAxisLabel);
        g2.drawString(xAxisLabel, getWidth() / 2 - xLabelWidth / 2, getHeight() - 5);
    }

    private double getMinValue() {
        double minValue = Double.MAX_VALUE;
        for (Double value : values) {
            minValue = Math.min(minValue, value);
        }
        return minValue;
    }

    private double getMaxValue() {
        double maxValue = Double.MIN_VALUE;
        for (Double value : values) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }

    public void setData(List<Double> newValues, String xAxisLabel) {
        this.values = newValues;
        this.xAxisLabel = xAxisLabel;
        repaint();
    }

    public void setValues(List<Double> values) {
        this.values = values;
        invalidate();
        this.repaint();
    }

    public List<Double> getValues() {
        return values;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    public void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }
}