import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.*;
import javax.swing.*;
import java.awt.font.*;
import java.awt.geom.*;

public class MyPaint extends JApplet implements ActionListener , ItemListener {
JLabel titlLabel,linLabel,filLabel,string;
static JComboBox linclr,filclr;
static  JButton b1,b2,b3,b4;
JFrame f ;
JPanel panel;
public static boolean no2D=false;
ShapePanel display;
static Object obj;
Font fo = new Font("Times New Roman",Font.BOLD,20);

public void init(){
f = new JFrame("MY PAINT APPLICATION");
f.addWindowListener(new WindowAdapter(){

@Override
public void windowClosing(WindowEvent e){
System.exit(0);
}
});
panel = new JPanel();
panel.setLayout(null);
f.getContentPane().add(panel);
f.setVisible(true);
f.setSize(300,300);
titlLabel = new JLabel();
b1 = new JButton("RECTANGLE");
b2 = new JButton("CIRCLE");
b3 = new JButton("POLYGON");
b4 = new JButton("ELLIPSE");
linLabel = new JLabel();
filLabel = new JLabel();
display = new ShapePanel();
string = new JLabel();
titlLabel.setText("MY PAINT APPLICATION");
titlLabel.setFont(fo);
titlLabel.setBounds(400,0,300,40);
panel.add(titlLabel);
b1.addActionListener(this);
b2.addActionListener(this);
b3.addActionListener(this);
b4.addActionListener(this);
b1.setFont(fo);
b1.setBounds(0,60,250,40);
panel.add(b1);
b2.setFont(fo);
b2.setBounds(260,60,250,40);
panel.add(b2);
b3.setFont(fo);
b3.setBounds(520,60,250,40);
panel.add(b3);
b4.setFont(fo);
b4.setBounds(780,60,250,40);
panel.add(b4);
linLabel.setFont(fo);
linLabel.setText("SELECT LINE COLOR");
linLabel.setBounds(200,120,400,40);
panel.add(linLabel);
filLabel.setFont(fo);
filLabel.setText("SELECT FILL COLOR");
filLabel.setBounds(720,120,400,40);
panel.add(filLabel);
linclr = new JComboBox( new Object []{"RED","BLUE","GREEN","YELLOW","PURPLE"});
linclr.addItemListener(this);
linclr.setFont(fo);
linclr.setBounds(10,160,500,40);
panel.add(linclr);
filclr = new JComboBox( new Object []{"BLUE","RED","GREEN","YELLOW","PURPLE"});
filclr.addItemListener(this);
filclr.setFont(fo);
filclr.setBounds(515,160,500,40);
panel.add(filclr);
display.setBackground(Color.white);
display.setBounds(0,200,1025,500);
panel.add(display);
validate();
}

public void actionPerformed(ActionEvent e){
obj= e.getActionCommand();
display.renderShape();
}

public void itemStateChanged(ItemEvent e) {
display.renderShape();
}

public static void main(String [] args)
{
JApplet applet = new MyPaint();
applet.init();
}
}

//M20071
class ShapePanel extends JPanel  {
BasicStroke bstroke = new BasicStroke(10.0f);
int w,h;
int pentagonXs[] = new int[5];
int pentagonYs[] = new int[5];
Shape shapes[]= new Shape[4];
private static int SHAPE_WIDTH = 200;
private static int SHAPE_HEIGHT = 200;
    
public ShapePanel() {
setBackground(Color.black);
calcPentagon( SHAPE_WIDTH/2,  SHAPE_HEIGHT/2, SHAPE_WIDTH/2,pentagonXs, pentagonYs);
shapes[0] = new Rectangle(0, 0, 200, 200);
shapes[1] = new Ellipse2D.Double(0.0, 0.0, 200.0, 200.0);
shapes[2] = new Polygon(pentagonXs, pentagonYs,5);
shapes[3] = new Ellipse2D.Double(0.0,0.0,260.0,200.0);
}

private void calcPentagon(int center_x, int center_y, int radius,int[] pointsX, int[] pointsY) {
double delta_radians = 2.0 * Math.PI / 5.0;
double angle = 0.0;
for ( int i = 0; i < 5; i++ ) {
 float x = (float) (radius * Math.sin(angle));
 float y = (float) (radius * Math.cos(angle));
 pointsX[i] = Math.round(center_x + x);
 pointsY[i] = Math.round(center_y + y);
 angle += delta_radians;
}
}

public void renderShape(){
repaint();
}

@Override
public void paint(Graphics g){
super.paint(g);
if(!MyPaint.no2D)
{
    Graphics2D g2=(Graphics2D) g;
    Dimension d = getSize();
    w=d.width;
    h=d.height;
    int width;
    g2.setPaint(Color.white);
    String s ="select any of the shapes mentioned above and also select line color and fill color from the given list";
    TextLayout thisTl = new TextLayout(s, new Font("Helvetica", 0, 20), g2.getFontRenderContext());
    Rectangle2D bounds = thisTl.getBounds();
    width = (int) bounds.getWidth();
    thisTl.draw(g2, w / 2 - width / 2, 20);
    Stroke oldStroke = g2.getStroke();
    g2.setStroke(new BasicStroke(10.0f));
    Paint oldPaint = g2.getPaint();
    // Sets the linecolor.
    switch (MyPaint.linclr.getSelectedIndex()) {
    case 0:
    g2.setPaint(Color.red);
    break;
    case 1:
    g2.setPaint(Color.blue);
    break;
    case 2:
    g2.setPaint(Color.green);
    break;
    case 3:
    g2.setPaint(Color.yellow);
    break;
    case 4:
    Color purple = new Color(255,0,255);
    g2.setPaint(purple);
    break;
    }

    int t=0;
    if(MyPaint.obj=="RECTANGLE")
    {
    t=0;
    }
    else if(MyPaint.obj=="CIRCLE")
    {
    t=1;
    }
    else if(MyPaint.obj=="POLYGON")
    {
    t=2;
    }
    else if(MyPaint.obj=="ELLIPSE")
    {
    t=3;
    }
    Shape shape = shapes[t];
    Rectangle r = shape.getBounds();
    // Sets the selected Shape to the center of the Canvas.
    AffineTransform saveXform = g2.getTransform();
    AffineTransform toCenterAt = new AffineTransform();
    toCenterAt.translate(w / 2 - (r.width / 2), h / 2 - (r.height / 2));
    g2.transform(toCenterAt);
    g2.draw(shape);
    //Sets fill color.
    switch(MyPaint.filclr.getSelectedIndex()){
    case 0:
    g2.setColor(Color.blue);
    g2.fill(shape);
    break;
    case 1:
    g2.setColor(Color.red);
    g2.fill(shape);
    break;
    case 2:
    g2.setColor(Color.green);
    g2.fill(shape);
    break;
    case 3:
    g2.setColor(Color.yellow);
    g2.fill(shape);
    break;
    case 4:
    Color purple = new Color(255,0,255);
    g2.setColor(purple);
    g2.fill(shape);
    break;
    }
    g2.setTransform(saveXform);
    g2.setStroke(oldStroke);
    g2.setPaint(oldPaint);

}
else
{
   g.drawRect(0, 0, 100, 100);
}
}
}
