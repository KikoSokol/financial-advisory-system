package sk.stu.fei.uim.bp.application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;

import java.util.Optional;
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@CssImport("views/main/main-view.css")
@JsModule("views/main/shared-styles.js")
@Route(value = "")
public class MainView extends AppLayout
{
    private final Tabs menu;
    private H1 title;

    public MainView()
    {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true,getHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawer(menu));

    }


    private Component getHeaderContent()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark",true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        title = new H1();
        layout.add(new DrawerToggle());
        layout.add(title);
        return layout;

    }

    private Tabs createMenu()
    {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getMenuItems());

        return tabs;
    }

    private Component createDrawer(Tabs menu)
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s",true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new H1("APP"));
        layout.add(logoLayout,menu);
        return layout;

    }


    private Component[] getMenuItems()
    {
        Component[] components = {getTab("Domov",MainView.class),
                getTab("Klienti", ClientMainView.class),getTab("Zmluvy",MainView.class)};

        return components;
    }


    private Tab getTab(String text, Class<? extends Component> navigationTarget)
    {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text,navigationTarget));
        ComponentUtil.setData(tab, Class.class,navigationTarget);
        return tab;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        title.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
