package iomis.github.mis_metro_information_system.mis;

/**
 * Created by felipe.gutierrez on 8/07/15.
 */
public class DrawerItem {
    private String name;
    private int IconId;

    public DrawerItem(String name, int id){
        this.name = name;
        this.IconId = id;
    }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return IconId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }
}
