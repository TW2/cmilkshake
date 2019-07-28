/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.table;

import cmilkshake.ass.Ass;
import cmilkshake.ass.AssLine;
import cmilkshake.ass.AssLineType;
import cmilkshake.status.CompareTextReport;
import cmilkshake.util.DrawColor;
import cmilkshake.util.Time;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author util2
 */
public class AssTableModel extends AbstractTableModel {
    
    private AssTableHeader ath = new AssTableHeader();
    private List<AssLine> als = new ArrayList<>();
    private JTable table = null;
    private static TextType textType = TextType.WithItems;
    private static CompareTextReport textReport = null;

    public AssTableModel(JTable table) {
        this.table = table;
        
        table.setDefaultRenderer(Integer.class, new AssCellRenderer());
        table.setDefaultRenderer(String.class, new AssCellRenderer());
        table.setDefaultRenderer(Time.class, new AssCellRenderer());
        table.setDefaultRenderer(AssLineType.class, new AssCellRenderer());
    }
    
    public void add(Ass ass){
        for(int i=0; i<ass.getLines().size(); i++){
            addLine(ass.getLines().get(i));
        }
    }
    
    public void addLine(AssLine al){
        als.add(al);
        
        table.revalidate();
        table.updateUI();
    }
    
    public AssLine getLine(int row){
        return als.get(row);
    }
    
    public void applyTextReport(CompareTextReport textReport){
        AssTableModel.textReport = textReport;
        
        table.revalidate();
        table.updateUI();
    }

    @Override
    public int getRowCount() {
        return als.size();
    }

    @Override
    public int getColumnCount() {
        return 13;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        String value = "?";
        
        switch(columnIndex){
            case 0: value = ath.getLineNumber(); break;
            case 1: value = ath.getLinetype(); break;
            case 2: value = ath.getLayer(); break;
            case 3: value = ath.getStart(); break;
            case 4: value = ath.getEnd(); break;
            case 5: value = ath.getTotal(); break;
            case 6: value = ath.getStyle(); break;
            case 7: value = ath.getName(); break;
            case 8: value = ath.getMl(); break;
            case 9: value = ath.getMr(); break;
            case 10: value = ath.getMv(); break;
            case 11: value = ath.getEffect(); break;
            case 12: value = ath.getText(); break;
            default: throw new IllegalArgumentException();
        }
        
        return value;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return rowIndex + 1;
            case 1: return als.get(rowIndex).getLineType();
            case 2: return als.get(rowIndex).getLayer();
            case 3: return als.get(rowIndex).getStart();
            case 4: return als.get(rowIndex).getEnd();
            case 5: return Time.substract(als.get(rowIndex).getStart(), als.get(rowIndex).getEnd());
            case 6: return als.get(rowIndex).getStyle();
            case 7: return als.get(rowIndex).getName();
            case 8: return als.get(rowIndex).getML();
            case 9: return als.get(rowIndex).getMR();
            case 10: return als.get(rowIndex).getMV();
            case 11: return als.get(rowIndex).getEffect();
            case 12: return als.get(rowIndex).getText();
        }
        
        return null;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return Integer.class;           // int (line number)
            case 1: return AssLineType.class;       // AssLineType (line type)
            case 2: return Integer.class;           // int (layer)
            case 3: return Time.class;              // Time (start)
            case 4: return Time.class;              // Time (end)
            case 5: return Time.class;              // Time (total)
            case 6: return String.class;            // String (style)
            case 7: return String.class;            // String (name)
            case 8: return Integer.class;           // int (margin L)
            case 9: return Integer.class;           // int (margin R)
            case 10: return Integer.class;          // int (margin V)
            case 11: return String.class;           // String (effect)
            case 12: return String.class;           // String (text)
            default: return Object.class;
        }
    }
    
    public void updateColumnSize(JTable table){
        final TableColumnModel columnModel = table.getColumnModel();
        
        int max = table.getWidth();
        
        for (int column = 0; column < table.getColumnCount(); column++) {
            switch(column){
                case 0: columnModel.getColumn(column).setPreferredWidth(40); max -= 40; break;
                case 1: columnModel.getColumn(column).setPreferredWidth(20); max -= 20; break;
                case 2: columnModel.getColumn(column).setPreferredWidth(30); max -= 30; break;
                case 3: columnModel.getColumn(column).setPreferredWidth(70); max -= 70; break;
                case 4: columnModel.getColumn(column).setPreferredWidth(70); max -= 70; break;
                case 5: columnModel.getColumn(column).setPreferredWidth(70); max -= 70; break;
                case 6: columnModel.getColumn(column).setPreferredWidth(80); max -= 80; break;
                case 7: columnModel.getColumn(column).setPreferredWidth(80); max -= 80; break;
                case 8: columnModel.getColumn(column).setPreferredWidth(20); max -= 20; break;
                case 9: columnModel.getColumn(column).setPreferredWidth(20); max -= 20; break;
                case 10: columnModel.getColumn(column).setPreferredWidth(20); max -= 20; break;
                case 11: columnModel.getColumn(column).setPreferredWidth(20); max -= 20; break;
                case 12: columnModel.getColumn(column).setPreferredWidth(max); break;
            }
        }
        
        // And reinit
        table.setAutoCreateRowSorter(true);
    }

    public void setTextType(TextType textType) {
        AssTableModel.textType = textType;
        table.revalidate();
        table.updateUI();
    }
    
    //##########################################################################
    //##########################################################################
    //##########################################################################
    
    public static class AssTableHeader {
        
        private String lineNumber, layer, linetype, start, end, total, style, 
                name, ml, mr, mv, effect, text;

        public AssTableHeader() {
            this.lineNumber = "#";
            this.layer = "Layer";
            this.linetype = "Line type";
            this.start = "Start";
            this.end = "End";
            this.total = "Total";
            this.style = "Style";
            this.name = "Name";
            this.ml = "M. Left";
            this.mr = "M. Right";
            this.mv = "M. Vertical";
            this.effect = "Effect";
            this.text = "Text";
        }

        public AssTableHeader(String lineNumber, String layer, String linetype, String start, String end, String total, String style, String name, String ml, String mr, String mv, String effect, String text) {
            this.lineNumber = lineNumber;
            this.layer = layer;
            this.linetype = linetype;
            this.start = start;
            this.end = end;
            this.total = total;
            this.style = style;
            this.name = name;
            this.ml = ml;
            this.mr = mr;
            this.mv = mv;
            this.effect = effect;
            this.text = text;
        }

        public String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public String getLinetype() {
            return linetype;
        }

        public void setLinetype(String linetype) {
            this.linetype = linetype;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMl() {
            return ml;
        }

        public void setMl(String ml) {
            this.ml = ml;
        }

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMv() {
            return mv;
        }

        public void setMv(String mv) {
            this.mv = mv;
        }

        public String getEffect() {
            return effect;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    
    //##########################################################################
    //##########################################################################
    //##########################################################################
    
    public static class AssCellRenderer extends JLabel implements TableCellRenderer {

        Color commentColor = new Color(230, 230, 255);
        
        public AssCellRenderer() {
            init();
        }
        
        private void init(){
            setOpaque(true);
            setBackground(Color.white);
            setForeground(Color.black);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            if(value instanceof Integer){
                int x = (Integer)value;
                setText(Integer.toString(x));
                setHorizontalAlignment(CENTER);
                if(column == 0){
                    setBackground(DrawColor.chocolate.getColor(0.5f));
                }else{
                    AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                    if(null == alt){
                        setBackground(isSelected ? Color.white.darker() : Color.white);
                    }else switch (alt) {
                        case Comment:
                            setBackground(isSelected ? commentColor.darker() : commentColor);
                            break;
                        case Dialogue:
                            setBackground(isSelected ? Color.white.darker() : Color.white);
                            break;
                        default:
                            break;
                    }
                    
                }
            }
            
            if(value instanceof String){
                if(column == 12){
                    setText(withTextRender(textType, (String)value));
                }else{
                    setText((String)value);
                }
                AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                
                boolean removed = false, added = false;                
                if(textReport != null){
                    for(AssLine line : textReport.getDeletedLines()){
                        if(line.getText().equals(value)){
                            removed = true;
                            break;
                        }
                    }
                    for(AssLine line : textReport.getAddedLines()){
                        if(line.getText().equals(value)){
                            added = true;
                            break;
                        }
                    }
                }
                
                Color current = Color.white;
                if(column == 12){
                    if(removed == true){
                        current = DrawColor.red.getColor(0.3f);
                    }else if(added == true){
                        current = DrawColor.green.getColor(0.3f);
                    }
                }
                if(null == alt){
                    setBackground(isSelected ? Color.white.darker() : Color.white);
                }else switch (alt) {
                    case Comment:
                        current = removed == true | added == true ? current : commentColor;
                        setBackground(isSelected ? commentColor.darker() : current);
                        break;
                    case Dialogue:
                        setBackground(isSelected ? Color.white.darker() : current);
                        break;
                    default:                        
                        break;
                }
            }
            
            if(value instanceof Time){
                Time t = (Time)value;
                setText(t.toASSTime());
                AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                if(null == alt){
                    setBackground(isSelected ? Color.white.darker() : Color.white);
                }else switch (alt) {
                    case Comment:
                        setBackground(isSelected ? commentColor.darker() : commentColor);
                        break;
                    case Dialogue:
                        setBackground(isSelected ? Color.white.darker() : Color.white);
                        break;
                    default:
                        break;
                }
            }
            
            if(value instanceof AssLineType){
                AssLineType alt = (AssLineType)value;
                setHorizontalAlignment(CENTER);
                switch (alt) {
                    case Comment:
                        setText("#");
                        setBackground(isSelected ? commentColor.darker() : commentColor);
                        break;
                    case Dialogue:
                        setText("D");
                        setBackground(isSelected ? Color.white.darker() : Color.white);
                        break;
                    default:
                        setText("?");
                        setBackground(isSelected ? Color.white.darker() : Color.white);
                        break;
                }
            }
            
            return this;
        }
        
    }
    
    //##########################################################################
    //##########################################################################
    //##########################################################################
    
    public static String withTextRender(TextType tt, String text){
        // Change text as follow :
        // StripAll - clears all tags.
        // Normal - nothing is done.
        // WithItems - replace tags by specials characters.
        String str = "";
        switch(tt){
            case StripAll:
                // Strip text if the text contains edit marks.
                if(text.contains("{\\")){
                    try{
                        str = text.replaceAll("\\{[^\\}]+\\}", "");
                    }catch(Exception e){
                        str = text;
                    }
                }else{
                    str = text;
                }
                break;
            case WithItems:
                // Replace tags by items if the text contains edit marks.
                if(text.contains("{\\")){
                    try{
                        str = text.replaceAll("\\{[^\\}]+\\}", "◆");
                    }catch(Exception e){
                        str = text;
                    }
                }else{
                    str = text;
                }
                break;
            case Normal:
                // Do nothing.
                str = text;
                break;
        }

        return str;
    }
    
}
