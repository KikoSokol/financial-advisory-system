package sk.stu.fei.uim.bp.application.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationProvider
{

    public NotificationProvider()
    {

    }

    public void showErrorMessage(String errorMessage)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(notification,errorMessage,VaadinIcon.EXCLAMATION,"#D50000"));
        notification.open();
    }

    public void showErrorMessage(String errorMessage,int duration)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(errorMessage,VaadinIcon.EXCLAMATION,"#D50000"));
        notification.setDuration(duration);
        notification.open();
    }

    public void showInformationMessage(String informationMessage)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(notification,informationMessage,VaadinIcon.INFO,"#2979FF"));
        notification.open();
    }

    public void showInformationMessage(String informationMessage,int duration)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(informationMessage,VaadinIcon.INFO,"#2979FF"));
        notification.setDuration(duration);
        notification.open();
    }

    public void showSuccessMessage(String successMessage)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(notification,successMessage,VaadinIcon.CHECK,"#00E676"));
        notification.open();
    }

    public void showSuccessMessage(String successMessage,int duration)
    {
        Notification notification = new Notification();
        notification.add(createLayoutForNotification(successMessage,VaadinIcon.CHECK,"#00E676"));
        notification.setDuration(duration);
        notification.open();
    }


    public HorizontalLayout createLayoutForNotification(Notification notification, String message,VaadinIcon iconType,String iconColor)
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        horizontalLayout.add(createIcon(iconType,iconColor));
        horizontalLayout.add(message);

//        Button button = new Button();
//        button.setIcon(new Icon(VaadinIcon.CLOSE));
//        button.addClickListener(event-> notification.close());
        Icon icon = new Icon(VaadinIcon.CLOSE);
        icon.addClickListener(event-> notification.close());
        horizontalLayout.add(icon);

        return horizontalLayout;
    }

    public HorizontalLayout createLayoutForNotification(String message,VaadinIcon iconType,String iconColor)
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        horizontalLayout.add(createIcon(iconType,iconColor));
        horizontalLayout.add(message);

        return horizontalLayout;
    }

    public Icon createIcon(VaadinIcon iconType,String color)
    {
        Icon icon = new Icon(iconType);
        icon.setColor(color);
        icon.setSize("20%");

        return icon;
    }




}
