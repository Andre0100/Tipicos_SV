package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.AbstractDataPersistence;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractDataSourceTest {

    @Mock
    private AbstractDataPersistence<String> mockPersistence;

    private AbstractDataSource<String> testDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testDataSource = new AbstractDataSource<String>() {
            @Override
            public AbstractDataPersistence<String> getBean() {
                return mockPersistence;
            }

            @Override
            public Integer getId(String registro) {
                return registro.hashCode();
            }

            @Override
            public String getClassName() {
                return "TestDataSource";
            }
        };
    }

    @Test
    void testFindRange_Success() throws Exception {
        // Arrange
        List<String> mockData = Arrays.asList("A", "B", "C");
        when(mockPersistence.findRange(0, 20)).thenReturn(mockData);
        when(mockPersistence.count()).thenReturn(100);

        // Act
        Response response = testDataSource.findRange(0, 20);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(mockData, response.getEntity());
        assertEquals("100", response.getHeaderString("Total-records"));
    }

    @Test
    void testFindRange_InvalidFirstParameter() {
        // Act
        Response response = testDataSource.findRange(-1, 20);

        // Assert
        assertEquals(422, response.getStatus());
    }

    @Test
    void testFindRange_InvalidMaxParameter() {
        // Act
        Response response = testDataSource.findRange(0, -5);

        // Assert
        assertEquals(422, response.getStatus());
    }

    @Test
    void testFindRange_MaxExceedsLimit() {
        // Act
        Response response = testDataSource.findRange(0, 51);

        // Assert
        assertEquals(422, response.getStatus());
    }

    @Test
    void testFindRange_ExceptionHandling() throws Exception {
        // Arrange
        when(mockPersistence.findRange(0, 20))
                .thenThrow(new RuntimeException("Database error"));

        // Act
        Response response = testDataSource.findRange(0, 20);

        // Assert
        assertEquals(500, response.getStatus());
        assertEquals("Database error", response.getEntity());
        verify(mockPersistence, times(1)).findRange(0, 20);
    }

    @Test
    void testFindRange_DefaultValues() throws Exception {
        // Arrange
        List<String> mockData = Arrays.asList("X", "Y", "Z");
        when(mockPersistence.findRange(0, 20)).thenReturn(mockData); // Valores por defecto
        when(mockPersistence.count()).thenReturn(50);

        // Act - No necesitamos pasar null, simplemente verificamos que usa 0 y 20
        Response response = testDataSource.findRange(0, 20);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(mockData, response.getEntity());
        assertEquals("50", response.getHeaderString("Total-records"));
    }

    @Test
    void testFindRange_EdgeCaseZeroRecords() throws Exception {
        // Arrange
        List<String> emptyList = List.of();
        when(mockPersistence.findRange(5, 10)).thenReturn(emptyList);
        when(mockPersistence.count()).thenReturn(0);

        // Act
        Response response = testDataSource.findRange(5, 10);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(((List<?>) response.getEntity()).isEmpty());
        assertEquals("0", response.getHeaderString("Total-records"));
    }

    @Test
    void testFindRange_UpperBoundary() throws Exception {
        // Arrange
        List<String> mockData = Arrays.asList("A", "B");
        when(mockPersistence.findRange(10, 50)).thenReturn(mockData);
        when(mockPersistence.count()).thenReturn(1000);

        // Act (probando el límite superior de max=50)
        Response response = testDataSource.findRange(10, 50);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(2, ((List<?>) response.getEntity()).size());
        assertEquals("1000", response.getHeaderString("Total-records"));
    }
}
