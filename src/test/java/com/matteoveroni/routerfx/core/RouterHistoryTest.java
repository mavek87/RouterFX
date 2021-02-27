package com.matteoveroni.routerfx.core;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouterHistoryTest {

    private static final String ROUTE_SCENE_1_NAME = "route1";
    private static final String ROUTE_SCENE_2_NAME = "route2";
    private static final String ROUTE_SCENE_3_NAME = "route3";
    private static final String ROUTE_SCENE_4_NAME = "route4";

    private RouterHistory routerHistory;

    @Mock
    RouteScene routeScene1;
    @Mock
    RouteScene routeScene2;
    @Mock
    RouteScene routeScene3;
    @Mock
    RouteScene routeScene4;

    @BeforeEach
    private void beforeEach() {
        routerHistory = new RouterHistory();
    }

    @Test
    public void testRouterHistoryWithNoElements() {
        assertTrue(routerHistory.getBreadcrumb().isEmpty());
        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
    }

    @Test
    public void testRouterHistoryWithOneElement() {
        when(routeScene1.getRouteId()).thenReturn(ROUTE_SCENE_1_NAME);

        routerHistory.pushState(routeScene1);

        assertEquals(1, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
    }

    @Test
    public void testRouterHistoryWithFourElements() {
        when(routeScene1.getRouteId()).thenReturn(ROUTE_SCENE_1_NAME);
        when(routeScene2.getRouteId()).thenReturn(ROUTE_SCENE_2_NAME);
        when(routeScene3.getRouteId()).thenReturn(ROUTE_SCENE_3_NAME);
        when(routeScene4.getRouteId()).thenReturn(ROUTE_SCENE_4_NAME);

        routerHistory.pushState(routeScene1);

        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        routerHistory.pushState(routeScene2);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        routerHistory.pushState(routeScene3);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        routerHistory.pushState(routeScene4);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        assertEquals(4, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_3_NAME, routerHistory.getBreadcrumb().get(2));
        assertEquals(ROUTE_SCENE_4_NAME, routerHistory.getBreadcrumb().get(3));
    }

    @Test
    public void testRouterHistoryWithFourElementsGoingBackAndForward() {
        when(routeScene1.getRouteId()).thenReturn(ROUTE_SCENE_1_NAME);
        when(routeScene2.getRouteId()).thenReturn(ROUTE_SCENE_2_NAME);
        when(routeScene3.getRouteId()).thenReturn(ROUTE_SCENE_3_NAME);
        when(routeScene4.getRouteId()).thenReturn(ROUTE_SCENE_4_NAME);

        routerHistory.pushState(routeScene1);

        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        routerHistory.pushState(routeScene2);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());

        routerHistory.pushState(routeScene3);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
        assertEquals(3, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_3_NAME, routerHistory.getBreadcrumb().get(2));

        routerHistory.goBack();

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertTrue(routerHistory.canGoForwardProperty().get());
        assertEquals(2, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));

        routerHistory.goBack();

        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertTrue(routerHistory.canGoForwardProperty().get());
        assertEquals(1, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));

        routerHistory.goBack();

        assertFalse(routerHistory.canGoBackwardProperty().get());
        assertTrue(routerHistory.canGoForwardProperty().get());
        assertEquals(1, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));

        routerHistory.goForward();

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertTrue(routerHistory.canGoForwardProperty().get());
        assertEquals(2, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));

        routerHistory.goForward();

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
        assertEquals(3, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_3_NAME, routerHistory.getBreadcrumb().get(2));

        routerHistory.goBack();

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertTrue(routerHistory.canGoForwardProperty().get());
        assertEquals(2, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));

        routerHistory.pushState(routeScene4);
        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
        assertEquals(3, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_4_NAME, routerHistory.getBreadcrumb().get(2));

        routerHistory.goForward();
        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
        assertEquals(3, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_4_NAME, routerHistory.getBreadcrumb().get(2));

        routerHistory.pushState(routeScene3);

        assertTrue(routerHistory.canGoBackwardProperty().get());
        assertFalse(routerHistory.canGoForwardProperty().get());
        assertEquals(4, routerHistory.getBreadcrumb().size());
        assertEquals(ROUTE_SCENE_1_NAME, routerHistory.getBreadcrumb().get(0));
        assertEquals(ROUTE_SCENE_2_NAME, routerHistory.getBreadcrumb().get(1));
        assertEquals(ROUTE_SCENE_4_NAME, routerHistory.getBreadcrumb().get(2));
        assertEquals(ROUTE_SCENE_3_NAME, routerHistory.getBreadcrumb().get(3));
    }

    @Test
    public void testGetFormattedBreadcrumbWithDelimiterCaseOneElement() {
        when(routeScene1.getRouteId()).thenReturn(ROUTE_SCENE_1_NAME);

        routerHistory.pushState(routeScene1);
        String formattedBreadcrumb = routerHistory.getFormattedBreadcrumbWithDelimiter(",");

        assertEquals(ROUTE_SCENE_1_NAME, formattedBreadcrumb);
    }

    @Test
    public void testGetFormattedBreadcrumbWithDelimiterCaseFourElements() {
        String EXPECTED_OUTPUT = ROUTE_SCENE_1_NAME + "," + ROUTE_SCENE_2_NAME + "," + ROUTE_SCENE_3_NAME + "," + ROUTE_SCENE_4_NAME;
        when(routeScene1.getRouteId()).thenReturn(ROUTE_SCENE_1_NAME);
        when(routeScene2.getRouteId()).thenReturn(ROUTE_SCENE_2_NAME);
        when(routeScene3.getRouteId()).thenReturn(ROUTE_SCENE_3_NAME);
        when(routeScene4.getRouteId()).thenReturn(ROUTE_SCENE_4_NAME);

        routerHistory.pushState(routeScene1);
        routerHistory.pushState(routeScene2);
        routerHistory.pushState(routeScene3);
        routerHistory.pushState(routeScene4);
        String formattedBreadcrumb = routerHistory.getFormattedBreadcrumbWithDelimiter(",");

        assertEquals(EXPECTED_OUTPUT, formattedBreadcrumb);
    }
}
