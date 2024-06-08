package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.ZoneId;
import java.time.LocalDateTime;

class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();

    public ImageRenderer() {
        lbl.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            String imagePath = value.toString();
            ImageIcon icon = new ImageIcon(imagePath);
            lbl.setIcon(icon);
        } else {
            lbl.setIcon(null);
        }

        if (isSelected) {
            lbl.setBackground(table.getSelectionBackground());
        } else {
            lbl.setBackground(table.getBackground());
        }

        return lbl;
    }
}
