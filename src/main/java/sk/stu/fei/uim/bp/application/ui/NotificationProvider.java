package sk.stu.fei.uim.bp.application.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

public class NotificationProvider
{

    public NotificationProvider()
    {

    }

    public void showErrorMessage(String errorText)
    {
        NotificationMessage message = new NotificationMessage(errorText, NotificationMessageType.ERROR);

        Notification notification = new Notification(message);
        notification.setPosition(Notification.Position.BOTTOM_START);

        Button button = new Button("Zavri");
        button.addClickListener(event-> notification.close());
        notification.add(button);

        notification.open();
    }

    public void showInformationMessage(String informationText)
    {
        NotificationMessage message = new NotificationMessage(informationText, NotificationMessageType.INFO);

        Notification notification = new Notification(message);
        notification.setPosition(Notification.Position.BOTTOM_START);

        Button button = new Button("Zavri");
        button.addClickListener(event-> notification.close());
        notification.add(button);

        notification.open();
    }

    public void showSuccessMessage(String successText)
    {
        NotificationMessage message = new NotificationMessage(successText, NotificationMessageType.SUCCESS);

        Notification notification = new Notification(message);
        notification.setPosition(Notification.Position.BOTTOM_START);

        Button button = new Button("Zavri");
        button.addClickListener(event-> notification.close());
        notification.add(button);

        notification.open();
    }




}
