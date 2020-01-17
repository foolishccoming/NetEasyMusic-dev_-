package t3.henu.neteasymusic.hkq_fragment1;

/**
 * Created by Android on 2017/5/28.
 */

public class SelectItems {
    public String ItemTitle;
    public int img1, img2, img3;
    public String text1, text2, text3;

    public SelectItems(String itemTitle, int img1, int img2, int img3, String text1, String text2, String text3) {
        ItemTitle = itemTitle;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;

        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getItemTitle() {
        return ItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        ItemTitle = itemTitle;
    }


    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getImg3() {
        return img3;
    }

    public void setImg3(int img3) {
        this.img3 = img3;
    }
}
