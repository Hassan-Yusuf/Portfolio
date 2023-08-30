package com.company;

import javax.swing.ImageIcon;

public class garbage extends gameObject{
    public garbage(int x, int y){initGarbage(x,y);}
    private void initGarbage(int x, int y){

        String garbageImg = "res/garbage.jpg";
        ImageIcon ii = new ImageIcon(garbageImg);
        setImage(ii.getImage());
        this.x=x;
        this.y=y;
    }


}
