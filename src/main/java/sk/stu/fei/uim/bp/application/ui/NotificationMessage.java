package sk.stu.fei.uim.bp.application.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the notification-message template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("notification-message")
@JsModule("./views/components/notification-message.js")
public class NotificationMessage extends PolymerTemplate<NotificationMessage.NotificationMessageModel> {

    @Id("iconPlace")
    private Div iconPlace;

    @Id("messagesPlace")
    private HorizontalLayout messagesPlace;

    @Id("messageText")
    private Div messageText;

    private Icon icon;


    public NotificationMessage(String infoText)
    {
        NotificationMessageType type = NotificationMessageType.INFO;
        setIconPlace(type.getIcon());
        this.messageText.setText(infoText);
    }

    public NotificationMessage(String text,NotificationMessageType type)
    {
        setIconPlace(type.getIcon());
        this.messageText.setText(text);
    }

    private void setIconPlace(Icon icon)
    {
        this.icon = icon;
        this.iconPlace.removeAll();
        this.iconPlace.add(this.icon);
    }

    /**
     * This model binds properties between NotificationMessage and notification-message
     */
    public interface NotificationMessageModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
