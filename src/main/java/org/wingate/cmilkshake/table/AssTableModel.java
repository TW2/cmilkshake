/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.table;

import org.wingate.cmilkshake.ass.Ass;
import org.wingate.cmilkshake.ass.AssLine;
import org.wingate.cmilkshake.ass.AssLineType;
import org.wingate.cmilkshake.compare.CompareTextReport;
import org.wingate.cmilkshake.compare.CompareTimeReport;
import org.wingate.cmilkshake.util.DrawColor;
import org.wingate.cmilkshake.util.Time;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.wingate.cmilkshake.compare.CompareCPLReport;
import org.wingate.cmilkshake.compare.CompareCPLReport.CplCounter;
import org.wingate.cmilkshake.compare.CompareCPSReport;
import org.wingate.cmilkshake.compare.CompareCPSReport.CpsCounter;
import org.wingate.cmilkshake.compare.Counter;

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
    private static CompareTimeReport timeReport = null;
    private static CompareCPLReport cplReport = null;
    private static CompareCPSReport cpsReport = null;

    public AssTableModel(JTable table) {
        this.table = table;
        
        table.setDefaultRenderer(Integer.class, new AssCellRenderer());
        table.setDefaultRenderer(String.class, new AssCellRenderer());
        table.setDefaultRenderer(Time.class, new AssCellRenderer());
        table.setDefaultRenderer(AssLineType.class, new AssCellRenderer());
        table.setDefaultRenderer(CpsCounter.class, new AssCellRenderer());
        table.setDefaultRenderer(CplCounter.class, new AssCellRenderer());
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
    
    public void applyTimeReport(CompareTimeReport timeReport){
        AssTableModel.timeReport = timeReport;
        
        table.revalidate();
        table.updateUI();
    }
    
    public void applyCplReport(CompareCPLReport cplReport){
        AssTableModel.cplReport = cplReport;
        
        table.revalidate();
        table.updateUI();
    }
    
    public void applyCpsReport(CompareCPSReport cpsReport){
        AssTableModel.cpsReport = cpsReport;
        
        table.revalidate();
        table.updateUI();
    }

    @Override
    public int getRowCount() {
        return als.size();
    }

    @Override
    public int getColumnCount() {
        return 15;
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
            case 12: value = "CPL"; break;
            case 13: value = "CPS"; break;
            case 14: value = ath.getText(); break;
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
            case 12: return new CplCounter(Counter.getCPL(als.get(rowIndex)), als.get(rowIndex));            
            case 13: return new CpsCounter(Counter.getCPS(als.get(rowIndex)), als.get(rowIndex));
            case 14: return als.get(rowIndex).getText();
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
            case 12: return CplCounter.class;
            case 13: return CpsCounter.class;
            case 14: return String.class;           // String (text)
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
                case 12: columnModel.getColumn(column).setPreferredWidth(30); max -= 30; break;
                case 13: columnModel.getColumn(column).setPreferredWidth(30); max -= 30; break;
                case 14: columnModel.getColumn(column).setPreferredWidth(max); break;
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

        Color commentColor = DrawColor.dark_magenta.getColor(0.5f);
        
        Color selectedCommentColor = DrawColor.gray.getColor(0.5f);
        Color selectedDialogueColor = DrawColor.gray.getColor();
        
        public AssCellRenderer() {
            init();
        }
        
        private void init(){
            setOpaque(true);
            setBackground(Color.black);
            setForeground(Color.white);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            if(value instanceof Integer){
                int x = (Integer)value;
                setText(Integer.toString(x));
                setHorizontalAlignment(CENTER);
                if(column == 0){
                    setBackground(DrawColor.chocolate.getColor(0.3f));
                }else{
                    AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                    if(null == alt){
                        setBackground(isSelected ? selectedDialogueColor : Color.black);
                    }else switch (alt) {
                        case Comment:
                            setBackground(isSelected ? selectedCommentColor : commentColor);
                            break;
                        case Dialogue:
                            setBackground(isSelected ? selectedDialogueColor : Color.black);
                            break;
                        default:
                            break;
                    }
                    
                }
            }
            
            if(value instanceof String){
                if(column == 14){
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
                
                Color current = Color.black;
                if(column == 14){
                    if(removed == true){
                        current = DrawColor.red.getColor(0.3f);
                    }else if(added == true){
                        current = DrawColor.green.getColor(0.3f);
                    }
                }
                if(null == alt){
                    setBackground(isSelected ? selectedDialogueColor : Color.black);
                }else switch (alt) {
                    case Comment:
                        current = removed == true | added == true ? current : commentColor;
                        setBackground(isSelected ? selectedCommentColor : current);
                        break;
                    case Dialogue:
                        setBackground(isSelected ? selectedDialogueColor : current);
                        break;
                    default:                        
                        break;
                }
            }
            
            if(value instanceof Time){
                Time t = (Time)value;
                setText(t.toASSTime());
                AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                
                Border oldborder = getBorder();
                Border sameStartBorder = new LineBorder(DrawColor.violet.getColor(0.3f), 3);
                Border sameEndBorder = new LineBorder(DrawColor.gold.getColor(0.3f), 3);
                Color toApplyColor = Color.black;
                
                boolean sameStart = false, sameEnd = false;
                
                if(timeReport != null){
                    // Compare time status :
                    // Same time >> white
                    // Shift time >> blue
                    // Enlarged >> green
                    // Reduced >> red
                    // Same start >> violet border
                    // Same end >> yellow border
                    for(AssLine line : timeReport.getShiftTimeLines()){
                        if(line.toAssLine().equals(((AssTableModel)table.getModel()).getLine(row).toAssLine())){
                            toApplyColor = DrawColor.corn_flower_blue.getColor(0.3f);
                            break;
                        }
                    }
                    for(AssLine line : timeReport.getEnlargedTimeLines()){
                        if(line.toAssLine().equals(((AssTableModel)table.getModel()).getLine(row).toAssLine())){
                            toApplyColor = DrawColor.green.getColor(0.3f);
                            break;
                        }
                    }
                    for(AssLine line : timeReport.getReducedTimeLines()){
                        if(line.toAssLine().equals(((AssTableModel)table.getModel()).getLine(row).toAssLine())){
                            toApplyColor = DrawColor.red.getColor(0.3f);
                            break;
                        }
                    }
                    for(AssLine line : timeReport.getSameStartTimeLines()){
                        if(line.toAssLine().equals(((AssTableModel)table.getModel()).getLine(row).toAssLine())){
                            sameStart = true;
                            break;
                        }
                    }
                    for(AssLine line : timeReport.getSameEndTimeLines()){
                        if(line.toAssLine().equals(((AssTableModel)table.getModel()).getLine(row).toAssLine())){
                            sameEnd = true;
                            break;
                        }
                    }
                }
                
                if(null == alt){
                    setBackground(isSelected ? selectedDialogueColor : Color.black);
                }else switch (alt) {                    
                    case Comment:
                        if(column != 3 | column != 4 | column != 5){
                            setBackground(isSelected ? selectedCommentColor : commentColor);
                        }else if(column == 3){
                            // Start
                            setBackground(isSelected ? selectedCommentColor : toApplyColor);
                            if(sameStart == true) { setBorder(sameStartBorder); }
                        }else if(column == 4){
                            // End
                            setBackground(isSelected ? selectedCommentColor : toApplyColor);
                            if(sameEnd == true) { setBorder(sameEndBorder); }
                        }else if(column == 5){
                            // Total
                            setBackground(isSelected ? selectedCommentColor : toApplyColor);
                        }
                        break;
                    case Dialogue:
                        if(column != 3 | column != 4 | column != 5){
                            setBackground(isSelected ? selectedDialogueColor : Color.black);
                        }else if(column == 3){
                            // Start
                            setBackground(isSelected ? selectedDialogueColor : toApplyColor);
                            if(sameStart == true) { setBorder(sameStartBorder); }
                        }else if(column == 4){
                            // End
                            setBackground(isSelected ? selectedDialogueColor : toApplyColor);
                            if(sameEnd == true) { setBorder(sameEndBorder); }
                        }else if(column == 5){
                            // Total
                            setBackground(isSelected ? selectedDialogueColor : toApplyColor);
                        }                        
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
                        setBackground(isSelected ? selectedCommentColor : commentColor);
                        break;
                    case Dialogue:
                        setText("D");
                        setBackground(isSelected ? selectedDialogueColor : Color.black);
                        break;
                    default:
                        setText("?");
                        setBackground(isSelected ? selectedDialogueColor : Color.black);
                        break;
                }
            }
            
            if(value instanceof CplCounter){
                CplCounter cplc = (CplCounter)value;
                AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                
                setForeground(Color.white);
                setText(Integer.toString(cplc.getCpl()));
                
                boolean removed = false, added = false;                
                if(cplReport != null){
                    for(CplCounter cpl : cplReport.getOldLines()){
                        if(cpl.getCpl() == cplc.getCpl()){
                            removed = true;
                            break;
                        }
                    }
                    for(CplCounter cpl : cplReport.getNewLines()){
                        if(cpl.getCpl() == cplc.getCpl()){
                            added = true;
                            break;
                        }
                    }
                }
                
                Color current = Color.black;
                if(column == 12){
                    if(removed == true){
                        current = DrawColor.red.getColor(0.3f);
                    }else if(added == true){
                        current = DrawColor.green.getColor(0.3f);
                    }
                }                
                if(null == alt){
                    setBackground(isSelected ? selectedDialogueColor : Color.black);
                }else switch (alt) {
                    case Comment:
                        current = removed == true | added == true ? current : commentColor;
                        setBackground(isSelected ? selectedCommentColor : current);
                        break;
                    case Dialogue:
                        setBackground(isSelected ? selectedDialogueColor : current);
                        break;
                    default:
                        break;
                }
            }
            
            if(value instanceof CpsCounter){
                CpsCounter cpsc = (CpsCounter)value;
                AssLineType alt = (AssLineType)table.getValueAt(row, 1);
                
                setForeground(Color.white);
                setText((new DecimalFormat("#0.00")).format(cpsc.getCps()));
                
                boolean removed = false, added = false;                
                if(cpsReport != null){
                    for(CpsCounter cps : cpsReport.getOldLines()){
                        if(cps.getCps() == cpsc.getCps()){
                            removed = true;
                            break;
                        }
                    }
                    for(CpsCounter cps : cpsReport.getNewLines()){
                        if(cps.getCps() == cpsc.getCps()){
                            added = true;
                            break;
                        }
                    }
                }
                
                Color current = Color.black;
                if(column == 13){
                    if(removed == true){
                        current = DrawColor.red.getColor(0.3f);
                    }else if(added == true){
                        current = DrawColor.green.getColor(0.3f);
                    }
                }                
                if(null == alt){
                    setBackground(isSelected ? selectedDialogueColor : Color.black);
                }else switch (alt) {
                    case Comment:
                        current = removed == true | added == true ? current : commentColor;
                        setBackground(isSelected ? selectedCommentColor : current);
                        break;
                    case Dialogue:
                        setBackground(isSelected ? selectedDialogueColor : current);
                        break;
                    default:
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
