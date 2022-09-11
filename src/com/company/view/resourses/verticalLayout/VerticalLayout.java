package com.company.view.resourses.verticalLayout;

import java.awt.*;

    public class VerticalLayout implements LayoutManager
    {
        private final Dimension size = new Dimension();

        public void addLayoutComponent (String name, Component comp) {}
        public void removeLayoutComponent(Component comp) {}

        public Dimension minimumLayoutSize(Container c) {
            return size;
        }

        public Dimension preferredLayoutSize(Container c) {
            return size;
        }

        public void layoutContainer(Container container)
        {
            Component[] list = container.getComponents();
            int currentY = 50;
            for (Component component : list) {
                component.setPreferredSize(new Dimension(400,80));
                Dimension pref = component.getPreferredSize();
                component.setBounds(200, currentY, pref.width, pref.height);
                currentY += 60;
                currentY += pref.height;
            }
        }
    }
