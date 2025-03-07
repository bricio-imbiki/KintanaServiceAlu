package Models;

import javax.swing.Icon;

public class ModelMenu {
  private boolean enableMenu;
    private Icon icon;
    private String menuName;
  private boolean showMenu;

    public ModelMenu(Icon icon, String menuName) {
        this.icon = icon;
        this.menuName = menuName;
    }
  
    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
      

    public boolean isEnableMenu() {
        return enableMenu;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }
    public ModelMenu() {
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
