package com.ddxlabs.nim.view;

import java.util.ArrayList;
import java.util.List;

public interface ViewContainer {

    public Menu getMenu();

    public List<ViewComponent> allViews();

}
