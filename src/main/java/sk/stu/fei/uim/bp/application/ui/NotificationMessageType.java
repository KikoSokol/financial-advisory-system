package sk.stu.fei.uim.bp.application.ui;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public enum NotificationMessageType
{
    SUCCESS(new Icon(VaadinIcon.CHECK),"#00E676"),
    ERROR(new Icon(VaadinIcon.EXCLAMATION),"#D50000"),
    INFO(new Icon(VaadinIcon.INFO),"#2979FF");


    private Icon icon;
    private String colorIcon;


    NotificationMessageType(Icon icon,String colorIcon)
    {
        setIcon(icon);
        setColorIcon(colorIcon);

        setIconParameter("100%",colorIcon);
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getColorIcon() {
        return colorIcon;
    }

    public void setColorIcon(String colorIcon) {
        this.colorIcon = colorIcon;
    }


    private void setIconParameter(String size, String color)
    {
        if(this.icon != null)
        {
            this.icon.setSize(size);
            this.icon.setColor(color);
        }
    }


}
